package app.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import app.MainApp;
import app.controllers.Controller;
import app.controllers.IController;
import app.hibernate.SimDataEntity;
import app.model.SimulationData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SimulationGUI implements ISimulationUI{
	MainApp mainApp;
	
	private IController controller = new Controller(this);
	
	private boolean running = false;
	private boolean historySet = false;
	
	@FXML private TextField givenTime;
	@FXML private TextField givenDelay;
	
	@FXML private Label finishTime;
	@FXML private Label delayTime;
	
	@FXML private Label amountOfClientsServed;
	@FXML private Label amountOfClients;
	
	@FXML private Button start;
	
	@FXML private Tab historyTab;
	
	@FXML private Label speed;
	@FXML private Button faster;
	@FXML private Button slower;
	
	@FXML private ProgressBar loadbar;
	
	@FXML private GridPane historyGrid;
	
	//Asetukset
	
	@FXML private TextField meanOfArr;
	@FXML private TextField meanOfCheckIn;
	@FXML private TextField meanOfTurvatarkastus;
	@FXML private TextField meanOfOdotusaula;
	@FXML private TextField meanOfPortti;
	@FXML private TextField varianceOfArr;
	@FXML private TextField varianceOfCheckIn;
	@FXML private TextField varianceOfTurvatarkastus;
	@FXML private TextField varianceOfOdotusaula;
	@FXML private TextField varianceOfPortti;
	
	@FXML private Label LmeanOfArr;
	@FXML private Label LmeanOfCheckIn;
	@FXML private Label LmeanOfTurvatarkastus;
	@FXML private Label LmeanOfOdotusaula;
	@FXML private Label LmeanOfPortti;
	@FXML private Label LvarianceOfArr;
	@FXML private Label LvarianceOfCheckIn;
	@FXML private Label LvarianceOfTurvatarkastus;
	@FXML private Label LvarianceOfOdotusaula;
	@FXML private Label LvarianceOfPortti;
	
	@FXML private Pane c1;
	@FXML private Pane c2;
	@FXML private Pane c3;
	
	@FXML private Pane t1;
	@FXML private Pane t2;
	@FXML private Pane t3;
	@FXML private Pane t4;
	
	@FXML private Pane o1;
	
	@FXML private Pane p1;
	@FXML private Pane p2;
	
	//Handlerit
	@FXML private void handleStart(ActionEvent event) {
		
		boolean isValidForRun = false;
		
		if(!running) {
			try {
				setSimData();
				isValidForRun = true;
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Virhe!");
				alert.setHeaderText("Virhe palvelupisteiden arvoissa!");
				alert.setContentText("Tarkista, että kaikki palvelupisteiden arvot ovat numeroita ja yritä uudestaan.");

				alert.showAndWait();
			}
		}
		
		if(!running && isValidForRun) {

			try {
				controller.startSimulation();
				this.running = true;
				updateUI();
				armButtons();
				setAmountOfClients(0);
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Virhe!");
				alert.setHeaderText("Virhe simulaattorin arvoissa!");
				alert.setContentText("Tarkista, että kaikki arvot ovat numeroita ja yritä uudestaan.");

				alert.showAndWait();
			}
			
		}
	}
	
	@FXML private void handleSimSpeedUp(ActionEvent event) {
		controller.speedUpSim();
	}
	
	@FXML private void handleSimSpeedDown(ActionEvent event) {
		controller.slowDownSim();
	}
	
	@FXML private void handleHistory() {
		controller.getHistory();
	}

	//Getterit

	@Override
	public double getTime() {
		return Double.parseDouble(givenTime.getText());
	}

	@Override
	public long getDelay() {
		return Long.parseLong(givenDelay.getText());
	}
	
	//Setterit
	
	@Override
	public void setHistory(SimDataEntity sDE) {
		if(!historySet) {
			historyGrid.addRow(sDE.getId(), new Label(sDE.getId()+"") , new Label(sDE.getAika()), new Label(sDE.getAsiakasmaara()));
		}
	}
	
	@Override
	public void historyIsDone() {
		this.historySet = true;
	}
	
	@Override
	public void updateUI() {
		SimulationData simData = SimulationData.getInstance();
		
		List<Integer> means = simData.getMeans();
		List<Integer> variances = simData.getVariances();
		
		LmeanOfArr.setText(					Integer.toString(means.get(0)));
		LmeanOfCheckIn.setText(				Integer.toString(means.get(1)));
		LmeanOfTurvatarkastus.setText(		Integer.toString(means.get(2)));
		LmeanOfOdotusaula.setText(			Integer.toString(means.get(3)));
		LmeanOfPortti.setText(				Integer.toString(means.get(4)));
		
		LvarianceOfArr.setText(				Integer.toString(variances.get(0)));
		LvarianceOfCheckIn.setText(			Integer.toString(variances.get(1)));
		LvarianceOfTurvatarkastus.setText(	Integer.toString(variances.get(2)));
		LvarianceOfOdotusaula.setText(		Integer.toString(variances.get(3)));
		LvarianceOfPortti.setText(			Integer.toString(variances.get(4)));
		
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
    }
	
	@Override
	public void setTime(double time, boolean isFinal){
		
		 DecimalFormat formatter = new DecimalFormat("#0.00");
		 finishTime.setText(formatter.format(time));
		 
		 if(isFinal) {
			 finishTime.setStyle("-fx-text-fill: #006400;");
		 }
	}
	
	@Override
	public void setLoadBarValue(double howFull) {
		loadbar.setProgress(howFull);
	}
	
	@Override
	public void setAmountOfClients(long maara) {
		amountOfClients.setText("Asiakkaita saapunut: " + maara);
	}
	
	@Override
	public void setAmountOfServedClients(long maara) {
		amountOfClientsServed.setText("Asiakkaita palveltu: " + maara);
	}
	
	@Override
	public void setCurrentDelay(long delay) {
		
		String multiplier = "";
		
		long uiDelay = getDelay();
		if(delay > uiDelay) {
			double mp = ((double)delay/(double)uiDelay)*100-100;
			DecimalFormat formatter = new DecimalFormat("#0");
			multiplier = " (nopeus -" + formatter.format(mp) + "%)";
		} else if (delay < uiDelay){
			double mp = ((double)uiDelay/(double)delay)*100-100;
			DecimalFormat formatter = new DecimalFormat("#0");
			multiplier = " (nopeus +" + formatter.format(mp) + "%)";
		}
		
		delayTime.setText(String.valueOf(delay) + multiplier);
	}
	
	@Override
	public void drawService(int[][] sList) {
		
		Pane[][] servicePoints = new Pane[][] {{}, {null, c1, c2, c3,}, {null, t1, t2, t3, t4}, {null, o1}, {null, p1, p2}};
		
		for(int dim1 = 1; dim1 < sList.length; dim1++) {
			for(int dim2 = 1; dim2 < sList[dim1].length; dim2++) {
				if(sList[dim1][dim2] != 0) {
					int colorVal = 255;
					colorVal -= colorVal * ((double)sList[dim1][dim2]/(double)sList[dim1].length);
					if(colorVal < 0) {
						colorVal = 0;
					}
					servicePoints[dim1][dim2].setBackground(new Background(new BackgroundFill(Color.rgb(255, colorVal, colorVal), CornerRadii.EMPTY, Insets.EMPTY)));
				} else {
					if(dim2 < servicePoints[dim1].length) {
						servicePoints[dim1][dim2].setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)));
					}
				}
			}
		}
		
		
	}
	
	private void armButtons() {
		start.setDisable(running);
		
		faster.setDisable(!running);
		slower.setDisable(!running);
		speed.setDisable(!running);
		
		givenTime.setDisable(!running);
		givenDelay.setDisable(!running);
		
		meanOfArr.setDisable(!running);
		meanOfCheckIn.setDisable(!running);
		meanOfTurvatarkastus.setDisable(!running);
		meanOfOdotusaula.setDisable(!running);
		meanOfPortti.setDisable(!running);
		varianceOfArr.setDisable(!running);
		varianceOfCheckIn.setDisable(!running);
		varianceOfTurvatarkastus.setDisable(!running);
		varianceOfOdotusaula.setDisable(!running);
		varianceOfPortti.setDisable(!running);
	}

	
	private void setSimData() {
		SimulationData simData = SimulationData.getInstance();
		
		String[] meanContent = getMeanContent();
		String[] varianceContent = getVarianceContent();
		
		List<Integer> means = new ArrayList<>();
		List<Integer> variances = new ArrayList<>();
		
		for(int i = 0; i < meanContent.length; i++) {
			means.add(Integer.valueOf(meanContent[i]));
			variances.add(Integer.valueOf(varianceContent[i]));
		}
		
		simData.setStartData(means, variances);		
		
	}
	
	private String[] getMeanContent() {
		return new String[] {
				meanOfArr.getText(), 
				meanOfCheckIn.getText(), 
				meanOfTurvatarkastus.getText(),
				meanOfOdotusaula.getText(),
				meanOfPortti.getText()
		};
	}
	
	private String[] getVarianceContent() {
		return new String[] {
				varianceOfArr.getText(), 
				varianceOfCheckIn.getText(), 
				varianceOfTurvatarkastus.getText(),
				varianceOfOdotusaula.getText(),
				varianceOfPortti.getText()
		};
	}

}
