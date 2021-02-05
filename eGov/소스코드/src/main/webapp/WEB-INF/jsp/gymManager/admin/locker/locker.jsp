<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>LOCKER</title>
	
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    
    <!-- Custom CSS -->    
    <link rel="stylesheet" href="css/modal.css">
    <link rel="stylesheet" href="css/button.css">
    <link rel="stylesheet" href="css/listTable.css">
    <style type="text/css">
    #locker-menu {
		background-color: white;
		color : #138496;
		border-radius: 10px;
		font-weight: bold;
	}    
    </style>
    <!-- Script -->	    
    <script src="js/common.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/gymManager/cmm/navbar.jsp"></jsp:include>
	
	<!-- Contents 영역 START -->
	<div class="main-content">
		<!-- 상단 버튼 영역 START -->
		<div class="button-area">
			<form id="search-form" class="form-inline">
				 <input type="text" class="form-control mb-2 mr-sm-2" id="pk" hidden>
				 <button class="btn btn btn btn-dark" id="search-button" hidden></button>
				 <p class="button-group"><a href="javascript:changeTable();" class="btn btn btn-dark" id="member-search">▤</a></p>
				 <p class="button-group"><a href="javascript:popUpAlert();"  class="btn btn btn-dark" id="update-button">배정</a></p>
				 <p class="button-group"><a href="javascript:popUpAlert();"  class="btn btn btn-dark" id="delete-button">배정삭제</a></p>
			</form>
		</div>
		<!-- 상단 버튼 영역 END -->
				
		<!-- 중단 테이블 영역 START -->
		<table id="listTable" class="ui celled table" style="width:100%">
			<thead style='display:none;'>
            <tr>
                <th>　</th>
                <th>　</th>
                <th>　</th>
                <th>　</th>
                <th>　</th>
                <th>　</th>
                <th>　</th>
                <th>　</th>
                <th>　</th>
                <th>　</th>
            </tr>
	        </thead>
	        <tbody>
	        	<c:set var="i" value="0"/>
	        	<c:set var="j" value="10"/>
	        	<c:forEach items="${list}" var="vo" varStatus="status">
	        		<c:if test="${i%j == 0}">
 						<tr>
 					</c:if>
			        <c:choose>
				        <c:when test="${vo.mid == 0}">
				        	<td align="center">
				        		<c:choose>
				        		<c:when test="${vo.lid < 10 }">
					        		<button class="btn btn-secondary loc" id="L${0}${vo.lid}" value="${vo.lid} ${vo.mid} ${vo.start_dtm} ${vo.end_dtm} ${names[status.index]}">
					        			<c:out value="${0}${vo.lid}"></c:out> 번
					        		</button>					        		
				        		</c:when>
				        		<c:otherwise>
				        			<button class="btn btn-secondary loc" id="L${vo.lid}" value="${vo.lid} ${vo.mid} ${vo.start_dtm} ${vo.end_dtm} ${names[status.index]}">
					        			<c:out value="${vo.lid}"></c:out> 번
					        		</button>
				        		</c:otherwise>
				        		</c:choose>
				        	</td>
				        </c:when>
				        <c:otherwise>
				        	<td align="center">
				        		<c:choose>
				        		<c:when test="${vo.lid < 10 }">
					        		<button class="btn btn-info loc" id="L${0}${vo.lid}" value="${vo.lid} ${vo.mid} ${vo.start_dtm} ${vo.end_dtm} ${names[status.index]}">
					        			<c:out value="${0}${vo.lid}"></c:out> 번
					        		</button>
					        		<p hidden>${vo.mid}</p>
				        		</c:when>
				        		<c:otherwise>
				        			<button class="btn btn-info loc" id="L${vo.lid}" value="${vo.lid} ${vo.mid} ${vo.start_dtm} ${vo.end_dtm} ${names[status.index]}">
					        			<c:out value="${vo.lid}"></c:out> 번
					        		</button>
					        		<p hidden>${vo.mid}</p>
				        		</c:otherwise>
				        		</c:choose>
				        	</td>
				        </c:otherwise>
				    </c:choose>
				    <c:if test="${i%j == j-1}">
 						</tr>
 					</c:if>
 					<c:set var="i" value="${i+1}"/>
		        </c:forEach>
	        </tbody>
		</table>
		<!-- 중단 테이블 영역 END -->
		
		<!-- 사물함 정보 테이블 START -->
		<table id="infoTable" class="ui celled table" style="width:100%">
			<thead>
            <tr>
                <th>   사물함   </th>
                <th>  회원번호  </th>
                <th>  회원이름  </th>
                <th>이용시작날짜</th>
                <th>이용만료날짜</th>
            </tr>
	        </thead>
	        <tbody>
	        <c:forEach items="${list}" var="vo" varStatus="status">
	        	<tr>
	        		<td><c:out value="${vo.lid}"></c:out></td>
	        		<c:choose>
		        		<c:when test="${vo.mid == 0}">
			            	<td>-</td>
			            	<td>-</td>
			            	<td>-</td>
			            	<td>-</td>
						</c:when>
						<c:otherwise>
			            	<td><c:out value="${vo.mid}"></c:out></td>
			            	<td><c:out value="${names[status.index]}"></c:out></td>
			            	<td><c:out value="${vo.start_dtm}"></c:out></td>
			            	<td><c:out value="${vo.end_dtm}"></c:out></td>
			            </c:otherwise>
		            </c:choose>
		        </tr>
	        </c:forEach>
	        </tbody>
		</table>
		<!-- 사물함 정보 테이블 END  -->				
	</div>
	<!-- Contents 영역 END -->
	
	<!-- 사물함 배정 모달 START -->
	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="updateModal" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="updateModal">사물함 정보</h5>
	      </div>
	      <div class="modal-body">
	        <form id="updateForm" class="needs-validation" method="POST" action="/updateLocker.do" novalidate>
			  <div class="form-row frow">
			    <div class="col-md-2 mb-3">
			      <label for="ulid">사물함번호</label>
			      <input type="text" class="form-control" id="ulid" name="lid" placeholder="사물함번호" onfocus="this.blur()" readonly>
			    </div>
			    <div class="col-md-5 mb-3">
			      <label for="umid">회원번호<span style="color:red"> *</span></label>
			      <input type="text" class="form-control" id="umid" name="mid" placeholder="아래 목록에서 선택하세요" onfocus="this.blur()" readonly required>
			    </div>
			    <div class="col-md-5 mb-3">
			      <label for="umid">회원이름</label>
			      <input type="text" class="form-control" id="umname" name="name" placeholder="아래 목록에서 선택하세요" onfocus="this.blur()" readonly required>
			    </div>
			  </div>
			  <div class="form-group" id="idList">
			  	<table id="grid-selection" class="table table-condensed table-hover">
			  		<thead>
			  			<tr>
			  				<th data-column-id="id"   data-identifier="true" >등록번호</th>
			  				<th data-column-id="name" data-identifier="false">회원이름</th>
			  			</tr>
			  		</thead>
			  		<tbody>
			  			<c:forEach items="${members}" var="vo">
			  				<tr>
			  					<td><c:out value="${vo.mid}"></c:out></td>
			  					<td><c:out value="${vo.name}"></c:out></td>
			  				</tr>
			  			</c:forEach>
			  		</tbody>
			  	</table>	
			  </div>
			  <div class="form-row frow">
			    <div class="col-md-6 mb-3">
			      <label for="ustart_dtm">이용시작일<span style="color:red"> *</span></label>
			      <input type="text" class="form-control datepicker" id="ustart_dtm" name="start_dtm" readonly required>
			    </div>
			    <div class="col-md-6 mb-3">
			      <label for="uend_dtm">이용종료일<span style="color:red"> *</span></label>
			      <input type="text" class="form-control datepicker" id="uend_dtm" name="end_dtm" readonly required>
			    </div>
			  </div>
			  <hr>
			  <div id="form-button-group" align="right">
	        	<button type="button" class="btn btn-outline-dark" data-dismiss="modal">취소</button>
	        	<button type="submit" class="btn btn-dark" id="btn_submit_update">배정</button>
	        	<a type="button" href="javascript:deleteLockerAjax();" class="btn btn-dark" id="btn_submit_delete" style='display:none;'>배정삭제</a>
	      	</div>
			</form>
	      </div>     
	    </div>
	  </div>
	</div>
	<!-- 사물함 수정 모달 END   -->
	
	<script>
	$(document).ready(function() {		
		// DataTable 설정 세팅
	    var table = $('#listTable').DataTable({
	    	// 언어 설정
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
	    	// 표시 건수 사용 안함
	    	lengthChange : false,
	    	// 검색 기능 사용 안함
	    	searching: false,
	    	// 정렬 기능 사용 안함
	    	ordering: false,
	    	// 정보 표시 숨기기
	    	info : false,
	    	// 페이징 사용 안함
	    	paging: false,
	    	// select 할 대상
	    	select : {
	    		items: 'cell'
	    	}
		});
		
	  	var infoTable = $('#infoTable').DataTable({
	    	// 언어 설정
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
	    	// 표시 건수 
	    	lengthChange : false,
	    	// 검색 기능 
	    	searching: true,
	    	// 정렬 기능 
	    	ordering: false,
	    	// 정보 표시 
	    	info : false,
	    	// 페이징 사용 
	    	paging: true,
	    	// select 할 대상
	    	select : {
	    		items: 'row'
	    	}
		});
	  			
		// 테이블 cell 클릭 시 [수정] 및 [삭제] 버튼 실행 함수 세팅
	    $('#listTable tbody').on( 'click', 'td', function () {
	        if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	            // 선택된 cell이 없으면 [수정],[삭제] 버튼 비활성화
	            $('#update-button').attr('href','javascript:popUpAlert();');
	            $('#delete-button').attr('href','javascript:popUpAlert();');
	            console.log('비활성화');
	        } else {
	            table.$('td.selected').removeClass('selected');
	            $(this).addClass('selected');
	            // 선택된 cell이 있으면 [삭제] 버튼 활성화
	            $('#delete-button').attr('href','javascript:deleteLockerAjax();');
	            var data = table.cell('.selected').data();
	            setupDeleteInfo(data)
	        }
	    });
		
	 	// 테이블 row 클릭 시 [수정] 및 [삭제] 버튼 실행 함수 세팅
		// 테이블 row 클릭 시 [수정] 및 [삭제]할 데이터 세팅
	    $('#infoTable tbody').on( 'click', 'tr', function () {
	        if ( $(this).hasClass('selected') ) {
	            $(this).removeClass('selected');
	            // 선택된 row가 없으면 [수정],[삭제] 버튼 비활성화
	            $('#update-button').attr('href','javascript:popUpAlert();');
	            $('#delete-button').attr('href','javascript:popUpAlert();');
	        } else {
	            table.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	            // 선택된 row가 있으면 [수정],[삭제] 버튼 활성화
	            $('#update-button').attr('href','javascript:popUpOpen();');
	            $('#delete-button').attr('href','javascript:deleteLockerAjax();');
	            var data = infoTable.row(this).data();
	            setDeleteRowValue(data[0])
	            setUpdateRowValue(data) 
	        }
	    });
		
	 	// 마우스 오버된 컬럼에 하이라이트 적용
	    $('#infoTable tbody').on('mouseenter', 'td', function () {
	    	var colIdx = infoTable.cell(this).index().column;	    	 
            $(infoTable.cells().nodes()).removeClass('highlight');
            $(infoTable.column(colIdx).nodes()).addClass('highlight');
	    });
	 	
		// 사물함 버튼 클릭시 이벤트 처리 동작
		$(".loc").click(function() {
			
			// 선택한 사물함 버튼의 value 값을 가져와서 전달할 변수에 세팅
			var data = $(this).attr('value').split(' ');
			var lid = data[0];
			var mid = '';
			var startDtm = '';
			var endDtm = '';
			var name = '';
			// 사용자가 배정된 사물함일 경우 정보 값 세팅
			if(data[1] != 0) {
				mid = data[1];
				startDtm = data[2];
				endDtm = data[3];		
				name = data[4];
				$("#btn_submit_update").hide();
				$("#btn_submit_delete").show();
				$("#idList").hide();
			} 
			
			$('#ulid').val(lid);
			$('#pk').val(lid);
			$('#umid').val(mid);
			$('#umname').val(name);
			$('#ustart_dtm').val(startDtm);
			$('#uend_dtm').val(endDtm);
			$('#searchKey').val(lid);
			$('#updateModal').modal('show');
			$('#delete-button').attr('href','javascript:deleteLockerAjax();');
		});
		
		// DatePicker 사용한 input form validation 적용을 위해 focusout 시켜주기
	    $("#start_dtm").focusout();
		$("#end_dtm").focusout();
		$("#ustart_dtm").focusout();
		$("#uend_dtm").focusout();
		$('#infoTable_wrapper').hide();
		
		// 사물함 배정 모달에서 회원 목록 테이블 세팅
		$("#grid-selection").bootgrid({
			navigation: 1,
		    selection: true,
		    multiSelect: false,
		    rowSelect: true,
		    labels: {
		        noResults: "검색 결과가 없습니다",
		        search: "검색"
		    }
		}).on("selected.rs.jquery.bootgrid", function(e, rows) {
			
		    var rowIds = [];
		    var rowNames = [];
		    
		    rowIds.push(rows[0].id);
		    rowNames.push(rows[0].name);
		    
		    $("#umid").val(rowIds[0]);
		    $('#umname').val(rowNames[0]);
		    
		}).on("deselected.rs.jquery.bootgrid", function(e, rows) {
			$("#umid").val("");
		});
		
		// 수정 폼 Validatie 설정
		$("#updateForm").validate({
			debug : false,
			rules : {
				mid : {
					required : true,
					digits : true
				},
				start_dtm : {
					required : true,
					date : true
				},
				end_dtm : {
					required : true,
					date : true
				}
			}, 
			messages : {
				mid : {
					required : "회원번호를 선택해주세요",
					digits : "회원번호를 선택해주세요"
				},
				start_dtm : {
					required : "이용시작일자를 선택해주세요",
					date : "이용시작일자를 선택해주세요"
				},
				end_dtm : {
					required : "이용종료일자를 선택해주세요",
					date : "이용종료일자를 선택해주세요"
				}
			}
		});
		
	});
		
	// focus 들어가면 readonly로 속성 변경해서 입력 금지
	$(document).on("focusin", "#start_dtm", function() {
		$(this).prop('readonly', true);  
	});

	$(document).on("focusout", "#start_dtm", function() {
		$(this).prop('readonly', false); 
	});
	
	$(document).on("focusin", "#end_dtm", function() {
		$(this).prop('readonly', true);  
	});

	$(document).on("focusout", "#end_dtm", function() {
		$(this).prop('readonly', false); 
	});
	
	$(document).on("focusin", "#ustart_dtm", function() {
		$(this).prop('readonly', true);  
	});

	$(document).on("focusout", "#ustart_dtm", function() {
		$(this).prop('readonly', false); 
	});
	
	$(document).on("focusin", "#uend_dtm", function() {
		$(this).prop('readonly', true);  
	});

	$(document).on("focusout", "#uend_dtm", function() {
		$(this).prop('readonly', false); 
	});
		
	$("#ustart_dtm").datepicker({ 
	   showOn: "focus", 
       dateFormat: "yy-mm-dd",             						// 날짜의 형식
       changeMonth: true,                  						// 월을 이동하기 위한 선택상자 표시여부
       onClose: function( selectedDate ) {    
           // 시작일(fromDate) datepicker가 닫힐때
           // 종료일(toDate)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정
           $("#uend_dtm").datepicker( "option", "minDate", selectedDate );
       }
	});
	
	$("#uend_dtm").datepicker({ 
	  showOn: "focus", 
      dateFormat: "yy-mm-dd",
      changeMonth: true,
      onClose: function( selectedDate ) {
          // 종료일(toDate) datepicker가 닫힐때
          // 시작일(fromDate)의 선택할수있는 최대 날짜(maxDate)를 선택한 종료일로 지정 
          $("#ustart_dtm").datepicker( "option", "maxDate", selectedDate );
      }                 
	});
	
	// 모달이 닫힐 때 모달 폼의 데이터 리셋
	$('.modal').on('hidden.bs.modal', function (e) {
	  $(this).find('form')[0].reset();
	  $("#btn_submit_delete").hide();
	  $("#btn_submit_update").show();
	  $("#idList").show();
	});	
	</script>
	
	<!-- Optional JavaScript -->
	<script src="js/locker.js" type="text/javascript"></script>	
    <script src="js/popper.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
</body>
</html>
