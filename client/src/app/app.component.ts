import { Component } from '@angular/core';
import { BasicAuthenticationService } from './service/basic-authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'client';

  constructor(
    private basicAuthenticationService: BasicAuthenticationService) { }


  isLogin() {
    return this.basicAuthenticationService.isUserLoggedIn();
  }

  isAdmin() {
    return this.basicAuthenticationService.isAdmin();
  }

}
