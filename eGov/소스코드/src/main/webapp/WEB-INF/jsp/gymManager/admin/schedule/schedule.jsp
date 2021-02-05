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
    <style type="text/css">
    #schedule-menu {
		background-color: white;
		color : #138496;
		border-radius: 10px;
		font-weight: bold;
	}       
    </style>
    
    <!-- Script -->	    
    <script src="js/common.js"></script>
    
    <script>
    
    </script>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/gymManager/cmm/navbar.jsp"></jsp:include>
	
	<!-- Contents 영역 START -->
	<div class="main-content">
		<!-- 상단 버튼 영역 START -->
		<div class="button-area">
			<form id="search-form" class="form-inline">
				 <input type="text" class="form-control mb-2 mr-sm-2" id="pk" hidden>				 
				 <p class="button-group"><a href="#scheduleInsertModal"     class="btn btn btn-dark" id="insert-schedule" data-toggle="modal" data-backdrop="static" data-keyboard="false">등록</a></p>
				 <p class="button-group"><a href="javascript:popUpAlert();" class="btn btn btn-dark" id="update-schedule">수정</a></p>
				 <p class="button-group"><a href="javascript:popUpAlert();" class="btn btn btn-dark" id="delete-schedule">삭제</a></p>
				 <p class="button-group"><a href="javascript:toggleView();" class="btn btn btn-dark" id="toggle-calendar">달력보기</a></p>
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
	        				<c:choose>
		        				<c:when test="${diary[status.index] == 'Y'}">
		        					<td>
		        						<button class="btn btn-info diary" value="${vo.cid} ${vo.mid} ${vo.tid} ${vo.class_date} ${vo.class_time}">
						        			수업일지
						        		</button>
		        					</td>
		        				</c:when>
		        				<c:otherwise>
		        					<td>
		        						<button class="btn btn-outline-info diary" value="${vo.cid} ${vo.mid} ${vo.tid} ${vo.class_date} ${vo.class_time}">
						        			수업일지
						        		</button>
		        					</td>
		        				</c:otherwise>
	        				</c:choose>	        				
	        			</c:otherwise>
	        		</c:choose>
		        </tr>
	        </c:forEach>
	        </tbody>
		</table>
		<!-- 수업일정 정보 테이블 END  -->
		<div id="calendar_div">
			<div id="calendar"></div>
		</div>
	</div>
	<!-- Contents 영역 END -->
	
	<!-- 수업일정 등록 모달 START -->
	<div class="modal fade" id="scheduleInsertModal" tabindex="-1" role="dialog" aria-labelledby="insertModal" aria-hidden="true">
	  <div class="modal-dialog modal-lg" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="insertModal">수업 일정 등록</h5>
	      </div>
	      <div class="modal-body">
	        <form id="insertForm" class="needs-validation" method="POST" action="/insertSchedule.do" novalidate>
			  <div class="form-row frow">
			    <div class="col-md-6 mb-3">
			      <label for="scheduleDate">수업날짜<span style="color:red"> *</span></label>
			      <input type="text" class="form-control datepicker condition" id="scheduleDate" name="class_date" readonly required>
			    </div>
			    <div class="col-md-6 mb-3">
			      <label for="scheduleType">진행시간<span style="color:red"> *</span></label>
			      <select class="form-control condition" id="scheduleType" name="type" required>
        			<option value="50" selected>50분 수업</option>
        			<option value="30">30분 수업</option>
      			  </select>
			    </div>
			   </div>
			   <div class="form-row frow">
			    <div class="col-md-6 mb-3">
			      <label for="scheduleMember">수강회원<span style="color:red"> *</span></label>
			      <input type="text" class="form-control condition" id="scheduleMember" name="mid" placeholder="아래의 목록에서 선택해주세요" readonly required>
			    </div>
			    <div class="col-md-6 mb-3">
			      <label for="scheduleTrainer">수업강사<span style="color:red"> *</span></label>
			      <input type="text" class="form-control condition" id="scheduleTrainer" name="tid" placeholder="아래의 목록에서 선택해주세요" readonly required>
			    </div>
			  </div>
			  <div class="form-row frow">
			    <div class="col-md-6 mb-3" id="memberList">
					<table id="grid-selection1" class="table table-condensed table-hover">
			  		<thead>
			  			<tr>
			  				<th data-column-id="id"   data-identifier="true" >아이디</th>
			  				<th data-column-id="name" data-identifier="false">회원이름</th>
			  			</tr>
			  		</thead>
			  		<tbody>
			  			<c:forEach items="${memberList}" var="vo">
			  				<tr>
			  					<td><c:out value="${vo.mid}"></c:out></td>
			  					<td><c:out value="${vo.name}"></c:out></td>
			  				</tr>
			  			</c:forEach>
			  		</tbody>
			  	</table>
			    </div>
			    <div class="col-md-6 mb-3" id="trainerList">
					<table id="grid-selection2" class="table table-condensed table-hover">
			  		<thead>
			  			<tr>
			  				<th data-column-id="id"   data-identifier="true" >아이디</th>
			  				<th data-column-id="name" data-identifier="false">트레이너 이름</th>
			  			</tr>
			  		</thead>
			  		<tbody>
			  			<c:forEach items="${trainerList}" var="vo">
			  				<tr>
			  					<td><c:out value="${vo.tid}"></c:out></td>
			  					<td><c:out value="${vo.name}"></c:out></td>
			  				</tr>
			  			</c:forEach>
			  		</tbody>
			  	</table>
			    </div>
			  </div>
			  <div class="form-row frow">
			    <div class="col-md-6 mb-3">
			      <label for="scheduleTime">수업시간<span style="color:red"> *</span></label>
			      <select class="form-control" id="scheduleTime" name="class_time" required ></select>
			    </div>
			    <div class="col-md-3 mb-3">
			    	<label for="timeCheckButton">　</label>
			    	<input type="button" id="timeCheckButton" name="button" class="form-control btn-outline-dark" onclick="timeCheck()" value="시간조회">
			    	<label class="error" for="timeCheckBox" style="display:none;"></label>
			    </div>
			    <div class="col-md-3 mb-3">
			    	<label for="timeCheckBox">　</label>
			    	<input class="form-control form-check-input" type="checkbox" id="timeCheckBox" name="timeCheckBox" required>			    	
			    </div>
			  </div>			   			  
			  <hr>
			  <div id="form-button-group" align="right">
	        	<button type="button" class="btn btn-outline-dark" data-dismiss="modal">취소</button>
	        	<button type="submit" class="btn btn-dark" id="btn_submit_insert">등록</button>
	      	</div>
			</form>
	      </div>     
	    </div>
	  </div>
	</div>
	<!-- 수업 일정 등록 모달 END   -->
	
	<!-- 수업일정 수정 모달 START -->
	<div class="modal fade" id="scheduleUpdateModal" tabindex="-1" role="dialog" aria-labelledby="updateModal" aria-hidden="true">
	  <div class="modal-dialog modal-lg" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="updateModal">수업 일정 수정</h5>
	      </div>
	      <div class="modal-body">
	        <form id="updateForm" class="needs-validation" method="POST" action="/updateSchedule.do" novalidate>
	          <div class="form-row frow" style="display:none">
			    <div class="col-md-6 mb-3">
			      <label for="uscheduleId">수업번호</label>
			      <input type="text" class="form-control" id="uscheduleId" name="cid" placeholder="등록번호" onfocus="this.blur()" readonly>
			    </div>
			   </div>	
			  <div class="form-row frow">
			    <div class="col-md-6 mb-3">
			      <label for="uscheduleDate">수업날짜<span style="color:red"> *</span></label>
			      <input type="text" class="form-control datepicker condition" id="uscheduleDate" name="class_date" readonly required>		      
			    </div>
			    <div class="col-md-6 mb-3">
			      <label for="uscheduleType">진행시간<span style="color:red"> *</span></label>
			      <select class="form-control condition" id="uscheduleType" name="type" required>
        			<option value="50" selected>50분 수업</option>
        			<option value="30">30분 수업</option>
      			  </select>
			    </div>
			   </div>
			   <div class="form-row frow">
			   <div class="col-md-6 mb-3">
			      <label for="uscheduleMember">수강회원<span style="color:red"> *</span></label>
			      <input type="text" class="form-control condition" id="uscheduleMember" name="mid" placeholder="아래의 목록에서 선택해주세요" onkeydown='return numChk(event)' onkeyup='removeChar(event)' style='ime-mode:disabled;' maxlength="4" required>
			    </div>
			    <div class="col-md-6 mb-3">
			      <label for="uscheduleTrainer">수업강사<span style="color:red"> *</span></label>
			      <input type="text" class="form-control condition" id="uscheduleTrainer" name="tid" placeholder="아래의 목록에서 선택해주세요" required>
			    </div>
			  </div>
			  <div class="form-row frow">
			    <div class="col-md-6 mb-3" id="memberList2">
					<table id="grid-selection3" class="table table-condensed table-hover">
			  		<thead>
			  			<tr>
			  				<th data-column-id="id"   data-identifier="true" >아이디</th>
			  				<th data-column-id="name" data-identifier="false">회원이름</th>
			  			</tr>
			  		</thead>
			  		<tbody>
			  			<c:forEach items="${memberList}" var="vo">
			  				<tr>
			  					<td><c:out value="${vo.mid}"></c:out></td>
			  					<td><c:out value="${vo.name}"></c:out></td>
			  				</tr>
			  			</c:forEach>
			  		</tbody>
			  	</table>
			    </div>
			    <div class="col-md-6 mb-3" id="trainerList2">
					<table id="grid-selection4" class="table table-condensed table-hover">
			  		<thead>
			  			<tr>
			  				<th data-column-id="id"   data-identifier="true" >아이디</th>
			  				<th data-column-id="name" data-identifier="false">트레이너 이름</th>
			  			</tr>
			  		</thead>
			  		<tbody>
			  			<c:forEach items="${trainerList}" var="vo">
			  				<tr>
			  					<td><c:out value="${vo.tid}"></c:out></td>
			  					<td><c:out value="${vo.name}"></c:out></td>
			  				</tr>
			  			</c:forEach>
			  		</tbody>
			  	</table>
			    </div>
			  </div>
			  <div class="form-row frow">
			    <div class="col-md-6 mb-3">
			      <label for="uscheduleTime">수업시간<span style="color:red"> *</span></label>
			      <select class="form-control" id="uscheduleTime" name="class_time" required ></select>
			    </div>
			    <div class="col-md-3 mb-3">
			    	<label for="timeCheckButton">　</label>
			    	<input type="button" id="timeCheckButton" name="button" class="form-control btn-outline-dark" onclick="utimeCheck()" value="시간조회">			    	
			    	<label class="error" for="utimeCheckBox" style="display:none;"></label>	
			    </div>
			    <div class="col-md-3 mb-3">
			    	<label for="utimeCheckBox">　</label>
			    	<input class="form-control form-check-input" type="checkbox" id="utimeCheckBox" name="utimeCheckBox" required>	
			    </div>
			  </div>	   
			  <hr>
			  <div id="form-button-group" align="right">
	        	<button type="button" class="btn btn-outline-dark" data-dismiss="modal">취소</button>
	        	<button type="submit" class="btn btn-dark" id="btn_submit_update">수정</button>
	      	</div>
			</form>
	      </div>     
	    </div>
	  </div>
	</div>
	<!-- 수업 일정 수정 모달 END   -->
	
	<!-- 수업일지 등록 모달 START -->
	<div class="modal fade" id="diaryModal" tabindex="-1" role="dialog" aria-labelledby="diaryFormModal" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="diaryFormModal">수업일지 작성</h5>
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
	        	<button type="button" class="btn btn-outline-dark" data-dismiss="modal">취소</button>
	        	<button type="submit" class="btn btn-dark" id="btn_submit_write">작성</button>
	        	<button type="button" class="btn btn-dark" id="btn_submit_rewrite" onclick="updateDiaryAjax()">수정</button>
	      	  </div>
			</form>
	      </div>
	    </div>
	  </div>
	</div>		
	<!-- 수업 일지 등록 모달 END   -->
	
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
		
		// 테이블 row 클릭 시 [수정] 및 [삭제] 버튼 실행 함수 세팅
		// 테이블 row 클릭 시 [수정] 및 [삭제]할 데이터 세팅
	    $('#listTable tbody').on( 'click', 'tr', function () {
	        if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	            // 선택된 row가 없으면 [수정],[삭제] 버튼 비활성화
	            $('#update-schedule').attr('href','javascript:popUpAlert();');
	            $('#delete-schedule').attr('href','javascript:popUpAlert();');
	        } else {
	            table.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	            // 선택된 row가 있으면 [수정],[삭제] 버튼 활성화
	            $('#update-schedule').attr('href','javascript:popUpOpen();');
	            $('#delete-schedule').attr('href','javascript:deleteScheduleAjax();');
	            var data = table.rows('.selected').data();
	            setUpdateValue(data);
	            setDeleteValue(data);
	        }
	    });
		
	    // 마우스 오버된 컬럼에 하이라이트 적용
	    $('#listTable tbody').on('mouseenter', 'td', function () {
	    	var colIdx = table.cell(this).index().column;	    	 
            $(table.cells().nodes()).removeClass('highlight');
            $(table.column(colIdx).nodes()).addClass('highlight');
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

			selectDiaryAjax(0, 0, 0, 0, 0);
		});
			 	
		// 회원 목록 테이블 세팅
		$("#grid-selection1").bootgrid({
			navigation: 1,
		    selection: true,
		    multiSelect: false,
		    rowSelect: true,
		    keepSelection: true,
		    labels: {
		        noResults: "검색 결과가 없습니다",
		        search: "검색"
		    }
		}).on("selected.rs.jquery.bootgrid", function(e, rows) {
			
		    var rowIds = [];
		    rowIds.push(rows[0].id);
			$("#scheduleMember").val(rowIds[0]);
			$("input:checkbox[id='timeCheckBox']").attr("checked", false);
		}).on("deselected.rs.jquery.bootgrid", function(e, rows) {
			$("#scheduleMember").val("");
		});
		
		$("#grid-selection3").bootgrid({
			navigation: 1,
		    selection: true,
		    multiSelect: false,
		    rowSelect: true,
		    keepSelection: true,
		    labels: {
		        noResults: "검색 결과가 없습니다",
		        search: "검색"
		    }
		}).on("selected.rs.jquery.bootgrid", function(e, rows) {
			
		    var rowIds = [];
		    rowIds.push(rows[0].id);
			$("#uscheduleMember").val(rowIds[0]);
			$("input:checkbox[id='utimeCheckBox']").attr("checked", false);
		}).on("deselected.rs.jquery.bootgrid", function(e, rows) {
			$("#uscheduleMember").val("");
		});
		
		// 트레이너 목록 테이블 세팅
		$("#grid-selection2").bootgrid({
			navigation: 1,
		    selection: true,
		    multiSelect: false,
		    rowSelect: true,
		    keepSelection: true,
		    labels: {
		        noResults: "검색 결과가 없습니다",
		        search: "검색"
		    }
		}).on("selected.rs.jquery.bootgrid", function(e, rows) {
			
		    var rowIds = [];
		    rowIds.push(rows[0].id);
		    $("#scheduleTrainer").val(rowIds[0]);
		    $("input:checkbox[id='timeCheckBox']").attr("checked", false);
		}).on("deselected.rs.jquery.bootgrid", function(e, rows) {
			$("#scheduleTrainer").val("");
		});
	 	
		$("#grid-selection4").bootgrid({
			navigation: 1,
		    selection: true,
		    multiSelect: false,
		    rowSelect: true,
		    keepSelection: true,
		    labels: {
		        noResults: "검색 결과가 없습니다",
		        search: "검색"
		    }
		}).on("selected.rs.jquery.bootgrid", function(e, rows) {
			
		    var rowIds = [];
		    rowIds.push(rows[0].id);
		    $("#uscheduleTrainer").val(rowIds[0]);
		    $("input:checkbox[id='utimeCheckBox']").attr("checked", false);
		}).on("deselected.rs.jquery.bootgrid", function(e, rows) {
			$("#uscheduleTrainer").val("");
		});
		
		// 수업일정 등록 폼 Validatie 설정
		$("#insertForm").validate({
			debug : false,
			rules : {
				mid : {
					required : true
				},
				tid : {
					required : true
				},
				class_type : {
					required : true
				},
				class_date : {
					required : true
				},
				class_time : {
					required : true
				},
				timeCheckBox : {
					required : true
				}
			}, 
			messages : {
				mid : {
					required : "회원을 선택해주세요"
				},
				tid : {
					required : "트레이너를 선택해주세요"
				},
				class_type : {
					required : "수업시간을 선택해주세요"
				},
				class_date : {
					required : "수업날짜를 선택해주세요"
				},
				class_time : {
					required : "수업시간을 선택해주세요"
				},
				timeCheckBox : {
					required : "시간조회 후 시간을 선택해주세요"
				}
			}
		});
	 	
		// 수업일정 수정 폼 Validatie 설정
		$("#updateForm").validate({
			debug : false,
			rules : {
				mid : {
					required : true
				},
				tid : {
					required : true
				},
				class_type : {
					required : true
				},
				class_date : {
					required : true
				},
				class_time : {
					required : true
				},
				utimeCheckBox : {
					required : true
				}
			}, 
			messages : {
				mid : {
					required : "회원을 선택해주세요"
				},
				tid : {
					required : "트레이너를 선택해주세요"
				},
				class_type : {
					required : "수업시간을 선택해주세요"
				},
				class_date : {
					required : "수업날짜를 선택해주세요"
				},
				class_time : {
					required : "수업시간을 선택해주세요"
				},
				utimeCheckBox : {
					required : "시간조회 후 시간을 선택해주세요"
				}
			}
		});
		
		// 수업일지 등록 폼 Validatie 설정
		$("#diaryForm").validate({
			debug : false,
			rules : {
				mid : {
					required : true
				},
				tid : {
					required : true
				},
				cid : {
					required : true
				},
				contents : {
					required : true
				}
			}, 
			messages : {
				contents : {
					required : "일지 내용을 입력해주세요"
				}
			}
		});
		
		// 조회 조건의 value가 변경되었을 때 [시간조회] 값 초기화
		$(".condition").change(function() {
			$("input:checkbox[id='timeCheckBox']").attr("checked", false);
			$("input:checkbox[id='utimeCheckBox']").attr("checked", false);
		});

		//체크박스 클릭 방지
		$('input[type="checkbox"]').click(function(event) {
		    var $checkbox = $(this);
		    event.preventDefault();
		    event.stopPropagation();
		});
		
	 	// DatePicker 사용한 input form validation 적용을 위해 focusout 시켜주기
	    $("#scheduleDate").focusout();
	    $("#uscheduleDate").focusout();
	    
	    var calendarEl = document.getElementById("calendar");

	    var calendar = new FullCalendar.Calendar(calendarEl, {
	        plugins: [ 'interaction', 'dayGrid', 'timeGrid' ],
	        defaultView: 'dayGridMonth',
	        header: {
	          left: 'prev,next today',
	          center: 'title',
	          right: 'dayGridMonth,timeGridWeek,timeGridDay'
	        },
	        eventSources: [{
	        	events : function(info, successCallback, failureCallback) {
					$.ajax({
						url: '/selectScheduleListAjax.do',
						type: 'POST',
						success: function(result) {
							var voList = JSON.parse(result).eventList;
		        			var eventsList = [];
		        			voList.forEach(function(item) {
		        				eventsList.push(item);
		        			});
		        			successCallback(eventsList);
						}
					});
				},
	        }],
	        eventTimeFormat: { // 예 : '14:30' 형식으로 시간표시됨
	            hour: '2-digit',
	            minute: '2-digit',
	            meridiem: false
	        },
	        eventClick: function(info) {
	        	
	        	// event의 info 객체에서 이벤트의 속정 정보 가져오기
	        	var cid = info.event.id;
	        	var start = info.event.start + ' ';
	        	var end = info.event.end + ' ';
	        	var title = info.event.title + ' ';
				var textColor = info.event.textColor;
				var backgroundColor = info.event.backgroundColor;
				var borderColor = info.event.borderColor;				
				var tmp = start.split(' ')[1] + ' ' + start.split(' ')[1] + ', ' + start.split(' ')[3] + ' ' + start.split(' ')[4];
				var date = new Date(tmp);
				var yyyy = date.getFullYear().toString();
				var mm = (date.getMonth() + 1).toString();
				var dd = date.getDate().toString();
				var dateStr = yyyy + '-' + (mm[1] ? mm : "0" + mm[0]) + "-" + (dd[1] ? dd : "0" + dd[0]);

	        	// 가져온 이벤트 정보 파싱하여 필요한 데이터 세팅
	        	var stime = start.split(' ')[4].split(':')[0] + ':' + start.split(' ')[4].split(':')[1];
	        	var etime = end.split(' ')[4].split(':')[0] + ':' + end.split(' ')[4].split(':')[1];
	        	var mname = title.substring(0,3);
	        	var tname = title.split('-')[1].substring(1,4);
	        	var mid = title.split('(')[1].split(')')[0];
	        	var tid = title.split(' ')[3].split('(')[1].split(')')[0];
	        	console.log(cid)
				console.log(mid);
				console.log(tid);
	        	// 이벤트 속성 값에 따라 수업일지를 조회하거나 수업 상세 정보 알림을 띄워준다
	        	if(backgroundColor == '#198595' || borderColor == '#198595') {	// 수업이 진행 중이거나 완료된 일정
	        		// Ajax로 해당 일정의 수업 일지 정보를 조회하여 존재 유무에 따라 알맞은 팝업 모달을 띄워준다
	        		selectDiaryAjax(cid, mid, tid, dateStr, stime);
	        	} else {		// 수업이 아직 예정 상태인 일정
	        		// 이벤트 클릭시 alert 발생
		        	Swal.fire({
		        		title: '수업정보',
		        		icon: 'info',
		        		width: '800px',
		        		showCloseButton: false,
		        		showCancelButton: false,
		        		confirmButtonText: '닫기',
		        		html: '<table cellpadding="20" cellspacing="0" style="margin-left:75px; margin-top:50px; margin-bottom:50px; border: 1px solid gray;"><thead style="border-bottom: 1px solid gray;"><tr><th style="border-right: 1px solid gray;">수강회원</th><th style="border-right: 1px solid gray;">수업강사</th><th>수업시간</th></tr></thead>' +
		        		      '<tbody><tr>' +
		        		      '<td style="border-right: 1px solid gray;">' + mname + '(회원등록번호 : ' + mid + ')' + '</td>' +
		        		      '<td style="border-right: 1px solid gray;">' + tname + '(트레이너ID : ' + tid + ')' + '</td>' +
		        		      '<td>' + stime + ' ~ ' + etime + '</td>' + 
		        		      '</tr></tbody></table>'
		        	});
	        	}	        		        		        	        	
			}
	      });
	    calendar.setOption('locale', 'ko');
        calendar.render();
	});
	
	// focus 들어가면 readonly로 속성 변경해서 입력 금지
	$(document).on("focusin", "#scheduleDate", function() {
		$(this).prop('readonly', true);  
	});

	$(document).on("focusout", "#scheduleDate", function() {
		$(this).prop('readonly', false); 
	});
	
	$(document).on("focusin", "#uscheduleDate", function() {
		$(this).prop('readonly', true);  
	});

	$(document).on("focusout", "#uscheduleDate", function() {
		$(this).prop('readonly', false); 
	});
	
	// scheduleDate DatePicker 설정
	$("#scheduleDate").datepicker({ 
		showOn: "focus", 
        dateFormat: "yy-mm-dd",             			// 날짜의 형식
        changeMonth: true,                  			// 월을 이동하기 위한 선택상자 표시여부
        changeYear: true,
        onClose: function( selectedDate ) {
        }
	});
	
	$("#uscheduleDate").datepicker({ 
		showOn: "focus", 
        dateFormat: "yy-mm-dd",             			// 날짜의 형식
        changeMonth: true,                  			// 월을 이동하기 위한 선택상자 표시여부
        changeYear: true,
        onClose: function( selectedDate ) {
        }
	});
	
	// modal 닫으면 입력 데이터 초기화
	$('.modal').on('hidden.bs.modal', function (e) {
	  $(this).find('form')[0].reset();
	});
	</script>
	
	<!-- Optional JavaScript -->
	<script src="js/schedule.js" type="text/javascript"></script>	
    <script src="js/popper.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
</body>
</html>
