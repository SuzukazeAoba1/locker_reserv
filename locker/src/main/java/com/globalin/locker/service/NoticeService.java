package com.globalin.locker.service;

import com.globalin.locker.domain.Notice;
import com.globalin.locker.mapper.NoticeMapper;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeMapper noticeMapper;

    // 리스트 모두 출력
    public List<Notice> getAllNotices() {
        return noticeMapper.selectAll();
    }

    public int getAllNoticesCount() {
        return noticeMapper.selectPageCount().intValue();
    }

    @Transactional
    public int updateNotice(Notice n) {
        int rows = noticeMapper.update(n);
        if (rows == 0) throw new IllegalStateException("更新対象が存在しません。");
        return rows;
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


    public int deleteNotice(long id) {
        return noticeMapper.deleteById(id);
    }



}
