(function() {
    'use strict';
    angular
        .module('haoApp')
        .factory('Goods', Goods);

    Goods.$inject = ['$resource', 'DateUtils'];

    function Goods ($resource, DateUtils) {
        var resourceUrl =  'api/goods/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.lastUpdateTime = DateUtils.convertLocalDateFromServer(data.lastUpdateTime);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.lastUpdateTime = DateUtils.convertLocalDateToServer(data.lastUpdateTime);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.lastUpdateTime = DateUtils.convertLocalDateToServer(data.lastUpdateTime);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
