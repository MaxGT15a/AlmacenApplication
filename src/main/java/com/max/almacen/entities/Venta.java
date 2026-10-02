package com.max.almacen.entities;

import com.max.almacen.enums.EstadoVenta;
import com.max.almacen.exceptions.ConflictException;
import com.max.almacen.exceptions.InvalidDataException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "VENTAS")
@NoArgsConstructor
@AllArgsConstructor
@Builder @Getter
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VENTA")
    private Long id;

    @Column(name = "ESTADO", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoVenta estadoVenta;

    @Column(name = "FECHA", nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUCURSAL")
    private Sucursal sucursal;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalleVentas = new ArrayList<>();

    public void agregarDetalle(DetalleVenta detalleVenta){
        if (detalleVenta == null)
            throw new InvalidDataException("El detalle de venta es requerido");

        this.detalleVentas.add(detalleVenta);
        detalleVenta.asignarVenta(this);
    }

    public static Venta crear(Sucursal sucursal) {
        if(sucursal == null)
            throw new InvalidDataException(
                    "La sucursal asociada a la venta es obligatoria"
            );

        return Venta.builder()
                .estadoVenta(EstadoVenta.REGISTRADA)
                .fecha(LocalDate.now())
                .sucursal(sucursal)
                .build();
    }

    public void cancelar(){
        if (this.estadoVenta == EstadoVenta.CANCELADA)
            throw new ConflictException(
                    "La venta ya se encuentra cancelada"
            );
        this.detalleVentas.forEach(d->
                d.getProducto().aumentarCantidad(d.getCantidadProducto())
        );
        this.estadoVenta = EstadoVenta.CANCELADA;
    }

    public BigDecimal obtenerTotalVenta(){
        return this.detalleVentas.stream()
                .map(d ->
                        d.getPrecioProducto().multiply(
                                BigDecimal.valueOf(d.getCantidadProducto())
                        )
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
