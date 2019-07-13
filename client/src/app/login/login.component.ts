import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { BasicAuthenticationService } from './../service/basic-authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  invalidLogin = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private basicAuthenticationService: BasicAuthenticationService) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      userName: [null, [Validators.required]],
      password: [null, [Validators.required]]
    });
    this.basicAuthenticationService.logout();
  }

  submitForm(): void {
    for (const i in this.loginForm.controls) {
      this.loginForm.controls[i].markAsDirty();
      this.loginForm.controls[i].updateValueAndValidity();
    }
    console.log(this.loginForm.value.userName + ',' + this.loginForm.value.password);
    this.basicAuthenticationService.executeJWTAuthenticationService(this.loginForm.value.userName, this.loginForm.value.password)
      .subscribe(
        data => {
          console.log(data);
          this.router.navigate(['merchants']);
          this.invalidLogin = false;
        },
        error => {
          console.log(error);
          this.invalidLogin = true;
        }
      )
  }

}
