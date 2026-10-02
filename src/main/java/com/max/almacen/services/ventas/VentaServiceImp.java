package com.max.almacen.services.ventas;

import com.max.almacen.dto.ventas.VentaRequest;
import com.max.almacen.dto.ventas.VentaResponse;
import com.max.almacen.entities.DetalleVenta;
import com.max.almacen.entities.Producto;
import com.max.almacen.entities.Sucursal;
import com.max.almacen.entities.Venta;
import com.max.almacen.enums.Categoria;
import com.max.almacen.enums.EstadoVenta;
import com.max.almacen.exceptions.NoSuchResourceException;
import com.max.almacen.mappers.VentaMapper;
import com.max.almacen.repositories.ProductoRepository;
import com.max.almacen.repositories.SucursalRepository;
import com.max.almacen.repositories.VentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VentaServiceImp implements VentaService{

    private final VentaRepository ventaRepository;

    private final ProductoRepository productoRepository;

    private final SucursalRepository sucursalRepository;

    private final VentaMapper ventaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> listar() {
        log.info("Listando todas las ventas registradas...");
        return ventaRepository.findByEstadoVenta(EstadoVenta.obtenerEstadoDeVentaPorCodigo(1L))
                .stream().map(ventaMapper::entidadAtResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<VentaResponse> listarArchived() {
        log.info("Listando todas las ventas canceladas...");
        return ventaRepository.findByEstadoVenta(EstadoVenta.obtenerEstadoDeVentaPorCodigo(0L))
                .stream().map(ventaMapper::entidadAtResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<VentaResponse> listarAll() {
        log.info("Listando todas las ventas...");
        return ventaRepository.findAll()
                .stream().map(ventaMapper::entidadAtResponse).toList();
    }

    @Override
    public VentaResponse obtenerPorIdActiva(Long id) {
        return null;
    }

    @Transactional
    @Override
    public VentaResponse registrar(VentaRequest request) {
        Sucursal sucursal = obtenerSucursalOException(request.idSucursal());
        log.info("Registrando venta...");

        Venta venta = Venta.crear(
                sucursal
        );
        request.productos().forEach(p-> {
            Producto producto = obtenerProductoOException(p.idProducto());
            DetalleVenta detalleVenta = DetalleVenta.crear(
                    producto,
                    p.cantidadProducto()
            );
            venta.agregarDetalle(detalleVenta);
        });

        ventaRepository.save(venta);

        return ventaMapper.entidadAtResponse(venta);
    }

    private Producto obtenerProductoOException(Long id) {
        log.info("Obteniendo producto con id: {}", id);
        return productoRepository.findById(id)
                .orElseThrow(() -> new NoSuchResourceException("Producto no encontrado con id: " + id));
    }

    private Sucursal obtenerSucursalOException(Long id){
        log.info("Obteniendo sucursal con id: {}", id);
        return sucursalRepository.findById(id)
                .orElseThrow(()-> new NoSuchResourceException("Sucursal no encontrada con id: " + id));
    }

    private Venta obtenerVentaOException(Long id){
        log.info("Obteniendo venta por id: {}", id);
        return ventaRepository.findById(id)
                .orElseThrow(() -> new NoSuchResourceException("Venta no encontrada con id: " + id));
    }

    @Transactional
    @Override
    public VentaResponse cancelar(Long id) {
        Venta venta = obtenerVentaOException(id);

        log.info("Cancelando venta con id: {}", id);

        venta.cancelar();

        return ventaMapper.entidadAtResponse(venta);
    }
}
