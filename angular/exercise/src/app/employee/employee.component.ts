/* ----------------------------------------------------------------------------------- */
/* Author       : FaerieRose                                                           */
/* Date created : 24 JUL 2017                                                          */
/* ----------------------------------------------------------------------------------- */
import { Component } from '@angular/core';
import { Router }    from '@angular/router';

import { EmployeeService } from './employee.service';

@Component({
  selector: 'employee-root',
  templateUrl: './employee.component.html',
  styleUrls: [ 'employee.css' ],
  providers: [ EmployeeService ]
})
export class EmployeeComponent {
  constructor(private employeeService: EmployeeService, private router: Router) { 
  }

}
