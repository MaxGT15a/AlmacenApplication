package com.max.almacen.utils;

import com.max.almacen.entities.Producto;
import com.max.almacen.entities.Sucursal;
import com.max.almacen.enums.Categoria;
import com.max.almacen.repositories.ProductoRepository;
import com.max.almacen.repositories.SucursalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DummyData implements CommandLineRunner {

    private final ProductoRepository productoRepository;

    private final SucursalRepository sucursalRepository;

    @Override
    public void run(String... args) throws Exception{

        if (productoRepository.count() == 0){

            productoRepository.saveAll(List.of(

                    new Producto(null,
                            "Laptop",
                            Categoria.ELECTRONICA,
                            BigDecimal.valueOf(1500),
                            10),
                    new Producto(null,
                            "Mouse",
                            Categoria.ELECTRONICA,
                            BigDecimal.valueOf(25),
                            50),
                    new Producto(null,
                            "Camisa",
                            Categoria.ROPA,
                            BigDecimal.valueOf(20),
                            100)
            ));
            log.info("Productos de prueba cargados correctamente");
        }

        if (sucursalRepository.count() == 0){

            sucursalRepository.saveAll(List.of(

                    new Sucursal(null,
                    "Sucursal Sucursal",
                            "Av.Amistad 123"),
                    new Sucursal(null,
                            "Sucursal Nestlé",
                            "Av.Goku"),
                    new Sucursal(null,
                            "Sucursal Chedraui",
                            "Av.Shit")
            ));

        }
    }
}
