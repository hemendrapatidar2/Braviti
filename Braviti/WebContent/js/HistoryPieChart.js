

var myApp = angular.module("centralApp", ['ngCookies','ngSanitize'])

myApp.controller('centralCtl', [ '$scope', '$http', '$window', '$log','$cookies','$sce',
		function($scope, $http, $window, $log,$cookies,$sce) {

			$log.info("in central controller")
			console.log($cookies.get('userName'));
			$scope.userName = $cookies.get('userName');
			$scope.location = "Nigdi";
			$scope.html = [];
			
			var titleTag = 'Pie Chart';
			var arr = [];
			console.log(titleTag);
			
			var stylesheet="position: absolute;top: 100px;left: 80px;z-index: 2;height:50px;width:50px;";
			
			$scope.data="";	
			$http({
                method: "GET",
                url: "data/useroffers/pieChartData",
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
                	     console.log($scope.data);
                	     
                	    /* for (var i = 0; i<$scope.data.length; i++) {
                	    	 if(i!=$scope.data.length-1)
                             var item = "{name:'" + $scope.data[i].category + "',y:" + $scope.data[i].percentAmt + "} ,";
                	    	 else{
                	    		 var item = " {name:'" + $scope.data[i].category + "',y:" + $scope.data[i].percentAmt + "}";
                	    	 }
                             console.log(item);
                             arr.push(item);
                         }*/
                	     
                	     
                });
			
			$(function () {
			    Highcharts.chart('container2', {
			        chart: {
			            plotBackgroundColor: null,
			            plotBorderWidth: null,
			            plotShadow: false,
			            type: 'pie'
			        },
			        title: {
			            text: 'Transaction History for Raj'
			        },
			        tooltip: {
			            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
			        },
			        plotOptions: {
			            pie: {
			                allowPointSelect: true,
			                cursor: 'pointer',
			                dataLabels: {
			                    enabled: true,
			                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
			                    style: {
			                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
			                    }
			                }
			            }
			        },
			        series: [{
			            name: 'Brands',
			            colorByPoint: true,
			            data: 			            	
			            	[{name:'Electronics',y:73.82245},
			                   {name:'Clothing',y:21.893171},
			                   {name:'Footware',y:4.2843776}]
			        }]
			    });
			});
			
					
			 
			/*$scope.locations = ['ShivajiNagar','Kothrud','Pimpri','Wakad','Nigdi',]
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
                            var top=160,left=90;
                            
                            for(var index=0;index <$scope.data.length;index++){
                            	document.getElementById("ballouns").innerHTML = document.getElementById("ballouns").innerHTML+'<a  href="#" data-toggle="tooltip" data-html="true" data-placement="top" title='+res[index].storeName+'> <img style="position: absolute;top: '+top+'px;left: '+left+'px;z-index: 2;height:50px;width:50px;" src="images/flag.png"></a>';
                            	top=top+Math.random()*100;
                            	left = left +Math.random()*100;
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
			*/
			
			$scope.logout = function(){
				 $window.location.href = './Login.jsp#/';
			}

		} ]);
