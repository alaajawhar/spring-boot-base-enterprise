import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ApiListComponent} from './screens/api-list/api-list.component';
import {ApiDetailsComponent} from './screens/api-list/api-details/api-details.component';
import {FooterComponent} from "./layouts/footer/footer.component";
import {HeaderComponent} from "./layouts/header/header.component";
import {SidebarComponent} from "./layouts/sidebar/sidebar.component";
import {Backend} from "./services/backend";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    ApiListComponent,
    ApiDetailsComponent,
    FooterComponent,
    HeaderComponent,
    SidebarComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
  ],
  providers: [
    Backend,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
