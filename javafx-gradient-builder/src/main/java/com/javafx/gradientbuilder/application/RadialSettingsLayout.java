package com.javafx.gradientbuilder.application;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.layout.ColumnConstraintsBuilder;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBoxBuilder;

public class RadialSettingsLayout extends AbstractSettingsLayout implements SyntaxConstants{

	protected SimpleBooleanProperty isFocusAngle = new SimpleBooleanProperty(true);
	protected SimpleIntegerProperty focusAngle = new SimpleIntegerProperty();
	protected SimpleBooleanProperty isFocusDistance = new SimpleBooleanProperty(true);
	protected SimpleIntegerProperty focusDistance = new SimpleIntegerProperty();
	protected SimpleBooleanProperty isCenter = new SimpleBooleanProperty(true);
	protected SimpleIntegerProperty centerX = new SimpleIntegerProperty();
	protected SimpleIntegerProperty centerY = new SimpleIntegerProperty();
	protected SimpleIntegerProperty radius = new SimpleIntegerProperty();
	
	public RadialSettingsLayout(GradientBuilderApp app){
		super();
		this.app = app;
		this.grid = new GridPane();
		this.grid.setVgap(10);
		addListeners();
		configure();
	}

	private void addListeners() {
		focusAngle.addListener(changeListener);
		focusDistance.addListener(changeListener);
		centerX.addListener(changeListener);
		centerY.addListener(changeListener);
		radius.addListener(changeListener);
		repeatReflect.addListener(changeListener);
		
		isFocusAngle.addListener(changeListener);
		isFocusDistance.addListener(changeListener);
		isCenter.addListener(changeListener);
		isRepeat.addListener(changeListener);
	}

	private void configure() {
		
		/* Output Heading*/
		Label outputHeading = LabelBuilder.create().text("Syntax Output :").styleClass("heading1").build();
		
		/* Output TextArea*/
		TextArea outputText = TextAreaBuilder.create().prefHeight(100).minHeight(60).wrapText(true).build();
		outputText.textProperty().bind(gradientSyntax);
		getChildren().addAll(outputHeading, outputText);
		
		/* Settings Heading*/
		Label settingsHeading = LabelBuilder.create().text("Settings :").styleClass("heading1").build();
		getChildren().addAll(settingsHeading, this.grid);
		
		int rowIndex =0;
		/* Focus Angle*/
		CheckBox focusAngleCB = new CheckBox();
		focusAngleCB.selectedProperty().bindBidirectional(isFocusAngle);
		SliderTextField focusAngleField = new SliderTextField(0, 360, 0, "deg");
		focusAngleField.sliderDisableProperty().bind(focusAngleCB.selectedProperty().not());
		focusAngle.bindBidirectional(focusAngleField.valueProperty());
		
		this.grid.add(focusAngleCB, 0, rowIndex);
		this.grid.add(new Label("Focus Angle : "), 1, rowIndex);
		this.grid.add(focusAngleField, 2, rowIndex, 2, 1);
		rowIndex++;
		
		/* Focus Distance*/
		CheckBox focusDistCB = new CheckBox();
		focusDistCB.selectedProperty().bindBidirectional(isFocusDistance);
		SliderTextField focusDistField = new SliderTextField(-120, 120, 0, "%");
		focusDistField.sliderDisableProperty().bind(focusDistCB.selectedProperty().not());
		focusDistance.bindBidirectional(focusDistField.valueProperty());
		
		this.grid.add(focusDistCB, 0, rowIndex);
		this.grid.add(new Label("Focus Distance : "), 1, rowIndex);
		this.grid.add(focusDistField, 2, rowIndex, 2, 1);
		rowIndex++;
		
		/* Center */
		CheckBox centerCB = new CheckBox();
		centerCB.selectedProperty().bindBidirectional(isCenter);
		SliderTextField centerXField = new SliderTextField(-120, 120, 50, "%");
		centerXField.sliderDisableProperty().bind(centerCB.selectedProperty().not());
		centerX.bindBidirectional(centerXField.valueProperty());
		
		this.grid.add(centerCB, 0, rowIndex);
		this.grid.add(new Label("Center : "), 1, rowIndex);
		this.grid.add(new Label("X : "), 2, rowIndex);
		this.grid.add(centerXField, 3, rowIndex);
		rowIndex++;
		
		SliderTextField centerYField = new SliderTextField(-120, 120, 50, "%");
		centerYField.sliderDisableProperty().bind(centerCB.selectedProperty().not());
		centerY.bindBidirectional(centerYField.valueProperty());
		
		this.grid.add(new Label("Y : "), 2, rowIndex);
		this.grid.add(centerYField, 3, rowIndex);
		rowIndex++;
		
		/* Radius */
		SliderTextField radiusField = new SliderTextField(0, 120, 50, "%");
		radius.bindBidirectional(radiusField.valueProperty());
		
		this.grid.add(new Label("Radius : "), 1, rowIndex);
		this.grid.add(radiusField, 2, rowIndex, 2, 1);
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
		this.grid.add(repeatChoice, 2, rowIndex, 2, 1);
		rowIndex++;
		
		/* Color Stops */
		colorStopsVB = VBoxBuilder.create().spacing(15).build();
		colorStopsVB.getChildren().addAll(getColorStopTemplate(0, 100, 0, -1),
										  getColorStopTemplate(0, 100, 0, -1));
		
		this.grid.add(StackPaneBuilder.create().alignment(Pos.TOP_LEFT).padding(new Insets(5,0,0,0)).children(new Label("Color Stops : ")).build(), 1, rowIndex);
		this.grid.add(colorStopsVB, 2, rowIndex, 2, 1);
		rowIndex++;
		
		checkForDeleteBtn();
		
		this.grid.getColumnConstraints().addAll(ColumnConstraintsBuilder.create().minWidth(20).build(),
				ColumnConstraintsBuilder.create().minWidth(110).build(),
				ColumnConstraintsBuilder.create().minWidth(20).build()
                );
	}
	
	
	protected void buildGradient() {
		StringBuilder sytx = new StringBuilder(bgRadial);
		
		// Focus Angle
		if(isFocusAngle.get()){
			sytx.append(focusAngleStart).append(focusAngle.get()).append(focusAngleUnit);
			sytx.append(separator);
		}
		
		// Focus Distant
		if(isFocusDistance.get()){
			sytx.append(focusDistStart).append(focusDistance.get()).append(focusDistUnit);
			sytx.append(separator);
		}
		
		// Center
		if(isCenter.get()){
			sytx.append(centerStart).append(centerX.get()).append(centerUnit);
			sytx.append(centerY.get()).append(centerUnit);
			sytx.append(separator);
		}
		
		// Radius
		sytx.append(radiusStart).append(radius.get()).append(radiusPercentUnit);
		sytx.append(separator);
		
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
