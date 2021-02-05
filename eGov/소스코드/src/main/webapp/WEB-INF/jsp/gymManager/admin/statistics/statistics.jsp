<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>통계</title>
	
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    
<!-- Custom CSS -->    
<link rel="stylesheet" href="css/modal.css">
<link rel="stylesheet" href="css/button.css">
<link rel="stylesheet" href="css/listTable.css">
<style type="text/css">
#statistics-menu {
	background-color: white;
	color : #138496;
	border-radius: 10px;
	font-weight: bold;
}        
</style>
    
<!-- Script -->	    
<script src="js/common.js"></script>
<script src="js/statistics.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/gymManager/cmm/navbar.jsp"></jsp:include>
	
	<!-- Contents 영역 START -->
	<div class="main-content">
		<form style="position:relative">
			<div id='d1' style="position:absolute; top:0px; left:0px; z-index:1">  
				<canvas id="genderRatio" width='400' height='400'></canvas>
			</div>
			<div id='d2' style="position:absolute; top:0px; left:450px; z-index:2">
				<canvas id="lockerUsage" width='200' height='400'></canvas>
			</div>
			<div id='d3' style="position:absolute; top:0px; left:750px; z-index:3">
				<canvas id="memberOfMonth" width='400' height='400'></canvas>
			</div>
			<div id='d4' style="position:absolute; top:0px; left:1200px; z-index:3">
				<canvas id="mbrsDuration" width='400' height='400'></canvas>
			</div>
		</form>
		<form style="position:relative">
			<div id='d5' style="position:absolute; top:450px; left:30px; z-index:1">  
				<canvas id="ptChanging" width='1600' height='300'></canvas>
			</div>
		</form>
	</div>
	<!-- Contents 영역 END -->
	
	<!-- 사용중 사물함 모달 테이블   -->
	<div class="modal fade" id="usedLockerListModal" tabindex="-1" role="dialog" aria-labelledby="lockerListModal" aria-hidden="true">
	  <div class="modal-dialog modal-lg" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="lockerListModal">사용중인 사물함 목록</h5>
	      </div>
	      <div class="modal-body">
	        <form id="lockerInfoFrm">
			  <div class="form-row frow">
			    <div class="col-md-12 mb-3" id="lockerList">
					<table id="grid-selection1" class="table table-condensed table-hover">
			  		<thead>
			  			<tr>
			  				<th data-column-id="lid"   data-identifier="true" >사물함번호</th>
			  				<th data-column-id="mid"   data-identifier="false" >회원번호</th>
			  				<th data-column-id="mname" data-identifier="false">회원이름</th>
			  				<th data-column-id="start" data-identifier="false">이용시작날짜</th>
			  				<th data-column-id="end"   data-identifier="false">이용종료날짜</th>
			  			</tr>
			  		</thead>
			  		<tbody>
			  			<c:forEach items="${usedLockerList}" var="vo" varStatus="status">
			  				<tr>
			  					<td><c:out value="${vo.lid}"></c:out></td>
			  					<td><c:out value="${vo.mid}"></c:out></td>
			  					<td><c:out value="${namesList[status.index]}"></c:out></td>
			  					<td><c:out value="${vo.start_dtm}"></c:out></td>
			  					<td><c:out value="${vo.end_dtm}"></c:out></td>
			  				</tr>
			  			</c:forEach>
			  		</tbody>
			  	</table>
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
	<!-- 사용중 사물함 모달 테이블   -->
	
	<!-- 배정된 회원 목록 모달 테이블   -->
	<div class="modal fade" id="memberGenListModal" tabindex="-1" role="dialog" aria-labelledby="mgenListModal" aria-hidden="true">
	  <div class="modal-dialog modal-lg" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="mgenListModal">등록 회원 목록</h5>
	      </div>
	      <div class="modal-body">
	        <form id="memberInfoFrm">
			  <div class="form-row frow">
			    <div class="col-md-12 mb-3" id="memberGenList">
					<table id="grid-selection2" class="table table-condensed table-hover">
			  		<thead>
			  			<tr>
			  				<th data-column-id="mid"   data-identifier="true" >회원번호</th>
			  				<th data-column-id="name" data-identifier="false">회원이름</th>
			  				<th data-column-id="gen" data-identifier="false">회원성별</th>
			  				<th data-column-id="start" data-identifier="false">이용시작날짜</th>
			  				<th data-column-id="end"   data-identifier="false">이용종료날짜</th>
			  			</tr>
			  		</thead>
			  		<tbody>
			  			<c:forEach items="${memberGenList}" var="vo" varStatus="status">
			  				<tr>
			  					<td><c:out value="${vo.mid}"></c:out></td>
			  					<td><c:out value="${vo.name}"></c:out></td>
			  					<td><c:out value="${vo.gen}"></c:out></td>
			  					<td><c:out value="${vo.mbrs_start}"></c:out></td>
			  					<td><c:out value="${vo.mbrs_end}"></c:out></td>
			  				</tr>
			  			</c:forEach>
			  		</tbody>
			  	</table>
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
	<!-- 배정된 회원 목록 모달 테이블   -->
	
	<script>
	$(document).ready(function() {
		/* 차트를 그릴 canvas element의 context를 구한다 */
		var genderRatioCTX = document.getElementById("genderRatio").getContext('2d');
		var lockerUsageCTX = document.getElementById("lockerUsage").getContext('2d');
		var memberOfMonthCTX = document.getElementById("memberOfMonth").getContext('2d');
		var mbrsDurationCTX = document.getElementById("mbrsDuration").getContext('2d');
		var ptChangingCTX   = document.getElementById("ptChanging").getContext('2d');
		
		/* 30분 수업만 진행할 시 한 달에 1080회 수업 진행 가능,
		 * 50분 수업만 진행할 시 한 달에 720회 수업 진행 가능
		 * 수업 진행률을 계산할 분모로서 이 두 값의 평균을 한 달에 수업 가능한 최대 횟수로 설정함
		 */
	    var maxPTCountOfMonth = 900; 
		
	   /*
		* 차트에 쓰일 데이터가 컨트롤러에서 넘어오면, 변수를 선언하고 초기화하여 세팅한다.
		* 1. maleCnt : 모든 등록된 회원 중에서 성별이 남성인 회원의 숫자.
		* 2. femaleCnt : 모든 등록된 회원 중에서 성별이 여성인 회원의 숫자.
		* 3. membersOfMonth : 각 월 별로 등록을 시작한 회원의 숫자를 담은 배열. 인덱스 + 1이 각 월이 된다.
		* 4. ptClassOfMonth : 각 월 별로 실행된 개인 수업 횟수를 담은 배열. 인덱스 + 1이 각 월이 된다.
		*    (한 달 동안 진행 된 수업 횟수 / 900) * 100 = 한 달 동안 진행된 수업의 비율
		* 5. lockerCnt : 모든 사물함 중, 사용자가 배정된 사물함의 갯수
		* 6. emptyCnt : 모든 사물함 중, 사용자가 배정되어 있지 않은 사물함의 갯수
		* 7. duration : 각 단위 기간 별 이용권을 등록한 회원의 비율
		     (단위 기간동안 등록한 회원 수 / 전체 회원수) * 100 = duration
		*/
		var maleCnt = <c:out value="${maleCnt}"/>;
		var femaleCnt = <c:out value="${femaleCnt}"/>;
		var allMember = maleCnt + femaleCnt;
		var membersOfMonth = new Array();
		var ptClassOfMonth = new Array();
		var lockerCnt = <c:out value="${lockerCnt}"/>;		
		var emptyCnt = 100 - lockerCnt;				
		var duration1 = parseInt(<c:out value="${duration[0]}"/> / allMember * 100);
		var duration2 = parseInt(<c:out value="${duration[1]}"/> / allMember * 100);
		var duration3 = parseInt(<c:out value="${duration[2]}"/> / allMember * 100);
		var duration4 = parseInt(<c:out value="${duration[3]}"/> / allMember * 100);
				
		membersOfMonth[0] = <c:out value="${membersOfMonth[0]}" />;
		membersOfMonth[1] = <c:out value="${membersOfMonth[1]}" />;
		membersOfMonth[2] = <c:out value="${membersOfMonth[2]}" />;
		membersOfMonth[3] = <c:out value="${membersOfMonth[3]}" />;
		membersOfMonth[4] = <c:out value="${membersOfMonth[4]}" />;
		membersOfMonth[5] = <c:out value="${membersOfMonth[5]}" />;
		membersOfMonth[6] = <c:out value="${membersOfMonth[6]}" />;
		membersOfMonth[7] = <c:out value="${membersOfMonth[7]}" />;
		membersOfMonth[8] = <c:out value="${membersOfMonth[8]}" />;
		membersOfMonth[9] = <c:out value="${membersOfMonth[9]}" />;
		membersOfMonth[10] = <c:out value="${membersOfMonth[10]}" />;
		membersOfMonth[11] = <c:out value="${membersOfMonth[11]}" />;
		
		ptClassOfMonth[0] = <c:out value="${ptClassOfMonth[0]}"/>;
		ptClassOfMonth[1] = <c:out value="${ptClassOfMonth[1]}"/>;
		ptClassOfMonth[2] = <c:out value="${ptClassOfMonth[2]}"/>;
		ptClassOfMonth[3] = <c:out value="${ptClassOfMonth[3]}"/>;
		ptClassOfMonth[4] = <c:out value="${ptClassOfMonth[4]}"/>;
		ptClassOfMonth[5] = <c:out value="${ptClassOfMonth[5]}"/>;
		ptClassOfMonth[6] = <c:out value="${ptClassOfMonth[6]}"/>;
		ptClassOfMonth[7] = <c:out value="${ptClassOfMonth[7]}"/>;
		ptClassOfMonth[8] = <c:out value="${ptClassOfMonth[8]}"/>;
		ptClassOfMonth[9] = <c:out value="${ptClassOfMonth[9]}"/>;
		ptClassOfMonth[10] = <c:out value="${ptClassOfMonth[10]}"/>;
		ptClassOfMonth[11] = <c:out value="${ptClassOfMonth[11]}"/>;
		
		/* 화면에 그려질 차트들을 선언하고 렌더링 한다. */
		// 회원의 남녀 성별 분포를 나타내는 차트
		var genderRatio = new Chart(genderRatioCTX, {
			type: 'doughnut',
			data: {
				labels : ["남성", "여성"],
				datasets: [{
					label: '전체 회원의 성별 비율',
					data: [maleCnt, femaleCnt],
					 backgroundColor: [
			                'rgba(255, 99, 132, 0.2)',
			                'rgba(54, 162, 235, 0.2)'
			            ],
			            borderColor: [
			                'rgba(255,99,132,1)',
			                'rgba(54, 162, 235, 1)'
			            ],
			            borderWidth: 1
				}]
			},
			options: {
		        maintainAspectRatio: true,
		        title:{
	                display:true,
	                text:"회원 성별 분포"
	            },
	            tooltips: {
	                mode: 'label'
	            },
	            // 차트를 클릭 시, 회원 목록을 조회하여 회원의 상세 정보 목록을 보여주는 모달 팝업을 띄워주는 함수를 실행한다 
	            onClick: function (genderRatioCTX) {
	            	selectAllGenListAjax();
	            }
			}
		});
		
		// 사물함의 사용 현황을 나타내는 차트
		var lockerUsage = new Chart(lockerUsageCTX, {
			type: 'bar',
			data: {
				labels : ["사물함 이용률"],
				datasets : [
					{
					label: '사용중인 사물함',
					data: [lockerCnt],
					backgroundColor: [
						'rgba(255, 206, 86, 0.2)'
		            ],
		            borderColor: [
		            	'rgba(255, 206, 86, 1)'
		            ],
		            borderWidth: 1
				},
				{
					label: '빈 사물함',
					data: [emptyCnt],
					backgroundColor: [
						'rgba(75, 192, 192, 0.2)'
		            ],
		            borderColor: [
		            	'rgba(75, 192, 192, 1)'
		            ],
		            borderWidth: 1
				}
			]},
			options: {
		        maintainAspectRatio: true,
		        title:{
	                display:true,
	                text:"사물함 이용 현황"
	            },
	            tooltips: {
	                mode: 'index'
	            },
		        scales: {
		            xAxes: [{
		                stacked: true
		            }],
		            yAxes: [{
		                stacked: true
		            }]
		        },
	            onClick: function (genderRatioCTX) {
					$("#usedLockerListModal").modal('show');
	            }
			}
		});
		
		// 각 월별 이용권을 등록한 회원의 숫자를 나타내는 차트
		var memberOfMonth = new Chart(memberOfMonthCTX, {
			type: 'bar',
			data: {
				labels : ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
				datasets: [
					{
						label: '월별 등록하는 회원 수(명)',
						data: [
								membersOfMonth[0], membersOfMonth[1], membersOfMonth[2], membersOfMonth[3], 
							    membersOfMonth[4], membersOfMonth[5], membersOfMonth[6], membersOfMonth[7], 
							    membersOfMonth[8], membersOfMonth[9], membersOfMonth[10], membersOfMonth[11]
						],
					 	backgroundColor: [
					 		'rgba(255, 100, 132, 0.2)',		// 1월
					 		'rgba(255, 130, 150, 0.2)',		// 2월			 		
					 		'rgba(255, 159, 64, 0.2)',		// 3월
			                'rgba(255, 200, 100, 0.2)',		// 4월
			                'rgba(255, 230, 86, 0.2)',		// 5월
			                'rgba(180, 210, 180, 0.2)',		// 6월              
			                'rgba(100, 210, 190, 0.2)',		// 7월
			                'rgba(75, 192, 192, 0.2)',		// 8월
			                'rgba(80, 210, 235, 0.2)',		// 9월
			                'rgba(54, 162, 235, 0.2)',		// 10월		                		                
			                'rgba(170, 130, 255, 0.2)',		// 11월
			                'rgba(153, 102, 255, 0.2)'		// 12월
			                
					 	],
			         	borderColor: [
			         		'rgba(255, 100, 132, 1)',
			         		'rgba(255, 130, 150, 1)',			         		
			         		'rgba(255, 159, 64, 1)',
			                'rgba(255, 200, 100, 1)',
			                'rgba(255, 230, 86, 1)',
			                'rgba(180, 210, 180, 1)',
			                'rgba(100, 210, 190, 1)',
			                'rgba(75, 192, 192, 1)',
			                'rgba(80, 210, 235, 1)',
			                'rgba(54, 162, 235, 1)',			             			                
			                'rgba(170, 130, 255, 1)',
			                'rgba(153, 102, 255, 1)'		                
			         	],
			         	borderWidth: 1
			       }
				]
			},
			options: {
		        maintainAspectRatio: true, 
		        title:{
	                display:true,
	                text: '월별 등록하는 회원 수(명)'
	            },
	            tooltips: {
	                mode: 'index'
	            },
	            scales: {
	                yAxes: [{
	                    ticks: {
	                        beginAtZero:true
	                    }
	                }]
	            },
	            onClick: function (genderRatioCTX) {
	            	selectAllGenListAjax();
	            }
			}
		});
		
		// 각 단위 기간 별만큼 이용권을 유지하는 회원의 수를 전체 회원 수와의 비율로 나타내는 차트
		var mbrsDuration = new Chart(mbrsDurationCTX, {
			type: 'horizontalBar',
			data: {
				labels : ["0 ~ 3개월", "3 ~ 6개월", "6 ~ 12개월", "12개월 이상"],
				datasets: [
					{
						label: '회원권 유지 기간 비율(%)',
						data: [duration1, duration2, duration3, duration4],
					 	backgroundColor: [
					 		'rgba(104, 202, 255, 0.2)',
					 		'rgba(54, 152, 205, 0.2)',
					 		'rgba(4, 102, 155, 0.2)',
					 		'rgba(0, 52, 105, 0.2)'
					 	],
			         	borderColor: [
			         		'rgba(84, 182, 255, 1)',
			         		'rgba(54, 152, 205, 1)',
					 		'rgba(4, 102, 155, 1)',
					 		'rgba(0, 52, 105, 1)'
			         	],
			         	borderWidth: 1
			       }
				]
			},
			options: {
		        maintainAspectRatio: true,		        
		        title:{
	                display:true,
	                text:"전체 회원 중 각 단위 기간별 회원권 유지 기간 비율(%)"
	            },
	            tooltips: {
	                mode: 'index'
	            },
	            scales: {
	                xAxes: [{
	                    ticks: {
	                        beginAtZero: true
	                    }
	                }]
	            },
	            onClick: function (genderRatioCTX) {
	            	selectAllGenListAjax();
	            }
			}
		});
		
		// 각 월별로 진행된 수업 횟수를 한달 동안 진행 가능한 최대 수업횟수와의 비율로 나타내는 차트
		var ptChanging = new Chart(ptChangingCTX, {
			type: 'line',
			data: {
				labels : ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
				datasets: [
					{
						label: '월별 개인수업 진행률(%)',
						data: [
							ptClassOfMonth[0], ptClassOfMonth[1], ptClassOfMonth[2], ptClassOfMonth[3], 
							ptClassOfMonth[4], ptClassOfMonth[5], ptClassOfMonth[6], ptClassOfMonth[7], 
							ptClassOfMonth[8], ptClassOfMonth[9], ptClassOfMonth[10], ptClassOfMonth[11] 
						],
					 	backgroundColor: ['rgba(180, 210, 180, 0.2)'],
			         	borderColor: ['rgba(75, 192, 192, 1)'],
			         	borderWidth: 1
			       }
				]
			},
			options: {
		        maintainAspectRatio: true, 
		        title:{
	                display:true,
	                text:"단위 기간별 개인수업 횟수 진행률(%)"
	            },
	            tooltips: {
	                mode: 'index'
	            },
	            scales: {
	                yAxes: [{
	                    ticks: {
	                        beginAtZero:true
	                    }
	                }]
	            }
			}
		});
		
		// 사용중인 사물함 목록 테이블 세팅
		$("#grid-selection1").bootgrid({
			navigation: 1,
		    selection: false,
		    multiSelect: false,
		    rowSelect: true,
		    keepSelection: false,
		    labels: {
		        noResults: "검색 결과가 없습니다",
		        search: "검색"
		    }
		});
		
		// 상세 회원정보 목록 테이블 세팅
		$("#grid-selection2").bootgrid({
			navigation: 1,
		    selection: false,
		    multiSelect: false,
		    rowSelect: true,
		    keepSelection: false,
		    labels: {
		        noResults: "검색 결과가 없습니다",
		        search: "검색"
		    }
		});
	});		
	</script>
	
    <script src="js/popper.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
</body>
</html>
