export const CATEGORIAS = [
    'Alimento',
    'Higiene',
    'Juguete',
    'Electrónica',
    'Ropa',
    'Accesorio',
    'Farmacia'
] as const;

export type Categoria = typeof CATEGORIAS[number];