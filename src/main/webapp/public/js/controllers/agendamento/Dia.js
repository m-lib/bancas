banca.controller('Dia', ['$scope', '$http', function($scope, $http) {

	$scope.limpar = function() {
		$scope.agendamentoDia = {
			
		};
	};
	
	$scope.salvar = function() {
		if ($scope.agendamentoDia.codigo === null) {
			$http.post('/bancas/service/agendamento/dia/persistir', $scope.agendamentoDia).success(function(response) {
				$scope.agendamentoDias.push(response);
			});
		} else {
			$http.put('/bancas/service/agendamento/dia/alterar', $scope.agendamentoDia).success(function(response) {
				$scope.listar();
			});
		}
		
		$scope.limpar();
	};
	
	$scope.listar = function () {
		$http.get('/bancas/service/agendamento/dia').success(function(response) {
			$scope.agendamentoDias = response;
		});
	};
	
	$scope.editar = function(codigo) {
		if (codigo === undefined) {
			$scope.limpar();
		} else {
			$scope.agendamentoDia  = $scope.agendamentoDias[codigo];
		}
	};
	
	$scope.deletar = function(codigo) {
		$http.delete('/bancas/service/agendamento/dia/excluir/' + codigo).success(function(response) {
			$scope.agendamentoDias = response;
		});
	};
	
	$scope.limpar();
	$scope.listar();

	$scope.open = function($event) {
	    $scope.opened = true;
	};

}]);
