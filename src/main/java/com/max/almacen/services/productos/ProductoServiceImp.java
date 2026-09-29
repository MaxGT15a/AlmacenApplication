package com.max.almacen.services.productos;

import com.max.almacen.dto.producto.ProductoRequest;
import com.max.almacen.dto.producto.ProductoResponse;
import com.max.almacen.entities.Producto;
import com.max.almacen.enums.Categoria;
import com.max.almacen.mappers.ProductoMapper;
import com.max.almacen.repositories.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductoServiceImp implements ProductoService{

    private final ProductoRepository productoRepository;

    private final ProductoMapper productoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listar(String nombre, String categoria, BigDecimal precioMin, BigDecimal precioMax) {
        log.info("Listando todos los productos");
        return productoRepository.findAll().stream()
                //.map(producto -> productoMapper.entidadAtResponse(producto)).toList()
                .map(productoMapper::entidadAtResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponse obtenerPorId(Long id) {

        return productoMapper.entidadAtResponse(obtenerProducto0Exception(id));
    }

    @Override
    public ProductoResponse registrar(ProductoRequest request) {
        log.info("Registrando nuevo producto...");

        Producto producto = productoMapper.requestAtEntiedad(
                request,
                Categoria.obtenerCategoriaPorDescripcion(request.categoria().trim())
        );

        productoRepository.save(producto);

        log.info("Nuevo producto {} registrado", producto.getNombre());

        return productoMapper.entidadAtResponse(producto);
    }

    @Override
    public ProductoResponse actualizar(ProductoRequest request, Long id) {

        Producto producto = obtenerProducto0Exception(id);

        log.info("Actualizando producto con id {}", id);

        producto.actualizar(
                request.nombre(),
                Categoria.obtenerCategoriaPorDescripcion(
                        request.categoria().trim()
                ),
                request.precio(),
                request.cantidad()
        );

        productoRepository.saveAndFlush(producto);

        log.info("Producto con id {} actualizado correctamente", id);

        return productoMapper.entidadAtResponse(producto);
    }

    @Override
    public void eliminar(Long id) {

        Producto producto = obtenerProducto0Exception(id);

        log.info("Eliminando producto con id {}", id);

        productoRepository.delete(producto);
        productoRepository.flush();

        log.info("Producto con id {} eliminado correctamente", id);

    }

    private Producto obtenerProducto0Exception(Long id){
        log.info("Buscando producto con id {}", id);

        return productoRepository.findById(id).orElseThrow(
                () -> new RuntimeException(
                        "Producto no encontrado con id: " + id
                ));
    }
}
