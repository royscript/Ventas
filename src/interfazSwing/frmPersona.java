/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfazSwing;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import modelo.Conexion;
import modelo.Persona;

/**
 *
 * @author Administrador
 */
public class frmPersona extends javax.swing.JFrame {
    private List<Persona> personas;
    private final Persona persona = new Persona();
    int pagInicial = 0;
    /**
     * Creates new form frmPersona
     */
    public frmPersona() throws SQLException, ClassNotFoundException {
        initComponents();
        personalizarGrilla();
        cargar_lista_de_personas();
    }
    //-------------------Métodos-----------------------
    private void editarRegistro(Object id, Object rut, Object nombres, Object apellidos){
        frmModificarPersona ventana = new frmModificarPersona(id,rut,nombres,apellidos);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Permite que se cierre solo la ventana y no el programa
    }
    private void personalizarGrilla() {

        // Esta lista contiene los nombres que se mostrarán en el encabezado de cada columna de la grilla
        String[] columnas = new String[]{
            "Id",
            "Rut",
            "Nombres",
            "Apellidos",
            "Editar",
            "Eliminar"};

        // Estos son los tipos de datos de cada columna de la lista
        final Class[] tiposColumnas = new Class[]{
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            java.lang.String.class,
            JButton.class, // <- noten que aquí se especifica que la última columna es un botón
            JButton.class
        };

        

        // Defino el TableModel y le indico los datos y nombres de columnas
        this.grillaPersonas.setModel(new javax.swing.table.DefaultTableModel(
                null,
                columnas) {
            // Esta variable nos permite conocer de antemano los tipos de datos de cada columna, dentro del TableModel
            Class[] tipos = tiposColumnas;

            @Override
            public Class getColumnClass(int columnIndex) {
                // Este método es invocado por el CellRenderer para saber que dibujar en la celda,
                // observen que estamos retornando la clase que definimos de antemano.
                return tipos[columnIndex];
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                // Sobrescribimos este método para evitar que la columna que contiene los botones sea editada.
                return !(this.getColumnClass(column).equals(JButton.class));
            }
        });

        // El objetivo de la siguiente línea es indicar el CellRenderer que será utilizado para dibujar el botón
        this.grillaPersonas.setDefaultRenderer(JButton.class, new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) {
                /**
                 * Observen que todo lo que hacemos en éste método es retornar el objeto que se va a dibujar en la 
                 * celda. Esto significa que se dibujará en la celda el objeto que devuelva el TableModel. También 
                 * significa que este renderer nos permitiría dibujar cualquier objeto gráfico en la grilla, pues 
                 * retorna el objeto tal y como lo recibe.
                 */
                return (Component) objeto;
            }

           // @Override
            //public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
              //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            //}
        });

        /**
         * Por último, agregamos un listener que nos permita saber cuando fue pulsada la celda que contiene el botón.
         * Noten que estamos capturando el clic sobre JTable, no el clic sobre el JButton. Esto también implica que en 
         * lugar de poner un botón en la celda, simplemente pudimos definir un CellRenderer que hiciera parecer que la 
         * celda contiene un botón. Es posible capturar el clic del botón, pero a mi parecer el efecto es el mismo y 
         * hacerlo de esta forma es más "simple"
         */
        this.grillaPersonas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = grillaPersonas.rowAtPoint(e.getPoint());
                int columna = grillaPersonas.columnAtPoint(e.getPoint());
                if(columna==4){//La columna 4 corresponde a editar
                    Object id = grillaPersonas.getModel().getValueAt(fila, 0);
                    Object rut = grillaPersonas.getModel().getValueAt(fila, 1);
                    Object nombres = grillaPersonas.getModel().getValueAt(fila, 2);
                    Object apellidos = grillaPersonas.getModel().getValueAt(fila, 3);
                    editarRegistro(id,rut,nombres,apellidos);
                }
                if(columna==5){//La columna 5 corresponde a eliminar
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < grillaPersonas.getModel().getColumnCount(); i++) {
                        if (!grillaPersonas.getModel().getColumnClass(i).equals(JButton.class)) {
                            sb.append("\n").append(grillaPersonas.getModel().getColumnName(i)).append(": ").append(grillaPersonas.getModel().getValueAt(fila, i));
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Eliminar la fila " + fila + sb.toString()+" nombre del boton:");
                }
                /**
                 * Preguntamos si hicimos clic sobre la celda que contiene el botón, si tuviéramos más de un botón 
                 * por fila tendríamos que además preguntar por el contenido del botón o el nombre de la columna
                 */
                //if (grillaPersonas.getModel().getColumnClass(columna).equals(JButton.class)) {
                    /**
                     * Aquí pueden poner lo que quieran, para efectos de este ejemplo, voy a mostrar
                     * en un cuadro de dialogo todos los campos de la fila que no sean un botón.
                     */
                   // StringBuilder sb = new StringBuilder();
                   // for (int i = 0; i < grillaPersonas.getModel().getColumnCount(); i++) {
                        //if (!grillaPersonas.getModel().getColumnClass(i).equals(JButton.class)) {
                            //sb.append("\n").append(grillaPersonas.getModel().getColumnName(i)).append(": ").append(grillaPersonas.getModel().getValueAt(fila, i));
                        //}
                    //}
                    //JOptionPane.showMessageDialog(null, "Seleccionada la fila " + fila + sb.toString()+" nombre del boton:");
                //}
            }
        });
    }
    private void cargar_lista_de_personas() throws SQLException, ClassNotFoundException{
        Conexion conectar;
        conectar = new Conexion();
        JButton etiqueta; 
        Conexion.abrir(); 
        int cantidadRegistrosPorPagina = Integer.parseInt((String) this.comboboxCantidadPorPagina.getSelectedItem());
        //Si se abre por primera vez, y el campo de texto correspondiente al número
        // de página esta vacio, se mostrará desde la página 1
        if(this.textPaginaActual.getText().equals("")){
            this.pagInicial = 1;
        }else{
            this.pagInicial = Integer.parseInt(this.textPaginaActual.getText());
        }
        this.textPaginaActual.setText(Integer.toString(this.pagInicial));
        try{
            
            this.personas = this.persona.mostrar(cantidadRegistrosPorPagina, this.pagInicial,this.txtBuscar.getText());
            DefaultTableModel dtm = (DefaultTableModel) grillaPersonas.getModel();
            dtm.setRowCount(0);
            if(personas!=null){//Si el objeto no es null imprimirá los valores
                for (Persona persona : personas) {
                    etiqueta = new JButton("Editar");
                    etiqueta.setName("editar");
                    dtm.addRow(new Object[]{
                        persona.getId(),
                        persona.getRut(),
                        persona.getNombres(),
                        persona.getApellidos(),
                        etiqueta,
                        new JButton("Eliminar")
                    });
                }
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this, "Ha surgido un error y no se han podido recuperar los registros");
        }catch(ClassNotFoundException ex){
            System.out.println(ex);
            JOptionPane.showMessageDialog(this, "Ha surgido un error y no se han podido recuperar los registros");
        }
        this.labelTotalPaginas.setText(
                                        Integer.toString(
                                                dividirRedondear(this.persona.cantidadRegistros(),cantidadRegistrosPorPagina)
                                        )
        );                          
       // Conexion.cerrar();
    }
    public static int dividirRedondear(double a, double b)
    {
          if((a/b)%1==0){//Si el numero no es entero se devuelve el numero entero
              return (int) (a/b);
          }else{//Si el numero es double, se le suma uno para redondearlo
              return (int) ((a/b)+1);
          }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        grillaPersonas = new javax.swing.JTable();
        botonAnterior = new javax.swing.JButton();
        botonSiguiente = new javax.swing.JButton();
        labelTotalPaginas = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        textPaginaActual = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        botonBuscar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        comboboxCantidadPorPagina = new javax.swing.JComboBox();
        botonIr = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Persona");

        grillaPersonas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Rut", "Nombres", "Apellidos", "Acción"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(grillaPersonas);

        botonAnterior.setText("Anterior");
        botonAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAnteriorActionPerformed(evt);
            }
        });

        botonSiguiente.setText("Siguiente");
        botonSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSiguienteActionPerformed(evt);
            }
        });

        labelTotalPaginas.setText("labelTotalPaginas");

        jLabel1.setText("/");

        jLabel2.setText("Buscar : ");

        botonBuscar.setText("Buscar");
        botonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarActionPerformed(evt);
            }
        });

        jLabel3.setText("Registros por página:");

        comboboxCantidadPorPagina.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5", "10", "15", "20", "40", "80", "100" }));
        comboboxCantidadPorPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboboxCantidadPorPaginaActionPerformed(evt);
            }
        });

        botonIr.setText("Ir");
        botonIr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonIrActionPerformed(evt);
            }
        });

        jButton1.setText("Agregar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscar)
                        .addGap(18, 18, 18)
                        .addComponent(botonBuscar)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboboxCantidadPorPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                        .addComponent(botonAnterior)
                        .addGap(18, 18, 18)
                        .addComponent(botonSiguiente)
                        .addGap(31, 31, 31)
                        .addComponent(textPaginaActual, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelTotalPaginas, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonIr)))
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonBuscar)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAnterior)
                    .addComponent(botonSiguiente)
                    .addComponent(jLabel3)
                    .addComponent(comboboxCantidadPorPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textPaginaActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(labelTotalPaginas)
                    .addComponent(botonIr))
                .addContainerGap(151, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboboxCantidadPorPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboboxCantidadPorPaginaActionPerformed
        this.pagInicial = 0;
        try {
            this.cargar_lista_de_personas();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(frmPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_comboboxCantidadPorPaginaActionPerformed

    private void botonIrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonIrActionPerformed
        try {
            this.cargar_lista_de_personas();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(frmPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonIrActionPerformed

    private void botonSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSiguienteActionPerformed
        if(Integer.parseInt(this.textPaginaActual.getText())+1>Integer.parseInt(this.labelTotalPaginas.getText())){
            //Esto es para que no se pase de la última página
        }else{
            this.pagInicial = Integer.parseInt(this.textPaginaActual.getText())+1;
            this.textPaginaActual.setText(Integer.toString(this.pagInicial));

            try {
                this.cargar_lista_de_personas();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(frmPersona.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_botonSiguienteActionPerformed

    private void botonAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnteriorActionPerformed
        if(Integer.parseInt(this.textPaginaActual.getText())-2<0){
            //Esto es para que no se pase de la última página
        }else{
            this.pagInicial = Integer.parseInt(this.textPaginaActual.getText())-1;
            this.textPaginaActual.setText(Integer.toString(this.pagInicial));

            try {
                this.cargar_lista_de_personas();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(frmPersona.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_botonAnteriorActionPerformed

    private void botonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarActionPerformed
        try {
            this.cargar_lista_de_personas();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(frmPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonBuscarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        frmCrearPersona ventana = new frmCrearPersona();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//Permite que se cierre solo la ventana y no el programa
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPersona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new frmPersona().setVisible(true);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(frmPersona.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAnterior;
    private javax.swing.JButton botonBuscar;
    private javax.swing.JButton botonIr;
    private javax.swing.JButton botonSiguiente;
    private javax.swing.JComboBox comboboxCantidadPorPagina;
    private javax.swing.JTable grillaPersonas;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelTotalPaginas;
    private javax.swing.JTextField textPaginaActual;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
