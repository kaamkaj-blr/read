package com.kaamkaj.resources;

import java.io.InputStream;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.kaamkaj.services.ProductService;

/**
 * @author: Amit Khandelwal
 * Date: 3/22/17
 */

@Path("/images")
public class ImageResource {

	private ProductService productService;

	@Inject
	public ImageResource(ProductService productService) {
		this.productService = productService;
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public Response uploadFile(
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {

		// save the file to the S3
		String url = productService.createS3Object(fileInputStream, contentDispositionHeader);
		return Response.status(201).entity(url).build();

	}

	/*// save uploaded file to a defined location on the server
	private void saveFile(InputStream uploadedInputStream,
			String serverLocation) {

		try {
			OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			outpuStream = new FileOutputStream(new File(serverLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outpuStream.write(bytes, 0, read);
			}
			outpuStream.flush();
			outpuStream.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}*/

}
