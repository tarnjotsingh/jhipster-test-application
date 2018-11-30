import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IChoice } from 'app/shared/model/choice.model';
import { Principal } from 'app/core';
import { ChoiceService } from './choice.service';

@Component({
    selector: 'jhi-choice',
    templateUrl: './choice.component.html'
})
export class ChoiceComponent implements OnInit, OnDestroy {
    choices: IChoice[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private choiceService: ChoiceService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.choiceService.query().subscribe(
            (res: HttpResponse<IChoice[]>) => {
                this.choices = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInChoices();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IChoice) {
        return item.id;
    }

    registerChangeInChoices() {
        this.eventSubscriber = this.eventManager.subscribe('choiceListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
