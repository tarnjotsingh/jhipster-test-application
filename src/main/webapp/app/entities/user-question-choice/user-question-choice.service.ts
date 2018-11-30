import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserQuestionChoice } from 'app/shared/model/user-question-choice.model';

type EntityResponseType = HttpResponse<IUserQuestionChoice>;
type EntityArrayResponseType = HttpResponse<IUserQuestionChoice[]>;

@Injectable({ providedIn: 'root' })
export class UserQuestionChoiceService {
    public resourceUrl = SERVER_API_URL + 'api/user-question-choices';

    constructor(private http: HttpClient) {}

    create(userQuestionChoice: IUserQuestionChoice): Observable<EntityResponseType> {
        return this.http.post<IUserQuestionChoice>(this.resourceUrl, userQuestionChoice, { observe: 'response' });
    }

    update(userQuestionChoice: IUserQuestionChoice): Observable<EntityResponseType> {
        return this.http.put<IUserQuestionChoice>(this.resourceUrl, userQuestionChoice, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IUserQuestionChoice>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUserQuestionChoice[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
