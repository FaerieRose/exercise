/* ----------------------------------------------------------------------------------- */
/* Author       : FaerieRose                                                           */
/* Date created : 24 JUL 2017                                                          */
/* ----------------------------------------------------------------------------------- */
import { NgModule }              from '@angular/core';
import { CommonModule }          from '@angular/common';

import { EmployeeComponent }     from './employee.component';
import { EmployeeNewComponent }  from './employee-new.component';
import { EmployeeHomeComponent } from './employee-home.component';
import { EmployeeFindComponent } from './employee-find.component';
import { EmployeeListComponent } from './employee-list.component';

import { EmployeeRoutingModule } from './employee-routing.module';

@NgModule({
  imports: [ 
    CommonModule, 
    EmployeeRoutingModule
  ],
  declarations: [
    EmployeeComponent, 
    EmployeeNewComponent,
    EmployeeFindComponent,
    EmployeeHomeComponent,
    EmployeeListComponent
  ]
})
export class EmployeeModule { }
