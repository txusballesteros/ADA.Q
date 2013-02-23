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

package com.mobandme.ada.examples.q.model.helpers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.content.Context;

import com.mobandme.ada.examples.q.helpers.ExceptionsHelper;
import com.mobandme.ada.examples.q.model.entities.Category;
import com.mobandme.ada.examples.q.model.entities.Product;

/**
 * Application products import helper.
 * @author Txus Ballesteros (@DesAndrOId)
 * @category Helper
 * @version 2.4
 */
public class ImportLoaderHelper {

	private final static String PRODUCTS_ASSET_NAME = "products.json";
	private final static String CATEGORIES_ASSET_NAME = "categories.json";
	
	/**
	 * This method read a categories.json file from application Assets folder and return a list of Categories.
	 * @param pContext
	 * @return List with filled Categories.
	 */
	public static List<Category> getCategoriesList(Context pContext) {
		List<Category> returnedValue = new ArrayList<Category>();

		try {
			String data = null;

			if (pContext != null) {

				InputStream input = pContext.getAssets().open(CATEGORIES_ASSET_NAME);
				if (input != null) {

					BufferedReader reader = new BufferedReader(
							new InputStreamReader(input));
					if (reader != null) {
						data = reader.readLine();
						reader.close();
					}

					input.close();
				}

				if (data != null && !data.trim().equals("")) {
					JSONArray dataParser = new JSONArray(data);
					if (dataParser != null && dataParser.length() > 0) {
						for (int index = 0; index < dataParser.length(); index++) {
							returnedValue.add(new Category(dataParser.getString(index)));
						}
					}
				}
			}

		} catch (Exception e) {
			ExceptionsHelper.manage(pContext, e);
		}

		return returnedValue;
	}
	
	/**
	 * This method read a products.json file from application Assets folder and return a list of Products.
	 * @param pContext
	 * @return List with filled Products.
	 */
	public static List<Product> getProductList(Context pContext, Category pCategory) {
		List<Product> returnedValue = new ArrayList<Product>();

		try {
			String data = null;
			
			if (pContext != null) {
				
				InputStream    input = pContext.getAssets().open(PRODUCTS_ASSET_NAME);
				if (input != null) {
					
					BufferedReader reader = new BufferedReader(new InputStreamReader(input));
					if (reader != null) {
						data =  reader.readLine();
						reader.close();
					}
					
					input.close();
				}
				
				if (data != null && !data.trim().equals("")) {
					JSONArray dataParser = new JSONArray(data);
					if (dataParser != null && dataParser.length() > 0) {
						for (int index = 0; index < dataParser.length(); index++) {
							returnedValue.add(new Product(dataParser.getString(index), pCategory));
						}
					}
				}
			}
			
		} catch (Exception e) {
			ExceptionsHelper.manage(pContext, e);
		}
		
		return returnedValue;
	}	
}
