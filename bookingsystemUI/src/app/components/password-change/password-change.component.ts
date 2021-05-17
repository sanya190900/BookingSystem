import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../services/user/user.service';

@Component({
  selector: 'app-password-change',
  templateUrl: './password-change.component.html',
  styleUrls: ['./password-change.component.scss']
})
export class PasswordChangeComponent implements OnInit {
  passwordForm: FormGroup = this.formBuilder.group({
    password: ['', [Validators.required, Validators.minLength(6)]]
  });
  submitted = false;
  token: string = "";

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private formBuilder: FormBuilder,
    private userService: UserService
  ) {
    activatedRoute.queryParams.subscribe(
      params => this.token = params['token']
    )
  }

  ngOnInit(): void {
  }

  get f() { return this.passwordForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.passwordForm.invalid) {
      return;
    }

    this.userService.updatePassword({
      token: this.token,
      password: this.f.password.value})
      .subscribe(
        data => {
          this.router.navigate(['/login']);
        },
        error => console.log(error)
      );
  }
}
