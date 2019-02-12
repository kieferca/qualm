package studienarbeit.prototype;

import java.io.IOException;

import org.apache.uima.UIMAException;

import dkpro.similarity.algorithms.api.SimilarityException;


/**Main class of the Prototype System for the thesis
 * "Fit of training data and text data - automatic identification
 * of the best fitting training data" which is based on a concept presented by Cornelia Kiefer in [1]
 * [1] Kiefer, Cornelia (2016): Assessing the Quality of Unstructured Data: An Initial Overview.
 *  In: Ralf Krestel, Davide Mottin und Emmanuel Müller (Hg.): CEUR Workshop Proceedings. 
 * Proceedings of the LWDA. Potsdam. Aachen (CEUR Workshop Proceedings), S. 62–73. 
 * Handles command line parameters.
 * @author Andreas Laukart
 *
 */
public class Core {

	/**Main method.
	 * 
	 * Runs Recommendation mode per default, using the cosine similarity metric.
	 * The command line parameter "LSA" uses the LSA metric instead. 
	 * The command line parameter "eval" triggers Evaluation Mode.
	 * Parameters are case sensitive. All other values are ignored resulting in default configuration.
	 * @param args command line parameters
	 * @throws IOException
	 * @throws SimilarityException
	 * @throws UIMAException
	 */
	public static void main(String[] args) throws IOException, SimilarityException, UIMAException {

		if (args.length > 0) {
			if (args[0].equals("eval")) {
				EvaluationMode mode = new EvaluationMode();
				mode.run();
			} else if (args[0].equals("LSA")) {
				RecommendationMode mode = new RecommendationMode();
				mode.run("LSA");
			} else {
				RecommendationMode mode = new RecommendationMode();
				mode.run("cosine");
			}
			
		} else {
			RecommendationMode mode = new RecommendationMode();
			mode.run("cosine");
		}
	}

}
