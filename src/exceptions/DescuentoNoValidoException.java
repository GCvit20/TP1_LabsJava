package exceptions;

public class DescuentoNoValidoException extends RuntimeException {

    private double descuento;

    public DescuentoNoValidoException(double descuento) {
        this.descuento = descuento;
    }

    public double getDescuento() {
        return descuento;
    }

    @Override
    public String getMessage() {
        return "El descuento que se quiso aplicar fue del " + getDescuento() + "% y no es valido.";
    }
}
