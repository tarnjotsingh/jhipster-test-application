import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IUserQuestionChoice } from 'app/shared/model/user-question-choice.model';
import { UserQuestionChoiceService } from './user-question-choice.service';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from 'app/entities/question';
import { IChoice } from 'app/shared/model/choice.model';
import { ChoiceService } from 'app/entities/choice';

@Component({
    selector: 'jhi-user-question-choice-update',
    templateUrl: './user-question-choice-update.component.html'
})
export class UserQuestionChoiceUpdateComponent implements OnInit {
    userQuestionChoice: IUserQuestionChoice;
    isSaving: boolean;

    questions: IQuestion[];

    choices: IChoice[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private userQuestionChoiceService: UserQuestionChoiceService,
        private questionService: QuestionService,
        private choiceService: ChoiceService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ userQuestionChoice }) => {
            this.userQuestionChoice = userQuestionChoice;
        });
        this.questionService.query().subscribe(
            (res: HttpResponse<IQuestion[]>) => {
                this.questions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.choiceService.query().subscribe(
            (res: HttpResponse<IChoice[]>) => {
                this.choices = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.userQuestionChoice.id !== undefined) {
            this.subscribeToSaveResponse(this.userQuestionChoiceService.update(this.userQuestionChoice));
        } else {
            this.subscribeToSaveResponse(this.userQuestionChoiceService.create(this.userQuestionChoice));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUserQuestionChoice>>) {
        result.subscribe((res: HttpResponse<IUserQuestionChoice>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackQuestionById(index: number, item: IQuestion) {
        return item.id;
    }

    trackChoiceById(index: number, item: IChoice) {
        return item.id;
    }
}
