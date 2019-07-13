import { BillStatus } from './BillStatus';

export class BillFilter {

    statDateBefore: Date;
    statDateAfter: Date;
    statDate: Date;
    merchantId: number;
    userId: number;
    statuses: Array<BillStatus>;
    page: number = 1;
    size: number = 10;

    constructor() { }
}