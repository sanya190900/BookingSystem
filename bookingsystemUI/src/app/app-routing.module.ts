import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginPageComponent} from './components/login-page/login-page.component';
import {RegistrationPageComponent} from './components/registration-page/registration-page.component';
import {MainPageComponent} from './components/main-page/main-page.component';
import {ProfilePageComponent} from './components/profile-page/profile-page.component';
import {PlacePageComponent} from './components/place-page/place-page.component';
import {AddPlaceFormComponent} from './components/add-place-form/add-place-form.component';

const routes: Routes = [
  {
    path: '',
    component: MainPageComponent
  },
  {
    path: 'login',
    component: LoginPageComponent
  },
  {
    path: 'register',
    component: RegistrationPageComponent
  },
  {
    path: 'profile',
    component: ProfilePageComponent
  },
  {
    path: 'place',
    children: [
      {
        path: '',
        redirectTo: '/',
        pathMatch: 'full'
      },
      {
        path: 'new',
        component: AddPlaceFormComponent
      },
      {
        path: ':id',
        component: PlacePageComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
