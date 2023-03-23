'use strict';

App.controller('menuController', ['$scope', '$http', function ($scope, $http) {
    $scope.OpcionesMenu = [];
    $http.get('/authoriza/menu/getmenu').then(function (data) {
        $scope.OpcionesMenu = data.data;
    }, function (error) {
        alert('Error get menu ');
    });
}])
