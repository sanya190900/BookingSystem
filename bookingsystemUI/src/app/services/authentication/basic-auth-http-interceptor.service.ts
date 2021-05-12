import { Injectable } from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BasicAuthHttpInterceptorService implements HttpInterceptor {
  token: string = ""
  constructor() { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.token = <string> sessionStorage.getItem('token');

    if (sessionStorage.getItem('username') && this.token) {
      req = req.clone({
        setHeaders: {
          Authorization: this.token
        }
      })
    }

    return next.handle(req);
  }
}
