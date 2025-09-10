<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Notice Create</title>
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
  <h2>お知らせ新規作成</h2>

  <div class="form">
    <form method="post" action="${pageContext.request.contextPath}/admin/notices/new">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <input type="hidden" name="backUrl" value="${backUrl}"/>

      <!-- 로그인 사용자 ID를 서버에서 넣어 전달해 주세요 (예: model.addAttribute("loginUserId", ...)) -->
      <input type="hidden" name="authorId" value="${loginUserId}"/>

      <div class="row">
        <label>Title</label>
        <input type="text" name="title" required />
      </div>

      <div class="row">
        <label>Content</label>
        <textarea name="content" required></textarea>
      </div>

      <div class="actions">
        <button type="submit">保存</button>
        <button type="button" onclick="location.href='${backUrl != null ? backUrl : pageContext.request.contextPath += "/"}'">キャンセル</button>
      </div>
    </form>
  </div>
</body>
</html>
