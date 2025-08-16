<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lockers List</title>
    <style>
        body { font-family: ui-sans-serif, system-ui, -apple-system; }
        table { border-collapse: collapse; width: 100%; margin-top: 16px; }
        th, td { border: 1px solid #ddd; padding: 8px 10px; text-align: left; }
        th { background: #f5f6f8; }
        .empty { padding: 24px; text-align: center; color: #777; }
    </style>
</head>
<body>
<h2>Lockers</h2>

<c:choose>
    <c:when test="${not empty lockers}">
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>location</th>
                <th>x</th>
                <th>y</th>
                <th>capacity</th>
                <th>price</th>
                <th>status</th>
                <th>Created At</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="a" items="${lockers}">
                <tr>
                    <td>${a.lockerCode}</td>
                    <td>${a.location}</td>
                    <td>${a.x}</td>
                    <td>${a.y}</td>
                    <td>${a.capacity}</td>
                    <td>${a.price}</td>
                    <td>${a.status}</td>
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
