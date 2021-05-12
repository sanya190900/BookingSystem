import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../../services/authentication/authentication.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
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
