<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Notice View</title>
  <style>
    .form {max-width:680px;}
    .row {display:flex; gap:12px; margin-bottom:10px; align-items:center;}
    .row label {width:120px; font-weight:600;}
    .value {flex:1; padding:6px 8px;}
    .content-box {flex:1; padding:6px 8px; min-height:180px; white-space:pre-wrap; border:1px solid #ddd; border-radius:4px; background:#fff;}
    .actions {margin-top:16px; display:flex; gap:8px;}
    .btn {display:inline-block; padding:8px 14px; border:1px solid #ccc; border-radius:6px; text-decoration:none; background:#f7f7f7; color:#222;}
    .btn:hover {background:#eee;}
  </style>
</head>
<body>
  <h2>お知らせ 詳細</h2>

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
</body>
</html>
