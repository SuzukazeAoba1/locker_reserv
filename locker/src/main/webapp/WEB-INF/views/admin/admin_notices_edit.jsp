<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Notice Edit</title>
  <style>
    .form {max-width:680px;}
    .row {display:flex; gap:12px; margin-bottom:10px; align-items:center;}
    .row label {width:120px;}
    .row input[type=text] {flex:1; padding:6px 8px;}
    .row textarea {flex:1; padding:6px 8px; min-height:180px;}
    .actions {margin-top:16px; display:flex; gap:8px;}
  </style>
</head>
<body>
  <h2>お知らせ編集</h2>

  <div class="form">
    <form method="post" action="${pageContext.request.contextPath}/admin/notices/${notice.id}/edit">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <input type="hidden" name="backUrl" value="${backUrl}"/>

      <!-- 작성자 값을 항상 서버로 보냄 (변경은 못 하지만 값은 유지) -->
      <input type="hidden" name="authorId" value="${notice.authorId}"/>

      <div class="row">
        <label>ID</label>
        <div>${notice.id}</div>
      </div>

      <div class="row">
        <label>Title</label>
        <input type="text" name="title" value="${notice.title}" required />
      </div>

      <div class="row">
        <label>Content</label>
        <textarea name="content" required>${notice.content}</textarea>
      </div>

      <div class="row">
        <label>Created</label>
        <div><fmt:formatDate value="${notice.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
      </div>

      <div class="actions">
        <button type="submit">保存</button>
        <button type="button" onclick="location.href='${backUrl}'">キャンセル</button>
      </div>
    </form>
  </div>
</body>
</html>
