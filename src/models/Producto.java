package models;

import enums.TipoAplicacion;
import exceptions.PorcentajeDeGananciaInvalidoException;
import exceptions.PrecioConDescuentoInvalidoException;

public class Producto {

    // Atributos
    private String id;
    private String descripcion;
    private int stock;
    private double precio;
    private int porcentajeGanancia;
    private boolean esDisponibleParaLaVenta;
    private double porcentajeDescuento;

    //Constructores

    public Producto(String id, String descripcion, int stock, double precio, int porcentajeGanancia, double porcentajeDescuento) {

        this.id = id;
        this.descripcion = descripcion;
        this.stock = stock;
        this.precio = precio;
        this.porcentajeGanancia = porcentajeGanancia;
        this.porcentajeDescuento = porcentajeDescuento;
        this.esDisponibleParaLaVenta = true;
    }

    // Getters y Setters

    public String getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setEsDisponibleParaLaVenta(boolean esDisponibleParaLaVenta) {
        this.esDisponibleParaLaVenta = esDisponibleParaLaVenta;
    }

    public boolean esDisponibleParaLaVenta() {
        return esDisponibleParaLaVenta;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public int getPorcentajeGanancia() {
        return porcentajeGanancia;
    }

    //Metodos

    public double calcularPorcentajeGanancia(Producto producto, int porcentajeGanancia, double totalProducto) {

        if(!esPorcentajeDeGananciaValido(producto, porcentajeGanancia)) {
            throw new PorcentajeDeGananciaInvalidoException(porcentajeGanancia);
        }

        return (totalProducto * porcentajeGanancia) / 100;
    }

    protected boolean esPorcentajeDeGananciaValido(Producto producto, double porcentajeGanancia) {
        if (producto instanceof Bebida || producto instanceof Envasado) {
            return porcentajeGanancia >= 0 && porcentajeGanancia <= 20;
        } else if (producto instanceof Limpieza) {
            Limpieza productoLimpieza = (Limpieza) producto;

            if (productoLimpieza.getTipoAplicacion() == TipoAplicacion.COCINA ||
                    productoLimpieza.getTipoAplicacion() == TipoAplicacion.MULTIUSO) {
                return porcentajeGanancia <= 25;
            } else {
                return porcentajeGanancia >= 10 && porcentajeGanancia <= 25;
            }
        }

        return false;
    }
    
    public double calcularDescuento(Producto producto, double porcentajeDescuento, double totalProducto) {

        if(!esDescuentoValido(producto, porcentajeDescuento)) {
            throw new PrecioConDescuentoInvalidoException(porcentajeDescuento);
        }

        return (totalProducto * porcentajeDescuento) / 100;
    }

    protected boolean esDescuentoValido(Producto producto, double porcentajeDescuento) {

        if (producto instanceof Bebida) {
            return porcentajeDescuento >= 0 && porcentajeDescuento <= 10;
        } else if (producto instanceof Envasado) {
            return porcentajeDescuento >= 0 && porcentajeDescuento <= 15;
        } else if (producto instanceof Limpieza) {
            return porcentajeDescuento >= 0 && porcentajeDescuento <= 20;
        }

        return false;
    }

    public String toString() {
        return String.format("Bebida {id: %s, descripcion: %s, stock: %d, precio: $%.2f, porcentaje de ganancia: %d, porcentaje de descuento: %.2f}",
                getId(),
                getDescripcion(),
                getStock(),
                getPrecio(),
                getPorcentajeGanancia(),
                getPorcentajeDescuento());
    }

    public String detalleProducto(int cantidadVendida) {
        return String.format("%s %s %d x %.2f", getId(), getDescripcion(), cantidadVendida, getPrecio());
    }
}
