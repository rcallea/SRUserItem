package co.edu.uniandes.useritem.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MovieView {

	/**
	 * Constants used in web page
	 */
	private RSConstants constants = GWT.create(RSConstants.class);
	private UserServiceAsync userSvc = GWT.create(UserService.class);

	/**
	 * Form elements: Label, textbox, dropdown, button
	 */
	//private FlexTable ft=new FlexTable();
	//private ListBox listBoxDispositivo=new ListBox();
	private VerticalPanel vp=new VerticalPanel();
	private HTML htmlUiTitle=new HTML("<h2>" + constants.miTitle() + "</h2>");
	private HTML htmlUsername=new HTML("<h3>" + constants.miUserName() + "</h3>");
	private HTML htmlMovie=new HTML("<h3>" + constants.miMovie() + "</h3>");
	private HTML htmlGrade=new HTML("<h3>" + constants.miGrade() + "</h3>");
	private HTML htmlFilter=new HTML("<h3>" + constants.miFilterTitle() + "</h3>");
	private HTML htmlError=new HTML();
	private ListBox listBoxUsername=new ListBox();
	private TextBox textBoxMovie=new TextBox();
	private ListBox listBoxMovie=new ListBox();
	private ListBox listBoxGrade=new ListBox();
	private Button buttonFilter = new Button(constants.miFilterButton());
	private Button buttonSend = new Button(constants.miSend());
	private Controller controller;
	
	private String[] users={};
	private String[] movies={};
	
	/**
	 * @param controller the controller to set
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * Adds the elements to the root page
	 */
	public void generateUI() {
		HorizontalPanel hp=new HorizontalPanel();
		this.getUsers();
		//Ojo, el update de users se hace luego de que se hace el llamado de update proque es asíncrono
		
		String[] valores=this.constants.miGradeList();
		for(String s:valores) {
			this.listBoxGrade.addItem(s);
		}
		
		hp.add(this.textBoxMovie);
		hp.add(this.buttonFilter);
		
		this.vp.add(this.htmlUiTitle);
		this.vp.add(this.htmlUsername);
		this.vp.add(this.listBoxUsername);
		this.vp.add(this.htmlFilter);
		this.vp.add(hp);
		this.vp.add(this.htmlMovie);
		this.vp.add(this.listBoxMovie);
		this.vp.add(this.htmlGrade);
		this.vp.add(this.listBoxGrade);
		this.vp.add(new HTML("<hr/>"));
		this.vp.add(this.buttonSend);
		this.vp.add(new HTML("<hr/>"));
		this.vp.add(this.htmlError);
		this.buttonSend.addClickHandler(this.controller);
		this.buttonFilter.addClickHandler(this.controller);
		RootPanel.get("movie").add(this.vp);
		
		this.hidErrorMessage();
	}
	
	/**
	 * Shows an error message on htmlError 
	 * @param message
	 */
	public void showErrorMessage(String message) {
		this.htmlError.setHTML(message);
		this.htmlError.setStyleName("alert alert-danger");
	}
	
	public void hidErrorMessage() {
		this.htmlError.setHTML("");
		this.htmlError.setStyleName("none");
	}


	public boolean validate() {
		boolean retorno=true;
		String message="<ul>";
		if(!(this.getListBoxUsername().getItemCount()>0)) {
			retorno=false;
			message += "<li>" + this.constants.uiFieldError() + "\"" + this.constants.miUserName() + "\": " + this.constants.uiFieldErrorMessage() + "</li>";
		}
		if(!(this.getListBoxMovie().getItemCount()>0)) {
			retorno=false;
			message += "<li>" + this.constants.uiFieldError() + "\"" + this.constants.miMovie() + "\": " + this.constants.uiFieldErrorMessage() + "</li>";
		}
		if(retorno==false) {
			this.showErrorMessage(message + "</ul>");
		}
		else {
			this.hidErrorMessage();
		}
		return(retorno);
	}

	public boolean validateFilter() {
		boolean retorno=true;
		String message="<ul>";
		if(this.getTextBoxMovie().getText().trim().length()<2) {
			retorno=false;
			message += "<li>" + this.constants.uiFilterErrorMessage() +"</li>";
		}
		if(retorno==false) {
			this.showErrorMessage(message + "</ul>");
		}
		else {
			this.hidErrorMessage();
		}
		return(retorno);
	}

	public void getUsers() {
		if(userSvc==null) userSvc = GWT.create(UserService.class);
		String[] ret={};
		
		AsyncCallback<String[]> callback = new AsyncCallback<String[]>() {
			public void onFailure(Throwable caught) {
		        // TODO: Do something with errors.
			}

			public void onSuccess(String[] result) {
				//TODO reemplazar aquí lo que está bien
				updateUsers(result);
				//Window.open("UserItem.html", "_self", "");
			}
		};

		// Make the call to the stock price service.
		userSvc.getUserList(callback);
	}
	
	private void updateUsers(String[] usersReturned) {
		this.users = new String[usersReturned.length];
		for(int i=0;i<usersReturned.length;i++) {
			users[i]=usersReturned[i];
		}
		this.setListBoxUsername(this.users);
	}
	
	public void getMovies() {
		if(userSvc==null) userSvc = GWT.create(UserService.class);
		String movie=this.getTextBoxMovie().getText().trim();
		
		AsyncCallback<String[]> callback = new AsyncCallback<String[]>() {
			public void onFailure(Throwable caught) {
		        // TODO: Do something with errors.
			}

			public void onSuccess(String[] result) {
				//TODO reemplazar aquí lo que está bien
				updateMovies(result);
				//Window.open("UserItem.html", "_self", "");
			}
		};

		// Make the call to the stock price service.
		userSvc.getMovieList(movie, callback);
	}
	
	private void updateMovies(String[] moviesReturned) {
		this.movies = new String[moviesReturned.length];
		for(int i=0;i<moviesReturned.length;i++) {
			this.movies[i]=moviesReturned[i];
		}
		this.setListBoxMovie(this.movies);
	}
	
	/**
	 * @return the constants
	 */
	public RSConstants getConstants() {
		return constants;
	}

	/**
	 * @return the vp
	 */
	public VerticalPanel getVp() {
		return vp;
	}

	/**
	 * @return the htmlUiTitle
	 */
	public HTML getHtmlUiTitle() {
		return htmlUiTitle;
	}

	/**
	 * @return the htmlUsername
	 */
	public HTML getHtmlUsername() {
		return htmlUsername;
	}

	/**
	 * @return the htmlMovie
	 */
	public HTML getHtmlMovie() {
		return htmlMovie;
	}

	/**
	 * @return the htmlGrade
	 */
	public HTML getHtmlGrade() {
		return htmlGrade;
	}

	/**
	 * @return the htmlError
	 */
	public HTML getHtmlError() {
		return htmlError;
	}

	/**
	 * @return the textBoxUsername
	 */
	public ListBox getListBoxUsername() {
		return listBoxUsername;
	}

	/**
	 * @return the textBoxMovie
	 */
	public ListBox getListBoxMovie() {
		return listBoxMovie;
	}

	/**
	 * @return the textBoxGrade
	 */
	public ListBox getListBoxGrade() {
		return listBoxGrade;
	}

	/**
	 * @return the buttonSend
	 */
	public Button getButtonSend() {
		return buttonSend;
	}

	/**
	 * @return the controller
	 */
	public Controller getController() {
		return controller;
	}

	/**
	 * @return the htmlFilter
	 */
	public HTML getHtmlFilter() {
		return htmlFilter;
	}

	/**
	 * @return the textBoxMovie
	 */
	public TextBox getTextBoxMovie() {
		return textBoxMovie;
	}

	/**
	 * @return the buttonFilter
	 */
	public Button getButtonFilter() {
		return buttonFilter;
	}

	/**
	 * @param listBoxUsername the listBoxUsername to set
	 */
	public void setListBoxUsername(String[] users) {
		this.listBoxUsername.clear();
		for(String s:users) {
			this.listBoxUsername.addItem(s);
		}
	}

	/**
	 * @param listBoxMovie the listBoxMovie to set
	 */
	public void setListBoxMovie(String[] movies) {
		this.listBoxMovie.clear();
		int i=0;
		for(String s:movies) {
			String[] values=s.split("\\|");
			this.listBoxMovie.insertItem(values[1], values[0],i++);
		}
	}

}
