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

package com.mobandme.ada.examples.q.binders;

import android.view.View;
import android.widget.Spinner;

import com.mobandme.ada.DataBinder;
import com.mobandme.ada.DataBinding;
import com.mobandme.ada.Entity;
import com.mobandme.ada.examples.q.adapters.CategoriesAdapter;
import com.mobandme.ada.examples.q.helpers.ExceptionsHelper;
import com.mobandme.ada.examples.q.model.entities.Category;
import com.mobandme.ada.examples.q.model.entities.Product;
import com.mobandme.ada.exceptions.AdaFrameworkException;

/**
 * Categories spinner data binder.
 * @author Txus Ballesteros (@DesAndrOId)
 * @category Data Binder
 * @version 2.4
 */
public class CategoryDataBinder extends DataBinder {

	@Override
	public void bind(DataBinding pBinding, Entity pEntity, View pView, int pDirection) throws AdaFrameworkException {
		try {
			
			Product entity = (Product)pEntity;
			
			if (entity != null) {
				if (pView != null && pView instanceof Spinner) {
					if (pDirection == BINDING_ENTITY_TO_UI) {
						if (entity.category != null) {
							CategoriesAdapter adapter = (CategoriesAdapter)((Spinner)pView).getAdapter();
							((Spinner)pView).setSelection(getPosition(adapter, entity.category));
						}
					} else if (pDirection == BINDING_UI_TO_ENTITY) {
						Category category = (Category)((Spinner)pView).getSelectedItem();
						entity.category = category;
					}
				}
			}
			
		} catch (Exception e) {
			ExceptionsHelper.manage(pView.getContext(), e);
		}
	}
	
	private int getPosition(CategoriesAdapter pAdapter, Category pCategpry) {
		int returnedValue = -1;
		for(int index = 0; index < pAdapter.getCount(); index++) {
			if (pAdapter.getItem(index).getID() == pCategpry.getID()) {
				returnedValue = index;
				break;
			}
		}
		return returnedValue;
	}
}
