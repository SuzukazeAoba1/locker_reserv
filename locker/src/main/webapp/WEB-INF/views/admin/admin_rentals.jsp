<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Rental List</title>
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

<h2>rentals</h2>

<c:choose>
    <c:when test="${not empty rentals}">
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>userId</th>
                <th>lockerId</th>
                <th>status</th>
                <th>startTime</th>
                <th>endTime</th>
                <th>createdAt</th>
                <th>updatedAt</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="a" items="${rentals}">
                  <tr>
                    <td>${a.id}</td>
                    <td>${a.userId}</td>
                    <td>${a.lockerCode}</td>
                    <td>${a.status}</td>
                    <td><fmt:formatDate value="${a.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td><fmt:formatDate value="${a.endTime}"   pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td><fmt:formatDate value="${a.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td><fmt:formatDate value="${a.updatedAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
