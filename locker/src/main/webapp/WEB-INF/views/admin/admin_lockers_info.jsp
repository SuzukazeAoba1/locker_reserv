<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>ロッカー情報</title>
  <style>
  /* 페이지 폭을 고정해서 양쪽으로 안 퍼지게 */
  .page{ width:1064px; margin:0 auto; padding:0 20px; box-sizing:border-box; }

  /* ← 핵심: 두 블럭을 flex로, 각각 고정폭 */
  .two-col{ display:flex; gap:24px; align-items:flex-start; }
  .main   { flex:0 0 400px; }   /* 왼쪽 박스 폭 (원하는 값으로 조절) */
  .side   { flex:0 0 320px; margin-top: 20px;}   /* 오른쪽 패널 폭 */

  /* 아래는 표시용 스타일 */
  .panel{ border:1px solid #e5e7eb; border-radius:8px; padding:12px; background:#fafafa; }
  .panel h3{ margin:0 0 8px; font-size:18px; }
  .kv p{ margin:4px 0; }
  .section{ margin-top:24px; padding-top:16px; border-top:1px solid #e5e7eb; }

  .badge{display:inline-block;padding:.2rem .5rem;border:1px solid #ddd;border-radius:.4rem}
  .ok{background:#ecfdf5}.res{background:#eef2ff}.use{background:#fef3c7}.mtc{background:#fee2e2}.empty{background:#f3f4f6}
  .btn-big{ width:160px; height:50px; font-size:16px; padding:0; text-align:center; display:inline-block; }
  .actions-row{display:flex; gap:12px; align-items:flex-start; flex-wrap:wrap}
  .reserve-inline{display:flex; gap:8px; align-items:center}
  .msg-wrap{min-height:2em; padding-top:6px}
  .input-narrow{width:160px}
  .divider{ border:0; height:1px; background:#e5e7eb; margin:16px 0; }
  </style>
</head>
<body>

<div class="page">

  <!-- ■ 상단: 딱 두 블럭(좌/우) -->
  <div class="two-col">
    <!-- 왼쪽: ロッカー詳細 -->
    <div class="main">
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
    </div>

    <!-- 오른쪽: 予約／利用情報 (예약중/사용중만 표시) -->
    <div class="side">
      <c:if test="${locker.status == 2 || locker.status == 3}">
        <div class="panel">
          <h3>予約／利用情報</h3>
          <c:choose>
            <c:when test="${not empty activeRental}">
              <div class="kv">
                <p>ユーザーID： ${activeRental.userId}</p>
                <p>開始：<fmt:formatDate value="${activeRental.startTime}" pattern="yyyy/MM/dd HH:mm"/></p>
                <p>終了：<fmt:formatDate value="${activeRental.endTime}" pattern="yyyy/MM/dd HH:mm"/></p>
                <p>状態：
                  <c:choose>
                    <c:when test="${activeRental.status == 1}">予約中</c:when>
                    <c:when test="${activeRental.status == 2}">使用中</c:when>
                    <c:otherwise>不明</c:otherwise>
                  </c:choose>
                </p>
                <hr class="divider"/>
                <p>作成：<fmt:formatDate value="${activeRental.createdAt}" pattern="yyyy/MM/dd HH:mm"/></p>
                <p>更新：<fmt:formatDate value="${activeRental.updatedAt}" pattern="yyyy/MM/dd HH:mm"/></p>
              </div>
            </c:when>
            <c:otherwise>
              <div style="color:#666">データが見つかりません。</div>
            </c:otherwise>
          </c:choose>
        </div>
      </c:if>
    </div>
  </div><!-- /two-col -->

  <!-- ■ 하단: 버튼/메시지/뒤로가기 (풀폭) -->
  <div class="section">
    <c:url var="backUrl" value="/admin/lockers">
      <c:param name="location" value="${backLocation}" />
    </c:url>

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
            <input type="number" name="userId" placeholder="ユーザーID" required class="input-narrow" min="1"/>
          </form>
        </div>
      </c:when>

      <c:when test="${locker.status == 2}">
        <div class="actions-row">
          <form method="post" action="${pageContext.request.contextPath}/admin/lockers/${locker.lockerCode}/start">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="location" value="${backLocation}"/>
            <button type="submit" class="btn-big">使用開始</button>
          </form>
          <form method="post" action="${pageContext.request.contextPath}/admin/lockers/${locker.lockerCode}/cancel">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="location" value="${backLocation}"/>
            <button type="submit" class="btn-big">予約をキャンセル</button>
          </form>
        </div>
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

    <div class="msg-wrap">
      <div style="color:green">${msg}</div>
      <div style="color:red">${error}</div>
    </div>

    <hr class="divider"/>
    <a href="${backUrl}">一覧に戻る</a>
  </div><!-- /section -->

</div><!-- /page -->

</body>
</html>
