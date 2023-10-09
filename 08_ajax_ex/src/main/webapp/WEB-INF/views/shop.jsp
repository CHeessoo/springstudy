<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script>
  $(function(){
	fnSearch();  
  })
  function fnSearch(){
	$('#btn_search').click(function(){
	  $.ajax({
		type: 'get',
		url: '',
		data: ,
		dataType: 'json',
		success: function(resData){
			
		}
	  })
	})
  }

</script>
</head>
<body>

  <div>
    <label for="list_no">검색결과건수</label>
    <select id="list_no">
      <c:forEach var="n" begin="1" end="10" step="1">
        <option>${n}</option>
      </c:forEach>
    </select>
  </div>
  <div>
    <input type="radio" id="similar" name="show" checked >
    <label for="similar">유사도순</label>
    <input type="radio" id="date" name="show" >
    <label for="date">날짜순</label>
    <input type="radio" id="lPrice" name="show" >
    <label for="lPrice">낮은가격순</label>
    <input type="radio" id="hPrice" name="show" >
    <label for="hPrice">높은가격순</label>
  </div>
  <div>
    <label for="search">검색어 입력</label>
    <input type="text" id="search" name="search">
    <button type="button" id="btn_search">검색</button>
  </div>
  
  <hr>
  
  <div>
    <table border="1">
      <thead>
        <tr>
          <td id="title">상품명</td>
          <td id="image">썸네일</td>
          <td>최저가</td>
          <td>판매처</td>
        </tr>
      </thead>
      <tbody>

      </tbody>
    </table>
  </div>  

</body>
</html>