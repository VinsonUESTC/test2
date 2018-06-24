/**
 * 
 */
$(function(){
	 //选择月份函数-查询分配
		$('.date-month3').datebox({    
	        onShowPanel : function() {// 显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层    
	            span3.trigger('click'); // 触发click事件弹出月份层    
	            if (!tds3)    
	                setTimeout(function() {// 延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔    
	                    tds3 = p3.find('div.calendar-menu-month-inner td');    
	                    tds3.click(function(e) {    
	                        e.stopPropagation(); // 禁止冒泡执行easyui给月份绑定的事件    
	                        var month2 = 0;
	                        var year = /\d{4}/.exec(span3.html())[0]// 得到年份    
	                        , month = parseInt($(this).attr('abbr'), 10) + 1; // 月份    
	                        $('.date-month3').datebox('hidePanel')// 隐藏日期对象    
	                        .datebox('setValue', year + '-' + month); // 设置日期的值   
	                        month2 = month-1;
	                        date_month = year+'-'+month2+'-1'; 
	                        console.log(date_month);
	                    });    
	                }, 0);    
	        },    
	        parser : function(s) {// 配置parser，返回选择的日期    
	            if (!s)    
	                return new Date();    
	            var arr = s.split('-');    
	            return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);    
	        },    
	        formatter : function(d) {    
	            var month = d.getMonth();  
	            if(month<10){  
	                month = "0"+month;  
	            }  
	            if (d.getMonth() == 0) {    
	                return d.getFullYear()-1 + '-' + 12;    
	            } else {    
	                return d.getFullYear() + '-' + month;    
	            }    
	        }// 配置formatter，只返回年月    
	    });    
	    var p3 = $('.date-month3').datebox('panel'), // 日期选择对象    
	    tds3 = false, // 日期选择对象中月份    
	    span3 = p3.find('span.calendar-text'); // 显示月份层的触发控件 
});
//点击分配触发事件
  function AllocateConfirm(){
	 if($('#allocate_boat_table').datagrid('getSelected')==null){
	            alert('请选择一行数据！');
	     }else{
            $('#allocate_window').window('center');
            $('#allocate_window').window('open');
            $('#allocate_order_number').textbox('setValue',"FPDD" + new Date().Format("yyyyMMddhhmmss"));
	     }
  }

  //确认分配触发事件
  function ClickAllocate(){
     if($('#boat_number').combobox('getValue')!=""){
       if($('#allocate_amount_form').numberbox('getValue')!=""){
           if($('#allocate_amount_form').numberbox('getValue')-$('#allocate_boat_table').datagrid('getSelected').amount_left>0){
              alert('分配数量大于可提货数量！');
              $('#allocate_amount_form').numberbox('clear');
           }else{
              $('#submit_allocate_alert').window('center');
              $('#submit_allocate_alert').window('open');
           }
        }else{
           alert('请填写完整！');
        }
     }else{
        alert('请填写完整！');
     }
  }
 
  //写入分配数据
  function SubmitAllocate(){
     $.post(
            "ajax/insert_allocate_data",//请求的地址
            {
            "allocate_order_number":$('#allocate_order_number').textbox('getValue'),
            "purchase_contract_number":$('#allocate_boat_table').datagrid('getSelected').purchase_contract_number,
                   "boat_number":$('#boat_number').combobox('getValue'),
            "allocate_amount":$('#allocate_amount_form').numberbox('getValue'),
                   "creater":document.getElementById("usertitle").innerHTML.substring(3),
                   "createtime":new Date().Format("yyyy-MM-dd hh:mm:ss") ,
                   "randomnumber":Math.random()+""
            },//需要提交到请求地址的参数
           function( returnedString )     //回调
           {
              var data = returnedString;
              var jsonObj = eval("("+data+")");
              if(jsonObj.i>0){
                     alert('提交成功！');
                     InitAllocateData();
              }else{
                     alert('提交失败！');
              }
           }
     );
     $('#boat_number').combobox('clear');
     $('#allocate_amount_form').numberbox('clear');
     $('#allocate_order_number').textbox('clear');
     $('#submit_allocate_alert').window('close');
     $('#allocate_window').window('close');
  }
 
  //读取关联采购数据
  function InitAllocateData(){
     $.post(
	       "ajax/allocate_table_data",//请求的地址
	       {
	              "randomnumber":Math.random()+""
	       },//需要提交到请求地址的参数
	              function( returnedString )     //回调
	              {
	                     var data = returnedString;
	                     var jsonObj = eval("("+data+")");
	              $('#allocate_boat_table').datagrid('loadData',jsonObj.allocate_table_data)
	              }
     );
  }
 