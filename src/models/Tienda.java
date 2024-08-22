package models;
import exceptions.*;
import interfaces.IImportado;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tienda {

    //Atributos
    private String nombre;
    private int numeroMaximoProductosEnStock;
    private BigDecimal saldoEnCaja;
    private List<Producto> productosEnStock;

    //Constructor
    public Tienda(String nombre, int numeroMaximoProductosEnStock, BigDecimal saldoEnCaja) {
        this.nombre = nombre;
        this.numeroMaximoProductosEnStock = numeroMaximoProductosEnStock;
        this.saldoEnCaja = saldoEnCaja;
        this.productosEnStock = new ArrayList<>();

    }

    //Getter y Setter

    public BigDecimal getSaldoEnCaja() {
        return saldoEnCaja;
    }

    public void setSaldoEnCaja(BigDecimal saldoEnCaja) {
        this.saldoEnCaja = saldoEnCaja;
    }

    public List<Producto> getProductosEnStock() {
        return productosEnStock;
    }


    //Metodos

    public boolean agregarProducto(Producto producto) {

        verificarCapacidadMaxima(producto);

        // Verifica si el producto ya existe en la lista
        for (Producto p : productosEnStock) {
            if (p.getId().equals(producto.getId())) {
                // Si el producto ya está en stock, actualiza el stock
                p.setStock(p.getStock() + producto.getStock());
                return true;
            }
        }

        return productosEnStock.add(producto);
    }

    public List<Producto> comprarProducto(Producto producto) {
        BigDecimal totalProductoConvertido = calcularTotalProducto(producto);

        if (esSaldoSuficiente(totalProductoConvertido)) {
            if (agregarProducto(producto)) {
                reducirSaldoEnCaja(totalProductoConvertido);
                return productosEnStock;
            } else {
                throw new ProductoNoAgregadoException(producto);
            }
        } else {
            throw new SaldoInsuficienteException(this.getSaldoEnCaja(), totalProductoConvertido);
        }
    }

    private void verificarCapacidadMaxima(Producto producto) {
        int stockTotal = calcularStockTotal();
        if (stockTotal + producto.getStock() > numeroMaximoProductosEnStock) {
            throw new CapacidadTiendaMaximoAlcalzadoException(numeroMaximoProductosEnStock);
        }
    }

    private void reducirSaldoEnCaja(BigDecimal cantidad) {
        BigDecimal saldoActualizado = this.getSaldoEnCaja().subtract(cantidad);
        this.setSaldoEnCaja(saldoActualizado);
    }

    private BigDecimal calcularTotalProducto(Producto producto) {
        double totalProducto = producto.getStock() * producto.getPrecio();
        return BigDecimal.valueOf(totalProducto);
    }

    private boolean esSaldoSuficiente(BigDecimal totalProductoConvertido) {
        return this.getSaldoEnCaja().compareTo(totalProductoConvertido) >= 0;
    }

    private int calcularStockTotal() {
        int stockTotal = 0;

        for (Producto producto : productosEnStock) {
            stockTotal += producto.getStock();
        }

        return stockTotal;
    }

    private boolean validarVentaProducto(List<Producto> productosVendidos, int cantidadSolicitada) {

        Map<Producto, Integer> conteoProductos = new HashMap<>();

        for (Producto producto : productosVendidos) {

            conteoProductos.put(producto, conteoProductos.getOrDefault(producto, 0) + 1);
        }


        if (cantidadSolicitada > 12) {
            return false;
        }

        if (conteoProductos.size() > 3) {
            return false;
        }

        return true;
    }

    public String ventaProducto(List<Producto> productosVendidos, int cantidad) {

        if (!validarVentaProducto(productosVendidos, cantidad)) {
            throw new VentaDeProductoException(productosVendidos);
        }

        StringBuilder detalleVenta = new StringBuilder();
        double totalVenta = 0;
        double gananciaTotal = 0;

        for (Producto prod : productosVendidos) {
            double totalPorProducto = procesarProducto(prod, cantidad, detalleVenta);
            totalVenta += totalPorProducto;
            gananciaTotal += prod.calcularPorcentajeGanancia(prod, prod.getPorcentajeGanancia(), totalPorProducto);
        }

        actualizarSaldoEnCaja(totalVenta + gananciaTotal);

        detalleVenta.append(String.format("TOTAL VENTA: $%.2f", totalVenta));
        return detalleVenta.toString();
    }

    private double procesarProducto(Producto prod, int cantidad, StringBuilder detalleVenta) {
        int stockDisponible = prod.getStock();

        if (!prod.esDisponibleParaLaVenta()) {
            detalleVenta.append(String.format("El producto %s %s no se encuentra disponible.\n", prod.getId(), prod.getDescripcion()));
            return 0;
        }

        int cantidadVendida = Math.min(stockDisponible, cantidad);
        double totalPorProducto = prod.getPrecio() * cantidadVendida;
        double descuento = prod.calcularDescuento(prod, prod.getPorcentajeDescuento(), totalPorProducto);
        totalPorProducto -= descuento;

        if (prod instanceof IImportado && ((IImportado) prod).esImportado()) {
            totalPorProducto += totalPorProducto * 0.12;
        }

        detalleVenta.append(prod.detalleProducto(cantidadVendida)).append("\n");

        if (stockDisponible < cantidad) {
            detalleVenta.append(String.format("Hay productos con stock disponible (%d) menor al solicitado (%d).\n", stockDisponible, cantidad));
            prod.setStock(0);
            prod.setEsDisponibleParaLaVenta(false);
        } else {
            prod.setStock(stockDisponible - cantidad);
        }

        return totalPorProducto;
    }

    private void actualizarSaldoEnCaja(double gananciaTotal) {
        BigDecimal saldoActualizado = this.getSaldoEnCaja().add(BigDecimal.valueOf(gananciaTotal));
        this.setSaldoEnCaja(saldoActualizado);
    }

    public String mostrarProductos(List<Producto> listaDeProductos) {

        StringBuilder stockList = new StringBuilder();

        for (Producto producto : listaDeProductos) {
            stockList.append(producto.toString()).append("\n");
        }

        if (stockList.length() == 0) {
            throw new RuntimeException("No hay productos cargados en la tienda.");
        }

        return stockList.toString();
    }

    public void asignarNuevoDescuento(Producto producto, double porcentajeDescuento) {

        if(!producto.esDescuentoValido(producto, porcentajeDescuento)) {
            throw new DescuentoNoValidoException(porcentajeDescuento);
        }

        producto.setPorcentajeDescuento(porcentajeDescuento);
    }

    @Override
    public String toString() {
        return "Tienda {" +
                "nombre='" + nombre +
                ", numeroMaximoProductosEnStock=" + numeroMaximoProductosEnStock +
                ", saldoEnCaja=" + saldoEnCaja +
                ", productosEnStock=" + productosEnStock +
                '}';
    }

    public void mostrarMenu() {
        System.out.println("=== Menú de Opciones ===");
        System.out.println("1. Mostrar los productos en stock");
        System.out.println("2. Agregar productos (La tienda no cuenta con productos inicialmente)");
        System.out.println("3. Agregar Productos (Exceder saldo de la tienda)");
        System.out.println("4. Agregar Productos (Exceder stock maximo de la tienda)");
        System.out.println("5. Actualizar descuentos de productos (Inicialmente el descuento de todos los productos es 0)");
        System.out.println("6. Actualizar descuentos de productos (No cumple con condición descuento maximo)");
        System.out.println("7. Realizar venta exitosamente");
        System.out.println("8. Realizar venta (No cumple con condición de venta de menos de 3 productos)");
        System.out.println("9. Realizar venta (No cumple con condición de venta de menos de 12 productos de cada tipo)");
        System.out.println("10. Opcion 10");
        System.out.println("11. Opcion 11");
        System.out.println("12. Opcion 12");
        System.out.println("13. Salir");
    }
}
