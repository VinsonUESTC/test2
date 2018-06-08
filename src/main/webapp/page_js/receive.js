/**
 * 
 */
//点击收款
function ReceiveConfirm(){
	if($('#receivables_table').datagrid('getSelected')==null){
		alert('请选择一行数据！');
	}else{
		$('#receive_window').window('center');
		$('#receive_window').window('open');
	}
}

//确认提交收款数据
function ClickReceive(){
	if($('#receive_date').datebox('getValue')!=""){
		if($('#receive_amount').numberbox('getValue')!=""){
			if($('#receive_amount').numberbox('getValue')<0||$('#receive_amount').numberbox('getValue')-$('#receivables_table').datagrid('getSelected').need_to_receive>0){
				alert('金额大于待收金额！');
				$('#submit_receive_alert').window('close');
				$('#receive_amount').numberbox('clear');
			}else{
				$('#submit_receive_alert').window('center');
				$('#submit_receive_alert').window('open');
				}
		}else{
			alert("请填写完整！");
			$('#submit_receive_alert').window('close');
		}
	}else{
		alert("请填写完整！");
		$('#submit_receive_alert').window('close');
	}
}

//插入收款数据
function SubmitReceive(type,name){
	$.post(
		"ajax/insert_receive_data",//请求的地址
		{
			"receive_co":$('#receivables_table').datagrid('getSelected').receive_co,
			"receivables_time":$('#receive_date').datebox('getValue'),
			"receivables_method":$('#receive_method').combobox('getValue'),
			"receivables_amount":$('#receive_amount').numberbox('getValue'),
			"creater":document.getElementById("usertitle").innerHTML.substring(3),
			"createtime":new Date().Format("yyyy-MM-dd hh:mm:ss") ,
			"randomnumber":Math.random()+""
		},//需要提交到请求地址的参数
		function( returnedString )     //回调
		{
			var data = returnedString;
			var jsonObj = eval("("+data+")");
			if(jsonObj.i=0){
				alert('提交错误！');
			}else{
				alert('提交成功！');
			};
			$('#submit_receive_alert').window('close');
			$('#receive_date').datebox('clear');
			$('#receive_amount').numberbox('clear');
			$('#receive_window').window('close');
			ReadReceiveTable();
		}
	);
}

//读取待收款数据
function ReadReceiveTable(){
	$.post(
		"ajax/receive_table_data",//请求的地址
		{
			"randomnumber":Math.random()+""
		},//需要提交到请求地址的参数
		function( returnedString )     //回调
		{
			var data = returnedString;
			var jsonObj = eval("("+data+")");
			$('#receivables_table').datagrid('loadData',jsonObj.receive_table_data);
		}
	);
}
		