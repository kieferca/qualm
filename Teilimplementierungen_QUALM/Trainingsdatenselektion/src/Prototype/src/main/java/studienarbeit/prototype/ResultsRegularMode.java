package studienarbeit.prototype;


/**Helper class to store results for the regular Mode
 */
public class ResultsRegularMode implements Comparable<ResultsRegularMode> {
	String name;
	double score;
	
	ResultsRegularMode (String name, double score) {
		this.name = name;
		this.score = score;
	}

	//comparison method to allow easy sorting according to similarity scores
	public int compareTo(ResultsRegularMode other) {
		int a = Double.compare(this.score, other.score); 
	    return a;
	}


	
}
