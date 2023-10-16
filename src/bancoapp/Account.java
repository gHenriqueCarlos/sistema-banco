/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bancoapp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author CARLO
 */

/*

    A maioria dessas funções só estarão disponível com o CLiente LOGADO. Funções que manipulam valores como credito, dinheiro e afins devem ser sempre obtida do banco de dados diretamente.


*/
public class Account {
    private String Name;
    private String CPF;
    private String Password;
    
    // Saldo
    private double Balance;
    private double BalanceCount;

    
    
    // Crédito
    private double Credit;
    private double CreditUsed;
    
    // Emprestimo
    private double Loan;
    private double LoanUsed;

    
    public boolean IsLogged;
    public boolean IsAdmin;
    
    public Account(){

    }
    
    public Account(String name, String cpf, double balance, double credit){
        this.Name = name;
        this.CPF = cpf;
        this.Balance = balance;
    }
    
    public void SetName(String name){
        this.Name = name;
    }
    public String GetName(){
        return Name;
    }
    public void SetCPF(String cpf){
        this.CPF = cpf;
    }
     public String GetCPF(){
        return CPF;
    }
    public void SetPassword(String password){
        this.Password = password;
    }
    public void SetBalance(double balance){
        this.Balance = balance;
    }
    public double GetBalance(){
        return Balance;
    }
    public double getBalanceCount() {
        return BalanceCount;
    }

    public void setBalanceCount(double BalanceCount) {
        this.BalanceCount = BalanceCount;
    }
    public double getCredit() {
        return Credit;
    }

    public void setCredit(double Credit) {
        this.Credit = Credit;
    }

    public double getCreditUsed() {
        return CreditUsed;
    }

    public void setCreditUsed(double CreditUsed) {
        this.CreditUsed = CreditUsed;
    }

    public double getLoan() {
        return Loan;
    }

    public void setLoan(double Loan) {
        this.Loan = Loan;
    }

    public double getLoanUsed() {
        return LoanUsed;
    }

    public void setLoanUsed(double LoanUsed) {
        this.LoanUsed = LoanUsed;
    }
    
    public boolean LoadAccount(String cpf){
        if(cpf.isBlank())
            return false;
        
        try{
            String queryAccount = "SELECT * from accounts where cpf = ?";
            PreparedStatement statement = Database.DatabaseConnection.prepareStatement(queryAccount);
            statement.setString(1, cpf);
            ResultSet result = statement.executeQuery();
            
            if(result.next()){
                SetName(result.getString("name"));
                SetCPF(cpf);
                SetBalance(result.getDouble("balance"));
                setBalanceCount(result.getDouble("balancecount"));
                
                setCredit(result.getDouble("credit"));
                setCreditUsed(result.getDouble("creditused"));
                
                setLoan(result.getDouble("loan"));
                setLoanUsed(result.getDouble("loanused"));

                IsLogged = true;
                IsAdmin = true;
            }
        } catch (SQLException e){
            
        }
        return false;
    }
    public boolean InsertAccount(Account account){
        if(account.GetCPF().isBlank())
            return false;
        
        try{
            String queryAccount = "insert into accounts(name, password, cpf, balance, balancecount, credit, creditused, loan, loanused) values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = Database.DatabaseConnection.prepareStatement(queryAccount);
            
            statement.setString(1, account.GetName());
            statement.setString(2, account.Password);
            statement.setString(3, account.GetCPF());
            statement.setDouble(4, account.GetBalance());
            statement.setDouble(5, account.getBalanceCount());
            statement.setDouble(6, account.getCredit());
            statement.setDouble(7, account.getCreditUsed());
            
            statement.setDouble(8, account.getLoan());
            statement.setDouble(9, account.getLoanUsed());
            
            int result = statement.executeUpdate();
            
            if(result > 0)
                return true;
            
        } catch (SQLException e){
            System.out.println(e);
        }
        return false;
    }
    public boolean CheckAccount(String cpf){
        if(cpf.isBlank())
            return false;
        
        try{
            String queryAccount = "SELECT name from accounts where cpf = ?";
            PreparedStatement statement = Database.DatabaseConnection.prepareStatement(queryAccount);
            statement.setString(1, cpf);
            ResultSet result = statement.executeQuery();
            
            if(result.next())
                return true;
            
        } catch (SQLException e){
            
        }
        return false;
    }
    public boolean CheckAccountPassword(String cpf, String password){
        if(cpf.isBlank())
            return false;
        
        try{
            String queryAccount = "SELECT password from accounts where cpf = ?";
            PreparedStatement statement = Database.DatabaseConnection.prepareStatement(queryAccount);
            statement.setString(1, cpf);
            ResultSet result = statement.executeQuery();
            
            if(result.next()){
                String pass = result.getString("password");
                
                if(pass.equals(password))
                    return true;
            }
            
        } catch (SQLException e){
            
        }
        return false;
    }
}
