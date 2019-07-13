import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgZorroAntdModule, NZ_I18N, zh_CN } from 'ng-zorro-antd';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { registerLocaleData } from '@angular/common';
import zh from '@angular/common/locales/zh';

import { MomentModule } from 'ngx-moment';

import { HttpIntercepterBasicAuthService } from './service/http/http-intercepter-basic-auth.service';
import { LoginComponent } from './login/login.component';
import { AdminComponent } from './admin/admin.component';
import { MerchantsComponent } from './merchants/merchants.component';

import { fakeBackendProvider } from './helpers/fake-backend-Interceptor';
import { ModifyMerchantComponent } from './modify-merchant/modify-merchant.component';
import { OrderComponent } from './order/order.component';
import { AgentComponent } from './agent/agent.component';

import { AgentProfileFormatPipe } from './helpers/AgentProfile.Pipe'
import { BillStatusFormatPipe } from './helpers/BillStatus.pipe';
import { CartStatusFormatPipe } from './helpers/CartStatus.pipe';
import { BillComponent } from './bill/bill.component';
import { AgentbillComponent } from './agentbill/agentbill.component';
import { EarningComponent } from './earning/earning.component';
import { ModifyAgentComponent } from './modify-agent/modify-agent.component';
import { CreateAgentComponent } from './create-agent/create-agent.component';
import { ModifyPasswordComponent } from './modify-password/modify-password.component';

registerLocaleData(zh);

@NgModule({
  declarations: [
    AgentProfileFormatPipe,
    CartStatusFormatPipe,
    BillStatusFormatPipe,
    AppComponent,
    LoginComponent,
    AdminComponent,
    MerchantsComponent,
    ModifyMerchantComponent,
    OrderComponent,
    AgentComponent,
    BillComponent,
    AgentbillComponent,
    EarningComponent,
    ModifyAgentComponent,
    CreateAgentComponent,
    ModifyPasswordComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    AppRoutingModule,
    NgZorroAntdModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MomentModule
  ],
  providers: [
    { provide: NZ_I18N, useValue: zh_CN },
    { provide: HTTP_INTERCEPTORS, useClass: HttpIntercepterBasicAuthService, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
