import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../../services/authentication/authentication.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss']
})
export class MainPageComponent implements OnInit {
  loggedIn: boolean;

  constructor(private authenticationService: AuthenticationService) {
    this.loggedIn = authenticationService.isUserLoggedIn();
  }

  ngOnInit(): void {
  }

  onLogout() {
    this.authenticationService.logout();
  }

}
