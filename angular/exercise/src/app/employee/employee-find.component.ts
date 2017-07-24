/* ----------------------------------------------------------------------------------- */
/* Author       : FaerieRose                                                           */
/* Date created : 24 JUL 2017                                                          */
/* ----------------------------------------------------------------------------------- */
import { Component }       from '@angular/core';
import { Router }          from '@angular/router';
import { ActivatedRoute }  from '@angular/router';
import { Params         }  from '@angular/router';

import { GlobalService }   from '../global.service';
import { EmployeeService } from './employee.service';
import { Resource }        from '../resource';
import { Employee }        from './employee';

@Component({
  selector: 'employee-find',
  templateUrl: './employee-find.component.html',
  styleUrls: [ 'employee.css' ],
  providers: [ EmployeeService ]
})
export class EmployeeFindComponent {
  constructor(
      private employeeService: EmployeeService,
      private globalService: GlobalService,
      private route: ActivatedRoute,
      private router: Router
    ) { 
    let id = +this.route.snapshot.params['id'];
    let resource: Resource = this.globalService.getResource("getOneEmployeeID");
    this.employeeService.getDatabaseInfo(resource.link+id).subscribe(response => {
      this.globalService.addResources(response.resources);
      this.employee = response.load;
      console.log(this.employee);
      if (this.employee.partner == null) {
        resource = this.globalService.getResource("possiblePartnersEmployee");
        this.employeeService.getDatabaseInfo(resource.link).subscribe(response => {
          this.possiblePartners = response.load;
        });
      }
    });
  }

  private employee: Employee;
  private possiblePartners: Employee[];
  
}
