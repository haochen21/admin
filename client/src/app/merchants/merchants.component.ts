import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';

import { NzModalRef, NzModalService } from 'ng-zorro-antd';

import { SecurityService } from './../service/security.service';
import { Merchant } from './../model/Merchant';
import { MerchantsPage } from './../model/MerchantsPage';
import { MerchantFilter } from './../model/MerchantFilter';

@Component({
  selector: 'app-merchants',
  templateUrl: './merchants.component.html',
  styleUrls: ['./merchants.component.css']
})
export class MerchantsComponent implements OnInit {

  queryPanelOpen = false;

  mapOfExpandData: { [key: string]: boolean } = {};

  merchant: Merchant;

  merchantsPage: MerchantsPage = new MerchantsPage();

  filter: MerchantFilter = new MerchantFilter();

  size: number = 10;

  isOperating = false;

  trashMerchantModal: NzModalRef;

  queryForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private modal: NzModalService,
    private securityService: SecurityService) { }

  ngOnInit() {
    this.queryForm = this.fb.group({
      name: [null],
      deviceNo: [null],
      openId: [null],
      trash: [false],
      loginName: [null]
    });

    this.query();
  }

  query() {
    this.securityService.findMerchants(this.filter)
      .subscribe(value => {
        this.merchantsPage = value;
        console.log(this.merchantsPage);
      });
  }

  pageChange(event) {
    this.filter.page = +event;
    this.query();
  }

  modifyMerchant(id: number) {
    this.router.navigate(['modifyMerchant', id]);
  }

  order(id: number) {
    this.router.navigate(['orders', id]);
  }

  openQueryDataPanel() {
    this.queryPanelOpen = !this.queryPanelOpen;
  }

  submitQueryForm() {
    this.queryPanelOpen = false;

    this.filter.name = this.queryForm.value.name;
    this.filter.loginName = this.queryForm.value.loginName;
    this.filter.deviceNo = this.queryForm.value.deviceNo;
    this.filter.openId = this.queryForm.value.openId;
    this.filter.trash = this.queryForm.value.trash;
    this.filter.page = 1;
    console.log(this.filter);

    this.query();
  }


  trashMerchant(id: number) {
    this.trashMerchantModal = this.modal.confirm({
      nzTitle: '商家移动到回收站',
      nzOnOk: () =>
        this.securityService.trashMerchant(id).toPromise()
          .then(value => {
            this.merchantsPage.content = this.merchantsPage.content.filter(d => d.id !== id);
          })
          .catch(() => console.log('Oops errors!'))
    });
  }

}
