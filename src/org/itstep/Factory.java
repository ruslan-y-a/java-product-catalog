package org.itstep;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.itstep.logic.CategoryService;
import org.itstep.logic.CategoryServiceImpl;
import org.itstep.logic.LogicException;
import org.itstep.logic.ProductService;
import org.itstep.logic.ProductServiceImpl;
import org.itstep.logic.UserService;
import org.itstep.logic.UserServiceImpl;
import org.itstep.storage.CategoryDao;
import org.itstep.storage.ProductDao;
import org.itstep.storage.UserDao;
import org.itstep.storage.postgres.CategoryDbDaoImpl;
import org.itstep.storage.postgres.ProductDbDaoImpl;
import org.itstep.storage.postgres.UserDbDaoImpl;
import org.itstep.ui.Command;
import org.itstep.ui.ProductDeleteCommand;
import org.itstep.ui.ProductListCommand;
import org.itstep.ui.ProductSaveCommand;

public class Factory implements AutoCloseable {
	private Command productDeleteCommand = null;
	public Command getProductDeleteCommand() throws LogicException {
		if(productDeleteCommand == null) {
			ProductDeleteCommand command = new ProductDeleteCommand();
			productDeleteCommand = command;
			command.setProductService(getProductService());
		}
		return productDeleteCommand;
	}

	private Command productListCommand = null;
	public Command getProductListCommand() throws LogicException {
		if(productListCommand == null) {
			ProductListCommand command = new ProductListCommand();
			productListCommand = command;
			command.setProductService(getProductService());
		}
		return productListCommand;
	}

	private Command productSaveCommand = null;
	public Command getProductSaveCommand() throws LogicException {
		if(productSaveCommand == null) {
			ProductSaveCommand command = new ProductSaveCommand();
			productSaveCommand = command;
			command.setProductService(getProductService());
		}
		return productSaveCommand;
	}

	private CategoryService categoryService = null;
	public CategoryService getCategoryService() throws LogicException {
		if(categoryService == null) {
			CategoryServiceImpl service = new CategoryServiceImpl();
			categoryService = service;
			service.setCategoryDao(getCategoryDao());
		}
		return categoryService;
	}

	private ProductService productService = null;
	public ProductService getProductService() throws LogicException {
		if(productService == null) {
			ProductServiceImpl service = new ProductServiceImpl();
			productService = service;
			service.setProductDao(getProductDao());
			service.setCategoryDao(getCategoryDao());
		}
		return productService;
	}

	private UserService userService = null;
	public UserService getUserService() throws LogicException {
		if(userService == null) {
			UserServiceImpl service = new UserServiceImpl();
			userService = service;
			service.setUserDao(getUserDao());
		}
		return userService;
	}

	private ProductDao productDao = null;
	public ProductDao getProductDao() throws LogicException {
		if(productDao == null) {
			ProductDbDaoImpl productDaoImpl = new ProductDbDaoImpl();
			productDao = productDaoImpl;
			productDaoImpl.setConnection(getConnection());
		}
		return productDao;
	}


	private CategoryDao categoryDao = null;
	public CategoryDao getCategoryDao() throws LogicException {
		if(categoryDao == null) {
			CategoryDbDaoImpl categoryDaoImpl = new CategoryDbDaoImpl();
			categoryDao = categoryDaoImpl;
			categoryDaoImpl.setConnection(getConnection());
		}
		return categoryDao;
	}

	private UserDao userDao = null;
	public UserDao getUserDao() throws LogicException {
		if(userDao == null) {
			UserDbDaoImpl userDaoImpl = new UserDbDaoImpl();
			userDao = userDaoImpl;
			userDaoImpl.setConnection(getConnection());
		}
		return userDao;
	}

	private Connection connection = null;
	public Connection getConnection() throws LogicException {
		if(connection == null) {
			try {
				connection = DriverManager.getConnection("jdbc:postgresql://localhost/store_db", "root", "root");
			} catch(SQLException e) {
				throw new LogicException(e);
			}
		}
		return connection;
	}

	@Override
	public void close() {
		try { connection.close(); } catch(Exception e) {}
	}
}
