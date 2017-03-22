package com.kaamkaj.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ami.model.AppError;
import com.ami.model.BaseResponse;
import com.ami.model.Product;
import com.codahale.metrics.annotation.Timed;
import com.kaamkaj.exceptions.EntityNotPresentException;
import com.kaamkaj.services.ProductService;

/**
 * @author: Amit Khandelwal.
 * Date: 2/26/17
 */

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {
	private static final Logger log = LoggerFactory.getLogger(ProductResource.class);

	private final ProductService productService;

	@Inject
	public ProductResource(ProductService productService){
		this.productService = productService;
	}


	@GET
	@Path("{id}")
	@Timed
	public Response get(@PathParam("id") String id) {
		Response response = null;
		BaseResponse productResponse = new BaseResponse();
		try {
			Product product = productService.get(id);
			productResponse.setOutput(product);
			response = response.status(200).entity(productResponse).build();
		} catch (EntityNotPresentException e) {
			AppError appError = new AppError();
			appError.setCode(50001);
			appError.setMessage(e.getMessage());
			productResponse.setError(appError);
			response = response.status(500).entity(productResponse).build();
		}

		return response;
	}

	@GET
	@Timed
	public Response get() {
		Response response = null;
		BaseResponse productResponse = new BaseResponse();
		try {
			List<Product> products = productService.get();
			productResponse.setOutput(products);
			response = response.status(200).entity(productResponse).build();
		} catch (Exception e) {
			AppError appError = new AppError();
			appError.setCode(50001);
			appError.setMessage(e.getMessage());
			productResponse.setError(appError);
			response = response.status(500).entity(productResponse).build();
		}

		return response;
	}

	@GET
	@Timed
	@Path("/db/{id}")
	public Response getDB(@PathParam("id") String id) {
		Response response = null;
		BaseResponse productResponse = new BaseResponse();
		try {
			Product product = productService.getDB(id);
			productResponse.setOutput(product);
			response = response.status(200).entity(productResponse).build();
		} catch (EntityNotPresentException e) {
			AppError appError = new AppError();
			appError.setCode(50001);
			appError.setMessage(e.getMessage());
			productResponse.setError(appError);
			response = response.status(500).entity(productResponse).build();
		}

		return response;
	}

}
