var number_format = function(num, options) {  
	options.point=options.point||'.';  
	options.group=options.group||',';  
	options.suffix=options.suffix||'';  
	options.prefix=options.prefix||'';  
	if (typeof(options.places)=="undefined")  
	{     
	    options.places=2;  
	}   
	regex = /(\d+)(\d{3})/;  
	var result = ((isNaN(num) ? 0 : Math.abs(num)).toFixed(options.places)) + '';  
	for (result = result.replace('.', options.point); regex.test(result) && options.group; result=result.replace(regex, '$1'+options.group+'$2')) {  
	    regex.exec(result);  
	};  
	return (num < 0 ? '-' : '') + options.prefix + result + options.suffix;  
};  