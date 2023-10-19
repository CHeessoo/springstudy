<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
  .ck.ck-editor {
    max-width: 800px;
  }
  .ck-editor__editable {
    min-height: 400px;
  }
  .ck-content {
    font-size: 12px;
    color: orange;
  }
</style>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/40.0.0/classic/ckeditor.js"></script>
<script>

  $(function(){     
    fnFileCheck(); // fnFileCheck() 호출
    fnUpload();    // fnUpload()    호출
    fnCkeditor();  // fnCkediotr()  호출
  })
  
  function fnFileCheck(){
    $('.files').change(function(){      // 파일 첨부가 바뀌거나 새로 첨부를 했을때 사용하는 change이벤트
      console.log(this.files);  
      $('#file_list').empty();          // 이전 첨부했던 목록이 남아있지 않도록 첨부된 파일 목록 초기화
      var maxSize = 1024 * 1024 * 100;  // 전체 파일 최대 크기 100MB
      var maxSizePerFile = 1024 * 1024 * 10;
      var totalSize = 0;
      var files = this.files;
      for(let i = 0; i < files.length; i++){
        totalSize += files[i].size;
        if(files[i].size > maxSizePerFile){  // files[i].size : 개별 파일 크기
          alert('각 첨부파일의 최대 크기는 10MB입니다.');
          $(this).val('');         // 빈문자열로 바꿔주면 첨부된 모든 파일을 지우라는 의미
          $('#file_list').empty(); // 실패했을 때 파일 리스트 초기화
          return;                  // 더 진행되지 못하게 종료
        }
        $('#file_list').append('<div>' + files[i].name + '</div>');
      }
      if(totalSize > maxSize){
        alert('전체 첨부파일의 최대 크기는 100MB입니다.');
        $(this).val(''); // 첨부된거 지움
        $('#file_list').empty();
        return;
      }
    })
  }
  
  function fnUpload(){
      $('#btn_upload').click(function(){
        // ajax 파일 첨부는 FormData 객체를 생성해서 data로 전달한다.
        var formData = new FormData();
        var files = $('#files')[0].files; // 첨부된파일들 배열이 됨
        $.each(files, function(i, elem){          
          formData.append('files', elem); // files안에 첨부된 파일들을 받음 (name="files" value=elem)과 같다. == 요청 파라미터를 files라는 이름으로 보낸것과 같다.)
        })
        // ajax
        $.ajax({
          // 요청
          type: 'post',
          url: '${contextPath}/ajax/upload.do',
          data: formData,     // 만든 form을 보내는 방법
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
  
  function fnCkeditor(){
	  
    ClassicEditor
     .create(document.getElementById('contents'), {
	    toolbar: {
		    items: [
	        'undo', 'redo',
	        '|', 'heading',
	        '|', 'fontfamily', 'fontsize', 'fontColor', 'fontBackgroundColor',
	        '|', 'bold', 'italic', 'strikethrough', 'subscript', 'superscript', 'code',
	        '|', 'link', 'uploadImage', 'blockQuote', 'codeBlock',
	        '|', 'bulletedList', 'numberedList', 'todoList', 'outdent', 'indent'
		    ],
		    shouldNotGroupWhenFull: false
	   },
     heading: {
       options: [
         { model: 'paragraph', title: 'Paragraph', class: 'ck-heading_paragraph' },
         { model: 'heading1', view: 'h1', title: 'Heading 1', class: 'ck-heading_heading1' },
         { model: 'heading2', view: 'h2', title: 'Heading 2', class: 'ck-heading_heading2' },
         { model: 'heading3', view: 'h3', title: 'Heading 3', class: 'ck-heading_heading3' },
         { model: 'heading4', view: 'h4', title: 'Heading 4', class: 'ck-heading_heading4' },
         { model: 'heading5', view: 'h5', title: 'Heading 5', class: 'ck-heading_heading5' },
         { model: 'heading6', view: 'h6', title: 'Heading 6', class: 'ck-heading_heading6' }
       ]
     },
     ckfinder: {
  	   // 업로드 경로(첨부되는 이미지가 전달되는 요청주소)
  	   uploadUrl: '${contextPath}/ckeditor/upload.do'
      }
    })
    .catch(err => {
	   console.log(err)
    });
  }

</script>
</head>
<body>

  <div>
    <h3>MVC 파일첨부</h3>
    <form method="post" action="${contextPath}/upload.do" enctype="multipart/form-data">
      <div>
        <input type="file" name="files" class="files" multiple>
      </div>
      <div>
        <button type="submit">업로드</button>
      </div>
      <div id="file_list"></div>
    </form>
  </div>
  
  <hr>
  
  <div>
    <h3>ajax 파일첨부</h3>
    <div>
      <input type="file" class="files" multiple>
    </div>
    <div>
      <button type="button" id="btn_upload">업로드</button>
    </div>
  </div>
  
  
  <hr>
  
  
  <div>
    <h3>CKEditor</h3>
    <form method="post" action="${contextPath}/add.do">
      <div>
        <textarea name="contents" id="contents"></textarea>
      </div>
      <div>
        <button type="submit">전송</button>
      </div>
    </form>
  </div>
  
  

</body>
</html>