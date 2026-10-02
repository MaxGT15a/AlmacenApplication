package com.max.almacen.dto.ventas;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Detalle de un producto dentro de una venta")
public record DetalleVentaResponse(

        @Schema(description = "ID del producto", example = "1")
        Long idProducto,

        @Schema(description = "Nombre del producto", example = "Laptop")
        String nombreProducto,

        @Schema(description = "Cantidad del producto", example = "10")
        Integer cantidadProducto,

        @Schema(description = "Precio del producto", example = "15999")
        BigDecimal precioProducto,

        @Schema(description = "Subtotal del producto", example = "20000")
        BigDecimal subtotal
) { }
