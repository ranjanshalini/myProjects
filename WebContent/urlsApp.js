/**
 * 
 */
var app = angular.module('myApp', [])
app.controller('urlCtrl', function($scope,$http) {
$scope.uName = '';
$scope.pwd = '';
$scope.desc = '';
$http({
    method : 'GET',
    url : 'DbManager'
}).success(function(data, status, headers, config) {
    $scope.urls = data;
    console.log($scope.urls);
}).error(function(data, status, headers, config) {
    // called asynchronously if an error occurs
    // or server returns response with an error status.
});


$scope.edit = true;
$scope.error = false;
$scope.incomplete = false; 
$scope.editUrl = function(id) {
  if (id == 'new') {
    $scope.edit = true;
    $scope.incomplete = true;
    $scope.uName = '';
    $scope.pwd = '';
    $scope.desc = '';
    $scope.id = id;
    } else {
    $scope.edit = false;
    $scope.id = id;
    $scope.tableId = $scope.urls[id-1].tableId;
    $scope.uName = $scope.urls[id-1].uName;
    $scope.pwd = $scope.urls[id-1].pwd; 
    $scope.desc = $scope.urls[id-1].desc;
  }
};
$scope.commitSave = function(){
	$http({
		method : 'POST',
		url: 'DbManager',
		data: {id : $scope.id,tableId:$scope.tableId, uName : $scope.uName, pwd : $scope.pwd, desc : $scope.desc},
		headers : {'Content-type' : 'application/json'} 
		}).success(function(data, status, headers, config){
			var id = $scope.id;
			
			if(id==0){
				$scope.urls.push(data);
			}
			else{
			    $scope.urls[id-1].tableId = $scope.tableId;
			    $scope.urls[id-1].uName = $scope.uName;
			    $scope.urls[id-1].pwd = $scope.pwd; 
			    $scope.urls[id-1].desc = $scope.desc;
			   
			}
			 $scope.initFirst();
			 $scope.reload();

		}).error(function(data, status, headers, config){

		//set error message.

		});
};


})
