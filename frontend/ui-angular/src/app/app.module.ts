import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule, provideAnimations } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { CustomerComponent } from './components/customer/customer.component';
import { MenuBarComponent } from './components/menu-bar/menu-bar.component';
import { AvatarModule } from 'primeng/avatar';
import { AvatarGroupModule } from 'primeng/avatargroup';
import { MenuModule } from 'primeng/menu';
import { MenuItemComponent } from './components/menu-item/menu-item.component';
import { HeaderBarComponent } from './components/header-bar/header-bar.component';
import { ButtonModule } from 'primeng/button';
import { SidebarModule } from 'primeng/sidebar';
import { ManageCustomerComponent } from './components/manage-customer/manage-customer.component';
import { LoginComponent } from './components/login/login.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { MessagesModule } from 'primeng/messages';
import { HttpInterceptorService } from './services/interceptor/http-interceptor.service';
import { CustomerCardComponent } from './components/customer-card/customer-card.component';
import { CardModule } from 'primeng/card';
import { BadgeModule } from 'primeng/badge';

@NgModule({
  declarations: [
    AppComponent,
    CustomerComponent,
    MenuBarComponent,
    MenuItemComponent,
    HeaderBarComponent,
    ManageCustomerComponent,
    LoginComponent,
    CustomerCardComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    InputTextModule,
    AvatarModule,
    AvatarGroupModule,
    MenuModule,
    ButtonModule,
    SidebarModule,
    MessagesModule,
    CardModule,
    BadgeModule
  ],
  providers: [
    provideAnimations(),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
