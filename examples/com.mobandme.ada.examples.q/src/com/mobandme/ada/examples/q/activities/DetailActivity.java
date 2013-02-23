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

package com.mobandme.ada.examples.q.activities;

import com.mobandme.ada.DataBinder;
import com.mobandme.ada.Entity;
import com.mobandme.ada.examples.q.R;
import com.mobandme.ada.examples.q.adapters.CategoriesAdapter;
import com.mobandme.ada.examples.q.helpers.ExceptionsHelper;
import com.mobandme.ada.examples.q.model.context.DataBase;
import com.mobandme.ada.examples.q.model.entities.Product;
import com.mobandme.ada.examples.q.model.managers.ProductsManager;
import com.mobandme.ada.exceptions.AdaFrameworkException;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Spinner;

/**
 * Detail Activity.
 * @author Txus Ballesteros (@DesAndrOId)
 * @category Activity
 * @version 2.4
 */
public class DetailActivity extends Activity {

	public  final static String EXTRA_PRODUCT_ID = "ProductID";
	public  final static String EXTRA_CATEGORY   = "Category";
	
	private ProductsManager   productsManager;
	private CategoriesAdapter categoriesAdapter;
	private Spinner           categorySelector;
	private Product           currentProduct;
	
	
	
	/***********************************************************/
	/* 						Activity EVENTS                    */
	/***********************************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			
			initializeActivity();
			initializeData();
			
		} catch (Exception e) {
			ExceptionsHelper.manage(this, e);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu pMenu) {
		try {
			
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.detail_menu, pMenu);
			
			MenuItem deleteCommand = pMenu.findItem(R.id.menu_delete);
			if (deleteCommand != null) {
				if (currentProduct.getStatus() == Entity.STATUS_NEW) {
					deleteCommand.setVisible(false);
				} else {
					deleteCommand.setVisible(true);
				}
			}
			
		} catch (Exception e) {
			ExceptionsHelper.manage(this, e);
		}
		
		return super.onCreateOptionsMenu(pMenu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem pItem) {
		try {
		
			switch (pItem.getItemId()) {
				case android.R.id.home:
					onCancelCommand();
					return true;
				case R.id.menu_save:
					onSaveCommand();
					return true;
				case R.id.menu_delete:
					onDeleteCommand();
					return true;
			}
			
		} catch (Exception e) {
			ExceptionsHelper.manage(this, e);
		}
		
		return super.onOptionsItemSelected(pItem);
	}
	
	
	/***********************************************************/
	/* 						PRIVATE METHODS                    */
	/***********************************************************/
	private void initializeActivity() {
		setContentView(R.layout.activity_detail);
		
		initializeActionBar();
		
		productsManager = new ProductsManager();
		currentProduct   = new Product();
		categorySelector = (Spinner)findViewById(R.id.CategorySelector);
		if (categorySelector != null) {
			categoriesAdapter = new CategoriesAdapter(this);
			categoriesAdapter.setDropDownViewResource(R.layout.category_spinner_item);
			DataBase.Context.CategoriesSet.setAdapter(categoriesAdapter);
			categorySelector.setAdapter(categoriesAdapter);
		}
	}
	
	private void initializeActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	private void initializeData() throws AdaFrameworkException {
		if (getIntent().getExtras() != null && getIntent().getExtras().size() > 0) {
			if (getIntent().getExtras().containsKey(EXTRA_CATEGORY)) {
				String categoryName = getIntent().getExtras().getString(EXTRA_CATEGORY);
				if (categoryName != null && !categoryName.trim().equals("")) {
					for (int index = 0; index < categorySelector.getAdapter().getCount(); index++) {
						if (((CategoriesAdapter)categorySelector.getAdapter()).getItem(index).name.equals(categoryName)) {
							categorySelector.setSelection(index);
							break;
						}
					}
				}
			}
			
			if (getIntent().getExtras().containsKey(EXTRA_PRODUCT_ID)) {
				Long   productID    = getIntent().getExtras().getLong(EXTRA_PRODUCT_ID);
				if (productID != null) {
					for(Product product : DataBase.Context.ProductsSet) {
						if (product.getID().equals(productID)) {
							currentProduct = product;
							currentProduct.bind(this, DataBinder.BINDING_ENTITY_TO_UI);
							break;
						}
					}
				}
			}
		}
	}
	
	
	/***********************************************************/
	/* 						COMMNAD METHODS					   */
	/***********************************************************/
	private void onSaveCommand() {
		try {

			currentProduct.bind(this, DataBinder.BINDING_UI_TO_ENTITY);
			if (currentProduct.validate(this)) {
				
				productsManager.saveProduct(currentProduct);
				
				finish();
			} else {
				onValidationError(currentProduct.getValidationResultString("\r\n*"));
			}
			
		} catch (Exception e) {
			ExceptionsHelper.manage(this, e);
		}
	}
	
	private void onDeleteCommand() {
		try {
			
			productsManager.deleteProduct(currentProduct);
						
			finish();
			
		} catch (Exception e) {
			ExceptionsHelper.manage(this, e);
		}
	}
	
	private void onCancelCommand() {
		try {
			
			finish();
			
		} catch (Exception e) {
			ExceptionsHelper.manage(this, e);
		}
	}
	
	private void onValidationError(String pMessage) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.setTitle(R.string.validation_message_title);
		alertDialog.setMessage(pMessage);
		alertDialog.setCancelable(true);
		alertDialog.setCanceledOnTouchOutside(true);
		alertDialog.show();
	}
}
