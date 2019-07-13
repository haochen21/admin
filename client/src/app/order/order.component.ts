import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { OrderService } from './../service/order.service';
import { CartPage } from './../model/CartPage';
import { CartFilter } from './../model/CartFilter';

import * as moment from 'moment';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  dateFormat = 'yyyy/MM/dd';

  mapOfExpandData: { [key: string]: boolean } = {};

  queryDateRange: Date[] = [];

  orderPage: CartPage = new CartPage();

  filter: CartFilter = new CartFilter();

  constructor(
    private route: ActivatedRoute,
    private orderService: OrderService
  ) { }

  ngOnInit() {
    let beginDay: moment.Moment = moment(new Date());
    beginDay.add(-180, 'd');
    let endDay: moment.Moment = moment(new Date());

    this.queryDateRange[0] = beginDay.toDate();
    this.queryDateRange[1] = endDay.toDate();

    let id = +this.route.snapshot.params.id;
    this.filter.merchantId = id;
    this.query();
  }

  query() {
    this.filter.createOnAfter = this.queryDateRange[0];
    this.filter.createOnBefore = this.queryDateRange[1];
    console.log(this.filter);

    this.orderService.findOrders(this.filter)
      .subscribe(value => {
        this.orderPage = value;
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
