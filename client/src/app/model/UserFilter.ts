import { Profile } from './Profile';

export class UserFilter {

    id: number;
    name: string;
    loginName: string;
    profile: Profile;
    page: number = 1;
    size: number = 10;
    
    constructor() {
    }
}