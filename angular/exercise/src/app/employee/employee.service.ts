/* ----------------------------------------------------------------------------------- */
/* Author       : FaerieRose                                                           */
/* Date created : 24 JUL 2017                                                          */
/* ----------------------------------------------------------------------------------- */
import { Injectable }              from '@angular/core';
import { Http, Response }          from '@angular/http';
import { Headers, RequestOptions } from '@angular/http';
import { Request, RequestMethod }  from '@angular/http';

import { Observable }              from 'rxjs/Observable';


@Injectable()
export class EmployeeService {
  	constructor(private http: Http) {
	}
    
	getInitialResources() : Observable<any>{
		return this.http.get("http://localhost:8081/api/employee").map(res => {
    		if (res.status == 200) return res.json();
            return {};
        });
    }
    
    getDatabaseInfo(url: string) : Observable<any> {
		return this.http.get(url).map(res => {
    		if (res.status == 200) return res.json();
            return {};
        });
    }
}
