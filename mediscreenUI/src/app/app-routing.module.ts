import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { PersonEditionComponent } from './person-edition/person-edition.component';
import { PersonListComponent } from './person-list/person-list.component';

const routes: Routes = [
  { path: 'persons', component: PersonListComponent},
  { path: 'person', component: PersonEditionComponent},
  { path: '', component: HomeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
