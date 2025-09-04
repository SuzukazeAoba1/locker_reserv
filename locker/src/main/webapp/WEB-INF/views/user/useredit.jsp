<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>ユーザー情報更新</title>
  <style>
    body { font-family: system-ui, -apple-system, Segoe UI, Roboto, sans-serif; }
    .form { max-width: 420px; margin: 24px auto; padding: 16px; border: 1px solid #ccc; border-radius: 8px; }
    .row { margin: 10px 0; display: flex; gap: 10px; align-items: center; }
    .row label { width: 100px; }
    .row input, .row select { flex: 1; padding: 8px; margin-right: 40px;}
    .actions { margin-top: 30px; margin-bottom: 10px; display: flex; gap: 8px; justify-content: center;}
    .btn { padding: 20px 16px; border: 1px solid #2980b9; background: #3498db; color:#fff; border-radius: 6px; cursor: pointer; text-decoration: none; display: inline-block; }
    .btn.secondary { background:#fff; color:#333; border-color:#ccc; text-align: center;}
    .actions .btn {flex: 1;}
  </style>
</head>
<body>
<c:if test="${not empty successMessage}">
  <script>
    alert("<c:out value='${successMessage}'/>");
  </script>
</c:if>
<c:if test="${not empty errorMessage}">
  <script>
    alert("<c:out value='${errorMessage}'/>");
  </script>
</c:if>
  <c:url var="postUrl" value="/user/edit"/>
  <div class="form">
    <h1 style="text-align: center; margin-top: 10px; margin-bottom: 30px;">ユーザー情報更新</h1>
    <form method="post" action="${postUrl}">
      <div class="row">
        <label for="username" style="text-align: right;">ユーザー名</label>
        <input id="username" name="username" type="text" value="<c:out value='${account.username}'/>" readonly style="background-color: #f5f5f5;" />
      </div>
      <div class="row">
        <label for="password" style="text-align: right;">パスワード</label>
        <input id="password" name="password" type="password" placeholder="変更する場合のみ入力" />
      </div>
      <div class="row">
        <label for="email" style="text-align: right;">メール</label>
        <input id="email" name="email" type="email" value="<c:out value='${account.email}'/>" required />
      </div>
      <div class="row">
        <label for="phoneNumber" style="text-align: right;">電話番号</label>
        <input id="phoneNumber" name="phoneNumber" type="tel" value="<c:out value='${account.phoneNumber}'/>" placeholder="090-1234-5678" />
      </div>
      <c:if test="${not empty _csrf}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      </c:if>
      <!-- 숨은 필드로 사용자 ID 전달 -->
      <input type="hidden" name="id" value="<c:out value='${account.id}'/>" />
      <div class="actions">
        <a href="${pageContext.request.contextPath}/" class="btn secondary">キャンセル</a>
        <button type="submit" class="btn">更新</button>
      </div>
    </form>
  </div>
</body>
</html>
