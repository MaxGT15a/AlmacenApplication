package com.max.almacen.controllers;

import com.max.almacen.docs.ProblemaDoc;
import com.max.almacen.dto.ventas.VentaRequest;
import com.max.almacen.dto.ventas.VentaResponse;
import com.max.almacen.services.ventas.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@Tag(name ="Ventas", description = "Gestion de ventas")
// Errores que pueden ocurrir en cualquier endpoint:
@ApiResponse(responseCode = "400", description = "Datos o parámetros inválidos",
        content = @Content(mediaType = "application/problem+json",
                schema = @Schema(implementation = ProblemaDoc.class)))
@ApiResponse(responseCode = "500", description = "Error interno del servidor",
        content = @Content(mediaType = "application/problem+json",
                schema = @Schema(implementation = ProblemaDoc.class)))
public class VentaController {
    private final VentaService ventaService;

    @GetMapping
    @Operation(summary = "Listar ventas registradas", description = "Ventas registradas")
    @ApiResponse(responseCode = "200", description = "Listado obtenido")
    public ResponseEntity<List<VentaResponse>> listar(
    ){
        return ResponseEntity.ok(ventaService.listar());
    }

    @GetMapping("/archivadas")
    @Operation(summary = "Listar histórico de ventas canceladas", description = "Ventas canceladas")
    @ApiResponse(responseCode = "200", description = "Listado obtenido")
    public ResponseEntity<List<VentaResponse>> listarArchived(
    ){
        return ResponseEntity.ok(ventaService.listarArchived());
    }

    @PostMapping
    @Operation(
            summary = "Registrar una venta"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Venta creada"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Sucursal o producto no encontrado",
            content = @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(
                            implementation = ProblemaDoc.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "El detalle de venta ya se encuentra registrado",
            content = @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(
                            implementation = ProblemaDoc.class
                    )
            )
    )
    public ResponseEntity<VentaResponse> registrarVenta(
            @Valid @RequestBody VentaRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ventaService.registrar(request));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Cancelar una venta con su id",
            description = "Cambia el estado de una venta registrada a CANCELADA y restaura las cantidades de inventario."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Venta cancelada correctamente"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Venta no encontrada",
            content = @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(
                            implementation = ProblemaDoc.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "Conflicto con los datos",
            content = @Content(
                    mediaType = "application/problem+json",
                    schema = @Schema(
                            implementation = ProblemaDoc.class
                    )
            )
    )
    public ResponseEntity<Void> cancelar(
            @Parameter(description = "Identificador de la venta", example = "1")
            @PathVariable @Positive(message = "El identificador debe ser positivo") Long id
    ) {
        ventaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
