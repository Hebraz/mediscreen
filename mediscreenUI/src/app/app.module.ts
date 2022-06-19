import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PersonComponent } from './person/person.component';
import { PersonListComponent } from './person-list/person-list.component';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './home/home.component';
import { PersonEditionComponent } from './person-edition/person-edition.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    PersonComponent,
    PersonListComponent,
    HeaderComponent,
    HomeComponent,
    PersonEditionComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
