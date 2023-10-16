/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bancoapp;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author CARLO
 */
public class TransactionsLog {
    
    public void SaveTransaction(String title, String from_cpf, String to_cpf, Double value){
       try{
            String queryAccount = "insert into transactions(title, cpf, to_cpf, value, data) values (?,?,?,?,?)";
            PreparedStatement statement = Database.DatabaseConnection.prepareStatement(queryAccount);
            
            statement.setString(1, title);
            statement.setString(2, from_cpf);
            statement.setString(3, to_cpf);
            statement.setDouble(4, value);
            Calendar cal = Calendar.getInstance();
            statement.setString(5, new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(cal.getTime()));
            
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println(e);
        } 
    }
}
