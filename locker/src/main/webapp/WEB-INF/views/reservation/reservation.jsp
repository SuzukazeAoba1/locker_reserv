<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>타이틀</title>
   <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <style>

        hr.divider {
          border: 0;
          height: 1px;
          background: #e5e7eb;
          margin: 8px 20px 16px; /* 위 8px, 좌우 20px, 아래 16px */
        }

      .h-heading { margin: 0 20px 24px; }
      .location-nav { padding: 12px 20px 8px; }

      .location-nav ul{
        display:flex;
        flex-wrap:wrap;
        gap:12px;
        list-style:none;
        margin:0 0 20px 0;
        padding:0;
      }
      .location-nav a{
        display:inline-block;
        padding:6px 10px;
        border:1px solid #ccc;
        border-radius:6px;
        background:#f9f9f9;
        text-decoration:none;
        color:#333;
        font-weight:600;
      }
      .location-nav a:hover{ background:#eee; }


      body{ margin:0; }

      /* 창이 좁아지면 가로 스크롤 (자동 줄바꿈 금지 유지) */
      .locker-wrap{
        max-width:100%;
        overflow-x:auto;
        padding-left: 20px;
      }

      /* 여러 행을 위아래로 쌓아 올림(행 간격 조절) */
      .locker-rows{
        display:flex;
        flex-direction:column;
        gap:48px; /* 행 사이 간격 */
        padding:0 8px 16px;
      }

      /* 각 행은 자동 줄바꿈 금지: 폭이 넘치면 가로 스크롤에 의존 */
      .locker-row{
        display:flex;
        gap:8px;
        align-items:flex-end;
        flex-wrap:nowrap;   /* 자동 줄바꿈 금지 */
        width:max-content;  /* 행 길이만큼만 차지 */
      }

      :root{
        --col-h: 250px;  /* 열 전체 높이 */
        --gap:   8px;    /* .col의 gap과 동일하게 */
      }

      /* 2) 열: 고정 높이 + 세로 간격 */
      .col{
        display:flex;
        flex-direction:column-reverse;
        gap: var(--gap);
        flex: 0 0 64px;       /* 열 너비 */
        height: var(--col-h); /* ★ 열 높이 고정 */
      }

      /* 3) 버튼 공통: 높이는 flex 분배로만 결정 (height 주지 않음) */
      .locker-btn{
        display:flex; align-items:center; justify-content:center;
        width:100%;
        box-sizing:border-box;
        border:1px solid #d0d7de; border-radius:8px;
        background:#fff; color:#111827; font-weight:700; cursor:pointer;
        min-height:0; /* flex 수축 안전장치 */
      }

       .col.scaled-60 { height: calc(var(--col-h) * .6); }

       a.locker-btn {
         text-decoration: none; /* 밑줄 제거 */
       }

      /* 크기별 높이 (capacity) */
      .size-1{ flex: 3 0 0; }
      .size-2{ flex: 5 0 0; }
      .size-3{ flex: 7 0 0; }
      .size-4{ flex: 7 0 0; }

      /* 상태별 색상 (status) */
      .status-1{ background:#4CAF50; color:#fff; } /* 사용 가능 */
      .status-2{ background:#2196F3; color:#fff; } /* 예약됨 */
      .status-3{ background:#FF9800; color:#fff; } /* 사용 중 */
      .status-4{ background:#9E9E9E; color:#fff; } /* 점검 중 */
      .status-5{ cursor:default; pointer-events:none; opacity:.75; } /* 빈 슬롯 */
    </style>
</head>
<body>
<div style="border:1px solid #ccc; padding:10px; margin-bottom:10px;">
    <div class="info">
        <div style="text-align:left; margin-top:1rem;">
        <form action="${pageContext.request.contextPath}/" method="get">
            <a href="${pageContext.request.contextPath}/reservation/lockers?location=${location}">
            <button type="button" class="btn back">一覧に戻る</button>
            </a>
        </form>
        </div>
        <h2>ロッカー詳細情報</h2>
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
    <div class="side" align="left">
      <c:if test="${locker.status == 2 || locker.status == 3}">
        <div class="panel" align="left">
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

<hr class="solid"/>
<!-- 예약 버튼 -->
    <form action="${pageContext.request.contextPath}/reservation/lockers/${locker.lockerCode}/reserve" method="post" style="display:inline;">
        <input type="hidden" name="userId" value="1" /> <!-- 로그인 유저 ID로 교체 -->
        <input type="hidden" name="location" value="${location}" />

        <label for="days">予約期間を選択</label><br>
        <input type='radio' name="days" value="1"/>1日
        <input type='radio' name="days" value="3"/>3日
        <input type='radio' name="days" value="7"/>7日
        <br>
        <button type="submit" class="btn reserve">予約する</button>
    </form>
<!-- 취소 버튼 -->
    <form action="${pageContext.request.contextPath}/reservation/lockers/${locker.lockerCode}/cancel" method="post" style="display:inline;">
        <input type="hidden" name="location" value="${location}" />
        <button type="submit" class="btn cancel">予約取り消し</button>
    </form>
</div>

</body>
</html>