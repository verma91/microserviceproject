package com.productservice.controller;

import java.text.DecimalFormat;
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

import com.productservice.model.Product;
import com.productservice.service.ProductServiceIntf;

@RestController
@RequestMapping("/products")
@Validated
public class ProductController {

	@Autowired
	ProductServiceIntf productServiceIntf;
	
	@PostMapping("/saveProduct")
	public ResponseEntity<String> saveProduct(@Valid @RequestBody Product product) {
		product.setCreationDate(new Date());
		productServiceIntf.saveProduct(product);
		return new ResponseEntity<String>("Product saved successfully", HttpStatus.CREATED);
	}
	
	@GetMapping("/getAllProducts")
	public ResponseEntity<List<Product>> findAll(){
		List<Product> products = productServiceIntf.findAllProducts();
		if(products.size()==0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(products,HttpStatus.OK);
	}
	
	@GetMapping("/findProductById/{productId}")
	public String findProductById(@PathVariable("productId") Long productId) throws JSONException {
		Product product = productServiceIntf.findProductById(productId);
		JSONObject jsonObject = new JSONObject();
		DecimalFormat df = new DecimalFormat("#.00");
		if(Objects.nonNull(product)) {
			jsonObject.put("productFound", true);
			jsonObject.put("productPrice", df.format(product.getProdPrice()));
		}
		else {
			jsonObject.put("productFound", false);
		}
		return jsonObject.toString();
	}
	
	@DeleteMapping("/deleteProductById/{productId}")
	public ResponseEntity<String> deleteProductById(@PathVariable("productId") Long productId){
		Product product = productServiceIntf.findProductById(productId);
		if(Objects.isNull(product)) {
			return new ResponseEntity<String>("Product is not available with this id :"+productId,HttpStatus.NOT_FOUND);
		}
		else {
			productServiceIntf.deleteProduct(product);
		}
		
		return new ResponseEntity<String>("Product is deleted successfully",HttpStatus.OK);
	}
}
