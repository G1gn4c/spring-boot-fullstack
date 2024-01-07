import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Message } from 'primeng/api';
import { AuthenticationRequest } from 'src/app/models/authentication-request';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  authenticationRequest: AuthenticationRequest = {};
  messages: Message[] = [];

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router
  ) {}

  login(): void {
    this.messages = [];
    this.authenticationService.login(this.authenticationRequest)
      .subscribe({
        next: (authenticationResponse) => {
          localStorage.setItem("user", JSON.stringify(authenticationResponse));
          this.router.navigate(["customers"]);          
        },
        error: (err) => {
          if (err.error.statusCode === 401) {
            this.messages = [
              { 
                severity: 'error', 
                summary: err.error.message, 
                detail: 'Username and/or password incorrect' 
              }
            ];
          }
        }
      });
  }

}
