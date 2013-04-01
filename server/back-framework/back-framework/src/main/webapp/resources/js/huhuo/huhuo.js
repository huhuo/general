(function($) {
	// page
	$.fn.myPage = function(pageNo, size, total, targetUrl, data, loadDiv,
			toolSize) {

		var countPage = total % size == 0 ? parseInt(total / size)
				: parseInt(total / size + 1);
		var ul = $("<ul/>");
		$(this).append(ul);
		console.info($(this));

		// 添加《《元素
		var liStart = $("<li/>");

		var liPre = $("<li/>");
		// 判断按钮是否能点击
		if (pageNo < 2) {
			liStart.addClass("disabled");
			liPre.addClass("disabled");
		}

		pageAddA(ul, liStart, "«", 1, size, targetUrl, data, loadDiv, false);
		pageAddA(ul, liPre, "<", pageNo - 1, size, targetUrl, data, loadDiv,
				false);

		var start = 1;

		var end = 1;

		if (toolSize >= countPage) {
			end = countPage;
		} else {
			var halfSize = parseInt(toolSize / 2);
			start = pageNo - halfSize;
			if (toolSize % 2 == 1) {
				end = pageNo + halfSize;
			} else {
				end = pageNo + halfSize - 1;
			}

			if (start < 1) {
				start = 1;
				end = toolSize;
			}
			if (end > countPage) {
				end = countPage;
				start = countPage - toolSize + 1;
			}
		}
		console.info(start);
		console.info(end);

		for ( var i = start; i <= end; i++) {
			var livar = $("<li/>");
			pageAddA(ul, livar, i, i, size, targetUrl, data, loadDiv,
					pageNo == i);
		}

		// 添加>>元素
		var liEnd = $("<li/>");

		var liNext = $("<li/>");
		// 判断按钮是否能点击
		if (pageNo >= countPage - 1) {
			liNext.addClass("disabled");
			liEnd.addClass("disabled");
		}

		pageAddA(ul, liNext, ">", pageNo + 1, size, targetUrl, data, loadDiv,
				false);
		pageAddA(ul, liEnd, "»", countPage, size, targetUrl, data, loadDiv,
				false);

	};
	$.fn.divBlickLoad = function(targetUrl, params) {
		var loadDiv = $(this);
		divBlockLoad(loadDiv, targetUrl, params);
	};
	function divBlockLoad(loadDiv, targetUrl, params) {
		loadDiv.block({
			message : "<img src='res/images/busy.gif' style='margin:20%' />",
			css : {
				top : '38%',
				border : 'none',
				backgroundColor : '#fff',
				height : '100%',
				width : '100%',
				opacity : .5,
			}

		});

		console.info(targetUrl);
		loadDiv.load(targetUrl, params, function() {
			$('this').unblock();
		});

	}
	;

	// 为page中每个元素简历a标签，生成对应url，并且创建点击刷新时间
	function pageAddA(ul, li, innerhtml, b, s, targetUrl, data, loadDiv,
			iscurrent) {
		var pageVar = {
			b : b,
			s : s
		};

		var params = $.extend(data, pageVar);
		console.info(params);
		var a = $("<a/>");
		a.html(innerhtml);
		a.attr("href", targetUrl);
		if (iscurrent) {
			a.css({
				"background-color" : "#F5F5F5",
				"color" : "#005580"
			});
			a.hover(function() {
				a.css({
					"background-color" : "#FFFFFF",
					"color" : "#0088CC"
				});
			}, function() {
				a.css({
					"background-color" : "#F5F5F5",
					"color" : "#005580"
				});
			});
		}

		a.click(function() {
			// 如果不能点击，直接设置
			if (li.hasClass("disabled"))
				return false;
			divBlockLoad(loadDiv, targetUrl, params);

			return false;
		});
		li.append(a);
		ul.append(li);
	}
	// 拼接url
	function setUrlparams(url, params) {

		var paramStr = $.param(params);
		if (url.indexOf("?") == -1) {
			/*
			 * 不需要的操作 if(url.lastIndexOf('/')==url.length-1){
			 * url=url.subString(0,url.length-1) }
			 */
			url = url + "?" + paramStr;
		} else {
			url = url + paramStr;
		}
		return url;
	}

	$(document).ready(function() {

		// menu click event

		// item click event
		$('a.huhuoItem').click(function() {

			// 屏蔽要加载页面的div
			divBlockLoad($('div.loaddiv'), $(this).attr("href"));

			return false;
		});

	});

	// form
	$.fn.huhuoFormRefushDiv = function(loadDiv, callback) {
		var form = $(this);
		form.submit(function() {
			loadDiv.block({
				message : "<img src='images/busy.gif' style='margin:20%' />",
				css : {
					top : '38%',
					border : 'none',
					backgroundColor : '#fff',
					height : '100%',
					width : '100%',
					opacity : .5,
				}

			});
			console.info($(this).attr("href"));
			// 加载对应页面
			loadDiv.load(form.attr('action'), form.serialize(), function(
					response, status) {
				if (status == 'success') {
					$('this').unblock();
				} else {
					$.huhuoGrowlUI("aaa");
				}
				callback(response, status);
			});
			return false;
		});

	};

	// form
	$.fn.huhuoFormPost = function(callback, url) {
		var form = $(this);
		var action = form.attr('action');
		if (typeof (url) != "undefined" && url != null) {
			action = url;
		}
		form.submit(function() {
			console.log(form.valid());
			if (form.valid()) {
				$.post(action, form.serialize(), function(data, status) {
					$.huhuoGrowlUI("hahaha");
					callback(data, status);
				});
			} else {
				$.huhuoGrowlUI("请根据提示输入正确的数据！");
			}
			return false;
		});
	};

	// growUI
	$.huhuoGrowlUI = function(message, timeout, onClose) {
		var $m = $('<div class="growlUI"></div>');
		if (message)
			$m.append('<h5>' + message + '</h5>');
		if (timeout === undefined)
			timeout = 3000;
		$.blockUI({
			message : $m,
			fadeIn : 700,
			fadeOut : 1000,
			centerY : false,
			timeout : timeout,
			showOverlay : false,
			onUnblock : onClose,
			css : {
				width : '350px',
				top : '0px',
				left : ($(window).width() - 175) / 2 + 'px',
				right : '',
				border : 'none',
				padding : '5px',
				opacity : 0.6,
				cursor : 'default',
				color : '#fff',
				backgroundColor : '#0932B9',
				'-webkit-border-radius' : '10px',
				'-moz-border-radius' : '10px',
				'border-radius' : '10px'
			}
		});
	};

	/**
	 * judge whether a text variable is in JSON format [JsonObject, JsonArray],
	 * note that text variable should be in strict JSON format
	 */
	$.isJsonObj = function(text) {
		try {
			JSON.parse(text);
			return true;
		} catch (e) {
			return false;
		}
	};
})(jQuery);