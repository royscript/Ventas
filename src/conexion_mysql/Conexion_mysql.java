/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conexion_mysql;

import java.sql.SQLException;
import java.util.List;
import modelo.Conexion;
import modelo.Persona;

/**
 *
 * @author Administrador
 */
public class Conexion_mysql {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // TODO code application logic here
        Conexion conectar;
        conectar = new Conexion();
        Conexion.abrir();
        List<Persona> personas;
        Persona per = new Persona();
        personas = per.mostrar(5, 1,"");
        if(personas!=null){//Si el objeto no es null imprimirá los valores
            for (Persona persona : personas) {
                System.out.println(persona.getId());
                System.out.println(persona.getRut());
                System.out.println(persona.getNombres());
                System.out.println(persona.getApellidos());
            }
        }
        int cant = per.cantidadRegistros();
        Conexion.cerrar();
        System.out.println("Cantidad de registros "+cant);
    }
    
}
