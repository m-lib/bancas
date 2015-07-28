var banca = angular.module('banca', ['ngRoute', 'ui.bootstrap']);

banca.config(function($routeProvider) {
	$routeProvider.when('/dias', {
		controller: 'Dia',
		templateUrl: 'public/view/agendamento/dia.html'
	}).when('/horarios', {
		controller: 'Horario',
		templateUrl: 'public/view/agendamento/horario.html'
	}).when('/trabalhos', {
		controller: 'Trabalho',
		templateUrl: 'public/view/trabalho/trabalho.html'
	}).otherwise({
		redirectTo: '/'
	});
});

banca.directive('autocomplete', function() {
	return function(scope, element, attribute) {
		element.css({
			width: element.parent()[0].clientWidth
		});
	};
});

banca.directive('loading', ['$http', function ($http) {
	return {
		restrict: 'A',
		link: function(scope, element, attribute) {
			element.hide();
			
			scope.isLoading = function() {
				return $http.pendingRequests.length > 0;
			};
			
			scope.$watch(scope.isLoading, function(check) {
				setTimeout(function() {
					if(scope.isLoading()) {
						element.show();
					} else {
						element.hide();
					}
				}, 300);
			});
		}
	};
}]);

banca.directive('show', ['$http', function ($http) {
	return {
		restrict: 'A',
		link: function(scope, element, attribute) {
			element.hide();
			setTimeout(function() {
				element.show();
			}, 400);
		}
	};
}]);

/*banca.directive('loading', ['$http', function ($http) {
	return {
		restrict: 'A',
		link: function(scope, element, attribute) {
			scope.isLoading = function() {
				return $http.pendingRequests.length > 0;
			};
			
			scope.$watch(scope.isLoading, function(check) {
				if(check) {
					element.hide();
				} else {
					element.show();
				}
			});
		}
	};
}]);*/
