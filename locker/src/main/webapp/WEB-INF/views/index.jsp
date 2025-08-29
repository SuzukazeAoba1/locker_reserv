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
      /* 원하는 고정 크기로 맞추세요 */
      --page-max-w: 1200px;  /* 전체 페이지 가로 고정 */
      --page-max-h: 900px;   /* 전체 페이지 세로 고정 */
      --pad: 16px;           /* 페이지 내부 패딩 */
      --gap: 12px;           /* 패널 간 간격 */
      --top-h: 350px;        /* 상단(로그인+공지) 영역 높이 고정 */
      --left-w: 400px;       /* 로그인 패널 너비 고정 */
      --card-border: 1px solid #666;

      /* 고정 계산 값들 */
      --right-w: calc(var(--page-max-w) - (var(--pad)*2) - var(--gap) - var(--left-w));
      --bottom-h: calc(var(--page-max-h) - (var(--pad)*2) - var(--gap) - var(--top-h));
    }

    /* (선택) 뷰포트가 더 작을 때 페이지 전체가 스크롤되도록 */
    html, body { height:100%; }
    body { overflow:auto; }

    .page {
      /* 뷰포트에 맞추지 않고 고정값으로 */
      width: var(--page-max-w);
      height: var(--page-max-h);
      margin: 0 auto;
      padding: var(--pad);
      box-sizing: border-box;

      display: grid;
      /* 상단/하단 모두 고정 높이 */
      grid-template-rows: var(--top-h) var(--bottom-h);
      row-gap: var(--gap);

      background: #fafafa;
      border: 1px solid #ccc;
    }

    /* 상단 2칼럼도 좌/우 모두 고정 폭 */
    .top-grid {
      display: grid;
      grid-template-columns: var(--left-w) var(--right-w);
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
      overflow: hidden; /* 내부 스크롤 제어 */
    }

    /* 로그인(좌) */
    .login-panel { }

    /* 공지(우): 헤더 sticky, 본문 스크롤 */
    .notice-panel {
      display: flex;
      flex-direction: column;
    }
    /* 공지 패널 내부 스크롤 구조 유지 */
    .notice-panel { display:flex; flex-direction:column; }
    .notice-header {
      position: sticky;
      top: 0;
      background: #fff;
      padding-bottom: 8px;
      margin-bottom: 8px;
      border-bottom: 1px solid #ddd;
      z-index: 1;
    }
    .notice-body { flex:1; overflow-y:auto; }

    /* 하단 지도: 고정 높이 이미 확보됨 */
    .map-panel {
      border: var(--card-border);
      background: #fff;
      border-radius: 6px;
      padding: 16px;
      box-sizing: border-box;
      height: 100%;
      overflow: hidden;
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
      <h3 style="margin:0 0 8px; text-align:center;">로그인</h3>
      <%@ include file="/WEB-INF/views/index-login.jspf" %>
        <ul>
        <!--
        <div class="menu">
          <a class="menu-btn" href="/test/lockers">Lockers</a>
          <a class="menu-btn" href="/admin/rentals">admin_rentals</a>
          <a class="menu-btn" href="/admin/accounts">admin_users</a>
        </div>
        -->
        </ul>
      </div>

      <div class="card notice-panel">
        <div class="notice-header">
          <h3 style="margin:0; text-align:center;" >공지사항</h3>
        </div>
        <div class="notice-body">
          <%@ include file="/WEB-INF/views/index-notices.jspf" %>
          <!--<a class="menu-btn" href="/admin/notices">admin_notices</a>-->
        </div>
      </div>
    </section>

    <section class="map-panel">
      <h3 style="margin-top:0; text-align:center;">미니 지도 / 버튼</h3>
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

        <a class="menu-btn" href="/reservation/lockers?location=西改札口券売機前">天神駅 lockers1</a>
        <a class="menu-btn" href="/reservation/lockers?location=西改札口横">天神駅 lockers2</a>
        <a class="menu-btn" href="/reservation/lockers?location=中央改札口横">天神駅 lockers3</a>
        <a class="menu-btn" href="/reservation/lockers?location=東口改札口・5番出入口前">天神駅 lockers4</a>
        <a class="menu-btn" href="/reservation/lockers?location=東口改札口出口付近">天神駅 lockers5</a>
        <a class="menu-btn" href="/reservation/lockers?location=12番出入口前">天神駅 lockers6</a>
        <a class="menu-btn" href="/reservation/lockers?location=お客様サービスセンター横">天神駅 lockers7</a>
      </div>
    </section>
  </main>
</body>
</html>
