package exceptions;

import models.Producto;
import java.util.List;

public class VentaDeProductoException extends RuntimeException{

    private List<Producto> productoList;

    public VentaDeProductoException(List<Producto> productoList) {
        this.productoList = productoList;
    }

    public List<Producto> getProductoList() {
        return productoList;
    }

    @Override
    public String getMessage() {
        return  "La venta del/los producto/s: " + this.getProductoList() + " no es valida (cada venta podrá incluir hasta un máximo de 3 productos de cualquier tipo " +
                "y hasta 12 unidades de cada uno.)";
    }
}
