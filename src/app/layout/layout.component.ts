import { Component } from '@angular/core';
import { ThemeService } from '../core/services/theme.service';
import { MaterialModule } from '../material/material.module';
import { AppRoutingModule } from '../app-routing.module';

interface ItemMenu {
  label: string;
  icon: string;
  route: string;
  disponible: boolean;
}

@Component({
  selector: 'app-layout',
  standalone: false,
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent {

  sidenavAbierto = true;

  menuItems: ItemMenu[] = [
    { label: 'Dashboard', icon: 'dashboard', route: '/dashboard', disponible: true },
    { label: 'Productos', icon: 'inventory_2', route: '/productos', disponible: true },
    { label: 'Sucursales', icon: 'store', route: '/sucursales', disponible: false },
    { label: 'Ventas', icon: 'point_of_sale', route: '/ventas', disponible: false }
  ];

  constructor(public themeService: ThemeService) {}

  toggleSidenav(): void {
    this.sidenavAbierto = !this.sidenavAbierto;
  }
}