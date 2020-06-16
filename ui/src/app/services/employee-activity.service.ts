import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthorModel} from '../models/author.model';
import {environment} from '../../environments/environment';
import {KnolderDetailsModel} from '../models/knolder-details.model';

@Injectable({
    providedIn: 'root'
})
export class EmployeeActivityService {
    private url = `${environment.api.baseUrl}${environment.api.routes.author.endpoint}`;
    private knolderDetailApiUrl = environment.api.routes.details.endpoint;
    constructor(private httpClient: HttpClient) {
    }

    getData(): Observable<AuthorModel[]> {
        return this.httpClient.get<AuthorModel[]>(this.url);
    }

    getDetails(id: number): Observable<KnolderDetailsModel> {
        return this.httpClient.get<KnolderDetailsModel>(this.knolderDetailApiUrl);
    }

    getAllTimeDetails(id: number): Observable<KnolderDetailsModel> {
        return this.httpClient.get<KnolderDetailsModel>(this.url + '/' + id);
    }

    getMonthlyDetails(id: number, month: string, year: number): Observable<KnolderDetailsModel> {
        return this.httpClient.get<KnolderDetailsModel>(this.url + '/' + id + '?month=' + month + '&year=' + year);
    }
}
