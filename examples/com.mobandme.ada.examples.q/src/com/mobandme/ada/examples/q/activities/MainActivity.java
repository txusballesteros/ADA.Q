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

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.ListView;
import android.widget.SearchView;
import com.mobandme.ada.examples.q.R;
import com.mobandme.ada.examples.q.adapters.ProductsAdapter;
import com.mobandme.ada.examples.q.helpers.ExceptionsHelper;
import com.mobandme.ada.examples.q.model.context.DataBase;
import com.mobandme.ada.examples.q.model.entities.Category;
import com.mobandme.ada.examples.q.model.entities.Product;
import com.mobandme.ada.examples.q.model.managers.ProductsManager;
import com.mobandme.ada.exceptions.AdaFrameworkException;

/**
 * Main Activity.
 * @author Txus Ballesteros (@DesAndrOId)
 * @category Activity
 * @version 2.4
 */
public class MainActivity extends Activity implements OnItemClickListener, TabListener, OnQueryTextListener {
	
	private ListView        productsList;
	private ProductsAdapter productsAdapter;
	private ProductsManager productsManager;
	private boolean         searchInProgress = false;

	/***********************************************************/
	/*  		  OnQueryTextListener EVENTS                   */
	/***********************************************************/
	@Override
	public boolean onQueryTextChange(String pQuery) {
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String pQuery) {
		try {
			
			onSelectCommand(pQuery);
			
		} catch (Exception e) {
			ExceptionsHelper.manage(MainActivity.this, e);
		}
		return true;
	}
	
	
	/***********************************************************/
	/*    		   OnItemClickListener EVENTS                  */
	/***********************************************************/
	@Override
	public void onItemClick(AdapterView<?> pParent, View pView, int pPosition, long pId) {
		try{

			Product currentProduct = DataBase.Context.ProductsSet.get(pPosition);
			onEditCommand(currentProduct);
			
		} catch (Exception e) {
			ExceptionsHelper.manage(this, e);
		}
	}
	
	
	/***********************************************************/
	/*					TabListener EVENTS                     */
	/***********************************************************/
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) { }

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) { 
		try {
			
			onSelectCommand();
			
		} catch (Exception e) {
			ExceptionsHelper.manage(this, e);
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) { }
	
	
	/***********************************************************/
	/* 						Activity EVENTS                    */
	/***********************************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			
			DataBase.initializeDataContext(this);
			
			initializeActivity();
			
		} catch (Exception e) {
			ExceptionsHelper.manage(this, e);
		}
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu pMenu) {
		try {
			
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.main_menu, pMenu);
			
			SearchView searchViewMenu = (SearchView)pMenu.findItem(R.id.Menu_Search).getActionView();
			if (searchViewMenu != null) {
				searchViewMenu.setOnQueryTextListener(this);
			}
			
			
		} catch (Exception e) {
			ExceptionsHelper.manage(this, e);
		}
		
		return super.onCreateOptionsMenu(pMenu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem pMenuItem) {
		try {
			
			switch (pMenuItem.getItemId()) {
				case R.id.menu_new:
					onNewCommand();
					return true;
				default:
					return super.onOptionsItemSelected(pMenuItem);
			}
			
		} catch (Exception e) {
			ExceptionsHelper.manage(this, e);
		}
		
		return super.onOptionsItemSelected(pMenuItem);	
	}
	

	/***********************************************************/
	/* 						PRIVATE METHODS					   */
	/***********************************************************/
	private void initializeActivity() {
		setContentView(R.layout.activity_main);
		
		productsManager = new ProductsManager();
		
		initializeActionBar();
		
		productsList      = (ListView)findViewById(R.id.ProductsList);
		if (productsList != null) {
			productsAdapter = new ProductsAdapter(this);
			DataBase.Context.ProductsSet.setAdapter(productsAdapter);
			productsList.setAdapter(productsAdapter);
			productsList.setOnItemClickListener(this);
		}		
	}
	
	private void initializeActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		
		for(Category category : DataBase.Context.CategoriesSet) {
			Tab tab = actionBar.newTab();
			tab.setText(category.name);
			tab.setTag(category);
			tab.setTabListener(this);
			actionBar.addTab(tab);
		}
	}
	
	private void onEditCommand(Product pProduct) {
		try {
			
			if (pProduct != null) {
				Intent detailIntent = new Intent(this, DetailActivity.class);
				detailIntent.putExtra(DetailActivity.EXTRA_PRODUCT_ID, pProduct.getID());
				detailIntent.putExtra(DetailActivity.EXTRA_CATEGORY, ((Category)getActionBar().getSelectedTab().getTag()).name);
				startActivity(detailIntent);
			}
			
		} catch (Exception e) {
			ExceptionsHelper.manage(this, e);
		}
	}
	
	
	/***********************************************************/
	/* 						COMMNAD METHODS					   */
	/***********************************************************/
	private void onSelectCommand() {
		onSelectCommand(null);
	}
	
	private void onSelectCommand(String pProductName) {
		if (!searchInProgress) {
			Category category = (Category)getActionBar().getSelectedTab().getTag();
			new SearchTask().execute(category.getID().toString(), pProductName);
		}
	}
	
	private void onNewCommand() {
		try {
			
			Intent detailIntent = new Intent(this, DetailActivity.class);
			detailIntent.putExtra(DetailActivity.EXTRA_CATEGORY, ((Category)getActionBar().getSelectedTab().getTag()).name);
			startActivity(detailIntent);
			
		} catch (Exception e) {
			ExceptionsHelper.manage(this, e);
		}
	}
	
	
	/***********************************************************/
	/* 						ASYNCTASKS 						   */
	/***********************************************************/
	private class SearchTask extends AsyncTask<String, Void, Boolean> {
		
		private void executeSelect(String pCategoryID, String pProductName) throws AdaFrameworkException {
			productsManager.fillProducts(pCategoryID, pProductName);
		}

		@Override
		protected Boolean doInBackground(String... params) {
			Boolean returnedValue = true;
			
			try {
				
				searchInProgress = true;
				executeSelect(params[0], params[1]);
				
			} catch (Exception e) {
				returnedValue = false;
				ExceptionsHelper.manage(MainActivity.this, e);
			}
			
			return returnedValue;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			searchInProgress = false;
			super.onPostExecute(result);
		}
	}
}
