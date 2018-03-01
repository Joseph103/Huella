/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import org.springframework.security.crypto.bcrypt.*;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPErrorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import BD.ConexionBD;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import javax.swing.JPasswordField;

/**
 *
 * @author josep
 */
public class form extends javax.swing.JFrame {

    //Nos sirve para identificar al dispositivo
    private DPFPCapture Lector = DPFPGlobal.getCaptureFactory().createCapture();
//Nos sirve para leer a modo de enrrolar, y crear una plantilla nueva, a base de 4 huellas.
    private DPFPEnrollment Reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
//Nos sirve para leer a modo de verificar o comparar, a base de una plantilla creada anteriormente
    private DPFPVerification Verificador = DPFPGlobal.getVerificationFactory().createVerification();
//La plantilla, nueva o rescatada
    private DPFPTemplate template;
//A modo de CONSTANTE para crear plantillas
    public String TEMPLATE_PROPERTY = "template";
//Para leer la huella, y definirla como un enrrolamiento
    public DPFPFeatureSet featureSetInscripcion;
//Para leer la huella, y definirla como una verificación
    public DPFPFeatureSet featureSetVerificacion;
    public formRegister rF;

    /**
     * Creates new form form
     */
    public form(formRegister rF) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "No es posible cambiar tema visual",
                    "LookAndFeel inválido",
                    JOptionPane.ERROR_MESSAGE);
        }
        initComponents();
        Iniciar();
        this.rF = rF;
        start();
        EstadoHuellas();
        btnGuardar.setEnabled(true);
        btnIdentificar.setEnabled(true);

        txtMensaje.setEditable(false);
        btnGuardarActionPerfomed bg = new btnGuardarActionPerfomed();
        btnGuardar.addActionListener(bg);

        btnIdentificarActionPerfomed bi = new btnIdentificarActionPerfomed();
        btnIdentificar.addActionListener(bi);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panHuellas = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblHuella = new javax.swing.JLabel();
        panBtns = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JToggleButton();
        btnIdentificar = new javax.swing.JToggleButton();
        Atras = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        textArea = new javax.swing.JScrollPane();
        txtMensaje = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setFont(new java.awt.Font("Franklin Gothic Medium", 0, 10)); // NOI18N
        setLocation(new java.awt.Point(400, 70));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        panHuellas.setBackground(new java.awt.Color(255, 255, 255));
        panHuellas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Captura Huella Digital ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 14))); // NOI18N
        panHuellas.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel2.setLayout(new java.awt.BorderLayout());

        lblHuella.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.add(lblHuella, java.awt.BorderLayout.CENTER);

        panHuellas.add(jPanel2, java.awt.BorderLayout.CENTER);

        panBtns.setBackground(new java.awt.Color(255, 255, 255));
        panBtns.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Acciones", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 14))); // NOI18N
        panBtns.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        btnGuardar.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow"));
        btnGuardar.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnGuardar.setText("Registrar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnIdentificar.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow"));
        btnIdentificar.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnIdentificar.setText("Ingresar");

        Atras.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow"));
        Atras.setText("Atras");
        Atras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AtrasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(btnIdentificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(42, 42, 42)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Atras, javax.swing.GroupLayout.PREFERRED_SIZE, 645, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnIdentificar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(Atras)
                .addContainerGap())
        );

        panBtns.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        textArea.setBackground(new java.awt.Color(255, 255, 255));

        txtMensaje.setBackground(new java.awt.Color(255, 255, 255));
        txtMensaje.setColumns(20);
        txtMensaje.setRows(5);
        textArea.setViewportView(txtMensaje);

        jPanel4.add(textArea, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panBtns, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
            .addComponent(panHuellas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panHuellas, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panBtns, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
                
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void AtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AtrasActionPerformed
            stop();
            rF.setVisible(true);
            this.setVisible(false);
    }//GEN-LAST:event_AtrasActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Atras;
    private javax.swing.JToggleButton btnGuardar;
    private javax.swing.JToggleButton btnIdentificar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblHuella;
    private javax.swing.JPanel panBtns;
    private javax.swing.JPanel panHuellas;
    private javax.swing.JScrollPane textArea;
    private javax.swing.JTextArea txtMensaje;
    // End of variables declaration//GEN-END:variables
    protected void Iniciar() {
        Lector.addDataListener(new DPFPDataAdapter() {
            @Override
            public void dataAcquired(final DPFPDataEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        EnviarTexto("La huella ha sido capturada");
                        ProcesarCaptura(e.getSample());
                    }
                });
            }
        });
        Lector.addReaderStatusListener(new DPFPReaderStatusAdapter() {
            @Override
            public void readerConnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        EnviarTexto("El sensor de huella dactilar se encuentra Activado");
                    }
                });
            }

            @Override
            public void readerDisconnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        EnviarTexto("El sensor de huella dactilar se encuentra Desactivado");
                    }
                });
            }
        });

        Lector.addSensorListener(new DPFPSensorAdapter() {
            @Override
            public void fingerTouched(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        EnviarTexto("El dedo ha sido colocado sobre el Lector de Huella");
                    }
                });
            }

            @Override
            public void fingerGone(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        EnviarTexto("El dedo ha sido quitado del Lector de Huella");
                    }
                });
            }
        });

        Lector.addErrorListener(new DPFPErrorAdapter() {
            public void errorReader(final DPFPErrorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        EnviarTexto("Error: " + e.getError());
                    }
                });
            }
        });
    }

    public void ProcesarCaptura(DPFPSample muestra) {
        featureSetInscripcion = extraerCaracteristicas(muestra, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
        featureSetVerificacion = extraerCaracteristicas(muestra, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
        if (featureSetInscripcion != null) {
            try {
                System.out.println("Las características de la huella han sido creadas");
                Reclutador.addFeatures(featureSetInscripcion);
                DibujarHuella(CrearImagenHuella(muestra));

                btnIdentificar.setEnabled(true);
                btnGuardar.setEnabled(true);
            } catch (DPFPImageQualityException e) {
                System.err.println("Error: " + e.getMessage());
            } finally {
                EstadoHuellas();
                //Comprueba si la plantilla se ha creado.
                switch (Reclutador.getTemplateStatus()) {
                    case TEMPLATE_STATUS_READY:
                        stop();
                        setTemplate(Reclutador.getTemplate());
                        EnviarTexto("Puede  pasar a registrar sus datos");
                        btnIdentificar.setEnabled(true);
                        btnGuardar.setEnabled(true);

                        btnGuardar.grabFocus();
                        break;
                    case TEMPLATE_STATUS_FAILED: // informe de fallas y reiniciar la captura de huellas
                        Reclutador.clear();
                        stop();
                        EstadoHuellas();
                        setTemplate(null);
                        JOptionPane.showMessageDialog(form.this,
                                "La plantilla de la huella no pudo ser creada. Repita el proceso",
                                "Inscripción de Huellas Dactilares",
                                JOptionPane.ERROR_MESSAGE);
                        start();
                        break;
                }
            }
        }
    }

    public DPFPFeatureSet extraerCaracteristicas(DPFPSample muestra, DPFPDataPurpose motivo) {
        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
        try {
            return extractor.createFeatureSet(muestra, motivo);
        } catch (DPFPImageQualityException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Image CrearImagenHuella(DPFPSample muestra) {
        return DPFPGlobal.getSampleConversionFactory().createImage(muestra);
    }

    public void DibujarHuella(Image image) {
        lblHuella.setIcon(new ImageIcon(
                image.getScaledInstance(lblHuella.getWidth(),
                        lblHuella.getHeight(),
                        Image.SCALE_DEFAULT
                )
        )
        );
        repaint();
    }

    public void EstadoHuellas() {
        EnviarTexto("Muestra de huellas necesarias para guardar plantilla: " + Reclutador.getFeaturesNeeded());
    }

    public void EnviarTexto(String mensaje) {
        txtMensaje.append(mensaje + "\n");
    }

    public void start() {
        Lector.startCapture();
        EnviarTexto("Utilizando lector de huella dactilar");
    }

    public void stop() {
        Lector.stopCapture();
        EnviarTexto("Lector detenido");
    }

    public DPFPTemplate getTemplate() {
        return template;
    }

    public void setTemplate(DPFPTemplate template) {
        DPFPTemplate antigua = this.template;
        this.template = template;
        firePropertyChange(TEMPLATE_PROPERTY, antigua, template);
    }

    ConexionBD con = new ConexionBD();

    /*
     * Guarda los datos de la huella digital actual en la base de datos
     */
    public void guardarHuella()throws Exception{
        //Obtiene los datos del template de la huella actual
        if(template==null){
            JOptionPane.showMessageDialog(this, "No se puede registrar debido a que no hay huella para ingresar","Error",ERROR_MESSAGE);
        }
        ByteArrayInputStream datosHuella = new ByteArrayInputStream(template.serialize());
        
        Integer tamañoHuella = template.serialize().length;
        System.out.println(datosHuella);
        //Pregunta los datos de la persona a la cual corresponde dicha huella
        
        String name = rF.getNamE();
        
        String rol = rF.getRol();
        int role = 0;
        switch(rol){
            case "admin":
                role=1;
                break;
            case "usuario":
                role=0;
                break;
      }
        System.out.println(rol);
        String email = rF.getEmail();
        String password = rF.getPassword();

        String nombre = rF.getNombre();
        String cedula = rF.getCedula();
        String sede = rF.getSede();
        String telefono = rF.getTelefono();
        
        if(name==null||rol==null||email==null||password==null||cedula==null||sede==null||telefono==null){
            JOptionPane.showMessageDialog(this, "no se puede registrar devido a que hace falta diligenciar los campos con *","Error",ERROR_MESSAGE);
        }
        
        int sed =0;
        System.out.println(sede);
        switch(sede){
            case "mercedes abrego":
            sed=1;
            break;
            case "sor maria luisa molina":
            sed=2;
            break;
            case "las palmeras":
            sed=3;
            break;
            case "alfonso cabal madriñan":
            sed=4;
            break;
        }
        
       
                
        try {
           
            
            //Establece los valores para la sentencia SQL
            Connection c = con.conectar();
            
            PreparedStatement guardarU = c.prepareStatement("INSERT INTO users(name,rol,email,password) values(?,?,?,?)");
            guardarU.setString(1, name);
            guardarU.setInt(2, role);
            guardarU.setString(3, email);
            guardarU.setString(4, BCrypt.hashpw(password, BCrypt.gensalt()));

            guardarU.execute();
            guardarU.close();

            PreparedStatement guardarStmt = c.prepareStatement("INSERT INTO empleados(nombre,cedula,huella,user, sede, telefono) values(?,?,?,?,?,?)");
            guardarStmt.setString(1, nombre);
            guardarStmt.setString(2, cedula);
            guardarStmt.setBinaryStream(3, datosHuella, tamañoHuella);
            guardarStmt.setString(4, name);
            guardarStmt.setInt(5, sed);
            guardarStmt.setString(6, telefono);
            //Ejecuta la sentencia*/
            guardarStmt.execute();
            guardarStmt.close();

            JOptionPane.showMessageDialog(this, "Huella Guardada Correctamente","Exito!!",INFORMATION_MESSAGE);
//            con.desconectar();
            btnGuardar.setEnabled(false);
            Reclutador.clear();
            stop();
            EstadoHuellas();
            setTemplate(null);
            btnIdentificar.setEnabled(true);
        } catch (SQLException ex) {
            //Si ocurre un error lo indica en la consola
            System.err.println("Error al guardar los datos de la huella.");
        } finally {
            con.desconectar();
        }
    }

    public void verificarHuella(String nom) {
        try {
            //Establece los valores para la sentencia SQL
            Connection c = con.conectar();
            //Obtiene la plantilla correspondiente a la persona indicada
            PreparedStatement verificarStmt = c.prepareStatement("SELECT huella FROM empleados WHERE nombre=?");
            verificarStmt.setString(1, nom);
            ResultSet rs = verificarStmt.executeQuery();

            //Si se encuentra el nombre en la base de datos
            if (rs.next()) {
                //Lee la plantilla de la base de datos
                byte templateBuffer[] = rs.getBytes("huella");
                //Crea una nueva plantilla a partir de la guardada en la base de datos
                DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
                //Envia la plantilla creada al objeto contendor de Template del componente de huella digital
                setTemplate(referenceTemplate);

                // Compara las caracteriticas de la huella recientemente capturda con la
                // plantilla guardada al usuario especifico en la base de datos
                DPFPVerificationResult result = Verificador.verify(featureSetVerificacion, getTemplate());

                //compara las plantilas (actual vs bd)
                if (result.isVerified()) {
                    JOptionPane.showMessageDialog(this, "La huella capturada coinciden con la de " + nom, "Verificacion de Huella", INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No corresponde la huella con " + nom, "Verificacion de Huella", ERROR_MESSAGE);
                }

                //Si no encuentra alguna huella correspondiente al nombre lo indica con un mensaje
            } else {
                JOptionPane.showMessageDialog(this, "No existe un registro de huella para " + nom, "Verificacion de Huella", ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            //Si ocurre un error lo indica en la consola
            System.err.println("Error al verificar los datos de la huella.");
        } finally {
            con.desconectar();
        }
    }

    /**
     * Identifica a una persona registrada por medio de su huella digital
     */
    /**
     * Identifica a una persona registrada por medio de su huella digital
     * @throws java.io.IOException
     */
    public void identificarHuella() throws Exception {
        try {
            //Establece los valores para la sentencia SQL
            Connection c = con.conectar();
            
            //Obtiene todas las huellas de la bd
            PreparedStatement identificarStmt = c.prepareStatement("SELECT nombre, id, huella FROM empleados");
            ResultSet rs = identificarStmt.executeQuery();

            //Si se encuentra el nombre en la base de datos
            while (rs.next()) {
                //Lee la plantilla de la base de datos
                
                byte templateBuffer[] = rs.getBytes("huella");
                
                if (templateBuffer == null) {
                    JOptionPane.showMessageDialog(this, "Error hay usuarios sin registro de huellas");
                    return;
                }
                String nombre = rs.getString("nombre");
                String id = rs.getString("id");
                //Crea una nueva plantilla a partir de la guardada en la base de datos
                DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
                //Envia la plantilla creada al objeto contendor de Template del componente de huella digital
                setTemplate(referenceTemplate);

                // Compara las caracteriticas de la huella recientemente capturda con la
                // alguna plantilla guardada en la base de datos que coincide con ese tipo
                DPFPVerificationResult result = Verificador.verify(featureSetVerificacion, getTemplate());
                  
                //compara las plantilas (actual vs bd)
                //Si encuentra correspondencia dibuja el mapa
                //e indica el nombre de la persona que coincidió.
                
                if (result.isVerified()) {
                     
                    //crea la imagen de los datos guardado de las huellas guardadas en la base de datos
//       JOptionPane.showMessageDialog(null, "Las huella capturada es de "+nombre,"Verificacion de Huella", JOptionPane.INFORMATION_MESSAGE);
                    PreparedStatement huellaIngresadaStmt = c.prepareStatement("UPDATE solicituds SET registro_solicitante = '" +1+ "'  where empleado='" + id + "' AND registro_receptor='" + 0 + "';");
                    int rsHuellaIngresada = huellaIngresadaStmt.executeUpdate();
                    System.out.println(rsHuellaIngresada);
                    if (rsHuellaIngresada > 0) {
            
                        JOptionPane.showMessageDialog(this, "La huella de las solicitudes del empleado " + nombre + " se validaron éxitosamente","Exito!!",INFORMATION_MESSAGE);

                    }else if(rsHuellaIngresada ==0){
                        JOptionPane.showMessageDialog(this, "El empleado " + nombre + " no tiene solicitudes pendientes","Exito!!",INFORMATION_MESSAGE);
                    }
                    
                     PreparedStatement huellaIngresadaReportesStmt = c.prepareStatement("UPDATE reportes SET registro_responsable = '" +1+ "'  where responsable='" + nombre + "' AND registro_responsable='" + 0 + "';");
                    int rsHuellaIngresadaReportes = huellaIngresadaReportesStmt.executeUpdate();
                    System.out.println(rsHuellaIngresadaReportes);
                    if (rsHuellaIngresadaReportes >0) {
            
                        JOptionPane.showMessageDialog(this, "La huella de los reportes del empleado " + nombre + " se validaron éxitosamente","Exito!!",INFORMATION_MESSAGE);

                    }else if(rsHuellaIngresadaReportes ==0){
                        JOptionPane.showMessageDialog(this, "El empleado " + nombre + " no tiene reportes pendientes","Exito!!",INFORMATION_MESSAGE);
                    }
                    
                     PreparedStatement huellaIngresadaActivoStmt = c.prepareStatement("UPDATE documentos SET registro_encargado = '" +1+ "'  where empleado='" + id + "';");
                    int rsHuellaIngresadaActivo = huellaIngresadaActivoStmt.executeUpdate();
                    System.out.println(rsHuellaIngresadaActivo);
                    if (rsHuellaIngresadaActivo > 0) {
            
                        JOptionPane.showMessageDialog(this, "La huella en el registro de activo del empleado " + nombre + " se validaron éxitosamente","Exito!!",INFORMATION_MESSAGE);

                    }else if (rsHuellaIngresadaActivo == 0){
                        JOptionPane.showMessageDialog(this, "El empleado " + nombre + " no tiene registros pendientes","Exito!!",INFORMATION_MESSAGE);
                    }
                    //crea la imagen de los datos guardado de las huellas guardadas en la base de datos
                    //JOptionPane.showMessageDialog(null, "Las huella capturada es de "+nombre,"Verificacion de Huella", JOptionPane.INFORMATION_MESSAGE);
                    //return;

                    return;
                }
            }
            //Si no encuentra alguna huella correspondiente al nombre lo indica con un mensaje
            JOptionPane.showMessageDialog(this, "No existe ningún registro que coincida con la huella", "Verificacion de Huella",ERROR_MESSAGE);
            setTemplate(null);
        } catch (SQLException e) {
            //Si ocurre un error lo indica en la consola
            System.err.println("Error al identificar huella dactilar." + e.getMessage());
        } catch(NullPointerException e){
            JOptionPane.showMessageDialog(this, "No se a digitalizado  ninguna huella", "Verificacion de Huella",ERROR_MESSAGE);
        }finally {
            con.desconectar();
        }
    }

    public class btnGuardarActionPerfomed implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            try {
                guardarHuella();
            } catch (Exception ex) {
                Logger.getLogger(form.class.getName()).log(Level.SEVERE, null, ex);
            }
            Reclutador.clear();
            lblHuella.setIcon(null);
            start();
            


        }
    }

    public class btnVerificarActionPerfomed implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            String nombre = JOptionPane.showInputDialog("Nombre a verificar:");
            verificarHuella(nombre);
            Reclutador.clear();
        }
    }

    public class btnIdentificarActionPerfomed implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                identificarHuella();
                Reclutador.clear();
            } catch (IOException ex) {
                Logger.getLogger(form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(form.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public class salir implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.exit(0);
        }
    }

}
