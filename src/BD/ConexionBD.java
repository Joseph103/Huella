/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author josep
 */
public class ConexionBD {
    public  String puerto="3306";
    public  String nomservidor="127.0.0.1";
    public  String db="inventario_ma";
    public  String user="root";
    public  String pass="";
    Connection conn=null;


public Connection conectar(){
    try{
    String ruta="jdbc:mysql://";
    String servidor=nomservidor+":"+puerto+"/";
    Class.forName("com.mysql.jdbc.Driver");
    conn = DriverManager.getConnection(ruta+servidor+db,user,pass);

    if (conn!=null){
    System.out.println("Conección a base de datos listo...");
    }
    else if (conn==null)
    {
    throw new SQLException();
    }
    }catch(SQLException e){
        JOptionPane.showMessageDialog(null, e.getMessage());
    } catch (ClassNotFoundException e) {
        JOptionPane.showMessageDialog(null, "Se produjo el siguiente error: "+e.getMessage());
    }catch (NullPointerException e){
        JOptionPane.showMessageDialog(null, "Se produjo el siguiente error: "+e.getMessage());
    }finally{
    return conn;
    }
}

public void desconectar(){
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    conn = null;
    System.out.println("Desconexion a base de datos listo...");
}

}