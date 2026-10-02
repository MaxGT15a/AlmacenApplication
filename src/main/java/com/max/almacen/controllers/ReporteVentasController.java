package com.max.almacen.controllers;

import com.max.almacen.docs.ProblemaDoc;
import com.max.almacen.dto.ventas.ReporteVentasResponse;
import com.max.almacen.services.reportes.ReporteVentasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reportes-ventas")
@RequiredArgsConstructor
@Tag(name = "Reportes Ventas Sucursales", description = "Gestion de reportes de ventas en sucursales")
@ApiResponse(
        responseCode = "400",
        description = "Datos o parametros invalidos",
        content = @Content(
                mediaType = "application/problem+json",
                schema = @Schema(
                        implementation = ProblemaDoc.class
                )
        )
)
@ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor",
        content = @Content(
                mediaType = "application/problem+json",
                schema = @Schema(
                        implementation = ProblemaDoc.class
                )
        )
)
public class ReporteVentasController {
    private final ReporteVentasService reporteVentasService;

    @GetMapping
    @Operation(
            summary = "Listar reporte de ventas de cada sucursal"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Reportes listados"
    )
    public ResponseEntity<List<ReporteVentasResponse>> listarReporteVentas() {
        return ResponseEntity.ok(reporteVentasService.listarReporteVentas());
    }
}
