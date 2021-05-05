<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Cassa Giornaliera</title>
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="js/AreaDipendente.js"></script>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AreaDipendente.css" type="text/css">
</head>

<%@ page import="org.json.JSONObject" %>

<%
	 JSONObject incassoPre=new JSONObject();
	 incassoPre= (JSONObject) request.getAttribute("incassoPren");
	 
	 JSONObject incassoOrd=new JSONObject();
	 incassoOrd= (JSONObject) request.getAttribute("incassoOrd");
	 
%>
</head>
<div>
<h3>PRENOTAZIONI</h3>
<%

	if(Integer.parseInt(incassoPre.get("numeroprenotazioni").toString())>0){
%>

<h4>
Data: <%=incassoPre.get("dataprenotazione")%>
</h4>
<h4>
Numero Prenotazioni: <%=incassoPre.get("numeroprenotazioni")%>
</h4>
<h4>
Incasso Giornaliero: <%=incassoPre.get("incassototale")%>&euro;
</h4>
</div>
<%
	}
%>

<div>
<h3>ORDINAZIONI</h3>
<%

	if(!incassoOrd.isEmpty()){
%>
<h4>
Data: <%=incassoOrd.get("dataordinazione")%>
</h4>
<h4>
Incasso Totale: <%=incassoOrd.get("incassototale")%>&euro;
</h4>
<%
	}
%>


</div>
</html>