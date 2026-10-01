package com.max.almacen.repositories;

import com.max.almacen.entities.Producto;
import com.max.almacen.enums.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // JPQL con múltiples condiciones
    @Query("SELECT p FROM Producto p WHERE " +
            "(:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
            "(:categoria IS NULL OR p.categoria = :categoria) AND " +
            "(:precioMin IS NULL OR p.precio >= :precioMin) AND " +
            "(:precioMax IS NULL OR p.precio <= :precioMax)")

    List<Producto> buscarPorFiltro(@Param("nombre") String nombre,
                                   @Param("categoria") Categoria categoria,
                                   @Param("precioMin") BigDecimal precioMin,
                                   @Param("precioMax") BigDecimal precioMax);
}
