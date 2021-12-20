package pe.edu.upeu.dao;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;



import pe.edu.upeu.data.AppCrud;
import pe.edu.upeu.modelo.ClienteTO;
import pe.edu.upeu.modelo.ProductoTO;
import pe.edu.upeu.modelo.VentaDetalleTO;
import pe.edu.upeu.modelo.VentaTO;
import pe.edu.upeu.utils.LeerArchivo;
import pe.edu.upeu.utils.LeerTeclado;
import pe.edu.upeu.utils.UtilsX;

public class VentaDao extends AppCrud{

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

    LeerTeclado leerTecla=new LeerTeclado();
    UtilsX util=new UtilsX();
    
    Ansi color;

    final String TABLA_VENTAS="Ventas.txt";
    final String TABLA_VENTA_DETALLE="VentaDetalle.txt";
    final String TABLA_PRODUCTO="Producto.txt";
    final String TABLA_CLIENTE="Cliente.txt";

    LeerArchivo leerArch;

    ProductoTO prodTO;
    VentaTO venTO;
    VentaDetalleTO ventaDetalleTO;
    ClienteTO clienteTO;

    SimpleDateFormat formatofechahora = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    SimpleDateFormat formatofecha = new SimpleDateFormat("dd-MM-yyyy");


    public void registrarVenta() {
       double subtotalXX=0;
       double totalImporteXX=0;
       double descuentoXX=0;

       VentaTO ventTo=crearVenta();
       String opcion="SI";
       if(ventTo!=null){
           //util.clearConsole();
           System.out.println(ANSI_YELLOW_BACKGROUND+ANSI_BLACK+"**********Agregar productos a carrito de ventas**********"+ANSI_RESET);
            do {
                VentaDetalleTO dataVD=carritoVenta(ventTo);   
                subtotalXX=subtotalXX+dataVD.getPrecioTotal();             
                opcion=leerTecla.leer("", ANSI_YELLOW+"¿Desea agregar un producto mas? SI/NO"+ANSI_RESET);
            } while (opcion.toUpperCase().equals("SI"));

            //Actualizar Tabla Ventas
            if(leerTecla.leer("SI", ANSI_YELLOW+"¿Desea aplicar descuento? SI/NO"+ANSI_RESET).toUpperCase().equals("SI")){
                descuentoXX=(subtotalXX/100);
                //descuentoXX=(leerTecla.leer(0,"Ingrese el descuento"));

            }else{
                descuentoXX=0;
            }
            totalImporteXX=subtotalXX-descuentoXX;
            ventTo.setDescuento(descuentoXX);
            ventTo.setSubtotal(subtotalXX);
            ventTo.setTotalimporte(totalImporteXX);

            leerArch=new LeerArchivo(TABLA_VENTAS);
            editarRegistro(leerArch, 0, ventTo.getIdVenta(), ventTo);            
       }
    }

    public VentaTO crearVenta() {
        leerArch=new LeerArchivo(TABLA_VENTAS);

        venTO=new VentaTO();
        venTO.setIdVenta(generarId(leerArch, 0, "V", 1));
        String dnix=leerTecla.leer("", ANSI_CYAN+"Ingrese el Dni del Cliente"+ANSI_RESET);
        venTO.setDni(crearCliente(dnix));
        Date fecha=new Date();
        venTO.setFecha(formatofechahora.format(fecha));
        venTO.setSubtotal(0);
        venTO.setDescuento(0);
        venTO.setTotalimporte(0);
        
        leerArch=new LeerArchivo(TABLA_VENTAS);
        agregarContenido(leerArch, venTO);

        leerArch=new LeerArchivo(TABLA_VENTAS);
        if(buscarContenido(leerArch, 0, venTO.getIdVenta()).length==1){
            return venTO;
        }else{ 
            System.out.println(ANSI_RED+"Intente nuevamente:"+ANSI_RESET);           
            return crearVenta();
        }               
    }

    public String crearCliente(String dni) {
        leerArch=new LeerArchivo(TABLA_CLIENTE);
        Object[][] dataCli=null;
        dataCli=buscarContenido(leerArch, 0, dni);
        //System.out.println("VEr:"+dataCli.length);
        if(dataCli==null || dataCli.length==0){
            if(dataCli.length==0){
                ClienteDao cDao=new ClienteDao();
                cDao.crearCliente(dni);                
            }
            return dni;
            
        }else{
            return dni;
        }       
    }

    public VentaDetalleTO carritoVenta(VentaTO venT) {
        mostrarProductos();
        ventaDetalleTO=new VentaDetalleTO();
        leerArch=new LeerArchivo(TABLA_VENTA_DETALLE);

        ventaDetalleTO.setIdDetVenta(generarId(leerArch, 0, "DV", 2));
        ventaDetalleTO.setIdVenta(venT.getIdVenta());
        ventaDetalleTO.setIdProd(leerTecla.leer("", ANSI_GREEN+"Ingrese Id del Producto a vender"+ANSI_RESET));
        leerArch=new LeerArchivo(TABLA_PRODUCTO);
        Object[][] prodData=buscarContenido(leerArch, 0, ventaDetalleTO.getIdProd());
        if(prodData!=null){
            double precioX=Double.parseDouble(String.valueOf(prodData[0][3]))
            +Double.parseDouble(String.valueOf(prodData[0][4]));
        ventaDetalleTO.setPrecioUnit(precioX);
        }
        ventaDetalleTO.setCantidad(leerTecla.leer(0, ANSI_GREEN+"Ingrese la cantidad"+ANSI_RESET));
        ventaDetalleTO.setPrecioTotal(ventaDetalleTO.getPrecioUnit()*ventaDetalleTO.getCantidad());        
        leerArch=new LeerArchivo(TABLA_VENTA_DETALLE);
        agregarContenido(leerArch, ventaDetalleTO);

        return ventaDetalleTO;
    }

    public void mostrarProductos() {
        leerArch=new LeerArchivo(TABLA_PRODUCTO);
        Object[][] data=listarContenido(leerArch);
        for (int i = 0; i < data.length; i++) {
            System.out.print(ANSI_GREEN+data[i][0]+"="+data[i][1]+
            "(Precio:"+
            (
            Double.parseDouble(String.valueOf(data[i][3]))+
            Double.parseDouble(String.valueOf(data[i][4]))
            )
            +" / Stock: "+ data[i][5]+") |\t"+ANSI_RESET);
        }
        System.out.println("\n");
    }

    public void reporteVentasRangoFecha() {
        util.clearConsole();
        System.out.println(ANSI_YELLOW_BACKGROUND+ANSI_BLACK+"<===================Registro Ventas==================>"+ANSI_RESET);
        String fechaInit=leerTecla.leer("", ANSI_CYAN+"Ingrese F. Inicio (dd-MM-yyyy)"+ANSI_RESET);
        String fechaFinal=leerTecla.leer("", ANSI_CYAN+"Ingrese F. Final (dd-MM-yyyy)"+ANSI_RESET);
        leerArch=new LeerArchivo(TABLA_VENTAS);
        Object[][] dataV=listarContenido(leerArch);
        int contadorVRD=0;
        try {
            //Para saber que cantidad de registros coinciden con el rango de fecha
            for (int i = 0; i < dataV.length; i++) {
                String[] fechaVentor=String.valueOf(dataV[i][2]).split(" ");
                Date fechaVentaX=formatofecha.parse(fechaVentor[0]);
                if(
                (fechaVentaX.after(formatofecha.parse(fechaInit)) || fechaInit.equals(fechaVentor[0])) && 
                (fechaVentaX.before(formatofecha.parse(fechaFinal)) || fechaFinal.equals(fechaVentor[0]))
                ){
                    contadorVRD++; 
                }
            }

            //Pasar los datos a un Vetor de tipo VentaTO segun Rango Fechas
            VentaTO[] dataReal=new VentaTO[contadorVRD];
            int indiceVector=0;
            for (int i = 0; i < dataV.length; i++) {
                String[] fechaVentor=String.valueOf(dataV[i][2]).split(" ");
                Date fechaVentaX=formatofecha.parse(fechaVentor[0]);
                if(
                (fechaVentaX.after(formatofecha.parse(fechaInit)) || fechaInit.equals(fechaVentor[0])) && 
                (fechaVentaX.before(formatofecha.parse(fechaFinal)) || fechaFinal.equals(fechaVentor[0]))
                ){
                   VentaTO vTo=new VentaTO();
                   vTo.setIdVenta(dataV[i][0].toString());
                   vTo.setDni(dataV[i][1].toString());
                   vTo.setFecha(dataV[i][2].toString());
                   vTo.setDescuento(Double.parseDouble(String.valueOf(dataV[i][4])));
                   vTo.setSubtotal(Double.parseDouble(String.valueOf(dataV[i][3])));
                   vTo.setTotalimporte(Double.parseDouble(String.valueOf(dataV[i][5])));
                   dataReal[indiceVector]=vTo;
                   indiceVector++;
                }
            }
            //Imprimir Ventas     
            AnsiConsole.systemInstall();
            color=new Ansi();
            System.out.println(color.bgBrightYellow().fgBlack()
            .a("<===========Reporte Ventas entre "+fechaInit+" Y "+fechaFinal+"============>").reset());
            
            util.pintarLine('H', 40);
            util.pintarTextHeadBody('H', 3, ANSI_BLUE+"ID,DNI cliente,Fecha, Sub. Total, Descuento, Imp.Total"+ANSI_RESET);
            System.out.println("");
            double subtotalX=0,descuentoX=0, importeTX =0;
            util.pintarLine('H', 40);
            for (VentaTO TOv : dataReal) {
                String datax=ANSI_CYAN+TOv.getIdVenta()+","+TOv.getDni()+","+TOv.getFecha()+","+
                TOv.getSubtotal()+","+TOv.getDescuento()+","+TOv.getTotalimporte()+ANSI_RESET;
                subtotalX+=TOv.getSubtotal(); 
                descuentoX+=TOv.getDescuento(); 
                importeTX+=TOv.getTotalimporte();
                util.pintarTextHeadBody('B', 3, datax);
            }
            //System.out.println("");
            color=new Ansi();
            util.pintarLine('H', 40);            
            System.out.println(color.render(
            
            "| @|blue Sub. Total:|@ | @|cyan S/"+ subtotalX+" |@ | @|blue Descuento: |@ @|cyan S/."+descuentoX+
            "|@ | @|blue Imp. Total: |@ @|green S/."+importeTX+"|@"
            
            )
            );
            util.pintarLine('H', 40);            


        } catch (Exception e) {
           System.err.println(ANSI_RED+"Error al Reportar!!"+ANSI_RESET+e.getMessage());
        }

        

    }
        
}