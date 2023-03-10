package com.sh.j3l.notice.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sh.j3l.notice.model.dao.NoticeDao;
import com.sh.j3l.notice.model.dto.Notice;

@Service
public class NoticeServiceImpl implements NoticeService{
	
	@Autowired
	private NoticeDao noticeDao;

	@Override
	public List<Notice> selectAllNotice(RowBounds rowBounds) {
		return noticeDao.selectAllNotice(rowBounds);
	}

	@Override
	public int insertNotice(Notice notice) {
		return noticeDao.insertNotice(notice);
	}
	

}