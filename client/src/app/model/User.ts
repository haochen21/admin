import { Profile } from './Profile';

export class User {

    id: number;
    name: string;
    loginName: string;
    password: string;
    phone: string;
    email: string;
    rate: number;
    transferOpenId: string;
    profile: Profile;

    constructor() {
    }
}