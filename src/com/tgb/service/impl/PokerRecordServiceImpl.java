package com.tgb.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgb.mapper.PokerRecordMapper;
import com.tgb.model.PokerRecord;
import com.tgb.service.PokerRecordService;

@Service("pokerRecordService")
@Transactional 
public class PokerRecordServiceImpl implements PokerRecordService {
    
	@Resource
	private PokerRecordMapper mapper;
	
	public int save(PokerRecord PokerRecord) {
		return mapper.save(PokerRecord);
	}

	public boolean update(PokerRecord PokerRecord) {

		return mapper.update(PokerRecord);
	}

	public boolean delete(int id) {
		return mapper.delete(id);
	}

	public PokerRecord findById(int id) {
		return mapper.findById(id);
	}

	public List<PokerRecord> findAll() {
		return mapper.findAll();
	}

	public int getMoney(int room_no) {
		return mapper.getMoney(room_no);
	}

}
