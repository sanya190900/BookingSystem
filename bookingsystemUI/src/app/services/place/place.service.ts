import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {apiPath} from '../../../../globals';
import {PlaceModel} from '../../models/PlaceModel';
import {PlacesModel} from '../../models/PlacesModel';
import {SearchModel} from '../../models/SearchModel';

@Injectable({
  providedIn: 'root'
})
export class PlaceService {

  constructor(private httpClient:HttpClient) { }

  getPlace(id: number) : Observable<PlaceModel> {
    return this.httpClient.get<PlaceModel>(apiPath + 'place/' + id);
  }

  addPlace(placeModel : PlaceModel) : Observable<any> {
    return this.httpClient.post(apiPath + 'place', placeModel);
  }

  getPlaces(searchModel : SearchModel) : Observable<PlacesModel> {
    return this.httpClient.get(apiPath + 'place?' +
      'page=' + searchModel.page +
      '&pageSize=' + searchModel.pageSize +
      (typeof searchModel.name !== "undefined" ? '&name=' + searchModel.name : "") +
      (searchModel.country ? '&country=' + searchModel.country : "") +
      (searchModel.city ? '&city=' + searchModel.city : "") +
      (searchModel.street ? '&street=' + searchModel.street : "") +
      (searchModel.creatorName ? '&creatorName=' + searchModel.creatorName : "") +
      (searchModel.creatorSurname ? '&creatorSurname=' + searchModel.creatorSurname : "")
    );
  }
}
