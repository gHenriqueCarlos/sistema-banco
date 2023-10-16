/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bancoapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLO
 */
public class Database {
    public Database () {    }
    public static Connection DatabaseConnection = null;
    
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/banco";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "8425";
    
    public static void Connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(DatabaseConnection == null){
                DatabaseConnection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            }
        } catch(SQLException e){
            System.out.println(e);
            throw new RuntimeException(e);
        } catch(ClassNotFoundException ex){
            System.out.println("Classe não encontrada, adicione o driver nas bibliotecas.");
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
