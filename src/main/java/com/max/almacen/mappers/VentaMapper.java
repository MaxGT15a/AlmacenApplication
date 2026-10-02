package com.max.almacen.mappers;

import com.max.almacen.dto.sucursales.SucursalResponse;
import com.max.almacen.dto.ventas.DetalleVentaResponse;
import com.max.almacen.dto.ventas.VentaRequest;
import com.max.almacen.dto.ventas.VentaResponse;
import com.max.almacen.entities.Venta;
import com.max.almacen.services.detalles.DetalleVentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class VentaMapper {
    private final DetalleVentaService detalleVentaService;

    public Venta requestAtEntidad (VentaRequest request){
        return request == null ? null:
                Venta.builder().build();
    }

    public VentaResponse entidadAtResponse(Venta venta){
        List<DetalleVentaResponse> detalleVentaResponses = detalleVentaService.obtenerPorVentaId(venta.getId());

        BigDecimal total = (detalleVentaResponses == null || detalleVentaResponses.isEmpty())
                ? BigDecimal.ZERO
                : detalleVentaResponses.stream()
                .map(DetalleVentaResponse::subtotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return venta == null ? null:
                new VentaResponse(
                        venta.getId(),
                        venta.getFecha().toString().trim(),
                        venta.getEstadoVenta().toString().trim(),
                        new SucursalResponse(
                                venta.getSucursal().getId(),
                                venta.getSucursal().getNombre(),
                                venta.getSucursal().getDireccion()
                        ),
                       detalleVentaResponses,
                        total
                );
    }
}
