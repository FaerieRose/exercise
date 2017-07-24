/* ----------------------------------------------------------------------------------- */
/* Author       : FaerieRose                                                           */
/* Date created : 24 JUL 2017                                                          */
/* ----------------------------------------------------------------------------------- */
import { Component } from '@angular/core';
import { Router }    from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private router: Router) {}

    navEmployeeList() {
    this.router.navigate(['employee/list']);
  }

    navHome() {
    this.router.navigate(['employee']);
  }
}
