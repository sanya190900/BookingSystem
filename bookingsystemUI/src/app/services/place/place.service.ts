import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {apiPath} from '../../../../globals';
import {PlaceModel} from '../../models/PlaceModel';

@Injectable({
  providedIn: 'root'
})
export class PlaceService {

  constructor(private httpClient:HttpClient) { }

  getPlace(id: number) : Observable<PlaceModel>{
    return this.httpClient.get<PlaceModel>(apiPath + 'place/' + id);
  }
}
