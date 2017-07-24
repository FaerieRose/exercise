/* ----------------------------------------------------------------------------------- */
/* Author       : FaerieRose                                                           */
/* Date created : 24 JUL 2017                                                          */
/* ----------------------------------------------------------------------------------- */
import { Component }              from '@angular/core';
import { Router }    from '@angular/router';

import { GlobalService }   from '../global.service';
import { EmployeeService } from './employee.service';
import { Employee }        from './employee';
import { Resource }        from '../resource';

@Component({
  selector: 'employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: [ 'employee.css' ],
  providers: [ EmployeeService ]
})
export class EmployeeListComponent {
  constructor(
    private globalService: GlobalService,
    private employeeService  : EmployeeService,
    private router: Router
  ) {
    let resource: Resource = this.globalService.getResource("getAllEmployees");
    this.employeeService.getDatabaseInfo(resource.link).subscribe(response => {
      this.employees = response.load;
      console.log(this.employees);
    });
  }

  private employees: Employee[];
  
  updateEmployee(id) {
    this.router.navigate(['employee/main/update',id.toString()]);
  }
  
}
