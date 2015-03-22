'use strict';

var warganiserControllers = angular.module('warganiserControllers', []);

/* Controller for Tournament creation screen*/
warganiserControllers.controller('TournamentController', ['$scope', '$location', 'tournamentService',
    function($scope, $location, tournamentService) {
        $scope.tournament = {};

        /* Create a new tournament with the provided name*/
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

        /* Update a tournament*/
        $scope.update = function(tournament) {

            var promise = tournamentService.update(tournament).$promise;

            promise.then(function(tournament) {
                $scope.tournament = tournament;
            }, function(errorDTO) {
                //TODO on error, display the error messages
                alert('failed');
            });

        };

    }
]);

/* Controller for Tournament creation screen*/
warganiserControllers.controller('TournamentListController', ['$scope', 'tournamentService',
    function($scope, tournamentService) {
        /* Load all the tournaments on instantiation*/
        $scope.tournaments = tournamentService.list();
    }
]);

/* Controller for Tournament editing screen*/
warganiserControllers.controller('TournamentEditController', ['$scope', '$routeParams', 'tournamentService',
    function($scope, $routeParams, tournamentService) {

        $scope.tournament = tournamentService.load($routeParams.tournamentId);
    }
]);