import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class CloneDetector {

	private Strategy strategy;

	/**
	 * allows for specific strategy to be passed through
	 * @param strategy
	 */
	public CloneDetector(Strategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * sets the chosen strategy
	 * @param strategy
	 */
	public void setStrategy (Strategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * 
	 * @param fileList
	 * @return
	 * @throws FileNotFoundException
	 */
	public HashMap<String,ArrayList<String>> preformCheck (File[] fileList) throws FileNotFoundException{
		return this.strategy.cloneCheck(fileList);
	}

}
