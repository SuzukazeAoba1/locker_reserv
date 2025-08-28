<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>index</title>
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <style>
    :root{
      --page-max-w: 1200px;  /* 페이지 최대 폭 */
      --page-max-h: 900px;   /* 페이지 최대 높이 */
      --pad: 16px;           /* 페이지 안쪽 여백 */
      --gap: 12px;           /* 영역 간 간격 */
      --top-h: 350px;        /* 상단(로그인+공지) 높이 */
      --left-w: 400px;       /* 로그인 패널 너비 */
      --card-border: 1px solid #666;
    }

    /* 전체 페이지 캔버스: 브라우저보다 커지지 않음 */
    .page {
      width: min(100vw, var(--page-max-w));
      height: min(100vh, var(--page-max-h));
      margin: 0 auto;
      padding: var(--pad);
      box-sizing: border-box;
      display: grid;
      grid-template-rows: var(--top-h) 1fr; /* 상단 고정 + 하단 남는 높이 */
      row-gap: var(--gap);
      background: #fafafa;
      border: 1px solid #ccc; /* 확인용 */
    }

    /* 상단 2칼럼 그리드 */
    .top-grid {
      display: grid;
      grid-template-columns: var(--left-w) 1fr; /* 좌 고정, 우 가변 */
      column-gap: var(--gap);
      height: 100%;
    }

    /* 공통 카드 스타일 */
    .card {
      height: 100%;
      background: #fff;
      border: var(--card-border);
      box-sizing: border-box;
      padding: 16px;
      border-radius: 6px;
      overflow: hidden; /* 내부에서 스크롤 제어 */
    }

    /* 로그인(좌) */
    .login-panel { }

    /* 공지(우): 헤더 sticky, 본문 스크롤 */
    .notice-panel {
      display: flex;
      flex-direction: column;
    }
    .notice-header {
      position: sticky;
      top: 0;
      background: #fff;
      padding-bottom: 8px;
      margin-bottom: 8px;
      border-bottom: 1px solid #ddd;
      z-index: 1;
    }
    .notice-body {
      flex: 1;
      overflow-y: auto;
    }

    /* 하단 지도(전폭, 남는 높이 꽉 채움) */
    .map-panel {
      border: var(--card-border);
      background: #fff;
      border-radius: 6px;
      padding: 16px;
      box-sizing: border-box;
      height: 100%;
      overflow: hidden; /* 필요시 내부에서 스크롤 */
    }

    .menu {
    margin: 16px 0;
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    }

    .menu-btn {
      display: inline-block;
      padding: 10px 16px;
      background-color: #3498db;
      color: white;
      border-radius: 6px;
      text-decoration: none;
      font-weight: 600;
      border: 1px solid #2980b9;
      transition: background 0.2s;
    }

    .menu-btn:hover {
      background-color: #2980b9;
    }
  </style>
</head>
<body>
  <main class="page">
    <!-- 상단: 로그인 + 공지 -->
    <section class="top-grid">
      <div class="card login-panel">
      <h3 style="margin-top:0;">로그인</h3>
        <ul>
        <div class="menu">
          <a class="menu-btn" href="/test/lockers">Lockers</a>
          <a class="menu-btn" href="/admin/rentals">admin_rentals</a>
          <a class="menu-btn" href="/admin/accounts">admin_users</a>
        </div>
        </ul>
      </div>

      <div class="card notice-panel">
        <div class="notice-header">
          <h3 style="margin:0;">공지사항</h3>
        </div>
        <div class="notice-body">
          <%@ include file="/WEB-INF/views/index-notices.jspf" %>
          <a class="menu-btn" href="/admin/notices">admin_notices</a>
        </div>
      </div>
    </section>

    <section class="map-panel">
      <h3 style="margin-top:0;">미니 지도 / 버튼</h3>
      <div class="menu">
        <c:url var="url1" value="/admin/lockers"><c:param name="location" value="西改札口券売機前"/></c:url>
        <c:url var="url2" value="/admin/lockers"><c:param name="location" value="西改札口横"/></c:url>
        <c:url var="url3" value="/admin/lockers"><c:param name="location" value="中央改札口横"/></c:url>
        <c:url var="url4" value="/admin/lockers"><c:param name="location" value="東口改札口・5番出入口前"/></c:url>
        <c:url var="url5" value="/admin/lockers"><c:param name="location" value="東口改札口出口付近"/></c:url>
        <c:url var="url6" value="/admin/lockers"><c:param name="location" value="12番出入口前"/></c:url>
        <c:url var="url7" value="/admin/lockers"><c:param name="location" value="お客様サービスセンター横"/></c:url>

        <a class="menu-btn" href="${url1}">admin_lockers (西改札口券売機前)</a>
        <a class="menu-btn" href="${url2}">admin_lockers (西改札口横)</a>
        <a class="menu-btn" href="${url3}">admin_lockers (中央改札口横)</a>
        <a class="menu-btn" href="${url4}">admin_lockers (東口改札口・5番出入口前)</a>
        <a class="menu-btn" href="${url5}">admin_lockers (東口改札口出口付近)</a>
        <a class="menu-btn" href="${url6}">admin_lockers (12番出入口前)</a>
        <a class="menu-btn" href="${url7}">admin_lockers (お客様サービスセンター横)</a>
      </div>
    </section>
  </main>
</body>
</html>
