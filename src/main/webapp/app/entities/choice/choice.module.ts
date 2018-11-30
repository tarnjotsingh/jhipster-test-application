import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipexampleSharedModule } from 'app/shared';
import {
    ChoiceComponent,
    ChoiceDetailComponent,
    ChoiceUpdateComponent,
    ChoiceDeletePopupComponent,
    ChoiceDeleteDialogComponent,
    choiceRoute,
    choicePopupRoute
} from './';

const ENTITY_STATES = [...choiceRoute, ...choicePopupRoute];

@NgModule({
    imports: [JhipexampleSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ChoiceComponent, ChoiceDetailComponent, ChoiceUpdateComponent, ChoiceDeleteDialogComponent, ChoiceDeletePopupComponent],
    entryComponents: [ChoiceComponent, ChoiceUpdateComponent, ChoiceDeleteDialogComponent, ChoiceDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipexampleChoiceModule {}
