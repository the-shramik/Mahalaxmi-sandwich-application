package com.maven.pos.services.impl;

import com.maven.pos.entities.dto.SaleItemReport;
import com.maven.pos.repositories.ISaleRepository;
import com.maven.pos.services.ISaleItemReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SaleItemReportImpl implements ISaleItemReport {

    @Autowired
    private ISaleRepository saleRepository;

    @Override
    public List<SaleItemReport> getAllSaleReports() {
        List<SaleItemReport> saleItemReports = new ArrayList<>();
        saleRepository.findAll().forEach(sale -> {
            sale.getSaleItems().forEach(saleItem -> {
                SaleItemReport saleItemReport = new SaleItemReport();
                saleItemReport.setBillNumber(saleItem.getSale().getSaleId());
                saleItemReport.setItemName(saleItem.getItem().getItemName());
                saleItemReport.setQuantity(saleItem.getQuantity());
                saleItemReport.setParcelCharges(sale.getParcelCharges());
                saleItemReport.setExtraCharges(sale.getExtraCharges());
                saleItemReport.setSubTotal(sale.getSubTotal());
                saleItemReport.setFinalTotal(sale.getFinalTotal());
                saleItemReport.setPaymentMode(sale.getPaymentMode());
                saleItemReports.add(saleItemReport);
            });

        });
        return saleItemReports;
    }

    @Override
    public List<SaleItemReport> getCashSaleReports() {
        List<SaleItemReport> saleItemReports = new ArrayList<>();
        saleRepository.getCashSaleReports().forEach(sale->{
            sale.getSaleItems().forEach(saleItem -> {
                SaleItemReport saleItemReport = new SaleItemReport();
                saleItemReport.setBillNumber(saleItem.getSale().getSaleId());
                saleItemReport.setItemName(saleItem.getItem().getItemName());
                saleItemReport.setQuantity(saleItem.getQuantity());
                saleItemReport.setParcelCharges(sale.getParcelCharges());
                saleItemReport.setExtraCharges(sale.getExtraCharges());
                saleItemReport.setSubTotal(sale.getSubTotal());
                saleItemReport.setFinalTotal(sale.getFinalTotal());
                saleItemReport.setPaymentMode(sale.getPaymentMode());
                saleItemReports.add(saleItemReport);
            });
        });
        return saleItemReports;
    }

    @Override
    public List<SaleItemReport> getUpiSaleReports() {
        List<SaleItemReport> saleItemReports = new ArrayList<>();
        saleRepository.getUpiSaleReports().forEach(sale->{
            sale.getSaleItems().forEach(saleItem -> {
                SaleItemReport saleItemReport = new SaleItemReport();
                saleItemReport.setBillNumber(saleItem.getSale().getSaleId());
                saleItemReport.setItemName(saleItem.getItem().getItemName());
                saleItemReport.setQuantity(saleItem.getQuantity());
                saleItemReport.setParcelCharges(sale.getParcelCharges());
                saleItemReport.setExtraCharges(sale.getExtraCharges());
                saleItemReport.setSubTotal(sale.getSubTotal());
                saleItemReport.setFinalTotal(sale.getFinalTotal());
                saleItemReport.setPaymentMode(sale.getPaymentMode());
                saleItemReports.add(saleItemReport);
            });
        });
        return saleItemReports;
    }

    @Override
    public List<SaleItemReport> getDateSummaryUPISaleReports(LocalDate startDate, LocalDate endDate) {
        List<SaleItemReport> saleItemReports = new ArrayList<>();
        saleRepository.getDateUPISaleReports(startDate,endDate).forEach(sale->{
            sale.getSaleItems().forEach(saleItem -> {
                SaleItemReport saleItemReport = new SaleItemReport();
                saleItemReport.setBillNumber(saleItem.getSale().getSaleId());
                saleItemReport.setItemName(saleItem.getItem().getItemName());
                saleItemReport.setQuantity(saleItem.getQuantity());
                saleItemReport.setParcelCharges(sale.getParcelCharges());
                saleItemReport.setExtraCharges(sale.getExtraCharges());
                saleItemReport.setSubTotal(sale.getSubTotal());
                saleItemReport.setFinalTotal(sale.getFinalTotal());
                saleItemReport.setPaymentMode(sale.getPaymentMode());
                saleItemReports.add(saleItemReport);
            });
        });
        return saleItemReports;
    }

    @Override
    public List<SaleItemReport> getDateSummaryCashSaleReports(LocalDate startDate, LocalDate endDate) {
        List<SaleItemReport> saleItemReports = new ArrayList<>();
        saleRepository.getDateCashSaleReports(startDate,endDate).forEach(sale->{
            sale.getSaleItems().forEach(saleItem -> {
                SaleItemReport saleItemReport = new SaleItemReport();
                saleItemReport.setBillNumber(saleItem.getSale().getSaleId());
                saleItemReport.setItemName(saleItem.getItem().getItemName());
                saleItemReport.setQuantity(saleItem.getQuantity());
                saleItemReport.setParcelCharges(sale.getParcelCharges());
                saleItemReport.setExtraCharges(sale.getExtraCharges());
                saleItemReport.setSubTotal(sale.getSubTotal());
                saleItemReport.setFinalTotal(sale.getFinalTotal());
                saleItemReport.setPaymentMode(sale.getPaymentMode());
                saleItemReports.add(saleItemReport);
            });
        });
        return saleItemReports;
    }
}
