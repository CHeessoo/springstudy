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

  // 호출
  $(function(){
	fnList();
	fnDetail();
  })
  
  // 정의
  function fnList(){
	$('#btn_list').click(function(){
	  $.ajax({
		 // 요청
		 type: 'get',
		 url: '${contextPath}/ajax1/list.do',
		 // 응답
		 dataType: 'json',
		 success: function(resData){
		   $('#list').empty();  // 목록 초기화(데이터 누적 방지)
		   $.each(resData, function(i, elem){
	    	  $('#list').append('<div class="row"><span>' + elem.name + '</span>, ' + elem.age + '</div>');  // 하위 태그로 name 구분하기
		   })
		 }
	  })
	})
  }
  function fnDetail(){
    $(document).on('click', '.row', function(){ 
      $.ajax({
    	//요청
    	type: 'get',
    	url: '${contextPath}/ajax1/detail.do',
    	data: 'name=' + $(this).find('span').text(),  // this : 클릭한 객체 / 클릭한 이름의 내부 텍스트 값만 name 파라미터로 보내기
    	//응답
    	dataType: 'json',
    	success: function(resData){
		  alert(resData.name + ', ' + resData.age);
    	}
      })
    })
  }

</script>
</head>
<body>

  <div>
    <button id="btn_list">목록보기</button>
  </div>
  
  <hr>
  
  <div id="list"></div>

</body>
</html>