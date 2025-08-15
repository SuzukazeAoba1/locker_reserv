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
  <c:url var="lockerUrl" value="/admin/lockers">
      <c:param name="name" value="テンジン駅 1階"/>
  </c:url>
  <h3><a href="${lockerUrl}">テンジン駅 1階</a></h3>


</ul>
</body>
</html>
