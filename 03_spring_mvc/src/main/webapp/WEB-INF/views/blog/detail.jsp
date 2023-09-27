<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <%-- EL(표현식)을 사용해서 저장된 blogNo를 꺼낸다. --%>
  ${blogNo}
  <br>
  ${requestScope.blogNo}
  <br>
  ${blogDto.blogNo}
  <br>
  ${blogDto.getBlogNo()}
  <br>
  ${dto.blogNo}
</body>
</html>