import { Component, OnInit } from '@angular/core';
import { ProductoService } from '../productos/producto.service';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  totalProductos = 0;
  cargando = true;

  constructor(private productoService: ProductoService) {}

  ngOnInit(): void {
    this.productoService.listar().subscribe({
      next: (data) => { this.totalProductos = data.length; this.cargando = false; },
      error: () => { this.cargando = false; }
    });
  }
}