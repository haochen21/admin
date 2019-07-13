import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { BillPage } from '../model/BillPage';
import { BillFilter } from '../model/BillFilter';
import { AgentBillPage } from '../model/AgentBillPage';
import { AgentBillFilter } from '../model/AgentBillFilter';

@Injectable({ providedIn: 'root' })
export class BillService {

    constructor(private http: HttpClient) { }

    findBills(filter: BillFilter): Observable<BillPage> {
        return this.http.post<BillPage>('/api/bill/page', filter);
    }

    findAgentBills(filter: AgentBillFilter): Observable<AgentBillPage> {
        return this.http.post<AgentBillPage>('/api/agentBill/page', filter);
    }

    findEarning(filter: BillFilter): Observable<any> {
        return this.http.post<any>('/api/bill/statTicketEarning', filter);
    }
}