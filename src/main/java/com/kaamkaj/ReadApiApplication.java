package com.kaamkaj;


import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.skife.jdbi.v2.DBI;

import com.kaamkaj.clients.aws.dynamodb.DynamoDBClient;
import com.kaamkaj.clients.aws.s3.S3Client;
import com.kaamkaj.resources.ImageResource;
import com.kaamkaj.resources.ProductResource;
import com.kaamkaj.services.ProductService;
import com.kaamkaj.store.ProductDao;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ReadApiApplication extends Application<ReadApiConfiguration> {

   // private GuiceBundle<ReadApiConfiguration> guiceBundle;

    public static void main(final String[] args) throws Exception {
        new ReadApiApplication().run(args);
    }

    @Override
    public String getName() {
        return "readapi";
    }

    @Override
    public void initialize(final Bootstrap<ReadApiConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final ReadApiConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application

        // DB
        DBIFactory factory = new DBIFactory();
        DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");
        ProductDao productDao = jdbi.onDemand(ProductDao.class);

        DynamoDBClient dynamoDBClient = new DynamoDBClient(configuration.getDynamoDBConfig().getRegion());

        S3Client s3Client = new S3Client(configuration);

        // Services
        final ProductService productService = new ProductService(productDao,dynamoDBClient
                .getDynamoDBDocClient(),s3Client.getS3Client());

        // resources
        environment.jersey().register(new ProductResource(productService));
        environment.jersey().register(new ImageResource(productService));
        environment.jersey().register(MultiPartFeature.class);

    }

}
