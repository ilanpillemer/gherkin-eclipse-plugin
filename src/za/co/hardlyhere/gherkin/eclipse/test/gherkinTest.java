package za.co.hardlyhere.gherkin.eclipse.test;

import java.util.List;

import gherkin.I18n;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;

public class gherkinTest {

	@Test public void can_create_gherkin_i18n_for_en() {
		gherkin.I18n gherkin = new I18n("en");
		Assert.assertTrue("code is en but was " + gherkin.getIsoCode(), gherkin.getIsoCode().equals("en"));
	}
	
	@Test public void code_keywords_for_english_contains_When() {
		gherkin.I18n gherkin = new I18n("en");
		List<String> codeKeywords = gherkin.getCodeKeywords();
		Assert.assertTrue(codeKeywords.contains("When"));
		
	}
	
	@Test public void step_keywords_for_english_contains_star() {
		gherkin.I18n gherkin = new I18n("en");
		List<String> stepKeywords = gherkin.getStepKeywords();
		Assert.assertTrue(stepKeywords.contains("* "));
		
	}
	@Test public void _keywords_for_english_contains_dWhen() {
		gherkin.I18n gherkin = new I18n("en_PIRATE");
		List<String> codeKeywords = gherkin.getCodeKeywords();
		List<String> stepKeywords = gherkin.getStepKeywords();
		String keywordTable = gherkin.getKeywordTable();
		Assert.assertTrue(codeKeywords.contains("When"));		
	}
	
	@Test public void keywords_for_norway_contains_Egenskap_for_eature() {
		gherkin.I18n gherkin = new I18n("no");
		List<String> keywords = gherkin.keywords("feature");
		Assert.assertTrue(keywords.contains("Egenskap"));		
	}
	
	@Test public void keywords_for_germany_contains_Egenskap_for_eature() {
		gherkin.I18n gherkin = new I18n("de");
		List<String> keywords = gherkin.keywords("feature");
		String keywordTable = gherkin.getKeywordTable();
		int i =0;
//		Assert.assertTrue(keywords.contains("Egenskap"));		
	}

	
	@Test public void code_keywords_for_english_does_not_contains_nonsense() {
		gherkin.I18n gherkin = new I18n("en");
		List<String> codeKeywords = gherkin.getCodeKeywords();
		Assert.assertFalse(codeKeywords.contains("nonsense"));
		
	}
	
}
