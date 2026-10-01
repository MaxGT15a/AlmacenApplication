import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

import { ProductoService } from '../producto.service';
import { ProductoResponse } from '../../core/models/producto.model';
import { ProductoFormComponent } from '../producto-form/producto-form.component';
import { CATEGORIAS } from '../../core/models/categoria.model';

@Component({
  standalone: false,
  selector: 'app-producto-list',
  templateUrl: './producto-list.component.html',
  styleUrls: ['./producto-list.component.css']
})
export class ProductoListComponent implements OnInit {

  columnas = ['id', 'nombre', 'categoria', 'precio', 'cantidad', 'acciones'];
  productos: ProductoResponse[] = [];
  categorias = CATEGORIAS;
  cargando = false;

  filtroForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private productoService: ProductoService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {
    this.filtroForm = this.fb.group({
      nombre: [''],
      categoria: [''],
      precioMin: [null],
      precioMax: [null]
    });
  }

  ngOnInit(): void {
    this.buscar();
  }

  buscar(): void {
    this.cargando = true;
    this.productoService.listar(this.filtroForm.value).subscribe({
      next: (data) => { this.productos = data; this.cargando = false; },
      error: () => { this.mostrarMensaje('Error al cargar los productos'); this.cargando = false; }
    });
  }

  limpiarFiltros(): void {
    this.filtroForm.reset();
    this.buscar();
  }

  abrirFormulario(producto?: ProductoResponse): void {
    const ref = this.dialog.open(ProductoFormComponent, {
      width: '450px',
      data: producto ?? null
    });
    ref.afterClosed().subscribe((resultado) => { if (resultado) this.buscar(); });
  }

  eliminar(producto: ProductoResponse): void {
    if (!confirm(`¿Eliminar el producto "${producto.nombre}"?`)) return;
    this.productoService.eliminar(producto.id).subscribe({
      next: () => { this.mostrarMensaje('Producto eliminado'); this.buscar(); },
      error: () => this.mostrarMensaje('Error al eliminar el producto')
    });
  }

  private mostrarMensaje(mensaje: string): void {
    this.snackBar.open(mensaje, 'Cerrar', { duration: 3000 });
  }
}