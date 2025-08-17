<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Accounts List</title>
    <style>
        body { font-family: ui-sans-serif, system-ui, -apple-system; }
        table { border-collapse: collapse; width: 100%; margin-top: 16px; }
        th, td { border: 1px solid #ddd; padding: 8px 10px; text-align: left; }
        th { background: #f5f6f8; }
        .empty { padding: 24px; text-align: center; color: #777; }
    </style>
</head>
<body>
<div style="text-align:left; margin-top:1rem;">
  <form action="${pageContext.request.contextPath}/" method="get">
    <button type="submit"
            style="width:200px; height:50px; margin:10px; font-size:20px;">
      ルートへ戻る
    </button>
  </form>
</div>

<h2>Accounts</h2>

<c:choose>
    <c:when test="${not empty accounts}">
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Role</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Active</th>
                <th>Created At</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="a" items="${accounts}">
                <tr>
                    <td>${a.id}</td>
                    <td>${a.username}</td>
                    <td>${a.role}</td>
                    <td>${a.email}</td>
                    <td>${a.phoneNumber}</td>
                    <td>${a.isActive}</td>
                    <td>
                        <fmt:formatDate value="${a.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <div class="empty">데이터가 없습니다.</div>
    </c:otherwise>
</c:choose>

</body>
</html>
