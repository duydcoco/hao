(function() {
    'use strict';

    angular
        .module('haoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('strategy', {
            parent: 'entity',
            url: '/strategy?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'haoApp.strategy.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/strategy/strategies.html',
                    controller: 'StrategyController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('strategy');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('strategy-detail', {
            parent: 'entity',
            url: '/strategy/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'haoApp.strategy.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/strategy/strategy-detail.html',
                    controller: 'StrategyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('strategy');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Strategy', function($stateParams, Strategy) {
                    return Strategy.get({id : $stateParams.id});
                }]
            }
        })
        .state('strategy.new', {
            parent: 'strategy',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/strategy/strategy-dialog.html',
                    controller: 'StrategyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                divType: null,
                                divData: null,
                                msgCode: null,
                                errorMsg: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('strategy', null, { reload: true });
                }, function() {
                    $state.go('strategy');
                });
            }]
        })
        .state('strategy.edit', {
            parent: 'strategy',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/strategy/strategy-dialog.html',
                    controller: 'StrategyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Strategy', function(Strategy) {
                            return Strategy.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('strategy', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('strategy.delete', {
            parent: 'strategy',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/strategy/strategy-delete-dialog.html',
                    controller: 'StrategyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Strategy', function(Strategy) {
                            return Strategy.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('strategy', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
