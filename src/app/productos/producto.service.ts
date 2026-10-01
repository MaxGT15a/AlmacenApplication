import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { ProductoRequest, ProductoResponse, ProductoFiltro } from '../core/models/producto.model';

@Injectable({ providedIn: 'root' })
export class ProductoService {

  private readonly baseUrl = `${environment.apiUrl}/api/productos`;

  constructor(private http: HttpClient) {}

  listar(filtro?: ProductoFiltro): Observable<ProductoResponse[]> {
    let params = new HttpParams();
    if (filtro?.nombre) params = params.set('nombre', filtro.nombre);
    if (filtro?.categoria) params = params.set('categoria', filtro.categoria);
    if (filtro?.precioMin != null) params = params.set('precioMin', filtro.precioMin);
    if (filtro?.precioMax != null) params = params.set('precioMax', filtro.precioMax);

    return this.http.get<ProductoResponse[]>(this.baseUrl, { params });
  }

  obtenerPorId(id: number): Observable<ProductoResponse> {
    return this.http.get<ProductoResponse>(`${this.baseUrl}/${id}`);
  }

  registrar(request: ProductoRequest): Observable<ProductoResponse> {
    return this.http.post<ProductoResponse>(this.baseUrl, request);
  }

  actualizar(id: number, request: ProductoRequest): Observable<ProductoResponse> {
    return this.http.put<ProductoResponse>(`${this.baseUrl}/${id}`, request);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
