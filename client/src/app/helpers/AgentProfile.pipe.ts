import { Pipe, PipeTransform } from '@angular/core';
import { Profile } from '../model/Profile';

@Pipe({ name: 'agentProfileFormatPipe' })
export class AgentProfileFormatPipe implements PipeTransform {

    transform(value: Profile): any {
        if (value === Profile.ADMIN) {
            return "管理员";
        } else if (value === Profile.AGENT) {
            return "代理商";
        } else {
            return "";
        }
    }

}