export interface ProductoResponse{
    id: number,
    nombre: string,
    categoria: string,
    precio: number,
    cantidad: number
}

export interface ProductoRequest{
    nombre: string,
    categoria: string,
    precio: number,
    cantidad: number
}

export interface ProductoFiltro{
    nombre?: string,
    categoria?: string,
    precioMin?: number,
    precioMax?: number
}