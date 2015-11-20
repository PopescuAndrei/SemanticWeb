semApp.controller('projectController', ['$scope', '$http',
    function ($scope, $http) {

        $scope.projects = [];
        
        $http({url: 'http://localhost:8080/SemanticWeb/mvc/projects', method: 'GET'}).
                success(function (data) {
                    $scope.projects = data;
                });


    }]);