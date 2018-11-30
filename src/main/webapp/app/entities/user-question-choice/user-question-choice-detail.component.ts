import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserQuestionChoice } from 'app/shared/model/user-question-choice.model';

@Component({
    selector: 'jhi-user-question-choice-detail',
    templateUrl: './user-question-choice-detail.component.html'
})
export class UserQuestionChoiceDetailComponent implements OnInit {
    userQuestionChoice: IUserQuestionChoice;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userQuestionChoice }) => {
            this.userQuestionChoice = userQuestionChoice;
        });
    }

    previousState() {
        window.history.back();
    }
}
