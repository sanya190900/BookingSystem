import {AddressModel} from './AddressModel'

export class UserModel {
  user_id?: number;
  username?: string;
  password?: string;
  email?: string;
  name?: string;
  surname?: string;
  phone?: string;
  pathToAvatar?: string;
  address?: AddressModel;
  creation_date_time?: Date;
  role?: string[];
}
