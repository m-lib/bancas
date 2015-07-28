banca.controller('Trabalho', ['$scope', '$http', function($scope, $http) {

	$scope.buscar = function() {
		$scope.paginacao.elementos = new Array();
		$scope.paginacao.elemento = $scope.trabalho;
		$http.post('/bancas/service/trabalho/buscar', $scope.paginacao).success(function(response) {
			$scope.trabalhos = response.elementos;
			$scope.paginacao = response;
		});
	};
	
	$scope.limpar = function() {
		$scope.trabalho = {
			alunos: new Array()
		};
		
		$scope.paginacao = {
			
		};
		
		$scope.aluno = {
			
		};
		
		$scope.buscar();
	};
	
	$scope.salvar = function() {
		if ($scope.trabalho.codigo === undefined) {
			$http.post('/bancas/service/trabalho/persistir', $scope.trabalho).success(function(response) {
				$scope.limpar();
			});
		} else {
			$http.put('/bancas/service/trabalho/alterar', $scope.trabalho).success(function(response) {
				$scope.limpar();
			});
		}
	};
	
	/*$scope.listar = function() {
		$http.get('/bancas/service/trabalho').success(function(response) {
			$scope.trabalhos = response;
		});
	};*/
	
	$scope.editar = function(index) {
		if (index === undefined) {
			$scope.limpar();
		} else {
			$scope.trabalho = $scope.trabalhos[index];
		}
	};
	
	$scope.excluir = function(codigo, index) {
		$http.delete('/bancas/service/trabalho/excluir/' + codigo).success(function(response) {
			$scope.trabalhos.splice(index, 1);
			$scope.limpar();
		});
	};
	
	$scope.listarCursos = function() {
		$http.get('/bancas/service/curso').success(function(data) {
			$scope.cursos = data;
		});
	};
	
	$scope.limpar();
	$scope.listarCursos();
	
	$scope.pesquisarOrientador = function(nome) {
		//se a pesquisa for vazia
		if (nome == "") {
			//retira o autocomplete
			$scope.exibirOrientadores = false;
		} else {
			//espera 500 milisegundos
			setTimeout(function() {
				//pesquisa no banco via AJAX
				$http.get('service/pessoa/buscar/docente/' + nome).success(function(response) {
					//coloca o autocomplemento
					$scope.exibirOrientadores = true;
					//JSON retornado do banco
					$scope.orientadores = response;
		        }).error(function(response) {
					console.log("Ocorreu um erro inesperado...");
		        });
			}, 500);
		}
	};
	
	$scope.selecionarOrientador = function(orientador) {
		$scope.trabalho.orientador.pessoa.nome = orientador.pessoa.nome;
		$scope.trabalho.orientador.codigo = orientador.codigo;
		$scope.exibirOrientadores = false;
	};
	
	$scope.pesquisarAlunos = function(nome) {
		if (nome == "") {
			$scope.exibirAlunos = false;
		} else {
			setTimeout(function() {
				$http.get('service/pessoa/buscar/aluno/' + nome).success(function(response) {
					$scope.exibirAlunos = true;
					$scope.alunos = response;
				}).error(function(response) {
					console.log("Ocorreu um erro inesperado...");
				});
			}, 500);
		}
	};
	
	$scope.selecionarAluno = function(aluno) {
		$scope.trabalho.alunos.push(aluno);
		$scope.aluno.nome = new String();
		$scope.exibirAlunos = false;
	};
	
	$scope.removerAluno = function(index) {
		$scope.trabalho.alunos.splice(index, 1);
	};

}]);
