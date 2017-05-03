/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;
import java.sql.*;
/**
 *
 * @author Administrador
 */
public class Conexion {
    private static Connection cnx = null;
    private static String host = "localhost";
    private static String bd = "micro_emprendimiento_social";
    private static String usuario = "root";
    private static String contrasena = "root";
    public static Connection abrir() throws SQLException, ClassNotFoundException {
        if (cnx == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                cnx = DriverManager.getConnection("jdbc:mysql://"+host+"/"+bd, usuario, contrasena);
            } catch (SQLException ex) {
                throw new SQLException(ex);
            } catch (ClassNotFoundException ex) {
                throw new ClassCastException(ex.getMessage());
            }
            return cnx;
        }
        return null;
    }
    
    public static void cerrar() throws SQLException {
        if (cnx != null) {
            cnx.close();
        }
    }
    
    public ResultSet consultaSQL(String sql,String sqlCantidad,String apodoCount,int cantidadRegistrosPorPagina,int paginaActual) throws SQLException, ClassNotFoundException{       
        //El paginador comienza desde la página 1
    
        ResultSet resultado;
        int cantidadPaginas = Math.round(cantidadRegistros(sqlCantidad,apodoCount)/cantidadRegistrosPorPagina);
        if(cantidadPaginas<(paginaActual-1)){//Si la pagina ingresada es mayor a la pagina maxima
            return null;
        }else if((paginaActual-1)<=0){
            paginaActual = 0;
        }else{
            paginaActual = paginaActual - 1;
        }
        paginaActual = paginaActual * cantidadRegistrosPorPagina;
        try{
            PreparedStatement consulta = cnx.prepareStatement(sql+" LIMIT "+paginaActual+","+cantidadRegistrosPorPagina);
            resultado = consulta.executeQuery();
        }catch(SQLException ex){
            throw new SQLException(ex);
        }
        return resultado;
    }
    
    public int cantidadRegistros(String sql, String apodoCount) throws SQLException, ClassNotFoundException{
        int cantidad = 0;
        try{
            PreparedStatement consulta = cnx.prepareStatement(sql);
            ResultSet resultado = consulta.executeQuery();
            while(resultado.next()){
                cantidad = resultado.getInt(apodoCount);
            }
        }catch(SQLException ex){
            throw new SQLException(ex);
        }
        return cantidad;
    }
}
