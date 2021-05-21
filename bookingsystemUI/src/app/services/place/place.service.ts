import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {apiPath} from '../../../../globals';
import {PlaceModel} from '../../models/PlaceModel';
import {PlacesModel} from '../../models/PlacesModel';

@Injectable({
  providedIn: 'root'
})
export class PlaceService {

  constructor(private httpClient:HttpClient) { }

  getPlace(id: number) : Observable<PlaceModel>{
    return this.httpClient.get<PlaceModel>(apiPath + 'place/' + id);
  }

  addPlace(placeModel : PlaceModel) : Observable<any> {
    return this.httpClient.post(apiPath + 'place', placeModel);
  }

  getPlaces(page : number, pageSize : number) : Observable<PlacesModel>{
    return this.httpClient.get(apiPath + 'place?page=' + page + '&pageSize=' + pageSize);
  }
}
