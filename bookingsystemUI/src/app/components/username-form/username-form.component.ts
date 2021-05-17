import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {UserService} from '../../services/user/user.service';

@Component({
  selector: 'app-username-form',
  templateUrl: './username-form.component.html',
  styleUrls: ['./username-form.component.scss']
})
export class UsernameFormComponent implements OnInit {
  usernameForm: FormGroup = this.formBuilder.group({
    username: ['', Validators.required],
  });
  submitted = false;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private userService: UserService
  ) { }

  ngOnInit(): void {
  }

  get f() { return this.usernameForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.usernameForm.invalid) {
      return;
    }

    this.userService.recoveryPassword(this.f.username.value)
      .subscribe(
        data => {
          this.router.navigate(['/login']);
        },
        error => console.log(error)
      );
  }
}
