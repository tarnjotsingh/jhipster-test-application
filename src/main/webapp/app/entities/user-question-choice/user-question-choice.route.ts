import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UserQuestionChoice } from 'app/shared/model/user-question-choice.model';
import { UserQuestionChoiceService } from './user-question-choice.service';
import { UserQuestionChoiceComponent } from './user-question-choice.component';
import { UserQuestionChoiceDetailComponent } from './user-question-choice-detail.component';
import { UserQuestionChoiceUpdateComponent } from './user-question-choice-update.component';
import { UserQuestionChoiceDeletePopupComponent } from './user-question-choice-delete-dialog.component';
import { IUserQuestionChoice } from 'app/shared/model/user-question-choice.model';

@Injectable({ providedIn: 'root' })
export class UserQuestionChoiceResolve implements Resolve<IUserQuestionChoice> {
    constructor(private service: UserQuestionChoiceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<UserQuestionChoice> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<UserQuestionChoice>) => response.ok),
                map((userQuestionChoice: HttpResponse<UserQuestionChoice>) => userQuestionChoice.body)
            );
        }
        return of(new UserQuestionChoice());
    }
}

export const userQuestionChoiceRoute: Routes = [
    {
        path: 'user-question-choice',
        component: UserQuestionChoiceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserQuestionChoices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-question-choice/:id/view',
        component: UserQuestionChoiceDetailComponent,
        resolve: {
            userQuestionChoice: UserQuestionChoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserQuestionChoices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-question-choice/new',
        component: UserQuestionChoiceUpdateComponent,
        resolve: {
            userQuestionChoice: UserQuestionChoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserQuestionChoices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-question-choice/:id/edit',
        component: UserQuestionChoiceUpdateComponent,
        resolve: {
            userQuestionChoice: UserQuestionChoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserQuestionChoices'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userQuestionChoicePopupRoute: Routes = [
    {
        path: 'user-question-choice/:id/delete',
        component: UserQuestionChoiceDeletePopupComponent,
        resolve: {
            userQuestionChoice: UserQuestionChoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserQuestionChoices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
