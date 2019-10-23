import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public interface Strategy {
	
	public HashMap<String,ArrayList<String>> cloneCheck(File[] fileList) throws FileNotFoundException;
	
	
}
