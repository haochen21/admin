import { Pipe, PipeTransform } from '@angular/core';
import { BillStatus } from '../model/BillStatus';

@Pipe({ name: 'billStatusFormatPipe' })
export class BillStatusFormatPipe implements PipeTransform {

    transform(value: BillStatus, args: string[]): any {
        if (value === BillStatus.UNPAID) {
            return "成功";
        } else if (value === BillStatus.PAID) {
            return "失败";
        } else {
            return "";
        }
    }

}