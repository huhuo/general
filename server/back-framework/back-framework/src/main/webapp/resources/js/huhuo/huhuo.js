(function($) {
	// page
	$.fn.myPage = function(page, targetUrl, data, loadDiv,
			toolSize) {
		if(page==null||typeof(page)=="undefined"){
			return;
		}
		

		var pageNo = page.pageNo;
		var size = page.limit;
		var total = page.total;
		
		var countPage = total % size == 0 ? parseInt(total / size)
				: parseInt(total / size + 1);
		var ul = $("<ul/>");
		$(this).append(ul);

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

		for ( var i = start; i <= end; i++) {
			var livar = $("<li/>");
			pageAddA(ul, livar, i, i, size, targetUrl, data, loadDiv,
					pageNo == i);
		}

		// 添加>>元素
		var liEnd = $("<li/>");

		var liNext = $("<li/>");
		// 判断按钮是否能点击
		if (pageNo >= countPage ) {
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

		loadDiv.load(targetUrl, params, function() {
			$('this').unblock();
		});

	}
	;

	// 为page中每个元素简历a标签，生成对应url，并且创建点击刷新时间
	function pageAddA(ul, li, innerhtml, b, s, targetUrl, data, loadDiv,
			iscurrent) {
		var pageVar = {
			"page.pageNo" : b,
			"page.limit" : s
		};

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
			divBlockLoad(loadDiv, targetUrl, $.extend(data, pageVar));

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
			// 加载对应页面
			loadDiv.load(form.attr('action'), form.serialize(), function(
					response, status) {
				if (status == 'success') {
					$('this').unblock();
				} else {
					$.huhuoGrowlUI("aaa");
				}
				if(callback!=null){
					callback(response, status);
				}
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
			if (form.valid()) {
				$.post(action, form.serialize(), function(data, status) {
					$.huhuoGrowlUI("hahaha");
					if(callback!=null){
						callback(data, status);
					}
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
			if(typeof(text)=='object')
				return true;
			JSON.parse(text);
			return true;
		} catch (e) {
			return false;
		}
	};
	
	$.extend({
		setFormValue:setFormValue
		
	});
	
	function setFormValue(consumer,addOrderform,inputNamePre){
		if(inputNamePre==null){
			inputNamePre="";
		}else{
			inputNamePre=inputNamePre+".";
		}
		 $.each(consumer, function (key, value) {
		    	var inputlist=addOrderform.find("[name='"+inputNamePre+key+"']");
		    	var input=inputlist.first();
		    	
		    	//單選框
		    	if(input.attr('type')=='radio'){
		    		addOrderform.find("[name='"+inputNamePre+key+"'][value="+value+"]").first()[0].checked=true;
		    			
		    	//下拉	
		    	}else if (input.is('select')){
		    		input.find('[value='+value+']')[0].selected=true;
		    	}
		    	//普通input
		    	else{
		    		input.attr("value",value);
		    	}
		    	
		    });
	};
	//自动填充
	/**
	 * url 访问地址
	 * paramKey，输入框内容对应的变量
	 * addOrderform 回填数据的表单
	 * params 访问的其他参数集合
	 * inputNamePre 在回填的时候，在同一表单中的不同对象可能会冲突，比如用户和车型都有name属性，就把input的name命名区分，比如，consumer.name,cartype.name, 在回填属性的时候，可以传入对应的前缀，比如consumer。
	 * callback 回调函数
	 */
	$.fn.autoFill = function(url,paramKey,addOrderform,params,inputNamePre,dataprocess,callback){
		$(this).typeahead({
			source: function (query, process) {
				var params_={};
				params_[""+paramKey]=query;
				if(params!=null){
					params_=params(params_);
				}
				consumers = [];
				consumermap = {};
			    var records;
			    $.post(url,params_,function(data,status){
			    	
			    	records=data.records;
			    	
				    $.each(records, function (i, consumer) {
				    	if(dataprocess==null){
				    		
				    		consumermap[consumer[""+paramKey]] = consumer;
				    		consumers.push(consumer[""+paramKey]);
				    	}else{
				    		dataprocess(consumer,consumermap,consumers);
				    	}
				    });
				 
				    process(consumers);
			    });
			    
			 
			},
			updater: function (item) {
			    var consumer = consumermap[item];
			    setFormValue(consumer,addOrderform,inputNamePre);
			    
			    if(callback!=null){
			    	callback(consumer);
			    }
			    
			    return item;
			},
			minLength:0
		
		});
	};
	
})(jQuery);