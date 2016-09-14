(function() {
    'use strict';

    angular
        .module('haoApp')
        .factory('StrategySearch', StrategySearch);

    StrategySearch.$inject = ['$resource'];

    function StrategySearch($resource) {
        var resourceUrl =  'api/_search/strategies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
