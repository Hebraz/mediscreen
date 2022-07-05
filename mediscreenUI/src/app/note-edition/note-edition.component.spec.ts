import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NoteEditionComponent } from './note-edition.component';

describe('NoteEditionComponent', () => {
  let component: NoteEditionComponent;
  let fixture: ComponentFixture<NoteEditionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NoteEditionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NoteEditionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
