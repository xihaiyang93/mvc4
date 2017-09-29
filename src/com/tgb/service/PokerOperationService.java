package com.tgb.service;

import java.util.List;

import com.tgb.model.PokerOperation;
import com.tgb.model.PokerRecord;

public interface PokerOperationService {

	int save(PokerOperation pokerOperation);

	boolean update(PokerOperation pokerOperation);

	boolean delete(int id);

	PokerRecord findById(int id);

	List<PokerOperation> findAll();

	int getRoomSumMoney(int room_no, int poker_record_id);

	int getUserSumMoney(int room_no, int user_id, int poker_record_id);

	int getLastMultiple(int room_no, int user_id, int poker_record_id);
	
	PokerOperation findLast(int room_no,int user_id,int poker_record_id); 
}
