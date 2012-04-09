package za.co.hardlyhere.gherkin.eclipse.plugin.editors;

import org.eclipse.ui.editors.text.TextEditor;

public class GherkinEditor extends TextEditor {

	private ColorManager colorManager;

	public GherkinEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new GherkinConfiguration(colorManager));
		setDocumentProvider(new GherkinDocumentProvider());
	}
	
//	public void resetSourceViewConfiguration() {
//		colorManager = new ColorManager();
//		setSourceViewerConfiguration(new GherkinConfiguration(colorManager));
//	}
	
	
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
