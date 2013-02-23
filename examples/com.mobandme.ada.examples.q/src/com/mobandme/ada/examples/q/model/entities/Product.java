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

import java.util.Date;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Databinding;
import com.mobandme.ada.annotations.RequiredFieldValidation;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;
import com.mobandme.ada.examples.q.R;
import com.mobandme.ada.examples.q.binders.CategoryDataBinder;

/**
 * Products application entity.
 * @author Txus Ballesteros (@DesAndrOId)
 * @category Entity
 * @version 2.4
 */
@Table(name = "Products")
public class Product extends Entity {

	public static final String DEFAULT_SORT              = "Name ASC";
	public static final String INDEX_NAME                = "INX_Product_Name";
	public static final String PRODUCTS_ID_FIELD         = "Products.ID";
	public static final String CATEGORIES_TABLE_NAME     = "LINK_Products_Category_Categories";
	public static final String CATEGORIES_ID_FIELD       = CATEGORIES_TABLE_NAME + ".Products_ID";
	public static final String CATEGORIES_FILTER_PATTERN = "Categories_ID = ?";
	public static final String CATEGORIES_NAME_FILTER_PATTERN = "Categories_ID = ? AND Name LIKE ?";
	
	/***********************************************************/
	/* 						FIELDS DEFINITION				   */
	/***********************************************************/
	@TableField(name = "Name", datatype = DATATYPE_STRING, maxLength = 200, required = true)
	@Databinding(ViewId = R.id.ProductName)
	@RequiredFieldValidation(messageResourceId = R.string.validation_message_nane_required)
	public String name;
	
	@TableField(name = "Description", datatype = DATATYPE_STRING)
	@Databinding(ViewId = R.id.ProductDescription)
	@RequiredFieldValidation(messageResourceId = R.string.validation_message_description_required)
	public String description;
	
	@TableField(name = "Category", datatype = DATATYPE_ENTITY_LINK)
	@Databinding(ViewId = R.id.CategorySelector, binder = CategoryDataBinder.class)
	public Category category;
	
	@TableField(name = "CreationDate", datatype = DATATYPE_DATE_BINARY)
	public Date creationDate = new Date();
	
	
	/***********************************************************/
	/* 						CONSTRUCTORS					   */
	/***********************************************************/
	public Product() {}
	
	public Product(String pName, Category pCategory) {
		name = pName;
		category = pCategory;
	}
	
	public Product(String pName, String pDescription, Category pCategory) {
		name = pName;
		description = pDescription;
		category = pCategory;
	}
}
