package com.max.almacen.entities;

import com.max.almacen.enums.Categoria;
import com.max.almacen.exceptions.InvalidDataException;
import com.max.almacen.utils.NumberCustomUtils;
import com.max.almacen.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Entity
@Table(name ="PRODUCTOS")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCTO")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 30)
    private String nombre;

    @Column(name = "CATEGORIA", nullable = false)
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Column(name = "PRECIO", nullable = false)
    private BigDecimal precio;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    private static void validarDatos(String nombre, Categoria categoria,
                              BigDecimal precio, Integer cantidad) {

        StringCustomUtils.validateSize(nombre, 5, 30,
                "El nombre es requerido y debe tener entre 5 y 30 carácteres");
        if (categoria == null)
            throw new InvalidDataException("La categoría es requerida");

        NumberCustomUtils.PositiveBigDecimal(precio,
                "El precio es requerido y debe ser positivo");
        NumberCustomUtils.PositiveInt(cantidad,
                "La cantidad es requerida y debe ser positiva");
    }

    public void actualizar(String nombre, Categoria categoria,
                              BigDecimal precio, Integer cantidad) {

        validarDatos(nombre, categoria, precio, cantidad);

        this.nombre = nombre.trim();
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public void aumentarCantidad(int cantidad){
        NumberCustomUtils.PositiveInt(cantidad, "La cantidad debe ser positiva");
        this.cantidad += cantidad;
    }

    public void descontarCantidad(int cantidad){
        NumberCustomUtils.PositiveInt(cantidad, "La cantidad debe ser positiva");
        if(cantidad > this.cantidad)
            throw new InvalidDataException("la cantidad debe ser menor " +
                    "o igual a la cantidad actual");
        this.cantidad -= cantidad;
    }

    public static Producto crear(String nombre, Categoria categoria,
                                  BigDecimal precio, Integer cantidad){
        validarDatos(nombre, categoria, precio, cantidad);

        return Producto.builder()
                .nombre(nombre.trim())
                .categoria(categoria)
                .precio(precio)
                .cantidad(cantidad)
                .build();
    }
}
