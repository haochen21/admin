import { Component, OnInit } from '@angular/core';

import { BillService } from './../service/bill.service';
import { BillFilter } from './../model/BillFilter';

import * as moment from 'moment';

@Component({
  selector: 'app-earning',
  templateUrl: './earning.component.html',
  styleUrls: ['./earning.component.css']
})
export class EarningComponent implements OnInit {

  dateFormat = 'yyyy/MM/dd';

  queryDateRange: Date[] = [];

  filter: BillFilter = new BillFilter();

  earning: number = 0.0;

  constructor(private billService: BillService) { }

  ngOnInit() {
    let beginDay: moment.Moment = moment(new Date());
    beginDay.add(-60, 'd');
    let endDay: moment.Moment = moment(new Date());

    this.queryDateRange[0] = beginDay.toDate();
    this.queryDateRange[1] = endDay.toDate();

    this.submitQueryForm();
  }

  submitQueryForm() {
    this.filter.statDateAfter = this.queryDateRange[0];
    this.filter.statDateBefore = this.queryDateRange[1];
    console.log(this.filter);

    this.billService.findEarning(this.filter)
      .subscribe(value => {
        this.earning = +value;
      });
  }

}
