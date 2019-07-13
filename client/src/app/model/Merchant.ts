import { User } from './User';

export class Merchant {

    id: number;
    loginName: string;
    deviceNo: string;
    printNo: string;
    approved: boolean;
    openId: string;
    transferOpenId: string;
    name: string;
    realName: string;
    phone: string;
    mail: string;
    open: boolean;
    discount: number;
    takeByPhone: boolean;
    takeByPhoneSuffix: boolean;
    rate: number;
    autoPayment: boolean;
    trash: boolean;
    takeOut: boolean;
    user: User;

    constructor() {
    }
}