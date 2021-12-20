package pe.edu.upeu.app;

import pe.edu.upeu.dao.ProductoDao;
import pe.edu.upeu.dao.UsuarioDao;
import pe.edu.upeu.dao.VentaDao;
import pe.edu.upeu.utils.LeerTeclado;

public class MenuPrincipal{
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

    LeerTeclado lt=new LeerTeclado();   
    ProductoDao prodDao;
    UsuarioDao uDao;
    VentaDao ventDao;


    public void mainLogin(){
      uDao=new UsuarioDao();
      if (uDao.login()) { 
      String perf=lt.leer("",ANSI_CYAN+"Ingrese el perfil("+ANSI_RESET+ANSI_YELLOW+"VENDEDOR"+ANSI_RESET+ANSI_CYAN+"/"+ANSI_RESET+ANSI_GREEN+"ADMINISTRADOR"+ANSI_RESET+ANSI_CYAN+")"+ANSI_RESET);
        if(perf.equals("ADMINISTRADOR")){

          menuOpciones();

        }if(perf.equals("VENDEDOR")){

          menuOpcionesSecundario();

        }
        
      }else{
        System.out.println("Intente Nuevamente!!");
        mainLogin();
      }

    }


    public void menuOpciones() {        
        int opcionesA=0;
        System.out.println(ANSI_GREEN_BACKGROUND+ANSI_BLACK+"°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°Bienvenido al menu de opciones de la gestion de ventas de un quiosco de periódicos (ADMINISTRADOR)°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°"+ANSI_RESET);
        String msg="\n"+ANSI_YELLOW+"Elija la acción a realizar:\n"+ANSI_RESET+ 
        "\n"+ANSI_CYAN+"1=Crear Producto"+
        "\n2=Listar Producto"+
        "\n3=Editar Producto"+
        "\n4=Eliminar Producto"+
        "\n5=Crear Nuevo Usuario"+
        "\n6=Realizar una venta"+
        "\n7=Reportar Ventas"+
        "\n0=Salir de la aplicacion"+
        "\n"+ANSI_RESET;        
        opcionesA=lt.leer(0, msg);  
        while(opcionesA!=0){
            switch(opcionesA) {

              case 1:{ 
                    prodDao=new ProductoDao();
                    prodDao.crearProducto();
                    } break;

            
              case 2: {
                  prodDao=new ProductoDao();
                  prodDao.reportarProductos();
              } break;  
              
              
              case 3: {
                prodDao=new ProductoDao();
                prodDao.updateProducto();
            } break; 
            

              case 4: {
                prodDao=new ProductoDao();
                prodDao.deleteProducto();
            } break;


            case 5: {
              uDao=new UsuarioDao();
              uDao.crearUsuario();
          } break;

            case 6: {
              ventDao=new VentaDao();
              ventDao.registrarVenta();
            } break;

            case 7: {
              ventDao=new VentaDao();
              ventDao.reporteVentasRangoFecha();
            } break;

              default: System.out.println(ANSI_RED+"Elija una opcion existente!!"+ANSI_RESET);
            }   
          System.out.println(ANSI_GREEN+"----------------------------------------------------------------"+ANSI_RESET);         
          opcionesA=lt.leer(0,msg);        
        }        
    }


    public void menuOpcionesSecundario() {        
      int numOpcion=0;
      System.out.println(ANSI_BLUE_BACKGROUND+ANSI_YELLOW+"°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°Bienvenido al menu de opciones de la gestion de ventas de un quiosco de periódicos (VENDEDOR)°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°"+ANSI_RESET);
      String msg="\n"+ANSI_YELLOW+"Elija la acción a realizar:\n"+ANSI_RESET+ 
      "\n"+ANSI_CYAN+"1=Listar Producto"+
      "\n2=Realizar una venta"+
      "\n3=Reportar Ventas"+
      "\n0=Salir de la aplicacion"+
      "\n"+ANSI_RESET;        
      numOpcion=lt.leer(0, msg);  
      while(numOpcion!=0){
          switch(numOpcion) {

            case 1: {
              prodDao=new ProductoDao();
              prodDao.reportarProductos();
            } break; 

          
            case 2: {
              ventDao=new VentaDao();
              ventDao.registrarVenta();
            } break;
            
            
            case 3: {
              ventDao=new VentaDao();
              ventDao.reporteVentasRangoFecha();
            } break;
          
            default: System.out.println(ANSI_RED+"Elija una opcion existente!!"+ANSI_RESET);
          }   
        System.out.println(ANSI_GREEN+"----------------------------------------------------------------"+ANSI_RESET);       
        numOpcion=lt.leer(0,msg);        
      }        
  }
}
