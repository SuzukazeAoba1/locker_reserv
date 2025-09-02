<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>index</title>
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <c:url value="/resources/css/index.css" var="indexCss"/>
  <link rel="stylesheet" href="${indexCss}" />
</head>
<body>
  <main class="page">
    <!-- 상단: 로그인 + 공지 -->
    <section class="top-grid">
      <div class="card login-panel">
      <h3 style="margin:0 0 8px; text-align:center;">로그인</h3>
      <%@ include file="/WEB-INF/views/index-login.jspf" %>
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
      <%@ include file="/WEB-INF/views/index-lockermap.jspf" %>
    </section>
  </main>
</body>
</html>
