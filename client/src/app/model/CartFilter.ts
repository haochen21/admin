import { CartStatus } from './CartStatus';

export class CartFilter {
    
    merchantId: number;
    statuses: Array<CartStatus>;
    payTimeBefore: Date; 
    payTimeAfter: Date;
    createOnBefore: Date; 
    createOnAfter: Date;
    weixinPaid:boolean;
    page: number = 1;
    size: number = 10;
    
    constructor() { }
}