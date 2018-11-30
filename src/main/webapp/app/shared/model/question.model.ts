import { IChoice } from 'app/shared/model//choice.model';
import { ISurvey } from 'app/shared/model//survey.model';
import { IUserQuestionChoice } from 'app/shared/model//user-question-choice.model';

export interface IQuestion {
    id?: number;
    question?: string;
    questions?: IChoice[];
    survey?: ISurvey;
    userQuestionChoices?: IUserQuestionChoice[];
}

export class Question implements IQuestion {
    constructor(
        public id?: number,
        public question?: string,
        public questions?: IChoice[],
        public survey?: ISurvey,
        public userQuestionChoices?: IUserQuestionChoice[]
    ) {}
}
