import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserQuestionChoice } from 'app/shared/model/user-question-choice.model';
import { UserQuestionChoiceService } from './user-question-choice.service';

@Component({
    selector: 'jhi-user-question-choice-delete-dialog',
    templateUrl: './user-question-choice-delete-dialog.component.html'
})
export class UserQuestionChoiceDeleteDialogComponent {
    userQuestionChoice: IUserQuestionChoice;

    constructor(
        private userQuestionChoiceService: UserQuestionChoiceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userQuestionChoiceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'userQuestionChoiceListModification',
                content: 'Deleted an userQuestionChoice'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-question-choice-delete-popup',
    template: ''
})
export class UserQuestionChoiceDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userQuestionChoice }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(UserQuestionChoiceDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.userQuestionChoice = userQuestionChoice;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
