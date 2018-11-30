import { IQuestion } from 'app/shared/model//question.model';

export interface ISurvey {
    id?: number;
    description?: string;
    surveys?: IQuestion[];
}

export class Survey implements ISurvey {
    constructor(public id?: number, public description?: string, public surveys?: IQuestion[]) {}
}
