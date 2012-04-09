package za.co.hardlyhere.gherkin.eclipse.plugin.editors;

import gherkin.I18n;

import java.io.IOException;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.ITokenScanner;

public class GherkinDamagerRepairer extends DefaultDamagerRepairer {
	private static String code = "en";

	public GherkinDamagerRepairer(ITokenScanner scanner) {
		super(scanner);
		// TODO Auto-generated constructor stub
	}

	public IRegion getDamageRegion(ITypedRegion partition, DocumentEvent e,
			boolean documentPartitioningChanged) {
		IRegion damageRegion = super.getDamageRegion(partition, e,
				documentPartitioningChanged);

		if (fScanner instanceof GherkinScanner) {
			String newCode = determineGherkinLanguageMode(fDocument);
			if (!newCode.equals(code)) {
				code = newCode;
				GherkinScanner.setCode(code);
				((GherkinScanner) fScanner).configureRules();
				damageRegion = new Region(0, fDocument.getLength());
			}
		}
		return damageRegion;
	}

	private String determineGherkinLanguageMode(IDocument document) {
		String code = "en"; // default
		try {

			IRegion lineInformation = document.getLineInformation(0);
			int length = lineInformation.getLength();
			int offset = lineInformation.getOffset();
			String string = document.get(offset, length);
			if (string.contains("language")) {
				int indexOf = string.indexOf(":");
				code = string.substring((indexOf + 1)).trim();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return code;
	}
}
