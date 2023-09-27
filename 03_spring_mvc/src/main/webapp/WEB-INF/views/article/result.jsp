<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

  <%-- model에 저장된 title --%>
  ${requestScope.title}
  <br>
  <%-- session에 저장된 title --%>
  ${sessionScope.title}   <%-- ${title}도 가능하지만 ${sessionScope.title}을 사용 권장(어디서 불러왔는지 로직이 보이도록 구분하는게 좋다.) --%>
  <br>
  
  <%-- session에 저장된 정보 초기화 --%>
  <a href="${contextPath}/article/main.do">세션 초기화하고 main 화면으로 가기</a>

  <br>
  
  <%-- 세션 정보 확인하기 --%>
  <a href="${contextPath}/article/confirm.do">세션 확인하기</a>    


</body>
</html>