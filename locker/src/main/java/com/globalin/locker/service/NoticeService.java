package com.globalin.locker.service;

import com.globalin.locker.domain.Notice;
import com.globalin.locker.mapper.NoticeMapper;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class NoticeService {

    private final NoticeMapper noticeMapper;

    public NoticeService(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    // 리스트 모두 출력
    public List<Notice> getAllNotices() {
        return noticeMapper.selectAll();
    }

    // ========================================
    // Notices: Postman 테스트용 단순 컬럼 반환
    // ========================================

    public Notice getNoticeById(long id) {
        return noticeMapper.selectById(id);
    }

    public List<Notice> getNoticesByAuthorId(long authorId) {
        return noticeMapper.selectByAuthorId(authorId);
    }

    public List<Notice> getNoticesPage(int offset, int limit) {
        return noticeMapper.selectPage(offset, limit);
    }

    // Create / Update / Delete
    public int createNotice(Notice notice) {
        return noticeMapper.insert(notice);
    }

    public int updateNotice(Notice notice) {
        return noticeMapper.update(notice);
    }

    public int deleteNotice(long id) {
        return noticeMapper.deleteById(id);
    }
}
