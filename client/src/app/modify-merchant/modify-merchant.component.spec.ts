import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyMerchantComponent } from './modify-merchant.component';

describe('ModifyMerchantComponent', () => {
  let component: ModifyMerchantComponent;
  let fixture: ComponentFixture<ModifyMerchantComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModifyMerchantComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModifyMerchantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
