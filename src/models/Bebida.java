package models;

import interfaces.IComestible;
import interfaces.IImportado;
import java.time.LocalDate;

public class Bebida extends Producto implements IComestible, IImportado {

    //Atributos
    private double graduacionAlcoholica;
    private boolean esImportado;
    private double calorias;
    private LocalDate fechaVencimiento;

    //Constructor
    public Bebida(String id, String descripcion, int cantidadEnStock, double precio, int porcentajeGanancia, double porcentajeDescuento,
                  double graduacionAlcoholica, boolean esImportado, double calorias, LocalDate fechaVencimiento) {

        super(generadorId(id), descripcion, cantidadEnStock, precio, porcentajeGanancia, porcentajeDescuento);
        this.graduacionAlcoholica = graduacionAlcoholica;
        this.calorias = calcularCalorias(graduacionAlcoholica, calorias);
        this.esImportado = esImportado;
        this.fechaVencimiento = fechaVencimiento;
    }

    //Getters
    public double getGraduacionAlcoholica() {
        return graduacionAlcoholica;
    }

    public boolean esImportado() {
        return esImportado;
    }

    //Metodos

    protected static String generadorId(String id) {
        if (id == null || id.length() != 5 || !id.matches("AC\\d{3}")) {
            throw new IllegalArgumentException("Identificador de Bebida no válido. Debe seguir el formato ACXXX.");
        }
        return id;
    }

    public double calcularCalorias(double graduacionAlcoholica, double calorias) {

        if(graduacionAlcoholica < 0 || calorias < 0) {
            throw new IllegalArgumentException("La graduacion alcoholica y las calorias no pueden ser numeros negativos");
        }

        if (graduacionAlcoholica >= 0 && graduacionAlcoholica <= 2 ) {
            return calorias;
        } else if (graduacionAlcoholica >= 2.1 && graduacionAlcoholica <= 4.5) {
            return calorias * 1.25;
        } else {
            return calorias * 1.5;
        }
    }

    @Override
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    @Override
    public double getCalorias() {
        return calorias;
    }

    public String toString() {

        String esImportadoStr = esImportado() ? "Si" : "No";

        return String.format(
                "id: '%s', descripcion: '%s', stock: %d, precio: $%.2f, precio ganancia: %d, precio descuento: %.2f, graduacionAlcoholica: %.2f, calorias: %.2f, fecha de vencimiento: %s, importado: %s",
                getId(),
                getDescripcion(),
                getStock(),
                getPrecio(),
                getPorcentajeGanancia(),
                getPorcentajeDescuento(),
                getGraduacionAlcoholica(),
                getCalorias(),
                getFechaVencimiento(),
                esImportadoStr
        );
    }
}