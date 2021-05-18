import {AddressModel} from './AddressModel';
import {UserModel} from './UserModel';
import {CommentModel} from './CommentModel';
import {GalleryModel} from './GalleryModel';
import {ScheduleModel} from './ScheduleModel';
import {ServiceModel} from './ServiceModel';
import {ScheduleModelRequest} from './ScheduleModelRequest';

export class PlaceModel{
  place_id?: number;
  name?: string;
  address?: AddressModel;
  user?: UserModel;
  description?: string;
  comments?: CommentModel[];
  gallery?: GalleryModel[];
  pathsToPhotos?: string[];
  schedule?: ScheduleModel[];
  schedules?: ScheduleModelRequest[];
  services?: ServiceModel[];
  service?: string[];
}
