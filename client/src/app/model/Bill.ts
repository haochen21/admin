import { BillStatus } from './BillStatus';

export class Bill {

    id: number;
    no: string;
    statDate: string;
    openId: string;
    name: string;
    status: BillStatus;
    totalPrice: number;
    rate: number;
    serviceCharge: number;
    payment: number;
    agentRate: number;
    weixinEarning: number;
    agentEarning: number;
    ticketEarning: number;
    createdOn: Date;
    updatedOn: Date;

}