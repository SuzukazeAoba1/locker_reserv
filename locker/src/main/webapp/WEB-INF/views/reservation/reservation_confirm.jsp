<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

<h2>予約確認</h2>
<c:set var="colLetter" value="${fn:substring('ABCDEFGHIJKLMNOPQRSTUVWXYZ', locker.x-1, locker.x)}"/>
          <c:choose>
            <c:when test="${locker.capacity == 1}"><c:set var="capacityLabel" value="小"/></c:when>
            <c:when test="${locker.capacity == 2}"><c:set var="capacityLabel" value="中"/></c:when>
            <c:when test="${locker.capacity == 3}"><c:set var="capacityLabel" value="大"/></c:when>
            <c:otherwise><c:set var="capacityLabel" value="不明"/></c:otherwise>
          </c:choose>

                  <p>番号: ${locker.lockerCode}</p>
                  <p>設置場所: ${locker.location}</p>
                  <p>座標: ${colLetter}列 / ${locker.y}階</p>
                  <p>サイズ: ${capacityLabel}</p>
                  <p>価格: ${locker.price}</p>

    <h3>予約情報</h3>
    <p>開始：<fmt:formatDate value="${activeRental.startTime}" pattern="yyyy/MM/dd HH:mm"/></p>
    <p>終了：<fmt:formatDate value="${activeRental.endTime}" pattern="yyyy/MM/dd HH:mm"/></p>
        <hr class="divider"/>
</body>
</html>