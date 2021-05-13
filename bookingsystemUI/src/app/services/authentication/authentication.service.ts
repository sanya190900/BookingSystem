import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {UserAuthModel} from '../../models/UserAuthModel';
import {apiPath} from '../../../../globals';
import {Observable} from 'rxjs';
import {TokenModel} from '../../models/TokenModel';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private httpClient:HttpClient) { }

  login(userAuth: UserAuthModel): Observable<TokenModel> {
    return this.httpClient.post<TokenModel>(
      apiPath + 'user/login',
      userAuth
    ).pipe(
      map(
        userData => {
          sessionStorage.setItem('username', <string> userAuth.username);
          let tokenStr = 'Bearer ' + userData.token;
          sessionStorage.setItem('token', tokenStr);
          sessionStorage.setItem('roles', JSON.stringify(userData.roles));
          sessionStorage.setItem('id', userData.userId + "");
          return userData;
        }
      )
    );
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('username');
    return !(user === null);
  }

  logout() {
    this.httpClient.get(apiPath + 'user/revoke')
      .pipe(
        map(
          value => {return value}
        )
      ).subscribe(
        value => {},
        error => console.log(error)
    );

    sessionStorage.removeItem('username');
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('roles');
    sessionStorage.removeItem('id');
  }
}
