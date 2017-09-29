package com.tgb.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.tgb.controller.UserController;
import com.tgb.model.Poker;
import com.tgb.model.PokerOperation;
import com.tgb.model.PokerRecord;
import com.tgb.model.PokerRoom;
import com.tgb.model.User;
import com.tgb.service.PokerOperationService;
import com.tgb.service.PokerRecordService;
import com.tgb.websocket.WebSocketUtils;

@Component
public class PokerRoomUtil {
   
	
	@Autowired  
    private PokerRecordService pokerRecordService;  
	private PokerOperationService pokerOperationService;  
    private static PokerRoomUtil pokerRoomUtil;  
  
    public void setUserInfo(PokerRecordService pokerRecordService,PokerOperationService pokerOperationService) {  
        this.pokerRecordService = pokerRecordService;
        this.pokerOperationService = pokerOperationService;
    }  
      
    @PostConstruct  
    public void init() {  
    	pokerRoomUtil = this;  
    	pokerRoomUtil.pokerRecordService = this.pokerRecordService; 
    	pokerRoomUtil.pokerOperationService = this.pokerOperationService;  
    }  
	
	/**
	 * 设置金额
	 * 
	 * @param money
	 * @return
	 */
	public  Map<String, Object> set_initMoeny(int money) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("money", money);
		return map;
	}

	/**
	 * 准备
	 * 
	 * @param room_no
	 * @param user_id
	 * @param money
	 * @return
	 */
	public  Map<String, Object> ready(int room_no, int user_id, int money) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (WebSocketUtils.roomMap.containsKey(room_no)) {
			map.put("success", true);

			List<PokerRoom> roomList = (List<PokerRoom>) WebSocketUtils.roomMap.get(room_no);

			for (int i = 0; i < roomList.size(); i++) {
				if (roomList.get(i).getUser().getId() == user_id) {
					PokerRoom pokerRoom = roomList.get(i);
					pokerRoom.setIs_ready(1);
					roomList.set(i, pokerRoom);
				}
			}
			WebSocketUtils.roomMap.put(room_no, roomList);
			map.put("data", roomList);
		} else {
			map.put("success", false);
		}
		return map;
	}
	
	public  Map<String, Object> set_initMoeny(int room_no,int money){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		return map;
	}

	/**
	 * 发牌
	 * 
	 * @param room_no
	 * @param money
	 * @return
	 */
	public  Map<String, Object> poker_start(int room_no, int money) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (WebSocketUtils.roomMap.containsKey(room_no)) {
			List<PokerRoom> roomList = (List<PokerRoom>) WebSocketUtils.roomMap.get(room_no);

			int ready_count = 0;
			int to_user = 0;
			int[] users = new int[roomList.size()];
					
			for (int i = 0; i < roomList.size(); i++) {

				if (roomList.get(i).getSort() == 1) {
					to_user = roomList.get(i).getUser().getId();
				}

				users[i] = roomList.get(i).getUser().getId();

				if (roomList.get(i).getIs_ready() == 1) {
					ready_count++;
				}
			}

			int poker_record_id = 0;

			if (ready_count == roomList.size()) {
				map.put("success", true);
				map.put("to_user", to_user);
				map.put("to_location", 3);
				List<PokerRecord> pokerRecordList = PokerUtil.sendPoker(users, room_no, money);
				ObjectMapper mapper = new ObjectMapper();

				for (int i = 0; i < pokerRecordList.size(); i++) {
					PokerRecord model = pokerRecordList.get(i);
					PokerRecord pokerRecord = new PokerRecord();
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
					String serial_no = format.format(new Date());
					serial_no = serial_no + "-" + room_no;
					pokerRecord.setserial_no(serial_no);
					pokerRecord.setUser_id(model.getUser_id());
					pokerRecord.setBeginTime(new Date());
					pokerRecord.setIs_win(0);
					pokerRecord.setMoney(0);
					pokerRecord.setRoom_no(room_no);

					try {
						pokerRecord.setPokers(mapper.writeValueAsString(model.getPokerList()));
						poker_record_id = pokerRoomUtil.pokerRecordService.save(pokerRecord);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				for (int i = 0; i < roomList.size(); i++) {

					if (roomList.get(i).getSort() == 1) {
						PokerRoom pookerRoom = roomList.get(i);
						pookerRoom.setPoker_record_id(poker_record_id);
						roomList.set(i, pookerRoom);
					}
				}

				WebSocketUtils.roomMap.put(room_no, roomList);

			} else {
				map.put("success", false);
				map.put("to_location", 2);
				map.put("message", "目前还不能开始,因为还有人未准备...");
			}
		} else {
			map.put("success", false);
			map.put("message", "未查询到改房间的记录...");
		}

		return map;
	}

	// 继续
	public  Map<String, Object> is_continue(int room_no, int user_id, int multiple, int money) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (WebSocketUtils.roomMap.containsKey(room_no)) {
			List<PokerRoom> roomList = (List<PokerRoom>) WebSocketUtils.roomMap.get(room_no);
			int to_user = 0;
			int poker_record_id = 0;

			for (int i = 0; i < roomList.size(); i++) {
				User user = roomList.get(i).getUser();
				if (user.getId() == user_id) {
					PokerRoom pokerRoom = roomList.get(i);
					pokerRoom.setIs_continue(1);
					roomList.set(i, pokerRoom);
				}

				if (roomList.get(i).getSort() == 1) {
					poker_record_id = roomList.get(i).getPoker_record_id();
				}

			}

			List<Integer> users = new ArrayList<Integer>();

			for (int i = 0; i < roomList.size(); i++) {
				if (roomList.get(i).getIs_continue() != -1) {
					users.add(roomList.get(i).getUser().getId());
				}
			}

			int user_index = users.indexOf(user_id);

			if (user_index + 1 <= users.size() - 1) {
				to_user = users.get(user_index + 1);
			} else {
				to_user = users.get(0);
			}

			WebSocketUtils.roomMap.put(room_no, roomList);
            
			
			PokerOperation lastPokerOperation =  pokerOperationService.findLast(room_no, user_id, poker_record_id);
			if(lastPokerOperation.getIs_show() == 1){
				redouble(room_no,user_id,poker_record_id);
			}else{
				PokerOperation pokerOperation = new PokerOperation();
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				String serial_no = format.format(new Date());
				serial_no = serial_no + "-" + room_no;
				pokerOperation.setserial_no(serial_no);
				pokerOperation.setRoom_no(room_no);
				pokerOperation.setUser_id(user_id);
				pokerOperation.setIs_show(0);
				pokerOperation.setIs_compare(0);
				pokerOperation.setCompare_user_id(user_id);
				pokerOperation.setIs_continue(1);
				pokerOperation.setMultiple(multiple);
				pokerOperation.setMoney(money);
				pokerOperation.setTotal_money(money * multiple);
				pokerOperation.setSend_time(new Date());
				pokerOperation.setPoker_record_id(poker_record_id);
				pokerOperationService.save(pokerOperation);
			}
			map.put("success", true);
			map.put("to_user", to_user);
		} else {
			map.put("success", false);
			map.put("message", "未查询到改房间的记录...");
		}

		return map;
	}

	// 看牌
	public  Map<String, Object> look_poker(int room_no, int user_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (WebSocketUtils.roomMap.containsKey(room_no)) {
			Map<Integer, List<Poker>> userPokers = PokerUtil.userPokers;
			map.put("success", true);
			map.put("data", userPokers.get(user_id));

		} else {
			map.put("success", false);
			map.put("message", "未查询到改房间的记录...");
		}

		return map;
	}
     
	// 比牌
	public   Map<String, Object> compare_poker(int room_no, int user_id, int compare_user_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		int to_user = 0;

		if (WebSocketUtils.roomMap.containsKey(room_no)) {
			List<PokerRoom> roomList = (List<PokerRoom>) WebSocketUtils.roomMap.get(room_no);

			Map<Integer, List<Poker>> userPokers = PokerUtil.userPokers;
			List<Poker> user1 = userPokers.get(user_id);
			List<Poker> user2 = userPokers.get(compare_user_id);
			user1 = PokerUtil.sortPoker(user1);
			user2 = PokerUtil.sortPoker(user2);
			Map<Object, Object> maps = PokerUtil.comparePokers(user1, user2);

			int self_result = 0;
			int other_result = 0;
			int multiple = 0;
			int money = 0;
			int poker_record_id = 0;

			for (int i = 0; i < roomList.size(); i++) {
				if (roomList.get(i).getSort() == 1) {
					poker_record_id = roomList.get(i).getPoker_record_id();
				}
			}

			if (maps.get("result").equals(1)) {
				self_result = 1;
				other_result = -1;
			} else if (maps.get("result").equals(-1)) {
				self_result = -1;
				other_result = 1;
			}

			PokerOperation pokerOperation = new PokerOperation();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String serial_no = format.format(new Date());
			serial_no = serial_no + "-" + room_no;
			pokerOperation.setserial_no(serial_no);
			pokerOperation.setRoom_no(room_no);
			pokerOperation.setUser_id(user_id);
			pokerOperation.setIs_show(0);
			pokerOperation.setIs_compare(1);
			pokerOperation.setCompare_user_id(user_id);
			pokerOperation.setIs_continue(self_result);
			pokerOperation.setMultiple(multiple);
			pokerOperation.setMoney(money);
			pokerOperation.setTotal_money(money * multiple);
			pokerOperation.setSend_time(new Date());
			pokerOperation.setPoker_record_id(poker_record_id);
			pokerOperationService.save(pokerOperation);

			pokerOperation = new PokerOperation();
			serial_no = serial_no + "-" + room_no;
			pokerOperation.setserial_no(serial_no);
			pokerOperation.setRoom_no(room_no);
			pokerOperation.setUser_id(user_id);
			pokerOperation.setIs_show(0);
			pokerOperation.setIs_compare(0);
			pokerOperation.setCompare_user_id(user_id);
			pokerOperation.setIs_continue(other_result);
			pokerOperation.setMultiple(multiple);
			pokerOperation.setMoney(money);
			pokerOperation.setTotal_money(money * multiple);
			pokerOperation.setSend_time(new Date());
			pokerOperation.setPoker_record_id(poker_record_id);
			pokerOperationService.save(pokerOperation);

			for (int i = 0; i < roomList.size(); i++) {
				User user = roomList.get(i).getUser();
				if (user.getId() == user_id) {
					PokerRoom pokerRoom = roomList.get(i);
					pokerRoom.setIs_continue(self_result);
					roomList.set(i, pokerRoom);
				}

				if (user.getId() == compare_user_id) {
					PokerRoom pokerRoom = roomList.get(i);
					pokerRoom.setIs_continue(other_result);
					roomList.set(i, pokerRoom);
				}
			}

			WebSocketUtils.roomMap.put(room_no, roomList);

			roomList = (List<PokerRoom>) WebSocketUtils.roomMap.get(room_no);

			List<Integer> users = new ArrayList<Integer>();

			for (int i = 0; i < roomList.size(); i++) {
				if (roomList.get(i).getIs_continue() != -1) {
					users.add(roomList.get(i).getUser().getId());
				}
			}

			if (users.size() <= 1) {
				map.put("success", true);
				map.put("result", 1);
				map.put("data", users.get(0));
				map.put("message", users.get(0) + "胜出!");
				clearPokerRoom(room_no);
				return map;
			}

			int user_index = users.indexOf(user_id);

			if (user_index + 1 <= users.size() - 1) {
				to_user = users.get(user_index + 1);
			} else {
				to_user = users.get(0);
			}

			map.put("success", true);
			map.put("result", 0);
			map.put("user1", map.get("user1"));
			map.put("user2", map.get("user2"));
			map.put("to_user", to_user);
			map.put("message", user_id + "比较" + compare_user_id);

		} else {
			map.put("success", false);
			map.put("message", "未查询到改房间的记录...");
		}

		return map;
	}

	/**
	 * 放弃
	 * 
	 * @param room_no
	 * @param user_id
	 * @return
	 */
	public   Map<String, Object> giveUp(int room_no, int user_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		int to_user = 0;

		if (WebSocketUtils.roomMap.containsKey(room_no)) {
			List<PokerRoom> roomList = (List<PokerRoom>) WebSocketUtils.roomMap.get(room_no);

			int money = 0;
			int poker_record_id = 0;

			for (int i = 0; i < roomList.size(); i++) {
				if (roomList.get(i).getSort() == 1) {
					poker_record_id = roomList.get(i).getPoker_record_id();
				}
			}

			PokerOperation pokerOperation = new PokerOperation();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String serial_no = format.format(new Date());
			serial_no = serial_no + "-" + room_no;
			pokerOperation.setserial_no(serial_no);
			pokerOperation.setRoom_no(room_no);
			pokerOperation.setUser_id(user_id);
			pokerOperation.setIs_show(0);
			pokerOperation.setIs_compare(0);
			pokerOperation.setCompare_user_id(user_id);
			pokerOperation.setIs_continue(-1);
			pokerOperation.setMultiple(0);
			pokerOperation.setMoney(money);
			pokerOperation.setTotal_money(0);
			pokerOperation.setSend_time(new Date());
			pokerOperation.setPoker_record_id(poker_record_id);
			pokerOperationService.save(pokerOperation);

			for (int i = 0; i < roomList.size(); i++) {
				User user = roomList.get(i).getUser();
				if (user.getId() == user_id) {
					PokerRoom pokerRoom = roomList.get(i);
					pokerRoom.setIs_continue(-1);
					roomList.set(i, pokerRoom);
				}
			}

			WebSocketUtils.roomMap.put(room_no, roomList);

			roomList = (List<PokerRoom>) WebSocketUtils.roomMap.get(room_no);

			List<Integer> users = new ArrayList<Integer>();

			for (int i = 0; i < roomList.size(); i++) {
				if (roomList.get(i).getIs_continue() != -1) {
					users.add(roomList.get(i).getUser().getId());
				}
			}

			if (users.size() <= 1) {
				map.put("success", true);
				map.put("result", 1);
				map.put("data", users.get(0));
				map.put("message", users.get(0) + "胜出!");
				clearPokerRoom(room_no);
				return map;
			}

			int user_index = users.indexOf(user_id);

			if (user_index + 1 <= users.size() - 1) {
				to_user = users.get(user_index + 1);
			} else {
				to_user = users.get(0);
			}

			map.put("success", true);
			map.put("result", 0);
			map.put("to_user", to_user);
			map.put("message", pokerOperation);

		} else {
			map.put("success", false);
			map.put("message", "未查询到改房间的记录...");
		}

		return map;
	}
     
	public int redouble(int room_no,int user_id,int poker_record_id){
		List<PokerRoom> roomList = (List<PokerRoom>) WebSocketUtils.roomMap.get(room_no);
        
		int multiple = pokerOperationService.getLastMultiple(room_no, user_id, poker_record_id);
		int money = pokerRecordService.getMoney(room_no);
		
		PokerOperation pokerOperation = new PokerOperation();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String serial_no = format.format(new Date());
		serial_no = serial_no + "-" + room_no;
		pokerOperation.setserial_no(serial_no);
		pokerOperation.setRoom_no(room_no);
		pokerOperation.setUser_id(user_id);
		pokerOperation.setIs_show(1);
		pokerOperation.setIs_compare(0);
		pokerOperation.setCompare_user_id(user_id);
		pokerOperation.setIs_continue(1);
		pokerOperation.setMultiple(multiple * 2);
		pokerOperation.setMoney(money);
		pokerOperation.setTotal_money(money * multiple * 2);
		pokerOperation.setSend_time(new Date());
		pokerOperation.setPoker_record_id(poker_record_id);
		pokerOperationService.save(pokerOperation);
		return multiple*2;
	}
	
	
	/**
	 * 清理房间详情
	 * 
	 * @param room_no
	 * @return
	 */
	public  boolean clearPokerRoom(int room_no) {
		if (WebSocketUtils.roomMap.containsKey(room_no)) {
			List<PokerRoom> roomList = (List<PokerRoom>) WebSocketUtils.roomMap.get(room_no);

			for (int i = 0; i < roomList.size(); i++) {
				PokerRoom pokerRoom = roomList.get(i);
				pokerRoom.setIs_continue(0);
				pokerRoom.setIs_ready(0);
				pokerRoom.setPoker_record_id(0);
				roomList.set(i, pokerRoom);
			}

			WebSocketUtils.roomMap.put(room_no, roomList);
			return true;
		}

		return false;
	}

	public  List<Integer> NotExitRoomUser() {
		List<Integer> roomList = new ArrayList<Integer>();
		List<Integer> userList = new ArrayList<Integer>();
		List<Integer> outRoomList = new ArrayList<Integer>();

		Map<Integer, Object> allUserList = UserController.loginUserList;

		for (Map.Entry<Integer, Object> entry : WebSocketUtils.roomMap.entrySet()) {
			List<PokerRoom> pokerRoomList = (List<PokerRoom>) entry.getValue();

			for (int i = 0; i < pokerRoomList.size(); i++) {
				roomList.add(pokerRoomList.get(i).getUser().getId());
			}
		}

		for (Map.Entry<Integer, Object> entry : allUserList.entrySet()) {
			userList.add(entry.getKey());
		}

		for (int i = 0; i < userList.size(); i++) {
			for (int j = 0; j < roomList.size(); j++) {
				userList.remove(roomList.get(j));
			}
		}
		outRoomList = userList;

		System.out.println(outRoomList);
		return outRoomList;
	}
}
