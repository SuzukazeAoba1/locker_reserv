<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Locker View</title>
<style>
    .locker-grid {
        display: grid;
        grid-template-columns: repeat(11, 1fr); /* x=1~11 */
        gap: 8px;
        max-width: 1200px;
        margin: 20px auto;
    }
    .locker-btn {
        width: 100%;
        border: none;
        color: #fff;
        font-weight: bold;
        cursor: pointer;
    }
    /* 크기별 높이 */
    .size-3 { height: 100px; } /* 대 */
    .size-2 { height: 60px; }  /* 중 */
    .size-1 { height: 40px; }  /* 소 */

    /* 상태별 색상 */
    .status-1 { background-color: #4CAF50; } /* 사용 가능 */
    .status-2 { background-color: #2196F3; } /* 예약됨 */
    .status-3 { background-color: #FF9800; } /* 사용 중 */
    .status-4 { background-color: #9E9E9E; } /* 점검 중 */

    .locker-grid { display:flex; gap:8px; align-items:flex-end; }
    .col { display:flex; flex-direction:column-reverse; gap:8px; flex: 0 0 64px; }
    .locker-btn { display:block; width:100%; }


</style>
</head>
<body>

<h2>텐진역 1층 코인로커</h2>


<div class="locker-grid">
  <c:set var="prevX" value="-1"/>
  <c:forEach var="l" items="${lockers}" varStatus="st">
    <!-- x가 바뀌면 새 컬럼 시작 -->
    <c:if test="${l.x ne prevX}">
      <c:if test="${st.index gt 0}"></div></c:if>
      <div class="col">
      <c:set var="prevX" value="${l.x}"/>
    </c:if>

    <button class="locker-btn size-${l.capacity} status-${l.status}" data-id="${l.id}">
      ${l.x}-${l.y}
    </button>

    <!-- 마지막 아이템이면 마지막 컬럼 닫기 -->
    <c:if test="${st.last}"></div></c:if>
  </c:forEach>
</div>

</body>
</html>
