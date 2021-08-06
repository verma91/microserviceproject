package com.invoiceservice.controller;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoiceservice.model.Invoice;
import com.invoiceservice.service.InvoiceServiceIntf;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/invoices")
@Validated
public class InoviceServiceController {

	@Autowired
	InvoiceServiceIntf invoiceServiceIntf;
	
	@Autowired 
	InvoiceFeignClient invoiceFeignClient;
	
	@PostMapping("/saveInvoice/{orderId}")
	public ResponseEntity<Object> saveInvoice(@Valid @RequestBody Invoice invoice, @PathVariable("orderId") Long orderId)
			throws JSONException {
		JSONObject jsonObject = new JSONObject(invoiceFeignClient.findOrderByIdResponse(orderId));
		
		
		if (!jsonObject.getBoolean("orderFound") ) {
			
			
			return new ResponseEntity<>("Invoice cannot be generated for order with id : " + orderId,HttpStatus.NOT_FOUND);
		}
		else if(jsonObject.get("orderStatus").equals("cancelled")) {
			return new ResponseEntity<>("Invoice cannot be generated as order is already cancelled with id : " + orderId,HttpStatus.BAD_REQUEST);
		}
		else if(!jsonObject.getBoolean("productFound")) {
			return new ResponseEntity<>("Invoice cannot be generated for order with id : " + orderId +" as product is already deleted",HttpStatus.NOT_FOUND);
		}
		else {
			int orderQty = (int) jsonObject.get("orderQuantity");
			float prdtPrice = Float.valueOf((String)jsonObject.get("productPrice"));
			System.out.println(orderQty+" "+prdtPrice);
			float amount = orderQty * prdtPrice;
			//Float result = (Float)((Float)jsonObject.get("orderQuantity")*(Integer)jsonObject.get("orderQuantity"));
			invoice.setAmountPaid(amount);
			invoice.setInvoiceGenerationDate(new Date());
			invoice.setOrderId(orderId);
			invoiceServiceIntf.saveInvoice(invoice);
			
		}
		
		return new ResponseEntity<Object>(invoice, HttpStatus.OK);

	}

	
	@DeleteMapping("/deleteInvoiceById/{invoiceId}")
	public ResponseEntity<String> deleteOrderById(@PathVariable("invoiceId") Long invoiceId){
		Invoice invoice = invoiceServiceIntf.findInvoiceById(invoiceId);
		if(Objects.isNull(invoice)) {
			return new ResponseEntity<String>("Invoice is not available with this id :"+invoiceId,HttpStatus.NOT_FOUND);
		}
		else {
			invoiceServiceIntf.deleteInvoice(invoice);
		}
		
		return new ResponseEntity<String>("Invoice is deleted successfully",HttpStatus.OK);
	}
	
	@GetMapping("findAllInvoices")
	public ResponseEntity<List<Invoice>> findAllInvoices(){
		List<Invoice> invoices = invoiceServiceIntf.findAllInvoices();
		if(invoices.size()==0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Invoice>>(invoices,HttpStatus.OK);
	}
}
