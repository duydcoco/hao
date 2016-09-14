(function() {
    'use strict';

    angular
        .module('haoApp')
        .controller('StrategyDialogController', StrategyDialogController);

    StrategyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Strategy'];

    function StrategyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Strategy) {
        var vm = this;
        vm.strategy = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('haoApp:strategyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.strategy.id !== null) {
                Strategy.update(vm.strategy, onSaveSuccess, onSaveError);
            } else {
                Strategy.save(vm.strategy, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
