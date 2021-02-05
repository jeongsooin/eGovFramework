<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>MEMBER</title>
	
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    
    <!-- Custom CSS -->    
    <link rel="stylesheet" href="css/modal.css">
    <link rel="stylesheet" href="css/button.css">
    <link rel="stylesheet" href="css/listTable.css">
    <style type="text/css">
    #member-menu {
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
				 <p class="button-group"><a class="btn btn btn-dark" id="insert-button" data-toggle="modal" data-backdrop="static" data-keyboard="false" href="#memberInsertModal">등록</a></p>
				 <p class="button-group"><a href="javascript:popUpAlert();" class="btn btn btn-dark" id="update-button">수정</a></p>
				 <p class="button-group"><a href="javascript:popUpAlert();" class="btn btn btn-dark" id="delete-button">삭제</a></p>
			</form>
		</div>
		<!-- 상단 버튼 영역 END -->
		
		<!-- 중단 테이블 영역 START -->
		<table id="listTable" class="ui celled table table-hover" style="width:100%">
			<thead>
            <tr>
            	<th></th>
                <th>  회원번호  </th>
                <th>  회원이름  </th>
                <th>  전화번호  </th>
                <th>    성별    </th>
                <th>  우편번호  </th>
                <th> 도로명주소 </th>
                <th>  상세주소  </th>
                <th>이용시작일자</th>
                <th>이용만료일자</th>
                <th>개인수업횟수</th>
                <th>  사용여부  </th>
                <th>최초등록일자</th>
                <th>최초등록자ID</th>
                <th>최초등록자IP</th>
                <th>최종수정일자</th>
                <th>최종수정자ID</th>
                <th>최종수정자IP</th>
            </tr>
	        </thead>
	        <tbody>
	        	<c:forEach items="${list}" var="vo">
		            <tr>
		            	<td></td>
		                <td><c:out value="${vo.mid}"></c:out></td>
		                <td><c:out value="${vo.name}"></c:out></td>
		                <td><c:out value="${vo.telNo}"></c:out></td>
		                <td><c:out value="${vo.gen}"></c:out></td>
		                <td><c:out value="${vo.zip_cd}"></c:out></td>
		                <td><c:out value="${vo.load_adr}"></c:out></td>
		                <td><c:out value="${vo.det_adr}"></c:out></td>
		                <td><c:out value="${vo.mbrs_start}"></c:out></td>
		                <td><c:out value="${vo.mbrs_end}"></c:out></td>
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
	
	<!-- 회원 등록 폼 모달 START -->
	<div class="modal fade" id="memberInsertModal" tabindex="-1" role="dialog" aria-labelledby="insertModal" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="insertModal">신규 회원 등록</h5>
	      </div>
	      <div class="modal-body">
	        <form id="insertForm" class="needs-validation" method="POST" action="/memberInsert.do" novalidate>
			  <div class="form-row frow">
			    <div class="col-md-4 mb-3">
			      <label for="memberId">등록번호</label>
			      <input type="text" class="form-control" id="memberId" name="mid" placeholder="등록번호" onfocus="this.blur()" readonly>
			    </div>
			    <div class="col-md-8 mb-3">
			      <label for="memberName">이름<span style="color:red"> *</span></label>
			      <input type="text" class="form-control" id="memberName" name="name" placeholder="이름" onkeyup='removeSpace(event)' required>
			    </div>
			  </div>
			  <div class="form-row frow">
			    <div class="col-md-4 mb-3">
			      <label for="memberGen">성별<span style="color:red"> *</span></label>
			      <select id="inputState" class="form-control" id="memberGen" name="gen" required>
        			<option value="M" selected>남</option>
        			<option value="F">여</option>
      			  </select>
			    </div>
			    <div class="col-md-2 mb-3">
			      <label for="memberTel">연락처<span style="color:red"> *</span></label>
			      <input type="text" class="form-control" id="memberTel" name="tel0" value="010" readonly onfocus="this.blur()" required>
			    </div>
			    <div class="col-md-3 mb-3">
			      <label for="memberTel">　</label>
			      <input type="text" class="form-control" id="memberTel2" name="tel1" onkeydown='return numChk(event)' onkeyup='removeChar(event)' style='ime-mode:disabled;' maxlength="4" required>
			    </div>
			    <div class="col-md-3 mb-3">
			      <label for="memberTel">　</label>
			      <input type="text" class="form-control" id="memberTel3" name="tel2" onkeydown='return numChk(event)' onkeyup='removeChar(event)' style='ime-mode:disabled;' maxlength="4" required>
			    </div>
			  </div>
			  <div class="form-row">
			  	<div class="col-md-3 mb-3">
			      <label for="zipcode">주소<span style="color:red"> *</span></label>
			      <input type="text" id="zipcode" name="zip_cd" class="form-control" placeholder="우편번호" onfocus="this.blur()" required>
			    </div>
			    <div class="col-md-7 mb-3">
			      <label for="loadAddress">　</label>
			      <input type="text" id="loadAddress" name="load_adr" class="form-control" placeholder="도로명주소" onfocus="this.blur()" required>
			    </div>
			    <div class="col-md-2 mb-3">
			    	<label for="addressSearchButton">　</label>
			      <input type="button" id="addressSearchButton" name="button" class="form-control btn-outline-dark" onclick="searchAddress()" value="검색">
			    </div>			    
			  </div>
			  <div class="form-row frow">			  					  				    
			    <div class="col-md-12 mb-3">
			      <input type="text" id="detailAddress" name="det_adr" class="form-control" placeholder="상세주소">
			    </div>			  
			  </div>
			  <div class="form-row frow">
			    <div class="col-md-6 mb-3">
			      <label for="mbrsStart">이용시작일<span style="color:red"> *</span></label>
			      <input type="text" class="form-control datepicker" id="mbrsStart" name="mbrs_start" readonly required>
			    </div>
			    <div class="col-md-6 mb-3">
			      <label for="mbrsEnd">이용종료일<span style="color:red"> *</span></label>
			      <input type="text" class="form-control datepicker" id="mbrsEnd" name="mbrs_end" readonly required>
			    </div>
			  </div>
			  <div class="form-row frow">
			    <div class="col-md-6 mb-3">
			      <label for="memberPT">PT 횟수 </label>
			      <input type="number" class="form-control form-inline" id="memberPT" name="pt_count" placeholder="0" value="0" onkeydown='return numChk(event)' onkeyup='removeChar(event)' style='ime-mode:disabled;'>
			    </div>
			    <div class="col-md-6 mb-3">
			      	<label>　 </label>
			      	<br>회
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
	<!-- 회원 등록 폼 모달 END   -->
	
	<!-- 회원 수정 폼 모달 START -->
	<div class="modal fade" id="memberUpdateModal" tabindex="-1" role="dialog" aria-labelledby="updateModal" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="updateModal">회원 정보 수정</h5>
	      </div>
	      <div class="modal-body">
	        <form id="updateForm" class="needs-validation" method="POST" action="/memberUpdate.do" novalidate>
			  <div class="form-row frow">
			    <div class="col-md-4 mb-3">
			      <label for="umemberId">등록번호</label>
			      <input type="text" class="form-control" id="umemberId" name="mid" placeholder="등록번호" onfocus="this.blur()" readonly>
			    </div>
			    <div class="col-md-8 mb-3">
			      <label for="umemberName">이름<span style="color:red"> *</span></label>
			      <input type="text" class="form-control" id="umemberName" name="name" placeholder="이름" onkeyup='removeSpace(event)' required>
			    </div>
			  </div>
			  <div class="form-row frow">
			    <div class="col-md-4 mb-3">
			      <label for="umemberGen">성별<span style="color:red"> *</span></label>
			      <select class="form-control" id="umemberGen" name="gen" required>
        			<option value="M">남</option>
        			<option value="F">여</option>
      			  </select>
			    </div>
			    <div class="col-md-2 mb-3">
			      <label for="umemberTel">연락처<span style="color:red"> *</span></label>
			      <input type="text" class="form-control" id="umemberTel" name="tel0" value="010" readonly onfocus="this.blur()" required>
			    </div>
			    <div class="col-md-3 mb-3">
			      <label for="umemberTel">　</label>
			      <input type="text" class="form-control" id="umemberTel2" name="tel1" onkeydown='return numChk(event)' onkeyup='removeChar(event)' style='ime-mode:disabled;' maxlength="4" required>
			    </div>
			    <div class="col-md-3 mb-3">
			      <label for="umemberTel">　</label>
			      <input type="text" class="form-control" id="umemberTel3" name="tel2" onkeydown='return numChk(event)' onkeyup='removeChar(event)' style='ime-mode:disabled;' maxlength="4" required>
			    </div>
			  </div>
			  <div class="form-row">
			  	<div class="col-md-3 mb-3">
			      <label for="uzipcode">주소<span style="color:red"> *</span></label>
			      <input type="text" id="uzipcode" name="zip_cd" class="form-control" placeholder="우편번호" onfocus="this.blur()" required>
			    </div>
			    <div class="col-md-7 mb-3">
			      <label for="uloadAddress">　</label>
			      <input type="text" id="uloadAddress" name="load_adr" class="form-control" placeholder="도로명주소" onfocus="this.blur()" required>
			    </div>
			    <div class="col-md-2 mb-3">
			    	<label for="uaddressSearchButton">　</label>
			      <input type="button" id="uaddressSearchButton" name="button" class="form-control btn-outline-dark" onclick="searchAddress2()" value="검색">
			    </div>			    
			  </div>
			  <div class="form-row frow">			  					  				    
			    <div class="col-md-12 mb-3">
			      <input type="text" id="udetailAddress" name="det_adr" class="form-control" placeholder="상세주소">
			    </div>			  
			  </div>
			  <div class="form-row frow">
			    <div class="col-md-6 mb-3">
			      <label for="umbrsStart">이용시작일<span style="color:red"> *</span></label>
			      <input type="text" class="form-control datepicker" id="umbrsStart" name="mbrs_start" readonly required>
			    </div>
			    <div class="col-md-6 mb-3">
			      <label for="umbrsEnd">이용종료일<span style="color:red"> *</span></label>
			      <input type="text" class="form-control datepicker" id="umbrsEnd" name="mbrs_end" readonly required>
			    </div>
			  </div>
			  <div class="form-row frow">
			    <div class="col-md-6 mb-3">
			      <label for="umemberPT">PT 횟수 </label>
			      <input type="number" class="form-control" id="umemberPT" name="pt_count" placeholder="0" value="0" onkeydown='return numChk(event)' onkeyup='removeChar(event)' style='ime-mode:disabled;'> 
			    </div>
			    <div class="col-md-6 mb-3">
			      	<label>　 </label>
			      	<br>회
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
	<!-- 회원 수정 폼 모달 END   -->

	<script>
	$(document).ready(function() {	
		
		// DataTable 설정 세팅
		function format (data) {
    		return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'+
        			'<tr>' +
           			'<td>　 　회원 정보　 　</td>' +
            		'<td>' + data[0][2] + '(' + data[0][4] + ', 등록번호 ' + data[0][1] + ')' +'</td>'  +
        			'</tr>'+
        			'<tr>' +
            		'<td>　 　 연 락 처　 　</td>' +
            		'<td>' + data[0][3] + '</td>' +
        			'</tr>'+
        			'<tr>' +
            		'<td>　 　회원 주소　 　</td>' +
            		'<td>' + data[0][5] + ' ' +data[0][6] + ' ' + data[0][7] + '</td>' +
            		'</tr>'+
        			'<tr>' +
            		'<td>　 　이용 일자　 　</td>' +
            		'<td>' + data[0][8] + ' ~ ' + data[0][9] + '</td>' +
        			'</tr>'+
    				'</table>';
		}
		
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
	    	responsive : true,
	    	columns : [
	    		{
	    			"className" : "details-control",
	    			"orderalbe" : false,
	    			"data" : null,
	    			"defaultContent" : ""
	    		}
	    	],
	    	columnDefs : [
	    		{ targets: 11, visible: false },
	    		{ targets: 12, visible: false },
	    		{ targets: 13, visible: false },
	    		{ targets: 14, visible: false },
	    		{ targets: 15, visible: false },
	    		{ targets: 16, visible: false },
	    		{ targets: 17, visible: false }
	    	],
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
	            $('#update-button').attr('href','javascript:popUpAlert();');
	            $('#delete-button').attr('href','javascript:popUpAlert();');
	        } else {
	            table.$('tr.selected').removeClass('selected');
	            $(this).addClass('selected');
	            // 선택된 row가 있으면 [수정],[삭제] 버튼 활성화
	            $('#update-button').attr('href','javascript:popUpOpen();');
	            $('#delete-button').attr('href','javascript:deleteMemberAjax();');
	            var data = table.rows('.selected').data();
	            setUpdateValue(data);
	            setDeleteValue(data)
	        }
	    });
	 	
	 	// 마우스 오버된 컬럼에 하이라이트 적용
	    $('#listTable tbody').on('mouseenter', 'td', function () {
	    	var colIdx = table.cell(this).index().column;	    	 
            $(table.cells().nodes()).removeClass('highlight');
            $(table.column(colIdx).nodes()).addClass('highlight');
	    });
	 	
		var detailRows = [];
		
		$('#listTable tbody').on('click', 'tr td.details-control', function () {
	        
			var tr = $(this).closest('tr');						// 클릭한 cell 이 속한 행
	        var row = table.row(tr);							// 클릭한 cell 이 속한 행을 row로 선언
	        var idx = $.inArray(tr.attr('id'), detailRows);
	        var data = table.rows(row).data();

	        if (row.child.isShown()) {
	            tr.removeClass('details');
	            row.child.hide();
	 
	            // Remove from the 'open' array
	            detailRows.splice(idx, 1);
	        }
	        else {
	            tr.addClass('details');
	            row.child(format(data)).show();
	 
	            // Add to the 'open' array
	            if (idx === -1) {
	                detailRows.push(tr.attr('id'));
	            }
	        }
	    });
	 
	    // On each draw, loop over the `detailRows` array and show any child rows
	    table.on('draw', function () {
	        $.each( detailRows, function (i, id) {
	            $('#'+id+' td.details-control').trigger('click');
	        });
	    });
		
		
	    
		// DatePicker 사용한 input form validation 적용을 위해 focusout 시켜주기
	    $("#mbrsStart").focusout();
		$("#mbrsEnd").focusout();
		$("#umbrsStart").focusout();
		$("#umbrsEnd").focusout();

		// 입력 폼 Validatie 설정
		$("#insertForm").validate({
			debug : false,
			rules : {
				name : {
					required : true,
					rangelength : [2, 10]
				},
				pt_count : {
					number : true,
					digits : true
					
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
				zip_cd : {
					required : ""
				},
				load_adr : {
					required : "주소는 필수입니다"
				},
				mbrs_start : {
					required : "이용시작일자는 필수입니다"
				},
				mbrs_end : {
					required : "이용종료일자는 필수입니다"
				},
				pt_count : {
					digits : "0 이상의 숫자만 입력 가능합니다"
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
				},
				pt_count : {
					number : true,
					digits : true
					
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
				zip_cd : {
					required : ""
				},
				load_adr : {
					required : "주소는 필수입니다"
				},
				mbrs_start : {
					required : "이용시작일자는 필수입니다"
				},
				mbrs_end : {
					required : "이용종료일자는 필수입니다"
				},
				pt_count : {
					digits : "0 이상의 숫자만 입력 가능합니다"
				}
			}
		});
		
	});
					
	// focus 들어가면 readonly로 속성 변경해서 입력 금지
	$(document).on("focusin", ".datepicker", function() {
		$("#mbrsStart").prop('readonly', true); 
		$("#mbrsEnd").prop('readonly', true);  
		$("#umbrsStart").prop('readonly', true); 
		$("#umbrsEnd").prop('readonly', true);  
	});

	$(document).on("focusout", ".datepicker", function() {
		$("#mbrsStart").prop('readonly', false); 
		$("#mbrsEnd").prop('readonly', false); 
		$("#umbrsStart").prop('readonly', false); 
		$("#umbrsEnd").prop('readonly', false); 
	});
	
	$("#mbrsStart").datepicker({ 
		showOn: "focus", 
        dateFormat: "yy-mm-dd",             			// 날짜의 형식
        changeMonth: true,                  			// 월을 이동하기 위한 선택상자 표시여부
        changeYear: true,								// 연도를 이동하기 위한 선택상자 표시여부
        onClose: function( selectedDate ) {    
            // 시작일(fromDate) datepicker가 닫힐때
            // 종료일(toDate)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정
            $("#mbrsEnd").datepicker( "option", "minDate", selectedDate );
        }
	});
	
	$("#mbrsEnd").datepicker({ 
	   showOn: "focus", 
       dateFormat: "yy-mm-dd",							// 날짜의 형식
       changeMonth: true,								// 월을 이동하기 위한 선택상자 표시여부
       changeYear: true,								// 연도를 이동하기 위한 선택상자 표시여부
       onClose: function( selectedDate ) {
           // 종료일(toDate) datepicker가 닫힐때
           // 시작일(fromDate)의 선택할수있는 최대 날짜(maxDate)를 선택한 종료일로 지정 
           $("#mbrsStart").datepicker( "option", "maxDate", selectedDate );
       }                 
	});
	
	$("#umbrsStart").datepicker({ 
	   showOn: "focus", 
       dateFormat: "yy-mm-dd",							// 날짜의 형식           
       changeMonth: true,								// 월을 이동하기 위한 선택상자 표시여부                  
       changeYear: true,								// 연도를 이동하기 위한 선택상자 표시여부
       onClose: function( selectedDate ) {    
           // 시작일(fromDate) datepicker가 닫힐때
           // 종료일(toDate)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정
           $("#umbrsEnd").datepicker( "option", "minDate", selectedDate );
       }
	});
	
	$("#umbrsEnd").datepicker({ 
	  showOn: "focus", 
      dateFormat: "yy-mm-dd",							// 날짜의 형식
      changeMonth: true,								// 월을 이동하기 위한 선택상자 표시여부 
      changeYear: true,									// 연도를 이동하기 위한 선택상자 표시여부
      onClose: function( selectedDate ) {
          // 종료일(toDate) datepicker가 닫힐때
          // 시작일(fromDate)의 선택할수있는 최대 날짜(maxDate)를 선택한 종료일로 지정 
          $("#umbrsStart").datepicker( "option", "maxDate", selectedDate );
      }                 
	});
	
	// 모달이 닫힐 때 모달 폼의 데이터 리셋
	$('.modal').on('hidden.bs.modal', function (e) {
	  $(this).find('form')[0].reset();
	});
	</script>
	
	<!-- Optional JavaScript -->
    <script src="js/popper.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
</body>
</html>
