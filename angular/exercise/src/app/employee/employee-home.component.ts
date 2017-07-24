/* ----------------------------------------------------------------------------------- */
/* Author       : FaerieRose                                                           */
/* Date created : 24 JUL 2017                                                          */
/* ----------------------------------------------------------------------------------- */
import { Component }       from '@angular/core';
import { Router }          from '@angular/router';

import { GlobalService }   from '../global.service';
import { EmployeeService } from './employee.service';
import { Resource }        from '../resource';

@Component({
  selector: 'employee-home',
  templateUrl: './employee-home.component.html',
  styleUrls: [ 'employee.css' ],
  providers: [ EmployeeService ]
})
export class EmployeeHomeComponent {
  constructor(
      private employeeService: EmployeeService,
      private globalService: GlobalService,
      private router: Router
    ) { 
    employeeService.getInitialResources().subscribe(response => {
      console.log(response);
      this.globalService.setResources(response.resources);
      this.isGetAllEmployees = this.globalService.isResourceAvailable("getAllEmployees");
      this.isPostNewEmployee = this.globalService.isResourceAvailable("postNewEmployee");          
    });
  }
  isGetAllEmployees: boolean = false;
  isPostNewEmployee: boolean = false;
  resources: Resource[];

  navEmployeeList() {
    this.router.navigate(['employee/main/list']);
  }
  navEmployeeNew() {
    this.router.navigate(['employee/main/new']);
  }


}
