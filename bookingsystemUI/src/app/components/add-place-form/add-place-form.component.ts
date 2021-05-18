import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import {PlaceModel} from '../../models/PlaceModel';
import {PlaceService} from '../../services/place/place.service';

@Component({
  selector: 'app-add-place-form',
  templateUrl: './add-place-form.component.html',
  styleUrls: ['./add-place-form.component.scss']
})
export class AddPlaceFormComponent implements OnInit {
  servicesControl = new FormControl();
  services: string[] = ['WiFi', 'TV', 'Pool', 'Bar', 'Shower', 'Transfer',
    'Breakfast', 'Fitness', 'Parking', 'Animals', 'Excursion'];
  addPlaceForm: FormGroup = this.formBuilder.group({
    name: ['', Validators.required],
    description: ['', Validators.required],
    photo1: [''], photo2: [''], photo3: [''], photo4: [''],
    time1Start: [''], time2Start: [''], time3Start: [''],  time4Start: [''], time5Start: [''], time6Start: [''], time7Start: [''],
    time1Stop: [''], time2Stop: [''], time3Stop: [''],  time4Stop: [''], time5Stop: [''], time6Stop: [''], time7Stop: [''],
    price1: [''], price2: [''], price3: [''],  price4: [''], price5: [''], price6: [''], price7: [''],
    working1: [''], working2: [''], working3: [''],  working4: [''], working5: [''], working6: [''], working7: [''],
    country: ['', Validators.required],
    city: ['', Validators.required],
    street: ['', Validators.required],
    house_number: ['', Validators.required]
  });
  loading = false;
  submitted = false;
  placeModel = new PlaceModel();

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authenticationService: AuthenticationService,
    private placeService: PlaceService,
  ) {
  }

  ngOnInit() {
  }

  get f() { return this.addPlaceForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.addPlaceForm.invalid) {
      return;
    }

    let schedules = [
      !this.f.working1.value ? JSON.stringify({start: this.f.time1Start.value, stop: this.f.time1Stop.value, day: "monday", price: this.f.price1.value}) : "",
      !this.f.working2.value ? JSON.stringify({start: this.f.time2Start.value, stop: this.f.time2Stop.value, day: "tuesday", price: this.f.price2.value}) : "",
      !this.f.working3.value ? JSON.stringify({start: this.f.time3Start.value, stop: this.f.time3Stop.value, day: "wednesday", price: this.f.price3.value}) : "",
      !this.f.working4.value ? JSON.stringify({start: this.f.time4Start.value, stop: this.f.time4Stop.value, day: "thursday", price: this.f.price4.value}) : "",
      !this.f.working5.value ? JSON.stringify({start: this.f.time5Start.value, stop: this.f.time5Stop.value, day: "friday", price: this.f.price5.value}) : "",
      !this.f.working6.value ? JSON.stringify({start: this.f.time6Start.value, stop: this.f.time6Stop.value, day: "saturday", price: this.f.price6.value}) : "",
      !this.f.working7.value ? JSON.stringify({start: this.f.time7Start.value, stop: this.f.time7Stop.value, day: "sunday", price: this.f.price7.value}) : ""
    ]

    schedules = schedules.filter(value => value != "")
    console.log(schedules);

    this.loading = true;
    this.placeModel = {
      name: this.f.name.value,
      description: this.f.description.value,
      address: {
        country: this.f.country.value,
        city: this.f.city.value,
        street: this.f.street.value,
        house_number: this.f.house_number.value
      },
      service: this.servicesControl.value.map((service: string) => service.toLowerCase()),
      pathsToPhotos: [
        this.f.photo1.value,
        this.f.photo2.value,
        this.f.photo3.value,
        this.f.photo4.value,
      ],
      schedules: schedules.map(value => JSON.parse(value))
    };

    console.log(JSON.stringify(this.placeModel));

    this.placeService.addPlace(this.placeModel)
      .subscribe(
        data => {
          this.router.navigate(['/']);
        },
        error => {
          console.log(error);
          this.loading = false;
        });
  }

}
