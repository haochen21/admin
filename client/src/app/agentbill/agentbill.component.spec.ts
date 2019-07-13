import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AgentbillComponent } from './agentbill.component';

describe('AgentbillComponent', () => {
  let component: AgentbillComponent;
  let fixture: ComponentFixture<AgentbillComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AgentbillComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AgentbillComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
