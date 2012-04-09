package za.co.hardlyhere.gherkin.eclipse.plugin.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class GherkinConfiguration extends SourceViewerConfiguration {
	private GherkinDoubleClickStrategy doubleClickStrategy;
	private GherkinTagScanner tagScanner;
	private GherkinScanner scanner;
	private ColorManager colorManager;

	public GherkinConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			GherkinPartitionScanner.GHERKIN_COMMENT,
			GherkinPartitionScanner.GHERKIN_TAG };
	}
	public ITextDoubleClickStrategy getDoubleClickStrategy(
		ISourceViewer sourceViewer,
		String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new GherkinDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected GherkinScanner getGherkinScanner() {
		if (scanner == null) {
			scanner = new GherkinScanner(colorManager);
			scanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(IGherkinColorConstants.DEFAULT))));
		}
		return scanner;
	}
	protected GherkinTagScanner getGherkinTagScanner() {
		if (tagScanner == null) {
			tagScanner = new GherkinTagScanner(colorManager);
			tagScanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(IGherkinColorConstants.TAG))));
		}
		return tagScanner;
	}

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr =
			new DefaultDamagerRepairer(getGherkinTagScanner());
		reconciler.setDamager(dr, GherkinPartitionScanner.GHERKIN_TAG);
		reconciler.setRepairer(dr, GherkinPartitionScanner.GHERKIN_TAG);

		dr = new GherkinDamagerRepairer(getGherkinScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		NonRuleBasedDamagerRepairer ndr =
			new NonRuleBasedDamagerRepairer(
				new TextAttribute(
					colorManager.getColor(IGherkinColorConstants.GHERKIN_COMMENT)));
		reconciler.setDamager(ndr, GherkinPartitionScanner.GHERKIN_COMMENT);
		reconciler.setRepairer(ndr, GherkinPartitionScanner.GHERKIN_COMMENT);

		return reconciler;
	}

}