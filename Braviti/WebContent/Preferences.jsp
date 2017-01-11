<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>

<body ng-controller="preferenceCtl">
<div class="form-data">
 <div class="panel panel-default">
 <div class="panel-heading preferenceHeading" style="background-color:#337ab7;color:white">Preferences</div>
<div class="panel-body">
<div class="row">
 
<div class="col-md-4"  >


<div class="panel panel-default" >
                    <!-- Default panel contents 1 -->
                    <div class="panel-heading">Category</div>
                    <div class="panel-body">
                        <form>
					        <div class="form-group" ng-repeat="value in categoryList">					         
					            <input
					              type="checkbox"
					              ng-model="value.selected"   					            				            
					             ng-checked="value.selected == true"
					            /> <!-- ng-change="setOutput(typeKey, $index, value)" --> <label>{{value.name}}
					          </label>
					        </div>
					       </form> 
					     
                    </div>                    
                </div></div>
    <div class="col-md-4"><div class="panel panel-default">
                    
                    <div class="panel-heading">Price</div>
                    <div class="panel-body">
                         
					        <div ng-repeat="value in priceList">					         
					            <input
					              type="checkbox"
					              ng-model="x"
					              ng-change="setOutput(value)"
					              ng-class="{'checked':isExist(value)!=-1}"
					               ng-checked="isExist(value)!=-1"
					            > <label>{{value}}
					          </label>
					        </div>
					     
                    </div>
                </div>
  
    </div>
 
</div></div>
<div class="panel-footer clearfix">

<div>
				            <button class="btn btn-default preferenceHeading pull-right" style="background-color:#337ab7" ng-click="applyFilter()">Done</button>
				        </div>
</div>

</div>


	
	<div class="clearfix">
	</div>
</div>

</body>
</html>