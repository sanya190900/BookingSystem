import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import {UserService} from '../../services/user/user.service';
import {UserModel} from '../../models/UserModel';

@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.scss']
})
export class RegistrationPageComponent implements OnInit {

  registerForm: FormGroup = this.formBuilder.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    username: ['', Validators.required],
    password: ['', [Validators.required, Validators.minLength(6)]],
    email: ['', Validators.required],
    phone: [''],
    pathToAvatar: [''],
    country: ['', Validators.required],
    city: ['', Validators.required],
    street: ['', Validators.required],
    house_number: ['', Validators.required],
    role: [false]
  });
  loading = false;
  submitted = false;
  returnUrl: string = "/";
  userModel: UserModel = new UserModel();

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authenticationService: AuthenticationService,
    private userService: UserService,
  ) {
    if (this.authenticationService.isUserLoggedIn()) {
      this.router.navigate([this.returnUrl]);
    }
  }

  ngOnInit() {
  }

  get f() { return this.registerForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.registerForm.invalid) {
      return;
    }

    this.loading = true;
    this.userModel = {
      username: this.f.username.value,
      password: this.f.password.value,
      email: this.f.email.value,
      name: this.f.firstName.value,
      surname: this.f.lastName.value,
      phone: this.f.phone.value,
      pathToAvatar: this.f.pathToAvatar.value,
      address: {
        country: this.f.country.value,
        city: this.f.city.value,
        street: this.f.street.value,
        house_number: this.f.house_number.value
      },
      role: this.f.role.value ?
        ["user", "manager"] :
        ["user"]
    };

    this.userService.register(this.userModel)
      .subscribe(
        data => {
          this.router.navigate(['/login']);
        },
        error => {
          //this.alertService.error(error);
          console.log(error);
          this.loading = false;
        });
  }

}
