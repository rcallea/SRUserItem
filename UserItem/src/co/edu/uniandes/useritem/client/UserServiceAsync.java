package co.edu.uniandes.useritem.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {
	//Ojo, lo que va dentro del <> del asyncCallBack debe ser la clase de retorno del método del service
	void sendUser(String user, AsyncCallback<String[]> callback);
	void getUserList(AsyncCallback<String[]> callback);
	void getMovieList(String movie, AsyncCallback<String[]> callback);
	void sendMovie(String movie, AsyncCallback<String[]> callback);
	void getIndexes(String values, AsyncCallback<String[]> callback);

	//Servicios de Deisy
	void getStatistics(AsyncCallback<String[]> callback);
	void getMoviesMostRatings(AsyncCallback<List<String[]>> callback);
	void getMoviesUser(String user, AsyncCallback<List<String[]>> callback);

}
