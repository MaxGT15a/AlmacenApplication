package com.max.almacen.services.reportes;

import com.max.almacen.dto.ventas.ReporteVentasResponse;

import java.util.List;

public interface ReporteVentasService {
    List<ReporteVentasResponse> listarReporteVentas();
}
