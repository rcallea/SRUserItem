package co.edu.uniandes.useritem.server;

import java.util.ArrayList;
import java.util.List;

import org.recommender101.data.DataModel;
import org.recommender101.recommender.baseline.NearestNeighbors;

public class ItemItemPrediction {
	private DataModel dm = new DataModel();
	private NearestNeighbors rec = new NearestNeighbors();
	private UserDb db= new UserDb();
	private ArrayList<String> otherRated=new ArrayList<String>();
	
	public void init(int limitRatings, int neighbors) {
		this.rec.setDataModel(db.getRatings(limitRatings));
		this.rec.setMinNeighbors("" + neighbors);
		this.rec.setItemBased("true");
	}

	public void fillOtherRated(int limitRatings,int user, int rows){
		this.otherRated=(ArrayList<String>)this.db.getOtherRated(limitRatings,user,rows);
	}
	
	public List<String> cosine(int totalResults, int user) {
		ArrayList<String> toRateArray=new ArrayList<String>();
		try {
			rec.setCosineSimilarity("true");
			rec.init();
			
			
			List<Integer> list=rec.recommendItems(user);
			System.out.println("Recommended items item-item with cosine: ");
			for(int i=0; (i<totalResults&&i<list.size()) ;i++){
				float prediction=rec.predictRating(user, list.get(i));
				System.out.println(list.get(i) + "|" + prediction);
				toRateArray.add(list.get(i) + "|" + prediction);
			}
			
			toRateArray=(ArrayList<String>)this.db.getMovieName(toRateArray);
			toRateArray.add(0, "Resultado|Valor");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		toRateArray.add("---");
		toRateArray.add("Resultado|Valor");
		for(int i=0;i<this.otherRated.size();i++) {
			String[] otherMovie=this.otherRated.get(i).split("\\|");
			int movie=0;
			try {
				movie=Integer.parseInt(otherMovie[0]);
			} catch (NumberFormatException e) {}
			toRateArray.add(otherMovie[1] + "|" + otherMovie[2] + " Vs " + rec.predictRating(user, movie));
		}
		if(this.otherRated.size()==0) {
			toRateArray.add("No hay resultados|_");
		}

		return(toRateArray);
	}

	public List<String> pearson(int totalResults, int user) {
		ArrayList<String> toRateArray=new ArrayList<String>();
		try {
			rec.setCosineSimilarity("false");
			rec.init();
			
			
			List<Integer> list=rec.recommendItems(user);
			System.out.println("Recommended items item-item with Pearson: ");
			for(int i=0; (i<totalResults&&i<list.size()) ;i++){
				float prediction=rec.predictRating(user, list.get(i));
				System.out.println(list.get(i) + "|" + prediction);
				toRateArray.add(list.get(i) + "|" + prediction);
			}
			
			toRateArray=(ArrayList<String>)this.db.getMovieName(toRateArray);
			toRateArray.add(0, "Resultado|Valor");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		toRateArray.add("---");
		toRateArray.add("Resultado|Valor");
		for(int i=0;i<this.otherRated.size();i++) {
			String[] otherMovie=this.otherRated.get(i).split("\\|");
			int movie=0;
			try {
				movie=Integer.parseInt(otherMovie[0]);
			} catch (NumberFormatException e) {}
			toRateArray.add(otherMovie[1] + "|" + otherMovie[2] + " Vs " + rec.predictRating(user, movie));
		}
		if(this.otherRated.size()==0) {
			toRateArray.add("No hay resultados|_");
		}

		return(toRateArray);
	}
	
	public static void main(String[] args) {
		ItemItemPrediction iip=new ItemItemPrediction();
		iip.init(50000, 50);
		iip.cosine(5, 50);
	}
}
