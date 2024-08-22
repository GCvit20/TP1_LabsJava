package exceptions;

public class PrecioConDescuentoInvalidoException extends RuntimeException {

    double descuentoAplicado;

    public PrecioConDescuentoInvalidoException(double descuentoAplicado) {
        this.descuentoAplicado = descuentoAplicado;
    }

    public double getDescuentoAplicado() {
        return descuentoAplicado;
    }

    @Override
    public String getMessage() {
        return "El porcentaje de descuento no puede superar en bebidas el 10%, el de los envasados el 15% y el de los " +
                "productos de limpieza el 20%. El descuento que intento aplicar fue del: " + getDescuentoAplicado() + "%";
    }
}
