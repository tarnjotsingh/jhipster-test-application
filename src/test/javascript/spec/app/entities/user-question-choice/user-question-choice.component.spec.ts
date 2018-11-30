/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipexampleTestModule } from '../../../test.module';
import { UserQuestionChoiceComponent } from 'app/entities/user-question-choice/user-question-choice.component';
import { UserQuestionChoiceService } from 'app/entities/user-question-choice/user-question-choice.service';
import { UserQuestionChoice } from 'app/shared/model/user-question-choice.model';

describe('Component Tests', () => {
    describe('UserQuestionChoice Management Component', () => {
        let comp: UserQuestionChoiceComponent;
        let fixture: ComponentFixture<UserQuestionChoiceComponent>;
        let service: UserQuestionChoiceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipexampleTestModule],
                declarations: [UserQuestionChoiceComponent],
                providers: []
            })
                .overrideTemplate(UserQuestionChoiceComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UserQuestionChoiceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserQuestionChoiceService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new UserQuestionChoice(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.userQuestionChoices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
