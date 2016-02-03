function setCookie(key, value){
	$.cookie(key, value, {path : '/'});
}

function getCookie(key){
	return $.cookie(key);
}

function deleteCookie(key) {
	$.cookie(key, '', {path:"/", expires: -1 });
}