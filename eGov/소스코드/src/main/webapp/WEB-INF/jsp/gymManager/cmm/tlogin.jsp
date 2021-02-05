<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Login</title>
	<!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="css/bootstrap.min.css">

    <!-- Custom styles for this template -->
    <link href="css/login.css" rel="stylesheet">
</head>
<body class="text-center">
	<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
  		<header class="masthead mb-auto">
    		<div class="inner">
      			<h3 class="masthead-brand"><!-- Page Name --></h3>
      				<nav class="nav nav-masthead justify-content-center">
       					<a class="nav-link active" href="#"><!-- Demo MainPage (Read Only) --></a>
        				<a class="nav-link" href="#"><!-- Send Email Form --></a>
     	 			</nav>
    		</div>
  		</header>
  		
  		<main role="main" class="inner cover">
    		<form action="/trainerLogin.do" method="post" class="form-signin" id="nativelogin">	
			  	<h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
			  		<label for="inputEmail" class="sr-only"> TRAINER ID</label>	
			  			<input id="id" type="text" name="tid" class="form-control" placeholder="Trainer ID" required autofocus><br>
					<label for="inputPassword" class="sr-only">Password</label>
						<input id="pw" type="password" name="pw" class="form-control" placeholder="Password" required>
						<div id="clossline" ><p>──────────────────</p></div>
					<input id="nlogin" class="btn btn-lg btn-light btn-block" type="submit" value="Login with ID">
				<p id="copyright" class="mt-5 mb-3 text-muted">&copy; 2019</p>
			</form>
  		</main>

  		<footer class="mastfoot mt-auto">
    		<div class="inner">
      			<!-- Contact information -->
    		</div>
  		</footer>
  	</div>
  	<!-- Optional JavaScript -->
    <script src="js/popper.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>