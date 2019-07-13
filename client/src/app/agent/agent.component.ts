import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

import { NzModalRef, NzModalService } from 'ng-zorro-antd';

import { SecurityService } from './../service/security.service';
import { UserFilter } from './../model/UserFilter';
import { Profile } from './../model/Profile';
import { UserPage } from './../model/UserPage';

@Component({
  selector: 'app-agent',
  templateUrl: './agent.component.html',
  styleUrls: ['./agent.component.css']
})
export class AgentComponent implements OnInit {

  profile = Profile;

  queryForm: FormGroup;

  agentPage: UserPage = new UserPage();

  filter: UserFilter = new UserFilter();

  trashAgentModal: NzModalRef;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private modal: NzModalService,
    private securityService: SecurityService) {
    this.queryForm = this.fb.group({
      'loginName': [null],
      'name': [null]
    });
  }

  ngOnInit() {
    this.query();
  }

  query() {
    console.log(this.filter);
    this.securityService.findAgentPage(this.filter)
      .subscribe(agentPage => {
        this.agentPage = agentPage;
      });
  }

  pageChange(event) {
    this.filter.page = +event;
    this.query();
  }

  submitQueryForm() {
    this.filter.loginName = this.queryForm.value.loginName;
    this.filter.name = this.queryForm.value.name;
    this.filter.page = 1;
    this.query();
  }

  modifyAgent(id: number) {
    this.router.navigate(['modifyAgent', id]);
  }

  trashAgent(id: number) {
    this.trashAgentModal = this.modal.confirm({
      nzTitle: '代理商删除！',
      nzOnOk: () =>
        new Promise((resolve, reject) => {
          setTimeout(Math.random() > 0.5 ? resolve : reject, 1000);
        }).catch(() => console.log('Oops errors!'))
    });
  }

}
