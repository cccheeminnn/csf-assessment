import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PizzaService } from '../pizza.service';
import { ActivatedRoute } from '@angular/router';
import { OrdersResponse } from '../models';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  email!: string;
  orders!: OrdersResponse;
  message!: string;

  constructor( 
    private http: HttpClient,
    private pizzaSvc: PizzaService,
    private activatedRoute: ActivatedRoute ) { }

  ngOnInit(): void {
    this.email = this.activatedRoute.snapshot.params['email'];
    this.pizzaSvc.getOrders(this.email).then(results => {
      console.info('>>> get results: ', results)
      this.orders = results;
    }).catch(error => {
      console.info('>>> error getting orders: ', error.error.message)
      this.message = error.error.message;
    })
  }
}
