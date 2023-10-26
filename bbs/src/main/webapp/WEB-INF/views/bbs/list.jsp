<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
  h1 {
    color: ForestGreen;
  }
  h3 {
    color: CadetBlue;
  }
  .bbs {
    color: Maroon;
  }
  .paging {
    margin : 30px auto;
    color: MidnightBlue;
  }
</style>
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>

  <h1>목록보기</h1>
  
  <h3>전체개수 : ${total}</h3>
  <c:forEach items="${bbsList}" var="bbs">
    <div class="bbs" data-bbs_no="${bbs.bbsNo}">
      <div>${bbs.bbsNo}</div>
      <div>${bbs.title}</div>
    </div>
  </c:forEach>
  
  <div class="paging">${paging}</div>

  <script>
    /*
  	$('.bbs').click(function(){
  	  // 클릭한 대상 : 이벤트 대상 (this)
  	  let bbsNo = $(this).data('bbs_no');
  	  alert('쨔쟌-☆★ ' + bbsNo + '번이지롱~!');
  	})
  	*/
  	$('.bbs').click((ev) => {
  	  // 클릭한 대상 : 이벤트 대상 (이벤트객체의 target 속성)
  	  console.log(ev.target.dataset.bbs_no);
  	  let bbsNo = $(ev.target).data('bbs_no');
  	  alert('쨔쟌-☆★ ' + bbsNo + '번이지롱~!');
  	})
  </script>

</body>
</html>