package co.edu.uniandes.useritem.server;

import org.recommender101.data.DataModel;
import org.recommender101.recommender.baseline.NearestNeighbors;

public class UserUserPrediction {
	
	NearestNeighbors rec = new NearestNeighbors();
	UserDb db= new UserDb();
	
	
	public void prepareData(int neighbors) {
		try {
			
			rec.setDataModel(Db.model);
			rec.setNeighbors("" + neighbors);
			rec.setItemBased("true");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public String[] cosine(int from, int number) {
		rec.setCosineSimilarity("true");
		String[] toRateArray = db.getRatings(from + 1, number+1);
		try {
			rec.init();
			for(int i=0;i<toRateArray.length;i++) {
				String[] values=toRateArray[i].split("\\|");
				int movieId=Integer.parseInt(values[1]);
				int userId=Integer.parseInt(values[0]);
				double prediction=rec.predictRating(userId, movieId);
				toRateArray[i]=toRateArray[i]+"|" + prediction;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return(toRateArray);
	}

	public String[] pearson(int from, int number) {
		rec.setCosineSimilarity("false");
		String[] toRateArray = db.getRatings(from + 1, number);
		try {
			rec.init();
			for(int i=0;i<toRateArray.length;i++) {
				String[] values=toRateArray[i].split("\\|");
				int movieId=Integer.parseInt(values[1]);
				int userId=Integer.parseInt(values[0]);
				double prediction=rec.predictRating(userId, movieId);
				toRateArray[i]=toRateArray[i]+"|" + prediction;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return(toRateArray);
	}
}
