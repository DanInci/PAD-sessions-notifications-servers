import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MDBBootstrapModule } from 'angular-bootstrap-md';

import { AppComponent } from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {ChartServiceService} from './service/chart-service.service';
import {AppRoutingModule, routes} from './app-routing.module';
import { ChartPageComponent } from './chart-page/chart-page.component';
import {RouterModule} from '@angular/router';
import { RoomsListComponent } from './rooms-list/rooms-list.component';
import { BadgeComponent } from './badge/badge.component';

@NgModule({
  declarations: [
    AppComponent,
    ChartPageComponent,
    RoomsListComponent,
    BadgeComponent,

  ],
  imports: [
    BrowserModule,
    MDBBootstrapModule.forRoot(),
    HttpClientModule,
    AppRoutingModule,
    RouterModule.forRoot(routes),
  ],
  providers: [ChartServiceService],
  bootstrap: [AppComponent]
})
export class AppModule { }
