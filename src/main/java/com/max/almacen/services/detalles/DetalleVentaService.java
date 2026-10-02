package com.max.almacen.services.detalles;

import com.max.almacen.dto.ventas.DetalleVentaRequest;
import com.max.almacen.dto.ventas.DetalleVentaResponse;

import java.math.BigDecimal;
import java.util.List;

public interface DetalleVentaService {

    List<DetalleVentaResponse> listar();

    List<DetalleVentaResponse> obtenerPorVentaId(Long id);

    DetalleVentaResponse registrar(DetalleVentaRequest request);

}
