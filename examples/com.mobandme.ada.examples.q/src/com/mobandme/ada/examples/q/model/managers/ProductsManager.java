/**
   Copyright Mob&Me 2013 (@MobAndMe)

   Licensed under the GPL General Public License, Version 3.0 (the "License"),  
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.gnu.org/licenses/gpl.html

   Unless required by applicable law or agreed to in writing, software 
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   
   Website: http://adaframework.com
   Contact: Txus Ballesteros <txus.ballesteros@mobandme.com>
*/

package com.mobandme.ada.examples.q.model.managers;

import com.mobandme.ada.Entity;
import com.mobandme.ada.examples.q.model.context.DataBase;
import com.mobandme.ada.examples.q.model.entities.Product;
import com.mobandme.ada.exceptions.AdaFrameworkException;
import com.mobandme.ada.q.Delete;
import com.mobandme.ada.q.Insert;
import com.mobandme.ada.q.Select;
import com.mobandme.ada.q.Update;

/**
 * Products data manager.
 * @author Txus Ballesteros (@DesAndrOId)
 * @category Manager
 * @version 2.4
 */
public class ProductsManager extends Manager {

	public void fillProducts(String pCategoryID, String pProductName) throws AdaFrameworkException {
		if (pCategoryID != null && !pCategoryID.trim().equals("")) {
			String wherePattern = null;
			String[] whereValues = null;
			
			if (pProductName != null && !pProductName.trim().equals("")) {
				wherePattern = Product.CATEGORIES_NAME_FILTER_PATTERN;
				whereValues = new String[] { pCategoryID, String.format("%%%s%%", pProductName) };
			} else {
				wherePattern = Product.CATEGORIES_FILTER_PATTERN;
				whereValues = new String[] { pCategoryID };
			}
			
			/* BEFORE ADA.Q */
			//String tableName = String.format("%s INNER JOIN %s ON (%s = %s)", DataBase.Context.ProductsSet.getDataBaseTableName(), Product.CATEGORIES_TABLE_NAME, Product.PRODUCTS_ID_FIELD, Product.CATEGORIES_ID_FIELD);
			//DataBase.Context.ProductsSet.fill(tableName, wherePattern, whereValues, Product.DEFAULT_SORT, null, null);
			

			/* NOW WITH ADA.Q */
			new Select()
				.from(Product.class)
				.innerJoin(Product.CATEGORIES_TABLE_NAME, Product.PRODUCTS_ID_FIELD, Product.CATEGORIES_ID_FIELD)
				.where(wherePattern, whereValues)
				.orderBy(Product.DEFAULT_SORT)
				.execute(DataBase.Context.ProductsSet);
		}
	}
	
	public void saveProduct(Product pProduct) throws AdaFrameworkException {
		
		/* BEFORE ADA.Q */
		//if (pProduct.getStatus() == Entity.STATUS_NEW) {
		//	DataBase.Context.ProductsSet.add(pProduct);
		//}
		//DataBase.Context.ProductsSet.save();
		
		
		/* NOW WITH ADA.Q */
		if (pProduct.getStatus() == Entity.STATUS_NEW) {
			new Insert()
				.into(Product.class)
				.values(pProduct)
				.execute(DataBase.Context.ProductsSet);
		} else if (pProduct.getStatus() == Entity.STATUS_UPDATED) {
			new Update(Product.class)
				.set(pProduct)
				.execute(DataBase.Context.ProductsSet);
		}
	}
	
	public void deleteProduct(Product pProduct) throws AdaFrameworkException {
		if (pProduct.getStatus() != Entity.STATUS_NEW) {
			
			/* BEFORE ADA.Q */
			//DataBase.Context.ProductsSet.remove(pProduct);
			//DataBase.Context.ProductsSet.save();
		
		
			/* NOW WITH ADA.Q */
			new Delete()
				.from(Product.class)
				.where(pProduct)
				.execute(DataBase.Context.ProductsSet);
		}
	}
}
