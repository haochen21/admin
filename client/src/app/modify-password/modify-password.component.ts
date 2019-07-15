import { Component, OnInit } from '@angular/core';
import { FormControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { NzMessageService } from 'ng-zorro-antd';

import { SecurityService } from './../service/security.service';

@Component({
  selector: 'app-modify-password',
  templateUrl: './modify-password.component.html',
  styleUrls: ['./modify-password.component.css']
})
export class ModifyPasswordComponent implements OnInit {

  form: FormGroup;

  selectedRoleValue: string = '1';

  constructor(
    private formBuilder: FormBuilder,
    private message: NzMessageService,
    private router: Router,
    private securityService: SecurityService
  ) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
      'password': [null, [Validators.required]],
      'checkPassword': [null, [Validators.required, this.confirmationValidator]]
    });
  }

  submitForm(): void {
    for (const i in this.form.controls) {
      this.form.controls[i].markAsDirty();
      this.form.controls[i].updateValueAndValidity();
    }
    this.securityService.modifyPassword(this.form.value.password)
      .subscribe(
        agent => {
          console.log(agent);
          this.message.create('success', '修改密码成功');
          this.router.navigate(['merchants']);
        },
        error => {
          console.log(error);
          this.message.create('error', '修改密码失败');
        });
  }

  updateConfirmValidator(): void {
    Promise.resolve().then(() => this.form.controls.checkPassword.updateValueAndValidity());
  }

  confirmationValidator = (control: FormControl): { [s: string]: boolean } => {
    if (!control.value) {
      return { required: true };
    } else if (control.value !== this.form.controls.password.value) {
      return { confirm: true, error: true };
    }
    return {};
  };

}
