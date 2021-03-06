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
package org.olat.course.reminder.manager;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.olat.test.JunitTestHelper.random;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.Test;
import org.olat.core.commons.persistence.DB;
import org.olat.core.id.Identity;
import org.olat.course.nodes.CourseNode;
import org.olat.course.nodes.SPCourseNode;
import org.olat.modules.assessment.AssessmentEntry;
import org.olat.modules.assessment.AssessmentService;
import org.olat.repository.RepositoryEntry;
import org.olat.test.JunitTestHelper;
import org.olat.test.OlatTestCase;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * Initial date: 3 Sep 2020<br>
 * @author uhensler, urs.hensler@frentix.com, http://www.frentix.com
 *
 */
public class ReminderRuleDAOTest extends OlatTestCase {
	
	@Autowired
	private DB dbInstance;
	@Autowired
	private AssessmentService assessmentService;
	
	@Autowired
	private ReminderRuleDAO sut;
	
	@Test
	public void shouldGetScores() {
		RepositoryEntry entry = JunitTestHelper.createAndPersistRepositoryEntry();
		RepositoryEntry entryOther = JunitTestHelper.createAndPersistRepositoryEntry();
		CourseNode node = new SPCourseNode();
		String subIdent = node.getIdent();
		String subIdenOther = random();
		Identity identity1 = JunitTestHelper.createAndPersistIdentityAsRndUser(random());
		Identity identity2 = JunitTestHelper.createAndPersistIdentityAsRndUser(random());
		Identity identityOther = JunitTestHelper.createAndPersistIdentityAsRndUser(random());
		
		AssessmentEntry ae1 = assessmentService.getOrCreateAssessmentEntry(identity1, null, entry, subIdent, Boolean.FALSE, null);
		ae1.setScore(BigDecimal.valueOf(5));
		assessmentService.updateAssessmentEntry(ae1);
		AssessmentEntry ae2 = assessmentService.getOrCreateAssessmentEntry(identity2, null, entry, subIdent, Boolean.FALSE, null);
		ae2.setScore(BigDecimal.valueOf(6));
		assessmentService.updateAssessmentEntry(ae2);
		AssessmentEntry aeOtherEntry = assessmentService.getOrCreateAssessmentEntry(identity1, null, entryOther, subIdent, Boolean.FALSE, null);
		aeOtherEntry.setScore(BigDecimal.valueOf(7));
		assessmentService.updateAssessmentEntry(aeOtherEntry);
		AssessmentEntry aeOtherSubIdent = assessmentService.getOrCreateAssessmentEntry(identity1, null, entry, subIdenOther, Boolean.FALSE, null);
		aeOtherSubIdent.setScore(BigDecimal.valueOf(8));
		assessmentService.updateAssessmentEntry(aeOtherSubIdent);
		AssessmentEntry aeOtherIdentity = assessmentService.getOrCreateAssessmentEntry(identityOther, null, entry, subIdent, Boolean.FALSE, null);
		aeOtherIdentity.setScore(BigDecimal.valueOf(9));
		assessmentService.updateAssessmentEntry(aeOtherIdentity);
		
		Map<Long, Float> scores = sut.getScores(entry, node, asList(identity1, identity2));
		
		assertThat(scores.size()).isEqualTo(2);
		assertThat(scores.get(identity1.getKey())).isEqualTo(5);
		assertThat(scores.get(identity2.getKey())).isEqualTo(6);
	}
	
	@Test
	public void shouldGetAttempts() {
		RepositoryEntry entry = JunitTestHelper.createAndPersistRepositoryEntry();
		RepositoryEntry entryOther = JunitTestHelper.createAndPersistRepositoryEntry();
		CourseNode node = new SPCourseNode();
		String subIdent = node.getIdent();
		String subIdenOther = random();
		Identity identity1 = JunitTestHelper.createAndPersistIdentityAsRndUser(random());
		Identity identity2 = JunitTestHelper.createAndPersistIdentityAsRndUser(random());
		Identity identityOther = JunitTestHelper.createAndPersistIdentityAsRndUser(random());
		
		AssessmentEntry ae1 = assessmentService.getOrCreateAssessmentEntry(identity1, null, entry, subIdent, Boolean.FALSE, null);
		ae1.setAttempts(Integer.valueOf(1));
		assessmentService.updateAssessmentEntry(ae1);
		AssessmentEntry ae2 = assessmentService.getOrCreateAssessmentEntry(identity2, null, entry, subIdent, Boolean.FALSE, null);
		ae2.setAttempts(Integer.valueOf(2));
		assessmentService.updateAssessmentEntry(ae2);
		AssessmentEntry aeOtherEntry = assessmentService.getOrCreateAssessmentEntry(identity1, null, entryOther, subIdent, Boolean.FALSE, null);
		aeOtherEntry.setAttempts(Integer.valueOf(3));
		assessmentService.updateAssessmentEntry(aeOtherEntry);
		AssessmentEntry aeOtherSubIdent = assessmentService.getOrCreateAssessmentEntry(identity1, null, entry, subIdenOther, Boolean.FALSE, null);
		aeOtherSubIdent.setAttempts(Integer.valueOf(4));
		assessmentService.updateAssessmentEntry(aeOtherSubIdent);
		AssessmentEntry aeOtherIdentity = assessmentService.getOrCreateAssessmentEntry(identityOther, null, entry, subIdent, Boolean.FALSE, null);
		aeOtherIdentity.setAttempts(Integer.valueOf(5));
		assessmentService.updateAssessmentEntry(aeOtherIdentity);
		
		Map<Long, Integer> attempts = sut.getAttempts(entry, node, asList(identity1, identity2));
		
		assertThat(attempts.size()).isEqualTo(2);
		assertThat(attempts.get(identity1.getKey())).isEqualTo(1);
		assertThat(attempts.get(identity2.getKey())).isEqualTo(2);
	}
	
	@Test
	public void shouldLoadPassed() {
		RepositoryEntry entry = JunitTestHelper.createAndPersistRepositoryEntry();
		RepositoryEntry entryOther = JunitTestHelper.createAndPersistRepositoryEntry();
		CourseNode node = new SPCourseNode();
		String subIdent = node.getIdent();
		String subIdenOther = random();
		Identity identity1 = JunitTestHelper.createAndPersistIdentityAsRndUser(random());
		Identity identity2 = JunitTestHelper.createAndPersistIdentityAsRndUser(random());
		Identity identityOther = JunitTestHelper.createAndPersistIdentityAsRndUser(random());
		
		AssessmentEntry ae1 = assessmentService.getOrCreateAssessmentEntry(identity1, null, entry, subIdent, Boolean.FALSE, null);
		ae1.setPassed(Boolean.TRUE);
		assessmentService.updateAssessmentEntry(ae1);
		AssessmentEntry ae2 = assessmentService.getOrCreateAssessmentEntry(identity2, null, entry, subIdent, Boolean.FALSE, null);
		ae2.setPassed(Boolean.TRUE);
		assessmentService.updateAssessmentEntry(ae2);
		AssessmentEntry aeOtherEntry = assessmentService.getOrCreateAssessmentEntry(identity1, null, entryOther, subIdent, Boolean.FALSE, null);
		aeOtherEntry.setPassed(Boolean.FALSE);
		assessmentService.updateAssessmentEntry(aeOtherEntry);
		AssessmentEntry aeOtherSubIdent = assessmentService.getOrCreateAssessmentEntry(identity1, null, entry, subIdenOther, Boolean.FALSE, null);
		aeOtherSubIdent.setPassed(Boolean.FALSE);
		assessmentService.updateAssessmentEntry(aeOtherSubIdent);
		AssessmentEntry aeOtherIdentity = assessmentService.getOrCreateAssessmentEntry(identityOther, null, entry, subIdent, Boolean.FALSE, null);
		aeOtherIdentity.setPassed(Boolean.FALSE);
		assessmentService.updateAssessmentEntry(aeOtherIdentity);
		
		Map<Long, Boolean> passed = sut.getPassed(entry, node, asList(identity1, identity2));
		
		assertThat(passed).hasSize(2);
		assertThat(passed.get(identity1.getKey())).isTrue();
		assertThat(passed.get(identity2.getKey())).isTrue();
	}
	
	@Test
	public void shouldLoadCompletions() {
		RepositoryEntry entry = JunitTestHelper.createAndPersistRepositoryEntry();
		RepositoryEntry entryOther = JunitTestHelper.createAndPersistRepositoryEntry();
		Identity identity1 = JunitTestHelper.createAndPersistIdentityAsRndUser(random());
		Identity identity2 = JunitTestHelper.createAndPersistIdentityAsRndUser(random());
		Identity identityOther = JunitTestHelper.createAndPersistIdentityAsRndUser(random());
		
		AssessmentEntry ae1 = assessmentService.getOrCreateAssessmentEntry(identity1, null, entry, "top", Boolean.TRUE, null);
		ae1.setCompletion(Double.valueOf(90));
		assessmentService.updateAssessmentEntry(ae1);
		AssessmentEntry ae1Sub = assessmentService.getOrCreateAssessmentEntry(identity1, null, entry, "sub", Boolean.FALSE, null);
		ae1Sub.setCompletion(Double.valueOf(80));
		assessmentService.updateAssessmentEntry(ae1Sub);
		AssessmentEntry ae2 = assessmentService.getOrCreateAssessmentEntry(identity2, null, entry, "top", Boolean.TRUE, null);
		ae2.setCompletion(Double.valueOf(70));
		assessmentService.updateAssessmentEntry(ae2);
		AssessmentEntry aeOtherIdentity = assessmentService.getOrCreateAssessmentEntry(identityOther, null, entry, "top", Boolean.TRUE, null);
		aeOtherIdentity.setCompletion(Double.valueOf(60));
		assessmentService.updateAssessmentEntry(aeOtherIdentity);
		AssessmentEntry aeOtherEntry = assessmentService.getOrCreateAssessmentEntry(identity1, null, entryOther, "top", Boolean.TRUE, null);
		aeOtherEntry.setCompletion(Double.valueOf(50));
		assessmentService.updateAssessmentEntry(aeOtherEntry);
		dbInstance.commitAndCloseSession();
		
		Map<Long, Double> completions = sut.getRootCompletions(entry, asList(identity1, identity2));
		
		assertThat(completions).hasSize(2);
		assertThat(completions.get(identity1.getKey())).isEqualTo(90);
		assertThat(completions.get(identity2.getKey())).isEqualTo(70);
	}


}
