<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Notice View</title>
 <style>
    * {
       font-family: 'Noto Sans JP', 'Hiragino Kaku Gothic Pro', 'Yu Gothic', 'Meiryo', sans-serif;
       font-weight: 500;
    }

    .page-container {
      max-width: 672px; /* 30% 축소 (960px -> 672px) */
      margin: 32px auto;
      padding: 0 16px;
      box-sizing: border-box;
    }

    .page-card {
      background: #fff;
      border: 1px solid #e5e7eb;
      border-radius: 12px;
      box-shadow: 0 4px 16px rgba(0,0,0,.06);
      padding: 24px; /* 패딩도 약간 축소 */
    }

    /* 게시글 제목 */
    .post-title {
      font-size: 1.5rem; /* 폰트 크기 약간 축소 */
      font-weight: 700;
      color: #111827;
      margin: 0 0 16px 0;
      line-height: 1.3;
      border-bottom: 2px solid #f3f4f6;
      padding-bottom: 12px;
    }

    /* 메타 정보 영역 */
    .post-meta {
      display: flex;
      gap: 20px;
      margin-bottom: 24px;
      padding: 10px 0;
      border-bottom: 1px solid #e5e7eb;
      color: #6b7280;
      font-size: 0.85rem;
    }

    .meta-item {
      display: flex;
      align-items: center;
      gap: 6px;
    }

    .meta-label {
      font-weight: 600;
      color: #374151;
    }

    /* 내용 영역 */
    .post-content {
      background: #fafafa;
      border: 1px solid #e5e7eb;
      border-radius: 8px;
      padding: 20px;
      margin-bottom: 24px;
      min-height: 160px;
      white-space: pre-wrap;
      line-height: 1.6;
      font-size: 0.95rem;
      color: #374151;
      text-align: left; /* 왼쪽 정렬 명시 */
      vertical-align: top; /* 위쪽 정렬 명시 */
    }

    /* 액션 버튼 */
    .actions {
      display: flex;
      gap: 10px;
      padding-top: 12px;
      border-top: 1px solid #e5e7eb;
      justify-content: center;
    }

    .btn {
      display: inline-block;
      padding: 16px 32px;
      border: 1px solid #d1d5db;
      border-radius: 6px;
      text-decoration: none;
      background: #f9fafb;
      color: #374151;
      font-weight: 500;
      font-size: 1.1rem;
      transition: all 0.2s;
    }

    .btn:hover {
      background: #f3f4f6;
      border-color: #9ca3af;
    }

    .btn-edit {
      background: #3b82f6;
      color: white;
      border-color: #3b82f6;
    }

    .btn-edit:hover {
      background: #2563eb;
      border-color: #2563eb;
    }
  </style>
</head>
<body>
  <div class="page-container">
    <div class="page-card">
      <!-- 게시글 제목 -->
      <h1 class="post-title">
        <c:out value="${notice.title}"/>
      </h1>

      <!-- 메타 정보 (작성자, 작성일, ID) -->
      <div class="post-meta">
        <div class="meta-item">
          <span class="meta-label">作成者:</span>
          <span><c:out value="${notice.authorUsername}"/></span>
        </div>
        <div class="meta-item">
          <span class="meta-label">作成日:</span>
          <span><fmt:formatDate value="${notice.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
        </div>
      </div>

      <!-- 게시글 내용 -->
      <div class="post-content"><c:out value="${notice.content}"/></div>

      <!-- 액션 버튼 -->
      <div class="actions">
        <!-- 필요 시 편집 링크 노출 -->
        <!--<a class="btn btn-edit" href="${pageContext.request.contextPath}/admin/notices/${notice.id}/edit">編集</a>-->
        <a class="btn" href="${backUrl}?page=${page}">戻る</a>
        <c:if test="${sessionScope.loginUser != null && sessionScope.loginUser.role eq 'ADMIN'}">
            <a class="btn" href="${pageContext.request.contextPath}/admin/notices/${notice.id}/delete"
               style="color:red; text-decoration:none; margin-left:8px;">
               削除
            </a>
        </c:if>
      </div>
    </div>
  </div>
</body>
</html>
