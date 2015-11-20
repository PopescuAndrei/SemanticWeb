semApp.controller('registerController', ['$scope', '$http',
    function ($scope, $http) {
        $scope.form = {};
        $scope.form.user = {};
        $scope.requiredErrorMessage = "Please fill out this form";

        $scope.reset = function () {
            console.log(user);
            this.user = {};
        }

        $scope.create = function (newUser) {
            $http({url: 'http://localhost:8080/SemanticWeb/mvc/users/', method: 'POST', data: user, headers: {
                    'Content-Type': 'application/json'
                }}).
                    success(function (data) {
                        console.log(user);
                        console.log(data);
                        console.log(user);
                        $scope.user = data;
                    });
        }
    }]);