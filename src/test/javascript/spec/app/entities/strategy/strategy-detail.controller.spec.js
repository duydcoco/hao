'use strict';

describe('Controller Tests', function() {

    describe('Strategy Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockStrategy;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockStrategy = jasmine.createSpy('MockStrategy');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Strategy': MockStrategy
            };
            createController = function() {
                $injector.get('$controller')("StrategyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'haoApp:strategyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
