package com.max.almacen.mappers;

import com.max.almacen.dto.producto.ProductoRequest;
import com.max.almacen.dto.producto.ProductoResponse;
import com.max.almacen.entities.Producto;
import com.max.almacen.enums.Categoria;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public Producto requestAtEntiedad(ProductoRequest request, Categoria categoria){
        return request == null ? null:
                Producto.crear(
                        request.nombre(),
                        categoria,
                        request.precio(),
                        request.cantidad()
                );
    }

    public ProductoResponse entidadAtResponse(Producto producto){
        return producto == null ? null:
                new ProductoResponse(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getCategoria().getDescripcion(),
                        producto.getPrecio(),
                        producto.getCantidad()
                );
    }
}
