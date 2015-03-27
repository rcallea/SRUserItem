package co.edu.uniandes.useritem.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.recommender101.data.Rating;
import org.recommender101.recommender.baseline.NearestNeighbors;

import co.edu.uniandes.useritem.shared.IndexType;
import co.edu.uniandes.useritem.client.UserService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserServiceImpl extends RemoteServiceServlet implements UserService {
	private ItemItemPrediction iip = new ItemItemPrediction();
	
	@Override
	public String[] sendUser(String user) {
		UserDb db=new UserDb();
		String[] ret=new String[1];
		ret[0] = "" + db.saveUser(user);
		return(ret);
	}

	@Override
	public String[] getUserList() {
		UserDb db=new UserDb();
		String[] ret= db.getUserList();
		return(ret);
	}

	@Override
	public String[] getMovieList(String movie) {
		UserDb db=new UserDb();
		String[] ret= db.getMovieList(movie);
		return(ret);
	}
	
	@Override
	public String[] sendMovie(String movie) {
		UserDb db=new UserDb();
		String[] ret= new String[1];
		ret[0]="" + db.saveMovie(movie);
		return(ret);
	}

	@Override
	public String[] getIndexes(String values) {
		String[] params=values.split("\\|");
		String[] ret={}, jaccard={};
		List<String> cosine;
		List<String> pearson;
		int results=0;
		int level=0;
		int number=0;
		int user=0;
		try {
			number = Integer.parseInt(params[0]);
			level = Integer.parseInt(params[1]);
			results = Integer.parseInt(params[2]);
			user = Integer.parseInt(params[3]);
		}catch (NumberFormatException e) {
			
		}
		
		if(results!=0 && number!=0) {
			ret = new String[results*3];
			int i=0;
			iip.init(number, level);
			iip.fillOtherRated(number, user, results);
			jaccard=this.jaccard(number,level,results);
			cosine=iip.cosine(results, user);
			pearson=iip.pearson(results, user);
			
			ret=new String[jaccard.length + cosine.size() + 1 + pearson.size() + 1];
			for(String s:jaccard) {
				ret[i++]=s;
			}

			ret[i++]="---";
			for(String s:cosine) {
				ret[i++]=s;
			}
			
			ret[i++]="---";
			for(String s:pearson) {
				ret[i++]=s;
			}
			
		}
		return ret;
	}
	
	public String[] jaccard(int number, double level, int results) {
		String[] ret=new String[results];
		for(int i=0;i<results;i++) {
			ret[i]="" + 10*i + "|" + 11*i + "|" + 12*i + "|" + 13*i; 
		}
		return ret;
	}

	public String[] getStatistics() {

		Db db=new Db();
		return db.getStatistics();
	}
	
	public List<String[]> getMoviesMostRatings() {
	
		Db db=new Db();
		return db.getMoviesMostRatings();
	}

	public List<String[]> getMoviesUser(String user) {
		
		Db db=new Db();
		return db.getMoviesByUser(user);
	}
}
