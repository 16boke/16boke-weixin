function thousandsFormatting(s) {    
   var l = s.split("").reverse(), t = "";   
   for(var i=0; i<l.length; i++) {   
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
   }   
   return t.split("").reverse().join("");   
}