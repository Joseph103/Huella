
import form.*;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author josep
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
           java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                formRegister x = new formRegister();
                
                x.setAlwaysOnTop(true);
                x.setVisible(true); 
                 
                
            }
        });
    }
    
}
