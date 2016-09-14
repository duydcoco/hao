(function() {
    'use strict';

    angular
        .module('haoApp')
        .controller('StrategyDetailController', StrategyDetailController);

    StrategyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Strategy'];

    function StrategyDetailController($scope, $rootScope, $stateParams, entity, Strategy) {
        var vm = this;
        vm.strategy = entity;
        
        var unsubscribe = $rootScope.$on('haoApp:strategyUpdate', function(event, result) {
            vm.strategy = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
