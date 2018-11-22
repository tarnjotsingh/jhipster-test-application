import { NgModule } from '@angular/core';

import { JhipexampleSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [JhipexampleSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [JhipexampleSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class JhipexampleSharedCommonModule {}
