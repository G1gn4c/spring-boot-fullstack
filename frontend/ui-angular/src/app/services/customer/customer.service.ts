import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CustomerDTO } from 'src/app/models/customer-dto';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private readonly customerUrl: string  = `${environment.api.baseUrl}${environment.api.customerUrl}`;

  constructor(
    private http: HttpClient
  ) { }

  findAll(): Observable<Array<CustomerDTO>> {
    return this.http.get<Array<CustomerDTO>>(this.customerUrl);
  }

}
