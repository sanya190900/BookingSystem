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
    sessionStorage.removeItem('username');
    sessionStorage.removeItem('token');
  }
}