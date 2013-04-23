ADA.Q
=====

ADA.Q - A new way of thinking.

Before at ADA.Q

	String tableName = String.format("%s INNER JOIN %s ON (%s = %s)", 
		DataBase.Context.ProductsSet.getDataBaseTableName(), 
		Product.CATEGORIES_TABLE_NAME, 
		Product.PRODUCTS_ID_FIELD, 
		Product.CATEGORIES_ID_FIELD);
	DataBase.Context.ProductsSet.fill(tableName, wherePattern, whereValues, Product.DEFAULT_SORT, null, null);

With ADA.Q

	new Select()
		.from(Product.class)
		.innerJoin(Product.CATEGORIES_TABLE_NAME, Product.PRODUCTS_ID_FIELD, Product.CATEGORIES_ID_FIELD)
		.where(wherePattern, whereValues)
		.orderBy(Product.DEFAULT_SORT)
		.execute(DataBase.Context.ProductsSet);
			

Same result, different implementation, with ADA.Q our code It's more readable to all team developers.

Changelog
=========

v1.2
	
	Added Truncate Statment, this command trancate a database table.
	
	Example:
		new Truncate()
			.table(Product.class)
			.execute(DataBase.Context);
	
