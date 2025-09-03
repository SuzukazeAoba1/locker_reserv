<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>Locker View</title>
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

  .locker-btn.status-1:hover { background:#43A047; color:#fff; } /* 사용 가능 */
  .locker-btn.status-2:hover { background:#1E88E5; color:#fff; } /* 예약됨 */
  .locker-btn.status-3:hover { background:#FB8C00; color:#fff; } /* 사용 중 */
  .locker-btn.status-4:hover { background:#8E8E8E; color:#fff; } /* 점검 중 */
</style>
</head>
<body>

<div style="text-align:left;">
  <form action="${pageContext.request.contextPath}/" method="get">
    <button type="submit"
            style="width:200px; height:50px; margin:20px; font-size:20px;">
      ルートへ戻る
    </button>
  </form>
</div>

<hr class="divider"/>

<c:url var="lockerDetailBase" value="/admin/lockers"/>

<!-- 위치 선택 링크 -->
<nav class="location-nav">
  <c:url var="url1" value="/admin/lockers"><c:param name="location" value="西改札口券売機前"/></c:url>
  <c:url var="url2" value="/admin/lockers"><c:param name="location" value="西改札口横"/></c:url>
  <c:url var="url3" value="/admin/lockers"><c:param name="location" value="中央改札口横"/></c:url>
  <c:url var="url4" value="/admin/lockers"><c:param name="location" value="東口改札口・5番出入口前"/></c:url>
  <c:url var="url5" value="/admin/lockers"><c:param name="location" value="東口改札口出口付近"/></c:url>
  <c:url var="url6" value="/admin/lockers"><c:param name="location" value="12番出入口前"/></c:url>
  <c:url var="url7" value="/admin/lockers"><c:param name="location" value="お客様サービスセンター横"/></c:url>

  <ul>
    <li><a href="${url1}">西改札口券売機前</a></li>
    <li><a href="${url2}">西改札口横</a></li>
    <li><a href="${url3}">中央改札口横</a></li>
    <li><a href="${url4}">東口改札口・5番出入口前</a></li>
    <li><a href="${url5}">東口改札口出口付近</a></li>
    <li><a href="${url6}">12番出入口前</a></li>
    <li><a href="${url7}">お客様サービスセンター横</a></li>
  </ul>
</nav>

<hr class="divider"/>

<c:forEach var="l" items="${lockers}" begin="0" end="0">
  <h2 class="h-heading">${l.location} コインロッカー</h2>
</c:forEach>

<div class="locker-wrap">
  <div class="locker-rows">
    <c:set var="prevX" value="${-1}" />

    <c:forEach var="l" items="${lockers}" varStatus="st">

      <!-- X 값이 바뀌면 새로운 열 시작 -->
      <c:if test="${l.x ne prevX}">
        <!-- 첫 아이템이면 첫 행을 연다 -->
        <c:if test="${st.index eq 0}">
          <div class="locker-row">
        </c:if>

        <!-- 직전 열이 있으면 닫기 -->
        <c:if test="${st.index gt 0}"></div></c:if> <!-- </div> = 닫는 .col -->

        <!-- 중간 X가 비었으면 행을 닫고 새 행을 연다 -->
        <c:if test="${prevX ne -1 and l.x gt (prevX + 1)}">
          </div> <!-- 닫는 .locker-row -->
          <div class="locker-row"> <!-- 새 행 시작 -->
        </c:if>

        <!-- 새 열 시작 -->
        <div class="col">
        <c:set var="prevX" value="${l.x}" />
      </c:if>

      <!-- 버튼(칸) -->
      <a class="locker-btn size-${l.capacity} status-${l.status}"
         href="${lockerDetailBase}/${l.lockerCode}?location=${fn:escapeXml(l.location)}">
        ${l.lockerCode}
      </a>

      <!-- 마지막 아이템이면 마지막 열과 행 닫기 -->
      <c:if test="${st.last}">
        </div>  <!-- 닫는 .col -->
        </div>  <!-- 닫는 .locker-row -->
      </c:if>

    </c:forEach>
  </div>
</div>
<hr class="divider"/>
<script>
(function(){
  document.querySelectorAll('.col').forEach(col => {
    // 빈칸(status-5)은 제외
    const items = Array.from(col.querySelectorAll('.locker-btn'))
      .filter(b => !b.classList.contains('status-5'));

    let s1=0, s2=0, s3=0, others=0;
    items.forEach(b=>{
      const m = b.className.match(/size-(\d+)/);
      const sz = m ? parseInt(m[1],10) : 0;
      if (sz===1) s1++;
      else if (sz===2) s2++;
      else if (sz===3) s3++;
      else others++;
    });

    // 정확히 아래 셋 중 하나일 때만 60% 적용
    const onlySmall3  = (s1===3 && s2===0 && s3===0 && items.length===3);
    const onlyMedium2 = (s1===0 && s2===2 && s3===0 && items.length===2);
    const onlyLarge1  = (s1===0 && s2===0 && s3===1 && items.length===1);

    if ((onlySmall3 || onlyMedium2 || onlyLarge1) && others===0) {
      col.classList.add('scaled-60');
    } else {
      col.classList.remove('scaled-60');
    }
  });
})();
</script>
</body>
</html>
