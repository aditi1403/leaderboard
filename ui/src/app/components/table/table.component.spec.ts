import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';
import { TableComponent } from './table.component';
import { RouterTestingModule } from '@angular/router/testing';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { CommonService } from '../../services/common.service';

describe('TableComponent', () => {
  let component: TableComponent;
  let fixture: ComponentFixture<TableComponent>;
  let router: Router;
  let location: Location;
  let commonService: CommonService;
  const routerSpy = jasmine.createSpyObj('Router', ['navigate']);
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        TableComponent,
      ],
      imports: [
        IonicModule.forRoot(),
        NgxDatatableModule,
        RouterTestingModule
      ],
      providers: [{ provide: Router, useValue: routerSpy }]
    }).compileComponents();

    router = TestBed.get(Router);
    location = TestBed.get(Location);

    fixture = TestBed.createComponent(TableComponent);
    component = fixture.componentInstance;
    commonService = TestBed.get(CommonService);
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should change route on click of row', () => {
    component.currentData = new Date();
    component.currentData.setMonth(0);
    component.currentData.setFullYear(2020);
    spyOn(commonService, 'getMonthName').and.returnValue('january');
    const event = { type: 'click', row: { knolderId: 2 } };
    component.onActivate(event);
    expect(routerSpy.navigate).toHaveBeenCalledWith(
        ['/details'],
        { queryParams: { id : event.row.knolderId, year: 2020, month: 'january' } }
      );
  });

  it('should stay on same route on other events', () => {
    const nonClickEvent = { type: 'notClick', row: { knolderId: 2 } };
    component.onActivate(nonClickEvent);
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/']);
  });

  it('should emit sorting criteria', () => {
    const eventObj = {newValue: 'asc', column: {prop: 'monthlyRank'}};
    spyOn(component.sortCriteria, 'emit');
    component.onSort(eventObj);
    expect(component.sortCriteria.emit).toHaveBeenCalledWith({newValue: 'asc', column: {prop: 'monthlyRank'}});
  });
});
