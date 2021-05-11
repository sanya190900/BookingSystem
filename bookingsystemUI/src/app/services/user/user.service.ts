import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {UserModel} from '../../models/UserModel';
import {Observable} from 'rxjs';
import {apiPath} from '../../../../globals';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient:HttpClient) { }

  register(user : UserModel) : Observable<UserModel> {
    console.log(apiPath + 'user/');
    console.log(user);
    return this.httpClient.post<UserModel>(apiPath + 'user/', user);
  }
}
