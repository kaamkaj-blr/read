package com.kaamkaj.store;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.ami.model.Product;

/**
 * @author: Amit Khandelwal
 * Date: 2/26/17
 */

@RegisterMapper(ProductMapper.class)
public interface ProductDao {
	@SqlQuery("select * from product where id = :id")
	Product findById(@Bind("id") String id);

	@SqlQuery("select * from product")
	List<Product> findAll();
}
