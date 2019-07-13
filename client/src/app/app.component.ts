import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { BasicAuthenticationService } from './service/basic-authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'client';

  constructor(
    private router: Router,
    private basicAuthenticationService: BasicAuthenticationService) { }


  isLogin() {
    return this.basicAuthenticationService.isUserLoggedIn();
  }

}
