import {AddressModel} from './AddressModel'

export class UserModel {
  username?: string;
  password?: string;
  email?: string;
  name?: string;
  surname?: string;
  phone?: string;
  pathToAvatar?: string;
  address?: AddressModel;
  role?: string[];
}
