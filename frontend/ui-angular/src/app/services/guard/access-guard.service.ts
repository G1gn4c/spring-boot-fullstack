import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';
import { AuthenticationResponse } from 'src/app/models/authentication-response';

@Injectable({
  providedIn: 'root'
})
export class AccessGuardService implements CanActivate {

  constructor(
    private router: Router
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    const storedUser: string | null = localStorage.getItem("user");
    if (storedUser) {
      const authenticationResponse: AuthenticationResponse = JSON.parse(storedUser);
      const token: string | undefined = authenticationResponse.token;
      if (token) {
        const jwtHelperService: JwtHelperService = new JwtHelperService();
        const isTokenNotExpired: boolean = !jwtHelperService.isTokenExpired(token);
        if (isTokenNotExpired) {
          return true;
        }
      }
    }
    this.router.navigate(["login"]);
    return false;
  }

}
