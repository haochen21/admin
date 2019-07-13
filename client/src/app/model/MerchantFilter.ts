export class MerchantFilter {

    deviceNo: string;
    openId: string;
    transferOpenId: string;
    name: string;
    loginName: string;
    open: string;
    autoPayment: boolean;
    trash: boolean;
    page: number = 1;
    size: number = 10;

    constructor() {
    }
}