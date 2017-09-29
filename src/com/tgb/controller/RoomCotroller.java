package com.tgb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tgb.model.PokerRoom;
import com.tgb.model.RoomInfo;
import com.tgb.model.User;
import com.tgb.service.UserService;
import com.tgb.websocket.WebSocketUtils;

@Controller
@RequestMapping("/room")
public class RoomCotroller {

	public static Map<Integer, Integer> roomMap = new ConcurrentHashMap<Integer, Integer>();

	@Autowired
	private UserService userService;
     
	@RequestMapping("/intoRoom")
	@ResponseBody
	public Map<String, Object> intoRoom(@RequestParam("room_no") int room_no, @RequestParam("user_id") int user_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean is_exist = false;
		List<PokerRoom> roomList = null;

		if (!WebSocketUtils.roomMap.containsKey(room_no)) {
			roomList = new ArrayList<PokerRoom>();
		} else {
			roomList = (List<PokerRoom>) WebSocketUtils.roomMap.get(room_no);
		}

		PokerRoom room = new PokerRoom();

		int sort = roomList.size() + 1;
		User user = userService.findById(user_id);

		room.setUser(user);
		room.setRoom_no(room_no);
		room.setRoom_state(true);
		room.setSort(sort);
		room.setOnlineCount(sort);
		room.setIs_ready(0);
		room.setIs_continue(0);

		if (sort == 1) {
			room.setRoom_owner(user_id);
		}

		for (int i = 0; i < roomList.size(); i++) {
			if (roomList.get(i).getUser().getId() == user_id) {
				is_exist = true;
				break;
			}
		}

		if (is_exist == false) {
			roomList.add(room);
		}

		WebSocketUtils.roomMap.put(room_no, roomList);
		map.put("success", true);
		return map;
	}

	@RequestMapping("/leaveRoom")
	@ResponseBody
	public Map<String, Object> leaveRoom(@RequestParam("room_no") int room_no, @RequestParam("user_id") int user_id) {
		Map<String, Object> map = new HashMap<String, Object>();

		List<PokerRoom> roomList = null;

		if (!WebSocketUtils.roomMap.containsKey(room_no)) {
			roomList = new ArrayList<PokerRoom>();
		} else {
			roomList = (List<PokerRoom>) WebSocketUtils.roomMap.get(room_no);

			for (int i = 0; i < roomList.size(); i++) {
				if (roomList.get(i).getUser().getId() == user_id) {
					roomList.remove(i);
					break;
				}
			}

			for (int i = 0; i < roomList.size(); i++) {
				PokerRoom room = roomList.get(i);
				int sort = (i + 1);
				room.setSort(sort);
				room.setOnlineCount(sort);

				if (sort == 1) {
					room.setRoom_owner(user_id);
				}
				roomList.set(i, room);
			}

			WebSocketUtils.roomMap.put(room_no, roomList);
		}
		return map;
	}

	@RequestMapping("/loadAllRoom")
	@ResponseBody
	public Map<String, Object> loadAllRoom() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<RoomInfo> roomInfoList = new ArrayList<RoomInfo>();

		for (Map.Entry<Integer, Object> entry : WebSocketUtils.roomMap.entrySet()) {
			RoomInfo roomInfo = new RoomInfo();
			roomInfo.setRoom_no(entry.getKey());
			List<PokerRoom> roomList = (List<PokerRoom>) entry.getValue();
			roomInfo.setOnlineCount(roomList.size());
			roomInfoList.add(roomInfo);
		}

		map.put("success", true);
		map.put("data", roomInfoList);
		return map;
	}

	@RequestMapping("/loadRoom")
	@ResponseBody
	public Map<String, Object> loadRoom(@RequestParam("room_no") int room_no) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (WebSocketUtils.roomMap.containsKey(room_no)) {
			map.put("success", true);
			map.put("data", WebSocketUtils.roomMap.get(room_no));
		} else {
			map.put("success", false);
		}

		return map;
	}

	@RequestMapping("/loadOnlineUser")
	@ResponseBody
	public Map<String, Object> loadOnlineUser(@RequestParam("room_no") int room_no) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (WebSocketUtils.roomMap.containsKey(room_no)) {
			map.put("success", true);

			List<PokerRoom> roomList = (List<PokerRoom>) WebSocketUtils.roomMap.get(room_no);

			for (int i = 0; i < roomList.size(); i++) {
				if (roomList.get(i).getIs_continue() != -1) {
					roomList.remove(i);
				}
			}
			map.put("data", roomList);
		} else {
			map.put("success", false);
		}

		return map;
	}


}
