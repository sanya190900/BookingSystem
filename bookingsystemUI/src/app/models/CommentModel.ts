import {UserModel} from './UserModel';
import {PlaceModel} from './PlaceModel';

export class CommentModel{
  comment?: string;
  place?: PlaceModel;
  user?: UserModel;
}
