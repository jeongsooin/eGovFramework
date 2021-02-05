/*
 * @Description 등록된 회원정보를 성별 순으로 정렬하여 조회하는 Ajax를 요청하는 함수
 * 				통계 메뉴 화면에서, [회원 성별 분포] 그래프를 클릭 시 실행된다
 * @param
 * @return
 */
function selectAllGenListAjax() {
	$.ajax({
		url: '/selectAllGenListAjax.do',
		type: 'POST',
		success: function(data) {
			
			var result = JSON.parse(data);

			// message = yes : 조회성공, fail: 조회 실패
			if(result.message == 'yes') {
				// 조회 
				$("#memberGenListModal").modal('show');			
			} else {
				alert("회원 목록 조회에 실패하였습니다.");
			}
		},
		error: function() {
			alert("회원 목록 조회에 실패하였습니다.");
		}
	});
}