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
  
  $(() => {
	fnRegisterStaff();
	fnGetStaffList();
	fnGetStaff();
	fnCheckSno();
	fnCheckDept();
  })
  
  
  
  const fnRegisterStaff = () => {
	$('#btn_register').click(() => {
	  $.ajax({
		type: 'post',
		url: '${contextPath}/add.do',
		data: $('#frm_registe').serialize(),
		dataType: 'json',
		success: (resData) => {
		  if(resData.addResult == 1){
			alert('사원 등록이 성공했습니다.');
			fnGetStaffList();
			fnInit();
		  } else {
			alert('사원 등록이 실패했습니다.');
		  }
		},
		error: (jqXHR) => {
		  if(jqXHR.responseJSON.addResult === 0){
			alert('사원 등록이 실패했습니다.');
		  }
		}
	  })
	})
  }
  
  const fnGetStaffList = () => {
	$('#btn_list').click(() => {
	  fnInit();
	  $.ajax({
		type: 'get',
		url: '${contextPath}/list.json',
		dataType: 'json',
		success: (resData) => {
		  $('#staff_list').empty();
		  $.each(resData.list, () => {
			var tr = '<tr>';
			tr += '<td>' + ${sno} + '</td>';
			tr += '<td>' + ${name} + '</td>';
			tr += '<td>' + ${dept} + '</td>';
			tr += '<td>' + ${salary} + '</td>';
			tr += '</tr>';
			$('#staff_list').append(tr);
		  console.log(resData);
		  })
		}
	  })
	})
  }
  
  
  const fnGetStaff = () => {
	$('#btn_query').click(() => {
	  $.ajax({
		type: 'get',
		url: '${contextPath}/query.json',
		data: 'query=' + $('#query').val(),
		dataType: 'json',
		success: (resData) => {
		  $('#staff_list').empty();
			tr += '<tr>';
			tr += '<td>' + resData.sno + '</td>';
			tr += '<td>' + resData.name + '</td>';
			tr += '<td>' + resData.dept + '</td>';
			tr += '<td>' + resData.salary + '</td>';
			tr += '</tr>';
			$('#staff_list').append(tr);
		},
		error: (jqXHR) => {
		  if(jqXHR.staff === null){
			alert('조회된 사원 정보가 없습니다.');
		  }
		}
	  })
	})
  }
  
  const fnInit = () => {
	$('#sno').val('');
	$('#name').val('');
	$('#dept').val('');
	$('#query').val('');
  }
  
  const fnCheckSno = () => {
	$('#sno').blur((ev) => {
	  let sno = $(ev.target).val();
	  let regSno = /^\d{5}$/;
	  if(!regSno.test(sno)){
		alert('사원번호는 5자리 숫자입니다.');
	  }
	})
  }
  
  const fnCheckDept = () => {
	$('#dept').blur((ev) => {
	  let dept = $(ev.target).val();
	  let regDept = /^[가-힣]{3,5}$/;
	  if(!regDept.test(dept)){
		alert('부서는 3~5자리 한글입니다.');
	  }
	})
  }
  
</script>
</head>
<body>

  <div>
    <h1>사원등록</h1>
    <div>
      <form id="frm_registe" >
        <input type="text" name="sno" id="sno" placeholder="사원번호">
        <input type="text" name="name" id="name" placeholder="사원명">
        <input type="text" name="dept" id="dept" placeholder="부서명">
        <button type="button" id="btn_register">등록</button>
      </form>
    </div>
  </div>
  
  <hr>
  
  <div>
    <h1>사원조회</h1>
    <div>
      <input type="text" name="query" id="query" placeholder="사원번호입력">
      <button type="button" id="btn_query">조회</button>
      <button type="button" id="btn_list">전체</button>
    </div>
  </div>
  
  <hr>
  
  <div>
    <h1>사원목록</h1>
    <div>
      <table border="1">
        <thead>
          <tr>
            <td>사원번호</td>
            <td>사원명</td>
            <td>부서명</td>
            <td>연봉</td>
          </tr>
        </thead>
        <tbody id="staff_list">
        
        </tbody>
      </table>
    </div>
  </div>

</body>
</html>