import { HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthenticationResponse } from 'src/app/models/authentication-response';

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorService implements HttpInterceptor {

  constructor() { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const storedUser: string | null = localStorage.getItem("user");
    if (storedUser) {
      const authenticationResponse: AuthenticationResponse = JSON.parse(storedUser);
      const token: string | undefined = authenticationResponse.token;
      if (token) {
        const authRequest = req.clone({
          headers: new HttpHeaders({
            Authorization: `Bearer ${token}`
          })
        });
        return next.handle(authRequest);
      }
    }
    return next.handle(req);
  }
}
