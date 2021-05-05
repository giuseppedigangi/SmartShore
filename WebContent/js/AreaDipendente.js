/**
 * 
 */
$(document).ready(function() {
	
	
	/*Funzione che seleziona la postazione e prende il prezzo corrispondente,
	 * stampando tutto nel form della prenotazione
	 * */
	$(".btnUmbrella").click(function(event){
		event.preventDefault();
		
		$("#postoPre").val($(this).attr("value"));
		var costo=$("#costo"+$(this).attr("value")).attr("value");
		$("#costoPre").val(costo);
		$(".btnPrice").attr('disabled',false);	
		$(".btnPrice").css('background-color',"white");
	});
	
	
	/*funzione richiamata dopo aver selezionato la postazione
	 * Calcola il prezzo totale della prenotazione
	 * */
	$(".btnPrice").click(function(event){
		event.preventDefault();
		var costo=0;
		costo=$("#costoPre").val();
		var nrLetti=$("#lettini").val();
		var costoTotale=0;
		costoTotale=costo;
		
		var turno=parseInt($("#turnoPre").val())
		
		/*Calcolo il prezzo a seconda del numero dei lettini e del turno selezionato*/
		if(nrLetti>2){
			costoTotale = +costo + ((+nrLetti -2) * 5);
			
		}
		if(turno==3){
			costoTotale = costoTotale * 2;
			
		}
		
		$("#costoPre").val(costoTotale);
		$('#costoPre').attr("hidden",false);
		
	/*Controllo che tutti i campi sono pieni e abilito il bottone per il pagamento*/
		var text1=$('#turnoPre').val();
		var text2=$('#lettiniPre').val();	
		var text3=$('#postoPre').val();
		var text4=$('#nomeUtente').val();
		var text5=$('#cognomeUtente').val();
		var text6=$('#emailUtente').val();
		
		if(text1 != '' && text2 != '' && text3 != '' && text4 != '' && text5 != '' && text6 != ''){
			$("#effettuaPrenotazione").attr('disabled',false);	
			$("#effettuaPrenotazione").css('background-color',"#EEC41D");
		}else{
			$("#effettuaPrenotazione").attr('disabled',true);	

		}
		
	})
	
	$("#formDipendente").submit(function(event) {
		event.preventDefault();

			aggiungiDipendente($("#formDipendente").serialize());	
			
	});

});






/*Parte Amministrativa*/

function displayMostraPersonale() {
 

  $.ajax({
	 
    success: function() {
    	
      $("#main").load("MostraPersonale");
      
    },
    error: function(xhr) {
		if(xhr.status == 400)
			alert(xhr.responseText);
		else
			$("html").html(xhr.responseText);
	}
  });
}
function displayNuovoPersonale(){
	$.ajax({
		 
	    success: function() {
	    	
	      $("#main").load("AggiungiPersonale");
	      
	    },
	    error: function(xhr) {
			if(xhr.status == 400)
				alert(xhr.responseText);
			else
				$("html").html(xhr.responseText);
		}
	  });
}
function displayCassaGiornaliera(){
	$.ajax({
		 
	    success: function() {
	    	
	      $("#main").load("CassaGiornaliera");
	      
	    },
	    error: function(xhr) {
		
				alert(xhr.responseText);
		},
		error: function(xhr) {
			if(xhr.status == 400)
				alert(xhr.responseText);
			else
				$("html").html(xhr.responseText);
		}
	  });
}

function eliminaDipendente(elem){
	var idDip=$(elem).attr("id");
	
	$.ajax({
		url: "EliminaPersonale",
		method: "post",
		data:{
			id:idDip,	
		},
		success: function() {


				$("#main").empty();
				$("#main").load("MostraPersonale");
				
			},
			error: function(xhr) {
				if(xhr.status == 400)
					alert(xhr.responseText);
				else
					$("html").html(xhr.responseText);
			}
		});
}

function aggiungiDipendente(data){

	$.ajax({
		
		url: "AggiungiPersonale",
		method: "post",
		data: data,
		processData: false,
    	cache: false,
		success: function() {
			$("#main").empty();
			$("#main").load("MostraPersonale");
            },
        error: function(xhr) {
        	if(xhr.status == 400)
				alert(xhr.responseText);
			else
				$("html").html(xhr.responseText);
		}
	}); 
}

/*Parte Bar*/
function displayOrdiniBar(){
	$.ajax({
		 
	    success: function() {
	    	
	      $("#main").load("MostraOrdini");
	      
	    },
	    error: function(xhr) {
			if(xhr.status == 400)
				alert(xhr.responseText);
			else
				$("html").html(xhr.responseText);
		}
	  });
}
function setOrdinePronto(prod){
	var idUtente=$(prod).attr('id');
	
	var idProdotto=$(prod).siblings('h5').eq(1).attr('id');
	var idOrdine=$(prod).siblings('h5').eq(3).attr('id');
	

		$.ajax({
			url: "MostraOrdini",
			method: "post",
			data:{utente:idUtente,
				ordine:idOrdine,
				prodotto:idProdotto		
			},
			success: function(data) {
					
					$("#main").empty();
					$("#main").html(data);
					
				},
				error: function(xhr) {
					if(xhr.status == 400)
						alert(xhr.responseText, "error");
					else
						$("html").html(xhr.responseText);
				}
			});
}



/* Parte Biglietteria*/
function displayDisponibilitaGiornaliera(){
	$.ajax({
		 
	    success: function() {
	    	
	      $("#main").load("MostraDisponibilitaGiornaliera");
	      	      
	    },
	    error: function(xhr) {
			if(xhr.status == 400)
				alert(xhr.responseText);
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

	
	/*Invio anche i dati dell'utente per creare l'account*/
	var nomeUt=$('#nomeUtente').val();
	var cognomeUt=$('#cognomeUtente').val();
	var emailUt=$('#emailUtente').val();
	var sessoUt=$('input[name="gender"]:radio').val();
	
	
	
	$.ajax({
		url: "PrenotaPosto",
		method: "post",
		data:{
			data:dataPre,
			turno:turnoPre,
			nr: nrLet,
			posto: postoPre,
			costo: costoPre,
			
			
			nome: nomeUt,
			cognome:cognomeUt,
			email:emailUt,
			sesso:sessoUt
		},
		success: function() {
				$("#main").empty();
				$("#main").text("Prenotazione effettuata con successo");
			},
			error: function(xhr) {
				if(xhr.status == 400)
					alert(xhr.responseText);
				else
					$("html").html(xhr.responseText);
			}
		});
}


/*PARTE BAGNINO*/
function displaySituazioneLido(){
	$.ajax({
		 
	    success: function() {
	    	
	      $("#main").load("MostraDisponibilitaGiornaliera");

	    },
	    error: function(xhr) {
			if(xhr.status == 400)
				alert(xhr.responseText);
			else
				$("html").html(xhr.responseText);
		}
	  });
}

function richiediInfo(elem){
	
	
	var id=$(elem).attr("value");
	$.ajax({
		url: "MostraDettagli",
		method: "post",
		data:{
			idPosto:id	
		},
		success: function(data) {
			console.log(JSON.stringify(data));
				
				mostraInfo(data);		
			
			},
			error: function(xhr) {
				if(xhr.status == 400)
					alert(xhr.responseText);
				else
					$("html").html(xhr.responseText);
			}
		});
	
}

function mostraInfo(data){
	
	
	$("#btnMod").attr('disabled', false);
	$("#infoNome").attr('value',data.idutente);
	$("#infoNome").text("NOME: "+data.nomeutente);
	$("#infoCognome").text("COGNOME: "+data.cognomeutente);
	
	$("#infoLettini").text("NUMERO LETTINI: "+data.numerolettini);
	
	if(data.turno==1){
		$("#infoTurno").text("TURNO 1: 08-14");
	}else if(data.turno==2){
		$("#infoTurno").text("TURNO 2: 14-20");
	}else{
		$("#infoTurno").text("TURNO 3: 08-20");
	}
	
	
	if(data.presente==0){
	$("#infoPresente").text("Assente");
	}else{
	$("#infoPresente").text("Presente");
	}
	
	var control=data.nomeutente;
	if (control==undefined){
		$("#btnMod").attr('disabled', true);
		
	}
		
	
}
/*PARTE CONTROLLORE*/
function displayVarcoAccessi(){
	$.ajax({
		 
	    success: function() {
	    	
	      $("#main").load("MostraDisponibilitaGiornaliera");
	      
	      
	    },error: function(xhr) {
			if(xhr.status == 400)
				alert(xhr.responseText);
			else
				$("html").html(xhr.responseText);
		}
	  });
}

function cambiaPresenza(){
	var idUt=$("#infoNome").attr("value");
	var turno=$("#infoTurno").text().substr(6,1);
		
	var presenza=$("#infoPresente").text();
	
	
	if(presenza=="Assente"){
		segnaIngresso(idUt,turno);
	}else{
		segnaUscita(idUt,turno);
	}
}

function segnaIngresso(idUt,turno){
	
	$.ajax({
		url: "SegnaIngresso",
		method: "post",
		data:{
			id:idUt,
			turno:turno
		},
		success: function() {
			
			
			},
			error: function(xhr) {
				if(xhr.status == 400)
					alert(xhr.responseText);
				else
					$("html").html(xhr.responseText);
			}
		});
}

function segnaUscita(idUt,turno){

	$.ajax({
		url: "SegnaUscita",
		method: "post",
		data:{
			id:idUt,
			turno:turno
		},
		success: function(data) {
				
			
			},
			error: function(xhr) {
				if(xhr.status == 400)
					alert(xhr.responseText);
				else
					$("html").html(xhr.responseText);
			}
		});
}