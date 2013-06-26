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
package org.olat.modules.qpool.ui;

import java.math.BigDecimal;

import org.olat.core.gui.UserRequest;
import org.olat.core.gui.components.form.flexible.FormItemContainer;
import org.olat.core.gui.components.form.flexible.elements.StaticTextElement;
import org.olat.core.gui.components.form.flexible.impl.FormBasicController;
import org.olat.core.gui.control.Controller;
import org.olat.core.gui.control.WindowControl;
import org.olat.core.util.StringHelper;
import org.olat.modules.qpool.QuestionItem;

/**
 * 
 * Initial date: 12.02.2013<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
public class QuestionItemSummaryController extends FormBasicController {
	
	private StaticTextElement subjectEl, studyFieldEl, keywordsEl, usageEl, descriptionEl;
	private StaticTextElement difficultyEl, stdevDifficultyEl, differentiationEl;
	
	private boolean canEdit;
	private QuestionItem item;
	
	public QuestionItemSummaryController(UserRequest ureq, WindowControl wControl) {
		super(ureq, wControl);
		initForm(ureq);
	}

	@Override
	protected void initForm(FormItemContainer formLayout, Controller listener, UserRequest ureq) {
		setFormTitle("metadatas");
		
		subjectEl = uifactory.addStaticTextElement("general.title", "", formLayout);
		keywordsEl = uifactory.addStaticTextElement("general.keywords", "", formLayout);
		studyFieldEl = uifactory.addStaticTextElement("classification.taxonomy.level", "", formLayout);
		usageEl = uifactory.addStaticTextElement("question.usage", "", formLayout);
		difficultyEl = uifactory.addStaticTextElement("question.difficulty", "", formLayout);
		stdevDifficultyEl = uifactory.addStaticTextElement("question.stdevDifficulty", "", formLayout);
		differentiationEl = uifactory.addStaticTextElement("question.differentiation", "", formLayout);
		descriptionEl = uifactory.addStaticTextElement("general.description", "", formLayout);
	}
	
	@Override
	protected void doDispose() {
		//
	}
	
	public boolean isCanEdit() {
		return canEdit;
	}
	
	public QuestionItem getItem() {
		return item;
	}
	
	public void refresh() {
		updateItem(item, canEdit);
	}
	
	public void updateItem(QuestionItem item, boolean canEdit) {
		this.item = item;
		if(item == null) {
			canEdit = false;
			subjectEl.setValue("");
			keywordsEl.setValue("" );
			studyFieldEl.setValue("");
			usageEl.setValue("");
			descriptionEl.setValue("");
			difficultyEl.setValue("");
			stdevDifficultyEl.setValue("");
			differentiationEl.setValue("");
		} else {
			this.canEdit = canEdit;
			subjectEl.setValue(item.getTitle());
			String keywords = item.getKeywords();
			keywordsEl.setValue(keywords == null ? "" : keywords);
			String taxonPath = item.getTaxonomicPath();
			studyFieldEl.setValue(taxonPath == null ? "" : taxonPath);
			
			int usage = item.getUsage();
			String usageStr = "";
			if(usage >= 0) {
				usageStr = Integer.toString(usage);
			}
			usageEl.setValue(usageStr);
			
			difficultyEl.setValue(toString(item.getDifficulty()));
			stdevDifficultyEl.setValue(toString(item.getStdevDifficulty()));
			differentiationEl.setValue(toString(item.getDifferentiation()));
			
			String description = item.getDescription();
			if(StringHelper.containsNonWhitespace(description)) {
				descriptionEl.setValue(description);
			} else {
				descriptionEl.setValue("");
			}
		}
	}
	
	public void reset() {
		subjectEl.setValue("");
		studyFieldEl.setValue("");
		keywordsEl.setValue("");
		usageEl.setValue("");
		descriptionEl.setValue("");
	}

	@Override
	protected void formOK(UserRequest ureq) {
		//
	}
	
	private String toString(BigDecimal val) {
		return (val == null) ? "" : val.toPlainString();
	}
}