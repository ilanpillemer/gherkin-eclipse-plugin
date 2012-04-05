package za.co.hardlyhere.gherkin.eclipse.plugin.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.*;
import org.eclipse.jface.text.*;


public class GherkinScanner extends RuleBasedScanner {
	
	// TODO: I18N
	private static String[] fgKeywords= {"Scenario", "Scenarios", "Feature", "Outline"};
	private static String[] fgSteps= { "Given", "When", "Then", "And"};

	public GherkinScanner(ColorManager manager) {
		IToken keyword= new Token(new TextAttribute(manager.getColor(IGherkinColorConstants.KEYWORD)));
		IToken tag= new Token(new TextAttribute(manager.getColor(IGherkinColorConstants.KEYWORD)));
		IToken step= new Token(new TextAttribute(manager.getColor(IGherkinColorConstants.STEP)));
		IToken string= new Token(new TextAttribute(manager.getColor(IGherkinColorConstants.STRING)));
		IToken comment= new Token(new TextAttribute(manager.getColor(IGherkinColorConstants.GHERKIN_COMMENT)));
		IToken other= new Token(new TextAttribute(manager.getColor(IGherkinColorConstants.DEFAULT)));

		List rules= new ArrayList();

		// Add rule for single line comments.
		rules.add(new EndOfLineRule("//", comment)); //$NON-NLS-1$

		// Add rule for strings and character constants.
		rules.add(new SingleLineRule("\"", "\"", string, '\\')); //$NON-NLS-2$ //$NON-NLS-1$
		rules.add(new SingleLineRule("'", "'", string, '\\')); //$NON-NLS-2$ //$NON-NLS-1$
		
		// Add a rule for tags
		rules.add(new SingleLineRule("@", " ", tag )); //$NON-NLS-2$ //$NON-NLS-1$
		

		// Add generic whitespace rule.
		rules.add(new WhitespaceRule(new GherkinWhitespaceDetector()));

		// Add word rule for keywords, types, and constants.
		WordRule wordRule= new WordRule(new GherkinWordDetector(), other);
		WordRule wordStarStepRule= new WordRule(new GherkinStarStepWordDetector(), other);
		
		// Add rule to colour the * that can be used instead of steps
		wordStarStepRule.addWord("*", keyword);
		for (int i= 0; i < fgKeywords.length; i++)
			wordRule.addWord(fgKeywords[i], keyword);
		for (int i= 0; i < fgSteps.length; i++)
			wordRule.addWord(fgSteps[i], step);
//		for (int i= 0; i < fgConstants.length; i++)
//			wordRule.addWord(fgConstants[i], type);
		rules.add(wordRule);
		rules.add(wordStarStepRule);

		IRule[] result= new IRule[rules.size()];
		rules.toArray(result);
		setRules(result);
	}
}
