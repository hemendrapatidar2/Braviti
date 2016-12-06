<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


</head>
<body ng-controller="preferenceCtl">



	<script type="text/ng-template" id="tree_item_renderer.html">
    <input type="checkbox" ng-model="nameCode" ng-change="setData(data,$index)"> {{ data.text }}
    <ul>
        <li ng-repeat = "data in data.children" ng-include= "'tree_item_renderer.html'"></li>
    </ul>
</script>


	<div class="form-group">
		<div class="container">
			<div class="col-md-4">
				<ul style="background-color: white">
					<li ng-repeat="data in tree" ng-include="'tree_item_renderer.html'"></li>
				</ul>

			</div>
			
			<div class="col-md-5">
			
			<div class="form-group">
											<input type="text" name="nameCode" class="form-control"
												ng-model="nameCode" placeholder="Name & Code" />

										</div>
				<table class="style1">

						<tr>
							<th><center>Outlet Name</center></th>
							<th>Category</th>
							<th>Offer Discription</th>
						</tr>
						<tr ng-repeat="item in res | filter: nameCode" class="tr1">
							<td><center>
									<b>{{item.storeName}}</b>
								</center></td>

							<td colspan="2">
								<table class="style2">
									<tr ng-repeat="offer in item.offerMap | filter: nameCode" class="tr2">
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
	
	


</body>
</html>