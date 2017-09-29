package com.tgb.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgb.mapper.PokerOperationMappper;
import com.tgb.model.PokerOperation;
import com.tgb.model.PokerRecord;
import com.tgb.service.PokerOperationService;

@Service
@Transactional 
public class PokerOperationServiceImpl implements PokerOperationService {
    
	@Resource
	private PokerOperationMappper mapper;
	
	@Override
	public int save(PokerOperation pokerOperation) {
		return mapper.save(pokerOperation);
	}

	@Override
	public boolean update(PokerOperation pokerOperation) {
		// TODO Auto-generated method stub
		return mapper.update(pokerOperation);
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return mapper.delete(id);
	}

	@Override
	public PokerRecord findById(int id) {
		// TODO Auto-generated method stub
		return mapper.findById(id);
	}

	@Override
	public List<PokerOperation> findAll() {

		return mapper.findAll();
	}

	@Override
	public int getRoomSumMoney(int room_no, int poker_record_id) {
		return mapper.getRoomSumMoney(room_no, poker_record_id);
	}

	@Override
	public int getUserSumMoney(int room_no, int user_id, int poker_record_id) {
		return mapper.getUserSumMoney(room_no, user_id, poker_record_id);
	}

	@Override
	public int getLastMultiple(int room_no, int user_id, int poker_record_id) {
		return mapper.getLastMultiple(room_no, user_id, poker_record_id);
	}

	@Override
	public PokerOperation findLast(int room_no, int user_id, int poker_record_id) {
		// TODO Auto-generated method stub
		return mapper.findLast(room_no, user_id, poker_record_id);
	}
}
