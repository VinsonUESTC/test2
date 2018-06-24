/**
 * 
 */

$(function(){
  //日期格式化函数
    Date.prototype.Format = function(fmt)  
    {  
    	 var o = {  
    	  "M+" : this.getMonth()+1,         //月份  
    	  "d+" : this.getDate(),          //日  
    	  "h+" : this.getHours(),          //小时  
    	  "m+" : this.getMinutes(),         //分  
    	  "s+" : this.getSeconds(),         //秒  
    	  "q+" : Math.floor((this.getMonth()+3)/3), //季度  
    	  "S" : this.getMilliseconds()       //毫秒  
    	 };  
    	 if(/(y+)/.test(fmt))  
    	  fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));  
    	 for(var k in o)  
    	  if(new RegExp("("+ k +")").test(fmt))  
    	 fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));  
    	 return fmt;  
    } 
});
//初始化订单列表
function InitForm(){
    InitTransportData();
    InitChoiceData();
	//设置编号
	hetonghao = "CGDD" + new Date().Format("yyyyMMddhhmmss");
	sale_hetonghao = "XSDD" + new Date().Format("yyyyMMddhhmmss");
	$("#purchase_contract_number_form").textbox('setValue',hetonghao);
	$("#sale_contract_number_form").textbox('setValue',sale_hetonghao);
	//设置联动
	$('#purchase_amount_form').numberbox({
		"onChange": function(){
			$('#purchase_totalprice_form').numberbox('setValue',$('#purchase_amount_form').numberbox('getValue')*$('#purchase_singleprice_form').numberbox('getValue'))
	}});
	$('#purchase_singleprice_form').numberbox({
		"onChange": function(){
			$('#purchase_totalprice_form').numberbox('setValue',$('#purchase_amount_form').numberbox('getValue')*$('#purchase_singleprice_form').numberbox('getValue'))
	}});
	$('#sale_amount_form').numberbox({
		"onChange": function(){
			$('#sale_totalprice_form').numberbox('setValue',$('#sale_amount_form').numberbox('getValue')*$('#sale_singleprice_form').numberbox('getValue'))
	}});
	$('#sale_singleprice_form').numberbox({
		"onChange": function(){
			$('#sale_totalprice_form').numberbox('setValue',$('#sale_amount_form').numberbox('getValue')*$('#sale_singleprice_form').numberbox('getValue'))
	}});	
	
}

//金额显示数据
function fmoney(value,row){  
   value = parseFloat((value + "").replace(/[^\d\.-]/g, "")).toFixed(2) + "";  
   var l = value.split(".")[0].split("").reverse(),  
   r = value.split(".")[1];  
   t = "";  
   for(i = 0; i < l.length; i ++ )  
   {  
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
   }  
   return "￥"+t.split("").reverse().join("") + "." + r;  
}

//百分比显示数据
function fpercent(value,row){
    value = parseFloat((value + "").replace(/[^\d\.-]/g, "")).toFixed(2) + "";
    var l = value.split(".")[0].split("").reverse(),
        r = value.split(".")[1];
    t = "";
    for(i = 0; i < l.length; i ++ )
    {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    return t.split("").reverse().join("") + "." + r+"%";
}