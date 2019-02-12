package studienarbeit.prototype;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;

import java.io.File;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.stopwordremover.StopWordRemover;



/**Provides stopword removal.
 * 
 * Uses the StopWordRemover provided by DKPro. All tokens that match those
 * in stopwords.txt are removed from the document.  * If the file does not exist or is empty, 
 * no stopwords will be removed. 
 * 
 */
public class StopwordRemoval {
	private AnalysisEngine stopwordremover;
	private String PATH = "stopwords.txt";
	private Boolean stopwordFileExists = false;
	
	/**Initialize the StopWordRemover provided by DKPro. If stopwords.txt does not exist,
	 * nothing happens.
	 * @throws ResourceInitializationException
	 */
	StopwordRemoval () throws ResourceInitializationException {
	File f = new File(PATH);
	//checks if the files stopwords.txt exists
	if(f.exists() && !f.isDirectory()) { 
		stopwordremover = createEngine(
	    		StopWordRemover.class,
	    		StopWordRemover.PARAM_MODEL_LOCATION, 
	    		PATH);
	    
	    stopwordFileExists = true;
	} else {
		stopwordFileExists = false;
	}
	}
	
	
	
	/**Remove stopwords.
	 * 
	 * The document is not changed if the file stopwords.txt is not found or is empty.
	 * 
	 * @param jCas the document
	 * @return document without stopwords
	 * @throws AnalysisEngineProcessException
	 */
	public JCas process (JCas jCas) throws AnalysisEngineProcessException {
		//only use stopwords removal if the file stopwords.txt exists
		if (stopwordFileExists) {
			stopwordremover.process(jCas);
		}
		return jCas;
	}
	
}
