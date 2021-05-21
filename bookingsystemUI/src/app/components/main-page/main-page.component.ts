import { Component, OnInit } from '@angular/core';
import {PageEvent} from '@angular/material/paginator';
import {PlaceService} from '../../services/place/place.service';
import {PlaceModel} from '../../models/PlaceModel';
import {PlacesModel} from '../../models/PlacesModel';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss']
})
export class MainPageComponent implements OnInit {
  places: PlacesModel = new PlacesModel();

  constructor(private placeService: PlaceService) {
    this.placeService.getPlaces(0, 5).subscribe(
      places => {
        this.places = places || [];
      },
      error => console.log(error)
    );
  }

  ngOnInit(): void {
  }

  pageEvent(event: PageEvent) {
    this.placeService.getPlaces(event.pageIndex, event.pageSize).subscribe(
      places => this.places = places || [],
      error => console.log(error)
    );
  }

  search() {

  }
}
