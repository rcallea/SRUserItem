package co.edu.uniandes.useritem.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserUserServiceAsync {

	void getUserUserRecommended(String user, int numVecinos, double numSimilarity, int indexType,
			AsyncCallback<List<String>> callback);
	
}
