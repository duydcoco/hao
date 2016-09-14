(function() {
    'use strict';

    angular
        .module('haoApp')
        .controller('StrategyDeleteController',StrategyDeleteController);

    StrategyDeleteController.$inject = ['$uibModalInstance', 'entity', 'Strategy'];

    function StrategyDeleteController($uibModalInstance, entity, Strategy) {
        var vm = this;
        vm.strategy = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Strategy.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
