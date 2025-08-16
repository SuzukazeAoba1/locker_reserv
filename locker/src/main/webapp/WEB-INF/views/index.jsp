<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<style>
.menu {
  margin: 16px 0;
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.menu-btn {
  display: inline-block;
  padding: 10px 16px;
  background-color: #3498db;
  color: white;
  border-radius: 6px;
  text-decoration: none;
  font-weight: 600;
  border: 1px solid #2980b9;
  transition: background 0.2s;
}

.menu-btn:hover {
  background-color: #2980b9;
}
</style>
    <title>게시판</title>
</head>
<body>
<ul>
<div class="menu">
  <a class="menu-btn" href="/test/lockers">Lockers</a>
  <a class="menu-btn" href="/admin/notices">admin_notices</a>
  <a class="menu-btn" href="/admin/rentals">admin_rentals</a>
  <a class="menu-btn" href="/admin/accounts">admin_users</a>
  <a class="menu-btn" href="/admin/lockers?location=西改札口券売機前">admin_lockers</a>
</div>
</ul>
</body>
</html>
