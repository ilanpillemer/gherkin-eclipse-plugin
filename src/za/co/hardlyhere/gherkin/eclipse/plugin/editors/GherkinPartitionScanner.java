package za.co.hardlyhere.gherkin.eclipse.plugin.editors;

import org.eclipse.jface.text.rules.*;

public class GherkinPartitionScanner extends RuleBasedPartitionScanner {
	public final static String GHERKIN_COMMENT = "__gherkin_comment";
	public final static String GHERKIN_TAG = "__gherkin_tag";

	public GherkinPartitionScanner() {

		IToken xmlComment = new Token(GHERKIN_COMMENT);
		IToken tag = new Token(GHERKIN_TAG);

		IPredicateRule[] rules = new IPredicateRule[2];

		rules[0] = new MultiLineRule("<!--", "-->", xmlComment);
		rules[1] = new TagRule(tag);

		setPredicateRules(rules);
	}
}
