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

public class ItemItemView {

	/**
	 * Constants used in web page
	 */
	private RSConstants constants = GWT.create(RSConstants.class);
	public static final String DOUBLE_VALIDATION="^\\d*(\\.\\d{1,2})?$";
	/**
	 * Form elements: Label, textbox, dropdown, button
	 */
	//private FlexTable ft=new FlexTable();
	//private ListBox listBoxDispositivo=new ListBox();
	private VerticalPanel vp=new VerticalPanel();
	private HTML htmlTitle=new HTML("<h2>" + constants.iiTitle() + "</h2>");
	private HTML htmlNumber=new HTML("<h4>" + constants.iiNumber() + "</h4>");
	private HTML htmlLevel=new HTML("<h4>" + constants.iiLevel() + "</h4>");
	private HTML htmlResults=new HTML("<h4>" + constants.iiResults() + "</h4>");
	private HTML htmlUsername=new HTML("<h4>" + constants.iiUsername() + "</h4>");
	private HTML htmlOutOfDataset=new HTML("<h4>" + constants.iiOutOfDataset() + "</h4>");
	private HTML htmlError=new HTML();
	private TextBox textBoxNumber=new TextBox();
	private TextBox textBoxLevel=new TextBox();
	private TextBox textBoxResults=new TextBox();
	private TextBox textBoxUserIdValue=new TextBox();
	private Button buttonSend = new Button(constants.iiSend());
	private FlexTable jaccard=new FlexTable();
	private FlexTable cosine=new FlexTable();
	private FlexTable pearson=new FlexTable();
	private FlexTable jaccard2=new FlexTable();
	private FlexTable cosine2=new FlexTable();
	private FlexTable pearson2=new FlexTable();
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
		this.textBoxNumber.setText("20000");
		this.textBoxLevel.setText("50");
		this.textBoxResults.setText("10");
		this.textBoxUserIdValue.setText("1");
		
		VerticalPanel vp1=new VerticalPanel();
		VerticalPanel vp2=new VerticalPanel();
		VerticalPanel vp3=new VerticalPanel();
		HorizontalPanel hp=new HorizontalPanel();
		this.jaccard.setText(0, 0, "_");
		this.cosine.setText(0, 0, "_");
		this.pearson.setText(0, 0, "_");
		this.jaccard2.setText(0, 0, "_");
		this.cosine2.setText(0, 0, "_");
		this.pearson2.setText(0, 0, "_");
		this.jaccard.setStyleName("table table-striped");
		this.cosine.setStyleName("table table-striped");
		this.pearson.setStyleName("table table-striped");
		this.jaccard2.setStyleName("table table-striped");
		this.cosine2.setStyleName("table table-striped");
		this.pearson2.setStyleName("table table-striped");
		
//		vp1.add(new HTML("<h4>" + this.constants.iiJaccard() + "</h4>"));
//		vp1.add(this.jaccard);
//		vp1.add(new HTML(""));
//		vp1.add(new HTML("<h4>" + constants.iiOutOfDataset() + "</h4>"));
//		vp1.add(this.jaccard2);
		vp2.add(new HTML("<h4>" + this.constants.iiCoseno() + "</h4>"));
		vp2.add(this.cosine);
		vp2.add(new HTML(""));
		vp2.add(new HTML("<h4>" + constants.iiOutOfDataset() + "</h4>"));
		vp2.add(this.cosine2);
		vp3.add(new HTML("<h4>" + this.constants.iiPearson() + "</h4>"));
		vp3.add(this.pearson);
		vp3.add(new HTML(""));
		vp3.add(this.htmlOutOfDataset);
		vp3.add(this.pearson2);
		hp.add(vp1);
		hp.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
		hp.add(vp2);
		hp.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
		hp.add(vp3);
		
		this.vp.add(this.htmlTitle);
		this.vp.add(this.htmlNumber);
		this.vp.add(this.textBoxNumber);
		this.vp.add(this.htmlLevel);
		this.vp.add(this.textBoxLevel);
		this.vp.add(this.htmlResults);
		this.vp.add(this.textBoxResults);
		this.vp.add(this.htmlUsername);
		this.vp.add(this.textBoxUserIdValue);
		this.vp.add(new HTML("<hr/>"));
		this.vp.add(this.buttonSend);
		this.vp.add(new HTML("<hr/>"));
		this.vp.add(this.htmlError);
		this.buttonSend.addClickHandler(this.controller);
		this.vp.add(hp);
		RootPanel.get("itemitem").add(this.vp);
		
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
		if(!this.getTextBoxNumber().getText().matches(ItemItemView.DOUBLE_VALIDATION) || this.getTextBoxNumber().getText().trim().equals("")) {
			retorno=false;
			message += "<li>" + this.constants.uiFieldError() + "\"" + this.constants.iiNumber() + "\": " + this.constants.iiFieldErrorMessage() + "</li>";
		}
		if(!this.getTextBoxLevel().getText().matches(ItemItemView.DOUBLE_VALIDATION) || this.getTextBoxLevel().getText().trim().equals("")) {
			retorno=false;
			message += "<li>" + this.constants.uiFieldError() + "\"" + this.constants.iiLevel() + "\": " + this.constants.iiFieldErrorMessage() + "</li>";
		}

		if(!this.getTextBoxResults().getText().matches(ItemItemView.DOUBLE_VALIDATION) || this.getTextBoxResults().getText().trim().equals("")) {
			retorno=false;
			message += "<li>" + this.constants.uiFieldError() + "\"" + this.constants.iiResults() + "\": " + this.constants.iiFieldErrorMessage() + "</li>";
		}
		
		if(!this.textBoxUserIdValue.getText().matches(ItemItemView.DOUBLE_VALIDATION) || this.textBoxUserIdValue.getText().trim().equals("")) {
			retorno=false;
			message += "<li>" + this.constants.uiFieldError() + "\"" + this.constants.uiUserName() + "\": " + this.constants.iiFieldErrorMessage() + "</li>";
		}
		
		if(retorno==false) {
			this.showErrorMessage(message + "</ul>");
		}
		else {
			this.hidErrorMessage();
		}
		return retorno;
	}
	
	public void updateTables(String[] tables) {
		int rowNumber=0;
		int i=0;
		this.jaccard.clear(true);
		this.jaccard.setText(0, 0, "_");
		this.cosine.clear(true);
		this.cosine.setText(0, 0, "_");
		this.pearson.clear(true);
		this.pearson.setText(0, 0, "_");
		this.jaccard2.clear(true);
		this.jaccard2.setText(0, 0, "_");
		this.cosine2.clear(true);
		this.cosine2.setText(0, 0, "_");
		this.pearson2.clear(true);
		this.pearson2.setText(0, 0, "_");

		while(!tables[i].equals("---")) {
			String[] row=tables[i].split("\\|");
			for(int j=0;j<row.length;j++) {
				this.getJaccard().setText(rowNumber, j, row[j]);
			}
			i++;
			rowNumber++;
		}
		
		i++;
		rowNumber=0;
		while(!tables[i].equals("---")) {
			String[] row=tables[i].split("\\|");
			for(int j=0;j<row.length;j++) {
				this.cosine.setText(rowNumber, j, row[j]);
			}
			i++;
			rowNumber++;
		}

		i++;
		rowNumber=0;
		while(!tables[i].equals("---")) {
			String[] row=tables[i].split("\\|");
			for(int j=0;j<row.length;j++) {
				this.cosine2.setText(rowNumber, j, row[j]);
			}
			i++;
			rowNumber++;
		}

		i++;
		rowNumber=0;
		while(!tables[i].equals("---")) {
			String[] row=tables[i].split("\\|");
			for(int j=0;j<row.length;j++) {
				this.pearson.setText(rowNumber, j, row[j]);
			}
			i++;
			rowNumber++;
		}

		i++;
		rowNumber=0;
		while(i<tables.length) {
			String[] row=tables[i].split("\\|");
			for(int j=0;j<row.length;j++) {
				this.pearson2.setText(rowNumber, j, row[j]);
			}
			i++;
			rowNumber++;
		}
	}

	/**
	 * @return the vp
	 */
	public VerticalPanel getVp() {
		return vp;
	}

	/**
	 * @return the htmlTitle
	 */
	public HTML getHtmlTitle() {
		return htmlTitle;
	}

	/**
	 * @return the htmlNumber
	 */
	public HTML getHtmlNumber() {
		return htmlNumber;
	}

	/**
	 * @return the htmlLevel
	 */
	public HTML getHtmlLevel() {
		return htmlLevel;
	}

	/**
	 * @return the htmlResults
	 */
	public HTML getHtmlResults() {
		return htmlResults;
	}

	/**
	 * @return the htmlError
	 */
	public HTML getHtmlError() {
		return htmlError;
	}

	/**
	 * @return the textBoxNumber
	 */
	public TextBox getTextBoxNumber() {
		return textBoxNumber;
	}

	/**
	 * @return the textBoxLevel
	 */
	public TextBox getTextBoxLevel() {
		return textBoxLevel;
	}

	/**
	 * @return the textBoxResults
	 */
	public TextBox getTextBoxResults() {
		return textBoxResults;
	}

	/**
	 * @return the buttonSend
	 */
	public Button getButtonSend() {
		return buttonSend;
	}

	/**
	 * @return the jaccard
	 */
	public FlexTable getJaccard() {
		return jaccard;
	}

	/**
	 * @return the cosine
	 */
	public FlexTable getCosine() {
		return cosine;
	}

	/**
	 * @return the pearson
	 */
	public FlexTable getPearson() {
		return pearson;
	}

	public HTML getHtmlUsername() {
		return htmlUsername;
	}

	public TextBox getTextBoxUserIdValue() {
		return textBoxUserIdValue;
	}

	public HTML getHtmlOutOfDataset() {
		return htmlOutOfDataset;
	}

	public FlexTable getJaccard2() {
		return jaccard2;
	}

	public FlexTable getCosine2() {
		return cosine2;
	}

	public FlexTable getPearson2() {
		return pearson2;
	}

}
