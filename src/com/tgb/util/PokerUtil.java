package com.tgb.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.tgb.model.Poker;
import com.tgb.model.PokerRecord;

public class PokerUtil {

	public static List<Poker> pokerLists = new ArrayList<Poker>();

	public static Map<Integer,List<Poker>> userPokers = new HashMap<Integer, List<Poker>>();
     
	public static void main(String[] args) throws InterruptedException {
		//PokerSendTest();
		PokerTest();
	}
	
	public static void PokerSendTest(){
		int room_no = 001;
		int[] users = new int[]{ 1,2,3};
		int money = 10;
		
		List<PokerRecord>  pokerRecordList = sendPoker(users, room_no,money);
		
		for (int i = 0; i < users.length; i++) {
			System.out.println("用户ID:"+users[i]);
			List<Poker> pokerList = pokerRecordList.get(i).getPokerList();
			for (int j = 0; j < pokerList.size(); j++) {
				System.out.print("牌"+(j+1)+":"+pokerList.get(j).getName()+"\t");
				System.out.println("类型:"+ pokerList.get(j).getType()+"\t");
			}
		}
		
		String serial_no =  getSerialNo(room_no);
		Map<Object, Object> maps = comparePoker(serial_no,1,2);
		System.out.println(maps.get("message"));
	}
	
	public static void PokerTest(){
		List<Poker> user1 = new ArrayList<Poker>();
		Poker poker = new Poker();
		poker.setName("Q");
		poker.setNumber(12);
		poker.setType(1);
		user1.add(poker);
		poker = new Poker();
		poker.setName("K");
		poker.setNumber(13);
		poker.setType(1);
		user1.add(poker);
		poker = new Poker();
		poker.setName("A");
		poker.setNumber(14);
		poker.setType(2);
		user1.add(poker);
		
		List<Poker> user2 = new ArrayList<Poker>();
		poker = new Poker();
		poker.setName("Q");
		poker.setNumber(12);
		poker.setType(1);
		user2.add(poker);
		poker = new Poker();
		poker.setName("K");
		poker.setNumber(13);
		poker.setType(1);
		user2.add(poker);
		poker = new Poker();
		poker.setName("A");
		poker.setNumber(14);
		poker.setType(1);
		user2.add(poker);
		
		System.out.println("用户1");
		for (int i = 0; i < user1.size(); i ++) {
			System.out.print("牌"+(i +1)+":"+user1.get(i).getName()+"\t");
			System.out.println("类型:"+ user1.get(i).getType()+"\t");
		}
		
		System.out.println("用户2");
		for (int i = 0; i < user2.size(); i ++) {
			System.out.print("牌"+(i +1)+":"+user2.get(i).getName()+"\t");
			System.out.println("类型:"+ user2.get(i).getType()+"\t");
		}
		
		user1 = sortPoker(user1);
		user2 = sortPoker(user2);
		Map<Object, Object> maps = comparePokers(user1,user2);
		System.out.println(maps.get("message"));
	}

	/**
	 * 生成52张牌
	 * 
	 * @return
	 */
	public static List<Poker> createPoker() {

		if (pokerLists.size() > 0) {
			return pokerLists;
		}

		List<Poker> pokerList = new ArrayList<Poker>();

		int id = 1;

		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 4; j++) {
				Poker poker = new Poker();
				poker.id = id;
				poker.name = (i + 1) + "";
				poker.number = (i + 1);
				poker.type = (j + 1);

				if ((i + 1) == 1) {
					poker.name = "A";
					poker.number = 14;
				} else if ((i + 1) == 11) {
					poker.name = "J";
				} else if ((i + 1) == 12) {
					poker.name = "Q";
				} else if ((i + 1) == 13) {
					poker.name = "K";
				}

				id++;
				pokerList.add(poker);
			}
		}

		pokerLists = pokerList;
		return pokerList;
	}

	/**
	 * 发牌
	 * 
	 * @param users
	 *            用户ID数组
	 * @param room_no
	 *            房间号
	 * @return
	 */
	public static List<PokerRecord> sendPoker(int[] users, int room_no,int money) {
	    
		List<PokerRecord>  pokerRecordList = new ArrayList<>();

		List<Poker> childPokerList = null;

		if (pokerLists.size() == 0) {
			createPoker();
		}

		childPokerList = pokerLists;
		
		for (int i = 0; i < users.length; i++) {			
			
			List<Poker> userPokerList = new ArrayList<Poker>();
			
			for (int j = 0; j < 3; j++) {
				int random = randomPoker(1, childPokerList.size() - 1);
				userPokerList.add(childPokerList.get(random));
				childPokerList.remove(random);
			}
			
			userPokerList = sortPoker(userPokerList);
			
			String serial_no =  getSerialNo(room_no);
			PokerRecord pokerRecord = new PokerRecord();
			pokerRecord.setserial_no(serial_no);
			pokerRecord.setRoom_no(room_no);
			pokerRecord.setBeginTime(new Date());
			pokerRecord.setMoney(money);
			pokerRecord.setPokerList(userPokerList);
			pokerRecord.setUser_id(users[i]);
			pokerRecordList.add(pokerRecord);
			userPokers.put(users[i], userPokerList);
		}
		return pokerRecordList;
	}

	/**
	 * 比较牌大小 1.豹子(同类型中3个A最大，3个2最小) 2.顺金(是花色一样的顺子)有的地方讲A23比QKA大，但常规来讲都是QKA最大
	 * 3.金花(就是花色一样，但不是顺子的三张牌，比较时以最大的一张开始比起，如果最大的一个相同，就从第二个数字比起，以此类推)
	 * 4.顺子(花色不同，三张连续的数字) 5.对子(对子就是有两张一样的牌)例如对K大于对Q，如果同是对Q，那么就比较第三张牌的大小。
	 * 6.单牌(玩家与玩家比较时先比较最大的那一张，最大的那一张一样的话就比较第二张，以此类推。)
	 * 
	 * @param serial_number
	 *            流水号
	 * @param user_id
	 *            用户ID
	 * @param compare_user_id
	 *            比较用户ID
	 */
	public static Map<Object, Object> comparePoker(String serial_no, int user_id, int compare_user_id) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		List<Poker> user1 = userPokers.get(user_id);
		List<Poker> user2 = userPokers.get(compare_user_id);
		
		int user1_pooker_type = validatePoker(user1);
		int user2_pooker_type = validatePoker(user2);
         
		int result = -1;
		
		if (user1_pooker_type > user2_pooker_type) {
			result = 2;
		} else if (user1_pooker_type < user2_pooker_type) {
			result = 1;
		}
		
		if(result == -1){
			switch (user1_pooker_type) {
			case 1:
				if (user1.get(0).number > user2.get(0).number) {
					result = 1;
				} else {
					result = 2;
				}
				break;
			case 2:
				result = compareResult(user1, user2);
				break;
			case 3:
				result = compareResult(user1, user2);
				break;
			case 4:
				result = compareResult(user1, user2);
				break;
			case 5:
				result = compareSubResult(user1, user2);
				break;
			default:
				result = compareResult(user1, user2);
				break;
			}
		}
		
		switch (result) {
		case -1:
			map.put("success", true);
			map.put("message", "比对失败！");
			break;
		case 0:
			map.put("success", true);
			map.put("message", "打成平手！");
			break;
		case 1:
			map.put("success", true);
			map.put("message", "挑战成功！");
			break;
		default:
			map.put("success", true);
			map.put("message", "挑战失败！");
			break;
		}

		return map;
	}
	
	public static Map<Object, Object> comparePokers(List<Poker> user1,List<Poker> user2) {
		Map<Object, Object> map = new HashMap<Object, Object>();
        
		/*List<Poker> user1 = userPokers.get(user_id);
		List<Poker> user2 = userPokers.get(compare_user_id);*/
		
		int user1_pooker_type = validatePoker(user1);
		int user2_pooker_type = validatePoker(user2);
         
		int result = -1;
		
		if (user1_pooker_type > user2_pooker_type) {
			result = 2;
		} else if (user1_pooker_type < user2_pooker_type) {
			result = 1;
		}
		
		if(result == -1){
			switch (user1_pooker_type) {
			case 1:
				if (user1.get(0).number > user2.get(0).number) {
					result = 1;
				} else {
					result = 2;
				}
				break;
			case 2:
				result = compareResult(user1, user2);
				break;
			case 3:
				result = compareResult(user1, user2);
				break;
			case 4:
				result = compareResult(user1, user2);
				break;
			case 5:
				result = compareSubResult(user1, user2);
				break;
			default:
				result = compareResult(user1, user2);
				break;
			}
		}
		
		switch (result) {
		case -1:
			map.put("success", true);
			map.put("result", -1);
			map.put("user1", user1);
			map.put("user2", user2);
			map.put("message", "比对失败！");
			break;
		case 0:
			map.put("success", true);
			map.put("result",  0);
			map.put("user1", user1);
			map.put("user2", user2);
			map.put("message", "打成平手！");
			break;
		case 1:
			map.put("success", true);
			map.put("result",  1);
			map.put("user1", user1);
			map.put("user2", user2);
			map.put("message", "挑战成功！");
			break;
		default:
			map.put("success", true);
			map.put("message", "挑战失败！");
			break;
		}

		return map;
	}

	
	
	
	/**
	 * 验证得到的牌型
	 * 
	 * @param poker
	 *            扑克牌
	 * @return
	 */
	public static int validatePoker(List<Poker> poker) {
        
		if(poker.size() == 0){
			return -1;
		}
		
		if (poker.get(0).number == poker.get(1).number && poker.get(1).number == poker.get(2).number) {
			//豹子
			return 1;
		} else if (poker.get(0).number + 1 == poker.get(1).number && poker.get(1).number + 1 == poker.get(2).number) {
			if (poker.get(0).type == poker.get(1).type && poker.get(1).type == poker.get(2).type) {
				//顺金
				return 2;
			}
			//顺子
			return 4;
		} else if (poker.get(0).type == poker.get(1).type && poker.get(1).type == poker.get(2).type) {
			//金花
			return 3;
		} else if (poker.get(0).number == poker.get(1).number || poker.get(0).number == poker.get(2).number
				|| poker.get(1).number == poker.get(2).number) {
			//对子
			return 5;
		}
		return 6;
	}

	/**
	 * 从大到小比较
	 * 
	 * @param user1
	 *            发起用户
	 * @param user2
	 *            比较用户
	 * @return
	 */
	public static int compareResult(List<Poker> user1, List<Poker> user2) {
		int result = -1;
        
		if(user1.size() == 0 || user2.size() ==0){
			return result;
		}
		
		if (user1.get(2).number > user2.get(2).number) {
			result = 1;
		} else if (user1.get(2).number < user2.get(2).number) {
			result = 2;
		} else {
			if (user1.get(1).number > user2.get(1).number) {
				result = 1;
			} else if (user1.get(1).number < user2.get(1).number) {
				result = 2;
			} else {
				if (user1.get(0).number > user2.get(0).number) {
					result = 1;
				} else if (user1.get(0).number < user2.get(0).number) {
					result = 2;
				} else {
					result = 0;
				}
			}
		}
		return result;
	}

	/**
	 * 比较对子
	 * 
	 * @param user1
	 *            发起用户
	 * @param user2
	 *            比较用户
	 * @return
	 */
	public static int compareSubResult(List<Poker> user1, List<Poker> user2) {

		int result = -1;
		int double1 = 0;
		int double2 = 0;
		int single1 = 0;
		int single2 = 0;

		if (user1.get(0).number == user1.get(1).number) {
			double1 = user1.get(0).number;
			single1 = user1.get(2).number;
		} else if (user1.get(1).number == user1.get(2).number) {
			double1 = user1.get(1).number;
			single1 = user1.get(0).number;
		} else {
			double1 = user1.get(2).number;
			single1 = user1.get(1).number;
		}

		if (user2.get(0).number == user2.get(1).number) {
			double2 = user2.get(0).number;
			single2 = user2.get(2).number;
		} else if (user1.get(1).number == user1.get(2).number) {
			double2 = user2.get(1).number;
			single2 = user2.get(0).number;
		} else {
			double2 = user2.get(2).number;
			single2 = user2.get(1).number;
		}

		if (double1 > double2) {
			result = 1;
		} else if (double1 < double2) {
			result = 2;
		} else {
			if (single1 > single2) {
				result = 1;
			} else if (single1 < single2) {
				result = 2;
			} else {
				result = 0;
			}
		}

		return result;
	}

	public static List<Poker> sortPoker(List<Poker> pokerList) {
		List<Poker> newPokerList = new ArrayList<>();

		int temp = 0; // 临时变量
		int[] arr = new int[3];
		arr[0] = pokerList.get(0).number;
		arr[1] = pokerList.get(1).number;
		arr[2] = pokerList.get(2).number;

		for (int i = 0; i < arr.length; i++) {
			for (int n = 0; n < arr.length; n++) {
				if (arr[n] > arr[i]) {
					temp = arr[n];
					arr[n] = arr[i];
					arr[i] = temp;
				}
			}
		}

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < pokerList.size(); j++) {
				Poker p = pokerList.get(j);
				if (p.getNumber() == arr[i]) {
					newPokerList.add(p);
					pokerList.remove(j);
				}
			}
		}
		return newPokerList;
	}

	/**
	 * 生成随机数
	 * 
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @return
	 */
	public static int randomPoker(int min, int max) {
		Random random = new Random();
		int val = random.nextInt(max) % (max - min + 1) + min;
		return val;
	}
	
	public static String getSerialNo(int room_no){
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String SerialNo = formatter.format(currentTime);
		SerialNo+= room_no;
		return SerialNo;
	}

}
