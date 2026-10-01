package com.max.almacen.enums;

import com.max.almacen.exceptions.InvalidDataException;
import com.max.almacen.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Categoria {

    ALIMENTO("Alimento"),
    HIGIENE("Higiene"),
    JUGUETE("Juguete"),
    ELECTRONICA("Electrónica"),
    ROPA("Ropa"),
    ACCESORIO("Accesorio"),
    FARMACIA("Farmacia");

    private final String descripcion;

    public static Categoria obtenerCategoriaPorDescripcion(String descripcion){
        StringCustomUtils.nonEmpty(descripcion, "La descripción es requerida");
        String descripcionNormalizada = StringCustomUtils.normalizeText(descripcion);

        for (Categoria categoria : values()){
            if(StringCustomUtils.normalizeText(categoria.descripcion).equalsIgnoreCase(descripcionNormalizada))
                return categoria;
        }

        throw new InvalidDataException("No existe una categoría con la descripcion: " + descripcion);
    }
    public static Categoria obtenerNullableCategoria (String descripcion){
        if (StringCustomUtils.isEmpty(descripcion))
            return null;

        return Categoria.obtenerCategoriaPorDescripcion(descripcion);
    }
}
