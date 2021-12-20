package pe.edu.upeu.dao;

import pe.edu.upeu.data.AppCrud;
import pe.edu.upeu.modelo.ClienteTO;
import pe.edu.upeu.utils.LeerArchivo;
import pe.edu.upeu.utils.LeerTeclado;
import pe.edu.upeu.utils.UtilsX;

public class ClienteDao extends AppCrud{

     //Reset
     public static final String ANSI_RESET = "\u001B[0m";
     //Colores de letra
     public static final String ANSI_BLACK = "\u001B[30m";
     public static final String ANSI_RED = "\u001B[31m";
     public static final String ANSI_GREEN = "\u001B[32m";
     public static final String ANSI_YELLOW = "\u001B[33m";
     public static final String ANSI_BLUE = "\u001B[34m";
     public static final String ANSI_PURPLE = "\u001B[35m";
     public static final String ANSI_CYAN = "\u001B[36m";
     public static final String ANSI_WHITE = "\u001B[37m";
     //Colores de fondo
     public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
     public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
     public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
     public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
     public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
     public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
     public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
     public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    //Objetos 
    LeerTeclado leerTecla=new LeerTeclado();
    UtilsX util=new UtilsX();
    //CrearCliente
    final String TABLA_CLIENTE="Cliente.txt";

    //ClienteTO
    LeerArchivo leerArch;
    ClienteTO cTo;

    public void crearCliente(String dni) {
        //util.clearConsole();
        cTo=new ClienteTO();        
        if(validarDni(dni)){
            cTo.setDni(dni);
            cTo.setNombre(leerTecla.leer("", ANSI_CYAN+"Ingrese su nombre"+ANSI_RESET));
            cTo.setCelular(leerTecla.leer("", ANSI_CYAN+"Ingrese su Celular"+ANSI_RESET));
            leerArch=new LeerArchivo(TABLA_CLIENTE);
            agregarContenido(leerArch, cTo);
        }
    }


    public boolean validarDni(String dni) {
        leerArch=new LeerArchivo(TABLA_CLIENTE);
        cTo=new ClienteTO();
        Object[][] data= buscarContenido(leerArch, 0, dni);
       if(data==null || data.length==0){
           return true;
       }{
           return false;      
       }            
    }
    
}
