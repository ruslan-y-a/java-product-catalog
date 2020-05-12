package org.itstep.ui;

import java.util.Arrays;

import org.itstep.logic.LogicException;

public class ProductDeleteCommand extends ProductCommand {
	@Override
	public boolean exec(String[] args) throws LogicException {
		if(args.length == 1) {
			Long id = Long.valueOf(args[0]);
			getProductService().delete(Arrays.asList(id));
			System.out.println("Товар успешно удалён");
		} else {
			System.out.println("Неверное количество аргументов");
		}
		return true;
	}
}
