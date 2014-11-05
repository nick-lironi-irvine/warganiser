'use strict';

/* App Module */
var tournamentApp = angular.module('tournamentApp', []).controller('TournamentController', ['$scope', function($scope) {
    $scope.create = function(tournament) {
        alert('create');
    };
}]);

