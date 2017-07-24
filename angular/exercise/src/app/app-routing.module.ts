/* ----------------------------------------------------------------------------------- */
/* Author       : FaerieRose                                                           */
/* Date created : 24 JUL 2017                                                          */
/* ----------------------------------------------------------------------------------- */
import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import './rxjs-extensions';

const routes: Routes = [
  { path: '', redirectTo: '/employee', pathMatch: 'full' },
  { path: 'employee', loadChildren: 'app/employee/employee.module#EmployeeModule' }  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
