import { FormArray } from '@angular/forms';
// Add your models here if you have any
export interface PizzaOrder {
    name: string,
    email: string,
    size: number,
    base: string,
    sauce: string,
    toppings: FormArray,
    comments?: string
}

export interface OrdersResponse {
    message: string,
    code: number,
    data: Order[]
}

export interface Order {
    orderId: number,
    name: string,
    email: string,
    amount: number
}