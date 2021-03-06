/**
 * <a href="http://www.openolat.org">
 * OpenOLAT - Online Learning and Training</a><br>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); <br>
 * you may not use this file except in compliance with the License.<br>
 * You may obtain a copy of the License at the
 * <a href="http://www.apache.org/licenses/LICENSE-2.0">Apache homepage</a>
 * <p>
 * Unless required by applicable law or agreed to in writing,<br>
 * software distributed under the License is distributed on an "AS IS" BASIS, <br>
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. <br>
 * See the License for the specific language governing permissions and <br>
 * limitations under the License.
 * <p>
 * Initial code contributed and copyrighted by<br>
 * frentix GmbH, http://www.frentix.com
 * <p>
 */
package org.olat.modules.edusharing;

import org.olat.core.id.CreateInfo;
import org.olat.core.id.Identity;
import org.olat.core.id.ModifiedInfo;
import org.olat.core.id.OLATResourceable;

/**
 * 
 * Initial date: 9 Dec 2018<br>
 * @author uhensler, urs.hensler@frentix.com, http://www.frentix.com
 *
 */
public interface EdusharingUsage extends ModifiedInfo, CreateInfo {

	public Long getKey();
	
	/**
	 * The identifier is the shared key between edu-sharing and OpenOLAT. In
	 * edu-sharing that key is called "resourceId".
	 *
	 * @return
	 */
	public String getIdentifier();
	
	public Identity getIdentity();
	
	public String getObjectUrl();

	public String getVersion();
	
	public String getMimeType();
	
	public String getMediaType();
	
	public String getWidth();
	
	public String getHeight();
	
	public OLATResourceable getOlatResourceable();
	
	/**
	 * Identifies the usage distinctly inside an OLATResourceable.
	 *
	 * @return
	 */
	public String getSubPath();
	
}
