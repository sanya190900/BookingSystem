import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import {UserModel} from '../../models/UserModel';
import {FormControl, FormGroup} from '@angular/forms';
import {UserService} from '../../services/user/user.service';

declare var $: any;

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.scss']
})
export class ProfilePageComponent implements OnInit {
  user: UserModel = new UserModel();
  editing: boolean = false;
  nameEditing: boolean = false;
  surnameEditing: boolean = false;
  usernameEditing: boolean = false;
  emailEditing: boolean = false;
  phoneEditing: boolean = false;
  imageEditing: boolean = false;
  countryEditing: boolean = false;
  cityEditing: boolean = false;
  streetEditing: boolean = false;
  house_numberEditing: boolean = false;
  form: FormGroup = new FormGroup({
    name: new FormControl(null),
    surname: new FormControl(null),
    username: new FormControl(null),
    email: new FormControl(null),
    phone: new FormControl(null),
    image: new FormControl(null),
    country: new FormControl(null),
    city: new FormControl(null),
    street: new FormControl(null),
    house_number: new FormControl(null),
  });

  constructor(
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private router: Router
  ) {
    if (!authenticationService.isUserLoggedIn()) {
      router.navigate(['/login']);
    }

    userService.getUser().subscribe(value => {
      this.user = value;
      console.log(this.user);
    });
  }

  ngOnInit(): void { }

  setEditFalse(): void {
    this.editing = false;
    this.nameEditing = false;
    this.surnameEditing = false;
    this.usernameEditing = false;
    this.emailEditing = false;
    this.phoneEditing = false;
    this.imageEditing = false;
    this.countryEditing = false;
    this.cityEditing = false;
    this.streetEditing = false;
    this.house_numberEditing = false;
  }

  nameEdit(): void {
    this.editing = !this.editing;
    this.nameEditing = !this.nameEditing;
  }

  surnameEdit(): void {
    this.editing = !this.editing;
    this.surnameEditing = !this.surnameEditing;
  }

  usernameEdit(): void {
    this.editing = !this.editing;
    this.usernameEditing = !this.usernameEditing;
  }

  emailEdit(): void {
    this.editing = !this.editing;
    this.emailEditing = !this.emailEditing;
  }

  phoneEdit(): void {
    this.editing = !this.editing;
    this.phoneEditing = !this.phoneEditing;
  }

  imageEdit(): void {
    this.editing = !this.editing;
    this.imageEditing = !this.imageEditing;
  }

  countryEdit(): void {
    this.editing = !this.editing;
    this.countryEditing = !this.countryEditing;
  }

  cityEdit(): void {
    this.editing = !this.editing;
    this.cityEditing = !this.cityEditing;
  }

  streetEdit(): void {
    this.editing = !this.editing;
    this.streetEditing = !this.streetEditing;
  }

  house_numberEdit(): void {
    this.editing = !this.editing;
    this.house_numberEditing = !this.house_numberEditing;
  }

  updateProfile(): void {
    this.setEditFalse();
    this.userService.updateUser(
      {
        user_id: this.user.user_id,
        username: this.form.value.username,
        email: this.form.value.email,
        name: this.form.value.name,
        surname: this.form.value.surname,
        phone: this.form.value.phone,
        pathToAvatar: this.form.value.image,
        address: {
          country: this.form.value.country,
          city: this.form.value.city,
          street: this.form.value.street,
          house_number: this.form.value.house_number
        }
      }).subscribe(value => this.user = value);
  }
}
