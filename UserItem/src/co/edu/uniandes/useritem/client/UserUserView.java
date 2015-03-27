package co.edu.uniandes.useritem.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UserUserView {

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
	private HTML htmlTitle1=new HTML("<h2>Informacion personalizada</h2>");
	private HTML htmlTitle=new HTML("<h2>" + constants.ruiValoraciones() + "</h2>");
	private HTML htmlTitle2=new HTML("<h2>" + constants.uiSubTittleUserUserRec() + "</h2>");
	private HTML htmlNumber=new HTML("<h4>" + constants.iiNumber() + "</h4>");
	private HTML htmlLevel=new HTML("<h4>" + constants.uiSimilaridad() + "</h4>");
	private HTML htmlError=new HTML();
	private TextBox textBoxNumVecinos=new TextBox();
	private TextBox textBoxSimilaridad=new TextBox();
	private Button buttonSend = new Button(constants.ruiSearchUser());
	private Button buttonRecommender = new Button(constants.ruiRecUsuario());
	private FlexTable jaccard=new FlexTable();
	private FlexTable cosine=new FlexTable();
	private FlexTable pearson=new FlexTable();
	private Controller controller;
	private HTML htmlMovieUser=new HTML();
	private TextBox textBoxUsername=new TextBox();
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
		this.textBoxNumVecinos.setText("50");
		this.textBoxSimilaridad.setText("0.1");
		
		VerticalPanel vp1=new VerticalPanel();
		VerticalPanel vp2=new VerticalPanel();
		VerticalPanel vp3=new VerticalPanel();
		HorizontalPanel hp=new HorizontalPanel();
		this.jaccard.setText(0, 0, "_");
		this.cosine.setText(0, 0, "_");
		this.pearson.setText(0, 0, "_");
		this.jaccard.setStyleName("table table-striped");
		this.cosine.setStyleName("table table-striped");
		this.pearson.setStyleName("table table-striped");
		
		vp1.add(new HTML("<h4>" + this.constants.iiJaccard() + "</h4>"));
		vp1.add(this.jaccard);
		vp2.add(new HTML("<h4>" + this.constants.iiCoseno() + "</h4>"));
		vp2.add(this.cosine);
		vp3.add(new HTML("<h4>" + this.constants.iiPearson() + "</h4>"));
		vp3.add(this.pearson);
		hp.add(vp1);
		hp.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
		hp.add(vp2);
		hp.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
		hp.add(vp3);
		
		//----------------------ACA----------------------//
		this.vp.add(this.htmlTitle1);
		HorizontalPanel hp2 = new HorizontalPanel();
		hp2.add(new HTML("<h4>Id de Usuario&nbsp;&nbsp;</h4>"));
		hp2.add(this.textBoxUsername);
		hp2.add(new HTML("&nbsp;&nbsp;"));
		hp2.add(this.buttonSend);
		this.vp.add(hp2);
		this.vp.add(this.htmlTitle);
		this.vp.add(this.htmlMovieUser);
		
		this.vp.add(this.htmlTitle2);
		this.vp.add(this.textBoxNumVecinos);
		this.vp.add(this.htmlNumber);
		this.vp.add(this.textBoxNumVecinos);
		this.vp.add(this.htmlLevel);
		this.vp.add(this.textBoxSimilaridad);
		this.vp.add(new HTML("<hr/>"));
		this.vp.add(this.buttonRecommender);
		this.vp.add(new HTML("<hr/>"));
		this.vp.add(this.htmlError);
		this.buttonSend.addClickHandler(this.controller);
		this.buttonRecommender.addClickHandler(this.controller);
		this.vp.add(hp);
		RootPanel.get("usuariousuario").add(this.vp);
		
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
		if(!this.getTextBoxNumVecinos().getText().matches(ItemItemView.DOUBLE_VALIDATION) || this.getTextBoxNumVecinos().getText().trim().equals("")) {
			retorno=false;
			message += "<li>" + this.constants.uiFieldError() + "\"" + this.constants.iiNumber() + "\": " + this.constants.iiFieldErrorMessage() + "</li>";
		}
		if(!this.getTextBoxSimilaridad().getText().matches(ItemItemView.DOUBLE_VALIDATION) || this.getTextBoxSimilaridad().getText().trim().equals("")) {
			retorno=false;
			message += "<li>" + this.constants.uiFieldError() + "\"" + this.constants.iiLevel() + "\": " + this.constants.iiFieldErrorMessage() + "</li>";
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
		int size=(int)(tables.length/3);
		int i=0;
		this.jaccard.clear();
		this.jaccard.setText(0, 0, "hhh");
		this.cosine.clear();
		this.cosine.setText(0, 0, "hhh");
		this.pearson.clear();
		this.pearson.setText(0, 0, "hhh");

		for(;i<size;i++) {
			String[] row=tables[i].split("\\|");
			for(int j=0;j<row.length;j++) {
				this.getJaccard().setText(i, j, row[j]);
			}
		}
		
		for(;i<(size*2);i++) {
			String[] row=tables[i].split("\\|");
			for(int j=0;j<row.length;j++) {
				this.cosine.setText(i-size, j, row[j]);
			}
		}

		for(;i<(size*3);i++) {
			String[] row=tables[i].split("\\|");
			for(int j=0;j<row.length;j++) {
				this.pearson.setText(i-(2*size), j, row[j]);
			}
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
	 * @return the htmlError
	 */
	public HTML getHtmlError() {
		return htmlError;
	}

	/**
	 * @return the textBoxNumber
	 */
	public TextBox getTextBoxNumVecinos() {
		return textBoxNumVecinos;
	}

	/**
	 * @return the textBoxLevel
	 */
	public TextBox getTextBoxSimilaridad() {
		return textBoxSimilaridad;
	}

	/**
	 * @return the buttonSend
	 */
	public Button getButtonSend() {
		return buttonSend;
	}
	/**
	 * @return the buttonSend
	 */
	public Button getButtonRecommender() {
		return buttonRecommender;
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
	
	/**
	 * @return the htmlError
	 */
	public HTML getHtmlMovieUser() {
		return htmlMovieUser;
	}
	
	/**
	 * @return the textBoxUsername
	 */
	public TextBox getTextBoxUsername() {
		return textBoxUsername;
	}
}
