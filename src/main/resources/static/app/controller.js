(function(){
    angular
        .module('creditCardApp')
        .controller('creditCardController',
        ['$scope','$http',
        function($scope,$http){
        console.log("controller constructing");
        $scope.test='Hello World from controller';

        $scope.addCard=function(){
            // clear any existing error flag
            $scope.error = "";
            // check the customer name is not empty
            if ( $scope.name===''){
                $scope.error = 'INVALID_NAME';
                return;
            }
            // check the limit is numeric
            if ( isNaN($scope.cardLimit)){
                $scope.error = 'INVALID_LIMIT';
                return;
            }
            
            if ( isNaN($scope.cardNumber)){
                $scope.error = 'INVALID_CARD_NO';
                return;
            }
            var requestBody={
                name:$scope.name,
                cardNumber:$scope.cardNumber,
                // cardlimit will be in pounds
                // needs converting to pence
                cardLimit:$scope.cardLimit*100,
                balance:0
            }
            $http.post('/api/v1/card',
                        requestBody)
                 .then(function(){
                     //success
                     //refresh the existing cards
                     getExistingCards();
                 },function(err){
                     if ( err.status === 400){
                         // flags the type of error on the UI
                         $scope.error = err.data.msgCode;
                     }else{
                         $scope.error = 'UNKNOWN_ERROR';
                     }
                 })
        };

        $scope.name="";
        $scope.cardNumber="";
        $scope.cardLimit="";

        $scope.exisitingCards = [];

        getExistingCards=function(){
            $http.get('/api/v1/card').then(function(response){
                // success
                // modify the currency values
                _.forEach(response.data,function(card){
                    card.cardLimit = card.cardLimit/100;
                    card.balance = card.balance/100;
                });
                $scope.existingCards=response.data;
            },function(error){
                // handle error
                $scope.error='FAILED_TO_GET_CARDS';
            });
        };

        getExistingCards();
    }]);
})();
