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
  .find_id_pw, .btn {
    margin-top: 5px
  }
  .form-check-input, .form-check-label {
    cursor: pointer;
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
        <button type="submit" class="btn btn-outline-dark">로그인</button>
        <div class="form-check form-switch">
        <input type="checkbox" id="autoLogin" name="autoLogin" class="form-check-input" role="switch" >
        <label for="autoLogin" class="form-check-label">자동로그인</label>
        </div>
      </div>
    </form>
      <div class="find_id_pw">
        <a href="${contextPath}" class="btn btn-light">아이디/비밀번호 찾기</a>
      </div>
    <hr>
    <div>
      <a href="${naverLoginURL}"><img src="${contextPath}/resources/image/btnG_완성형.png" width="200px"></a>
    </div>
  </div>

<%-- include지시어는 파라미터 전달이 없을 때 사용 --%>
<%@ include file="../layout/footer.jsp" %>