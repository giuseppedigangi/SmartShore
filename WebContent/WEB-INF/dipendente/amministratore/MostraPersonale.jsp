<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Mostra Personale</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/AreaDipendente.css" type="text/css">

</head>
<%@ page import="java.util.List"%>
<%@ page import="it.unipa.community.digangi.giuseppe.model.Dipendente"%>
<%
	List<Dipendente>dipendenti=(List<Dipendente>) request.getAttribute("listaDipendenti");
%>
<table>
                <tr>
                    <th>Nome</th>
                    <th>Cognome</th>
                    <th>Sesso</th>
                    <th>Email</th>
                    <th>Ruolo</th>
                    <th>Azione</th>

                </tr>
                <%
					for (Dipendente dip : dipendenti) {
				%>
                <tr>
               		
                    <td><%=dip.getNome()%></td>
                    <td><%=dip.getCognome()%></td>
                    <td><%=dip.getSesso()%></td>
                    <td><%=dip.getEmail()%></td>
                    <td><%=dip.getRuolo()%></td>
                    <td><button id="<%=dip.getId() %>" type="button" class="deleteButton" onclick="eliminaDipendente(this)">Elimina</button></td>

                </tr>
                <%
					}
				%>
              
            </table>

</html>