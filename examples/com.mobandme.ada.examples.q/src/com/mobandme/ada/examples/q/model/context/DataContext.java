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

package com.mobandme.ada.examples.q.model.context;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mobandme.ada.ObjectContext;
import com.mobandme.ada.ObjectSet;
import com.mobandme.ada.examples.q.helpers.ExceptionsHelper;
import com.mobandme.ada.examples.q.model.entities.Category;
import com.mobandme.ada.examples.q.model.entities.Product;
import com.mobandme.ada.examples.q.model.helpers.ImportLoaderHelper;
import com.mobandme.ada.exceptions.AdaFrameworkException;

/**
 * Application object context.
 * @author Txus Ballesteros (@DesAndrOId)
 * @category ObjectContext
 * @version 2.4
 */
public class DataContext extends ObjectContext {

	public ObjectSet<Category> CategoriesSet;
	public ObjectSet<Product> ProductsSet;
	
	public DataContext(Context pContext) { 
		super(pContext); 
		
		try {
			
			initializeDataContext();
			
		} catch (Exception e) {
			ExceptionsHelper.manage(getContext(), e);
		}
	}

	@Override
	protected void onError(Exception pException) {
		ExceptionsHelper.manage(getContext(), pException);
	}
	
	@Override
	protected void onPopulate(SQLiteDatabase pDatabase) {
		try {
			
			CategoriesSet.addAll(ImportLoaderHelper.getCategoriesList(getContext()));
			CategoriesSet.save(pDatabase);
			
			ProductsSet.addAll(ImportLoaderHelper.getProductList(getContext(), CategoriesSet.get(4)));		
			ProductsSet.save(pDatabase);
			
		} catch (Exception e) {
			ExceptionsHelper.manage(getContext(), e);
		} finally {
			super.onPopulate(pDatabase);
		}
	}
	
	private void initializeDataContext() throws AdaFrameworkException {
		CategoriesSet = new ObjectSet<Category>(Category.class, this);
		ProductsSet = new ObjectSet<Product>(Product.class, this);
		
		CategoriesSet.fill(Category.DEFAULT_SORT);
	}
}
