package com.invoiceservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoiceservice.dao.InvoiceRepository;
import com.invoiceservice.model.Invoice;

@Service
public class InvoiceServiceImpl implements InvoiceServiceIntf {

	@Autowired
	InvoiceRepository invoiceRepository;
	public void saveInvoice(Invoice invoice) {
		invoiceRepository.save(invoice);
		
	}

	public List<Invoice> findAllInvoices() {
		List<Invoice> invoices = invoiceRepository.findAll();
		return invoices;
	}

	public Invoice findInvoiceById(Long invoiceId) {
		Optional<Invoice> invoice = invoiceRepository.findById(invoiceId);
		if(invoice.isPresent()) {
			return invoice.get();
		}
		return null;
	}

	public void deleteInvoice(Invoice invoice) {
		invoiceRepository.delete(invoice);
		
	}

}
