import { Component, OnInit } from '@angular/core';

import { BillService } from './../service/bill.service';
import { BillPage } from './../model/BillPage';
import { BillFilter } from './../model/BillFilter';
import { BillStatus } from './../model/BillStatus';

import * as moment from 'moment';

@Component({
  selector: 'app-bill',
  templateUrl: './bill.component.html',
  styleUrls: ['./bill.component.css']
})
export class BillComponent implements OnInit {

  dateFormat = 'yyyy/MM/dd';

  queryDateRange: Date[] = [];

  billPage: BillPage = new BillPage();

  filter: BillFilter = new BillFilter();

  billStatus = BillStatus;

  constructor(private billService: BillService) { }

  ngOnInit() {
    let beginDay: moment.Moment = moment(new Date());
    beginDay.add(-60, 'd');
    let endDay: moment.Moment = moment(new Date());

    this.queryDateRange[0] = beginDay.toDate();
    this.queryDateRange[1] = endDay.toDate();
    
    this.query();
  }

  query() {
    this.filter.statDateAfter = this.queryDateRange[0];
    this.filter.statDateBefore = this.queryDateRange[1];
    console.log(this.filter);

    this.billService.findBills(this.filter)
      .subscribe(value => {
        this.billPage = value;
      });
  }

  pageChange(event) {
    this.filter.page = +event;
    this.query();
  }

  submitQueryForm() {
    this.filter.page = 1;
    this.query();
  }

}
