// import { AfterContentInit, Component, OnInit } from '@angular/core';
// import { AdminService } from '../../services/admin.service';

// @Component({
//   selector: 'app-customer',
//   templateUrl: './customer.component.html',
//   styleUrl: './customer.component.css'
// })
// export class CustomerComponent implements OnInit {

//   constructor(private service: AdminService){}

//   orders = [
//     {
//       billNumber: "",
//       itemName: ""
//     }
//   ]

//   pending_orders = [
//     {
//       billNumber: "",
//       itemName: ""
//     }
//   ]

//   ngOnInit(): void {
//     this.service.getCompletedOrders().subscribe(res =>{
//       this.orders = res;
//     },err=>{
//       console.log(err);
//     })
    
    
//     this.service.getPendingOrdersKitchen().subscribe(res =>{
//       this.pending_orders = res;
//     },err=>{
//       console.log(err);
//     })


//   }

// }




import { AfterContentInit, Component, OnDestroy, OnInit, Renderer2 } from '@angular/core';
import { AdminService } from '../../services/admin.service';

// Define the Order interface
interface Order {
  billNumber: string; // Ensure this matches the structure of your order
  itemName: string;
}

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit ,AfterContentInit {

  orders = [
    {
      billNumber: "",
      tempSaleId:""
    }
  ]

  orders2 = [
    {
      "tempSaleId": 0
    }
  ]

  pending_orders: any[] = []; // Initialize pending orders array

  constructor(private service: AdminService, private renderer: Renderer2) {}
  ngAfterContentInit(): void {
    this.makeFullscreen();
  }
 
  makeFullscreen(): void {
    const customerComponent = document.querySelector('app-customer');
    if (customerComponent) {
      // Add the 'fullscreen' class to make the component occupy the full screen
      this.renderer.addClass(customerComponent, 'fullscreen');
    }
  }

  ngOnInit(): void {
    // Fetch completed orders
    this.service.getCustomerCompletedOrders().subscribe(
      res => {
        this.orders = res
        this.orders2 = res
      }
    );
    setTimeout(() => {
      this.orders2.forEach(o=>{

          this.service.deleteTempSaleData(o).subscribe(res=>{
            console.log(res);
            this.ngOnInit()
          }, err=>{
            console.log(err);
          })
        
      })
      window.location.reload();
    }, 10000)

    // Fetch pending orders
    this.service.getPendingOrdersKitchen().subscribe(
      res => {
        this.pending_orders = res; // Store pending orders
      },
      err => {
        console.log(err);
      }
    );
  }
}