<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/AreaDipendente.css" type="text/css">
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<%@ page import="it.unipa.community.digangi.giuseppe.model.Dipendente"%>

<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	response.setDateHeader("Expires", 0);
	session = request.getSession();
	if (session == null) {
		response.sendRedirect("/LoginDipendente");
		return;
	}
	Dipendente dip = (Dipendente) session.getAttribute("loginedDip");

	
%>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Area Dipendente</title>

   
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="js/AreaDipendente.js"></script>
	    <!-- jQuery Modal -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" />
 	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/AreaDipendente.css" type="text/css" />
</head>
<body class="areaDipendente">
<div class="header">

    <h1 id="titleHeader">SMART SHORE</h1>
    <h2>Area Dipendente:  <p>${dipendente.nome } ${dipendente.cognome }</p></h2>
   

</div>
<div class="homeDipendente" >
    <div class="row">
        <div id="side" class="sideSection">
         			<%
                       		switch(dip.getRuolo()) {
                       		case "amministratore":
                       			%>
				        
				                <button class="buttonSide">
				                <a onclick="displayMostraPersonale()" id="prenotaHeader">Mostra Personale</a>
				                </button>
				                <button class="buttonSide">
				                <a onclick="displayNuovoPersonale()" id="prenotaHeader">Aggiungi Personale</a>
				                </button>
				                 <button class="buttonSide">
				                <a onclick="displayCassaGiornaliera()" id="prenotaHeader">Cassa Giornaliera</a>
				                </button>
                				 <button class="buttonSide">
				                <a href="${pageContext.request.contextPath}/Logout"> Logout </a>
				                </button>
                			<%
                       			break;
                       		case "bagnino":
                       			
                       			%>	
	                       			 <button class="buttonSide">
						             <a onclick="displaySituazioneLido()" id="ordinaHeader" >Mostra Lido</a>
						             </button>
						              <button class="buttonSide">
				                <a href="${pageContext.request.contextPath}/Logout"> Logout </a>
				                </button>
						             <%
                       			break;
                       		case "barista":
                       			
                       			%>	
                       			<button class="buttonSide">
						            <a onclick="displayOrdiniBar()" id="ordinaHeader" >Ordini</a>
						        </button>
						        
                       			<button class="buttonSide">
				                <a href="${pageContext.request.contextPath}/Logout"> Logout </a>
				                </button>
						             <%
                       			break;
                       		case "biglietteria":
                       			
                       			%>
                       			
                       			 <button class="buttonSide">
					                <a onclick="displayDisponibilitaGiornaliera()" id="contoHeader">Biglietteria</a>
					                </button>
					              	
					                 <button class="buttonSide">
				                <a href="${pageContext.request.contextPath}/Logout"> Logout </a>
				                </button>
                       			<%
                       			break;
                       		case "controllore":
                       			
                       			%>	
                       			<button class="buttonSide">
				                <a onclick="displayVarcoAccessi()" id="contoHeader">Varco</a>
				                </button>	
                       			 <button class="buttonSide">
				                <a href="${pageContext.request.contextPath}/Logout"> Logout </a>
				                </button>
                       	<%
                       	break;
                       		}
                       	%>
               
           
        </div>
        <div id="main" class="mainSection">
         	 		 
 
 
 
        </div>
        
    </div>
</div>

</body>
</html>