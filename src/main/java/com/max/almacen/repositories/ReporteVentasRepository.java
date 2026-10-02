package com.max.almacen.repositories;

import com.max.almacen.dto.ventas.ReporteVentasResponse;
import com.max.almacen.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteVentasRepository extends JpaRepository<Venta, Long> {

    @Query(value = """
        SELECT new com.max.almacen.dto.ventas.ReporteVentasResponse(
                v.sucursal.id,
                v.sucursal.nombre,
                SUM(d.cantidadProducto * d.precioProducto),
                SUM(d.cantidadProducto)
            )
        FROM Venta v
        JOIN v.detalleVentas d
        WHERE v.estadoVenta = com.max.almacen.enums.EstadoVenta.REGISTRADA
        GROUP BY v.sucursal.id, v.sucursal.nombre
    """)
    List<ReporteVentasResponse> obtenerReportes();

}
