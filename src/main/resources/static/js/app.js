'use strict';

angular.module('app', ['ngRoute', 'ngResource'])
    .config(function ($routeProvider, $httpProvider) {
        $routeProvider
            .when('/list', {
                templateUrl: 'partials/list.html',
                controller: 'ListController',
                controllerAs: 'listCtrl'
            })
            .when('/details/:id', {
                templateUrl: 'partials/details.html',
                controller: 'DetailsController',
                controllerAs: 'detailsCtrl'
            })
            .when('/new', {
                templateUrl: 'partials/new.html',
                controller: 'NewController',
                controllerAs: 'newCtrl'
            })
            .when('/login', {
                templateUrl: 'partials/login.html',
                controller: 'AuthenticationController',
                controllerAs: 'authController'
            })
            .when('/register', {
                templateUrl: 'partials/register.html',
                controller: 'RegisterController',
                controllerAs: 'regCtrl'
            })
            .otherwise({
                redirectTo: '/list'
            });
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    })
    .constant('LOGIN_ENDPOINT', '/login')
    .constant('DISCOVERY_ENDPOINT', '/api/discoveries/:id')
    .constant('LOGOUT_ENDPOINT', '/logout')
    .constant('USER_ENDPOINT', '/api/users/:id')
    .factory('Discovery', function($resource, DISCOVERY_ENDPOINT) {
        return $resource(DISCOVERY_ENDPOINT);
    })
    .factory('User', function ($resource, USER_ENDPOINT) {
        return $resource(USER_ENDPOINT);
    })
    .service('AuthenticationService', function($http, LOGIN_ENDPOINT) {
        this.authenticate = function(credentials, successCallback) {
            var authHeader = {Authorization: 'Basic ' + btoa(credentials.username+':'+credentials.password)};
            var config = {headers: authHeader};
            $http
                .post(LOGIN_ENDPOINT, {}, config)
                .then(function success(value) {
                    $http.defaults.headers.post.Authorization = authHeader.Authorization;
                    successCallback();
                }, function error(reason) {
                    console.log('Login error');
                    console.log(reason);
                });
        }
        this.logout = function(successCallback) {
            delete $http.defaults.headers.post.Authorization;
            successCallback();
        }
    })
    .service('Discoveries', function(Discovery) {
       
        this.getAll = function () {
            return Discovery.query();
        }
        
        this.get = function (index) {
            return Discovery.get({id: index});
        }
        
        this.add = function (discovery) {
            discovery.$save();
        }
    })
    .service('Users', function (User) {

        this.getAll = function () {
            return User.query();
        }

        this.get = function (index) {
            return User.get({id: index})
        }

        this.add = function (user) {
            user.$save();
        }
    })
    .controller('ListController', function(Discoveries) {
        var vm = this;
        vm.discoveries = Discoveries.getAll();
    })
    .controller('DetailsController', function($routeParams, Discoveries, $location) {
        var vm = this;
        var discoveryIndex = $routeParams.id;
        vm.discovery = Discoveries.get(discoveryIndex);
    })
    .controller('NewController', function(Discoveries, Discovery, $location, $rootScope) {
        var vm = this;
        vm.discovery = new Discovery();
        vm.saveDiscovery = function () {
            if(vm.discovery.name && vm.discovery.description){
                Discoveries.add(vm.discovery);
                vm.discovery = new Discovery();
                $location.path('/list')
                vm.validationError = false;
            } else {
                $location.path('/new')
                vm.validationError = true;
            }

        }
    })
    .controller('AuthenticationController', function($rootScope, $location, AuthenticationService) {
        var vm = this;
        vm.credentials = {};
        var loginSuccess = function() {
            $rootScope.authenticated = true;
            $location.path('/new');
        }
        vm.login = function() {
            AuthenticationService.authenticate(vm.credentials, loginSuccess);
        }
        var logoutSuccess = function() {
            $rootScope.authenticated = false;
            $location.path('/');
        }
        vm.logout = function() {
            AuthenticationService.logout(logoutSuccess);
        }
    })
    .controller('RegisterController', function (Users, User, $location) {
        var vm = this;
        vm.validationError = false;
        vm.user = new User();
        console.log(vm.user);
        vm.saveUser = function () {
            if(vm.user.username && vm.user.password){
                Users.add(vm.user);
                vm.user = new User();
                $location.path("/list")
                vm.validationError = false;
            } else {
                $location.path("/register")
                vm.validationError = true;
            }


        }

    });

