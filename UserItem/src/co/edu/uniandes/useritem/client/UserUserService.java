package co.edu.uniandes.useritem.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("userUserRemoteService")
public interface UserUserService extends RemoteService {

	List<String> getUserUserRecommended(String user, int numVecinos, double numSimilarity, int indexType);
	
}
