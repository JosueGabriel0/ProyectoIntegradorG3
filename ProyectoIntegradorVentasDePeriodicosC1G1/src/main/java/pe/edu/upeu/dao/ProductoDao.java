package pe.edu.upeu.dao;

import pe.edu.upeu.data.AppCrud;
import pe.edu.upeu.modelo.ProductoTO;
import pe.edu.upeu.utils.LeerArchivo;
import pe.edu.upeu.utils.LeerTeclado;
import pe.edu.upeu.utils.UtilsX;

public class ProductoDao extends AppCrud {

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
    //Crear variable(TABLA_PRODUCTO)producto.txt
    final String TABLA_PRODUCTO="Producto.txt";
    //Variables del tipo LeerArchivo y ProductoTO
    LeerArchivo leerArch;
    ProductoTO prodTO;
    //Variable para Periodico o Revista
    String tipoProd="Periodico\nRevista\n";



    public Object[][] crearProducto(){
        util.clearConsole();
        System.out.println(ANSI_CYAN_BACKGROUND+ANSI_BLACK+"<------------------Crear Producto------------------->"+ANSI_RESET);
                                //(Constructor)creame la TABLA_PRODUCTO("Producto.txt")       
        leerArch=new LeerArchivo(TABLA_PRODUCTO);    
        prodTO=new ProductoTO();
        prodTO.setIdProd(generarId(leerArch, 0, "P", 1));
        prodTO.setNombre(leerTecla.leer("",ANSI_YELLOW+"Ingrese nombre del Producto"+ANSI_RESET));
        prodTO.setPrecio(leerTecla.leer(0.0,ANSI_YELLOW+"Ingrese precio base del Producto"+ANSI_RESET));
        prodTO.setUnidadMed(leerTecla.leer("",ANSI_YELLOW+ "Ingrese unidad de medidad"+ANSI_RESET));
        prodTO.setTipo(leerTecla.leer("",ANSI_YELLOW+"Ingrese el tipo ("+tipoProd+")"+ANSI_RESET));
        prodTO.setUtilidad(leerTecla.leer(0.0,ANSI_YELLOW+"Ingrese la utilidad Ejem: 0.02"+ANSI_RESET));
        prodTO.setStock(leerTecla.leer(0,ANSI_YELLOW+"Ingrese el Stock"+ANSI_RESET));
        leerArch=new LeerArchivo(TABLA_PRODUCTO);           
        return agregarContenido(leerArch, prodTO); 
        
    }

    public void reportarProductos() {
        util.clearConsole();
        System.out.println(ANSI_YELLOW_BACKGROUND+ANSI_BLACK+"<------------------Listar Productos------------------->"+ANSI_RESET);
        leerArch=new LeerArchivo(TABLA_PRODUCTO);
       Object data[][] = listarContenido(leerArch);
       util.pintarLine('H', 60);
       util.pintarTextHeadBody('H', 4, ANSI_YELLOW+"ID,Nombre,Tipo,Precio,Utilidad,Stock,Unidad de Medida"+ANSI_RESET);
       System.out.println("");        
       util.pintarLine('H', 60);
       String dataPrint="";
       for (int i = 0; i < data.length; i++) {            
            dataPrint=ANSI_CYAN+data[i][0]+","+data[i][1]+","+data[i][2]+","+data[i][3]+","+data[i][4]+","+data[i][5]+","+data[i][6]+ANSI_RESET;
            util.pintarTextHeadBody('B', 4, dataPrint);   
       }
       util.pintarLine('H', 60);
       System.out.println();
    }

    public void reportarProductos(Object[][] data) {
        System.out.println(ANSI_GREEN_BACKGROUND+ANSI_BLACK+"<------------------Nuevo Listar Productos------------------->"+ANSI_RESET);
        util.pintarLine('H', 60);
        util.pintarTextHeadBody('H', 4, ANSI_YELLOW+"ID,Nombre,Tipo,Precio,Utilidad,Stock,Unidad de Medida"+ANSI_RESET);
        System.out.println("");        
        util.pintarLine('H', 60);
        String dataPrint="";
        for (int i = 0; i < data.length; i++) {            
            dataPrint=ANSI_GREEN+data[i][0]+","+data[i][1]+","+data[i][2]+","+data[i][3]+","+data[i][4]+","+data[i][5]+","+data[i][6]+ANSI_RESET;
             util.pintarTextHeadBody('B', 4, dataPrint);   
        }
        util.pintarLine('H', 60);
        System.out.println();
     }    

    public void updateProducto(){
        util.clearConsole();
        reportarProductos();
        System.out.println(ANSI_CYAN_BACKGROUND+ANSI_BLACK+"<------------------Actualizar o modificar un producto------------------->"+ANSI_RESET);
        String dataId=leerTecla.leer("",ANSI_GREEN+"Ingrese el Id del Producto a modificar");
        prodTO=new ProductoTO();
        prodTO.setNombre(leerTecla.leer("",ANSI_GREEN+"Ingrese el nuevo nombre del producto"+ANSI_RESET));
        prodTO.setTipo(leerTecla.leer("",ANSI_GREEN+"Ingrese el nuevo tipo"+ANSI_RESET));
        prodTO.setPrecio(leerTecla.leer(0.0,ANSI_GREEN+"Ingrese el nuevo precio"+ANSI_RESET));
        prodTO.setUtilidad(leerTecla.leer(0.0,ANSI_GREEN+"Ingrese la nueva utilidad"+ANSI_RESET));
        prodTO.setStock(leerTecla.leer(0,ANSI_GREEN+"Ingrese el nuevo Stock"+ANSI_RESET));
        prodTO.setUnidadMed(leerTecla.leer("",ANSI_GREEN+"Ingrese la nueva unidad de medida"+ANSI_RESET));
        leerArch=new LeerArchivo(TABLA_PRODUCTO);
        Object[][] data= editarRegistro(leerArch,0, dataId, prodTO);
        util.clearConsole();
        reportarProductos(data);


    }

    public void deleteProducto(){
        util.clearConsole();
        reportarProductos();
        System.out.println(ANSI_RED_BACKGROUND+ANSI_BLACK+"<------------------Borrar Producto------------------->"+ANSI_RESET);
        String dataId=leerTecla.leer("",ANSI_RED+"Ingrese el Id del Producto a eliminar"+ANSI_RESET);
        leerArch=new LeerArchivo(TABLA_PRODUCTO);
        Object[][] data= eliminarRegistros(leerArch, 0, dataId);
        util.clearConsole();
        reportarProductos(data);


    }




    
}
