<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Notice View</title>
  <style>
    .page-container {
      max-width: 960px;
      margin: 32px auto;
      padding: 0 16px;
      box-sizing: border-box;
    }

    .page-card {
      background: #fff;
      border: 1px solid #e5e7eb;
      border-radius: 12px;
      box-shadow: 0 4px 16px rgba(0,0,0,.06);
      padding: 24px;
    }

    .page-title {
      margin: 0 0 24px;
      font-size: 1.25rem;
      font-weight: 700;
      color: #111827;
    }

    .form {max-width:680px;}
    .row {display:flex; gap:12px; margin-bottom:10px; align-items:center;}
    .row label {width:120px; font-weight:600;}
    .value {flex:1; padding:6px 8px;}
    .content-box {flex:1; padding:6px 8px; min-height:180px; white-space:pre-wrap; border:1px solid #ddd; border-radius:4px; background:#f9f9f9;}
    .actions {margin-top:16px; display:flex; gap:8px;}
    .btn {display:inline-block; padding:8px 14px; border:1px solid #ccc; border-radius:6px; text-decoration:none; background:#f7f7f7; color:#222;}
    .btn:hover {background:#eee;}
  </style>
</head>
<body>
  <div class="page-container">
    <div class="page-card">
      <h2 class="page-title">お知らせ 詳細</h2>

      <div class="form">
        <!-- 폼 제거: 읽기 전용 출력 -->
        <div class="row">
          <label>ID</label>
          <div class="value"><c:out value="${notice.id}"/></div>
        </div>
        <div class="row">
          <label>Title</label>
          <div class="value"><c:out value="${notice.title}"/></div>
        </div>
        <div class="row">
          <label>Content</label>
          <div class="content-box"><c:out value="${notice.content}"/></div>
        </div>
        <div class="row">
          <label>Created</label>
          <div class="value"><fmt:formatDate value="${notice.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
        </div>
        <div class="actions">
          <!-- 필요 시 편집 링크 노출 -->
          <!--<a class="btn" href="${pageContext.request.contextPath}/admin/notices/${notice.id}/edit">編集</a>-->
          <a class="btn" href="${backUrl}">戻る</a>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
