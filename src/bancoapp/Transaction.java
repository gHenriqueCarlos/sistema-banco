/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bancoapp;

import bancoapp.Views.ViewHomeScreen;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author CARLO
 */
public class Transaction {
/*
    private String CPF;
    private String ToCPF;
    private double Value;
*/
    private double GetBalance(String cpf) {
        try {
            String queryBalance = "SELECT balance from accounts where cpf = ?";
            PreparedStatement statement = Database.DatabaseConnection.prepareStatement(queryBalance);
            statement.setString(1, cpf);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getDouble("balance");
            }
        }catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }
    
    private double GetBalanceCount(String cpf) {
        try {
            String queryBalance = "SELECT balancecount from accounts where cpf = ?";
            PreparedStatement statement = Database.DatabaseConnection.prepareStatement(queryBalance);
            statement.setString(1, cpf);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getDouble("balancecount");
            }
        }catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }
    
    public boolean DepositValue(String from_cpf, String to_cpf, double value, Account account, ViewHomeScreen home){
        if (from_cpf.isBlank() || to_cpf.isBlank()) {
            return false;
        }

        Account temp_account = new Account();

        if (!temp_account.CheckAccount(to_cpf)) {
            return false;
        }

        try {
            double balance = GetBalance(to_cpf);
            balance += value;
            
            double balance_total = GetBalanceCount(to_cpf) + balance;
            
            if (account != null) {
                if(account.IsLogged){
                    account.SetBalance(balance);
                    account.setBalanceCount(balance_total);
                    
                    if(home != null){
                        home.UpdateTextValues();
                    }
                }
            }

            String queryAccount = "update accounts set balance = ?, balancecount = ? where cpf = ?";
            PreparedStatement statement = Database.DatabaseConnection.prepareStatement(queryAccount);

            statement.setDouble(1, balance);
            statement.setDouble(2, balance_total);
            statement.setString(3, to_cpf);
            statement.executeUpdate();
            
            if (temp_account.CheckAccount(from_cpf) && !from_cpf.equals(to_cpf)){
                balance = GetBalance(from_cpf);
                balance -= value;
                
                queryAccount = "update accounts set balance = ? where cpf = ?";
                statement = Database.DatabaseConnection.prepareStatement(queryAccount);

                statement.setDouble(1, balance);
                statement.setString(2, from_cpf);
                statement.executeUpdate();
            }
            new TransactionsLog().SaveTransaction("deposito", from_cpf, to_cpf, value);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }
    
    public boolean WithdrawValue(String cpf, double value, Account account, ViewHomeScreen home){
        if (cpf.isBlank()) {
            return false;
        }

       // Account temp_account = new Account();

        if (!account.CheckAccount(cpf)) 
            return false;
        

        try {
            double balance = GetBalance(cpf);
            balance -= value;
            
            if (account != null) {
                if(account.IsLogged){
                    account.SetBalance(balance);
                    
                    if(home != null){
                        home.UpdateTextValues();
                    }
                }
            }

            String queryAccount = "update accounts set balance = ? where cpf = ?";
            PreparedStatement statement = Database.DatabaseConnection.prepareStatement(queryAccount);

            statement.setDouble(1, balance);
            statement.setString(2, cpf);
            statement.executeUpdate();
            
            new TransactionsLog().SaveTransaction("saque", cpf, cpf, value);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }
}
