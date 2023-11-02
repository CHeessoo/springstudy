<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />
    
<jsp:include page="../layout/header.jsp">
  <jsp:param value="업로드게시글작성" name="title"/>
</jsp:include>


<div>
  <h1 style="text-align: center; color: darkgreen;">Upload 게시글 작성하기</h1>
  <form method="post" action="${contextPath}/upload/add.do" enctype="multipart/form-data"> <!-- 첨부를 위해서 method, enctype 필수 -->
    <div class="mb-3 row">
      <label for="email" class="col-sm-2 col-form-label">작성자</label>
      <div class="col-sm-10">
        <input type="text" id="email" value="${sessionScope.user.email}" class="form-control-plaintext" readonly >
      </div>
    </div>
    <div>
      <label for="title" class="form-label">제목</label>
      <input type="text" name="title" id="title" class="form-control border border-success" >
    </div>
    <div>
      <label for="contents" class="form-label">내용</label>
      <textarea rows="3" cols="50" name="contents" id="contents" class="form-control border border-success" ></textarea>
    </div>
    <div>
      <label for="files" class="form-label">첨부</label>
      <input type="file" name="files" id="files" class="form-control" multiple > <!-- multiple이 있어야 다중첨부 가능 -->
    </div>
    <div >
      <input type="hidden" name="userNo" value="${sessionScope.user.userNo}" >
      <button type="submit" class="btn btn-outline-success col-12" style="margin: 32px auto;">작성완료</button>
    </div>
  </form>
  <div id="file_list"></div>
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
  
  fnFileCheck(); // fnFileCheck() 호출
  
  
</script>

  
  

<%@ include file="../layout/footer.jsp" %>