import { Merchant } from './Merchant';

export class MerchantsPage {
    first: boolean;
    last: boolean;
    numberOfElements: number;
    totalElements: number;
    totalPages: number;
    size: number;
    content: Array<Merchant>;
}