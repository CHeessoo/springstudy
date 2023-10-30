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
    color: DarkCyan;
  }
  div {
    color: DarkMagenta;
  }
</style>
</head>
<body>


  <h1>상세보기</h1>
  
  <form id="frm_detail" method="post">
    <div>게시글 번호: ${bbs.bbsNo}</div>
    <div>작성자: ${bbs.editor}</div>
    <div>제목: <input type="text" name="title" value="${bbs.title}" required></div>
    <div>내용: <input type="text" name="contents" value="${bbs.contents}"></div>
    <div>작성일: ${bbs.createdAt}</div>
    <div>수정일: ${bbs.modifiedAt == null ? '없음' : bbs.modifiedAt}</div>
    <input type="hidden" name="bbsNo" value="${bbs.bbsNo}">
    <button type="button" id="btn_modify" class="btn btn-secondary">수정</button>
    <button type="button" id="btn_remove" class="btn btn-outline-secondary">삭제</button>
  </form>
  
  <div><a href="${contextPath}/list.do">목록보기</a></div>
  
  <script>
  
  	var frmDetail = $('#frm_detail');
  	
  	// 수정
  	$('#btn_modify').click(() => {
  	  frmDetail.attr('action', '${contextPath}/modify.do');
  	  frmDetail.submit();
  	})
  	
  	// 삭제
  	$('#btn_remove').click(() => {
  	  if(confirm('삭제할까?')){
  		frmDetail.attr('action', '${contextPath}/remove.do');
  		frmDetail.submit();
  	  }
  	})
  	
  	const modifyResult = '${modifyResult}';  // '', '1', '0'
  	if(modifyResult !== ''){
  	  if(modifyResult === '1'){
  		alert('수정 성공');
  	  } else {
  		alert('수정 실패');
  	  }
  	}
  	

  
  </script>
  
  
  
  
  
  
  
  
  
  
  
  

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
</body>
</html>