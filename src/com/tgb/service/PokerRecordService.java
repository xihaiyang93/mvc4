package com.tgb.service;

import java.util.List;

import com.tgb.model.PokerRecord;

public interface PokerRecordService {
	
	int save(PokerRecord PokerRecord);

	boolean update(PokerRecord PokerRecord);

	boolean delete(int id);

	PokerRecord findById(int id);

	List<PokerRecord> findAll();

	int getMoney(int room_no);
}
