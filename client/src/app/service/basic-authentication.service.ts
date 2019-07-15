import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { JsonPipe } from '@angular/common';

export const TOKEN = 'token'
export const AUTHENTICATED_USER = 'authenticaterUser'

@Injectable({
    providedIn: 'root'
})
export class BasicAuthenticationService {

    constructor(private http: HttpClient) { }

    executeJWTAuthenticationService(userName, password) {

        return this.http.post<any>(
            '/auth/signin', {
                "userName": userName,
                "password": password
            }).pipe(
                map(
                    data => {
                        let user = {
                            userName: userName,
                            isAdmin: data.admin
                        };
                        sessionStorage.setItem(AUTHENTICATED_USER, JSON.stringify(user));
                        sessionStorage.setItem(TOKEN, `Bearer ${data.token}`);
                        return data;
                    }
                )
            );
        //console.log("Execute Hello World Bean Service")
    }

    getAuthenticatedUser() {
        return sessionStorage.getItem(AUTHENTICATED_USER)
    }

    getAuthenticatedToken() {
        if (this.getAuthenticatedUser())
            return sessionStorage.getItem(TOKEN)
    }

    isUserLoggedIn() {
        let user = sessionStorage.getItem(AUTHENTICATED_USER)
        return !(user === null)
    }

    isAdmin() {
        let userStr = sessionStorage.getItem(AUTHENTICATED_USER);
        if (userStr) {
            let user = JSON.parse(userStr);
            return user.isAdmin;
        }
        return false;
    }

    logout() {
        sessionStorage.removeItem(AUTHENTICATED_USER)
        sessionStorage.removeItem(TOKEN)
    }

}

export class AuthenticationBean {
    constructor(public message: string) { }
}