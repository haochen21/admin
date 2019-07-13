import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormControl, FormBuilder, FormGroup } from '@angular/forms';

import { NzMessageService } from 'ng-zorro-antd';

import { SecurityService } from './../service/security.service';
import { User } from './../model/User';

@Component({
  selector: 'app-modify-agent',
  templateUrl: './modify-agent.component.html',
  styleUrls: ['./modify-agent.component.css']
})
export class ModifyAgentComponent implements OnInit {

  form: FormGroup;

  agent: User = new User();

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private message: NzMessageService,
    private securityService: SecurityService) {
    this.form = this.formBuilder.group({
      'name': [null],
      'phone': [null],
      'mail': [null]
    });
  }

  ngOnInit() {
    let id = +this.route.snapshot.params.id;
    this.securityService.findAgent(id)
      .subscribe(agent => {
        this.agent = agent;
        this.setForm();
      });
  }

  setForm() {
    (<FormControl>this.form.controls['name']).setValue(this.agent.name);
    (<FormControl>this.form.controls['phone']).setValue(this.agent.phone);
    (<FormControl>this.form.controls['mail']).setValue(this.agent.email);
  }

  submit() {
    this.agent.name = this.form.value.name;
    this.agent.phone = this.form.value.phone;
    this.agent.email = this.form.value.mail;

    console.log(this.agent);

    this.securityService.modifyAgent(this.agent)
      .subscribe(
        agent => {
          this.agent = agent;
          console.log(this.agent);
          this.setForm();
          this.message.create('success', '修改代理商信息成功');
        },
        error => {
          console.log(error);
          this.message.create('error', '修改代理商信息失败');
        });
  }

}
