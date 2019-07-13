import { BillStatus } from './BillStatus';

export class AgentBill {

    id: number;
    no: string;
    statBeginDate: string;
    statEndDate: string;
    openId: string;
    name: string;
    status: BillStatus;
    earning: number;
    createdOn: Date;
    updatedOn: Date;
}