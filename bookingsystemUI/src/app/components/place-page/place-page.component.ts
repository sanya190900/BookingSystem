import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {PlaceService} from '../../services/place/place.service';
import {PlaceModel} from '../../models/PlaceModel';
import {ScheduleModel} from '../../models/ScheduleModel';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import {CommentService} from '../../services/comment/comment.service';
import {FormControl, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-place-page',
  templateUrl: './place-page.component.html',
  styleUrls: ['./place-page.component.scss']
})
export class PlacePageComponent implements OnInit{
  @ViewChild('slideshow') slideshow: any;

  placeId: number = 0;
  place: PlaceModel = new PlaceModel();
  schedule: ScheduleModel[] = [new ScheduleModel()];
  photoUrls: string[] = [];
  form: FormGroup = new FormGroup({
    comment: new FormControl(null)
  });

  constructor(
    private activateRoute: ActivatedRoute,
    private router: Router,
    private placeService: PlaceService,
    private authenticationService: AuthenticationService,
    private commentService: CommentService
  ) {
    activateRoute.params.subscribe(value => {
      if(value.id > 0)
        this.placeId = value.id;

      placeService.getPlace(this.placeId).subscribe(
        place => {
          this.place = place
          console.log(this.place);
          this.schedule = [
            this.place.schedule.find( ({day}) => day?.day === "MONDAY") || new ScheduleModel(),
            this.place.schedule.find( ({day}) => day?.day === "TUESDAY") || new ScheduleModel(),
            this.place.schedule.find( ({day}) => day?.day === "WEDNESDAY") || new ScheduleModel(),
            this.place.schedule.find( ({day}) => day?.day === "THURSDAY") || new ScheduleModel(),
            this.place.schedule.find( ({day}) => day?.day === "FRIDAY") || new ScheduleModel(),
            this.place.schedule.find( ({day}) => day?.day === "SATURDAY") || new ScheduleModel(),
            this.place.schedule.find( ({day}) => day?.day === "SUNDAY") || new ScheduleModel(),
          ];
          this.photoUrls = this.place.gallery.map(gal => {return gal.pathToPhoto || ""});
        },
          error => console.log(error)
      );
    });
  }

  isLoggedIn(): boolean {
    return this.authenticationService.isUserLoggedIn();
  }

  sendComment() : void{
    this.commentService.addComment({place: this.place, comment: this.form.value.comment}).subscribe(
      value => {
        let currentUrl = this.router.url;
        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
          this.router.navigate([currentUrl]);
        });
      },
      error => console.log(error)
    );
  }

  ngOnInit(): void {
  }
}
