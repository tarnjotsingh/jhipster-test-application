import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipexampleUserQuestionChoiceModule } from './user-question-choice/user-question-choice.module';
import { JhipexampleChoiceModule } from './choice/choice.module';
import { JhipexampleQuestionModule } from './question/question.module';
import { JhipexampleSurveyModule } from './survey/survey.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JhipexampleUserQuestionChoiceModule,
        JhipexampleChoiceModule,
        JhipexampleQuestionModule,
        JhipexampleSurveyModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipexampleEntityModule {}
