import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipexampleSharedModule } from 'app/shared';
import {
    SurveyComponent,
    SurveyDetailComponent,
    SurveyUpdateComponent,
    SurveyDeletePopupComponent,
    SurveyDeleteDialogComponent,
    surveyRoute,
    surveyPopupRoute
} from './';

const ENTITY_STATES = [...surveyRoute, ...surveyPopupRoute];

@NgModule({
    imports: [JhipexampleSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SurveyComponent, SurveyDetailComponent, SurveyUpdateComponent, SurveyDeleteDialogComponent, SurveyDeletePopupComponent],
    entryComponents: [SurveyComponent, SurveyUpdateComponent, SurveyDeleteDialogComponent, SurveyDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipexampleSurveyModule {}
