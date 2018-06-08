/**
 * 
 */
//读取关联采购数据
function InitAssociateData(){
	$.post(
			"ajax/associate_table_data_sale",//请求的地址
			{
				"randomnumber":Math.random()+""
			},//需要提交到请求地址的参数
				function( returnedString )     //回调
				{
					var data = returnedString;
					var jsonObj = eval("("+data+")");
					$('#associate_sale_order_table').datagrid('loadData',jsonObj.associate_sale_table_data)
				}
	);
}



//点击确认关联
function ClickAssociate(){
	if($('#associate_purchase_order_table').datagrid('getSelected')==null){
		alert('请选择一行数据！');
	}else{
		$('#associate_alert').window('center');
		$('#associate_alert').window('open');
	}
}


//确认关联采购单
function ConfirmAssociate(){
	//写入关联数据
	$.post(
		"ajax/associate_data",//请求的地址
		{
			"sale_contract_number":$('#associate_sale_order_table').datagrid('getSelected').sale_contract_number,
			"purchase_contract_number":$('#associate_purchase_order_table').datagrid('getSelected').purchase_contract_number,
			"creater":document.getElementById("usertitle").innerHTML.substring(3),
			"createtime":new Date().Format("yyyy-MM-dd hh:mm:ss") ,
			"randomnumber":Math.random()+""
		},//需要提交到请求地址的参数
		function( returnedString )     //回调
		{
			var data = returnedString;
			var jsonObj = eval("("+data+")");
			console.log(jsonObj);
			if(jsonObj.tip){
				alert('提交成功！');
				InitAssociateData();
			}else{
				alert('提交失败！');
			}
		}
	);
	
	$('#associate_sale_order_window').window('close');
	$('#associate_alert').window('close');
}
//读取关联采购单数据
function AssociatePurchaseOrder(){
	if($('#associate_sale_order_table').datagrid('getSelected')==null){
		alert('请选择一行数据！');
	}else{
		$('#associate_sale_order_window').window('center');
		$('#associate_sale_order_window').window('open');
		console.log($('#associate_sale_order_table').datagrid('getSelected'));
		$.post(
			"ajax/associate_table_data_purchase",//请求的地址
			{
				"sale_amount":$('#associate_sale_order_table').datagrid('getSelected').sale_amount_left,
				"product_name":$('#associate_sale_order_table').datagrid('getSelected').product_name,
				"randomnumber":Math.random()+""
			},//需要提交到请求地址的参数
			function( returnedString )     //回调
			{
				var data = returnedString;
				var jsonObj = eval("("+data+")");
				console.log(jsonObj);
				$('#associate_purchase_order_table').datagrid('loadData',jsonObj.associate_purchase_table_data)
			}
		);
	}
}
