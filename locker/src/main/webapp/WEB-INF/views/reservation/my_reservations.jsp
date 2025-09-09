<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>予約リスト</title>
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
    <p>現在、予約されているロッカーはありません。</p>
    </c:if>

<p>${userId}様のロッカー利用情報</p>

<c:forEach var="res" items="${reservations}">
    <c:set var="rental" value="${res.rental}" />
    <c:set var="locker" value="${res.locker}" />

    <div style="border:1px solid #ccc; box-sizing: border-box; width: 500px; height: 400px; padding:10px; margin-bottom:10px;">
        <p>番号: ${rental.lockerCode}</p>
        <p>設置場所: ${locker.location}</p>

        <c:set var="colLetter" value="${fn:substring('ABCDEFGHIJKLMNOPQRSTUVWXYZ', locker.x-1, locker.x)}"/>
        <c:choose>
            <c:when test="${locker.capacity == 1}"><c:set var="capacityLabel" value="小"/></c:when>
            <c:when test="${locker.capacity == 2}"><c:set var="capacityLabel" value="中"/></c:when>
            <c:when test="${locker.capacity == 3}"><c:set var="capacityLabel" value="大"/></c:when>
            <c:otherwise><c:set var="capacityLabel" value="不明"/></c:otherwise>
        </c:choose>

        <p>座標: ${colLetter}列 / ${locker.y}階</p>
        <p>サイズ: ${capacityLabel}</p>
        <p>価格: ${locker.price}</p>
        <p>開始: <fmt:formatDate value="${rental.startTime}" pattern="yyyy/MM/dd HH:mm"/></p>
        <p>終了: <fmt:formatDate value="${rental.endTime}" pattern="yyyy/MM/dd HH:mm"/></p>
        <p>状態：
            <c:choose>
                <c:when test="${rental.status == 1}">予約中</c:when>
                <c:when test="${rental.status == 2}">使用中</c:when>
                <c:when test="${rental.status == 3}">使用終了</c:when>
                <c:when test="${rental.status == 4}">使用終了</c:when>
                <c:otherwise>不明</c:otherwise>
            </c:choose>
        </p>

        <c:if test ="${rental.status ==1}">
            <form action="${pageContext.request.contextPath}/reservation/lockers/${locker.lockerCode}/cancel" method="post" style="display:inline;">
                <input type="hidden" name="location" value="${location}" />
                <button type="submit" class="btn cancel">利用終了</button>
        </c:if>
    </div>
</c:forEach>

</body>
</html>
