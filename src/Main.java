import enums.TipoAplicacion;
import models.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        boolean flag = true;

        LocalDate fechaBebida = LocalDate.of(2025, 10, 20);
        LocalDate fechaEnvasado = LocalDate.of(2025, 7, 20);

        BigDecimal saldoCaja = BigDecimal.valueOf(50000);
        Tienda tienda = new Tienda("Mc", 100, saldoCaja);

        Producto bebida = new Bebida("AC123", "Vodka Absolut", 10, 100, 0, 0, 1, false, 2, fechaBebida);
        Producto bebida2 = new Bebida("AC456", "Gin Bombay", 20, 100, 0, 0, 1, false, 2, fechaBebida);
        Producto envasado = new Envasado("AB156", "Pure de tomate", 10, 200, 0, 0, "Metal", false, 2, fechaEnvasado);
        Producto envasado2 = new Envasado("AB666", "Mermelada", 20, 200, 0, 0, "Plástico", false, 2, fechaEnvasado);
        Producto limpieza = new Limpieza("AZ888", "Detergente CIF", 10, 300, 0, 0, TipoAplicacion.COCINA);
        Producto limpieza2 = new Limpieza("AZ555", "Lavandina", 20, 300, 0, 0, TipoAplicacion.MULTIUSO);


        do {
            tienda.mostrarMenu();
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (opcion) {
                    case 1:
                        System.out.println(tienda.mostrarProductos(tienda.getProductosEnStock()));
                        break;
                    case 2:
                        tienda.comprarProducto(bebida);
                        tienda.comprarProducto(bebida2);
                        tienda.comprarProducto(envasado);
                        tienda.comprarProducto(envasado2);
                        tienda.comprarProducto(limpieza);
                        tienda.comprarProducto(limpieza2);
                        System.out.println("Productos agregado con exito.");
                        break;
                    case 3:
                        Producto bebida3 = new Bebida("AC789", "Fernet Branca", 20, 5000, 0, 0, 1, false, 2, fechaBebida);
                        tienda.comprarProducto(bebida3);
                        break;
                    case 4:
                        Producto envasado3 = new Envasado("AB753", "Miel", 101, 200, 0, 0, "Vidrio", true, 2, fechaEnvasado);
                        tienda.comprarProducto(envasado3);
                        break;
                    case 5:
                        tienda.asignarNuevoDescuento(bebida, 10);
                        tienda.asignarNuevoDescuento(bebida2, 5);
                        tienda.asignarNuevoDescuento(envasado, 10);
                        tienda.asignarNuevoDescuento(envasado2, 15);
                        tienda.asignarNuevoDescuento(limpieza, 10);
                        tienda.asignarNuevoDescuento(limpieza2, 20);
                        System.out.println("Descuentos actualizados con exito.");
                        break;
                    case 6:
                        tienda.asignarNuevoDescuento(bebida, 20);
                        break;
                    case 7:
                        List<Producto> listaVenta = new ArrayList<>();
                        listaVenta.add(bebida);
                        listaVenta.add(limpieza);
                        listaVenta.add(envasado);
                        System.out.println(tienda.ventaProducto(listaVenta, 10));
                        break;
                    case 8:
                        List<Producto> listaVenta2 = new ArrayList<>();
                        listaVenta2.add(bebida);
                        listaVenta2.add(limpieza);
                        listaVenta2.add(envasado);
                        listaVenta2.add(envasado2);
                        System.out.println(tienda.ventaProducto(listaVenta2, 10));
                        break;
                    case 9:
                        List<Producto> listaVenta3 = new ArrayList<>();
                        listaVenta3.add(bebida);
                        listaVenta3.add(limpieza);
                        listaVenta3.add(envasado);
                        System.out.println(tienda.ventaProducto(listaVenta3, 15));
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        System.out.println("Saliendo de la tienda...");
                        flag = false;
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, elige una opción entre 1 y 14.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Se ha producido un error: " + e.getMessage());
            }

        } while (flag);

        scanner.close();
    }
}