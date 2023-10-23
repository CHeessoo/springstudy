<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />
    
<%-- 파라미터 title을 header.jsp로 보낸다. --%>
<%-- jsp:include는 파라미터 전달이 있을때 사용 --%>
<jsp:include page="../layout/header.jsp">
  <jsp:param value="로그인" name="title"/>
</jsp:include>

<style>
  .login_form_wrap {
    width: 300px;
  }
</style>

  <div class="login_form_wrap center_wrap">
    <form method="post" action="${contextPath}/user/login.do">
      <div>
        <label for="email">아이디</label>
        <input type="text" name="email" id="email" placeholder="admin@gmail.com">
      </div>
      <div>
        <label for="pw">비밀번호</label>
        <input type="password" name="pw" id="pw" placeholder="●●●●●●">
      </div>
      <div>
        <input type="hidden" name="referer" value="${referer}">
        <button type="submit">로그인</button>
      </div>
    </form>
    <ul class="ul_menu center_wrap">
      <li><a href="${contextPath}">자동로그인</a>
      <li><a href="${contextPath}">아이디/비밀번호 찾기</a>
    </ul>
  </div>

<%-- include지시어는 파라미터 전달이 없을 때 사용 --%>
<%@ include file="../layout/footer.jsp" %>