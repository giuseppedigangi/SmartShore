<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AreaDipendente.css" type="text/css" />
<script src="js/AreaDipendente.js"></script>
<title>Aggiungi Personale</title>
</head>
<form class="form" id="formDipendente">
        <h2 style="margin-bottom: 1%">Aggiungi Dipendente</h2>
        <svg class="line">
            <rect width="100%" height="5px" stroke="" stroke-width="3" fill="#EEC41D"/>
        </svg>
        <h3 class="label">Nome</h3>
        <input type="text" id="nome" name="nomeDip" class="textarea" required>
        <h3 class="label">Cognome</h3>
        <input type="text" id="cognome" name="cognomeDip" class="textarea" required>
        <h3 class="label">Email</h3>
        <input type="email" id="email" name="emailDip" placeholder="example@unipa.it" class="textarea" required>
        <h3 class="label">Password</h3>
        <input type=password id="password" name="passwordDip" class="textarea" required>
        <h3 class="label">Sesso</h3>
		<input type="radio" id="male" name="genderDip" value="M">
		<label for="male">Male</label><br>
		<input type="radio" id="female" name="genderDip" value="F">
		<label for="female">Female</label><br>
		<h3 class="label">Ruolo</h3>
		<select id="ruolo" name="ruoloDip" class="labelForm" size="0">
	                        <option value="amministratore">Amministratore</option>
	                        <option value="bagnino">Bagnino</option>
	                        <option value="barista">Barista</option>
	                        <option value="biglietteria">Biglietteria</option>
	                        <option value="controllore">Controllore</option>
        </select><br>
                    
        <input type="submit" value="Aggiungi" class="btnLog" id="submit">

    </form>
</html>