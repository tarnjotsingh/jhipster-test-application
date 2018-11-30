/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipexampleTestModule } from '../../../test.module';
import { UserQuestionChoiceUpdateComponent } from 'app/entities/user-question-choice/user-question-choice-update.component';
import { UserQuestionChoiceService } from 'app/entities/user-question-choice/user-question-choice.service';
import { UserQuestionChoice } from 'app/shared/model/user-question-choice.model';

describe('Component Tests', () => {
    describe('UserQuestionChoice Management Update Component', () => {
        let comp: UserQuestionChoiceUpdateComponent;
        let fixture: ComponentFixture<UserQuestionChoiceUpdateComponent>;
        let service: UserQuestionChoiceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipexampleTestModule],
                declarations: [UserQuestionChoiceUpdateComponent]
            })
                .overrideTemplate(UserQuestionChoiceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UserQuestionChoiceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserQuestionChoiceService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new UserQuestionChoice(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.userQuestionChoice = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new UserQuestionChoice();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.userQuestionChoice = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
