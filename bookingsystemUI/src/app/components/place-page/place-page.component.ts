import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {PlaceService} from '../../services/place/place.service';
import {PlaceModel} from '../../models/PlaceModel';
import {ScheduleModel} from '../../models/ScheduleModel';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import {CommentService} from '../../services/comment/comment.service';
import {FormControl, FormGroup} from '@angular/forms';
import {BookingService} from '../../services/booking/booking.service';
import {UserService} from '../../services/user/user.service';
import {formatDate} from '@angular/common';

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

  form2: FormGroup = new FormGroup({
    start: new FormControl(null),
    startDate: new FormControl(null),
    stop: new FormControl(null),
    stopDate: new FormControl(null),
    message: new FormControl(null)
});


  constructor(
    private activateRoute: ActivatedRoute,
    private router: Router,
    private placeService: PlaceService,
    private authenticationService: AuthenticationService,
    private commentService: CommentService,
    private bookingService: BookingService,
    private userService: UserService
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

  sendComment() : void {
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

  makeReservation() : void {
    this.userService.getUser().subscribe(user => {
        let messageUser = "Your reservation has been sent to manager " +
          this.place.user?.name + " " + this.place.user?.surname + "(" + this.place.user?.email + ") of place " + this.place.name + ".<br />" +
          "Reservation parameters:<br />" +
          "From: " + formatDate(this.form2.value.startDate, 'yyyy-MM-dd', 'en-US') + " " + this.form2.value.start + "<br />" +
          "To: " + formatDate(this.form2.value.stopDate, 'yyyy-MM-dd', 'en-US') + " " + this.form2.value.stop + "<br />" +
          "Manager will send you a reply abour your reservation request.<br />" + "Your Booking system!";

        let messageManager = "You received a reservation request of place " + this.place.name + ".<br />" +
          "Reservation parameters:<br />" +
          "Customer: " + user.name + " " + user.surname + " (" + user.email + ")<br />" +
          "From: " + formatDate(this.form2.value.startDate, 'yyyy-MM-dd', 'en-US') + " " + this.form2.value.start + "<br />" +
          "To: " + formatDate(this.form2.value.stopDate, 'yyyy-MM-dd', 'en-US') + " " + this.form2.value.stop + "<br />" +
          "Message from customer: " + this.form2.value.message + "<br />" +
          "Please send a reply to customer on email.<br />" + "Your Booking system!";

        this.bookingService.reserve(
          {
            messageToManager: messageManager,
            messageToCustomer: messageUser,
            emailManager: this.place.user?.email,
            emailCustomer: user.email
          }
        ).subscribe(
          value => {},
          error => console.log(error)
        );
    },
        error => console.log(error));
  }

  ngOnInit(): void {
  }
}
