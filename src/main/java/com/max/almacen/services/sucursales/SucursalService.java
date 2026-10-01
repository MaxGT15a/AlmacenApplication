package com.max.almacen.services.sucursales;

import com.max.almacen.dto.sucursales.SucursalRequest;
import com.max.almacen.dto.sucursales.SucursalResponse;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface SucursalService {

    List<SucursalResponse>listar(String nombre, String direccion);

    SucursalResponse obtenerPorId(Long id);

    SucursalResponse registrar(SucursalRequest request);

    SucursalResponse actualizar(SucursalRequest request, Long id);

    void eliminar(Long id);

    @Transactional(readOnly = true)
    List<SucursalResponse> listar(String nombre, String categoria, BigDecimal precioMin, BigDecimal precioMax);
}
