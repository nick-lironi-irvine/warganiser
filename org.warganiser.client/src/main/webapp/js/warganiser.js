'use strict';

/* App Module */
var tournamentApp = angular.module('tournamentApp', ['ngResource']);

/* Service for Tournaments*/
tournamentApp.factory('tournamentService', ['$resource', function($resource) {

    var tournamentService = {},
        resource = $resource('service/tournament/:name', {name: '@name'}, {
            create: {
                method: 'POST'
            }
        });

    tournamentService.create = function(tournament) {
        return resource.create(tournament,
            function(value, responseHeaders) {
                //alert('created tournament with name '+value.name);
                return value;
            },
            function(httpResponse) {
                alert('fail');
            });
    };

    return tournamentService;
}]);

/* Controller for Tournament creation screen*/
tournamentApp.controller('TournamentController', ['$scope', 'tournamentService', function($scope, tournamentService) {
    $scope.tournament = {};

    $scope.create = function(tournament) {
        $scope.tournament = tournamentService.create(tournament);
    };

}]);

