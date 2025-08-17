<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>アカウント編集</title>
  <style>
    .form {max-width:560px;}
    .row {display:flex; gap:12px; margin-bottom:10px; align-items:center;}
    .row label {width:140px;}
    .row input, .row select {flex:1; padding:6px 8px;}
    .actions {margin-top:16px; display:flex; gap:8px;}
  </style>
</head>
<body>
<h2>アカウント編集</h2>

<div class="form">
  <form method="post" action="${pageContext.request.contextPath}/admin/accounts/${account.id}/edit">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="hidden" name="backUrl" value="${backUrl}"/>

    <div class="row">
      <label>ID</label>
      <div>${account.id}</div>
    </div>

    <div class="row">
      <label>Username</label>
      <input type="text" name="username" value="${account.username}" required/>
    </div>

    <div class="row">
      <label>Role</label>
      <select name="role" required>
        <c:forEach var="r" items="${roles}">
          <option value="${r}" <c:if test="${r == account.role}">selected</c:if>>${r}</option>
        </c:forEach>
      </select>
    </div>

    <div class="row">
      <label>Email</label>
      <input type="email" name="email" value="${account.email}" />
    </div>

    <div class="row">
      <label>Phone</label>
      <input type="text" name="phoneNumber" value="${account.phoneNumber}" />
    </div>

    <div class="row">
      <label>Active</label>
      <input type="checkbox" name="isActive" value="Y"
             <c:if test="${account.isActive == 'Y'}">checked</c:if> />
    </div>

    <div class="row">
      <label>Created At</label>
      <div><fmt:formatDate value="${account.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
    </div>

    <div class="actions">
      <button type="submit">保存</button>
      <button type="button" onclick="location.href='${backUrl}'">キャンセル</button>
    </div>
  </form>
</div>
</body>
</html>
