import { Component, OnInit } from '@angular/core';
import {PageEvent} from '@angular/material/paginator';
import {PlaceService} from '../../services/place/place.service';
import {PlaceModel} from '../../models/PlaceModel';
import {PlacesModel} from '../../models/PlacesModel';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss']
})
export class MainPageComponent implements OnInit {
  places: PlacesModel = new PlacesModel();

  searchForm: FormGroup = this.formBuilder.group({
    name: ['']
  });

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private placeService: PlaceService
  ) {
    //this.places = this.router.getCurrentNavigation()?.extras.state;
    console.log(history.state);
    if (!history.state.hasOwnProperty("content")) {
      this.placeService.getPlaces({page: 0, pageSize: 5}).subscribe(
        places => {
          this.places = places || [];
        },
        error => console.log(error)
      );
    } else
      this.places = history.state;
  }

  ngOnInit(): void {
  }

  pageEvent(event: PageEvent) {
    let searchModel = {
      page: event.pageIndex,
      pageSize: event.pageSize
    }

    this.placeService.getPlaces(searchModel).subscribe(
      places => this.places = places || [],
      error => console.log(error)
    );
  }

  search() {
    let searchModel = {
      page: 0,
      pageSize: 5,
      name: this.searchForm.value.name
    }

    this.placeService.getPlaces(searchModel).subscribe(
      places => this.places = places || [],
      error => console.log(error)
    );
  }
}
