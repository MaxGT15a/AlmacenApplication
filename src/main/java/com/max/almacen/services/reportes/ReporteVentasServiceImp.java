package com.max.almacen.services.reportes;

import com.max.almacen.dto.ventas.ReporteVentasResponse;
import com.max.almacen.repositories.ReporteVentasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReporteVentasServiceImp implements ReporteVentasService{
    private final ReporteVentasRepository reporteVentasRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReporteVentasResponse> listarReporteVentas() {

        log.info("Listando reportes de ventas de sucursales...");

        return reporteVentasRepository.obtenerReportes();
    }
}
