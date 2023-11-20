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
      <c:if test="${sessionScope.user.userNo == upload.userDto.userNo}">
        <button type="submit" id="btn_modify" class="btn btn-outline-secondary">수정</button>
      </c:if>
    </form>
  </div>
  
  <hr>
  
  <!-- 첨부 추가 -->
  <c:if test="${sessionScope.user.userNo == upload.userDto.userNo}">
    <div>
      <label for="files" class="form-label">첨부</label>
      <input type="file" name="files" id="files" class="form-control" multiple > <!-- multiple이 있어야 다중첨부 가능 -->
    </div>
    <div >
      <input type="hidden" name="userNo" value="${sessionScope.user.userNo}" >
      <button type="button" id="btn_add_attach" class="btn btn-success" style="margin: 10px auto;">첨부추가하기</button>
    </div>
  </c:if>
  
  <!-- 첨부 목록에서 삭제 -->
  <h5>기존 첨부 목록</h5>
  <div id="attach_list"></div>

</div>
  
<script>

  const fnFileCheck = () => {
    $('#files').change((ev) => {              // 파일 첨부가 바뀌거나 새로 첨부를 했을때 사용하는 change이벤트
      $('#file_list').empty();                // 이전 첨부했던 목록이 남아있지 않도록 첨부된 파일 목록 초기화
      let maxSize = 1024 * 1024 * 100;        // 전체 파일 최대 크기 100MB
      let maxSizePerFile = 1024 * 1024 * 10;  // 개별 파일 최대 크기 10MB
      let totalSize = 0;
      let files = ev.target.files;
      for(let i = 0; i < files.length; i++){
        totalSize += files[i].size;
        if(files[i].size > maxSizePerFile){  // files[i].size : 개별 파일 크기
          alert('각 첨부파일의 최대 크기는 10MB입니다.');
          $(ev.target).val('');              // 빈문자열로 바꿔주면 첨부된 모든 파일을 지우라는 의미
          $('#file_list').empty();           // 실패했을 때 파일 리스트 초기화
          return;                            // 더 진행되지 못하게 종료
        }
        $('#file_list').append('<div>' + files[i].name + '</div>');
      }
      if(totalSize > maxSize){
        alert('전체 첨부파일의 최대 크기는 100MB입니다.');
        $(ev.target).val('');               // 첨부된거 지움
        $('#file_list').empty();
        return;
      }
    })
  }

  const fnAddAttach = () => {
    $('#btn_add_attach').click(() => {
  	  // 폼을 FormData 객체로 생성한다.
  	  let formData = new FormData();
  	  // 첨부된 파일들을 FormData에 추가한다.
  	  let files = $('#files').prop('files');
  	  $.each(files, (i, file) => {
  		  formData.append('files', file);  // 폼에 포함된 파라미터명은 files이다. files는 여러 개의 파일을 가지고 있다.
  	  })
  	  // 현재 게시글 번호(uploadNo)를 FormData에 추가한다.
  	  formData.append('uploadNo', '${upload.uploadNo}');
  	  // FormData 객체를 보내서 저장한다.
  	  $.ajax({
    	// 요청
    	type: 'post',
    	url: '${contextPath}/upload/addAttach.do',
    	data: formData,
    	contentType: false,
    	processData: false,
    	// 응답
    	dataType: 'json',
    	success: (resData) => {  // resData = {"attachResult": true}
    	  if(resData.attachResult){
    		alert('첨부 파일이 추가되었습니다.');
    		fnAttachList();
    	  } else {
    		alert('첨부 파일이 추가되지 않았습니다.');
    	  }
    	}
  	  })
    })
  }

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
          if('${sessionScope.user.userNo}' === '${upload.userDto.userNo}'){
            str += '<span data-attach_no="' + attach.attachNo + '"><i class="fa-solid fa-xmark ico_remove_attach"></i></span>';
          }
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
  
  const fnModifyAttach = () => {
    $('#frm_upload_edit').submit((ev) => {
      if($('#title').val() === ''){
        alert('제목은 반드시 입력해야 합니다.');
        $('#title').focus();
        ev.preventDefault();
        return;
      } else if($('#files').val() !== ''){
    	  alert('새로운 첨부가 있는 경우 첨부추가하기 버튼을 먼저 클릭해 주세요.');
    	  $('#btn_add_attach').focus();
    	  ev.preventDefault();
        return;
      }
    })
  }

  fnFileCheck();
  fnAddAttach();
  fnAttachList();
  fnRemoveAttach();
  fnModifyAttach();
  
</script>

  
  

<%@ include file="../layout/footer.jsp" %>