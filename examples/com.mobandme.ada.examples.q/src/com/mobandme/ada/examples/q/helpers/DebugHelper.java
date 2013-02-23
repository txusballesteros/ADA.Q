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

package com.mobandme.ada.examples.q.helpers;

import android.util.Log;

/**
 * Application debug helper.
 * @author Txus Ballesteros (@DesAndrOId)
 * @category Helper
 * @version 2.4
 */
public class DebugHelper {

	private final static String LOG_TAG = "ADA.Q";
	
	public static void write(String pMessage) {
		Log.d(LOG_TAG, pMessage);
	}
}
