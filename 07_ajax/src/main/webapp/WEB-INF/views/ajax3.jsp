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
		 url: '${contextPath}/ajax3/list.do',
		 // 응답
		 dataType: 'json',
		 success: function(resData){
		   $('#list').empty();  // 목록 초기화(데이터 누적 방지)
		   $.each(resData, function(i, elem){
	    	  $('#list').append('<div class="row" data-name="'+ elem.name +'">' + elem.name + ', ' + elem.age + '</div>');  // 데이터 속성 값으로 name 구분해서 전달하기
		   })
		 }
	  })
	})
  }
  function fnDetail(){
    $(document).on('click', '.row', function(){ 
      $.ajax({
    	//요청                            //(JSON 데이터를 서버로 보내기)
    	type: 'post',                     // post 방식은 서버로 보낼 데이터를 요청 본문에 저장하는 방식이다.
    	url: '${contextPath}/ajax3/detail.do',
    	contentType: 'application/json',  // 서버로 보내는 요청 데이터의 타입
    	data: JSON.stringify({            // 문자열 형식의 JSON 데이터를 보낸다. 파라미터로 보내는 방식이 아니다.(JSON 데이터는 get방식 사용 불가)
    		'name':$(this).data('name')   // (데이터 속성 값 가져오기)
        }), 
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