/*********************************************************
 * 功能描述：
 * 这是一个公共方法，主要包含一些公共操作，例如数据校验、类型转换、ajax异步传输等
 * 
 * action以JSON的格式返回到页面，页面的回调整函数中通过这样进行取
 * function callback(res) {
	var member = eval(res);
	$(res.rows).each(function(i, per) {
		alert(i + "\t" + per.qd + "\t" + per.zd);
	});
	}
 * 
 **********************************************************/
/**
 * 异步执行ajax请求
 * @param {Object} url 请求URL地址
 * @param {Object} param 请求参数
 * @param {Object} succCallback 返回成功后调用的回调函数
 */
function exeAjaxJSONAsync(url, param, succCallback) {
	$.ajax( {
		type :"post",
		url :url,
		data :param,
		dataType :"json",
		async :true,
		success : function(res) {
			succCallback(res);
		}
	});
}
/**
 * 异步执行ajax请求
 * @param {Object} url 请求URL地址
 * @param {Object} param 请求参数
 * @param {Object} succCallback 返回成功后调用的回调函数
 * @param {Object} errCallback 返回失败后调用的回调函数
 */
function exeAjaxJSONAsync(url, param, succCallback, errCallback) {
	$.ajax( {
		type :"post",
		url :url,
		data :param,
		dataType :"json",
		async :true,
		success : function(res) {
			succCallback(res);
		},
		error : function(XMLHttpRequest, textStatus, errorText) {
			errCallback(errorText);
		}
	});
}
/**
 * 异步执行ajax请求
 * @param {Object} url 请求URL地址
 * @param {Object} param 请求参数
 * @param {Object} succCallback 返回成功后调用的回调函数
 * @param {Object} errCallback 返回失败后调用的回调函数
 */
function exeAjaxJSONAsync(url, param, succCallback, errCallback, parameter) {
	$.ajax( {
		type :"post",
		url :url,
		data :param,
		dataType :"json",
		async :true,
		success : function(res) {
			succCallback(res,parameter);
		},
		error : function(XMLHttpRequest, textStatus, errorText) {
			errCallback(errorText);
		}
	});
}

/**
 * 同步执行ajax请求
 * @param {Object} url 请求URL地址
 * @param {Object} param 请求参数
 * @param {Object} succCallback 返回成功后调用的回调函数
 */
function exeAjaxJSONSync(url, param, succCallback) {
	$.ajax( {
		type :"post",
		url :url,
		data :param,
		dataType :"json",
		async :false,
		success : function(res) {
			succCallback(res);
		}
	});
}
/**
 * 同步执行ajax请求
 * @param {Object} url 请求URL地址
 * @param {Object} param 请求参数
 * @param {Object} succCallback 返回成功后调用的回调函数
 * @param {Object} errCallback 返回失败后调用的回调函数
 */
function exeAjaxJSONSync(url, param, succCallback, errCallback) {
	$.ajax( {
		type :"post",
		url :url,
		data :param,
		dataType :"json",
		async :false,
		success : function(res) {
			succCallback(res);
		},
		error : function(XMLHttpRequest, textStatus, errorText) {
			errCallback(errorText);
		}
	});
}
String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
};
/**
 * 删除数组中指定的元素
 */
function remove(val,arr){
	for (var i = 0; i < arr.length; i++) {
	   if (arr[i] == val) {
	        arr.splice(i, 1);
	   }
	}
}
/**
 * 去掉数组中重复元素
 */
function uniQueue(array) {
	var arr = [];
	var m;
	while (array.length > 0) {
		m = array[0];
		arr.push(m);
		array = $.grep(array, function(n, i) {
			return n == m;
		}, true);
	}
	return arr;
}
//去掉字符串头空格
function leftTrim(strValue) {
	var re = /^\s*/;
	if (strValue == null)
		return null;

	strValue = "" + strValue;
	var strReturn = strValue.replace(re, "");

	return strReturn;
}

//去掉字符串尾空格
function rightTrim(strValue) {
	var re = /\s*$/;
	if (strValue == null)
		return null;

	var strReturn = strValue.replace(re, "");

	return strReturn;
}
//RegExt Test
function regExpTest(source, re) {
	var result = false;
	var re = new RegExp(re);//解决firefox中日期函数月份不能匹配为整型问题 zhuangyuan
	if (source == null || source == "")
		return false;

	if (source == re.exec(source))
		result = true;

	return result;
}
//去掉字符串头尾空格
function trim(s) {
	var strReturn;
	strReturn = leftTrim(s);
	strReturn = rightTrim(strReturn);

	return strReturn;
}

//对输入域是否是整数的校验,即只包含字符0123456789
function isInteger(strValue) {
	var result = regExpTest(strValue, /\d+/g);
	return result;
}
//文本框只能输入数字和字母
function checknum(obj) {
	if (obj.value != '') {
		var re = /^[A-Za-z0-9]*$/;
		if (re.test(obj.value) == false) {
			obj.value = '';
			alert("该文本框内只能输入数字和字母，请重新输入！");
		}
	}
}
//文本框只能输入字母
function checkLetter(obj) {
	if (obj.value != '') {
		var re = /[_a-zA-Z]/;
		if (re.test(obj.value) == false) {
			obj.value = '';
			alert("该文本框内只能输入字母，请重新输入！");
		}
	}
}
//只能输入汉字和字母
function checkName(field) {
	var AppliName = field.value;
	if (AppliName != '') {
		var pattern = /^[A-Za-z\u4e00-\u9fa5\uE815-\uE863\s\（\）\(\)\《\》\.\_\．\·\u3B4E\u4DAE\u3473\u3447\u359E\u361A\u360E\u2E97\u396E\u3918\u9FB5\u39CF\u39DF\u3A73\u39D0\u3C6E\u3CE0\u4056\u415F\u4337\u43B1\u43AC\u43DD\u44D6\u4661\u464C\u4723\u4729\u477C\u478D\u2ECA\u4947\u497A\u497D\u4982\u4983\u4985\u4986\u499F\u499B\u49B7\u49B6\u9FBA\u4CA3\u4C9F\u4CA0\u4CA1\u4C77\u4CA2\u4D13\u4D14\u4D15\u4D16\u4D17\u4D18\u4D19\u2E81\u4DAE\u36C3\ue07e]+$/gi;
		if (pattern.test(AppliName)) {
		} else {
			alert("该文本框内只能输入汉字或英文，请重新输入！");
			field.focus();
			return false;
		}
	}
	return true;
}
//对输入域是否是数字的校验
function isNumeric(strValue) {
	var result = regExpTest(strValue, /\d*[.]?\d*/g);
	return result;
}

//离开域时的数字校验
function checkInteger(field, MinValue, MaxValue) {
	if (field.value == "")
		return true;
	field.value = trim(field.value);
	var strValue = field.value;
	if (strValue == "")
		strValue = "0";
	var desc = field.description;
	//如果description属性不存在，则用name属性
	if (desc == null)
		desc = field.name;

	if (!isInteger(strValue)) {
		errorMessage("请输入合法的数字");
		field.focus();
		field.select();
		return false;
	}

	MinValue = parseInt(MinValue);
	if (isNaN(MinValue))
		MinValue = MIN_INTEGER;

	MaxValue = parseInt(MaxValue);
	if (isNaN(MaxValue))
		MaxValue = MAX_INTEGER;
	var value = parseInt(strValue);
	if (isNaN(value) || value > MaxValue || value < MinValue) {
		alert("请输入合法的" + desc + "\n类型为数字(integer),最小值为" + MinValue + ",最大值为"
				+ MaxValue);
		field.focus();
		field.select();
		return false;
	}
	return true;
}

function errCallback(errorText){
	console.log(errorText);
}