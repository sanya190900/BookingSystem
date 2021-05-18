import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  constructor(
    private authenticationService: AuthenticationService,
    private router: Router) {
  }

  ngOnInit(): void {
  }

  isLoggedIn(): boolean {
    return this.authenticationService.isUserLoggedIn();
  }

  isManager() : boolean {
    return JSON
      .parse(sessionStorage.getItem('roles') || "[]")
      .includes("ROLE_MANAGER");
  }

  onLogout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }
}
