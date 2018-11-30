import { IUserQuestionChoice } from 'app/shared/model//user-question-choice.model';
import { IQuestion } from 'app/shared/model//question.model';

export interface IChoice {
    id?: number;
    choice?: string;
    weight?: number;
    userQuestionChoices?: IUserQuestionChoice[];
    question?: IQuestion;
}

export class Choice implements IChoice {
    constructor(
        public id?: number,
        public choice?: string,
        public weight?: number,
        public userQuestionChoices?: IUserQuestionChoice[],
        public question?: IQuestion
    ) {}
}
