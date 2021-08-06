package com.invoiceservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invoiceservice.model.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>{

}
