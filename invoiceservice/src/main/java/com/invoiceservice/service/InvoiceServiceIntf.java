package com.invoiceservice.service;


import java.util.List;

import com.invoiceservice.model.Invoice;

public interface InvoiceServiceIntf {

	void saveInvoice(Invoice invoice);

	List<Invoice> findAllInvoices();

	Invoice findInvoiceById(Long invoiceId);

	void deleteInvoice(Invoice invoice);

}
