<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath }/res/ext-integration/js/other/function.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/res/ext-integration/js/other/jquery.min-1.7.2.js"></script>
<script type="text/javascript">
	function changeCaptcha() {  
	    $('#captchaImage').hide().attr('src', '${pageContext.request.contextPath }/sm/enter/captcha.do?' + Math.floor(Math.random()*100) ).fadeIn();  
	    event.cancelBubble=true;  
	} 
</script>
<title>Insert title here</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/sm/enter/login.do" method="post">
		<table align="center">
			<tr>
				<td colspan="3">
					<c:if test="${not empty seLoginErrMsg}">
						${seLoginErrMsg}
					</c:if>
				</td>
			</tr>
			<tr> 
				<td><label>用户名：</label></td>
				<td><input type="text" name="username" /></td>
				<td></td>
			</tr>
			<tr>
				<td><label>密码：</label></td>
				<td><input type="password" name="password"/></td>
				<td></td>
			</tr>
			<tr>
				<td>
					<label>验证码：</label>
				</td>
				<td>
					<input name="captcha" type="text" id="captcha" />
				</td>
				<td>
					<img src="${pageContext.request.contextPath }/sm/enter/captcha.do" 
						id="captchaImage" title="刷新"  onclick="javascript:changeCaptcha()" style= "CURSOR:pointer"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="登录" /></td>
				<td></td>
			</tr>
		</table>
		

	</form>


</body>
</html>