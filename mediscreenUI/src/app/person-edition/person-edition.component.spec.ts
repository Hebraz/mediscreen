import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonEditionComponent } from './person-edition.component';

describe('PersonEditionComponent', () => {
  let component: PersonEditionComponent;
  let fixture: ComponentFixture<PersonEditionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PersonEditionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersonEditionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
