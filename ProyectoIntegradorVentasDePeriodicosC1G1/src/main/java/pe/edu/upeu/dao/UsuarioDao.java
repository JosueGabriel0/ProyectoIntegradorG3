package pe.edu.upeu.dao;

import java.io.Console;

import pe.edu.upeu.data.AppCrud;
import pe.edu.upeu.modelo.UsuarioTO;
import pe.edu.upeu.utils.LeerArchivo;
import pe.edu.upeu.utils.LeerTeclado;
import pe.edu.upeu.utils.UtilsX;

public class UsuarioDao extends AppCrud {

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
    //Crear Usuario.txt
    final String TABLA_USUARIO="Usuario.txt";
    //UsuarioTO
    LeerArchivo leerArch;
    UsuarioTO usuTO;


    public void crearUsuario() {
        util.clearConsole();
        System.out.println(ANSI_CYAN_BACKGROUND+ANSI_BLACK+"Crear Usuario"+ANSI_RESET);
        leerArch=new LeerArchivo(TABLA_USUARIO);
        usuTO=new UsuarioTO();
        usuTO.setUsuario(leerTecla.leer("", ANSI_CYAN+"Ingrese el nuevo Usuario"+ANSI_RESET));

        Console cons=System.console();
        System.out.println( ANSI_CYAN+"Ingrese su clave:"+ANSI_RESET);
        char[] dataPass=cons.readPassword();
        usuTO.setPassword(String.valueOf(dataPass));
        usuTO.setPerfil(leerTecla.leer("",ANSI_CYAN+"Ingrese el perfil("+ANSI_RESET+ANSI_YELLOW+"VENDEDOR"+ANSI_RESET+ANSI_CYAN+"/"+ANSI_RESET+ANSI_GREEN+"ADMINISTRADOR"+ANSI_RESET+ANSI_CYAN+")"+ANSI_RESET));
        agregarContenido(leerArch, usuTO);

    }


    public boolean login(){
        util.clearConsole();
        System.out.println(ANSI_CYAN_BACKGROUND+ANSI_BLACK+"*********Autenticacion para el menu de opciones del Quiosco de periodicos**********"+ANSI_RESET);
        leerArch=new LeerArchivo(TABLA_USUARIO);
        String user=leerTecla.leer("",ANSI_YELLOW_BACKGROUND+ANSI_BLACK+"Ingrese su usuario"+ANSI_RESET);
        Object[][] data=buscarContenido(leerArch, 0, user);
        Console cons=System.console();
        System.out.println(ANSI_YELLOW_BACKGROUND+ANSI_BLACK+"Ingrese su clave:"+ANSI_RESET);
        char[] dataPass=cons.readPassword();        

        if(data.length==1 && data[0][1].equals(String.valueOf(dataPass))){
            return true;
        }
        return false;
    }



    
}
