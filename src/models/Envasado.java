package models;


import interfaces.IComestible;
import interfaces.IImportado;

import java.time.LocalDate;

public class Envasado extends Producto implements IComestible, IImportado {

    // Atributos
    private String tipoDeEnvase;
    private boolean esImportado;
    private LocalDate fechaVencimiento;
    private double calorias;

    //Constructor
    public Envasado(String id, String descripcion, int cantidadEnStock, double precio, int porcentajeGanancia,
                    double porcentajeDescuento, String tipoDeEnvase, boolean esImportado, double calorias, LocalDate fechaVencimiento) {

        super(generadorId(id), descripcion, cantidadEnStock, precio, porcentajeGanancia, porcentajeDescuento);
        this.tipoDeEnvase = tipoDeEnvase;
        this.esImportado = esImportado;
        this.calorias = calorias;
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getTipoDeEnvase() {
        return tipoDeEnvase;
    }

    public boolean esImportado() {
        return esImportado;
    }

    //Metodos
    protected static String generadorId(String id) {
        if (id == null || id.length() != 5 || !id.matches("AB\\d{3}")) {
            throw new IllegalArgumentException("Identificador de Envasado no válido. Debe seguir el formato ABXXX.");
        }
        return id;
    }

    public String toString() {

        String esImportadoStr = esImportado() ? "Si" : "No";

        return String.format(
                "id: '%s', descripcion: '%s', stock: %d, precio: $%.2f, precio ganancia: %d, precio descuento: %.2f, tipo de envace: %s, calorias: %.2f, fecha de vencimiento: %s, importado: %s",
                getId(),
                getDescripcion(),
                getStock(),
                getPrecio(),
                getPorcentajeGanancia(),
                getPorcentajeDescuento(),
                getTipoDeEnvase(),
                getCalorias(),
                getFechaVencimiento(),
                esImportadoStr
        );
    }

    @Override
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    @Override
    public double getCalorias() {
        return calorias;
    }

}
