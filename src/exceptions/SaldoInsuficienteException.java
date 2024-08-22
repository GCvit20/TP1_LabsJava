package exceptions;

import java.math.BigDecimal;

public class SaldoInsuficienteException extends RuntimeException {

    private BigDecimal saldoCaja;
    private BigDecimal totalProducto;

    public SaldoInsuficienteException(BigDecimal saldoCaja, BigDecimal totalProducto) {
        this.totalProducto = totalProducto;
        this.saldoCaja = saldoCaja;
    }

    public BigDecimal getSaldoCaja() {
        return saldoCaja;
    }

    public BigDecimal getTotalProducto() {
        return totalProducto;
    }

    @Override
    public String getMessage() {
        return  "El producto no podrá ser agregado a la tienda por saldo insuficiente en la caja " +
             "(tu saldo actual es de: " + this.getSaldoCaja() + " para realizar la operacion de: "
                + this.getTotalProducto() +").";
    }

}
