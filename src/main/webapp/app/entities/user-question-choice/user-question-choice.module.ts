import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipexampleSharedModule } from 'app/shared';
import {
    UserQuestionChoiceComponent,
    UserQuestionChoiceDetailComponent,
    UserQuestionChoiceUpdateComponent,
    UserQuestionChoiceDeletePopupComponent,
    UserQuestionChoiceDeleteDialogComponent,
    userQuestionChoiceRoute,
    userQuestionChoicePopupRoute
} from './';

const ENTITY_STATES = [...userQuestionChoiceRoute, ...userQuestionChoicePopupRoute];

@NgModule({
    imports: [JhipexampleSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UserQuestionChoiceComponent,
        UserQuestionChoiceDetailComponent,
        UserQuestionChoiceUpdateComponent,
        UserQuestionChoiceDeleteDialogComponent,
        UserQuestionChoiceDeletePopupComponent
    ],
    entryComponents: [
        UserQuestionChoiceComponent,
        UserQuestionChoiceUpdateComponent,
        UserQuestionChoiceDeleteDialogComponent,
        UserQuestionChoiceDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipexampleUserQuestionChoiceModule {}
