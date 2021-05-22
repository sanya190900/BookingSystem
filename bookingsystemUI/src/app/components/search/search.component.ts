import { Component, OnInit } from '@angular/core';
import {FormBuilder,FormGroup, Validators} from '@angular/forms';
import {PlaceService} from '../../services/place/place.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {
  searchForm: FormGroup = this.formBuilder.group({
    country: [''],
    city: [''],
    street: [''],
    creatorName: [''],
    creatorSurname: [''],
  });
  routerNavigation: number = 0;

  constructor(
    private formBuilder: FormBuilder,
    private placeService: PlaceService,
    private router: Router
  ) {
    this.routerNavigation = this.router.getCurrentNavigation()?.id || 0;
  }

  ngOnInit(): void {
  }

  search() {
    let searchModel = {
      page: 0,
      pageSize: 5,
      country: this.searchForm.value.country,
      city: this.searchForm.value.city,
      street: this.searchForm.value.street,
      creatorName: this.searchForm.value.creatorName,
      creatorSurname: this.searchForm.value.creatorSurname,
    }

    this.placeService.getPlaces(searchModel)
      .subscribe(
        value => {
          this.router.navigateByUrl('/' + (this.routerNavigation % 2 ? this.routerNavigation : ""), { state: value });
        },
        error => console.log(error)
      )
  }
}
