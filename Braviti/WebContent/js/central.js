var myApp = angular.module("centralApp", ['ngCookies','ngSanitize'])

myApp.controller('centralCtl', [ '$scope', '$http', '$window', '$log','$cookies','$sce',
		function($scope, $http, $window, $log,$cookies,$sce) {

			$log.info("in central controller")
			console.log($cookies.get('userName'));
			$scope.userName = $cookies.get('userName');
			
			$scope.html = [];
			
			var b= "hemendra";
			$scope.a ='asdasd\nasdasdasdasd\n'+b;
			
			var titleTag = 'hppHemendra';
			
			console.log(titleTag);
			
		
			$scope.html.push($sce.trustAsHtml('<a href="http://www.w3schools.com" data-toggle="tooltip" data-html="true" data-placement="top" title='+titleTag+'> <img class="flag2" src="images/flag.png"></a>'));
			$scope.html.push($sce.trustAsHtml('<a href="http://www.w3schools.com" data-toggle="tooltip" title="Hooray!"> <img class="flag1" src="images/flag.png"></a>'));
			$scope.html.push($sce.trustAsHtml('<a href="http://www.w3schools.com" data-toggle="tooltip" title="Hooray!"> <img class="flag3" src="images/flag.png"></a>'));
					
			 
			$scope.locations = ['ShivajiNagar','Kothrud','Pimpri','Wakad','Nigdi',]
			$scope.selectedImage="images/map.png";
			$scope.changeLocation  =function(){
				console.log('in change location '+$scope.location);
				
				switch ($scope.location) {
	            case 'ShivajiNagar':
	            	$scope.selectedImage="images/Shivajinagar.JPG";
	                break;
	            case 'Kothrud':
	            	$scope.selectedImage="images/Kothrud.JPG";
	                break;
	            case 'Pimpri':
	            	$scope.selectedImage="images/Pimpri-Chinchwad.JPG";
	                break;
	            case 'Wakad':
	            	$scope.selectedImage="images/Wakad.JPG";
	                break;
	            case 'Nigdi':
	            	$scope.selectedImage="images/Nigdi.JPG";
	                break;
	            default:

	        }
			}

		} ]);
