<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="centralApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Braviti</title>

<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<script src="lib/jquery.min.js"></script>

<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>

<script src="lib/bootstrap.min.js"></script>
<script type="text/javascript" src="lib/angular.min.js"></script>
<script type="text/javascript" src="lib/angular-route.js"></script>
<script src="js/central.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-cookies.js"></script>

<script src="lib/angular-sanitize.min.js"></script>
<link rel="stylesheet" href="css/central.css">

<script>
	$(document).ready(function() {
		$('[data-toggle="tooltip"]').tooltip();
	});
</script>

</head>
<body ng-controller="centralCtl" style="background-color: #495C70">

	<div class="form-group">
		<div class="container">

			<div class="container-fluid">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#offers">Offers</a></li>
					<li><a href="#piechart">Piechart</a></li>
					<li><a href="#user">User</a></li>
					<li><a href="#" ng-click="logout()">LogOut</a></li>
				</ul>
			</div>

		</div>
		<div class="clearfix"></div>
		<div>
			<div ng-view></div>
		</div>
	</div>
	

</body>
</html>