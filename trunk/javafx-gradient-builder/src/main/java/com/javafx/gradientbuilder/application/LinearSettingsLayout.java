package com.javafx.gradientbuilder.application;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioButtonBuilder;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraintsBuilder;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;


public class LinearSettingsLayout extends AbstractSettingsLayout implements SyntaxConstants{

	protected SimpleBooleanProperty isFrom = new SimpleBooleanProperty(true);
	protected SimpleBooleanProperty isFromPixel = new SimpleBooleanProperty(true);
	protected SimpleIntegerProperty fromXPixel = new SimpleIntegerProperty();
	protected SimpleIntegerProperty fromYPixel = new SimpleIntegerProperty();
	protected SimpleIntegerProperty fromXPercent = new SimpleIntegerProperty();
	protected SimpleIntegerProperty fromYPercent = new SimpleIntegerProperty();
	
	protected SimpleBooleanProperty isTo = new SimpleBooleanProperty(true);
	protected SimpleIntegerProperty toXPixel = new SimpleIntegerProperty();
	protected SimpleIntegerProperty toYPixel = new SimpleIntegerProperty();
	protected SimpleIntegerProperty toXPercent = new SimpleIntegerProperty();
	protected SimpleIntegerProperty toYPercent = new SimpleIntegerProperty();
	protected SimpleObjectProperty<LinearDirection> toDirection = new SimpleObjectProperty<LinearDirection>();
	
	public LinearSettingsLayout(GradientBuilderApp app){
		super();
		this.app = app;
		this.grid = new GridPane();
		this.grid.setVgap(10);
		addListeners();
		configure();
	}
	
	private void addListeners() {
		isFrom.addListener(changeListener);
		isFromPixel.addListener(changeListener);
		fromXPixel.addListener(changeListener);
		fromYPixel.addListener(changeListener);
		fromXPercent.addListener(changeListener);
		fromYPercent.addListener(changeListener);
		
		isTo.addListener(changeListener);
		toXPixel.addListener(changeListener);
		toYPixel.addListener(changeListener);
		toXPercent.addListener(changeListener);
		toYPercent.addListener(changeListener);
		toDirection.addListener(changeListener);
		
		isRepeat.addListener(changeListener);
		repeatReflect.addListener(changeListener);
	}

	private void configure() {
		/* Output Heading*/
		Label outputHeading = LabelBuilder.create().text("Syntax Output :").styleClass("heading1").build();
		
		/* Output TextArea*/
		TextArea textArea = TextAreaBuilder.create().prefHeight(100).minHeight(60).wrapText(true).build();
		textArea.textProperty().bind(gradientSyntax);
		layout.getChildren().addAll(outputHeading, textArea);
		
		/* Settings Heading*/
		Label settingsHeading = LabelBuilder.create().text("Settings :").styleClass("heading1").build();
		layout.getChildren().addAll(settingsHeading, this.grid);
		
		int rowIndex =0;
		
		/* From */
		CheckBox fromCB = new CheckBox();
		fromCB.selectedProperty().bindBidirectional(isFrom);
		
		ToggleGroup grp = new ToggleGroup();
		RadioButton percentBtn = RadioButtonBuilder.create().id("per").text("Percentage").toggleGroup(grp).build();
		RadioButton pixelBtn = RadioButtonBuilder.create().id("pix").text("Pixel").toggleGroup(grp).build();
		percentBtn.disableProperty().bind(fromCB.selectedProperty().not());
		pixelBtn.disableProperty().bind(fromCB.selectedProperty().not());
		
		this.grid.add(fromCB, 0, rowIndex);
		this.grid.add(new Label("From : "), 1, rowIndex);
		this.grid.add(HBoxBuilder.create().alignment(Pos.CENTER_LEFT).spacing(10).children(percentBtn,pixelBtn).build(), 2, rowIndex);
		rowIndex++;
		
		// From Percent Container fields
		SliderTextField fromXPercentField = new SliderTextField(-120, 120, 0, "%");
		fromXPercentField.sliderDisableProperty().bind(fromCB.selectedProperty().not());
		fromXPercent.bindBidirectional(fromXPercentField.valueProperty());
		
		SliderTextField fromYPercentField = new SliderTextField(-120, 120, 0, "%");
		fromYPercentField.sliderDisableProperty().bind(fromCB.selectedProperty().not());
		fromYPercent.bindBidirectional(fromYPercentField.valueProperty());
		
		final VBox fromPercentLayout = new VBox();
		fromPercentLayout.getChildren().addAll(fromXPercentField, fromYPercentField);
		
		// From Pixel Container fields
		SliderTextField fromXPixelField = new SliderTextField(-120, 120, 0, "px");
		fromXPixelField.sliderDisableProperty().bind(fromCB.selectedProperty().not());
		fromXPixel.bindBidirectional(fromXPixelField.valueProperty());
		
		SliderTextField fromYPixelField = new SliderTextField(-120, 120, 0, "px");
		fromYPixelField.sliderDisableProperty().bind(fromCB.selectedProperty().not());
		fromYPixel.bindBidirectional(fromYPixelField.valueProperty());
		
		final VBox fromPixelLayout = new VBox();
		fromPixelLayout.getChildren().addAll(fromXPixelField, fromYPixelField);
		
		final StackPane fromContainer = new StackPane();
		
		this.grid.add(fromContainer, 2, rowIndex);
		rowIndex++;
		
		grp.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> arg0,	Toggle arg1, Toggle arg2) {
				RadioButton btn = (RadioButton)arg2;
				fromContainer.getChildren().clear();
				if(btn.getId().equals("per")){
					fromContainer.getChildren().add(fromPercentLayout);
				}else{
					fromContainer.getChildren().add(fromPixelLayout);
				}
			}
		});
		grp.selectToggle(percentBtn);
		
		/* To */
		CheckBox toCB = new CheckBox();
		toCB.selectedProperty().bindBidirectional(isTo);
		ChoiceBox<LinearDirection> toChoice = new ChoiceBox<LinearDirection>();
		toChoice.disableProperty().bind(toCB.selectedProperty().not());
		toChoice.setItems(LinearDirection.getList());
		toChoice.getSelectionModel().select(LinearDirection.BOTTOM);
		toDirection.bind(toChoice.getSelectionModel().selectedItemProperty());
		
		this.grid.add(toCB, 0, rowIndex);
		this.grid.add(new Label("To : "), 1, rowIndex);
		this.grid.add(toChoice, 2, rowIndex);
		rowIndex++;
		
		/* Repeat Or Reflect*/
		CheckBox repeatCB = new CheckBox();
		repeatCB.selectedProperty().bindBidirectional(isRepeat);
		ChoiceBox<RepeatOrReflect> repeatChoice = new ChoiceBox<RepeatOrReflect>();
		repeatChoice.disableProperty().bind(repeatCB.selectedProperty().not());
		repeatChoice.setItems(RepeatOrReflect.getList());
		repeatChoice.getSelectionModel().select(0);
		repeatReflect.bind(repeatChoice.getSelectionModel().selectedItemProperty());
		
		this.grid.add(repeatCB, 0, rowIndex);
		this.grid.add(new Label("Repeat or Reflect : "), 1, rowIndex);
		this.grid.add(repeatChoice, 2, rowIndex);
		rowIndex++;
		
		/* Color Stops */
		colorStopsVB = VBoxBuilder.create().spacing(15).build();
		colorStopsVB.getChildren().addAll(getColorStopTemplate(0, 100, 0, -1),
										  getColorStopTemplate(0, 100, 0, -1));
		
		this.grid.add(StackPaneBuilder.create().alignment(Pos.TOP_LEFT).padding(new Insets(5,0,0,0)).children(new Label("Color Stops : ")).build(), 1, rowIndex);
		this.grid.add(colorStopsVB, 2, rowIndex);
		rowIndex++;
		
		checkForDeleteBtn();
		
		this.grid.getColumnConstraints().addAll(ColumnConstraintsBuilder.create().minWidth(20).build(),
				                                ColumnConstraintsBuilder.create().minWidth(110).build()  );
	}

	public void buildGradient() {
		StringBuilder sytx = new StringBuilder(bgLinear);
		
		// From
		if(isFrom.get()){
			
		}
		
		// To
		if(isTo.get() && toDirection.getValue()!=null){
			sytx.append(to);
			sytx.append(toDirection.getValue().toString());
			sytx.append(separator);
		}
		
		// Repeat or Reflect
		if(isRepeat.get() && repeatReflect.getValue()!=null && !repeatReflect.getValue().equals(RepeatOrReflect.NONE)){
			sytx.append(repeatReflect.getValue().toString());
			sytx.append(separator);
		}
		
		// Color Stops
		ColorStopDTO dto;
		for (int i=0 ; i<colorStops.size(); i++) {
			dto =colorStops.get(i);
			if(dto.getColorCode()!=null && !dto.getColorCode().equals("")){
				sytx.append(dto.getColorCode());
				
				if(dto.getPercent()>0){
					sytx.append(spacer).append(dto.getPercent()).append(colorStopUnit);
				}
				
				if(i<(colorStops.size()-1)){
					sytx.append(separator);
				}
			}
		}
		
		sytx.append(bgGradEnd);
		gradientSyntax.set(sytx.toString());
		
		// Setting the result style to nodes.
		app.applyStyles(gradientSyntax.get());
		
	}
}
