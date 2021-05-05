/**
 * 
 */

$(document).ready(function(){
	
	$("#formComplete").submit(function(event) {
		event.preventDefault();
			
			completaRegistrazione($("#formComplete").serialize());	
			
	});
	
});

function completaRegistrazione(data){
	$.ajax({
		
		url: "CompletaRegistrazione",
		method: "post",
		data: data,
		processData: false,
    	
    	cache: false,
		success: function() {
			var context=document.location.pathname;
			console.log(context)
            },
        error: function(xhr) {
            if(xhr.status == 400)
            	$("#errorePostazione").html(xhr.responseText).show();
        }
	}); 
}