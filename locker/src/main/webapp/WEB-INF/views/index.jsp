<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>게시판</title>
</head>
<body>
<ul>
  <h3><li><a href="/test/accounts">Accounts 목록</a></li></h3>
  <h3><li><a href="/test/notices">Notices 목록</a></li></h3>
  <h3><li><a href="/test/rentals">Rentals 목록</a></li></h3>
  <h3><li><a href="/test/lockers">Lockers 목록</a></li></h3>
    <c:set var="lockerNames" value="${fn:split('西改札口券売機前,西改札口横,中央改札口横,東口改札口・5番出入口前,東口改札口出口付近,12番出入口前,お客様サービスセンター横', ',')}" />
    <c:forEach var="name" items="${lockerNames}">
        <c:url var="lockerUrl" value="/admin/lockers">
            <c:param name="location" value="${name}" />
        </c:url>
        <h3><a href="${lockerUrl}">${name}</a></h3>
    </c:forEach>



</ul>
</body>
</html>
