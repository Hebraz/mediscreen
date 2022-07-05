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
import { NoteComponent } from './note/note.component';
import { PersonDetailsComponent } from './person-details/person-details.component';
import { NoteEditionComponent } from './note-edition/note-edition.component';
import { PersonCreationComponent } from './person-creation/person-creation.component';
import { PersonUpdateComponent } from './person-update/person-update.component';

@NgModule({
  declarations: [
    AppComponent,
    PersonComponent,
    PersonListComponent,
    HeaderComponent,
    HomeComponent,
    PersonEditionComponent,
    NoteComponent,
    PersonDetailsComponent,
    NoteEditionComponent,
    PersonCreationComponent,
    PersonUpdateComponent
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
