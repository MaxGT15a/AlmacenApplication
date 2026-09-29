package com.max.almacen.utils;

import com.max.almacen.entities.Producto;
import com.max.almacen.enums.Categoria;
import com.max.almacen.repositories.ProductoRepository;
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
    }
}
