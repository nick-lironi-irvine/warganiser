'use strict';

/* App Module */
var tournamentApp = angular.module('tournamentApp', [])
    .controller('TournamentController', ['$scope', '$http', function($scope, $http) {
        $scope.tournament = {};

        $scope.create = function() {
            $http.post('service/tournament',$scope.tournament)
                .success(function(data, status, headers, config) {
                    alert('created tournament with name '+$scope.tournament.name);
                }).error(function(data, status, headers, config) {
                        debugger;
                        alert('fail');
                });
        };
}]);

