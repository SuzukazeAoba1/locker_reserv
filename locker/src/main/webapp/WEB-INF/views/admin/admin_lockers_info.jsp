<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Locker Info</title>
</head>
<body>
    <h2>Locker 상세 정보</h2>

    <p>위치: ${locker.location}</p>
    <p>코드: ${locker.lockerCode}</p>
    <p>용량: ${locker.capacity}</p>
    <p>가격: ${locker.price}</p>
    <p>상태: ${locker.status}</p>

    <hr>
    <c:url var="backUrl" value="/admin/lockers">
      <c:param name="location" value="${backLocation}" />
    </c:url>

    <a href="${backUrl}">목록으로</a>
</body>
</html>