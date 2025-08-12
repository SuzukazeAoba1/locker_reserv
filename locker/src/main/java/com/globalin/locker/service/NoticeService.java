package com.globalin.locker.service;

import com.globalin.locker.domain.Notice;
import com.globalin.locker.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    private final NoticeMapper noticeMapper;

    public NoticeService(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    public List<Notice> getAllNotices() {
        return noticeMapper.selectAll();
    }
}
