import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './components/app/app.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { RegistrationPageComponent } from './components/registration-page/registration-page.component';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MainPageComponent } from './components/main-page/main-page.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatNativeDateModule} from '@angular/material/core';
import {MatButtonModule} from '@angular/material/button';
import {MatMenuModule} from '@angular/material/menu';
import {MatIconModule} from '@angular/material/icon';
import {BasicAuthHttpInterceptorService} from './services/authentication/basic-auth-http-interceptor.service';
import { ProfilePageComponent } from './components/profile-page/profile-page.component';
import { PlacePageComponent } from './components/place-page/place-page.component';
import { AddPlaceFormComponent } from './components/add-place-form/add-place-form.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginPageComponent,
    RegistrationPageComponent,
    MainPageComponent,
    HeaderComponent,
    FooterComponent,
    ProfilePageComponent,
    PlacePageComponent,
    AddPlaceFormComponent
  ],
    imports: [
        MatMenuModule,
        MatIconModule,
        BrowserModule,
        BrowserAnimationsModule,
        FormsModule,
        AppRoutingModule,
        ReactiveFormsModule,
        HttpClientModule,
        MatCheckboxModule,
        MatNativeDateModule,
        MatButtonModule
    ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: BasicAuthHttpInterceptorService, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
