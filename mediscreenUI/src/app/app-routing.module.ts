import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { PersonCreationComponent } from './person-creation/person-creation.component';
import { PersonDetailsComponent } from './person-details/person-details.component';
import { PersonEditionComponent } from './person-edition/person-edition.component';
import { PersonListComponent } from './person-list/person-list.component';
import { PersonUpdateComponent } from './person-update/person-update.component';

const routes: Routes = [
  { path: 'persons', component: PersonListComponent},
  { path: 'person/create', component: PersonCreationComponent},
  { path: 'person/edit/:id', component: PersonUpdateComponent},
  { path: 'person/:id', component: PersonDetailsComponent},
  { path: 'note/edit/:id', component: PersonDetailsComponent},
  { path: '', component: HomeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
