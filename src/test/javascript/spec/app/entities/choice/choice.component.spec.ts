/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipexampleTestModule } from '../../../test.module';
import { ChoiceComponent } from 'app/entities/choice/choice.component';
import { ChoiceService } from 'app/entities/choice/choice.service';
import { Choice } from 'app/shared/model/choice.model';

describe('Component Tests', () => {
    describe('Choice Management Component', () => {
        let comp: ChoiceComponent;
        let fixture: ComponentFixture<ChoiceComponent>;
        let service: ChoiceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipexampleTestModule],
                declarations: [ChoiceComponent],
                providers: []
            })
                .overrideTemplate(ChoiceComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ChoiceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChoiceService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Choice(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.choices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
