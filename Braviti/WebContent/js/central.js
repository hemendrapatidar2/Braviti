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
			$scope.categoryList = {};
			$scope.priceList = [];
			$scope.priceSelectedOutputs = [];
			$scope.result = {};
			$scope.priceRange = [];
			//Service call to get the preferences
			checkPreferencesExists();
			
//			getPreferences();			
			
			//get the selected checkbox price value
			/*$scope.setOutput = function(value) {	
				console.log("Value to be added in array->"+value);
				console.log("Pushing data to array original array->[priceSelectedOutputs="+$scope.priceSelectedOutputs+"],["+angular.isDefined($scope.confirmed)+"],[confirmed="+$scope.confirmed+"]");
				if($scope.priceSelectedOutputs.indexOf(value) == -1) {
					 
				$scope.priceSelectedOutputs.push(value); 
				}
				console.log("After pushing values->"+$scope.priceSelectedOutputs);
			 };*/

			 //Call the desired service to send preference data
			$scope.applyFilter = function(){				
					  $scope.categoryIdArray = [];
					  $scope.priceArray = []; 
					  angular.forEach($scope.categoryList, function(category){						
					    if (!!category.selected) $scope.categoryIdArray.push(category.id);
					  });
					  angular.forEach($scope.priceList, function(price){						
						    if (price.selected==true) 
						    	{
						    	$scope.priceSelectedOutputs.push(price.range);
						    	console.log("Value to be pushed-> "+price+" "+price.range+":"+$scope.priceSelectedOutputs);
						    	}
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
			
			$scope.isExist = function(value){
		        return $scope.priceRange.map(function(type){return type;}).indexOf(value);
		    }
			
			
			function checkPreferencesExists(){
				getPreferences()
				
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
					getPreferencePerUser();
				});
			}
			
			function getPreferencePerUser(){
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
					
					if(response.data != null){
						
						setUserPreferencesData(response.data);
					} 
					
					
				});
			}
			
			function setUserPreferencesData(data){
				
				console.log("Preference data to set "+angular.toJson(data));
				$scope.result = data;
				$scope.categories = data.categories;
				 
				 $scope.priceRange = data.priceRange;
				 
				 
				 for(var index = 0;index<$scope.categoryList.length;index++) {
						for(var i = 0;i<$scope.categories.length;i++) {
							console.log($scope.categories[i] +"             "+$scope.categoryList[i].id);
							if($scope.categories[i] == $scope.categoryList[index].id) {
								$scope.categoryList[index].selected=true;
								break;
							} else {
								$scope.categoryList[index].selected=false;
								
							}
						}
					}
				 
				 for(var index = 0;index<$scope.priceList.length;index++) {
						for(var i = 0;i<$scope.priceRange.length;i++) {
							console.log($scope.priceRange[i]+" \"is equal to\" "+$scope.priceList[index].range);
							if($scope.priceRange[i] == $scope.priceList[index].range) {
								console.log("set price "+$scope.priceList[index].range+".selected=true");
								$scope.priceList[index].selected=true; 
								break;
							} else {
								console.log("set price "+$scope.priceList[index].range+".selected=false");
								$scope.priceList[index].selected=false; 
							}
						}
					}
				 console.log(angular.toJson($scope.priceList));
				
			}
			
			function setData(data){
				console.log("setData(data)");
				 $scope.categoryList = data.categoryList;
				 $scope.priceList = data.priceRangeList;
				 
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

							$scope.locations = [{location : 'ShivajiNagar',latitude:'18.5318',langitude:'73.8499'},{location : 'Kothrud',latitude:'18.5102',langitude:'73.8173'},{location : 'Pimpri',latitude:'18.6256',langitude:'73.8060'},{location : 'Wakad',latitude:'18.6059',langitude:'73.7526'},{location : 'Nigdi',latitude:'18.6654',langitude:'73.7710'}];									
							$scope.selectedImage = "images/map.png";
							$scope.changeLocation = function() {
								console.log('in change location '
										+ $scope.location);
//								$scope.selectedLocation = true;
								$scope.loading = true;
								$http({
									method : "GET",
									url : "controller.jsp",
									params : {
										location : $scope.userSelectedLocation.location,
										userName : $scope.userName

									},
									headers : {
										'Content-Type' : 'application/json'
									}
								})
										.then(
												function(reponse) {
													$scope.loading = false;
													$scope.selectedLocation = true;
													$scope.data = reponse.data;
													prepareMap();
												});
												

							}
							$scope.loadRouteMap =function(outlet){
								var map = prepareMap();
								var currentLocation = new google.maps.LatLng($scope.userSelectedLocation.latitude,$scope.userSelectedLocation.langitude);
								var outletLocation = new google.maps.LatLng(outlet.latitude,outlet.langitude);
								var request = {
									        origin : currentLocation,
									        destination : outletLocation,
									        //avoidTolls: true,
									       // avoidHighways: false,
									        travelMode : google.maps.TravelMode.DRIVING
									    };
									var directionsService = new google.maps.DirectionsService();
							        var directionsDisplay = new google.maps.DirectionsRenderer();
								    directionsDisplay.setMap(map); 
								    directionsService.route(request, function (response, status) {
								    	
								        if (status == google.maps.DirectionsStatus.OK) {
								            directionsDisplay.setDirections(response);
								        } else {
								            window.alert('Directions request failed due to ' + status);
								        }
								    });
								    setTimeout(function () { map.setZoom(12); }, 1000);
							}
							function prepareMap(){
								try {
									var res = $scope.data;
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
												$scope.data[index],
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
									return map;
									}catch(ex){
										
										console.log("Error in Maps:"+ex);
									}
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
				'$window',
				function($scope, $http,$window) {
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
					/**
					 * function calls sendAddUserRequest function to add user in database.
					 * Builds UserDTO object and converts it into JSON format to send it to REST web service
					 */
					$scope.addUser=function()
					{
						$scope.UserDTO = {
							firstName : $scope.firstName,
							lastName : $scope.lastName,
							gender : $scope.gender,
							userId : $scope.loginId,
							password : $scope.password,
							panNumber : $scope.panNumber,
							fatherName : $scope.fatherName,
							voterId : $scope.voterId,
							emailId : $scope.email,
							countryName : $scope.countryName,
							localAddress : $scope.address,
							stateName : $scope.stateName,
							cityName : $scope.cityName,
							districtName : $scope.districtName,
							nationality : $scope.nationality
						};
						$scope.dto = angular.toJson($scope.UserDTO);
						sendAddUserRequest();
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

				    /**
				     * Sends addUser request to REST web service
				     */
					function sendAddUserRequest() {
						$http({
							method : "POST",
							url : "data/user/addUser",
							data : $scope.dto,
							dataType : 'json',
							headers : {
								'Content-Type' : 'application/json'
							}
						}).then(
								function(response) {
									if (response.data.status == true) {
										gotoHomePage();
									}
								});
					}
					/**
					 * On receiving result status=true from REST web services shows success alert message to user.
					 * Once user clicks on 'OK' shows home page. 
					 */
					function gotoHomePage() {
						 
						 var url ='./Central.jsp#/'
					     alert("User "+$scope.UserDTO.userId+" added succesfully");
						 $window.location.href = url;
					}

				}
				]);
