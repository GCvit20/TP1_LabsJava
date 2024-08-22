package exceptions;

public class CapacidadTiendaMaximoAlcalzadoException extends RuntimeException {

    private int numeroMaximoProductosEnStock;

    public CapacidadTiendaMaximoAlcalzadoException(int numeroMaximoProductosEnStock) {
        this.numeroMaximoProductosEnStock = numeroMaximoProductosEnStock;
    }

    public int getNumeroMaximoProductosEnStock() {
        return numeroMaximoProductosEnStock;
    }

    @Override
    public String getMessage() {
        return  "No se puede agregar el producto. Se ha alcanzado la capacidad máxima de stock. " +
                "(la capacidad actual de la tienda es de: " + this.getNumeroMaximoProductosEnStock() + ").";
    }
}
