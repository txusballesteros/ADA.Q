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

package com.mobandme.ada.examples.q.model.entities;


import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Databinding;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;
import com.mobandme.ada.examples.q.R;

/**
 * Categories application entity.
 * @author Txus Ballesteros (@DesAndrOId)
 * @category Entity
 * @version 2.4
 */
@Table(name = "Categories")
public class Category extends Entity {

	public static final String DEFAULT_SORT = "Name ASC";
	
	/***********************************************************/
	/* 						FIELDS DEFINITION				   */
	/***********************************************************/
	@TableField(name = "Name", datatype = DATATYPE_STRING, required = true)
	@Databinding(ViewId = R.id.CategoryName)
	public String name;
	
	
	/***********************************************************/
	/* 						CONSTRUCTORS					   */
	/***********************************************************/
	public Category() { }
	public Category(String pName) { name = pName; }
	
	@Override
	public String toString() { return name; }
}
