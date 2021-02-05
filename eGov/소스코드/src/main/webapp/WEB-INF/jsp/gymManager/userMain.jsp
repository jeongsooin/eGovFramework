<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>MAIN</title>
	
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    
    <!-- Custom CSS -->    
    <link rel="stylesheet" href="css/modal.css">
    <link rel="stylesheet" href="css/button.css">
    <link rel="stylesheet" href="css/info.css">
    <link rel="stylesheet" href="css/listTable.css">
    
    <!-- Script -->	
    <script src="js/common.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/gymManager/cmm/usrNavbar.jsp"></jsp:include>
	
	<!-- Contents 영역 START -->
	<div class="main-content">
		<!-- 상단 사용자 정보 표시 영역 START -->
		<div class="info-area">
			<form class="form-inline info-group">
				 <label>ID</label>
				 <p class="data-group">${mid}</p>
				 <label id="info-start">이용시작일자</label>
				 <p class="data-group">${start}</p>
				 <label id="info-end">이용만료일자</label>
				 <p class="data-group">${end}</p>
				 <label id="info-count">개인수업횟수</label>
				 <p class="data-group">${pc}</p>
				 <label>회 남음</label>				 
			</form>
		</div>
		<!-- 상단 사용자 정보 표시 영역 END -->
		
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
                <th>  진행시간  </th>
                <th>  진행상태  </th>
                <th>  수업일지  </th>
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
	        		<td><c:out value="${vo.class_type}"></c:out> 분</td>
	        		<td><c:out value="${stat[status.index]}"></c:out></td>
	        		<c:choose>
	        			<c:when test="${stat[status.index] == '예정'}">
	        				<td>
	        					<button class="btn btn-secondary diary" disabled="disabled">
					        			수업일지
					        	</button>
	        				</td>
	        			</c:when>
	        			<c:otherwise>
	        				<td>
	        					<button class="btn btn-info diary" value="${vo.cid} ${vo.mid} ${vo.tid} ${vo.class_date} ${vo.class_time}">
					        			수업일지
					        	</button>
	        				</td>
	        			</c:otherwise>
	        		</c:choose>
		        </tr>
	        </c:forEach>
	        </tbody>
		</table>
		<!-- 수업일정 정보 테이블 END  -->
	</div>
	<!-- Contents 영역 END -->
	
	<!-- 수업일지 조회 모달 START -->
	<div class="modal fade" id="diaryModal" tabindex="-1" role="dialog" aria-labelledby="diaryFormModal" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="diaryFormModal">수업일지</h5>
	      </div>
	      <div class="modal-body">
	      	<form id="diaryForm" method="POST" action="/insertDiary.do">
	      		<div class="form-row frow">
	      			<div class="col-md-4 mb-3">
	      				<label for="dcid">수업번호</label>
	      				<input type="text" class="form-control" id="dcid" name="cid" onfocus="this.blur()" readonly>
	      			</div>
	      			<div class="col-md-4 mb-3">
	      				<label for="dmid">수강회원</label>
	      				<input type="text" class="form-control" id="dmid" name="mid" onfocus="this.blur()" readonly>
	      			</div>
	      			<div class="col-md-4 mb-3">
	      				<label for="dtid">수업강사</label>
	      				<input type="text" class="form-control" id="dtid" name="tid" onfocus="this.blur()" readonly>
	      			</div>
			   </div>
			   <div class="form-row frow">
	      			<div class="col-md-6 mb-3">
	      				<label for="dclass_date">수업날짜</label>
	      				<input type="text" class="form-control" id="dclass_date" name="class_date" onfocus="this.blur()" readonly>
	      			</div>
	      			<div class="col-md-6 mb-3">
	      				<label for="dclass_time">수업시간</label>
	      				<input type="text" class="form-control" id="dclass_time" name="class_time" onfocus="this.blur()" readonly>
	      			</div>
			   </div>
			   <div class="form-row frow">
			   		<div class="col-md-12 mb-3">
			   			<label for="contents">일지내용<span style="color:red"> *</span></label>
						<textarea class="form-control" id="contents" name="contents" rows="10"></textarea>
			    	</div>
			  </div>			   
			  <hr>
			  <div id="form-button-group" align="right">
	        	<button type="button" class="btn btn-outline-dark" data-dismiss="modal">닫기</button>
	      	  </div>
			</form>
	      </div>
	    </div>
	  </div>
	</div>		
	<!-- 수업 일지 조회 모달 END   -->
	
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
		
		// 수업일지 버튼 클릭시 이벤트 처리 동작
		$(".diary").click(function() {
			// 선택한 사물함 버튼의 value 값을 가져와서 전달할 변수에 세팅
			var data = $(this).attr('value').split(' ');
			var cid = data[0];
			var mid = data[1];
			var tid = data[2];
			var class_date = data[3];
			var class_time = data[4];
			
			// 수업일지 작성 폼에 데이터 셋팅
			$('#dcid').val(cid);
			$('#dmid').val(mid);
			$('#dtid').val(tid);
			$("#dclass_date").val(class_date);
			$("#dclass_time").val(class_time);

			selectDiaryAjax();
		});
	});	
	</script>
	
	<!-- Optional JavaScript -->
    <script src="js/popper.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
</body>
</html>
