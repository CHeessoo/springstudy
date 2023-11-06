<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />
    
<jsp:include page="../layout/header.jsp">
  <jsp:param value="${upload.uploadNo}번 게시글" name="title"/>
</jsp:include>

<div>

  <h1 style="text-align: center;">Upload 게시글</h1>
  
  <div>
    <form id="frm_edit" method="post" action="${contextPath}/upload/modify.do">
      <div>작성자 : ${upload.userDto.name}</div>
      <div>작성일 : ${upload.createdAt}</div>
      <div>수정일 : ${upload.modifiedAt}</div>
      <div>제목 : <input type="text" name="title" value="${upload.title}"></div>
      <div>내용</div>
      <div><textarea name="contents" >${upload.contents}</textarea></div>
      <input type="hidden" name="uploadNo" value="${upload.uploadNo}">
      <button type="submit" id="btn_modify">수정</button>
    </form>
  </div>
  
  <hr>
  
  <!-- 첨부 추가 -->
  
  <!-- 첨부 목록에서 삭제 -->
  <div id="attach_list"></div>



  
</div>
  
<script>

  const fnAttachList = () => {
    $.ajax({
  	  // 요청
  	  type: 'get',
  	  url: '${contextPath}/upload/getAttachList.do',
  	  data: 'uploadNo=${upload.uploadNo}',
  	  // 응답
  	  dataType: 'json',
  	  success: (resData) => {  // resData = {"attachList": []}
  		$('#attach_list').empty();
  	    $.each(resData.attachList, (i, attach) => {
       	  let str = '<div>';
          str += '<span>' + attach.originalFilename + '</span>';
          str += '<span data-attach_no="' + attach.attachNo + '"><i class="fa-solid fa-xmark ico_remove_attach"></i></span>';
          str += '</div>';
          $('#attach_list').append(str);
  	    })
  	  }
    })
  }
  
  const fnRemoveAttach = () => {
	$(document).on('click', '.ico_remove_attach', (ev) => {
	  if(!confirm('해당 첨부 파일을 삭제할까요?')){
		return;
	  }
	  $.ajax({
		// 요청
		type: 'post',
		url: '${contextPath}/upload/removeAttach.do',
		data: 'attachNo=' + $(ev.target).parent().data('attach_no'),
		// 응답
		dataType: 'json',
		success: (resData) => {  // resData = {"removeResult": 1}
		  if(resData.removeResult === 1){
			alert('해당 첨부 파일이 삭제되었습니다.');
			fnAttachList();
		  } else {
			alert('해당 첨부 파일이 삭제되지 않았습니다.');
		  }
		}
	  })
	})
  }

  fnAttachList();
  fnRemoveAttach();
  
</script>

  
  

<%@ include file="../layout/footer.jsp" %>