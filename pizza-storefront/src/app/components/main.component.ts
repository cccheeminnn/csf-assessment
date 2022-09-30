import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { Router } from '@angular/router'

import { PizzaOrder } from '../models';
import { PizzaService } from '../pizza.service';

const SIZES: string[] = [
  "Personal - 6 inches",
  "Regular - 9 inches",
  "Large - 12 inches",
  "Extra Large - 15 inches"
]

const PizzaToppings: string[] = [
    'chicken', 'seafood', 'beef', 'vegetables',
    'cheese', 'arugula', 'pineapple'
]

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  formGrp!: FormGroup;
  toppingsArray!: FormArray;

  pizzaSize = SIZES[0]

  constructor(
    private fb: FormBuilder,
    private pizzaSvc: PizzaService,
    private router: Router) { }

  ngOnInit(): void {
    this.resetForm();
  }

  resetForm() {
    this.toppingsArray = this.fb.array([], [ Validators.required, Validators.minLength(1) ]);
    this.formGrp = this.fb.group({
      name: this.fb.control<string>('', [ Validators.required, Validators.minLength(1) ]),
      email: this.fb.control<string>('', [ Validators.required, Validators.email ]),
      size: this.fb.control<number>(0, [ Validators.required ]),
      base: this.fb.control<string>('thin', [ Validators.required ]),
      sauce: this.fb.control<string>('classic', [ Validators.required ]),
      toppings: this.toppingsArray,
      comments: this.fb.control<string>('')
    })
  }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)]
  }

  updateToppingsArray(event: Event) {
    let inputEle = event.target as HTMLInputElement;
    let toppingStr:string = inputEle.value;
    const toppingFormGroup = this.fb.group({
      topping: toppingStr
    })
    if (inputEle.checked) {
      this.toppingsArray.push(toppingFormGroup);
    } else {
      for (let i = 0; i < this.toppingsArray.length; i++) {
        if(this.toppingsArray.controls[i].value['topping'] == inputEle.value){
          this.toppingsArray.removeAt(i);
        }
      }
    }
  }

  onSubmitForm() {
    const pizzaOrder: PizzaOrder = this.formGrp.value as PizzaOrder;
    console.info('>>> Submit btn pressed', pizzaOrder);
    this.pizzaSvc.createOrder(pizzaOrder)
      .then(results => {
        this.router.navigate([`/orders/${this.formGrp.controls['email'].value}`]);
      }
    );
  }

  listOrders() {
    this.router.navigate([`/orders/${this.formGrp.controls['email'].value}`]);
  }

}
