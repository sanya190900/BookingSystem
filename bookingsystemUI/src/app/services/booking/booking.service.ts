import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {BookingRequestModel} from '../../models/BookingRequestModel';
import {HttpClient} from '@angular/common/http';
import {apiPath} from '../../../../globals';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  constructor(private httpClient : HttpClient) { }

  reserve(bookingModel : BookingRequestModel) : Observable<any>{
    console.log(bookingModel);
    return this.httpClient.post<BookingRequestModel>(apiPath + 'booking/reserve', bookingModel);
  }
}
