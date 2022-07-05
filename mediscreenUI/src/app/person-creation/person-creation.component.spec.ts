import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonCreationComponent } from './person-creation.component';

describe('PersonCreationComponent', () => {
  let component: PersonCreationComponent;
  let fixture: ComponentFixture<PersonCreationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PersonCreationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersonCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
