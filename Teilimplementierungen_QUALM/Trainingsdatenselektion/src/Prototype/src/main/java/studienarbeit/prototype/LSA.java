package studienarbeit.prototype;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;
import dkpro.similarity.algorithms.sspace.util.LsaIndexer;


import java.io.IOException;






/**Provides method to create LSA model
 */
public class LSA {
	
	/**Creates the model required for LSA.
	 * 
	 * Reads all training data sets. The input file can be included as well by passing
	 * includeInputFile = TRUE. This is currently used in Recommendation mode. 
	 * 
	 * @param includeInputFile TRUE if input.txt should be included (for Regular mode), FALSE otherwise
	 * @return the path to the created model
	 * @throws UIMAException
	 * @throws IOException
	 */
	String createModel (Boolean includeInputFile) throws UIMAException, IOException {
		String[] location;
		String MODEL_PATH = "training_data/sspace";
        String PATH = "training_data/sspace/test.sspace";
		
		if (includeInputFile) {
			location = new String[] {"[+]training_data/*.txt", "[+]input/input.txt"};
		} else 	{
			location = new String[] {"[+]training_data/*.txt"};
		}
        
        CollectionReader reader = createReader(
                TextReader.class,
                TextReader.PARAM_PATTERNS, location);
        
        AnalysisEngine segmenter = createEngine(
                OpenNlpSegmenter.class,
                OpenNlpSegmenter.PARAM_LANGUAGE, "de");
        
        AnalysisEngine indexTermGenerator = createEngine(
                LsaIndexer.class,
                LsaIndexer.PARAM_INDEX_PATH, MODEL_PATH,
                LsaIndexer.PARAM_FEATURE_PATH, Token.class.getName());
        
        SimplePipeline.runPipeline(reader, segmenter, indexTermGenerator);
        return PATH;
	}
}
