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
<a href="${pageContext.request.contextPath}/reservation/my_reservations?lockerCode=${reservation.locker.lockerCode}&days=7&userId=${user.id}"></a>
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
<c:set var="colLetter" value="${fn:substring('ABCDEFGHIJKLMNOPQRSTUVWXYZ', locker.x-1, locker.x)}"/>
    <c:forEach var="reservation" items="${reservations}">
    <h3>라커 정보</h3>
       <p>番号: ${locker.lockerCode}</p>
       <p>設置場所: ${locker.location}</p>
       <p>座標: ${colLetter}列 / ${locker.y}階</p>
       <p>サイズ: ${locker.capacityLabel}</p>
       <p>価格: ${locker.price}</p>
    <p>예약일：<fmt:formatDate value="${reservation.startTime}" pattern="yyyy/MM/dd HH:mm"/></p>
    <p>반납일：${reservation.formattedReturnDate}</p> <!-- 계산된 반납일 출력 -->

   <!--
   <p>開始：<fmt:formatDate value="${activeRental.startTime}" pattern="yyyy/MM/dd HH:mm"/></p>
   <p>終了：<fmt:formatDate value="${activeRental.endTime}" pattern="yyyy/MM/dd HH:mm"/></p>
   -->
    </c:forEach>

    <!-- 취소 버튼 -->
    <form action="${pageContext.request.contextPath}/reservation/lockers/${reservation.locker.lockerCode}/cancel" method="post" style="display:inline;">
        <input type="hidden" name="location" value="${reservation.locker.location}" />
        <button type="submit" class="btn cancel">예약 취소</button>
    </form>

</body>
</html>
