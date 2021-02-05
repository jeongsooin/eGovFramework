// 테이블 row 선택했을 시 사물합 정보 수정 모달을 오픈
function popUpOpen() {
	$('#updateModal').modal('show');
}
// 테이블 row 선택하지 않았을 시 alert 발생
function popUpAlert() {
	alert("사물함을 선택하세요.");
}

// [자세히] 버튼 클릭 시 테이블 토글
function changeTable() {
	$("#listTable_wrapper").toggle();
	$("#infoTable_wrapper").toggle();
}

//테이블 row 선택 시 수정 모달 폼 데이터 세팅
function setUpdateRowValue(data) {
	var lid = data[0];
	var mid = data[1];
	var start_dtm = data[3];
	var end_dtm = data[4];
	
	$('#ulid').val(lid);
	$('#umid').val(mid);
	$('#ustart_dtm').val(start_dtm);
	$('#uend_dtm').val(end_dtm);
}

// 테이블 row 선택 시 삭제 데이터 세팅
function setDeleteRowValue(lid) {
	$('#pk').val(lid);
}

// 테이블 cell 선택 시 삭제 데이터 세팅
function setupDeleteInfo(data) {
	var lid = data.split('">')[1].split(' 번')[0] *1;
	$('#pk').val(lid);
}