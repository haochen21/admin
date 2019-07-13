import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { CartPage } from '../model/CartPage';
import { CartFilter } from '../model/CartFilter';

@Injectable({ providedIn: 'root' })
export class OrderService {

    constructor(private http: HttpClient) { }

    findOrders(filter: CartFilter): Observable<CartPage> {
        return this.http.post<CartPage>('/api/order/page', filter);
    }

}