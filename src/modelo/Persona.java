/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class Persona {
    private int id;
    private String rut = null;
    private String nombres = null;
    private String apellidos = null;

    //Constructores
    public Persona(int id) {
        this.id = id;
    }
    public Persona(int id, String rut, String nombres, String apellidos) {
        this.id = id;
        this.rut = rut;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }
    public Persona() {
    }
    
    //Métodos set
    public void setId(int id) {
        this.id = id;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    //Métodos get
    public int getId() {
        return id;
    }

    public String getRut() {
        return rut;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }
    
    //Métodos
    public List<Persona> mostrar(int cantidadRegistrosPorPagina,int paginaActual, String parametros) throws SQLException, ClassNotFoundException{    
        List<Persona> personas = new ArrayList<>();//Preparamos un array para ingresar a todas las personas consultadas
        Conexion con = new Conexion();
        String sqlDatos = "SELECT * "
                        + "FROM `persona` "
                        + "WHERE `ID_PERSONA` LIKE '%"+parametros+"%' "
                        + "OR  `RUT_PERSONA` LIKE '%"+parametros+"%'"
                        + "OR  `NOMBRES_PERSONA` LIKE '%"+parametros+"%'"
                        + "OR  `APELLIDOS_PERSONA` LIKE '%"+parametros+"%'"
                        + "ORDER BY `ID_PERSONA` DESC";
        String sqlCantidad = "SELECT COUNT(*) AS CANTIDAD "
                        + "FROM `persona` "
                        + "WHERE `ID_PERSONA` LIKE '%"+parametros+"%' "
                        + "OR  `RUT_PERSONA` LIKE '%"+parametros+"%'"
                        + "OR  `NOMBRES_PERSONA` LIKE '%"+parametros+"%'"
                        + "OR  `APELLIDOS_PERSONA` LIKE '%"+parametros+"%'"
                        + "ORDER BY `ID_PERSONA` DESC";
        ResultSet resultado = con.consultaSQL(sqlDatos,sqlCantidad,"CANTIDAD", cantidadRegistrosPorPagina, paginaActual);
        try{
            while(resultado.next()){
                personas.add(
                        new Persona(
                                resultado.getInt("ID_PERSONA"),
                                resultado.getString("RUT_PERSONA"),
                                resultado.getString("NOMBRES_PERSONA"),
                                resultado.getString("APELLIDOS_PERSONA")
                        )
                );
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return personas;
    }
    public int cantidadRegistros() throws SQLException, ClassNotFoundException{
        int cant = 0;
        Conexion conectar = new Conexion();
        cant = conectar.cantidadRegistros("SELECT COUNT(*) AS CANTIDAD FROM `persona`","CANTIDAD");
        return cant;
    }
}
