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
            when('/warganiser/:tournamentName', {
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
        resource = $resource('service/tournament/:name', {name: '@name'}, {
            create: {
                method: 'POST'
            }
        });

    /* Initiates creation of a Tournament and returns a promise.*/
    tournamentService.create = function(tournament) {
        return resource.create(tournament,
            function(value, responseHeaders) {
                //Return the Tournament in the success case
                return value;
            },
            function(httpResponse) {
                //Return the error response in the success case
                return httpResponse;
            });
    };

    return tournamentService;
}]);



