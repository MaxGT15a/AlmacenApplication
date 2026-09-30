package com.max.almacen.enums;

import com.max.almacen.exceptions.InvalidDataException;
import com.max.almacen.utils.NumberCustomUtils;
import com.max.almacen.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EstadoVenta {

    REGISTRADA(1L, "Registrada"),
    CANCELADA(0L, "Cancelada");

    private final Long codigo;

    private final String descripcion;

    public static EstadoVenta obtenerEstadoDeVentaPorDescripcion(String descripcion){
        StringCustomUtils.nonEmpty(descripcion, "La descripción es requerida");
        String descripcionNormalizada = StringCustomUtils.normalizeText(descripcion);

        for (EstadoVenta estadoVenta : values()){
            if(StringCustomUtils.normalizeText(estadoVenta.descripcion).equalsIgnoreCase(descripcionNormalizada))
                return estadoVenta;
        }

        throw new InvalidDataException("No existe un estado de venta con la descripcion: " + descripcion);
    }

    public static EstadoVenta obtenerEstadoDeVentaPorCodigo(Long codigo){

        NumberCustomUtils.nonNullNum(codigo,"El código es requerido");

        for (EstadoVenta estadoVenta : values()){
            if(estadoVenta.codigo.equals(codigo))
                return estadoVenta;
        }

        throw new InvalidDataException("No existe un estado de venta con el código: " + codigo);
    }
}
