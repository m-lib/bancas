banca.controller('Menu', ['$scope', '$location', function($scope, $location) {

	$scope.index = function() {
		$location.path('/bancas');
	};
	
	$scope.exibirDias = function() {
		$location.path('/dias');
	};
	
	$scope.exibirHorarios = function() {
		$location.path('/horarios');
	};
	
	$scope.exibirTrabalhos = function() {
		$location.path('/trabalhos');
	};

}]);
