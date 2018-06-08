<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% 
	session = request.getSession();
	String errMsg = (String)session.getAttribute("msg");
	if(errMsg==null)
	errMsg = "";
%>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<title>login</title>
		<link rel="stylesheet" type="text/css" href="resource/jquery-easyui-1.5.3/themes/metro/easyui.css">
		<link rel="stylesheet" type="text/css" href="resource/jquery-easyui-1.5.3/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="resource/jquery-easyui-1.5.3/themes/mobile.css">
		<script type="text/javascript" src="resource/jquery-easyui-1.5.3/jquery.min.js"></script>
		<script type="text/javascript" src="resource/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="resource/jquery-easyui-1.5.3/jquery.easyui.mobile.js"></script>
		<script type="text/javascript" src="resource/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="page_js/login.js"></script>
		<style type="text/css">
			a:link {color: #444;text-decoration: none;}     
			a:visited {color: #444;text-decoration: none;}  
			a:hover {color: #444;text-decoration: underline;}   
			a:active {color: #444;text-decoration: none;} 
		</style>
	</head>
	<body data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-3" data-genuitec-path="/test2/WebRoot/login.jsp">
		<div class="easyui-navpanel" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-3" data-genuitec-path="/test2/WebRoot/login.jsp">
			<header>
				<div class="m-toolbar">
					<span class="m-title">群立登陆系统</span>
				</div>
			</header>
			<div style="margin:20px auto;width:100px;height:100px;border-radius:100px;overflow:hidden">
				<img src="resource/jquery-easyui-1.5.3/demo-mobile/images/login1.jpg" style="margin:0;width:100%;height:100%;">
			</div>
			<div style="padding:0 20px">
				<form method="post" action = "/test2/ajax/loginServlet">
					<div style="margin-bottom:10px">
						<input name="username" class="easyui-textbox" data-options="prompt:'请输入用户名...',iconCls:'icon-man'" style="width:100%;height:38px">
					</div>
					<div style="margin-bottom:10px">
						<input name="password" class="easyui-passwordbox" data-options="prompt:'请输入密码...'" style="width:100%;height:38px">
					</div>
					<div style="height:38px;">
						<table style="width:100%;">
							<tr>
								<td>
									<input name = "validationCode" class="easyui-textbox" data-options="prompt:'请输入验证码...'" style="width:70%;height:38px">
								</td>
								<td>
									<img class = "validationCode_img" src="/test2/ajax/validationCode" style="height:38px">
								</td>
							</tr>
						</table>
					</div>
					<div style="text-align:center;margin-top:30px">
						<input href="#" class="easyui-linkbutton" style="width:100%;height:40px" type="submit"  class="loginform_submit"  value="登陆">
					</div>
					<div style="color:red;font-size:12px;text-align:center;width:100%;">
						<%=errMsg %>
	                </div>
	            </form>
			</div>
			<footer>
	        	<div class="m-toolbar">
	                <a href="http://www.miitbeian.gov.cn">备案号:苏ICP备18007225号-1</a>
	            </div>
	        </footer>
		</div>
	</body>	
</html>