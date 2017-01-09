var myApp = angular.module("centralApp", [ 'ngCookies', 'ngSanitize',
		'ngRoute', 'angularTreeview' ])
String.prototype.replaceAll = function(target, replacement) {
  return this.split(target).join(replacement);
};
myApp.config(function($routeProvider) {
	$routeProvider.when("/", {
		templateUrl : "Offers.jsp",
		controller : "offerCtl"
	}).when("/offers", {
		templateUrl : "Offers.jsp",
		controller : "offerCtl"
	}).when("/piechart", {
		templateUrl : "HistoryPieChart.jsp",
		controller : "piechartCtl"
	}).when("/user", {
		templateUrl : "User.jsp",
		controller : "userCtl"
	}).when("/preferences", {
		templateUrl : "Preferences.jsp",
		controller : "preferenceCtl"
	})

});

myApp.controller('centralCtl', [ '$scope', '$http', '$window', '$log',
		'$cookies', '$sce',
		function($scope, $http, $window, $log, $cookies, $sce) {
			console.log("inside central controller");
			$scope.userName = $cookies.get('userName');
			$scope.logout = function() {
				$window.location.href = './Login.jsp#/';

			}

		} ]);



/////////////////////////////////////////////////////////Preference Controller///////////////////////////////////
myApp.controller('preferenceCtl', [ '$scope', '$http', '$cookies','$window',
		function($scope, $http, $cookies, $window) {
			console.log("inside preferenceCtl controller");
			//get the User Name Logged in
			$scope.userName = $cookies.get('userName');
			$scope.categoryList = [];
			$scope.priceList = [];
			$scope.priceSelectedOutputs = [];		
			
			//Service call to get the preferences
			getPreferences();			
			
			//get the selected checkbox price value
			$scope.setOutput = function(value) {						  
			  $scope.priceSelectedOutputs.push(value);
			 };

			 //Call the desired service to send preference data
			$scope.applyFilter = function(){				
					  $scope.categoryIdArray = [];
					  $scope.priceArray = [];
					  angular.forEach($scope.categoryList, function(category){						
					    if (!!category.selected) $scope.categoryIdArray.push(category.id);
					  });
					  var categoryJsonData=angular.toJson($scope.categoryIdArray);
					  var priceJsonData=angular.toJson($scope.priceSelectedOutputs);
					  categoryJsonData = categoryJsonData.replaceAll('[',"");
					  categoryJsonData = categoryJsonData.replaceAll(']',"");
					  categoryJsonData = categoryJsonData.replaceAll('"',"");
					  priceJsonData = priceJsonData.replaceAll('[',"");
					  priceJsonData = priceJsonData.replaceAll(']',"");
					  priceJsonData = priceJsonData.replaceAll('"',"");
					  
					  gotoOffers();
					  //Service call to post preference data
					  sendPreferenceData(categoryJsonData,priceJsonData);
				
			}
			
			function getPreferences(){
				$http({
					method : "GET",
					url : "getPreferences.jsp",
					headers : {
						'Content-Type' : 'application/json'
					}
				}).then(function(response){
					console.log("Service Success:"+angular.toJson(response));
					setData(response.data);
					
				});
			}
			
			function getPreferencesPerUser(){
				$http({
					method : "GET",
					url : "data/useroffers/getPreferencesPerUser",
					params : {
						userName : $scope.userName
					},
					headers : {
						'Content-Type' : 'application/json'
					}
				}).then(function(response){
					alert("Service Success:"+angular.toJson(response));
					
					//setData(response.data);
					
				});
			}
			
			
			
			function setData(data){
				 $scope.categoryList = data.categoryList;
				 $scope.priceList = data.priceRangeList;
				 getPreferencesPerUser();
			 }
			
			function sendPreferenceData(categoryJsonData,priceJsonData) {
				$http({
					method : "GET",
					url : "data/useroffers/setUserPreferences",
					params : {
						userName : $scope.userName,
						category : categoryJsonData,
						price : priceJsonData						
					},
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
					}
				}).then(function(response) {
					if(response.data.status == true) {
						gotoOffers();
//						loadData();
					} else {
						alert("Error!!");
					}
					
				});
			}
			
			 function gotoOffers() {
				 
				 var url ='./Central.jsp#/offers'
				 $window.location.href = url;
			}
			
			function loadData() {
				$http({
					method : "GET",
					url : "controller.jsp",
					params : {
						location : $scope.location,
						userName : $scope.userName

					},
					headers : {
						'Content-Type' : 'application/json'
					}
				}).then(function(reponse) {
					
				});
			}
			
//			$http({
//				method : "GET",
//				url : "data/useroffers",
//				params : {
//					location : 'ShivajiNagar',
//					userName : 'Raj'
//				},
//				headers : {
//					'Content-Type' : 'application/json'
//				}
//			}).then(function(reponse) {
//				$scope.res = reponse.data;
//				buildJson($scope.res);
//			});
//			function initTree(tree) {
//				function processNode(node) {
//					angular.forEach(node.children, function(child) {
//						if (processNode(child) === true) {
//							node.chk = true;
//						}
//					});
//
//					return node.chk;
//				}
//				angular.forEach(tree, processNode);
//			}
//			;
//			initTree($scope.tree);
//
//			function buildJson(list) {
//				var stores, cat;
//				var child1 = [], child = []
//				for (var i = 0; i < list.length; i++) {
//					console.log(list[i].storeName);
//
//					child1.push({
//						"text" : list[i].storeName
//					})
//					stores = {
//						"text" : "stores",
//						"children" : child1
//					}
//					for (var j = 0; j < list[i].offerList.length; j++) {
//						console.log(list[i].offerList[j].categoryName);
//						child.push({
//							'text' : list[i].offerList[j].categoryName
//						})
//					}
//					cat = {
//						"text" : "category",
//						"children" : child
//					}
//				}
//				var arr = [];
//				arr.push(cat);
//				arr.push(stores)
//				$scope.tree = arr;
//			}

		} ]);

myApp
		.controller(
				'offerCtl',
				[
						'$scope',
						'$http',
						'$window',
						'$log',
						'$cookies',
						'$sce',
						function($scope, $http, $window, $log, $cookies, $sce) {

							$log.info("in central controller");
							
							console.log($cookies.get('userName'));
							$scope.userName = $cookies.get('userName');
							$scope.selectedLocation = false;
							$scope.html = [];

							var titleTag = 'hppHemendra';

							console.log(titleTag);

							var stylesheet = "position: absolute;top: 100px;left: 80px;z-index: 2;height:50px;width:50px;";

							$scope.locations = [ 'ShivajiNagar', 'Kothrud',
									'Pimpri', 'Wakad', 'Nigdi', ]
							$scope.selectedImage = "images/map.png";
							$scope.changeLocation = function() {
								console.log('in change location '
										+ $scope.location);
								$scope.selectedLocation = true;
								$http({
									method : "GET",
									url : "controller.jsp",
									params : {
										location : $scope.location,
										userName : $scope.userName

									},
									headers : {
										'Content-Type' : 'application/json'
									}
								})
										.then(
												function(reponse) {
													try {
													$scope.data = reponse.data;
													var res = $scope.data;
													console.log("1:"+angular.toJson($scope.data));
													
													var firstArea = {
														lat : res[0].latitude,
														lng : res[0].langitude
													};
													var map = new google.maps.Map(
															document
																	.getElementById('map'),
															{
																zoom : 12,
																center : firstArea
															});

													for (var index = 0; index < $scope.data.length; index++) {
														var contentString = '<div id="content">';
														for (var offerIndex = 0; offerIndex < $scope.data[index].offerList.length; offerIndex++) {
															contentString = contentString
																	+ '<h4>'
																	+ $scope.data[index].offerList[offerIndex].categoryName
																	+ '</h4>'
																	+ '<div id="bodyContent">'
																	+ '<p>'
																	+ $scope.data[index].offerList[offerIndex].offerDescription
																	+ ',</p></div>';

														}
														contentString = contentString
																+ '</div>';
														var infowindow=null;
														var markerObj = getMarker(
																res[index],
																contentString,
																map);
														markerObj
																.addListener(
																		'click',
																		function() {
																			if(null != infowindow){
																				infowindow.close();
																			}
																			infowindow = new google.maps.InfoWindow(
																					{
																						content : this.description
																					});

																			infowindow
																					.open(
																							map,
																							this);
																		});
														map.center = {
															lat : res[index].latitude,
															lng : res[index].langitude
														};
													}
													}catch(ex){
														
														console.log("Error in Maps:"+ex);
													}
												});
												

							}
							function getMarker(data, contentString, map) {
								var marker = new google.maps.Marker({
									position : {
										lat : data.latitude,
										lng : data.langitude
									},
									title : data.storeName,
									description : contentString,
									map : map
								});
								return marker;
							}
							$scope.getRandomArbitrary = function(min, max) {
								console.log("inside random function");
								return Math.random() * (max - min) + min;
							}

							$scope.piechart = function() {
								$window.location.href = './HistoryPieChart.jsp#/';
							}

						} ]);

myApp
		.controller(
				'piechartCtl',
				[
						'$scope',
						'$http',
						'$window',
						'$log',
						'$cookies',
						'$sce',
						function($scope, $http, $window, $log, $cookies, $sce) {

							$log.info("in central controller")
							console.log($cookies.get('userName'));
							$scope.userName = $cookies.get('userName');
							$scope.location = "Nigdi";
							$scope.html = [];

							var titleTag = 'Pie Chart';
							var arr = [];
							var arr2 = [];
							console.log(titleTag);

							var stylesheet = "position: absolute;top: 100px;left: 80px;z-index: 2;height:50px;width:50px;";

							$scope.data = "";
							$http({
								method : "GET",
								url : "data/useroffers/pieChartData",
								params : {
									location : $scope.location,
									userName : $scope.userName

								},
								headers : {
									'Content-Type' : 'application/json'
								}
							})
									.then(
											function(reponse) {
												$scope.data = reponse.data;
												console
														.log("+++++++++++++++++++++++++++++");
												console.log($scope.data);
												var item;

												for (var i = 0; i < $scope.data.length; i++) {

													var item = {
														"name" : $scope.data[i].category,
														"y" : $scope.data[i].percentAmt
													};
													console.log(item);
													arr.push(item);
												}

												console
														.log("--------------------------------------");
												console.log(arr);

												$(function() {

													console
															.log("*************************");
													console.log(arr);
													Highcharts
															.chart(
																	'container2',
																	{
																		chart : {
																			plotBackgroundColor : null,
																			plotBorderWidth : null,
																			plotShadow : false,
																			type : 'pie'
																		},
																		title : {
																			text : 'Transaction History for '
																					+ $scope.userName
																		},
																		tooltip : {
																			pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
																		},
																		plotOptions : {
																			pie : {
																				allowPointSelect : true,
																				cursor : 'pointer',
																				dataLabels : {
																					enabled : true,
																					format : '<b>{point.name}</b>: {point.percentage:.1f} %',
																					style : {
																						color : (Highcharts.theme && Highcharts.theme.contrastTextColor)
																								|| 'black'
																					}
																				}
																			}
																		},
																		series : [ {
																			name : 'Brands',
																			colorByPoint : true,
																			data : arr
																		} ]
																	});
												});
											});

						} ]);

myApp.controller('userCtl',
		[
				'$scope',
				'$http',
				function($scope, $http) {
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
