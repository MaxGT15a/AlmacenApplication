import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

import { ProductoService } from '../producto.service';
import { ProductoResponse } from '../../core/models/producto.model';
import { CATEGORIAS } from '../../core/models/categoria.model';

@Component({
  selector: 'app-producto-form',
  standalone: false,
  templateUrl: './producto-form.component.html',
  styleUrls: ['./producto-form.component.css']
})
export class ProductoFormComponent implements OnInit {

  categorias = CATEGORIAS;
  guardando = false;
  esEdicion = false;
  
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private productoService: ProductoService,
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<ProductoFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ProductoResponse | null
  ) {
    this.form = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(30)]],
      categoria: ['', Validators.required],
      precio: [null, [Validators.required, Validators.min(0.01)]],
      cantidad: [null, [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit(): void {
    if (this.data) {
      this.esEdicion = true;
      this.form.patchValue(this.data);
    }
  }

  guardar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.guardando = true;
    const obs = this.esEdicion
      ? this.productoService.actualizar(this.data!.id, this.form.value)
      : this.productoService.registrar(this.form.value);

    obs.subscribe({
      next: () => {
        this.guardando = false;
        this.dialogRef.close(true);
        this.mostrarMensaje(`Producto: ${this.form.value.nombre} ${this.esEdicion
          ? ' actualizado' : ' registrado'}`);
      },
      error: (err) => {
        this.guardando = false;
        this.mostrarMensaje(err?.error?.message ?? 'Error al guardar el producto');
      }
    });
  }

  cancelar(): void {
    this.dialogRef.close(false);
  }
  
  private mostrarMensaje(mensaje: string): void {
    this.snackBar.open(mensaje, 'Cerrar', { duration: 3000 });
  }
}