<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style>
/* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
#map {
	height: 100%;
}
/* Optional: Makes the sample page fill the window. */
html, body {
	height: 100%;
	margin: 0;
	padding: 0;
}

#floating-panel {
	position: absolute;
	top: 100px;
	left: 25%;
	z-index: 2;
	background-color: #fff;
	padding: 5px;
	border: 1px solid #999;
	text-align: center;
	font-family: 'Roboto', 'sans-serif';
	line-height: 30px;
	padding-left: 10px;
}
</style>
</head>

<body>
	<script>
		function initMap() {
			var shivajinagar = {
				lat : 18.5308,
				lng : 73.8475
			};
			var map = new google.maps.Map(document.getElementById('map'), {
				center : shivajinagar
			});
		}
	</script>
	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB6L6lXxPAsw_vCiKUkhXB-9vTG93rDEYU&callback=initMap">
		
	</script>

	<div class="container">
		<div class="col-xs-3">
			<div
				style="height: 550px; width: 550px; margin-top: 30px; margin-left: -100px; border-radius: 10px;">

				<!-- <img class="img-responsive" src="{{selectedImage}}" alt=""
					style="height: 450px; width: 500px; position: relative;"> -->
				<div id="map"></div>

			</div>


		</div>


		<div class="col-xs-2"></div>

		<div class="col-xs-3">
			<div class="form-group">

				<label style="color: white">Select Preferred Location :</label>
				<p>
					<select class="shortenedSelect" ng-model="location"
						ng-change="changeLocation()" ng-options="x for x in locations">
						<option value="">--Select an option--</option>
					</select>
				</p>
			</div>

			<div class="form-group">
				<label style="color: white">Outlets</label>

				<div class="well"
					style="width: 700px; background-color: #DCDCDC; border-color: black; border-style: solid;">
					<table class="style1">

						<tr>
							<th><center>Outlet Name</center></th>
							<th>Category</th>
							<th>Offer Discription</th>
						</tr>
						<tr ng-repeat="item in data" class="tr1">
							<td><center>
									<b>{{item.storeName}}</b>
								</center></td>

							<td colspan="2">
								<table class="style2">
									<tr ng-repeat="offer in item.offerMap" class="tr2">
										<td width="173px">{{offer.categoryName}}</td>
										<td width="150px">{{offer.offerDescription}}</td>
									</tr>

								</table>
							</td>


						</tr>
					</table>

				</div>

			</div>
		</div>

	</div>
</body>
</html>