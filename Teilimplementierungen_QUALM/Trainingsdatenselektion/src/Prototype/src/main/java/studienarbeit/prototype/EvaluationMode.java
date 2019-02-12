package studienarbeit.prototype;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UIMAException;

import dkpro.similarity.algorithms.api.SimilarityException;
import dkpro.similarity.algorithms.api.TextSimilarityMeasure;
import dkpro.similarity.algorithms.lexical.string.CosineSimilarity;
import dkpro.similarity.algorithms.sspace.LsaSimilarityMeasure;

/**Computes similarity for all enabled metrics between all training data sets.
 */
public class EvaluationMode {
	
	EvaluationMode () throws IOException {
	}
	
	private static String EVALUATION_FILE = "evaluation.txt";
	
	private List<String> namesOfMetrics = new ArrayList<String>();
	
	/** Execute the evaluation and write the output to file.
	 * 
	 * Reads the training data sets. Then pairwise similarity comparisons are performed
	 * between all training data sets. The results are not ranked or sorted and
	 * written to the files is "evaluation.txt".
	 *  
	 * The output contains a line for each combination of training data sets,
	 * containing the names of the data sets and the results for all enabled metrics.
	 * The fist line contains a header with the names of the respective metrics. 
	 * 
	 * @throws SimilarityException
	 * @throws UIMAException
	 * @throws IOException
	 */
	void run () throws SimilarityException, UIMAException, IOException {
		InputOutput io = new InputOutput();
		List<TextData> training_data = io.getTrainingData();
		List<ResultsEvalMode> results = new ArrayList<ResultsEvalMode>();
		
		//Levenshtein and GreedyStringTiling  metrics are disabled because they failed to compute
		//values for the data sets used in this thesis. 
		//Cosine and LSA performed much better than WordNGramJaccard and are recommendend,
		//thus WordNGramJaccard is disabled.
		//All of these metrics can be enabled by uncommenting the respective code
		//and adding the respective metric to the metrics list
		//see LSA and cosine for comparison.

//		TextSimilarityMeasure greedy = new GreedyStringTiling(3);
//		TextSimilarityMeasure leven = new LevenshteinComparator();
//		TextSimilarityMeasure ngram = new WordNGramJaccardMeasure(2);
		
		TextSimilarityMeasure cosine = new CosineSimilarity();
		
		LSA a = new LSA();
		String path = a.createModel(false);
		TextSimilarityMeasure lsa = new LsaSimilarityMeasure(new File(path));
		
		List<TextSimilarityMeasure> metrics = new ArrayList<TextSimilarityMeasure>();
		
		metrics.add(cosine);
		namesOfMetrics.add("Cosine");
		
		metrics.add(lsa);
		namesOfMetrics.add("LSA");
		
		results = getSimilarityForMetrics(metrics, training_data);
		
		String output = getOutput (results);

		io.writeOutputFile(EVALUATION_FILE, output);
	}
	
	
	
	/**Compute the similarity and return the results.
	 * 
	 * For each metric, all training data sets are compared with each other.
	 * For comparisons within the same corpus, a training / test data split
	 * has to be used to ensure valid evaluation. This is handled in NLTK.
	 * 
	 * @param metrics the metrics to be used during evaluation
	 * @param training_data the training data sets between which similarity should be computed
	 * @return results of the similarity comparisons
	 * @throws SimilarityException
	 */
	private List<ResultsEvalMode> getSimilarityForMetrics(
			List<TextSimilarityMeasure> metrics, List<TextData> training_data) throws SimilarityException {
		List<ResultsEvalMode> results = new ArrayList<ResultsEvalMode>();
		// compare each data set with all others (excluding the comparison with itself)  
		for (TextData k : training_data) {
			for (TextData i : training_data) {
				if (!k.name.equals(i.name)) {
					List<Double> scores = new ArrayList<Double>(); 	
					for (TextSimilarityMeasure metric : metrics) {
						scores.add(metric.getSimilarity(k.data, i.data));
					}
					results.add(new ResultsEvalMode(k.name, i.name, scores));
				}
			}
		}
		return results;
	}
	
	
	/**Format the results of the evaluation.
	 * 
	 * For each comparison, one line is created.
	 * The first two columns contain the name of the respective corpora.
	 * The remaining values are the similarity scores for the respective metrics.
	 * 
	 * Everything is separated by whitespaces.
	 * 
	 * The first line, the header, contains the names of the metrics.
	 * 
	 * @param results the results of the similarity computations
	 * @return the output as String
	 */
	private String getOutput (List<ResultsEvalMode> results) {
		//creates a new line
		String br = System.getProperty("line.separator");

		String output = new String();
		String header = "Cropus1 Corpus2";
		for (String name : namesOfMetrics) {
			header += " " + name;
		}
		//the first line of the results file is a header containing the names of the metrics
		output = header + br;
		
		for (ResultsEvalMode i : results) {
			String scores = new String();
			for (double score : i.scores) {
				//The output score is displayed rounded to 3 decimal places
				scores += " " + String.format("%.3f", score);
			}
			//each line contains the names of the two corpora, as well as their similarity scores for all
			//used metrics
			output += i.name + " " + i.name1 + scores + br;
		}
		return output;
	}
}
