(function() {
    'use strict';

    angular
        .module('haoApp')
        .controller('GoodsDialogController', GoodsDialogController);

    GoodsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Goods'];

    function GoodsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Goods) {
        var vm = this;
        vm.goods = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('haoApp:goodsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.goods.id !== null) {
                Goods.update(vm.goods, onSaveSuccess, onSaveError);
            } else {
                Goods.save(vm.goods, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.lastUpdateTime = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
