/* ----------------------------------------------------------------------------------- */
/* Author       : FaerieRose                                                           */
/* Date created : 24 JUL 2017                                                          */
/* ----------------------------------------------------------------------------------- */
import { NgModule }     from '@angular/core';
import { Routes,
         RouterModule } from '@angular/router';

import { EmployeeComponent }     from './employee.component';
import { EmployeeNewComponent }  from './employee-new.component';
import { EmployeeHomeComponent } from './employee-home.component';
import { EmployeeFindComponent } from './employee-find.component';
import { EmployeeListComponent } from './employee-list.component';

const routes: Routes = [
  { path: '', redirectTo: 'main', pathMatch: 'full'},
  { path: 'main', component: EmployeeComponent,
    children: [
      { path: '', component: EmployeeHomeComponent },
      { path: 'new', component: EmployeeNewComponent },
      { path: 'list', component: EmployeeListComponent },
      { path: 'update/:id', component: EmployeeFindComponent }
    ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmployeeRoutingModule {}
