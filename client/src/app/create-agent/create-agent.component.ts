import { Component, OnInit } from '@angular/core';
import { FormControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { NzMessageService } from 'ng-zorro-antd';

import { SecurityService } from './../service/security.service';
import { User } from './../model/User';
import { Profile } from './../model/Profile';

@Component({
  selector: 'app-create-agent',
  templateUrl: './create-agent.component.html',
  styleUrls: ['./create-agent.component.css']
})
export class CreateAgentComponent implements OnInit {

  form: FormGroup;

  selectedRoleValue: string = '1';

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private message: NzMessageService,
    private securityService: SecurityService
  ) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
      'loginName': [null, [Validators.required]],
      'password': [null, [Validators.required]],
      'checkPassword': [null, [Validators.required, this.confirmationValidator]],
      'name': [null, [Validators.required]],
      'phone': [null, [Validators.required]],
      'email': [null, [Validators.email]],
      'rate': [null],
      'openId': [null, [Validators.required]]
    });
  }

  submitForm(): void {
    for (const i in this.form.controls) {
      this.form.controls[i].markAsDirty();
      this.form.controls[i].updateValueAndValidity();
    }
    let agent: User = new User();
    agent.loginName = this.form.value.loginName;
    agent.password = this.form.value.password;
    agent.name = this.form.value.name;
    agent.phone = this.form.value.phone;
    agent.email = this.form.value.email;
    agent.rate = this.form.value.rate;
    agent.transferOpenId = this.form.value.openId;
    if (this.selectedRoleValue === '0') {
      agent.profile = Profile.ADMIN;
    } else if (this.selectedRoleValue === '1') {
      agent.profile = Profile.AGENT;
    }
    console.log(agent);

    this.securityService.createAgent(agent)
      .subscribe(
        agent => {
          console.log(agent);
          this.message.create('success', '创建代理商信息成功');
          this.router.navigate(['agents']);
        },
        error => {
          console.log(error);
          this.message.create('error', '创建代理商信息失败');
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
