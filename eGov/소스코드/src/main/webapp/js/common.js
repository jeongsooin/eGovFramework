// 패턴 체크 (길이, 문자, 숫자, 특수문자 포함여부 체크) 
function checkPattern(str, min) {  
	
	var pattern1 = /[0-9]/; 					//     숫자 포함 검사 정규식 
	var pattern2 = /[a-zA-Z]/; 					//   영문자 포함 검사 정규식 
	var pattern3 = /[~!@#$%^&*()_+|<>?:{}]/; 	// 특수문자 포함 검사 정규식 
	
	// 숫자 포함, 영문자 포함, 특수문자 미포함, 길이 제한 조건 체크
	if(!pattern1.test(str) || !pattern2.test(str) || !pattern3.test(str) || str.length < min) { 
		alert("비밀번호는 8자리 이상 문자, 숫자, 특수문자로 구성하여야 합니다."); 
		return false; 
	} else { 
		return true; 
	} 
}

//keyDown 이벤트 시, 숫자만 입력 가능하게 입력 제한(영문 입력 금지)
function numChk(event) {
	event = event || window.event;
	// 입력한 키의 keyCode를 얻어옴
	var keyID = (event.which) ? event.which : event.keyCode;
	// 숫자의 keyCode에 해당하면 함수 빠져나감, 숫자 이외의 키가 눌리면 false 리턴
	if ((keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105)
			|| keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39) {
		return;
	} else {
		return false;
	}
}

// keyUp 이벤트 시, 입력된 한글 문자 제거
function removeChar(event) {
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if (keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39) {
		return;
	} else {
		// 입력된 문자의 코드가 숫자가 아닌 값들은 ""로 바꿔서 제거
		event.target.value = event.target.value.replace(/[^0-9]/g, "");
		event.target.value = event.target.value.replace(/ /gi, "");
	}
}

//keyUp 이벤트 시, 입력된 공백 문자 제거
function removeSpace(event) {
	event = event || window.event;
	// 입력된 문자 중 공백 값을 ""로 바꿔서 제거 
	event.target.value = event.target.value.replace(/ /gi, "");
}

//트레이너 아이디 패턴 체크
function idCheck() {
	var pattern1 = /[0-9]/;				//   숫자 포함 검사 정규식 				
	var pattern2 = /[a-zA-Z]/; 			// 영문자 포함 검사 정규식 
	
	var tid = $("#trainerId").val();
	
	// 트레이너 아이디 값이 입력되지 않았거나 길이가 5미만이거나 정규식을 만족하지 않는 경우 경고창 발생후 false 리턴
	if(tid == null || tid.length < 5 || !pattern1.test(tid) || !pattern2.test(tid)) {
		alert("올바른 형식의 아이디를 입력하세요");
		return false;
	} else {
		// 조건 만족 시 아이디 조회하는 Ajax 실행
		selectTrainerIdAjax(tid);
	}
}

// 트레이너 아이디 중복체크 Ajax
function selectTrainerIdAjax(_tid) {
	
	// 검색할 트레이너 아이디
	var tid = _tid;
	
	// Ajax 실행
	$.ajax({
		url: '/selectTrainerIdAjax.do',
		type: 'POST',
		data: {
			'tid':tid 
		},
		success: function(data) {
			
			var result = JSON.parse(data);

			// message = yes : 사용가능, no: 중복된 아이디, fail: 조회 실패
			if(result.message == 'yes') {
				// 아이디 중복체크 체크박스를 Checked로 변경
				$("input:checkbox[id='idCheckBox']").attr("checked", true);
				$("#trainerId").prop("readonly", true);
				$("#idCheckBox").focusout();				
				alert("사용 가능한 아이디 입니다.");				
			} else {
				// 아이디 중복체크 체크박스 Checked를 해제
				$("input:checkbox[id='idCheckBox']").attr("checked", false);
				$("#idCheckBox").focusout();
				alert("사용할 수 없는 아이디 입니다.");
			}
		},
		error: function() {
			alert("아이디 조회에 실패하였습니다.");
		}
	});
}

// 수업일지 정보를 조회
function selectDiaryAjax(_cid, _mid, _tid, _date, _time) {
	
	var cid;
	var mid;
	var tid;
	var type = 0;		// 호출된 곳이 테이블이면 0, 달력이면 1
	
	if(_tid == 0 && _date == 0 && _time == 0) {
		// 조건 값 입력 없이 함수가 호출된 경우, 이미 조건값이 폼에 존재하므로 값을 가져온다
		// 수업일지를 조회할 조건값을 선언
		cid = $("#dcid").val();
		mid = $("#dmid").val();
		tid = $("#dtid").val();
		type = 0;
	} else {
		// 인자 값이 존재하면 달력에서 해당 함수를 호출하였으므로 인자 값으로 조건값을 세팅한다
		cid = _cid;
		mid = _mid;
		tid = _tid;
		type = 1;		
	}
	
	$.ajax({
		url: '/selectDiaryAjax.do',
		type: 'POST',
		data: {
			'cid':cid,
			'mid':mid,
			'tid':tid 
		},
		success: function(data) {

			var result = JSON.parse(data);
			if(result.message == 'yes') {				
				// 조회결과가 성공이고 달력에서 호출했을 경우 일지 내용을 폼에 세팅
				if(type == 1) {
					$("#dcid").val(_cid);
					$("#dmid").val(_mid);
					$("#dtid").val(_tid);
					$("#dclass_date").val(_date);
					$("#dclass_time").val(_time);
					$("#contents").val(result.contents);
				} else {
					// 조회결과가 성공이고 테이블에서 호출했을 경우 일지 내용을 폼에 세팅
					$("#contents").val(result.contents);
				}
				
				// 조회결과가 성공이면 등록된 일지가 있는 것이므로 수정버튼은 보이고 작성 버튼은 숨김
				$('#btn_submit_rewrite').show();
				$('#btn_submit_write').hide();
				
				// 수업일지 모달 창 오픈
				$('#diaryModal').modal('show');
			} else {
				// 조회결과가 성공이고 달력에서 호출했을 경우 일지 내용을 폼에 세팅
				if(type == 1) {
					$("#dcid").val(_cid);
					$("#dmid").val(_mid);
					$("#dtid").val(_tid);
					$("#dclass_date").val(_date);
					$("#dclass_time").val(_time);
					$("#contents").val(result.contents);
				} 
				
				// 조회결과가 없으면 처음 작성하는 것이므로 수정버튼은 숨기고 작성 버튼은 보임
				$('#btn_submit_rewrite').hide();
				$('#btn_submit_write').show();
				
				// 모달 창 오픈
				$('#diaryModal').modal('show');
			}
		},
		error: function() {
			alert("수업일지 조회에 실패하였습니다.");
		}
	});
}

// 수업일지 정보를 수정
function updateDiaryAjax() {
	
	// 수업일지 입력 폼에서 입력할 정보들을 읽고 선언
	var cid = $("#dcid").val();
	var mid = $("#dmid").val();
	var tid = $("#dtid").val();
	var contents = $("#contents").val();
	
	// 일지 내용을 입력하지 않았으면 Ajax 전송하지 않고 리턴
	if(contents == null || contents == '') {
		alert("일지 내용을 입력하세요");
		return false;
	}
	
	// 일지 정보가 있으면 Ajax 실행
	$.ajax({
		url: '/updateDiaryAjax.do',
		type: 'POST',
		data: {
			'cid':cid,
			'mid':mid,
			'tid':tid,
			'contents' : contents
		},
		success: function(data) {
			var result = JSON.parse(data);
			if(result.message == 'yes') {
				// 수정 성공 시 메시지 세팅
				$('#diaryModal').modal('hide');
				alert("수정되었습니다.");
			} else {
				// 수정 실패 시 메시지 세팅
				alert("수업일지 수정에 실패하였습니다.");
			}
		},
		error: function() {
			alert("수업일지 수정에 실패하였습니다.");
		}
	});
}

// 수업일지 정보 삭제(사용 안함)
function deleteDiaryAjax() {
	
	// 수업일지 입력 폼에서 삭제할 일지의 정보를 읽고 선언
	var cid = $("#dcid").val();
	var mid = $("#dmid").val();
	var tid = $("#dtid").val();
	
	// 확인창 띄운 후 [취소] 누르면 Ajax 실행하지 않고 리턴
	if(!confirm("정말로 삭제하시겠습니까?")) {
		return false;
	}
	
	// [확인] 누르면 Ajax 전송
	$.ajax({
		url: '/deleteDiaryAjax.do',
		type: 'POST',
		data: {
			'cid':cid,
			'mid':mid,
			'tid':tid
		},
		success: function(data) {
			var result = JSON.parse(data);
			if(result.message == 'yes') {
				// 삭제 성공 메세지 세팅
				$('#diaryModal').modal('hide');
				alert("삭제되었습니다.");
			} else {
				// 삭제 실패 메시지 세팅
				alert("수업일지 삭제에 실패하였습니다.");
			}
		},
		error: function() {
			alert("수업일지 삭제에 실패하였습니다.");
		}
	});
}

// 수업일정 정보 삭제
function deleteScheduleAjax() {
	
	// 삭제할 수업의 아이디를 읽고 선언
	var cid = $("#pk").val();
	
	// 확인창 띄운 후 [취소] 누르면 Ajax 실행하지 않고 리턴
	if(!confirm("정말로 삭제하시겠습니까?")) {
		return false;
	}
	
	// [확인] 누르면 Ajax 전송
	$.ajax({
		url: '/deleteScheduleAjax.do',
		type: 'POST',
		data: {
			'cid':cid
		},
		success: function(data) {			
			var result = JSON.parse(data);
			// 삭제 성공 메세지 세팅
			if(result.message == 'yes') {
				alert("삭제되었습니다.");
				location.href="/scheduleView.do";				
			} else if(result.message == 'end'){	
				// 삭제 불가 메시지 세팅
				alert("완료된 수업은 삭제할 수 없습니다.");
				location.href="/scheduleView.do";
			} else {
				// 삭제 실패 메시지 세팅
				alert("수업일정 삭제에 실패하였습니다.");
				location.href="/scheduleView.do";
			}
		},
		error: function() {
			alert("수업일정 삭제에 실패하였습니다.");
			location.href="/scheduleView.do";
		}
	});
}

// 회원 정보 삭제
function deleteMemberAjax() {
	// 삭제할 회원의 아이디를 읽고 선언
	var mid = $("#pk").val();
	// 확인창 띄운 후 [취소] 누르면 Ajax 실행하지 않고 리턴
	if(!confirm("정말로 삭제하시겠습니까?")) {
		return false;
	}
	// [확인] 누르면 Ajax 전송
	$.ajax({
		url: '/deleteMemberAjax.do',
		type: 'POST',
		data: {
			'mid':mid
		},
		success: function(data) {			
			var result = JSON.parse(data);
			// 삭제 성공 메세지 세팅
			if(result.message == 'yes') {
				alert("삭제되었습니다.");
				location.href="/memberView.do";
			// 삭제 실패 메시지 세팅	
			} else if(result.message == 'schedule') {
				alert('배정된 수업이 존재합니다.');
				location.href="/memberView.do";
			} else if(result.message == 'count') {
				alert("잔여 수업횟수가 존재합니다.");
				location.href="/memberView.do";
			} else if(result.message == 'locker') {
				alert("배정된 사물함이 존재합니다.");
				location.href="/memberView.do";
			} else {				
				alert("삭제에 실패하였습니다.");
				location.href="/memberView.do";
			}
		},
		error: function() {
			alert("삭제에 실패하였습니다.");
			location.href="/memberView.do";
		}
	});
}

// 트레이너 정보 삭제
function deleteTrainerAjax() {
	// 삭제할 트레이너의 아이디를 읽고 선언
	var tid = $("#pk").val();
	// 확인창 띄운 후 [취소] 누르면 Ajax 실행하지 않고 리턴
	if(!confirm("정말로 삭제하시겠습니까?")) {
		return false;
	}
	// [확인] 누르면 Ajax 전송
	$.ajax({
		url: '/deleteTrainerAjax.do',
		type: 'POST',
		data: {
			'tid':tid
		},
		success: function(data) {			
			var result = JSON.parse(data);
			// 삭제 성공 메세지 세팅
			if(result.message == 'yes') {
				alert("삭제되었습니다.");
				location.href="/trainerView.do";
			// 삭제 실패 메시지 세팅
			} else if(result.message == 'schedule') {
				alert('배정된 수업이 존재합니다.');
				location.href="/trainerView.do";
			} else {				
				alert("삭제에 실패하였습니다.");
				location.href="/trainerView.do";
			}
		},
		error: function() {
			alert("삭제에 실패하였습니다.");
			location.href="/trainerView.do";
		}
	});
}

// 사물함 배정 정보 초기화
function deleteLockerAjax() {
	// 삭제할 사물함의 번호를 읽고 선언
	var lid = $("#pk").val();
	// 확인창 띄운 후 [취소] 누르면 Ajax 실행하지 않고 리턴
	if(!confirm("정말로 삭제하시겠습니까?")) {
		return false;
	}
	// [확인] 누르면 Ajax 전송
	$.ajax({
		url: '/deleteLockerAjax.do',
		type: 'POST',
		data: {
			'lid':lid
		},
		success: function(data) {			
			var result = JSON.parse(data);
			// 삭제 성공 메세지 세팅
			if(result.message == 'yes') {
				alert("삭제되었습니다.");
				location.href="/lockerView.do";				
			} else {
				// 삭제 실패 메시지 세팅
				alert("삭제에 실패하였습니다.");
				location.href="/lockerView.do";
			}
		},
		error: function() {
			alert("삭제에 실패하였습니다.");
			location.href="/lockerView.do";
		}
	});
}

//테이블 row 클릭 시 해당 row의 data 값으로 수정할 폼 데이터 세팅
function setUpdateValue(data) {
	// row 데이터에서 값 얻어오기
	var mid = data[0][1];
	var name = data[0][2];
	var telno = data[0][3];
	var telno1 = telno.slice(3, 7);
	var telno2 = telno.slice(7, 11)
	var gen = data[0][4]
    var zipcode = data[0][5];
    var loadAdr = data[0][6];
    var detAdr = data[0][7];
    var mbrsStart = data[0][8];
    var mbrsEnd = data[0][9];
    var ptCount = data[0][10];
    
    // row 데이터에서 얻은 값으로 폼 데이터 세팅
    $('#umemberId').val(mid);
    $('#umemberName').val(name);
    $('#umemberTel2').val(telno1);
    $('#umemberTel3').val(telno2);
    $("#umemberGen").val(gen).prop("selected", true);
    $('#uzipcode').val(zipcode);
    $('#uloadAddress').val(loadAdr);
    $('#udetailAddress').val(detAdr);
    $('#umbrsStart').val(mbrsStart);
    $('#umbrsEnd').val(mbrsEnd);
    $('#umemberPT').val(ptCount);

}

//테이블 row 클릭 시 해당 row의 data 값으로 수정할 폼 데이터 세팅
function setDeleteValue(data) {
	// row 데이터에서 값 얻어오기
	var mid = data[0][0];
	$('#pk').val(mid);
}

//테이블 row 선택했을 시 회원정보 수정 모달을 오픈
function popUpOpen() {
	$('#memberUpdateModal').modal('show');
}

//테이블 row 선택하지 않았을 시 alert 발생
function popUpAlert() {
	alert("회원 정보를 선택하세요.");	
}

//[삭제] 버튼 누르면 삭제할 데이터 전송
function submitDelete() {
	$('#search-form').submit();
}

// 등록시 실행될 함수
function formAction() {
	// validate 성공 시 submit hangler 실행
	$('insertForm').submit();
}

//근무 시간 select box option 동적으로 생성
function timeChange() {
	
	// 근무 시간 option 배열 선언	
	var times = ["00:00 08:00", "00:30 08:30", "01:00 09:00", "01:30 09:30", "02:00 10:00", "02:30 10:30", "03:00 11:00", "03:30 11:30", 
		 		 "04:00 12:00", "04:30 12:30", "05:00 13:00", "05:30 13:30", "06:00 14:00", "06:30 14:30", "07:00 15:00", "07:30 15:30",
		 		 "08:00 16:00", "08:30 16:30", "09:00 17:00", "09:30 17:30", "10:00 18:00", "10:30 18:30", "11:00 19:00", "11:30 19:30",
		 		 "12:00 20:00", "12:30 20:30", "13:00 21:00", "13:30 21:30", "14:00 22:00", "14:30 22:30", "15:00 23:00", "15:30 23:30",
		 		 "16:00 00:00", "16:30 00:30", "17:00 01:00", "17:30 01:30", "18:00 02:00", "18:30 02:30", "19:00 03:00", "19:30 03:30",
		 		 "20:00 04:00", "20:30 04:30", "21:00 05:00", "21:30 05:30", "22:00 06:00", "22:30 06:30", "23:00 07:00", "23:30 07:30"];
	
	// 근무 시간 select box 초기화
	$('#work_time').empty();
	$('#uwork_time').empty();
	
	// 근무 시간 select box에 option 동적으로 추가
	for(var i = 0; i < times.length; i++) {
		// 시작시간 ~ 종료시간 형식으로 표시하기 위해 문자열을 나눔
		var start = times[i].slice(0,5);
		var end   = times[i].slice(6);
		$('<option value="'+ i +'">' + start + ' ~ '+ end + '</option>').appendTo('#work_time');
		$('<option value="'+ i +'">' + start + ' ~ '+ end + '</option>').appendTo('#uwork_time');
	}
}

//수업일정 등록 모달에서 시간조회 시 값들이 유효한지 체크
function timeCheck() {
	// 조회에 쓰일 값들을 읽고 선언
	var mid = $("#scheduleMember").val();
	var tid = $("#scheduleTrainer").val();
	var class_date = $("#scheduleDate").val();
	
	// 조회 조건 값 중 입력되지 않은 것이 있으면 Ajax 실행하지 않고 리턴
	if(mid == null || mid == '') {
		alert('회원을 선택해주세요');
		return;
	} else if(tid == null || tid == '') {
		alert('트레이너를 선택해주세요');
		return;
	} else if(class_date == null || class_date == '') {
		alert('수업날짜를 선택해주세요');
		return;
	} else {
		// 모든 조회 값들이 유효하면 Ajax 실행
		selectScheduleTimeListAjax();
	}
}

//수업일지 등록 모달에서 수업 가능 시간 조회
function selectScheduleTimeListAjax() {
	// 조회에 쓰일 조건 값들을 읽고 선언
	var mid = $("#scheduleMember").val();
	var tid = $("#scheduleTrainer").val();
	var class_date = $("#scheduleDate").val();
	var class_type = $("#scheduleType").val();
	var timeList = [];
	
	// 수업시간 select box 초기화
	$('#scheduleTime').empty();
	
	// Ajax 전송
	$.ajax({
		url: '/selectScheduleTimeListAjax.do',
		type: 'POST',
		data: {
			'mid':mid,
			'tid':tid,
			'class_date':class_date,
			'class_type':class_type
		},
		success: function(data) {
			var result = JSON.parse(data);
			if(result.message == 'yes') {
				// 조회 결과가 성공이면 수업 시간 목록을 받아서 선언
				timeList = result.timeList;
				// 수업 시간 목록을 차례로 select box에 option 값으로 추가
				$.each(timeList, function(index, value) {
					console.log(index + " : " + value);
					$('<option value="'+ value +'">' + value + '</option>').appendTo('#scheduleTime');
				});
				$("input:checkbox[id='timeCheckBox']").attr("checked", true);
				$("#timeCheckBox").focusout();
			} else {
				// 조회 실패 메시지 세팅
				alert("조회에 실패하였습니다.");
				$("input:checkbox[id='timeCheckBox']").attr("checked", false);
				$("#timeCheckBox").focusout();
			}
		},
		error: function() {
			alert("조회에 실패하였습니다.");
			$("input:checkbox[id='timeCheckBox']").attr("checked", false);
			$("#timeCheckBox").focusout();
		}
	});
}

// 수업일정 수정 모달에서 시간조회 시 값들이 유효한지 체크
function utimeCheck() {
	
	// 조회에 쓰일 값들을 읽고 선언
	var mid = $("#uscheduleMember").val();
	var tid = $("#uscheduleTrainer").val();
	var class_date = $("#uscheduleDate").val();
	
	// 조회 조건 값 중 입력되지 않은 것이 있으면 Ajax 실행하지 않고 리턴
	if(mid == null || mid == '') {
		alert('회원을 선택해주세요');
		return;
	} else if(tid == null || tid == '') {
		alert('트레이너를 선택해주세요');
		return;
	} else if(class_date == null || class_date == '') {
		alert('수업날짜를 선택해주세요');
		return;
	} else {
		// 모든 조회 값들이 유효하면 Ajax 실행
		uselectScheduleTimeListAjax();
	}
}

// 수업일지 수정 모달에서 수업 가능 시간 조회
function uselectScheduleTimeListAjax() {
	
	// 조회에 쓰일 조건 값들을 읽고 선언
	var mid = $("#uscheduleMember").val();
	var tid = $("#uscheduleTrainer").val();
	var class_date = $("#uscheduleDate").val();
	var class_type = $("#uscheduleType").val();
	var timeList = [];
	// 수업시간 select box 초기화
	$('#uscheduleTime').empty();
	
	// Ajax 전송
	$.ajax({
		url: '/selectScheduleTimeListAjax.do',
		type: 'POST',
		data: {
			'mid':mid,
			'tid':tid,
			'class_date':class_date,
			'class_type':class_type
		},
		success: function(data) {
			var result = JSON.parse(data);
			if(result.message == 'yes') {
				// 조회 결과가 성공이면 수업 시간 목록을 받아서 선언
				timeList = result.timeList;
				// 수업 시간 목록을 차례로 select box에 option 값으로 추가
				$.each(timeList, function(index, value) {
					console.log(index + " : " + value);
					$('<option value="'+ value +'">' + value + '</option>').appendTo('#uscheduleTime');
					$("input:checkbox[id='utimeCheckBox']").attr("checked", true);
					$("#utimeCheckBox").focusout();
				});
			} else {
				// 조회 실패 메시지 세팅
				alert("조회에 실패하였습니다.");
				$("input:checkbox[id='utimeCheckBox']").attr("checked", false);
				$("#utimeCheckBox").focusout();
			}
		},
		error: function() {
			alert("조회에 실패하였습니다.");
			$("input:checkbox[id='utimeCheckBox']").attr("checked", false);
			$("#utimeCheckBox").focusout();
		}
	});
}