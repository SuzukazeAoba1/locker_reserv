<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>예약목록</title>
</head>
<body>
<c:set var="userId" value="${sessionScope.loginUser.id}" />
<a href="${pageContext.request.contextPath}/reservation/my_reservations?lockerCode=${reservation.locker.lockerCode}&days=7&userId=${sessionScope.loginUser.id}"></a>
<div style="text-align:left; margin-top:1rem;">
  <form action="${pageContext.request.contextPath}/" method="get">
    <button type="submit"
            style="width:200px; height:50px; margin:10px; font-size:20px;">
      ルートへ戻る
    </button>
  </form>
</div>
<c:url var="mylocker" value="/reservation/my_reservations"/>
    <c:if test="${empty reservations}">
    <p>현재 예약한 라커가 없습니다.</p>
    </c:if>

<p>${userId}님의 라커 이용 정보 (${days}일 기준)</p>

<c:forEach var="res" items="${reservations}">
    <c:set var="rental" value="${res.rental}" />
    <c:set var="locker" value="${res.locker}" />

    <div style="border:1px solid #ccc; padding:10px; margin-bottom:10px;">
        <p>번호: ${rental.lockerCode}</p>
        <p>설치 위치: ${locker.location}</p>

        <c:set var="colLetter" value="${fn:substring('ABCDEFGHIJKLMNOPQRSTUVWXYZ', locker.x-1, locker.x)}"/>
        <c:choose>
            <c:when test="${locker.capacity == 1}"><c:set var="capacityLabel" value="小"/></c:when>
            <c:when test="${locker.capacity == 2}"><c:set var="capacityLabel" value="中"/></c:when>
            <c:when test="${locker.capacity == 3}"><c:set var="capacityLabel" value="大"/></c:when>
            <c:otherwise><c:set var="capacityLabel" value="不明"/></c:otherwise>
        </c:choose>

        <p>좌표: ${colLetter}열 / ${locker.y}층</p>
        <p>크기: ${capacityLabel}</p>
        <p>가격: ${locker.price}</p>
        <p>대여일: <fmt:formatDate value="${rental.createdAt}" pattern="yyyy/MM/dd HH:mm"/></p>
        <p>반납일: ${res.formattedReturnDate}</p>
        <p>상태: ${rental.status}</p>
    </div>
</c:forEach>



    <!-- 취소 버튼 -->
    <form action="${pageContext.request.contextPath}/reservation/lockers/${locker.lockerCode}/cancel" method="post" style="display:inline;">
            <input type="hidden" name="location" value="${reservation.locker.location}" />
            <button type="submit" class="btn cancel">사용 종료</button>
    </form>

</body>
</html>
