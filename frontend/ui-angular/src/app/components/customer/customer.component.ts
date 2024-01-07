import { Component, OnInit } from '@angular/core';
import { CustomerDTO } from 'src/app/models/customer-dto';
import { CustomerService } from 'src/app/services/customer/customer.service';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.scss']
})
export class CustomerComponent implements OnInit {

  display: boolean = false;
  customers: Array<CustomerDTO> = [];

  constructor(
    private customerService: CustomerService
  ) {}

  ngOnInit(): void {
    this.findAllCustomers();
  }

  private findAllCustomers(): void {
    this.customerService.findAll()
    .subscribe({
      next: (data) => {
        this.customers = data;
      }
    });
  }

}
