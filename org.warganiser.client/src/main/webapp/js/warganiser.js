'use strict';

/* App Module */
var warganiserApp = angular.module('warganiserApp', [
    'ngResource',
    'ngRoute',
    'warganiserControllers'
]);

/* Router*/
warganiserApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/warganiser', {
                templateUrl: 'partials/landing.html',
                controller: 'TournamentController'
            }).
            when('/warganiser/:tournamentId', {
                templateUrl: 'partials/tournament.html',
                controller: 'TournamentController'
            }).
            otherwise({
                /* Default to the landing page*/
                redirectTo: '/warganiser'
            });
    }]);

/* Service for Tournaments*/
warganiserApp.factory('tournamentService', ['$resource', function($resource) {

    var tournamentService = {},
    /* resource will POST to the URL, templating the name based on the name property of the object provided as an argument to the functions (e.g create)*/
        resource = $resource('service/tournament/:id', {id: '@id'}, {
            create: {
                method: 'POST',
                url: 'service/tournament/:name',
                params: {name: '@name'}
            },
            update: {
                method:'PUT'
            }
        });

    /* Initiates creation of a Tournament */
    tournamentService.create = function(tournament) {
        return resource.create(tournament,
            function(value, responseHeaders) {
                //Return the Tournament in the success case
                return value;
            },
            function(httpResponse) {
                //Return the error response in the error case
                return httpResponse;
            }
        );
    };

    /* Load a tournament by Id */
    tournamentService.load = function(tournamentId) {
        return resource.get({id: tournamentId},
            function(value, responseHeaders) {
                //Return the Tournament in the success case
                return value;
            },
            function(httpResponse) {
                //Return the error response in the error case
                return httpResponse;
            }
        );
    };

    /* Update a tournament*/
    tournamentService.update = function(tournament) {
        return resource.update(tournament,
            function(value, responseHeaders) {
                //Return the Tournament in the success case
                return value;
            },
            function(httpResponse) {
                //Return the error response in the error case
                return httpResponse;
            }
        );
    };

    return tournamentService;
}]);



