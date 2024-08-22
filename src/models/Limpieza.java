package models;

import enums.TipoAplicacion;

public class Limpieza extends Producto {

    private TipoAplicacion tipoAplicacion;

    public Limpieza(String id, String descripcion, int cantidadEnStock, double precio, int porcentajeGanancia,
                    double porcentajeDescuento, TipoAplicacion tipoAplicacion) {

        super(generadorId(id), descripcion, cantidadEnStock, precio, porcentajeGanancia, porcentajeDescuento);
        this.tipoAplicacion = tipoAplicacion;
    }

    public TipoAplicacion getTipoAplicacion() {
        return tipoAplicacion;
    }

    protected static String generadorId(String id) {
        if (id == null || id.length() != 5 || !id.matches("AZ\\d{3}")) {
            throw new IllegalArgumentException("Identificador de Limpieza no válido. Debe seguir el formato AZXXX.");
        }
        return id;
    }

    public String toString() {

        return String.format(
                "id: '%s', descripcion: '%s', stock: %d, precio: $%.2f, precio ganancia: %d, precio descuento: %.2f",
                getId(),
                getDescripcion(),
                getStock(),
                getPrecio(),
                getPorcentajeGanancia(),
                getPorcentajeDescuento()
        );
    }

}
