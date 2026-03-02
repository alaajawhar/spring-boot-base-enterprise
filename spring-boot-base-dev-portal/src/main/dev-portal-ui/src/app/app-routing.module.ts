import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ApiListComponent} from "./screens/api-list/api-list.component";
import {ApiDetailsComponent} from "./screens/api-list/api-details/api-details.component";

const routes: Routes = [
  { path: '', component: ApiListComponent },
  { path: 'api-list', component: ApiListComponent },
  { path: 'api-details/:id', component: ApiDetailsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
