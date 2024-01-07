import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthenticationRequest } from 'src/app/models/authentication-request';
import { Observable } from 'rxjs';
import { AuthenticationResponse } from 'src/app/models/authentication-response';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private readonly authUrl: string  = `${environment.api.baseUrl}${environment.api.authUrl}`;

  constructor(private http: HttpClient) { }

  login(authenticationRequest: AuthenticationRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(this.authUrl, authenticationRequest);
  }
}
