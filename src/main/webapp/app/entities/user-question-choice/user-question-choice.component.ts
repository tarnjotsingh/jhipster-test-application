import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUserQuestionChoice } from 'app/shared/model/user-question-choice.model';
import { Principal } from 'app/core';
import { UserQuestionChoiceService } from './user-question-choice.service';

@Component({
    selector: 'jhi-user-question-choice',
    templateUrl: './user-question-choice.component.html'
})
export class UserQuestionChoiceComponent implements OnInit, OnDestroy {
    userQuestionChoices: IUserQuestionChoice[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private userQuestionChoiceService: UserQuestionChoiceService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.userQuestionChoiceService.query().subscribe(
            (res: HttpResponse<IUserQuestionChoice[]>) => {
                this.userQuestionChoices = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInUserQuestionChoices();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IUserQuestionChoice) {
        return item.id;
    }

    registerChangeInUserQuestionChoices() {
        this.eventSubscriber = this.eventManager.subscribe('userQuestionChoiceListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
