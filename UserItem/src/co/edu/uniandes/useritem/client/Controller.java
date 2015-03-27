package co.edu.uniandes.useritem.client;


import java.util.List;

import co.edu.uniandes.useritem.server.SimilarityMeasure;
import co.edu.uniandes.useritem.shared.IndexType;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;

public class Controller implements ClickHandler, KeyUpHandler, EntryPoint, ChangeHandler {
	
	private UserView userView;
	private MovieView movieView;
	private ItemItemView itemItemView;
	private RSConstants constants = GWT.create(RSConstants.class);
	private HomeView homeView;
	private UserUserView userUserView;
	
	//private ArrayList<String> stocks = new ArrayList<String>();
	private UserServiceAsync userSvc = GWT.create(UserService.class);
	private UserUserServiceAsync userUserSvc = GWT.create(UserUserService.class);
	
	int panelNumber=0;

	@Override
	public void onKeyUp(KeyUpEvent event) {
		Window.alert(event.toString());
		
	}

	@Override
	public void onClick(ClickEvent event) {
		String sender;
		if(event.getSource() instanceof Button) {
			sender = ((Button) event.getSource()).getText();
			
			if(sender.equals(this.constants.uiSend())) {
				if(this.userView.validate()) {
					this.sendUser();
				}
			}
			else if(sender.equals(this.constants.miSend())) {
				if(this.movieView.validate()) {
					this.sendMovie();
				}
			}
			else if(sender.equals(this.constants.miFilterButton())) {
				if(this.movieView.validateFilter()) {
					//Llame al que actualiza las películas
					this.movieView.getMovies();
				}
			}
			else if(sender.equals(this.constants.iiSend())) {
				if(this.itemItemView.validate()) {
					//Llame al que actualiza las películas
					this.getIndexesItemItem();
				}
			}
			else if(sender.equals(this.constants.ruiRecUsuario())) {
				if(this.userUserView.validate()) {
					this.getUserUserRecommended();
				}
			}
			else if(sender.equals(this.constants.ruiSearchUser())) {
				if(this.userUserView.validate()) {
					this.getMoviesUser();
				}
			}
			/*else if(sender.equals(this.constants.hiSend())) {
					this.getStatisticsData();
			}*/
		}
	}
	
	@Override
	public void onModuleLoad() {
		this.userView=new UserView();
		this.userView.setController(this);
		this.userView.generateUI();
		this.movieView=new MovieView();
		this.movieView.setController(this);
		this.movieView.generateUI();
		this.itemItemView=new ItemItemView();
		this.itemItemView.setController(this);
		this.itemItemView.generateUI();
		this.homeView=new HomeView();
		this.homeView.setController(this);
		this.homeView.generateUI();
		this.userUserView=new UserUserView();
		this.userUserView.setController(this);
		this.userUserView.generateUI();
		
		this.getStatisticsData();
	}
	
	private void sendUser() {
		if(userSvc==null) userSvc = GWT.create(UserService.class);
		String user=this.userView.getTextBoxUsername().getText() + "|"
				+ this.userView.getTextBoxName().getText() + "|"
				+ this.userView.getTextBoxTwitter().getText() + "|" 
				+ this.userView.getTextBoxEmail().getText();
		
		AsyncCallback<String[]> callback = new AsyncCallback<String[]>() {
			public void onFailure(Throwable caught) {
			}

			public void onSuccess(String[] result) {
				updateUsers();
				Window.alert("Registro actualizado");
			}
		};

		// Make the call to the stock price service.
		userSvc.sendUser(user, callback);
	}

	private void sendMovie() {
		if(userSvc==null) userSvc = GWT.create(UserService.class);
		String movie=this.movieView.getListBoxUsername().getItemText(this.movieView.getListBoxUsername().getSelectedIndex()) + "|"
				+ this.movieView.getListBoxMovie().getValue(this.movieView.getListBoxMovie().getSelectedIndex()) + "|"
				+ this.movieView.getListBoxGrade().getItemText(this.movieView.getListBoxGrade().getSelectedIndex());
		
		AsyncCallback<String[]> callback = new AsyncCallback<String[]>() {
			public void onFailure(Throwable caught) {
			}

			public void onSuccess(String[] result) {
				Window.alert("Registro actualizado");
			}
		};

		// Make the call to the stock price service.
		userSvc.sendMovie(movie, callback);
	}
	
	private void getIndexesItemItem() {
		if(userSvc==null) userSvc = GWT.create(UserService.class);
		String values=this.itemItemView.getTextBoxNumber().getText() + "|"
				+ this.itemItemView.getTextBoxLevel().getText() + "|"
				+ this.itemItemView.getTextBoxResults().getText() + "|"
				+ this.itemItemView.getTextBoxUserIdValue().getText();
		
		AsyncCallback<String[]> callback = new AsyncCallback<String[]>() {
			public void onFailure(Throwable caught) {
		        // TODO: Do something with errors.
			}

			public void onSuccess(String[] result) {
				//TODO reemplazar aquí lo que está bien updateTable(result);Aquí enviar a almacenar
				updateIndexesItemItem(result);
			}
		};

		// Make the call to the stock price service.
		userSvc.getIndexes(values, callback);
	}

	private void updateUsers() {
		this.movieView.getUsers();
	}
	
	private void updateIndexesItemItem(String[] data) {
		this.itemItemView.updateTables(data);
	}

	@Override
	public void onChange(ChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	private void getStatisticsData() {
		if(userSvc==null) userSvc = GWT.create(UserService.class);
		
		AsyncCallback<String[]> callback = new AsyncCallback<String[]>() {
			
			public void onFailure(Throwable caught) {
		        // TODO: Do something with errors.
			}

			public void onSuccess(String[] result) {
				int i=1;
				homeView.getStats().setText(0, 0, "Concepto estadistico");
				homeView.getStats().setText(0, 1, "Valor");
				homeView.getStats().setStyleName("table table-striped");
				for (String stringStatistic : result) {
					String[] cols=stringStatistic.split("\\|");
					for(int j=0;j<cols.length;j++) {
						homeView.getStats().setText(i, j, cols[j]);
					}
					i++;
				}
				
			}
		};

		       // Make the call to the stock price service.
		userSvc.getStatistics(callback);
		
		AsyncCallback<List<String[]>> callbackTop = new AsyncCallback<List<String[]>>() {
			
			public void onFailure(Throwable caught) {
		        // TODO: Do something with errors.
			}

			public void onSuccess(List<String[]> result) {
				
				String html = "<table>";
				html += "<tr>";
				html += "<td style='padding-right:20px'>";
				html += "<img src='/images/top10films.jpg' />";
				html += "</td>";
				html += "<td>";
				
				for (String[] stringsTop : result) {
					html += stringsTop[0] + "  Prom. Calificacion: <b>" + stringsTop[1] + "</b> <br>"; 
				}
				
				html += "</td>";
				html += "</tr>";
				html += "</table>";
            
				homeView.getHtmlArrayTop().setHTML(html);

			}
		};
		
		userSvc.getMoviesMostRatings(callbackTop);
	}

	private void getMoviesUser() {
		String user = this.userUserView.getTextBoxUsername().getText(); 
		
		AsyncCallback<List<String[]>> callback = new AsyncCallback<List<String[]>>() {
			public void onFailure(Throwable caught) {
		        // TODO: Do something with errors.
			}

			public void onSuccess(List<String[]> result) {
				
				String html = "<table>";
				html += "<tr>";
				html += "<td style='padding-right:20px'>";
				html += "<img src='/images/moviesUser.jpg' />";
				html += "</td>";
				html += "<td>";
				
				for (String[] stringsTop : result) {
					html += stringsTop[0] + "  Prom. Calificacion: <b>" + stringsTop[1] + "</b> <br>"; 
				}
				
				html += "</td>";
				html += "</tr>";
				html += "</table>";
				
				userUserView.getHtmlMovieUser().setHTML(html);
			}
		};
		userSvc.getMoviesUser(user, callback); 
		
	}
	
	private void getUserUserRecommended() {
		
		String userId = this.userUserView.getTextBoxUsername().getText(); 
		String numVecinos = this.userUserView.getTextBoxNumVecinos().getText();  
		String similarity = this.userUserView.getTextBoxSimilaridad().getText();
		
		AsyncCallback<List<String>> callbackJaccard = new AsyncCallback<List<String>>() {
			public void onFailure(Throwable caught) {
		        // TODO: Do something with errors.
			}

			public void onSuccess(List<String> result) {
				int i = 0;
				for (String stringsRecommender : result) {
					userUserView.getJaccard().clear();
					userUserView.getJaccard().setText(i, 0, stringsRecommender);
					i++;
				}
			}
		};
		userUserSvc.getUserUserRecommended(userId, Integer.parseInt(numVecinos), Double.parseDouble(similarity), IndexType.JACCARD, callbackJaccard);
		
		AsyncCallback<List<String>> callbackCosine = new AsyncCallback<List<String>>() {
			public void onFailure(Throwable caught) {
		        // TODO: Do something with errors.
			}

			public void onSuccess(List<String> result) {
				
				int i = 0;
				for (String stringsRecommender : result) {
					userUserView.getCosine().clear();
					userUserView.getCosine().setText(i, 0, stringsRecommender);
					i++;
				}
			}
		};
		userUserSvc.getUserUserRecommended(userId,Integer.parseInt(numVecinos), Double.parseDouble(similarity),IndexType.COSINE, callbackCosine);
		
		AsyncCallback<List<String>> callbackPearson = new AsyncCallback<List<String>>() {
			public void onFailure(Throwable caught) {
		        // TODO: Do something with errors.
			}

			public void onSuccess(List<String> result) {
				//Window.open("UserItem.html", "_self", "");
				
				int i = 0;
				for (String stringsRecommender : result) {
					userUserView.getPearson().clear();
					userUserView.getPearson().setText(i, 0, stringsRecommender);
					i++;
				}
			}
		};
		       // Make the call to the stock price service.
		userUserSvc.getUserUserRecommended(userId,Integer.parseInt(numVecinos), Double.parseDouble(similarity),IndexType.PEARSON, callbackPearson);
	}

	
}
