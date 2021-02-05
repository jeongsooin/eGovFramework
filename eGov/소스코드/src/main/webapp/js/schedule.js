// 테이블 row 클릭 시 해당 row의 data 값으로 수정할 폼 데이터 세팅
function setUpdateValue(data) {
	// row 데이터에서 값 얻어오기
	var cid = data[0][0]
	var mid = data[0][1];
	var tid = data[0][3];
	var class_date = data[0][5];
	var class_time = data[0][6];

	// row 데이터에서 얻은 값으로 폼 데이터 세팅
	$('#uscheduleId').val(cid);
	$('#uscheduleMember').val(mid);
	$('#uscheduleTrainer').val(tid);
	$('#uscheduleDate').val(class_date);
	$("#uscheduleTime").val(class_time);

}
// 테이블 row 클릭 시 해당 row의 data 값으로 수정할 폼 데이터 세팅
function setDeleteValue(data) {
	// row 데이터에서 값 얻어오기
	var cid = data[0][0];
	$('#pk').val(cid);
}

// 테이블 row 선택했을 시 수업일정 수정 모달을 오픈
function popUpOpen() {
	$('#scheduleUpdateModal').modal('show');
}

// 테이블 row 선택하지 않았을 시 alert 발생
function popUpAlert() {
	alert("수업일정을 선택하세요.");
}

// [삭제] 버튼 누르면 삭제할 데이터 전송
function submitDelete() {
	$('#search-form').submit();
}

// [달력보기] 버튼 클릭 시 테이블 토글
function toggleView() {
	$("#listTable_wrapper").toggle();
	$("#calendar_div").toggle();
}