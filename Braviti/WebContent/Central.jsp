<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="centralApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Braviti</title>

<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<script src="lib/jquery.min.js"></script>

<script src="lib/bootstrap.min.js"></script>
<script type="text/javascript" src="lib/angular.min.js"></script>
<script src="js/central.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-cookies.js"></script>
	
	<script src="lib/angular-sanitize.min.js"></script>
<link rel="stylesheet" href="css/central.css">

<script>
$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();   
});
</script>

</head>
<body ng-controller="centralCtl" style="background-color: #495C70">

	<div class="form-group">
		<h3 style="color: red">Welcome: {{userName}}</h3>
		<div class="container">
			<div class="col-xs-3">
				<div class="well"
					style="height: 500px; width: 500px; margin-top: 15px; margin-left: -100px; border-radius: 20px; background-color: #53868b; border-color: lightblue; border-style: solid;">

					<img class="img-responsive" src="{{selectedImage}}" alt=""
						style="height: 450px; width: 500px;">
						
					<div ng-bind-html="html"></div>
				</div>

			</div>
			<div class="col-xs-2"></div>
			<div class="col-xs-3">
				<div class="form-group">

					<label style="color: white">select preffered location</label>
					<p>
						<select class="shortenedSelect" ng-model="location"
							ng-change="changeLocation()" ng-options="x for x in locations">
						</select>
					</p>
					<div class="containerdiv">
						<!-- <img class=flag1 src="images/Aundh.JPG"> <img class=flag2
							src="images/login.png"> -->
						<div></div>
					</div>
				</div>
			</div>
			
			<div class="container">
  
</div>
</body>
</html>