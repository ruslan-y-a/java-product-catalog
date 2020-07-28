package org.itstep.storage;

import java.util.List;

import org.itstep.domain.Product;

public interface ProductDao extends Dao<Product> {
	List<Product> read() throws DaoException;

	List<Product> readBySearchString(String search) throws DaoException;

	List<Product> readLatest() throws DaoException;
}
