<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<style>
  h1 {
    color: ForestGreen;
  }
  h3 {
    color: CadetBlue;
  }
  .bbs {
    width: 300px;
    border: 1px solid LightSeaGreen;
    color: Maroon;
    cursor: pointer;
    background-color: Azure;
  }
  .paging {
    margin : 30px auto;
    color: MidnightBlue;
  }
</style>
</head>
<body>


  <h1>목록보기</h1>
  
  <div>
    <form id="frm_add" method="post" action="${contextPath}/add.do">
      <div><input type="text" name="editor" id="editor" placeholder="작성자"></div>
      <div><input type="text" name="title" id="title" placeholder="제목"></div>
      <div><input type="text" name="contents" id="contents" placeholder="내용"></div>
      <div><button type="submit" class="btn btn-outline-success">등록하기</button></div>
    </form>
  </div>
  
  <h3>전체개수 : ${total}</h3>
  <c:forEach items="${bbsList}" var="bbs">
    <div class="bbs" data-bbs_no="${bbs.bbsNo}">
      <div>글번호: ${bbs.bbsNo}</div>
      <div>글제목: ${bbs.title}</div>
    </div>
  </c:forEach>
  
  <div class="paging">${paging}</div>

  <script>
    /*
  	$('.bbs').click(function(){
  	  // 클릭한 대상 : 이벤트 대상 (this)
  	  let bbsNo = $(this).data('bbs_no');
  	  alert(bbsNo);
  	})
  	*/
  	$('.bbs').click((ev) => {
  	  // 클릭한 대상 : 이벤트 대상 (이벤트객체의 target 속성)
  	  console.log(ev.target.dataset.bbs_no);
  	  let bbsNo = $(ev.target).parent().data('bbs_no');
  	  location.href = '${contextPath}/detail.do?bbsNo=' + bbsNo;
  	})
  	
  	$('#frm_add').submit((ev) => {
  	  let title = $('#title');
  	  if(title.val() === ''){
  		alert('제목은 필수입니다.');
  		title.focus();
  		ev.preventDefault();
  		return;
  	  }
  	})
  	
  	const addResult = '${addResult}';  // '', '1', '0'
  	if(addResult !== ''){
  	  if(addResult === '1'){
  		alert('추가 성공');
  	  } else {
  		alert('추가 실패했대요 푸하하');
  	  }
  	}
  	
  	const removeResult = '${removeResult}';  // '', '1', '0'
  	if(removeResult !== ''){
  	  if(removeResult === '1'){
  		alert('삭제했다 흥!');
  	  } else {
  		alert('삭제 실패');
  	  }
  	}
  	

  	
  </script>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
</body>
</html>