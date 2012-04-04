package za.co.hardlyhere.gherkin.eclipse.plugin.editors;

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.rules.*;

public class GherkinTagScanner extends RuleBasedScanner {

	public GherkinTagScanner(ColorManager manager) {
		IToken string =
			new Token(
				new TextAttribute(manager.getColor(IGherkinColorConstants.STRING)));

		IRule[] rules = new IRule[3];

		// Add rule for double quotes
		rules[0] = new SingleLineRule("\"", "\"", string, '\\');
		// Add a rule for single quotes
		rules[1] = new SingleLineRule("'", "'", string, '\\');
		// Add generic whitespace rule.
		rules[2] = new WhitespaceRule(new GherkinWhitespaceDetector());

		setRules(rules);
	}
}
