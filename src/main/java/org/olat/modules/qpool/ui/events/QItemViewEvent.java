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
package org.olat.modules.qpool.ui.events;

import java.util.Collections;
import java.util.List;

import org.olat.core.gui.control.Event;
import org.olat.modules.qpool.QuestionItemView;

/**
 * 
 * Initial date: 12.02.2013<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
public class QItemViewEvent extends Event {

	private static final long serialVersionUID = 1868410260121125418L;
	private final QuestionItemView item;
	private final List<QuestionItemView> itemList;

	public QItemViewEvent(String cmd, QuestionItemView item) {
		super(cmd);
		this.item = item;
		itemList = null;
	}
	
	public QItemViewEvent(String cmd, List<QuestionItemView> itemList) {
		super(cmd);
		item = null;
		this.itemList = itemList;
	}

	public QuestionItemView getItem() {
		return item;
	}
	
	public List<QuestionItemView> getItemList() {
		if(itemList == null) {
			if(item == null) {
				return Collections.emptyList();
			}
			return Collections.singletonList(item);
		}
		return itemList;
	}
}
