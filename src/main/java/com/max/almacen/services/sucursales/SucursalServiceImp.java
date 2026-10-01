package com.max.almacen.services.sucursales;

import com.max.almacen.dto.sucursales.SucursalRequest;
import com.max.almacen.dto.sucursales.SucursalResponse;
import com.max.almacen.entities.Sucursal;
import com.max.almacen.exceptions.ConflictException;
import com.max.almacen.mappers.SucursalMapper;
import com.max.almacen.repositories.SucursalRepository;
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
public class SucursalServiceImp implements SucursalService{

    private final SucursalRepository sucursalRepository;

    private final SucursalMapper sucursalMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SucursalResponse> listar(String nombre, String direccion) {
        log.info("Listando todas las sucursales");
        return sucursalRepository.findAll().stream()
                .map(sucursalMapper::entidadAtResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SucursalResponse obtenerPorId(Long id) {
        return sucursalMapper.entidadAtResponse(obtenerSucursal0Exception(id));
    }

    @Override
    public SucursalResponse registrar(SucursalRequest request) {
        log.info("Registrando nueva sucursal...");

        validarDatosUnicos(request);

        Sucursal sucursal = sucursalMapper.requestAtEntidad(
                request
        );

        sucursalRepository.save(sucursal);

        log.info("Nueva sucursal {} registrada", sucursal.getNombre());

        return sucursalMapper.entidadAtResponse(sucursal);
    }

    @Override
    public SucursalResponse actualizar(SucursalRequest request, Long id) {

        Sucursal sucursal = obtenerSucursal0Exception(id);

        validarCambiosUnicos(request, id);

        log.info("Actualizando sucursal con id {}", id);

        sucursal.actualizar(
                request.nombre(),
                request.direccion()
        );

        sucursalRepository.saveAndFlush(sucursal);

        log.info("Sucursal con id {} actualizada correctamente", id);

        return sucursalMapper.entidadAtResponse(sucursal);
    }

    @Override
    public void eliminar(Long id) {

        Sucursal sucursal = obtenerSucursal0Exception(id);

        log.info("Eliminando sucursal con id {}", id);

        sucursalRepository.delete(sucursal);
        sucursalRepository.flush();

        log.info("Sucursal con id {} eliminada correctamente", id);
    }

    @Override
    public List<SucursalResponse> listar(String nombre, String categoria, BigDecimal precioMin, BigDecimal precioMax) {
        return List.of();
    }
    private Sucursal obtenerSucursal0Exception(Long id){
        log.info("Buscando sucursal con id {}", id);

        return sucursalRepository.findById(id).orElseThrow(
                () -> new RuntimeException(
                        "Sucursal no encontrada con id: " + id
                ));
    }

    private void validarDatosUnicos(SucursalRequest request){

        log.info("Validando nombre único...");

        if (sucursalRepository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw new ConflictException("Ya existe una sucursal con el nombre de: " + request.nombre());
    }

    private void validarCambiosUnicos(SucursalRequest request, Long id){

        log.info("Validando cambio en nombre único...");

        if (sucursalRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(), id))
            throw new ConflictException("Ya existe una sucursal con el nombre de: " + request.nombre());
    }
}
