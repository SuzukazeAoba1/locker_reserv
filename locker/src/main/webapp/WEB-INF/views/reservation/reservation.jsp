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

<p>Location: ${locker.location}</p>
<p>Status:
    <c:choose>
        <c:when test="${locker.status == 1}">Available</c:when>
        <c:when test="${locker.status == 2}">Reserved</c:when>
        <c:when test="${locker.status == 3}">In Use</c:when>
        <c:otherwise>Unknown</c:otherwise>
    </c:choose>
</p>

<c:if test="${locker.status == 1}">
    <form action="${pageContext.request.contextPath}/reservation/lockers/${locker.code}/reserve" method="post">
        <input type="hidden" name="userId" value="1"/> <!-- 테스트용, 실제 로그인 유저 ID -->
        <input type="hidden" name="location" value="${locker.location}"/>
        <button type="submit">Reserve</button>
    </form>
</c:if>

<c:if test="${locker.status != 1}">
    <p>Reservation not possible for this locker.</p>
</c:if>

<!-- 라커 상세 페이지로 이동하는 버튼 -->
<form action="${pageContext.request.contextPath}/reservation/lockers/${locker.code}/detail" method="get">
    <input type="hidden" name="location" value="${locker.location}"/>
    <button type="submit">View Locker Details</button>
</form>

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
                    <td><fmt:formatDate value="${a.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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