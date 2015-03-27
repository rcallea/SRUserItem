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

public class UserView {

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
	private HTML htmlUiTitle=new HTML("<h2>" + constants.uiTitle() + "</h2>");
	private HTML htmlUsername=new HTML("<h3>" + constants.uiUserName() + "</h3>");
	private HTML htmlName=new HTML("<h3>" + constants.uiName() + "</h3>");
	private HTML htmlTwitter=new HTML("<h3>" + constants.uiTwitter() + "</h3>");
	private HTML htmlEmail=new HTML("<h3>" + constants.uiEmail() + "</h3>");
	private HTML htmlError=new HTML();
	private TextBox textBoxUsername=new TextBox();
	private TextBox textBoxName=new TextBox();
	private TextBox textBoxTwitter=new TextBox();
	private TextBox textBoxEmail=new TextBox();
	private Button buttonSend = new Button(constants.uiSend());
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
		this.textBoxUsername.setText("3500");
		this.textBoxName.setText("Nombre");
		this.textBoxTwitter.setText("@twitter");
		this.textBoxEmail.setText("example@example.com");
		
		this.vp.add(this.htmlUiTitle);
		this.vp.add(this.htmlUsername);
		this.vp.add(this.textBoxUsername);
		this.vp.add(this.htmlName);
		this.vp.add(this.textBoxName);
		this.vp.add(this.htmlTwitter);
		this.vp.add(this.textBoxTwitter);
		this.vp.add(this.htmlEmail);
		this.vp.add(this.textBoxEmail);
		this.vp.add(new HTML("<hr/>"));
		this.vp.add(this.buttonSend);
		this.vp.add(new HTML("<hr/>"));
		this.vp.add(this.htmlError);
		this.buttonSend.addClickHandler(this.controller);
		RootPanel.get("user").add(this.vp);
		
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

	public boolean validate() {
		boolean retorno=true;
		String message="<ul>";
		if(!this.getTextBoxUsername().getText().matches("[0-9]+")) {
			retorno=false;
			message += "<li>" + this.constants.uiFieldError() + "\"" + this.constants.uiUserName() + "\": " + this.constants.uiFieldErrorMessage() + "</li>";
		}

		if(this.getTextBoxName().getText().trim().equals("")) {
			retorno=false;
			message += "<li>" + this.constants.uiFieldError() + "\"" + this.constants.uiName() + "\": " + this.constants.uiFieldErrorMessage() + "</li>";
		}
		
		if(this.getTextBoxEmail().getText().trim().equals("")) {
			retorno=false;
			message += "<li>" + this.constants.uiFieldError() + "\"" + this.constants.uiEmail() + "\": " + this.constants.uiFieldErrorMessage() + "</li>";
		}
		
		if(this.getTextBoxTwitter().getText().trim().equals("")) {
			retorno=false;
			message += "<li>" + this.constants.uiFieldError() + "\"" + this.constants.uiTwitter() + "\": " + this.constants.uiFieldErrorMessage() + "</li>";
		}
		
		if(retorno==false) {
			this.showErrorMessage(message + "</ul>");
		}
		else {
			this.hidErrorMessage();
		}
		return retorno;
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
	 * @return the htmlTwitter
	 */
	public HTML getHtmlTwitter() {
		return htmlTwitter;
	}

	/**
	 * @return the htmlEmail
	 */
	public HTML getHtmlEmail() {
		return htmlEmail;
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
	public TextBox getTextBoxUsername() {
		return textBoxUsername;
	}

	/**
	 * @return the textBoxName
	 */
	public TextBox getTextBoxName() {
		return textBoxName;
	}

	/**
	 * @return the textBoxTwitter
	 */
	public TextBox getTextBoxTwitter() {
		return textBoxTwitter;
	}

	/**
	 * @return the textBoxEmail
	 */
	public TextBox getTextBoxEmail() {
		return textBoxEmail;
	}

	/**
	 * @return the buttonSend
	 */
	public Button getButtonSend() {
		return buttonSend;
	}
}
