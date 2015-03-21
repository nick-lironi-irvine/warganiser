'use strict';

var warganiserControllers = angular.module('warganiserControllers', []);

/* Controller for Tournament creation screen*/
warganiserControllers.controller('TournamentController', ['$scope', '$location', 'tournamentService',
    function($scope, $location, tournamentService) {
        $scope.tournament = {};

        $scope.create = function(tournament) {

            var promise = tournamentService.create(tournament).$promise;

            promise.then(function(tournament) {
                //On success, update the tournament in scope and redirect to the tournament editing page
                $scope.tournament = tournament;
                $location.path('/warganiser/' + tournament.id);
            }, function(errorDTO) {
                //TODO on error, display the error messages and stay on this page
                alert('failed');
            });

        };

    }
]);

/* Controller for Tournament editing screen*/
warganiserControllers.controller('TournamentEditController', ['$scope', '$routeParams', 'tournamentService',
    function($scope, $routeParams, tournamentService) {

        $scope.tournament = tournamentService.load($routeParams.tournamentId);
    }
]);