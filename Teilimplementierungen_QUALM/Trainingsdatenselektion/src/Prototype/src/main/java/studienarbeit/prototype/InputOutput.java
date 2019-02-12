package studienarbeit.prototype;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FilenameUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;


/**Provides methods for input and output
 */
public class InputOutput {
	StopwordRemoval stopWordRemoval;
	AnalysisEngine segmenter;
	
	
	/**Initialize the segmenter to be used, as well as the stopword removal
	 * 
	 * @throws ResourceInitializationException
	 */
	InputOutput () throws ResourceInitializationException {
		stopWordRemoval = new StopwordRemoval();
		
        segmenter = createEngine(
                OpenNlpSegmenter.class,
                OpenNlpSegmenter.PARAM_LANGUAGE, "en");
	}
	

	/**Read and processes the training data.
	 * 
	 * All .txt files in the training_data folder are read, creating a corpus representation
	 * for each one, with the name of the file used as identifier.
	 * 
	 * @return list of training corpora
	 * @throws FileNotFoundException
	 * @throws UIMAException
	 */
	public List<TextData> getTrainingData() throws FileNotFoundException, UIMAException {
		String training_data_folder = "./training_data";
		File folder = new File (training_data_folder);
		List<TextData> training_data = new ArrayList<TextData>();
		
		// read all .txt files in the training data folder, ignore all other files and folders
		for (File file : folder.listFiles()) {
			if (file.isFile()) {
				String filename = file.getName();
				String extension = FilenameUtils.getExtension(filename);
				if (extension.equals("txt")) {
					String input = readFile(training_data_folder + "/" + filename);
					List<String> data = processInput(input);
					
					//the name of the corpus is the name of the file, without the file extension
					String corpus_name = filename.substring(0, filename.lastIndexOf("."));
				
					training_data.add(new TextData(corpus_name, data));
				}
			}
		}
		return training_data;
	}
	
	/**Read input.txt located in the input folder, and return a corpus representation.
	 * 
	 * @return the content of the input.txt as a corpus representation
	 * @throws FileNotFoundException
	 * @throws UIMAException
	 */
	public TextData getInputData() throws FileNotFoundException, UIMAException {
		String name = "Input Data";
		
		List<String> data = new ArrayList<String>();
		String string = readFile("./input/input.txt");
		
		data = processInput(string);
		
		TextData inputData = new TextData(name, data);
		return inputData;
	}
	
	
	/**Write the output to the specified file in the output directory. 
	 * Fails with error when the file cannot be written, for example if it is write-protected or locked.
	 * Also fails if the output directory does not exist.
	 * 
	 * @param filename name of the file
	 * @param the text to be written
	 */
	public void writeOutputFile(String filename, String data) {
		String directory = "output/";
		try {
			PrintWriter out = new PrintWriter(directory + filename);
			out.write(data);
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error: could not write output file");
		}
	}
	
	/**helper method to read a text file
	 * @param name name of the file
	 * @return content of the file as String
	 * @throws FileNotFoundException
	 */
	private String readFile (String name) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(name));
		String content = scanner.useDelimiter("\\Z").next();
		scanner.close();
		return content;
	}
	
	

	/**Process the input string by segmenting the input string and removing stopwords.
	 * @param input input string
	 * @return corpus representation as a list of tokens
	 * @throws UIMAException
	 */
	private List<String> processInput (String input) throws UIMAException {
		List<String> corpus = new ArrayList<String>();

		JCas jCas = segment(input);
        
        jCas = stopWordRemoval.process(jCas);

        corpus = getStringList(jCas);
		return corpus;
	}
	
	/**Segment the input string, splitting it into tokens
	 * @param input the input string
	 * @return document representation
	 * @throws UIMAException
	 */
	private JCas segment (String input) throws UIMAException {
		JCas jCas = JCasFactory.createJCas();	
		
		jCas.setDocumentText(input);
		segmenter.process(jCas);
		
		return jCas;
	}
	
	
	/**Converts the jcas document into a list of tokens, represented as list of strings
	 * 
	 * @param jcas the document to be converted
	 * @return corpus representation as a list of tokens
	 */
	private static List<String> getStringList (JCas jcas) {
		List<String> corpus = new ArrayList<String>();
		Collection<Token> tokens = JCasUtil.select(jcas, Token.class);
        
		for (Token t : tokens) {
        	corpus.add(t.getCoveredText());
        } 
		return corpus;
	}

}
