require.config({
	baseUrl : "script/vv/schedule",
	waitSeconds : 5
});
Array.prototype.contains = function(element) {
	for ( var index = 0; index < this.length; index++) {
		if (this[index] === null) {
			return -1;
		}
		var split = this[index].split("-");
		if (split[0] == element) {
			return split[1];
		}
	}
	return -1;
};
Array.prototype.contain = function(element) {
	for ( var index = 0; index < this.length; index++) {
		if (this[index] === null) {
			return -1;
		}
		if (this[index] == element) {
			return 1;
		}
	}
	return -1;
};
String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {
	if (!RegExp.prototype.isPrototypeOf(reallyDo)) {
		return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi" : "g")), replaceWith);
	} else {
		return this.replace(reallyDo, replaceWith);
	}
};
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	};
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};
function recordLog(obj, state) {
	$.ajax({
		async : false,
		url : getContextPath() + "/log.do?obj=" + obj + "&state=" + state,
		type : "post"
	});
}
function getContextPath() {
	var pathName = document.location.pathname;
	var index = pathName.substr(1).indexOf("/");
	var result = pathName.substr(0, index + 1);
	return result;
}
function createTag(tag) {
	return $("<" + tag + "></" + tag + ">");
}
$(document).ready(function() {
	$(".tab-pane").each(function() {
		$(this).removeClass("active");
	});
	$(".tab-title").each(function() {
		$(this).removeClass("active");
	});
	var param = document.location.href.split("&");
	if (param.length > 1) {
		var selected = getQueryString("subselected");
		$("#" + selected + "Pane").addClass("active in");
		$("#" + selected + "Title").addClass("active");
	} else {
		$("#areaPane").addClass("active in");
		$("#areaTitle").addClass("active");
		$("#chinaPane").addClass("active in");
		$("#chinaTitle").addClass("active");
	}
	var selected = getQueryString("selected");
	$("#" + selected + "Pane").addClass("active in");
	$("#" + selected + "Title").addClass("active");
	$("#quit").bind("click", function(){
		deleteCookie('crius_token');
		$.ajax({url : "/pv/j_spring_security_logout"});
	});
	$(document).bind('keydown', function(evt) {
		if (evt.keyCode == 27) {
			$(".close").click();
		}
	});
});
function json2str(o) {
	var arr = [];
	var fmt = function(s) {
		if (typeof s == 'object' && s != null)
			return json2str(s);
		return /^(string|number)$/.test(typeof s) ? "'" + s + "'" : s;
	};
	for ( var i in o)
		arr.push("'" + i + "':" + fmt(o[i]));
	return '{' + arr.join(',') + '}';
}
var areaDataJson = null;
function getAreaDataJson() {
	if (!areaDataJson) {
		$.ajax({
			type : "POST",
			async : false,
			url : getContextPath() + "/vv/getAreas.do",
			dataType : "json",
			success : function(data) {
				areaDataJson = data;
			}
		});
	}
	return areaDataJson;
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) 
         return unescape(r[2]);
    return null;
}
