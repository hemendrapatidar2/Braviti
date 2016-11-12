var myApp = angular.module("centralApp", ['ngCookies','ngSanitize'])

myApp.controller('centralCtl', [ '$scope', '$http', '$window', '$log','$cookies','$sce',
		function($scope, $http, $window, $log,$cookies,$sce) {

			$log.info("in central controller")
			console.log($cookies.get('userName'));
			$scope.userName = $cookies.get('userName');
			
			$scope.html = [];
			
			var titleTag = 'hppHemendra';
			
			console.log(titleTag);
			
			var stylesheet="position: absolute;top: 100px;left: 80px;z-index: 2;height:50px;width:50px;";
			
					
			 
			$scope.locations = ['ShivajiNagar','Kothrud','Pimpri','Wakad','Nigdi',]
			$scope.selectedImage="images/map.png";
			$scope.changeLocation  =function(){
				console.log('in change location '+$scope.location);
				
				$scope.outlets = ['RelianceMart','Central','DMart','Dunkin']
				
				
				$http({
                    method: "GET",
                    url: "data/useroffers",
                    params: {
                    	 location: $scope.location,
                         userName: $scope.userName
                       
                    },
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                .then(
                    function(reponse) {
                            $scope.data =reponse.data;
                            var res = $scope.data;
                            console
                            .log($scope.data)
                            document.getElementById("ballouns").innerHTML="";
                            var differ=Math.random()*100; 
                            var top=160,left=70;
                            
                            for(var index=0;index <$scope.data.length;index++){
                            	document.getElementById("ballouns").innerHTML = document.getElementById("ballouns").innerHTML+'<a  href="#" data-toggle="tooltip" data-html="true" data-placement="top" title='+res[index].storeName+'> <img style="position: absolute;top: '+top+'px;left: '+left+'px;z-index: 2;height:50px;width:50px;" src="images/flag.png"></a>';
                            	/*top=top+Math.random()*100;
                            	left = left +Math.random()*100;*/
                            	top=top+$scope.getRandomArbitrary(0.4,0.6)*100;
                            	left = left +$scope.getRandomArbitrary(0.2,0.4)*100;
                            	
                            	if(top>300){
                            		console.log("greater then 300");
                            		top=200+index*10;
                            	}
                            	if(left>300){
                            		console.log("greater then 300");
                            		left=150+index*10;
                            	}

                            
                            }
                            
                    });
				
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
			
		$scope.getRandomArbitrary = function(min, max) {
			console.log("inside random function");
			    return Math.random() * (max - min) + min;
			}
			
			$scope.logout = function(){
				 $window.location.href = './Login.jsp#/';
			}

		} ]);
