import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';

import { PizzaOrder, OrdersResponse } from './models';
// Implement the methods in PizzaService for Task 3
// Add appropriate parameter and return type 

@Injectable()
export class PizzaService {

  constructor( 
    private http: HttpClient ) { }

  // POST /api/order
  // Add any required parameters or return type
  createOrder(pizzaOrder: PizzaOrder) { 
    const headers = new HttpHeaders()
      .set('Accept', 'application/json')
      .set('Content-Type', 'application/json');

    return firstValueFrom(
      this.http.post('/api/order/', pizzaOrder, { headers })
    )
}

  // GET /api/order/<email>/all
  // Add any required parameters or return type
  getOrders(email: string):Promise<OrdersResponse>{ 
    return firstValueFrom(
      this.http.get<OrdersResponse>(`/api/order/${email}/all`)
    )
  }

}
