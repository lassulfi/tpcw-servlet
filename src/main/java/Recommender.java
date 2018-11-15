
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;

import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;

import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 * @author esoft12 e esoft25
 * Esta classe é a implementação da tarefa C02T05
 */
public class Recommender {
	
	private File file;
	
	public Recommender(File _file) {		
	    file = _file;
	}
	
	public List<RecommendedItem> GetRecommender() throws TasteException, IOException
	{
		 //Creating data model
	    DataModel datamodel = new FileDataModel(file); //data
	 
	    //Creating UserSimilarity object.
	    UserSimilarity usersimilarity = new PearsonCorrelationSimilarity(datamodel);
	 
	    //Creating UserNeighbourHHood object.
	    UserNeighborhood userneighborhood = new ThresholdUserNeighborhood(3.0, usersimilarity, datamodel);
	 
	    //Create UserRecomender
	    UserBasedRecommender recommender = new GenericUserBasedRecommender(datamodel, userneighborhood, usersimilarity);
	    
	    FileReader fr = new FileReader(file);
	    BufferedReader bf = new BufferedReader(fr);
	    String linha = bf.readLine();
	    String[] s = linha.split(",");
	    System.out.println("codigo cliente: " + s[0]);
	   
	    return  recommender.recommend(Integer.parseInt(s[0]), 5);				  
	}	
	
	public void SetFile(File _file)
	{
		 file = _file;
	}
}
