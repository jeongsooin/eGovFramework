<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>CLASS</title>
	
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    
    <!-- Custom CSS -->    
    <link rel="stylesheet" href="css/modal.css">
    <link rel="stylesheet" href="css/button.css">
    <link rel="stylesheet" href="css/listTable.css">
    
    <!-- Script -->	
    <script src="js/common.js"></script>
    <script>
	$(document).ready(function() {		
		// DataTable 설정 세팅
		var table = $('#listTable').DataTable({
	    	language : {
	    		"emptyTable": "데이터가 없습니다.",
	            "lengthMenu": "페이지당 _MENU_ 개씩 보기",
	            "info": "현재 _START_ - _END_ / _TOTAL_건",
	            "infoEmpty": "데이터 없음",
	            "infoFiltered": "( _MAX_건의 데이터에서 필터링됨 )",
	            "search" : "검색",
	            "zeroRecords": "일치하는 데이터가 없습니다.",
	            "loadingRecords": "로딩중...",
	            "processing":     "잠시만 기다려 주십시오...",
	            "paginate": {
	                "next": "다음",
	                "previous": "이전"
	            }
	    	},
	    	lengthChange : false,
	    	searching: true,
	    	info : false,
	    	select : {
	    		items: 'row'
	    	}
		});
	});	
	</script>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/gymManager/cmm/navbar.jsp"></jsp:include>
	
	<!-- Contents 영역 START -->
	<div class="main-content">
		<!-- 상단 버튼 영역 START -->
		<div class="button-area">
			<form id="search-form" class="form-inline"  method="POST" action="/deleteSchedule.do">
				 <input type="text" class="form-control mb-2 mr-sm-2" id="searchKey" name="cid" hidden>
				 <button class="btn btn btn btn-dark" id="search-button" hidden></button>
				 <p class="button-group"><a class="btn btn btn-dark" id="insert-button" data-toggle="modal" data-backdrop="static" data-keyboard="false" href="#scheduleInsertModal">등록</a></p>
				 <p class="button-group"><a href="javascript:popUpAlert();" class="btn btn btn-dark" id="update-button">수정</a></p>
				 <p class="button-group"><a href="javascript:popUpAlert();" class="btn btn btn-dark" id="delete-button">삭제</a></p>
			</form>
		</div>
		<!-- 상단 버튼 영역 END -->
		
		<!-- 수업일정 정보 테이블 START -->
		<table id="listTable" class="ui celled table" style="width:100%">
			<thead>
            <tr>
                <th>  수업번호  </th>
                <th>  회원번호  </th>
                <th>  회원이름  </th>
                <th>  수업강사  </th>
                <th>  강사이름  </th>
                <th>  수업날짜  </th>
                <th>  수업시간  </th>
            </tr>
	        </thead>
	        <tbody>
	        <c:forEach items="${list}" var="vo" varStatus="status">
	        	<tr>
	        		<td><c:out value="${vo.cid}"></c:out></td>
	        		<td><c:out value="${vo.mid}"></c:out></td>
	        		<td><c:out value="${members[status.index]}"></c:out></td>
	        		<td><c:out value="${vo.tid}"></c:out></td>
	        		<td><c:out value="${trainers[status.index]}"></c:out></td>
	        		<td><c:out value="${vo.class_date}"></c:out></td>
	        		<td><c:out value="${vo.class_time}"></c:out></td>
		        </tr>
	        </c:forEach>
	        </tbody>
		</table>
		<!-- 사물함 정보 테이블 END  -->
	</div>
	<!-- Contents 영역 END -->
		
	<!-- Optional JavaScript -->
    <script src="js/popper.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
</body>
</html>
