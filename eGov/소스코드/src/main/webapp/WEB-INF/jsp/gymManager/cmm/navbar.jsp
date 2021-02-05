<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>HOME</title>
	
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Required CSS -->    
    <link rel="stylesheet" href="css/jquery-ui.css">
    <link rel="stylesheet" href="css/jquery.bootgrid.css">
    <link rel="stylesheet" href="css/responsive.dataTables.min.css">
    <link rel="stylesheet" href="DataTables/semantic.min.css">
    <link rel="stylesheet" href="DataTables/dataTables.semanticui.min.css">
    <link rel="stylesheet" href="DataTables/select.semanticui.min.css"> 
    <link rel='stylesheet' href="fullcalendar/core/main.min.css"/>
    <link rel='stylesheet' href="fullcalendar/daygrid/main.min.css"/>
    <link rel='stylesheet' href="fullcalendar/timegrid/main.min.css"/>     
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/sweetalert2.css">
    <link rel="stylesheet" href="css/Chart.css">
 	 	
 	<!-- 공통 사용 Script -->
 	<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
 	<script src="js/jquery-3.3.1.js"></script>
 	<script src="js/jquery.validate.min.js"></script>
 	<script src="js/jquery.bootgrid.js"></script>
 	<script src="js/additional-methods.min.js"></script>
 	<script src="js/moment.min.js"></script>  	
 	<script src="DataTables/datatables.min.js"></script>
 	<script src="DataTables/dataTables.semanticui.min.js"></script>
 	<script src="DataTables/semantic.min.js"></script>	
 	<script src="DataTables/select.semanticui.min.js"></script>	
 	<script src="js/dataTables.responsive.min.js"></script>
 	<script src="fullcalendar/core/main.min.js"></script>
 	<script src="fullcalendar/interaction/main.min.js"></script>  
    <script src="fullcalendar/daygrid/main.min.js"></script>
    <script src="fullcalendar/timegrid/main.min.js"></script>    	 	
 	<script src="js/jquery-ui.js"></script>
 	<script src="js/sweetalert2.js"></script>
 	<script src="js/Chart.js"></script>	 			 		 	
 	<script src="js/map.js"></script>
 	
 	<!-- 공통 사용 CSS -->	
 	<link rel="stylesheet" href="css/nav.css">
 	
</head>
<body>
	<!-- GNB 영역 START-->
	<nav class="navbar navbar-global navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" id="brand-name"    href="/mainView.do">Gym-Manager</a>	
	          	<a class="navbar-brand" id="member-menu"   href="/memberView.do">회원 관리</a>
	          	<a class="navbar-brand" id="trainer-menu"  href="/trainerView.do">트레이너 관리</a>
	          	<a class="navbar-brand" id="locker-menu"   href="/lockerView.do">사물함 관리</a>
	          	<a class="navbar-brand" id="schedule-menu" href="/scheduleView.do">수업일정 관리</a>
	          	<a class="navbar-brand" id="statistics-menu" href="/statisticsView.do">통계보기</a>	  	
	      	</div>
	      </div>
    </nav>
    <!-- GNB 영역 END -->
    
    <!-- LNB 영역 STARAT -->
	<nav class="navbar-primary">
	  <ul class="navbar-primary-menu">
	    <li>
	      <a href="/mainView.do"><span class="glyphicon glyphicon-list-alt"></span><span class="nav-label">홈</span></a>
	      <a href="/logOut.do"><span class="glyphicon glyphicon-cog"></span><span class="nav-label">로그아웃</span></a>
	    </li>
	  </ul>
	</nav>
	<!-- LNB 영역 END -->
		
	<!-- Optional JavaScript -->
    <script src="js/popper.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
  </body>
</body>
</html>
