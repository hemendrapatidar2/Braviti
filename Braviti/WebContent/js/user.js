var myApp = angular.module("userApp", []);



	
myApp.controller('UserCtl',['$scope','$http',function($scope, $http) {
	console.log("inside controller");

	$scope.countries = [ "India", "USA", "UK" ];

	$scope.selectedItem;
	$scope.dropboxitemselected = function(item) {

		$scope.selectedItem = item;

	}

	$scope.getState = function() {
		console
				.log("inside get state  >>"
						+ $scope.countryName);
		$scope.state = [ "" ];
		if (angular.equals("India", $scope.countryName)) {
			console.log("Inside India Condition");
			$scope.state = [ "M.P", "Maharashtra", "Gujrat",
					"Delhi" ];

		}
		if (angular.equals("USA", $scope.countryName)) {
			$scope.state = [ "California", "Florida", "Texas",
					"Alaska" ];
		}

	}
	$scope.getDistrict = function() {
		$scope.district = [];
		if (angular.equals("M.P", $scope.stateName)) {
			$scope.district = [ "Khargone", "Indore", "Dhar",
					"Khandwa" ];
		}
		if (angular.equals("Maharashtra", $scope.stateName)) {
			$scope.district = [ "Pune", "Mumbai", "Satara",
					"Dhule" ];
		}
		if (angular.equals("Gujrat", $scope.stateName)) {
			$scope.district = [ "Gandhinagar", "Badoda",
					"Bhavnagar", "Jamnagar" ];
		}
		if (angular.equals("Delhi", $scope.stateName)) {
			$scope.district = [ "East Delhi",
					"North East Delhi", "South West Delhi",
					"New Delhi" ];
		}

		console.log("inside get state");
	}
	$scope.getCity = function() {
		console.log("inside get state");
		$scope.city = [ "Pune", "Indore", "Vadodra", "Raipur",
				"Bhopal", "Chicago", "Houston", "San Antonio",
				"Dallas", "Denver" ];
	}

	$(function() {
		$('#datetimepicker1').datetimepicker({
			todayBtn : 1,
			todayHighlight : 1,
			showMeridian : 1,
			ormat : 'yyyy-mm-dd',
			startView : 'month',
			minView : 'month',
			autoclose : true,
			format : "dd-mm-yyyy",
		});
	});

} ]);

