<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/kissy.js"></script>
</head>
<body>
	<button id="btn">click here</button><br><br>
	<div>
		<img id="img" style="position: absolute;" alt="log" src="${pageContext.request.contextPath }/resources/picture/logo.png" />
		<span id="txt" style="font-size: 40px; position: absolute; left: 800px; opacity: 0">Hello, Kissy</span>
	</div>
	
	
	<!-- script -->
	<script type="text/javascript">
		KISSY.ready(function(S) {
			var DOM = S.DOM, Event = S.Event,
				btn = DOM.get('#btn');
			
			Event.on(btn, 'click', function() {
				DOM.attr(btn, 'disabled', true);
				S.Anim('#img', 'left: 800px; opacity: 0', 1, 'eastOut', function() {
					S.Anim('#txt', 'left: 0px; opacity: 1', 2, 'bounceOut', function() {
						
					}).run();
				}).run();
			});
		});
	</script>
</body>
</html>