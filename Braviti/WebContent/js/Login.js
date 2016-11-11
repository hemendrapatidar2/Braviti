var myApp = angular.module("LoginApp", ['ui.bootstrap','toaster','ngCookies'])

myApp
    .controller(
        'LoginCtl', [
            '$scope',
            '$http',
            '$window',
            '$uibModal',
            '$log',
            '$compile',
            '$cookies',
            function($scope, $http, $window, $uibModal, $log,$compile,$cookies) {
                console.log('in logon ctl ');
                var $ctrl = this;
                $scope.errMsg = [];
                $scope.login = function() {
                    console.log($scope.userId + " " +
                        $scope.password);


                    console.log("Length " + $scope.validateForm().length);
                    $scope.validateForm();
                    if ($scope.errMsg.length > 0) {
                        $ctrl.open("validationModal");
                        return;

                    }
                    $cookies.put('userName', $scope.userId);
                    $window.location.href = './Central.jsp#/';
                   /* if(angular.equals('Raj',$scope.userId)&&angular.equals('Raj',$scope.password)){
                    	  $cookies.put('userName', $scope.userId);
                    	
                    	  $window.location.href = './Central.jsp#/';
                    }else{
                    	 $scope.errMsg.push("Invalid User Id OR Password");
                         $ctrl.open("validationModal");
                    }*/
                   /* $http({
                            method: "POST",
                            url: "rest/user/authenticate",
                            params: {
                                userId: $scope.userId,
                                password: $scope.password
                            },
                            headers: {
                                'Content-Type': 'application/json'
                            }
                        })
                        .then(
                            function(reponse) {
                                console
                                    .log("Getting reponse >>>> " +
                                        reponse.data)

                                if (angular.equals(true,
                                        reponse.data)) {
                                    $window.location.href = './Central.jsp#/';

                                } else {
                                    $scope.errMsg.push("Invalid User Id OR Password");
                                    $ctrl.open("validationModal");
                                }
                            });*/
                }

                $ctrl.animationsEnabled = true;

                $ctrl.open = function(actionName) {
                    console.log("inside open function >>> " + $scope.errMsg);

                    var templateUrl=null;
                    var controller=null;
                    
                    if(angular.equals("validationModal",actionName)){
                    	templateUrl='validation.html';
                    	controller='ValidationModalInstanceCtrl';
                    }
                    if(angular.equals("forgotPasswordModal",actionName)){
                    	templateUrl='ForgotPassword.html';
                    	controller='ForgotPasswordModalInstanceCtrl';
                    }
                    
                    var modalInstance = $uibModal.open({
                        animation: $ctrl.animationsEnabled,
                        ariaLabelledBy: 'modal-title',
                        ariaDescribedBy: 'modal-body',
                        templateUrl: templateUrl,
                        controller: controller,
                        controllerAs: '$ctrl',
                        resolve: {
                            items: function() {
                                return $scope.errMsg;
                            }
                        }
                    });
                    modalInstance.result.then(
                        function() {
                            $log.info('Modal dismissed at: ' +
                                new Date());
                        });
                };

                $scope.validateForm = function() {
                    console.log("inside validate form    ");
                    $scope.errMsg = [];
                    if (angular.equals("", $scope.userId) || angular.equals(undefined, $scope.userId)) {
                        console.log("User ID is Required");
                        $scope.errMsg.push("User ID is Required");
                    }
                    if (angular.equals("", $scope.password) || angular.equals(undefined, $scope.password)) {
                        $scope.errMsg.push("Password Required");
                    }
                    return $scope.errMsg;

                }
                $scope.forgotPassword=function(){
                	console.log("inside forgotPassword");
                	/* var compiledeHTML = $compile("<div forgot-Password></div>")($scope);
                     $("#d").append(compiledeHTML);
                     console.log("after function");*/
                	 $ctrl.open("forgotPasswordModal");
                	
                }
                $scope.register=function(){
                	$window.location.href = './Register.jsp#/';
                }
            }
        ]);

myApp.controller('ForgotPasswordModalInstanceCtrl', function($uibModalInstance, items, $scope, $log,toaster) {
    var $ctrl = this;
    $log.log("inside ForgotPasswordModalInstanceCtrl");

    $ctrl.items = items;
   
    $ctrl.submit = function() {
    	 toaster.pop('success', "success", "Password Changed Succefully");
        $uibModalInstance.dismiss('cancel');
    };
});

myApp.controller('ValidationModalInstanceCtrl', function($uibModalInstance, items, $scope, $log) {
    var $ctrl = this;
    $log.log("inside ValidationModalInstanceCtrl");

    $ctrl.items = items;
   
    $ctrl.cancel = function() {
        $uibModalInstance.dismiss('cancel');
    };
});



myApp.directive('forgotPassword', function() {	
	console.log("inside template");
	
	return {
	      templateUrl: 'ForgotPassword.html'
	    };  
});


