import { Component, OnInit } from '@angular/core';

import { BillService } from './../service/bill.service';
import { AgentBillPage } from './../model/AgentBillPage';
import { AgentBillFilter } from './../model/AgentBillFilter';
import { BillStatus } from './../model/BillStatus';

import * as moment from 'moment';
@Component({
  selector: 'app-agentbill',
  templateUrl: './agentbill.component.html',
  styleUrls: ['./agentbill.component.css']
})
export class AgentbillComponent implements OnInit {

  selectedStatusValue = null;

  dateFormat = 'yyyy/MM/dd';

  queryDateRange: Date[] = [];

  agentBillPage: AgentBillPage = new AgentBillPage();

  filter: AgentBillFilter = new AgentBillFilter();

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
    this.filter.statBeginDateAfter = this.queryDateRange[0];
    this.filter.statBeginDateBefore = this.queryDateRange[1];
    if (this.selectedStatusValue === '0') {
      this.filter.statuses = [this.billStatus.UNPAID];
    } else if (this.selectedStatusValue === '1') {
      this.filter.statuses = [this.billStatus.PAID];
    } else {
      this.filter.statuses = null;
    }
    console.log(this.filter);

    this.billService.findAgentBills(this.filter)
      .subscribe(value => {
        this.agentBillPage = value;
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
