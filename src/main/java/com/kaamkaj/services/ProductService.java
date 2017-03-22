package com.kaamkaj.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.util.IOUtils;
import com.ami.model.Product;
import com.kaamkaj.exceptions.EntityNotPresentException;
import com.kaamkaj.store.ProductDao;

/**
 * @author: Amit Khandelwal
 * Date: 2/27/17
 */

public class ProductService {
	private final org.slf4j.Logger log = LoggerFactory.getLogger(ProductService.class);

	private ProductDao productDao;
	private DynamoDB dynamoDB;
	private AmazonS3 s3Client;

	public static final String S3_BUCKET_NAME="ami-so";
	public static final String S3_REGION="ap-south-1";

	@Inject
	public ProductService(ProductDao productDao, DynamoDB dynamoDB, AmazonS3 amazonS3) {
		this.productDao = productDao;
		this.dynamoDB = dynamoDB;
		this.s3Client = amazonS3;
	}


	/**
	 * get a product using the unique id.
	 * @param id id
	 * @return product.
	 */
	public Product get(String id) throws EntityNotPresentException {
		Product product = productDao.findById(id);
		if(product == null){
			throw new EntityNotPresentException("product with id: "+id+ "not present");
		}
		return product;
	}

	/**
	 * get all product.
	 * @return product.
	 */
	public List<Product> get(){
		List<Product> products = productDao.findAll();
		return products;
	}

	public Product getDB(String id) {
		Table table = dynamoDB.getTable("product");
		try {

			Item item = table.getItem("id", "1");

			System.out.println("Printing item after retrieving it....");
			System.out.println(item.toJSONPretty());

			Product product = new Product();
			product.setId(item.getString("id"));
			product.setName(item.getString("name"));
			return product;
		} catch (Exception e) {
			System.err.println("GetItem failed.");
			System.err.println(e.getMessage());
		}
		return null;

	}

	public String createS3Object(InputStream fileInputStream, FormDataContentDisposition formDataContentDisposition) {
		String fileUrl;
		byte[] contents;
		try {
			contents = IOUtils.toByteArray(fileInputStream);
			InputStream stream = new ByteArrayInputStream(contents);

			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentLength(contents.length);
			meta.setContentType(formDataContentDisposition.getType());


			PutObjectResult putObjectResult = s3Client.putObject(new PutObjectRequest(
					"ami-so", formDataContentDisposition.getFileName(), stream, meta)
					.withCannedAcl(CannedAccessControlList.PublicRead));

			fileInputStream.close();
			fileUrl = "https://"+"s3."+S3_REGION+".amazonaws"
					+ ".com/"+S3_BUCKET_NAME+"/"+formDataContentDisposition.getFileName();
			return fileUrl;
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}
}
