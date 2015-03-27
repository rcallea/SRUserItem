package co.edu.uniandes.useritem.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
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

public class HomeView {

	/**
	 * Constants used in web page
	 */
	private RSConstants constants = GWT.create(RSConstants.class);

	/**
	 * Form elements: Label, textbox, dropdown, button
	 */
	//private FlexTable ft=new FlexTable();
	//private ListBox listBoxDispositivo=new ListBox();
	private VerticalPanel vp=new VerticalPanel();
	private HTML htmlUiTitle=new HTML("<h2>" + constants.uiTittleHome() + "</h2>");
	private HTML htmlUsername=new HTML("<h3>" + constants.uiSubTittleTop() + "</h3>");
	private HTML htmlName=new HTML("<h3>" + constants.uiSubTittleStatistics() + "</h3>");
	private HTML htmlError=new HTML();
	private HTML htmlArray=new HTML();
	private HTML htmlArrayTop=new HTML();
	private FlexTable stats=new FlexTable();
	private FlexTable topMovies=new FlexTable();
	private Controller controller;
	
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
		
		
		this.vp.add(this.htmlUiTitle);
		this.vp.add(this.htmlUsername);
		this.vp.add(this.htmlArrayTop);
		this.vp.add(new HTML("<hr/>"));
		this.vp.add(this.topMovies);
		this.vp.add(this.htmlName);
		this.vp.add(this.htmlArray);
		this.vp.add(this.stats);
		this.vp.add(new HTML("<hr/>"));
		this.vp.add(new HTML("<hr/>"));
		this.vp.add(this.htmlError);
		RootPanel.get("home").add(this.vp);
		
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

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		this.generateUI();
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element

		// Focus the cursor on the name field when the app loads
//		nameField.setFocus(true);
//		nameField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);
		
	}

	/**
	 * @return the constants
	 */
	public RSConstants getConstants() {
		return constants;
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
	 * @return the htmlName
	 */
	public HTML getHtmlName() {
		return htmlName;
	}

	/**
	 * @return the htmlError
	 */
	public HTML getHtmlError() {
		return htmlError;
	}
	
	/**
	 * @return the htmlError
	 */
	public HTML getHtmlArray() {
		return htmlArray;
	}
	
	/**
	 * @return the htmlError
	 */
	public HTML getHtmlArrayTop() {
		return htmlArrayTop;
	}
	
	/**
	 * @return the vp
	 */
	public VerticalPanel getVp() {
		return vp;
	}

	/**
	 * @return the stats
	 */
	public FlexTable getStats() {
		return stats;
	}

	/**
	 * @return the topMovies
	 */
	public FlexTable getTopMovies() {
		return topMovies;
	}
}
