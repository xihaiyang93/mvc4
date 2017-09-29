package com.tgb.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.Session;

import com.tgb.controller.UserController;
import com.tgb.model.PokerRoom;

public class WebSocketUtils {
	private static Map<String,Session> map = new ConcurrentHashMap<String,Session>();
   
	public static int onlineCount = 0;
	
	public static Map<Integer,Object> roomMap = new ConcurrentHashMap<Integer,Object>();
	
    private static final String PREFIX="user-->";
  
    public static void put(String userid,Session session){
        map.put(getKey(userid),session);
    }

    public static Session get(String userid){
        return map.get(getKey(userid));
    }

    public static List<Session> getOtherSession(String userid){
        List<Session> result = new ArrayList<Session>();
        Set<Map.Entry<String, Session>> set=  map.entrySet();
        
        for(Map.Entry<String, Session> s:set){
            /*if(!s.getKey().equals(getKey(userid))){  
            }*/
        	 result.add(s.getValue());
        }
        return result;
    }

    public static void remove(String userid){
        map.remove(getKey(userid));
    }

    public static boolean hasConnection(String userid){
        return map.containsKey(getKey(userid));
       
    }

    private static String getKey(String userid){
        return PREFIX+"_"+userid;
    }
}
