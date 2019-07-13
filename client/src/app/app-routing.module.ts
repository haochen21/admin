import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { AdminComponent } from './admin/admin.component';
import { MerchantsComponent } from './merchants/merchants.component';
import { ModifyMerchantComponent } from './modify-merchant/modify-merchant.component';
import { OrderComponent } from './order/order.component';
import { AgentComponent } from './agent/agent.component';
import { BillComponent } from './bill/bill.component';
import { AgentbillComponent } from './agentbill/agentbill.component';
import { EarningComponent } from './earning/earning.component';
import { ModifyAgentComponent } from './modify-agent/modify-agent.component';
import { CreateAgentComponent } from './create-agent/create-agent.component';
import { ModifyPasswordComponent } from './modify-password/modify-password.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'login', component: LoginComponent },
  { path: 'merchants', component: MerchantsComponent },
  { path: 'modifyMerchant/:id', component: ModifyMerchantComponent },
  { path: 'orders/:merchantId', component: OrderComponent },
  { path: 'bills', component: BillComponent },
  { path: 'agentBills', component: AgentbillComponent },
  { path: 'earning', component: EarningComponent },
  { path: 'agents', component: AgentComponent },
  { path: 'modifyAgent/:id', component: ModifyAgentComponent },
  { path: 'createAgent', component: CreateAgentComponent },
  { path: 'modifyPassword', component: ModifyPasswordComponent },
  { path: 'admin/:name', component: AdminComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
