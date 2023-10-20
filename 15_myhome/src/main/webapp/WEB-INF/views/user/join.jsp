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
  <jsp:param value="회원가입" name="title"/>
</jsp:include>
<%-- 자바 스크립트 user_join.js파일로 분할 --%>
<script src="${contextPath}/resources/js/user_join.js"></script> 
<script>

  /* 함수 호출 */
  $(() => {
	fnCheckEmail();
  })
  
  /* 전역변수 선언 */
  var emailPassed = false;
  
  
  /* 함수 정의 */
  const fnCheckEmail = () => {
	$('#btn_get_code').click(() => {
		
	  let email = $('#email').val();
	  
	  // 연속된 ajax() 함수 호출의 실행 순서를 보장하는 JavaScript 객체 Promise
	  new Promise((resolve, reject) => {
		// 성공했다면 resolve() 함수 호출 -> then() 메소드에 정의된 화살표 함수 호출
		// 실패했다면 reject() 함수 호출 -> catch() 메소드에 정의된 화살표 함수 호출
		
		// 1. 정규식 검사
		let regEmail = /^[A-Za-z0-9-_]+@[A-Za-z0-9]{2,}([.][A-Za-z]{2,6}){1,2}$/;
		emailPassed = regEmail.test(email); // test() 메소드 : 검사가 성공하면 true, 실패하면 false
		if(!emailPassed){
		  reject(1);
		  return;
		}
		
	  }).then(() => {
		
	  }).catch((state) => {
		switch(state){
		case 1: $('#msg_email').text('이메일 형식이 올바르지 않습니다.'); break;
		}
	  })
	})
  }

</script>

  <div>
    <form id="frm_join" method="post" action="${contextPath}/user/join.do">
    
      <h1>회원가입</h1>
      
      <div>
        <div>
          <label for="email">이메일</label>
          <input type="text" name="email" id="email">
          <button type="button" id="btn_get_code">인증코드받기</button>
          <span id="msg_email"></span> <%-- 이메일 테스트 후 표시할 위치 --%>
        </div>
        <div>
          <input type="text" id="code" placeholder="인증코드입력">
          <button type="button" id="btn_verify_code">인증하기</button>
        </div>
      </div>
      
      
      <div>
        <input type="hidden" name="event" value="${event}">
        <button type="submit">회원가입하기</button>
      </div>
      
    </form>
  </div>

<%-- include지시어는 파라미터 전달이 없을 때 사용 --%>
<%@ include file="../layout/footer.jsp" %>