<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="userApp">

<head>
<meta charset="ISO-8859-1">


<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	media="screen">

<link href="lib/bootstrap-datetimepicker.min.css" rel="stylesheet"
	media="screen">

<script type="text/javascript" src="lib/jquery.min.js">
	
</script>
<script type="text/javascript" src="lib/bootstrap.min.js"></script>
<script type="text/javascript" src="lib/bootstrap-datetimepicker.js"
	charset="UTF-8"></script>
<script type="text/javascript" src="lib/bootstrap-datetimepicker.uk.js"
	charset="UTF-8"></script>

<script type="text/javascript" src="lib/angular.min.js"></script>


<script src="js/user.js"></script>

<style>
legend {
	color: grey
}
</style>

<style type="text/css">
.shortenedSelect {
	width: 150px;
}
</style>
<title>User</title>
</head>
<body ng-controller="UserCtl as demo">
	<div class="form-group">
		<div class="container">
			<div class="panel-group">
				<div class="panel panel-primary">
					<div class="panel-heading">User Form</div>

					<div class="panel-body">
						<div class="form-group">
							<div class="col-xs-3">
								<div class="form-group">

									<fieldset>
										<legend>Personal Details</legend>

									</fieldset>
								</div>
								<div class="form-group">
									<input type="text" name="firstName" class="form-control"
										ng-model="firstName" placeholder="First Name" />

								</div>
								<div class="form-group">
									<input type="text" name="lastName" class="form-control"
										ng-model="lastName" placeholder="Last Name" />
								</div>
								<div class="form-group">
									<input type="text" name="fatherName" class="form-control"
										ng-model="fatherName" placeholder="Father's Name" />
								</div>

								<div class="form-group">

									<div class="form-group">
										<div class='input-group date' id='datetimepicker1'>
											<input type='text' class="form-control"
												placeholder="Date Of Birth" /> <span
												class="input-group-addon"> <span
												class="glyphicon glyphicon-calendar"></span>
											</span>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label style="color: grey">Gender</label>
									<div class="btn-group" data-toggle="buttons">
										<div class="pull-center">
											<label class="btn btn-info btn-sm"> <input
												type="radio" name="gender" id="gender" ng-model="gender" checked>Male
											</label> <label class="btn btn-info btn-sm"> <input
												type="radio" name="gender" id="gender" ng-model="gender">Female
											</label>
										</div>

									</div>
								</div>

								<div class="form-group">
									<input type="text" name="nationality" class="form-control"
										ng-model="nationality" placeholder="Nationality" />
								</div>
								<div class="form-group">
									<input type="text" name="panNumber" class="form-control"
										ng-model="panNumber" placeholder="Pan Number" />
								</div>
								<div class="form-group">
									<input type="email" name="email" class="form-control"
										ng-model="email" placeholder="Email ID" />
								</div>
							</div>

							<div class="col-xs-1"></div>
							<div class="col-xs-3">
								<div class="form-group">
									<fieldset>
										<legend>Address Details</legend>

									</fieldset>
								</div>
								<div class="form-group">

									<label style="color: grey">Country</label>
									<p>
										<select class="shortenedSelect" ng-model="countryName"
											ng-change="getState()" ng-options="x for x in countries">
										</select>
									</p>

									<label style="color: grey">State</label>
									<p>
										<select class="shortenedSelect" ng-model="stateName"
											ng-change="getDistrict()" ng-options="x for x in state">
										</select>
									</p>

									<label style="color: grey">District</label>
									<p>
										<select class="shortenedSelect" ng-model="districtName"
											ng-change="getCity()" ng-options="x for x in district">
										</select>
									</p>

									<label style="color: grey">City</label>
									<p>
										<select class="shortenedSelect" ng-model="cityName"
											ng-options="x for x in city">
										</select>
									</p>
								</div>
								<div class="form-group">
									<label style="color: grey">Address</label>
									<textarea type="text" name="address" class="form-control"
										ng-model="address"></textarea>
								</div>


							</div>

							<div class="col-xs-1"></div>
							<div class="col-xs-3">

								<div class="form-group">

									<fieldset>
										<legend>Account Login Details</legend>

									</fieldset>
								</div>

								<div class="form-group">
									<input type="text" name="loginId" class="form-control"
										ng-model="loginId" placeholder="Login ID" />
								</div>
								<div class="form-group">
									<input type="password" name="password" class="form-control"
										ng-model="password" placeholder="Password" />
								</div>

								<div class="form-group">
									<input type="password" name="rePassword" class="form-control"
										ng-model="rePassword" placeholder="Re Enter Password" />
								</div>
							</div>

						</div>

					</div>
					<div class="panel-footer">

						<button type="button" data-dismiss="modal"
							class="btn btn-success pull-center btn-sm" ng-click="addUser()">Submit</button>
						<button type="button" data-dismiss="modal"
							class="btn btn-success pull-center btn-sm" ng-click="clearFormData()">Clear</button>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>


</html>