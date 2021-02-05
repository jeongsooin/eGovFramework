<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>HOME</title>
	
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Required CSS -->
    <link rel="stylesheet" href="css/timepicker.css">    
    <link rel="stylesheet" href="css/jquery-ui.css">
    <link rel="stylesheet" href="DataTables/semantic.min.css">
    <link rel="stylesheet" href="DataTables/dataTables.semanticui.min.css">
    <link rel="stylesheet" href="DataTables/select.semanticui.min.css">      
    <link rel="stylesheet" href="css/bootstrap.min.css">
 	 	
 	<!-- 공통 사용 Script -->
 	<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
 	<script src="js/jquery-3.3.1.js"></script>  	
 	<script src="DataTables/datatables.min.js"></script>
 	<script src="DataTables/dataTables.semanticui.min.js"></script>
 	<script src="DataTables/semantic.min.js"></script>	
 	<script src="DataTables/select.semanticui.min.js"></script>		 	
 	<script src="js/jquery-ui.js"></script>	 
 	<script src="js/timepicker.js"></script>		
 	<script src="js/map.js"></script>
 	
 	<!-- 공통 사용 CSS -->	
 	<link rel="stylesheet" href="css/nav.css">
 	
 	<!-- Custom Script -->
 	<script>
 	// 유효성이 검증안된 폼의 submit 방지
	(function() {
	  'use strict';
	  window.addEventListener('load', function() {
	    // 해당 클래스 속성인 폼의 입력 데이터를 가져옴
	    var forms = document.getElementsByClassName('needs-validation');
	    // form에 필수 데이터 미입력 시 submit 막기
	    var validation = Array.prototype.filter.call(forms, function(form) {
	      form.addEventListener('submit', function(event) {
	        if (form.checkValidity() === false) {
	          event.preventDefault();
	          event.stopPropagation();
	        }
	        form.classList.add('was-validated');
	      }, false);
	    });
	  }, false);	  
	})();
 	</script> 	
</head>
<body>
	<!-- GNB 영역 START-->
	<nav class="navbar navbar-global navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" id="brand-name" href="/userMainView.do">Gym-Manager</a>		  	
	      	</div>
	      </div>
    </nav>
    <!-- GNB 영역 END -->
    
    <!-- LNB 영역 STARAT -->
	<nav class="navbar-primary">
	  <ul class="navbar-primary-menu">
	    <li>
	      <a href="/userMainView.do"><span class="glyphicon glyphicon-list-alt"></span><span class="nav-label">Home</span></a>
	      <a href="/logOut.do"><span class="glyphicon glyphicon-cog"></span><span class="nav-label">Logout</span></a>
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
