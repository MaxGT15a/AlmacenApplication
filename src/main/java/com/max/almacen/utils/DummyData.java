package com.max.almacen.utils;

import com.max.almacen.entities.DetalleVenta;
import com.max.almacen.entities.Producto;
import com.max.almacen.entities.Sucursal;
import com.max.almacen.entities.Venta;
import com.max.almacen.enums.Categoria;
import com.max.almacen.enums.EstadoVenta;
import com.max.almacen.repositories.DetalleVentaRepository;
import com.max.almacen.repositories.ProductoRepository;
import com.max.almacen.repositories.SucursalRepository;
import com.max.almacen.repositories.VentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DummyData implements CommandLineRunner {

    private final ProductoRepository productoRepository;

    private final SucursalRepository sucursalRepository;

    private final VentaRepository ventaRepository;

    private final DetalleVentaRepository detalleVentaRepository;

    @Override
    public void run(String... args) throws Exception{

        if (productoRepository.count() == 0){

            productoRepository.saveAll(List.of(

                    new Producto(1L,
                            "Laptop",
                            Categoria.ELECTRONICA,
                            BigDecimal.valueOf(1500),
                            10),
                    new Producto(2L,
                            "Mouse",
                            Categoria.ELECTRONICA,
                            BigDecimal.valueOf(25),
                            50),
                    new Producto(3L,
                            "Camisa",
                            Categoria.ROPA,
                            BigDecimal.valueOf(20),
                            100)
            ));
            log.info("Productos de prueba cargados correctamente");
        }

        if (sucursalRepository.count() == 0){

            sucursalRepository.saveAll(List.of(

                    new Sucursal(1L,
                    "Sucursal Sucursal",
                            "Av.Amistad 123"),
                    new Sucursal(2L,
                            "Sucursal Nestlé",
                            "Av.Goku"),
                    new Sucursal(3L,
                            "Sucursal Chedraui",
                            "Av.Shit")
            ));

        }


    }
}
