package upa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexao {
	   public static Connection ConnectDb() {
		   try {
	            Class.forName("com.mysql.jdbc.Driver");
	            String url = "jdbc:mysql://localhost:3306/tcc_java";
	            String username = "root";
	            String password = "";
	            Connection conn = DriverManager.getConnection(url, username, password);
	            return conn;
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	}