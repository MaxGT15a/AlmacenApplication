package com.max.almacen.mappers;

import com.max.almacen.dto.sucursales.SucursalResponse;
import com.max.almacen.dto.ventas.DetalleVentaRequest;
import com.max.almacen.dto.ventas.DetalleVentaResponse;
import com.max.almacen.dto.ventas.VentaRequest;
import com.max.almacen.dto.ventas.VentaResponse;
import com.max.almacen.entities.DetalleVenta;
import com.max.almacen.entities.Venta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DetalleVentaMapper {
    public DetalleVenta requestAtEntidad (DetalleVentaRequest request){
        return request == null ? null:
                DetalleVenta.builder().build();
    }

    public DetalleVentaResponse entidadAtResponse(DetalleVenta detalleVenta){
        return detalleVenta == null ? null:
                new DetalleVentaResponse(
                        detalleVenta.getId(),
                        detalleVenta.getProducto().getNombre().trim(),
                        detalleVenta.getCantidadProducto(),
                        detalleVenta.getPrecioProducto(),
                        detalleVenta.getPrecioProducto().multiply(BigDecimal.valueOf(detalleVenta.getCantidadProducto()))
                );
    }
}
