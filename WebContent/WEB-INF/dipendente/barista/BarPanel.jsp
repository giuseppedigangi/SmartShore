<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta http-equiv="Refresh" content="10">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Bar Panel</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/AreaDipendente.css" type="text/css">

<%@ page import="org.json.JSONArray"%>
<%
JSONArray ordinazioni=new JSONArray();
ordinazioni= (JSONArray) request.getAttribute("ordini");
%>
</head>
<body>
<h3>Ordini da preparare </h3>
<div class="listaOrdinazioni">
                
                 <%
                 	int statoOrdine=0;
	                 for(int i=0;i<ordinazioni.length();i++){
	                	 statoOrdine=(int) ordinazioni.getJSONObject(i).get("stato");
	            			
	            			if(statoOrdine==0){
	            				
	            			
			 			%>
                <div class="scheda">
                    <h5 class="riepilogo"><i class="fa fa-calendar" aria-hidden="true"></i>  CATEGORIA: <%=ordinazioni.getJSONObject(i).get("categoria") %></h5>
                   
                    <h5 class="riepilogo" id="<%=ordinazioni.getJSONObject(i).get("ref_idprodotto") %>"><i name="<%=ordinazioni.getJSONObject(i).get("ref_idprodotto") %>" class="fa fa-cutlery" aria-hidden="true"></i> PRODOTTO: <%=ordinazioni.getJSONObject(i).get("nomeprodotto") %> </h5>
                    <h5 class="riepilogo"><i class="fa fa-list-ol" aria-hidden="true"></i> QUANTIT&Agrave;: <%=ordinazioni.getJSONObject(i).get("qtprodotto") %></h5>
                    <h5 class="riepilogo" id="<%=ordinazioni.getJSONObject(i).get("idordinazione") %>"><i class="fa fa-money" aria-hidden="true"></i> COMANDA nr: <%=ordinazioni.getJSONObject(i).get("idordinazione") %> <i class="fa fa-eur" aria-hidden="true"></i> </h5>
                  
                    <button class="riepilogo" onclick="setOrdinePronto(this)" id=<%=ordinazioni.getJSONObject(i).get("ref_idutente") %>> Preparato </button>
                </div>
                <%
	            			}
	            			
	                 }
	                 
                %>
                
                
</div>
<h3>Ordini Pronti </h3>
<div class="listaOrdinazioni">
                
                 <%
                 	//int statoOrdine=0;
	                 for(int i=0;i<ordinazioni.length();i++){
	                	 statoOrdine=(int) ordinazioni.getJSONObject(i).get("stato");
	            			
	            			if(statoOrdine==1){
	            				
	            			
			 			%>
                				<div class="scheda">
				                    <h5 class="riepilogo"><i class="fa fa-calendar" aria-hidden="true"></i>  DATA: <%=ordinazioni.getJSONObject(i).get("categoria") %></h5>
				                   
				                    <h5 class="riepilogo"><i class="fa fa-cutlery" aria-hidden="true"></i> PRODOTTO: <%=ordinazioni.getJSONObject(i).get("nomeprodotto") %> </h5>
				                    <h5 class="riepilogo"><i class="fa fa-list-ol" aria-hidden="true"></i> QUANTIT&Agrave;: <%=ordinazioni.getJSONObject(i).get("qtprodotto") %></h5>
				                     <h5 class="riepilogo"><i class="fa fa-list-ol" aria-hidden="true"></i> ORDINE nr: <%=ordinazioni.getJSONObject(i).get("idordinazione") %></h5>
				                    <h5 class="riepilogo"><i class="fa fa-money" aria-hidden="true"></i> POSTAZIONE: <%=ordinazioni.getJSONObject(i).get("ref_idposto") %> <i class="fa fa-eur" aria-hidden="true"></i> </h5>
				                  
				                    
                				</div>
                <%
	            			}
	            			
	                 }
	                 
                %>
                
                
</div>




</body>
</html>