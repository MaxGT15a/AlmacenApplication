package com.max.almacen.services.ventas;

import com.max.almacen.dto.ventas.VentaRequest;
import com.max.almacen.dto.ventas.VentaResponse;

import java.util.List;

public interface VentaService {
    List<VentaResponse> listar();

    VentaResponse obtenerPorIdActiva(Long id);

    VentaResponse registrar(VentaRequest request);

    VentaResponse cancelar(Long id);
}
