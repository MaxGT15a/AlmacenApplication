package com.max.almacen.mappers;

import com.max.almacen.dto.sucursales.SucursalRequest;
import com.max.almacen.dto.sucursales.SucursalResponse;
import com.max.almacen.entities.Sucursal;
import org.springframework.stereotype.Component;

@Component
public class SucursalMapper {

    public Sucursal requestAtEntidad(SucursalRequest request){
        return request == null ? null :
                Sucursal.crear(
                        request.nombre(),
                        request.direccion()

                );
    }

    public SucursalResponse entidadAtResponse(Sucursal sucursal){
        return sucursal == null ? null:
                new SucursalResponse(
                        sucursal.getId(),
                        sucursal.getNombre(),
                        sucursal.getDireccion()
                );
    }

}
