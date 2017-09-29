package com.tgb.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.tgb.controller.RoomCotroller;
import com.tgb.model.PokerRoom;
import com.tgb.model.SocketModel;
import com.tgb.util.PokerRoomUtil;

@ServerEndpoint("/websocket/{userid}")
public class WebSocketTest {
	private static CopyOnWriteArraySet<WebSocketTest> sessions = new CopyOnWriteArraySet<WebSocketTest>();
	private  Session session;
	PokerRoomUtil PokerRoomUtil = new PokerRoomUtil();

	@OnMessage()
	public void onMessage(String message, Session session, @PathParam("userid") String userid) {
		System.out.println("收到用户信息: " + message);
		ObjectMapper mapper = new ObjectMapper();
		SocketModel model;

		try {
			model = mapper.readValue(message, SocketModel.class);
			model.onlineCount = WebSocketUtils.onlineCount;
			broadcast(model);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@OnOpen
	public void onOpen(Session session, @PathParam("userid") String userid) {
		System.out.println("Client connected");
		this.session = session;
		sessions.add(this);
		WebSocketUtils.put(userid,this.session);
		WebSocketUtils.onlineCount++;
		System.out.println("有新连接加入！当前在线人数为" + WebSocketUtils.onlineCount);
	}

	@OnClose
	public void onClose(@PathParam("userid") String userid) {
		WebSocketUtils.remove(userid);
		sessions.remove(this);
		WebSocketUtils.onlineCount--;
		System.out.println("有一连接关闭！当前在线人数为" + WebSocketUtils.onlineCount);
	}

	@OnError
	public void onError(Session session, java.lang.Throwable throwable, @PathParam("userid") String userid) {
		WebSocketUtils.remove(userid);
		System.err.println("session " + session.getId() + " error:" + throwable);
	}

	/**
	 * sockect收到信息后进行处理广播
	 * 
	 * @param model用户发参数
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	private synchronized  void broadcast(SocketModel model) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> repnseMap = new HashMap<String, Object>();
		switch (model.getDo_action()) {
		case "load_all_room":
			repnseMap.put("success", true);
			repnseMap.put("url", "/room/loadAllRoom");
			repnseMap.put("data", "");
			model.setSend_message(repnseMap);
			break;
		case "load_room":
			if (WebSocketUtils.roomMap.containsKey(model.getRoom_no())) {

				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("room_no", model.getRoom_no());
				repnseMap.put("success", true);
				repnseMap.put("url", "/room/loadRoom");
				repnseMap.put("data", dataMap);
			} else {
				repnseMap.put("success", false);
			}
			model.setSend_message(repnseMap);
			break;
		case "ready":
			repnseMap = PokerRoomUtil.ready(model.getRoom_no(), Integer.parseInt(model.getForm_user()), model.getMoney());
			model.setSend_message(repnseMap);
			break;
		case "set_initMoeny":
			repnseMap = PokerRoomUtil.set_initMoeny(model.getRoom_no(), model.getMoney());
			model.setSend_message(repnseMap);
			break;
		case "poker_start":
			PokerRoomUtil.init();
			repnseMap = PokerRoomUtil.poker_start(model.getRoom_no(), model.getMoney());
			model.setSend_message(repnseMap);
			break;
		case "is_continue":
			repnseMap = PokerRoomUtil.is_continue(model.getRoom_no(), Integer.parseInt(model.getForm_user()), model.getMultiple(), model.getMoney());
			model.setSend_message(repnseMap);
			break;
		case "look_poker":
			repnseMap = PokerRoomUtil.look_poker(model.getRoom_no(), Integer.parseInt(model.getForm_user()));
			model.setSend_message(repnseMap);
			break;
		case "compare_poker":
			repnseMap = PokerRoomUtil.compare_poker(model.getRoom_no(), Integer.parseInt(model.getForm_user()), Integer.parseInt(model.getTo_user()));
			model.setSend_message(repnseMap);
			break;
		case "giveUp":
			repnseMap = PokerRoomUtil.giveUp(model.getRoom_no(), Integer.parseInt(model.getForm_user()));
			model.setSend_message(repnseMap);
			break;
		default:
			break;
		}

		if (model.to_location == 1) {
			List<Session> sessions = WebSocketUtils.getOtherSession(model.form_user);
			if (sessions.size() > 0) {
				for (Session s : sessions) {
					String message = mapper.writeValueAsString(model);
					s.getAsyncRemote().sendText(message);
				}
			}
		} else if (model.to_location == 2) {
			Session session = WebSocketUtils.get(model.to_user);
			if (null != session && session.isOpen()) {
				String message = mapper.writeValueAsString(model);
				session.getAsyncRemote().sendText(message);
			} else {
				// 获取自己的session
				Session self = WebSocketUtils.get(model.form_user);
				model.to_user = model.form_user;
				model.setSend_message("系统消息:对方已下线");
				String message = mapper.writeValueAsString(model);
				self.getAsyncRemote().sendText(message);
			}
		} else {
			if ("load_all_room".equals(model.getDo_action())) {
				List<Integer> sendUserList = PokerRoomUtil.NotExitRoomUser();

				for (int i = 0; i < sendUserList.size(); i++) {
					String sendUserId = sendUserList.get(i).toString();
					Session session = WebSocketUtils.get(sendUserId);
					if (null != session && session.isOpen() && !model.getForm_user().equals(sendUserId) ) {
						String message = mapper.writeValueAsString(model);
						session.getBasicRemote().sendText(message);
					}
				}
			} else {

				List<PokerRoom> pokerRoomList = (List<PokerRoom>) WebSocketUtils.roomMap.get(model.getRoom_no());

				for (int i = 0; i < pokerRoomList.size(); i++) {
					Session session = WebSocketUtils.get(pokerRoomList.get(i).getUser().getId() + "");
					if (null != session && session.isOpen()) {
						String message = mapper.writeValueAsString(model);
						session.getBasicRemote().sendText(message);
					}
				}
			}
		}
	}
}
