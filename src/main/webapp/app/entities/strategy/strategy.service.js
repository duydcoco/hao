(function() {
    'use strict';
    angular
        .module('haoApp')
        .factory('Strategy', Strategy);

    Strategy.$inject = ['$resource'];

    function Strategy ($resource) {
        var resourceUrl =  'api/strategies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
