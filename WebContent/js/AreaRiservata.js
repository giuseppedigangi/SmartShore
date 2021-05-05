/*Funzione per mostrare all'utente la pagina di Prenotazione*/

$(document).ready(function(){
	
	$("#formTurno").submit(function(event) {
		event.preventDefault();
			
		$("#postoPre").val("");
		$("#costoPre").val("");
		$(".errore").text("");
		
			 $("#dataPre").val($("#data").val());
			 $("#turnoPre").val($("#turno").val());
			 $("#lettiniPre").val($("#lettini").val());
			 
			 $("#effettuaPrenotazione").attr('disabled',true);	
			 
			displayDisponibilita($("#formTurno").serialize());	
			
	});
	
	
	
	$(".btnUmbrella").click(function(event){
		event.preventDefault();
		
		$("#postoPre").val($(this).attr("value"));
		var turno=parseInt($("#turno").val());
		var costo=$("#costo"+$(this).attr("value")).attr("value");
		var nrLetti=$("#lettini").val();
		var costoTotale=costo;
	
		if(nrLetti>2){
			costoTotale = +costo + ((+nrLetti -2) * 5);
			
		}
		if(turno==3){
			costoTotale = costoTotale * 2;
			
		}
		
		$("#costoPre").val(costoTotale);
	
		var text1=$('#dataPre').val();
		var text2=$('#turnoPre').val();
		var text3=$('#lettiniPre').val();
		var text4=$('#postoPre').val();
		if(text1 != '' && text2 != '' && text3 != '' && text4 != ''){
			$("#effettuaPrenotazione").attr('disabled',false);	
			$("#effettuaPrenotazione").css('background-color',"#EEC41D");
		}else{
			$("#effettuaPrenotazione").attr('disabled',true);	

		}
		
	});
	
	$("#ordinaHeader").click(function(event){
		event.preventDefault();
		displayOrdinazioneUtente();
	})
	

	 const today = new Date();
	// Imposta la data minima e la data massima nei calendario per verificare la disponibilità */
	$(".calendario").attr({
		"max" : new Date("2020-09-30").toISOString().split('T')[0],
		"min" : new Date(today.getTime() - (today.getTimezoneOffset() * 60000 )).toISOString().split("T")[0]
	 });
	
});
function displayDisponibilita(data){
	
	$.ajax({
		
		url: "MostraDisponibilita",
		method: "post",
		data: data,
		processData: false,
    	
    	cache: false,
		success: function(data) {
			console.log(JSON.stringify(data));
	
			aggiornaPosti(data);
            },
        error: function(xhr) {
            if(xhr.status == 400)
            $('#errDisp').text(xhr.responseText);
            else
				$("html").html(xhr.responseText);
        }
	}); 
	
}

/*Funzione che si occupa di segnalare visivamente all'utente i posti occupati
 * e di disabilitare il bottone corrispondente per evitare che l'utenta lo clicchi
 * */
function aggiornaPosti(data){
	for(i=0;i<=24;i++){
		$("#posto"+i).css("color","green")
		$("#btn"+i).prop("hidden",false)
		$("#btn"+i).prop("disabled",false)
	}
	
	
	for(i=0; i<data.length;i++){
			$("#posto"+data[i].ref_idposto).css("color","red")
			$("#btn"+data[i].ref_idposto).prop("disabled",true)
			
	}
	
	
}


//funzione che apre il menu mostrando la categoria scelta
function openMenu(pageName,elmnt) {
	  var i, tabcontent;
	 
	  $(".carrello").attr("hidden",false)
	  
	  tabcontent = document.getElementsByClassName("tabContent");
	  
	  for (i = 0; i < tabcontent.length; i++) {
	    tabcontent[i].style.display = "none";
	  }
	  tablinks = document.getElementsByClassName("tablink");
	    for (i = 0; i < tablinks.length; i++) {
	        tablinks[i].style.backgroundColor = "";
	        tablinks[i].style.color = "";
	    }
	  
	  document.getElementById(pageName).style.display = "block";
	  elmnt.style.backgroundColor = "rgba(255,255,255,0.8)";
	  elmnt.style.color="cornflowerblue";
	}

function aggiungiAlCarrello(prod){
	var id = $(prod).attr("id")
	
	var $row = $(prod).closest("tr"),      
    $tds = $row.find("td");             

	var nome=$($tds[0]).text();
	var split=$($tds[1]).text().split("€");
	prezzo=split[0];
	var numero =$($tds[2]).find(":selected").text();
	
	$.ajax({
		url: "AggiungiProdottoCarrello",
		method: "post",
		data: {idProd: id,
				quantita : numero,
				nomeProd: nome,
				prezzoProd:prezzo},
		success: function() {
			$(".carrello").load("AggiungiProdottoCarrello");
		},
		error: function(xhr) {
			if(xhr.status == 400)
				alert(xhr.responseText);
			else
				$("html").html(xhr.responseText);
		}
	});



}

function rimuoviDalCarrello(prod){
	var id = $(prod).attr("id")
	$.ajax({
		url: "RimuoviProdottoCarrello",
		method: "post",
		data: {idProd: id},
		success: function(data) {
			
			$(".carrello").empty();
			$(".carrello").html(data);
		},
		error: function(xhr) {
			if(xhr.status == 400)
				alert(xhr.responseText, "error");
			else
				$("html").html(xhr.responseText);
		}
	});
}
function effettuaPrenotazione(){
	var dataPre=$('#dataPre').val();
	var turnoPre=$('#turnoPre').val();
	var nrLet=$('#lettiniPre').val();
	var postoPre=$('#postoPre').val();
	var costoPre=$("#costoPre").val();
	$.ajax({
		url: "EffettuaPrenotazione",
		method: "post",
		data:{
			data:dataPre,
			turno:turnoPre,
			nr: nrLet,
			posto: postoPre,
			costo: costoPre
		},
		success: function() {
				
				$("#main").empty();
				$("#main").text("Prenotazione effettuta con successo! Controlla lo storico per il riepilogo");
			},
			error: function(xhr) {
				if(xhr.status == 400)
					  $('#errPre').text(xhr.responseText);
				else
					$("html").html(xhr.responseText);
			}
		});
}

function annullaPrenotazione(elem){
	var id = $(elem).attr("id");
	
	var data=$("#data"+id).text().substr(8,18);
	
	$.ajax({
		url: "AnnullaPrenotazione",
		method: "post",
		data:{
			idPre:id,	
			dataPre:data
		},
		success: function() {
				$("#main").empty();
				$("#main").load("StoricoUtente");
			},
			error: function(xhr) {
				if(xhr.status == 400)
					alert(xhr.responseText);
				else
					$("html").html(xhr.responseText);
			}
		});
}

function effettuaOrdine(){
	$.ajax({
		url: "EffettuaOrdinazione",
		method: "post",
		success: function() {		
				$(".carrello").empty();	
				$("#main").empty();
				$("#main").text("Ordine BAR effettuto con successo! Controlla lo storico per il riepilogo");
			},
			error: function(xhr) {
				if(xhr.status == 400)
					alert(xhr.responseText);
				else
					$("html").html(xhr.responseText);
			}
		});
}
/*Funzione per mostrare all'utente la pagina di Prenotazione POSTO*/
function displayPrenotazioneUtente() {
	  $.ajax({ 
	    success: function() {
	    	
	      $("#main").load("ScegliTurno");
	      
	    },error: function(xhr) {
			if(xhr.status == 400)
				alert(xhr.responseText);
			else
				$("html").html(xhr.responseText);
		}
	  });
	}
/*Funzione per mostrare all'utente la pagina di Ordinazione BAR*/
function displayOrdinazioneUtente() {
	  $.ajax({ 
	    success: function(event) {
	    	
	      $("#main").load("VisualizzaProdotti");
	      $(".carrello").attr("hidden",false);
	      //$("#main").load("AggiungiProdottoCarrello");
	    },error: function(xhr) {
			if(xhr.status == 400)
				$("#main").text(xhr.responseText);
			else
				$("html").html(xhr.responseText);
		}
	  });
	}
/*Funzione per mostrare all'utente la pagina in cui vi è il riepilogo delle prenotazioni e degli ordini*/
function displayStoricoUtente() {
	  $.ajax({ 
	    success: function() {
	    	
	      $("#main").load("StoricoUtente");
	      
	    },error: function(xhr) {
			if(xhr.status == 400)
				alert(xhr.responseText);
			else
				$("html").html(xhr.responseText);
		}
	  });
	}
