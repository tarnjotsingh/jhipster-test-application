/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipexampleTestModule } from '../../../test.module';
import { UserQuestionChoiceDetailComponent } from 'app/entities/user-question-choice/user-question-choice-detail.component';
import { UserQuestionChoice } from 'app/shared/model/user-question-choice.model';

describe('Component Tests', () => {
    describe('UserQuestionChoice Management Detail Component', () => {
        let comp: UserQuestionChoiceDetailComponent;
        let fixture: ComponentFixture<UserQuestionChoiceDetailComponent>;
        const route = ({ data: of({ userQuestionChoice: new UserQuestionChoice(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipexampleTestModule],
                declarations: [UserQuestionChoiceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(UserQuestionChoiceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserQuestionChoiceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.userQuestionChoice).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
