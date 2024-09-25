import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { AdminService } from '../../services/admin.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { DatePipe } from '@angular/common'; // Import DatePipe
import { Router } from '@angular/router';
import { MatPaginator } from '@angular/material/paginator';

export interface ProductsElements {
  billNumber: number;
  itemName: string;
  quantity: number;
  subTotal: number;
  extraCharges: number;
  parcelCharges: number;
  finalTotal: number;
  paymentMode: string;
}

@Component({
  selector: 'app-counter-order',
  templateUrl: './counter-order.component.html',
  styleUrls: ['./counter-order.component.css'],
  providers: [DatePipe],
})
export class CounterOrderComponent implements OnInit {
  displayedColumns: string[] = [
    'billNumber',
    'itemName',
    'quantity',
    'subTotal',
    'extraCharges',
    'parcelCharges',
    'finalTotal',
    'paymentMode',
  ];
  dataSource = new MatTableDataSource<ProductsElements>([]);
  form!: FormGroup;
  paymentMode: string = ''; // Add this variable to track the payment mode

  constructor(
    private service: AdminService,
    private formBuilder: FormBuilder,
    private toast: ToastrService,
    private router: Router,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
    });
  }

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  onPageChange(event: any) {
    console.log('Page changed:', event);
  }

  // Function to set the payment mode
  setPaymentMode(mode: string) {
    this.paymentMode = mode;
  }

  transformPurchaseData() {
    const { startDate, endDate } = this.form.value;

    const transformedData = {
      startDate: this.datePipe.transform(startDate, 'yyyy-MM-dd'),
      endDate: this.datePipe.transform(endDate, 'yyyy-MM-dd'),
      purchaseProducts: this.dataSource.data,
    };

    console.log('Transformed Data:', transformedData);

    return transformedData;
  }

  onSubmitSale() {
    console.log('Form Valid:', this.form.valid);
    console.log('Start Date:', this.form.get('startDate')?.value);
    console.log('End Date:', this.form.get('endDate')?.value);

    if (this.form.valid) {
      const transformedData = this.transformPurchaseData();

      if (this.paymentMode === 'cash') {
        this.service.salecashdatereports(transformedData).subscribe(
          (res) => this.handleResponse(res),
          (error) => this.handleError(error)
        );
      } else if (this.paymentMode === 'upi') {
        this.service.saleupidatereports(transformedData).subscribe(
          (res) => this.handleResponse(res),
          (error) => this.handleError(error)
        );
      } else {
        this.toast.warning('Please select a payment mode.');
      }
    } else {
      this.toast.warning('Please fill out all required fields.');
    }
  }

  handleResponse(res: any) {
    if (res && res.length > 0) {
      this.dataSource.data = res;
      this.toast.success('Data fetched successfully.', 'Success');
    } else {
      this.dataSource.data = [];
      this.toast.info('No data found for the selected dates.', 'Info');
    }
  }

  handleError(error: any) {
    console.error('Error occurred:', error);
    this.toast.error('Failed to fetch data. Please try again.', 'Error');
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
}
