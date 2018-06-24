/**
 * 
 */
//点击新增按钮
function append(type){
	$("#insert_"+type+"_window").window('center');
	$("#insert_"+type+"_window").window('open');
}

//点击新增确认
function ClickInsert(type) {
    $.post(
        "ajax/insert_name_data",//请求的地址
        {
            "name":$("#"+type+"_name").textbox('getValue'),
            "type" : type,
            "creater":document.getElementById("usertitle").innerHTML.substring(3),
            "createtime":new Date().Format("yyyy-MM-dd hh:mm:ss") ,
            "randomnumber":Math.random()+""
        },//需要提交到请求地址的参数
        function( returnedString )     //回调
        {
            var data = returnedString;
            var jsonObj = eval("("+data+")");
            if(jsonObj.i=='0'){
                alert('提交错误！');
            }else{
                alert('提交成功！');
            };
            $("#insert_"+type+"_window").window('close');
            ReadNameTable(type);
        }
    );
}

//点击删除按钮
function remove(type) {
    if($("#"+type+"_name_table").datagrid('getSelected')==null){
        alert('请选择一行数据！');
    }else{
    	SubmitDelete($("#"+type+"_name_table").datagrid('getSelected').name,type);
	}
}

//删除数据
function SubmitDelete(value,type) {
    $.post(
        "ajax/delete_name_data",//请求的地址
        {
            "name":value,
            "type":type,
            "randomnumber":Math.random()+""
        },//需要提交到请求地址的参数
        function( returnedString )     //回调
        {
            var data = returnedString;
            var jsonObj = eval("("+data+")");
            if(jsonObj.i=='0'){
                alert('删除错误！');
            }else{
                alert('删除成功！');
            };
            ReadNameTable(type);
        }
    );
}

//读取表格数据
function ReadNameTable(type) {
    $.post(
        "ajax/quest_name_data",//请求的地址
        {
            "type":type,
            "randomnumber":Math.random()+""
        },//需要提交到请求地址的参数
        function( returnedString )     //回调
        {
            var data = returnedString;
            var jsonObj = eval("("+data+")");
            $("#"+type+"_name_table").datagrid('loadData',jsonObj.name_data);
            switch (type){
                case "supply" :
                    var supply_co_data = jsonObj.choice_data;
                    $('#supply_co_form').combobox('loadData',supply_co_data);
                    $('#supply_co_delivery').combobox('loadData',supply_co_data);
                    $('#receive_co_pay').combobox('loadData',supply_co_data);
                    $('#payment_cash_supply').combobox('loadData',supply_co_data);;
                    $('#payment_bill_supply').combobox('loadData',supply_co_data);
                    break;
                case "receive" :
                    var receive_co_data = jsonObj.choice_data;
                    $('#receive_co_form').combobox('loadData',receive_co_data);
                    $('#receive_co_discharge').combobox('loadData',receive_co_data);
                    $('#receive_co_receive').combobox('loadData',receive_co_data);
                    break;
                case "product" :
                    var product_name_data = jsonObj.choice_data;
                    $('#purchase_product_form').combobox('loadData',product_name_data);
                    $('#sale_product_form').combobox('loadData',product_name_data);
            }
        }
    );
}

//初始化下拉框数据
function InitChoiceData() {
    ReadNameTable('supply');
    ReadNameTable('receive');
    ReadNameTable('product');
}
