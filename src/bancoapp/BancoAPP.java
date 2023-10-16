/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bancoapp;

//import bancoapp.Views.ViewLogin;
//import bancoapp.Views.ViewRegister;
//import bancoapp.Views.PopUp;
import bancoapp.Views.ViewStart;
import javax.swing.JOptionPane;

/**
 *
 * @author CARLO
 */
public class BancoAPP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Database.Connect();
        if (Database.DatabaseConnection == null) {
            JOptionPane.showMessageDialog(null, "Sem conexão ao banco de dados. Tente novamente mais tarde.", "Sem conexão", 0);
        }

        java.awt.EventQueue.invokeLater(() -> {
            //System.out.println("run");
            new ViewStart().setVisible(true);

            //new PopUp("Sou um PopUP", "Olá, sou uma mensagem popup/dialog nova para o sistema!!").setVisible(true);
        });
    }

    public static boolean IsNumber(String number) {
        if (number == null) {
            return false;
        }

        try {
            double num = Double.parseDouble(number);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}
