package exceptions;

import models.Producto;

public class ProductoNoAgregadoException extends RuntimeException {

    Producto producto;

    public ProductoNoAgregadoException(Producto producto) {
        this.producto = producto;
    }

    public Producto getProducto() {
        return producto;
    }

    @Override
    public String getMessage() {
        return "Error, no se pudo agregar el producto: " + getProducto();
    }

}
