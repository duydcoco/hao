(function() {
    'use strict';

    angular
        .module('haoApp')
        .controller('GoodsDeleteController',GoodsDeleteController);

    GoodsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Goods'];

    function GoodsDeleteController($uibModalInstance, entity, Goods) {
        var vm = this;
        vm.goods = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Goods.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
