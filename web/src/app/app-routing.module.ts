import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {ChartPageComponent} from './chart-page/chart-page.component';
import {RoomsListComponent} from './rooms-list/rooms-list.component';


export const routes: Routes = [
  { path: '', component: RoomsListComponent},
  { path: 'room/:id', component: ChartPageComponent}
];

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
