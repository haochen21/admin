import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Merchant } from './../model/Merchant';
import { MerchantFilter } from './../model/MerchantFilter';
import { MerchantsPage } from './../model/MerchantsPage';
import { User } from './../model/User';
import { UserFilter } from './../model/UserFilter';
import { UserPage } from './../model/UserPage';

@Injectable({ providedIn: 'root' })
export class SecurityService {

    constructor(private http: HttpClient) { }

    findMerchants(filter: MerchantFilter): Observable<MerchantsPage> {
        return this.http.post<MerchantsPage>('/api/merchant/page', filter);
    }

    findMerchant(id: number): Observable<Merchant> {
        return this.http.get<Merchant>('/api/merchant/' + id);
    }

    modifyMerchant(merchant: Merchant): Observable<Merchant> {
        return this.http.put<Merchant>('/api/merchant/' + merchant.id, merchant);
    }

    trashMerchant(id: number): Observable<any> {
        return this.http.put<any>('/api//merchant/trash/' + id, {});
    }

    findAgents(filter: UserFilter): Observable<User[]> {
        return this.http.post<User[]>('/api/user/agent', filter);
    }

    findAgentPage(filter: UserFilter): Observable<UserPage> {
        return this.http.post<UserPage>('/api/agent/page', filter);
    }

    findAgent(id: number): Observable<User> {
        return this.http.get<User>('/api/agent/' + id);
    }

    modifyAgent(agent: User): Observable<User> {
        return this.http.put<User>('/api/agent/' + agent.id, agent);
    }

    createAgent(agent: User): Observable<User> {
        return this.http.post<User>('/api/agent', agent);
    }

    deleteAgent(id: number): Observable<any> {
        return this.http.delete<any>('/api/agent/' + id);
    }

    modifyPassword(password: string): Observable<any> {
        return this.http.put<any>('/api/user/password/' + password, {});
    }

}