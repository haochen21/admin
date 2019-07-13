import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormControl, FormBuilder, FormGroup } from '@angular/forms';

import { NzMessageService } from 'ng-zorro-antd';

import { SecurityService } from './../service/security.service';
import { Merchant } from './../model/Merchant';
import { User } from './../model/User';
import { UserFilter } from './../model/UserFilter';

@Component({
  selector: 'app-modify-merchant',
  templateUrl: './modify-merchant.component.html',
  styleUrls: ['./modify-merchant.component.css']
})
export class ModifyMerchantComponent implements OnInit {

  merchant: Merchant = new Merchant();

  selectedAgent: User = new User();

  listOfAgents: Array<User> = [];

  nzFilterOption = () => true;

  form: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private message: NzMessageService,
    private securityService: SecurityService) {
    this.form = this.formBuilder.group({
      'approved': [false],
      'open': [false],
      'takeOut': [false],
      'transferOpenId': [null],
      'deviceNo': [null],
      'printNo': [null],
      'discount': [null],
      'autoPayment': [false],
      'rate': [null],
      'loginName': [null],
      'takeByPhone': [false],
      'takeByPhoneSuffix': [false],
      'user': [null]
    });
  }

  ngOnInit() {
    let id = +this.route.snapshot.params.id;
    this.securityService.findMerchant(id)
      .subscribe(merchant => {
        this.merchant = merchant;
        this.setForm();
      });
  }

  setForm() {
    (<FormControl>this.form.controls['approved']).setValue(this.merchant.approved);
    (<FormControl>this.form.controls['open']).setValue(this.merchant.open);
    (<FormControl>this.form.controls['takeOut']).setValue(this.merchant.takeOut);
    (<FormControl>this.form.controls['transferOpenId']).setValue(this.merchant.transferOpenId);
    (<FormControl>this.form.controls['deviceNo']).setValue(this.merchant.deviceNo);
    (<FormControl>this.form.controls['printNo']).setValue(this.merchant.printNo);
    (<FormControl>this.form.controls['discount']).setValue(this.merchant.discount);
    (<FormControl>this.form.controls['autoPayment']).setValue(this.merchant.autoPayment);
    (<FormControl>this.form.controls['rate']).setValue(this.merchant.rate);
    (<FormControl>this.form.controls['loginName']).setValue(this.merchant.loginName);
    (<FormControl>this.form.controls['takeByPhone']).setValue(this.merchant.takeByPhone);
    (<FormControl>this.form.controls['takeByPhoneSuffix']).setValue(this.merchant.takeByPhoneSuffix);
    if (this.merchant.user) {
      this.listOfAgents.push(this.merchant.user);
      this.selectedAgent = this.listOfAgents[0];
    }
  }

  searchAgents(value: string): void {
    let userFilter: UserFilter = new UserFilter();
    userFilter.name = value;
    this.securityService.findAgents(userFilter)
      .subscribe(agents => {
        const listOfOption: Array<User> = [];
        agents.forEach(agent => {
          listOfOption.push(agent);
        });
        this.listOfAgents = listOfOption;
      });
  }

  submit() {
    this.merchant.approved = this.form.value.approved;
    this.merchant.open = this.form.value.open;
    this.merchant.takeOut = this.form.value.takeOut;
    this.merchant.transferOpenId = this.form.value.transferOpenId;
    this.merchant.deviceNo = this.form.value.deviceNo;
    this.merchant.printNo = this.form.value.printNo;
    this.merchant.discount = this.form.value.discount;
    this.merchant.autoPayment = this.form.value.autoPayment;
    this.merchant.rate = this.form.value.rate;
    this.merchant.loginName = this.form.value.loginName;
    this.merchant.takeByPhone = this.form.value.takeByPhone;
    this.merchant.takeByPhoneSuffix = this.form.value.takeByPhoneSuffix;
    this.merchant.user = this.selectedAgent;

    console.log(this.merchant);

    this.listOfAgents.splice(0);

    this.securityService.modifyMerchant(this.merchant)
      .subscribe(
        merchant => {
          this.selectedAgent = null;
          this.merchant = merchant;
          console.log(this.merchant);
          this.setForm();
          this.message.create('success', '修改商户信息成功');
        },
        error => {
          console.log(error);
          this.message.create('error', '修改商户信息失败');
        });
  }

}
