banca.controller('Horario', ['$scope', '$http', function($scope, $http) {

	$scope.inicio = function() {
		var inicio = new Date();
		
		inicio.setHours(0);
		inicio.setMinutes(0);
		inicio.setSeconds(0);
		inicio.setMilliseconds(0);
		
		return inicio;
	}
	
	$scope.termino = function() {
		var termino = new Date();
		
		termino.setHours(0);
		termino.setMinutes(0);
		termino.setSeconds(0);
		termino.setMilliseconds(0);
		
		return termino;
	}
	
	$scope.limpar = function() {
		$scope.agendamentoHorario = {
			inicio: $scope.inicio(), termino: $scope.termino()
		};
	};
	
	$scope.salvar = function() {
		console.log($scope.agendamentoHorario);
		
		if ($scope.agendamentoHorario.codigo === undefined) {
			$http.post('/bancas/service/agendamento/horario/persistir', $scope.agendamentoHorario).success(function(response) {
				$scope.agendamentoHorarios.push(response);
			});
		} else {
			$http.put('/bancas/service/agendamento/horario/alterar', $scope.agendamentoHorario).success(function(response) {
				$scope.listar();
			});
		}
		
		$scope.limpar();
	};
	
	$scope.listar = function() {
		$http.get('/bancas/service/agendamento/horario').success(function(response) {
			$scope.agendamentoHorarios = response;
		});
	};
	
	$scope.editar = function(codigo) {
		if (codigo === undefined) {
			$scope.limpar();
		} else {
			$scope.agendamentoHorario  = $scope.agendamentoHorarios[codigo];
		}
	};
	
	$scope.deletar = function(codigo) {
		$http.delete('/bancas/service/agendamento/horario/excluir/' + codigo).success(function(response) {
			$scope.agendamentoHorarios = response;
		});
	};
	
	$scope.limpar();
	$scope.listar();

}]);
