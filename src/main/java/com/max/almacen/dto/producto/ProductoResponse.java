package com.max.almacen.dto.producto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Información del producto")
public record ProductoResponse(

        @Schema(description = "ID único del producto", example = "1")
        Long Id,

        @Schema(description = "Nombre del producto", example = "Laptop")
        String nombre,

        @Schema(description = "Categoría del producto", example = "Electrónica")
        String categoria,

        @Schema(description = "Precio del producto", example = "15999.99")
        BigDecimal precio,

        @Schema(description = "Cantidad disponible del producto", example = "300")
        Integer cantidad
) {
}
