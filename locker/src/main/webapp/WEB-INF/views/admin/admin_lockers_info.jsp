<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
  <title>ロッカー情報</title>
<style>
body {padding-left: 10px;}
.badge{display:inline-block;padding:.2rem .5rem;border:1px solid #ddd;border-radius:.4rem}
.ok{background:#ecfdf5}
.res{background:#eef2ff}
.use{background:#fef3c7}
.mtc{background:#fee2e2}
.empty{background:#f3f4f6}
  .btn-big {
    width: 160px;
    height: 50px;
    font-size: 16px;
    padding: 0;
    text-align: center;
    display: inline-block;
  }
  .actions-row{display:flex;gap:12px;align-items:flex-start;flex-wrap:wrap}
  .reserve-inline{display:flex;gap:8px;align-items:center}
  .msg-wrap{min-height:2em;padding-top:6px}
  .input-narrow{width:160px}
</style>
</head>
<body>

<h2>ロッカー詳細</h2>

<c:set var="colLetter" value="${fn:substring('ABCDEFGHIJKLMNOPQRSTUVWXYZ', locker.x-1, locker.x)}"/>

<c:choose>
  <c:when test="${locker.capacity == 1}"><c:set var="capacityLabel" value="小"/></c:when>
  <c:when test="${locker.capacity == 2}"><c:set var="capacityLabel" value="中"/></c:when>
  <c:when test="${locker.capacity == 3}"><c:set var="capacityLabel" value="大"/></c:when>
  <c:otherwise><c:set var="capacityLabel" value="不明"/></c:otherwise>
</c:choose>

<c:choose>
  <c:when test="${locker.status == 1}">
    <c:set var="statusLabel" value="利用可能"/><c:set var="statusClass" value="badge ok"/>
  </c:when>
  <c:when test="${locker.status == 2}">
    <c:set var="statusLabel" value="予約中"/><c:set var="statusClass" value="badge res"/>
  </c:when>
  <c:when test="${locker.status == 3}">
    <c:set var="statusLabel" value="使用中"/><c:set var="statusClass" value="badge use"/>
  </c:when>
  <c:when test="${locker.status == 4}">
    <c:set var="statusLabel" value="点検中"/><c:set var="statusClass" value="badge mtc"/>
  </c:when>
  <c:when test="${locker.status == 5}">
    <c:set var="statusLabel" value="空き枠"/><c:set var="statusClass" value="badge empty"/>
  </c:when>
  <c:otherwise>
    <c:set var="statusLabel" value="不明"/><c:set var="statusClass" value="badge"/>
  </c:otherwise>
</c:choose>

<p>番号: ${locker.lockerCode}</p>
<p>設置場所: ${locker.location}</p>
<p>座標: ${colLetter}列 / ${locker.y}階</p>
<p>サイズ: ${capacityLabel}</p>
<p>価格: ${locker.price}</p>
<p>状態: <span class="${statusClass}">${statusLabel}</span></p>

<c:url var="backUrl" value="/admin/lockers">
  <c:param name="location" value="${backLocation}" />
</c:url>

<hr/>

<c:choose>
  <c:when test="${locker.status == 1}">
    <div class="actions-row">
      <form method="post" action="${pageContext.request.contextPath}/admin/lockers/${locker.lockerCode}/toggle">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="location" value="${backLocation}"/>
        <button type="submit" class="btn-big">点検中に切替</button>
      </form>

      <form method="post" action="${pageContext.request.contextPath}/admin/lockers/${locker.lockerCode}/reserve" class="reserve-inline">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="location" value="${backLocation}"/>
        <button type="submit" class="btn-big">このロッカーを予約</button>
        <input type="number" name="userId" placeholder="ユーザーID" required class="input-narrow"/>
      </form>
    </div>
  </c:when>

  <c:when test="${locker.status == 2}">
    <form method="post" action="${pageContext.request.contextPath}/admin/lockers/${locker.lockerCode}/cancel">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <input type="hidden" name="location" value="${backLocation}"/>
      <button type="submit" class="btn-big">予約をキャンセル</button>
    </form>
  </c:when>

  <c:when test="${locker.status == 3}">
    <form method="post" action="${pageContext.request.contextPath}/admin/lockers/${locker.lockerCode}/cancel">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <input type="hidden" name="location" value="${backLocation}"/>
      <button type="submit" class="btn-big">使用を終了</button>
    </form>
  </c:when>

  <c:when test="${locker.status == 4}">
    <form method="post" action="${pageContext.request.contextPath}/admin/lockers/${locker.lockerCode}/toggle">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <input type="hidden" name="location" value="${backLocation}"/>
      <button type="submit" class="btn-big">利用可能に切替</button>
    </form>
  </c:when>

  <c:otherwise>
    <div style="color:#666">現在の状態では予約/切替はできません。</div>
  </c:otherwise>
</c:choose>

<div style="min-height:2em; padding-top:6px;">
  <div style="color:green">${msg}</div>
  <div style="color:red">${error}</div>
</div>

<hr/>

<a href="${backUrl}">一覧に戻る</a>
</body>
</html>
