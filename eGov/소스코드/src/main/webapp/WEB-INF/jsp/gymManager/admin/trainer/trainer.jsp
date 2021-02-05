<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>TRAINER</title>
	
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    
    <!-- Custom CSS -->    
    <link rel="stylesheet" href="css/button.css">
    <link rel="stylesheet" href="css/modal.css">
    <link rel="stylesheet" href="css/listTable.css">
    <style type="text/css">
    #trainer-menu {
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
				 <p class="button-group"><a class="btn btn btn-dark" id="insert-button" data-toggle="modal" data-backdrop="static" data-keyboard="false" href="#trainerInsertModal">등록</a></p>
				 <p class="button-group"><a href="javascript:popUpAlert();" class="btn btn btn-dark" id="update-button">수정</a></p>
				 <p class="button-group"><a href="javascript:popUpAlert();" class="btn btn btn-dark" id="delete-button">삭제</a></p>
			</form>
		</div>
		<!-- 상단 버튼 영역 END -->
		
		<!-- 중단 테이블 영역 START -->
		<table id="listTable" class="ui celled table" style="width:100%">
			<thead>
            <tr>
                <th>   아이디   </th>
                <th>    이름    </th>              
                <th>  전화번호  </th>
                <th>    성별    </th>
                <th>근무시작시간</th>
                <th>근무종료시간</th>
                <th>개인수업횟수</th>
            </tr>
	        </thead>
	        <tbody>
	            <c:forEach items="${list}" var="vo">
		            <tr>
		                <td><c:out value="${vo.tid}"></c:out></td>
		                <td><c:out value="${vo.name}"></c:out></td>
		                <td><c:out value="${vo.telNo}"></c:out></td>
		                <td><c:out value="${vo.gen}"></c:out></td>		                		                
		                <td><c:out value="${vo.work_start}"></c:out></td>
		                <td><c:out value="${vo.work_end}"></c:out></td>
		                <td><c:out value="${vo.pt_count}"></c:out></td>		                
		                <td><c:out value="${vo.use_yn}"></c:out></td>
		                <td><c:out value="${vo.reg_dtm}"></c:out></td>
		                <td><c:out value="${vo.reg_usr_id}"></c:out></td>
		                <td><c:out value="${vo.reg_usr_ip}"></c:out></td>
		                <td><c:out value="${vo.modify_dtm}"></c:out></td>
		                <td><c:out value="${vo.modify_usr_id}"></c:out></td>
		                <td><c:out value="${vo.modify_usr_ip}"></c:out></td>
		            </tr>
	            </c:forEach>
	        </tbody>
		</table>
		<!-- 중단 테이블 영역 END --> 
	</div>
	<!-- Contents 영역 END -->
	
	<!-- 트레이너 등록 폼 모달 START -->
	<div class="modal fade" id="trainerInsertModal" tabindex="-1" role="dialog" aria-labelledby="insertModal" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="insertModal">신규 트레이너 등록</h5>
	      </div>
	      <div class="modal-body">
	        <form id="insertForm" action="insertTrainer.do" method="POST">
			  <div class="form-row frow">
			    <div class="col-md-6 mb-3">
			      <label for="tainerId">트레이너 ID<span style="color:red"> *</span></label>
			      <input type="text" class="form-control" id="trainerId" name="tid" placeholder="아이디" onkeyup='removeSpace(event)' required>
			    </div>
			    <div class="col-md-3 mb-3">
			    	<label for="idCheckButton">　</label>
			    	<input type="button" id="idCheckButton" name="button" class="form-control btn-outline-dark" onclick="idCheck()" value="중복확인">
			    	<label class="error" for="idCheckBox" style="display:none;"></label>
			    </div>
			    <div class="col-md-3 mb-3">
			    	<label for="idCheckBox">　</label>
			    	<input class="form-control form-check-input" type="checkbox" id="idCheckBox" name="idCheckBox" onclick="preventClick()" required>			    	
			    </div>				    
			  </div>
			  <div class="form-row frow">
			    <div class="col-md-4 mb-3">
			      <label for="trainerGen">성별<span style="color:red"> *</span></label>
			      <select class="form-control" id="tainerGen" name="gen" required>
        			<option value="M" selected>남</option>
        			<option value="F">여</option>
      			  </select>
			    </div>
			    <div class="col-md-8 mb-3">
			      <label for="trainerName">이름<span style="color:red"> *</span></label>
			      <input type="text" class="form-control" id="trainerName" name="name" placeholder="이름" onkeyup='removeSpace(event)' required>
			    </div>
			  </div>
			  <div class="form-row frow">
			    <div class="col-md-4 mb-3">
			      <label for="trainerTel">연락처<span style="color:red"> *</span></label>
			      <input type="text" class="form-control" id="trainerTel" name="tel0" value="010" readonly onfocus="this.blur()" required>
			    </div>
			    <div class="col-md-4 mb-3">
			      <label for="trainerTel2">　</label>
			      <input type="text" class="form-control" id="trainerTel2" name="tel1" onkeydown='return numChk(event)' onkeyup='removeChar(event)' style='ime-mode:disabled;' maxlength="4" required>
			    </div>
			    <div class="col-md-4 mb-3">
			      <label for="trainerTel3">　</label>
			      <input type="text" class="form-control" id="trainerTel3" name="tel2" onkeydown='return numChk(event)' onkeyup='removeChar(event)' style='ime-mode:disabled;' maxlength="4" required>
			    </div>
			  </div>
			  <div class="form-row frow">
			    <div class="col-md-4 mb-3">
			      <label for="work_time">근무시간<span style="color:red"> *</span></label>
			      <select class="form-control" id="work_time" name="work_time" required ></select>
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
	<!-- 트레이너 등록 폼 모달 END   -->
	
	<!-- 트레이너 수정 폼 모달 START -->
	<div class="modal fade" id="trainerUpdateModal" tabindex="-1" role="dialog" aria-labelledby="updateModal" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="updateModal">트레이너 정보 수정</h5>
	      </div>
	      <div class="modal-body">
	        <form id="updateForm" class="needs-validation" action="/updateTrainer.do" method="POST" novalidate>
			  <div class="form-row frow">
			    <div class="col-md-6 mb-3">
			      <label for="utainerId">트레이너 ID<span style="color:red"> *</span></label>
			      <input type="text" class="form-control" id="utrainerId" name="tid" placeholder="아이디" readonly>
			    </div>    
			  </div>
			  <div class="form-row frow">
			    <div class="col-md-4 mb-3">
			      <label for="utrainerGen">성별<span style="color:red"> *</span></label>
			      <select class="form-control" id="utrainerGen" name="gen" required>
        			<option value="M">남</option>
        			<option value="F">여</option>
      			  </select>
			    </div>
			    <div class="col-md-8 mb-3">
			      <label for="utrainerName">이름<span style="color:red"> *</span></label>
			      <input type="text" class="form-control" id="utrainerName" name="name" placeholder="이름" onkeyup='removeSpace(event)' required>
			    </div>
			  </div>
			  <div class="form-row frow">
			    <div class="col-md-4 mb-3">
			      <label for="utrainerTel">연락처<span style="color:red"> *</span></label>
			      <input type="text" class="form-control" id="utrainerTel" name="tel0" value="010" readonly onfocus="this.blur()" required>
			    </div>
			    <div class="col-md-4 mb-3">
			      <label for="utrainerTel2">　</label>
			      <input type="text" class="form-control" id="utrainerTel2" name="tel1" onkeydown='return numChk(event)' onkeyup='removeChar(event)' style='ime-mode:disabled;' maxlength="4" required>
			    </div>
			    <div class="col-md-4 mb-3">
			      <label for="utrainerTel3">　</label>
			      <input type="text" class="form-control" id="utrainerTel3" name="tel2" onkeydown='return numChk(event)' onkeyup='removeChar(event)' style='ime-mode:disabled;' maxlength="4" required>
			    </div>
			  </div>
			  <div class="form-row frow">
			    <div class="col-md-4 mb-3">
			      <label for="uwork_time">근무시간<span style="color:red"> *</span></label>
			      <select class="form-control" id="uwork_time" name="work_time" required ></select>
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
	<!-- 트레이너 수정 폼 모달 END   -->

	<script>
	$(document).ready(function() {
		timeChange();
		
		$("#idCheckBox").change(function () {
			checkboxChanged();
		});
		
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
	            "loadingRecords": "로딩중",
	            "processing":     "잠시만 기다려 주십시오",
	            "paginate": {
	                "next": "다음",
	                "previous": "이전"
	            }
	    	},
	    	// 표시 건수 사용
	    	lengthChange : false,
	    	// 검색 기능 사용 안함
	    	searching: true,
	    	// 정보 표시 숨기기
	    	info : false,
	    	// column 속성 설정
	    	columnDefs : [
	    		{ targets: 7, visible: false },
	    		{ targets: 8, visible: false },
	    		{ targets: 9, visible: false },
	    		{ targets: 10, visible: false },
	    		{ targets: 11, visible: false },
	    		{ targets: 12, visible: false },
	    		{ targets: 13, visible: false }
	    	],
	    	// select 할 대상
	    	select : {
	    		items: 'row'
	    	}
		});
	    
		// 테이블 row 클릭 시 [수정] 및 [삭제] 버튼 실행 함수 및 데이터 세팅
	    $('#listTable tbody').on( 'click', 'tr', function () {
	        if ($(this).hasClass('selected')) {
	            $(this).removeClass('selected');
	            // 선택된 row가 없으면 [수정],[삭제] 버튼 비활성화
	            $('#update-button').attr('href','javascript:popUpAlert();');
	            $('#delete-button').attr('href','javascript:popUpAlert();');
	        } else {
	            table.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	            // 선택된 row가 있으면 [수정],[삭제] 버튼 활성화
	            $('#update-button').attr('href','javascript:popUpOpen();');
	            $('#delete-button').attr('href','javascript:deleteTrainerAjax();');
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
		
	    $.validator.addMethod( 
	    		"eng_number", 
	    		function(value, element) {
	    			return this.optional(element) || /^[a-zA-Z\d]+$/.test(value);
	    });

	    // 입력 폼 Validatie 설정
		$("#insertForm").validate({
			debug : false,
			ignore: [],
			rules : {
				name : {
					required : true,
					rangelength : [2, 10]
				},
				tid : {
					required : true,
					rangelength : [5, 20],
					eng_number : true
				},
				idCheckBox : {
					required: true
				}
			}, 
			messages : {
				name : {
					required : "이름은 필수입니다",
					rangelength : "이름은 최소 2글자 이상 최대 10글자 이내 입니다"
				},
				tid : {
					required : "아이디 입력은 필수입니다",
					rangelength : "아이디는 5글자이상 20글자 이내여야 합니다",
					eng_number : "아이디는 영문자 또는 숫자만 사용가능합니다"
				},
				idCheckBox : {
					required : "중복확인 필수"
				},
				tel1 : {
					required : "연락처는 필수입니다"
				},
				tel2 : {
					required : "연락처는 필수입니다"
				},
				work_time : {
					required : "근무시간은 필수입니다"
				}
			}
		});
	    
		// 수정 폼 Validatie 설정
		$("#updateForm").validate({
			debug : false,
			rules : {
				name : {
					required : true,
					rangelength : [2, 10]
				}
			}, 
			messages : {
				name : {
					required : "이름은 필수입니다",
					rangelength : "이름은 최소 2글자 이상 최대 10글자 이내 입니다"
				},
				tel1 : {
					required : "연락처는 필수입니다"
				},
				tel2 : {
					required : "연락처는 필수입니다"
				},
				uwork_start : {
					required : "근무시작시간은 필수입니다"
				},
				work_time : {
					required : "근무시간은 필수입니다"
				}
			}
		});
		
		//체크박스 클릭 방지
		$('input[type="checkbox"]').click(function(event) {
		    var $checkbox = $(this);
		    event.preventDefault();
		    event.stopPropagation();
		});
	});
		
	// modal 닫으면 입력 데이터 초기화
	$('.modal').on('hidden.bs.modal', function (e) {
	  $(this).find('form')[0].reset();
	  $("#trainerId").prop("readonly", false);
	});	
	</script>
	
	<!-- Optional JavaScript -->
	<script src="js/trainer.js" type="text/javascript"></script>
    <script src="js/popper.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
</body>
</html>
