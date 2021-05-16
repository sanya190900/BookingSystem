import { Injectable } from '@angular/core';
import {CommentModel} from '../../models/CommentModel';
import {HttpClient} from '@angular/common/http';
import {apiPath} from '../../../../globals';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private httpClient: HttpClient) { }

  addComment(comment: CommentModel) : Observable<any> {
    console.log(comment)
    return this.httpClient.post(apiPath + "place/comment", comment);
  }
}
