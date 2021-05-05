package it.unipa.community.digangi.giuseppe.controller;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConnectionDB {
	private Context ctx = null;
    private DataSource ds = null;
    public Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet rs = null;
   

    public Connection startConnection() throws NamingException, SQLException {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ctx = new InitialContext();
        ds = (DataSource) this.ctx.lookup("java:comp/env/jdbc/SmartShore_db");
        return connection  = this.ds.getConnection();
		
    }
    
    /*funzione che chiude la connessione
     * In realtà non la utilizzo mai perchè uso quella prestabilita
     * */
    public void closeConnection() throws SQLException {
        if(rs != null)
            this.rs.close();
        
        statement.close();
        connection.close();
    }
    
  
}
