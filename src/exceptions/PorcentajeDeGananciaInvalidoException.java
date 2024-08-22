package exceptions;

public class PorcentajeDeGananciaInvalidoException extends RuntimeException {

    private int porcentajeGanancia;

    public PorcentajeDeGananciaInvalidoException(int porcentajeGanancia) {
        this.porcentajeGanancia = porcentajeGanancia;
    }

    public int getPorcentajeGanancia() {
        return porcentajeGanancia;
    }

    @Override
    public String getMessage() {
        return  "El porcentaje de ganacia de los productos comestibles no podrá superar el 20% y " +
                "los productos de limpieza no podrá ser menor del 10% ni mayor al 25%, excepto los de " +
                "tipo COCINA y MULTIUSO que no tendrán mínimo. El pocentaje de ganancia que intento aplicar fue del: " +
                getPorcentajeGanancia() + "%";
    }
}
