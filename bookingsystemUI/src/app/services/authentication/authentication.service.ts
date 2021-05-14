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
  private refreshTokenTimeout = 0;

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
          sessionStorage.setItem('refreshToken', <string> userData.refreshToken);
          sessionStorage.setItem('roles', JSON.stringify(userData.roles));
          sessionStorage.setItem('id', userData.userId + "");
          this.startRefreshTokenTimer();
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
    this.stopRefreshTokenTimer();
  }

  refreshToken() {
    console.log({refreshToken: sessionStorage.getItem('refreshToken')});
    return this.httpClient.post<any>(apiPath + `user/refreshtoken`,
      {refreshToken: sessionStorage.getItem('refreshToken')})
      .pipe(map(response => {
        sessionStorage.setItem('token', 'Bearer ' + response.token);
        console.log(sessionStorage.getItem('token'));
        this.startRefreshTokenTimer();
        return response;
      }));
  }

  private startRefreshTokenTimer() {
    const token = sessionStorage.getItem('token');
    if(token != null) {
      const jwtTokenParse = JSON.parse(atob(token.split('.')[1]));

      const expires = new Date(jwtTokenParse.exp * 1000);
      var timeout = expires.getTime() - Date.now() - (60 * 1000);
      this.refreshTokenTimeout = setTimeout(() => this.refreshToken().subscribe(), timeout);
    }
  }

  private stopRefreshTokenTimer() {
    clearTimeout(this.refreshTokenTimeout);
  }
}
