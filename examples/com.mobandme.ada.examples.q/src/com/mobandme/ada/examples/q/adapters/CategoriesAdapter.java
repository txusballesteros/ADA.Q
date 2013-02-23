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

package com.mobandme.ada.examples.q.adapters;

import com.mobandme.ada.examples.q.R;
import com.mobandme.ada.examples.q.helpers.ExceptionsHelper;
import com.mobandme.ada.examples.q.model.context.DataBase;
import com.mobandme.ada.examples.q.model.entities.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Categories data adapter.
 * @author Txus Ballesteros (@DesAndrOId)
 * @category Data Adapter
 * @version 2.4
 */
public class CategoriesAdapter extends ArrayAdapter<Category> {

	public CategoriesAdapter(Context context) {
		super(context, R.layout.category_spinner_item);
	}

	@Override
	public View getView(int pPosition, View pConvertView, ViewGroup pParent) {
		View returnedValue = pConvertView;
		
		try {
			
			if (returnedValue == null) {
				LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				returnedValue = inflater.inflate(R.layout.category_spinner_item, null);
			}
			
			DataBase.Context.CategoriesSet.get(pPosition).bind(returnedValue);
			
		} catch (Exception e) {
			ExceptionsHelper.manage(getContext(), e);
		}
		
		return returnedValue;
	}
}
