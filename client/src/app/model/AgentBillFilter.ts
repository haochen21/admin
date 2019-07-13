import { BillStatus } from './BillStatus';

export class AgentBillFilter {

    statBeginDateBefore: Date;
    statBeginDateAfter: Date;    
    userId: number;
    statuses: Array<BillStatus>;
    page: number = 1;
    size: number = 10;

    constructor() { }
}