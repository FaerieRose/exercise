/* ----------------------------------------------------------------------------------- */
/* Author       : FaerieRose                                                           */
/* Date created : 24 JUL 2017                                                          */
/* ----------------------------------------------------------------------------------- */
import { Injectable }     from '@angular/core';
import { Http, Response } from '@angular/http';

import { Observable }     from 'rxjs/Observable';

import { Resource }       from './resource';

@Injectable()
export class GlobalService {
  constructor(private http: Http) {
  }

  private resources: Resource[];

  setResources(resources: Resource[]) {
    this.resources = resources;
  }

  addResources(resources: Resource[]) {
    for (let resource of resources) {
        this.resources.push(resource);
    }
  }

  isResourceAvailable(resource: string): boolean {
    for (let res of this.resources) {
      if (res.command == resource) {
        return true;
      }
    }
    return false;
  }

  getResource(resource: string): Resource {
    for (let res of this.resources) {
      if (res.command == resource) {
        return res;
      }
    }
    return null;
  }
}
