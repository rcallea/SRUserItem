package co.edu.uniandes.useritem.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("userRemoteService")
public interface UserService extends RemoteService{
	String[] sendUser(String user);
	String[] getUserList();
	String[] getMovieList(String movie);
	String[] sendMovie(String movie);
	String[] getIndexes(String values);

	//Servicios de Deisy
	String[] getStatistics();
	List<String[]> getMoviesMostRatings();
	List<String[]> getMoviesUser(String user);
}
