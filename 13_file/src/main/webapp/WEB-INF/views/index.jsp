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
</head>
<body>

  <div>
   <form method="post" action="${contextPath}/upload.do" enctype="multipart/form-data">
    <div>
      <input type="file" name="files" multiple>
    </div>
    <div>
      <button type="submit">업로드</button>
    </div>
   </form>
  </div>
  
  <hr>
  
  <div>
    <div>
      <input type="file" id="files" multiple>
    </div>
    <div>
      <button type="button" id="btn_upload">업로드</button>
    </div>
  </div>
  <script>
    fnUpload();  // fnUpload(); 호출
    
  	function fnUpload(){
  	  $('#btn_upload').click(function(){
  		// ajax 파일 첨부는 FormData 객체를 생성해서 data로 전달한다.
  		var formData = new FormData();
  		var files = $('#files')[0].files;  // 첨부된파일들 배열이 됨
  		$.each(files, function(i, elem){
    	  formData.append('files', elem);  // files안에 첨부된 파일들을 받음 (name="files" value=elem)과 같다. == 요청 파라미터를 files라는 이름으로 보낸것과 같다.)
  		})
  		// ajax
  		$.ajax({
  		  // 요청
  		  type: 'post',
  		  url : '${contextPath}/ajax/upload.do',
  		  data: formData,     // 위에서 만든 form을 보내는 방법
  		  contentType: false, // contentType : 파라미터로 전달되는 데이터 타입이 아닐때(post) 사용
  		  processData: false, // processData : form 을 보낼때 사용
  		  // 응답
  		  dataType: 'json',
  		  success: function(resData){  // resData === {"success":true} (true면 성공 false면 실패)
  			if(resData.success){
  			  alert('성공');
  			} else {
  			  alert('실패');
  			}
  			  
  		  }
  		})
  	  })
  	}
  </script>

</body>
</html>