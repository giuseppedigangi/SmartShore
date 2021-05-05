<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Situazione Lido</title>
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<script src="js/AreaDipendente.js"></script>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AreaDipendente.css" type="text/css">
</head>

<%@ page import="org.json.JSONArray" %>
<%@ page import="java.time.LocalDate"%>
<%
	 JSONArray posti=new JSONArray();
	 posti= (JSONArray) request.getAttribute("posti");
	 
	 JSONArray postiOccupati=new JSONArray();
	 postiOccupati= (JSONArray) request.getAttribute("postiOccupati");
	 
%>
</head>

<div class="mappaPosti">
        
        <%
        	String colore="";
			int idPosto=0;
			int idOcc=0;
        	for(int i=0;i<posti.length();i++){
        			idPosto=i+1;
        			colore="green";
        			
        			for(int j=0;j<postiOccupati.length();j++){
        				
	        			idOcc=(int)postiOccupati.getJSONObject(j).get("ref_idposto");
	        			
	        			if(idOcc==idPosto)
	        				colore="red";
        					
        			}
        %>
            <div  class="posto">
            <i id="posto<%=idPosto%>"class='fas fa-umbrella-beach' style="color:<%=colore%>"></i><br>
                <a href="#informazioniPosto" rel="modal:open" ><button type=button value="<%=idPosto%>" class="btnUmbrella" id="btn<%=idPosto%>" onclick="richiediInfo(this)">Seleziona</button></a>
             
            </div>
            <%
        				
        	}
            %>
            
            <div class="boxMare">
                <svg width="100%" height="80">
                    <rect width="100%" height="80" style="fill:rgb(60,192,253);stroke-width:3;stroke:rgb(100,233,253)" />
                    Sorry, your browser does not support SVG.
                </svg>
            </div>
 </div>
 
		 <!-- Modal Informazioni Utente si apre al click della postazione-->
               <div id="informazioniPosto" class="modal" style="text-align:center">
            <h3 style="color:cornflowerblue">INFO POSTAZIONE</h4>
			 <p></p>
			 
				<h4 id="infoNome"></h4>
				<h4 id="infoCognome"></h4>
				<h4 id="infoTurno"></h4>
				<h4 id="infoLettini"></h4>
				<h4 id="infoPresente"></h4>
			
			 <a href="#" rel="modal:close"><button>Chiudi</button></a>
			</div>

</html>