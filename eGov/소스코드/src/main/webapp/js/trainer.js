// 테이블 row 클릭 시 해당 row의 data 값으로 수정할 폼 데이터 세팅
function setUpdateValue(data) {
	
	// 근무 시간 option 배열 선언	
	var times = ["00:00 08:00", "00:30 08:30", "01:00 09:00", "01:30 09:30", "02:00 10:00", "02:30 10:30", "03:00 11:00", "03:30 11:30", 
		 		 "04:00 12:00", "04:30 12:30", "05:00 13:00", "05:30 13:30", "06:00 14:00", "06:30 14:30", "07:00 15:00", "07:30 15:30",
		 		 "08:00 16:00", "08:30 16:30", "09:00 17:00", "09:30 17:30", "10:00 18:00", "10:30 18:30", "11:00 19:00", "11:30 19:30",
		 		 "12:00 20:00", "12:30 20:30", "13:00 21:00", "13:30 21:30", "14:00 22:00", "14:30 22:30", "15:00 23:00", "15:30 23:30",
		 		 "16:00 00:00", "16:30 00:30", "17:00 01:00", "17:30 01:30", "18:00 02:00", "18:30 02:30", "19:00 03:00", "19:30 03:30",
		 		 "20:00 04:00", "20:30 04:30", "21:00 05:00", "21:30 05:30", "22:00 06:00", "22:30 06:30", "23:00 07:00", "23:30 07:30"];
	
	// row 데이터에서 값 얻어오기
	var tid = data[0][0];
	var name = data[0][1];
	var telno = data[0][2];
	var telno1 = telno.slice(3, 7);
	var telno2 = telno.slice(7, 11)
	var gen = data[0][3]
	var workStart = data[0][4];
	var workEnd = data[0][5];

	// row 데이터에서 얻은 값으로 폼 데이터 세팅
	$('#utrainerId').val(tid);
	$('#utrainerName').val(name);
	$('#utrainerTel2').val(telno1);
	$('#utrainerTel3').val(telno2);
	$("#utrainerGen").val(gen).prop("selected", true);
	
	for(var i = 0; i < times.length; i++) {
		if(times[i].slice(0,5) == workStart) {
			$("#uwork_time").val(i).prop("selected", true);
		}
	}
}

// 테이블 row 클릭 시 해당 row의 data 값으로 수정할 폼 데이터 세팅
function setDeleteValue(data) {
	// row 데이터에서 값 얻어오기
	var tid = data[0][0];
	console.log(tid);
	$('#pk').val(tid);
}

// 테이블 row 선택했을 시 트레이너 정보 수정 모달을 오픈
function popUpOpen() {
	$('#trainerUpdateModal').modal('show');
}

// 테이블 row 선택하지 않았을 시 alert 발생
function popUpAlert() {
	alert("트레이너 정보를 선택하세요.");
}

// 수정 모달 팝업 창에서 [수정] 버튼 누르면 수정 폼을 전송
function submitDelete() {
	$('#search-form').submit();
}