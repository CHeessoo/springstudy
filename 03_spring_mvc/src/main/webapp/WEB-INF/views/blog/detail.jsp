<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <%-- request에 저장된 blogNo를 부르는 방법인 EL(표현식) 사용 --%>
  ${blogNo}
  <br>
  ${requestScope.blogNo}
</body>
</html>