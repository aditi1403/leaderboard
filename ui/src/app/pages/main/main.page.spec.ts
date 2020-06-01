import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {IonicModule} from '@ionic/angular';

import {MainPage} from './main.page';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {AuthorModel} from '../../models/author.model';
import {CardComponent} from '../../components/card/card.component';
import {HeadersComponent} from '../../components/headers/headers.component';
import {of} from 'rxjs';
import {TableComponent} from '../../components/table/table.component';
import {EmployeeFilterPipe} from '../../pipe/employee-filter.pipe';
import {ReactiveFormsModule} from '@angular/forms';

describe('MainPage', () => {
    let component: MainPage;
    let fixture: ComponentFixture<MainPage>;
    let mockEmployeeService: EmployeeActivityService;
    const dummyAuthorData: AuthorModel[] = [
        {
            knolderId: 1,
            knolderName: 'mark',
            allTimeScore: 10,
            allTimeRank: 2,
            quarterlyStreak: '5-6-7',
            monthlyScore: 7,
            monthlyRank: 1
        }, {
            knolderId: 2,
            knolderName: 'sam',
            allTimeScore: 15,
            allTimeRank: 1,
            quarterlyStreak: '5-6-8',
            monthlyScore: 5,
            monthlyRank: 2
        }
    ];

    const dummyBlogCount = '5';
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [MainPage, CardComponent, HeadersComponent, TableComponent, EmployeeFilterPipe],
            imports: [HttpClientTestingModule, IonicModule.forRoot(), RouterTestingModule, ReactiveFormsModule]
        }).compileComponents();

        fixture = TestBed.createComponent(MainPage);
        component = fixture.componentInstance;
        mockEmployeeService = TestBed.get(EmployeeActivityService);
        fixture.detectChanges();
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should return the sum of the work', () => {
        spyOn(mockEmployeeService, 'getData').and.returnValue(of(dummyAuthorData));
        component.ngOnInit();
        expect(component.getTotalCount('blogs')).toEqual(dummyBlogCount);
    });

    it('should return the authorData as per api call', () => {
        spyOn(mockEmployeeService, 'getData').and.returnValue(of(dummyAuthorData));
        component.ngOnInit();
        expect(component.employeeData).toEqual(dummyAuthorData);
    });

});
