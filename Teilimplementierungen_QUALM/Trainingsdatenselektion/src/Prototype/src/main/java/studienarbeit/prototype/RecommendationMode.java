package studienarbeit.prototype;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.uima.UIMAException;

import dkpro.similarity.algorithms.api.SimilarityException;
import dkpro.similarity.algorithms.api.TextSimilarityMeasure;
import dkpro.similarity.algorithms.lexical.string.CosineSimilarity;
import dkpro.similarity.algorithms.sspace.LsaSimilarityMeasure;



/**provides methods to compute the recommendation
 */
public class RecommendationMode {
	
	RecommendationMode () throws IOException {
	}
	
	private static String RESULT_FILE = "result.txt";
	private String recommendedCorpus = null;
	
	
	
	/**Execute the similarity comparisons and write the results to file.
	 * 
	 * Reads the training data sets and the input. Then similarity comparisons are performed.
	 * The training data sets are ranked according to their similarity with the input data.
	 * The training data set with the highest similarity is recommended.
	 * 
	 * The results are written to file result.txt, containing the ranking of all 
	 * corpora as well as the respective similarity score.
	 * 
	 * 
	 * @param useMetric the metric to be used
	 * @throws SimilarityException
	 * @throws UIMAException
	 * @throws IOException
	 */
	public void run (String useMetric) throws SimilarityException, UIMAException, IOException {
		InputOutput io = new InputOutput();
		TextData input = io.getInputData();
		List<TextData> training_data = io.getTrainingData();
		List<ResultsRegularMode> results = new ArrayList<ResultsRegularMode>();
		TextSimilarityMeasure metric;
		
		//choose which metric to use, based on command line parameter
		//default is the cosine similarity metric
		if (useMetric.equals("LSA")) {
			LSA a = new LSA();
			String path = a.createModel(true);
			metric = new LsaSimilarityMeasure(new File(path));
		} else {
			metric = new CosineSimilarity();
		}
		
		//compute the similarity for the input data with each training data set
		for (TextData i : training_data) {
			double score = 	metric.getSimilarity(input.data, i.data);
			results.add(new ResultsRegularMode(i.name, score));
		}
		
		String recommendation = createRecommendation (results);
		io.writeOutputFile(RESULT_FILE, recommendation);
	}
	
	/**Return the name of the recommended training data set.
	 * The method run() must be executed first to actually compute the recommendation. 
	 * Otherwise an error message will be returned instead.
	 * 
	 * This method is currently not used and included as an interface
	 * for potential extensions of the prototype.
	 * 
	 * @return the name of the recommended training data set
	 */
	public String getRecommendedCorpusName () {
		if (recommendedCorpus == null) {
			return "Error - must use the run() method first";
		} else {
			return recommendedCorpus;
		}
	}
	
	
	/**Create a recommendation.
	 * This is done ranking the Corpora according to their similarity with the input data.
	 * The firt corpus has the highest score and is therefore the recommended training data set.
	 * 
	 * The format is:
	 * Name_of_corpus whitespace score
	 *  
	 * 
	 * @param results the results of the similarity comparisons
	 * @return String representing the recommendation
	 */
	private String createRecommendation (List<ResultsRegularMode> results) {
		String output = new String();
		String br = System.getProperty("line.separator");
		
		// Sort the results by the obtained score, with the highest on top.
		// This means that the corpus with the highest similarity score is recommended
		// as training data
		Collections.sort(results, Collections.reverseOrder());
		
		//The first corpus is recommended by the system
		recommendedCorpus = results.get(0).name;

		for (ResultsRegularMode i : results) {
			//The output score is displayed rounded to 3 decimal places
			String score =  String.format("%.3f", i.score);
			output += i.name + " " + score + br;
		}
		return output;
	}
}
