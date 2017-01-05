<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html ng-app="LoginApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<script src="lib/jquery.min.js"></script>
<script src="lib/bootstrap.min.js"></script>
<script type="text/javascript" src="lib/angular.min.js"></script>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-cookies.js"></script>
<script src="lib/angular-animate.min.js"></script>
<script src="lib/ui-bootstrap-tpls-2.1.3.js"></script>

<link rel="stylesheet" href="lib/toaster.min.css">
<link href="css/style.css" rel="stylesheet">
<script type="text/javascript" src="lib/toaster.min.js"></script>




<script src="js/Login.js"></script>



<!-- <style type="text/css">
.body {
	background: url(images/intro-bg.jpg) no-repeat center center;
	background-size: cover;
}
</style>

<style type="text/css">
#share-buttons img {
	width: 35px;
	padding: 5px;
	border: 0;
	box-shadow: 0;
	display: inline;
}
</style> -->

<title>Login</title>
</head>


<body ng-controller="LoginCtl">
<div class="container">
	<div class = "page-header">
		<h1  style="color: #FFFFFF"> Braviti</h1>		
	</div>
	
	
	<section class="container">
    <div class="login">
      <p >Sign-In</p>
      <form id="login-form" method="post" class="form-signin" role="form" novalidate>
        <input name="userId" id="userId" type="text" class="form-control requiredField"placeholder="UserId" ng-model="userId" 
			tabindex="1" type="text" ng-required="true" autofocus> 
			<input name="password" id="password" type="password" class="form-control disable requiredField" ng-model="password"
			placeholder="Password" tabindex="2" ng-required="true"	>
			
          <label>
            <input type="checkbox" name="remember_me" id="remember_me">
            Remember me on this computer
          </label>
        </p>
        <button class="btn btn-block bt-login" type="submit" ng-click="login()">Log In</button>
        <p class="remember_me">
      </form>
    </div>

    <div class="login-help">
      <p>Forgot your password? <a href="#">Sign Up</a>.</p>
    </div>
  </section>
  </div>
</div>
</body>


<!-- <body ng-controller="LoginCtl as $ctrl" class="body">
	<form name="form" novalidate>

		<toaster-container
			toaster-options="{'position-class': 'toast-top-center'}"></toaster-container>
		<div class="form-group"></div>
		<div class="form-group">
			<div class="container">
				<div class="panel-group">
					<div class="panel panel-default" style="background-color: #18BC9C">
						<div class="panel-heading"
							style="background-color: rgba(0, 0, 0, 1)">
							<b style="color: #FFFFFF">Braviti</b>
						</div>
						<div class="panel-body">
							<div class="col-xs-3">

								<div class="form-group">

									<div class="container">

										<div class="row">

											<div class="col-lg-4">

												<div class="well"
													style="height: 350px; width: 330px; margin-top: 15px; margin-left: 350px; border-radius: 20px; background-color: #53868b; border-color: lightblue; border-style: solid;">


													<center>

														<img style="height: 120px; width: 290px"
															src="images/login.png" /><br> <br>

														<div class="form-group has-feedback">
															<input type="text" name="userId" class="form-control"
																ng-model="userId" required placeholder="User ID" /> <i
																class="glyphicon glyphicon-user form-control-feedback"></i>

														</div>
														<div class="form-group has-feedback">
															<input type="password" name="password"
																class="form-control" ng-model="password" required
																placeholder="Password" /> <i
																class="glyphicon glyphicon-tag form-control-feedback"></i>

														</div>
														<button type="submit"
															class="btn btn-primary pull-center btn-md"
															ng-click="login()">Login</button>

													</center>

												</div>




											</div>

										</div>

									</div>

								</div>

							</div>
						</div>

						<div class="panel-footer">
							<div class="container"></div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
			</div>
		</div>





	</form>
	<script type="text/ng-template" id="validation.html">
        <div class="modal-header">
            <h3 class="modal-title" id="modal-title" style="color:red">Validation</h3>
        </div>
        <div class="modal-body" id="modal-body">
            <ul>
                <li ng-repeat="item in $ctrl.items track by $index">
                    <a href="#"><h4 style="color:red">{{ item }}</h4></a>
                </li>
            </ul>
        </div>
        <div class="modal-footer">
            <button class="btn btn-primary" type="button" ng-click="$ctrl.cancel()">Cancel</button>
        </div>
    </script>




</body> -->

</html>