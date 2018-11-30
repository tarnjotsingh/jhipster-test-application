import { IQuestion } from 'app/shared/model//question.model';
import { IChoice } from 'app/shared/model//choice.model';

export interface IUserQuestionChoice {
    id?: number;
    timeStamp?: number;
    question?: IQuestion;
    choice?: IChoice;
}

export class UserQuestionChoice implements IUserQuestionChoice {
    constructor(public id?: number, public timeStamp?: number, public question?: IQuestion, public choice?: IChoice) {}
}
