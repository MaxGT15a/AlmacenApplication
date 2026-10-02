package com.max.almacen.services.detalles;

import com.max.almacen.dto.ventas.DetalleVentaRequest;
import com.max.almacen.dto.ventas.DetalleVentaResponse;
import com.max.almacen.mappers.DetalleVentaMapper;
import com.max.almacen.repositories.DetalleVentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DetalleVentaServiceImp implements DetalleVentaService{

    private final DetalleVentaRepository detalleVentaRepository;

    private final DetalleVentaMapper detalleVentaMapper;

    @Override
    public List<DetalleVentaResponse> listar() {
        log.info("Listando todos los detalles de venta");
        return detalleVentaRepository.findAll().stream()
                .map(detalleVentaMapper::entidadAtResponse).toList();
    }

    @Override
    public List<DetalleVentaResponse> obtenerPorVentaId(Long id) {
        log.info("Listando los detalles de la venta con ID: " + id);
        return detalleVentaRepository.findByVentaId(id).stream()
                .map(detalleVentaMapper::entidadAtResponse).toList();
    }

    public List<DetalleVentaResponse> obtenerPorId(Long id) {
        log.info("Listando los detalles con ID: " + id);
        return detalleVentaRepository.findById(id).stream()
                .map(detalleVentaMapper::entidadAtResponse).toList();
    }

    @Override
    public DetalleVentaResponse registrar(DetalleVentaRequest request) {
        return null;
    }
}
