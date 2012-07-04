package com.javafx.gradientbuilder.application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class RadialSettingsLayout extends GridPane implements SyntaxConstants{

	private ChangeListener<Number> numberListener = new ChangeListener<Number>() {
		@Override
		public void changed(ObservableValue<? extends Number> arg0,	Number arg1, Number arg2) {
			buildGradient();
			app.applyStyles(gradientSyntax);
		}
	};
	
	private ChangeListener<REPEAT> repeatListener = new ChangeListener<REPEAT>() {
		@Override
		public void changed(ObservableValue<? extends REPEAT> arg0,	REPEAT arg1, REPEAT arg2) {
			buildGradient();
			app.applyStyles(gradientSyntax);
		}
	};
	
	private GradientBuilderApp app;
	private String gradientSyntax;
	private SimpleIntegerProperty focusAngle = new SimpleIntegerProperty();
	private SimpleIntegerProperty focusDistance = new SimpleIntegerProperty();
	private SimpleIntegerProperty centerX = new SimpleIntegerProperty();
	private SimpleIntegerProperty centerY = new SimpleIntegerProperty();
	private SimpleIntegerProperty radius = new SimpleIntegerProperty();
	private SimpleObjectProperty<REPEAT> repeatReflect = new SimpleObjectProperty<REPEAT>();
	
	public RadialSettingsLayout(GradientBuilderApp app){
		super();
		this.app = app;
		setVgap(10);
		addListeners();
		configure();
	}

	private void addListeners() {
		focusAngle.addListener(numberListener);
		focusDistance.addListener(numberListener);
		centerX.addListener(numberListener);
		centerY.addListener(numberListener);
		radius.addListener(numberListener);
		repeatReflect.addListener(repeatListener);
	}

	private void configure() {
		int rowIndex =0;
		/* Focus Angle*/
		SliderTextField focusAngleField = new SliderTextField(0, 360, 0, "deg");
		focusAngle.bindBidirectional(focusAngleField.valueProperty());
		this.add(new Label("Focus Angle : "), 0, rowIndex);
		this.add(focusAngleField, 1, rowIndex, 2, 1);
		rowIndex++;
		
		/* Focus Distance*/
		SliderTextField focusDistField = new SliderTextField(-120, 120, 0, "%");
		focusDistance.bindBidirectional(focusDistField.valueProperty());
		this.add(new Label("Focus Distance : "), 0, rowIndex);
		this.add(focusDistField, 1, rowIndex, 2, 1);
		rowIndex++;
		
		/* Center */
		SliderTextField centerXField = new SliderTextField(-120, 120, 50, "%");
		centerX.bindBidirectional(centerXField.valueProperty());
		this.add(new Label("Center : "), 0, rowIndex);
		this.add(new Label("X : "), 1, rowIndex);
		this.add(centerXField, 2, rowIndex);
		rowIndex++;
		
		SliderTextField centerYField = new SliderTextField(-120, 120, 50, "%");
		centerY.bindBidirectional(centerYField.valueProperty());
		this.add(new Label("Y : "), 1, rowIndex);
		this.add(centerYField, 2, rowIndex);
		rowIndex++;
		
		/* Radius */
		SliderTextField radiusField = new SliderTextField(0, 120, 50, "%");
		radius.bindBidirectional(radiusField.valueProperty());
		this.add(new Label("Radius : "), 0, rowIndex);
		this.add(radiusField, 1, rowIndex, 2, 1);
		rowIndex++;
		
		/* Repeat Or Reflect*/
		ChoiceBox<REPEAT> repeatChoice = new ChoiceBox<REPEAT>();
		repeatChoice.setItems(REPEAT.getList());
		repeatReflect.bind(repeatChoice.getSelectionModel().selectedItemProperty());
		this.add(new Label("Repeat or Reflect : "), 0, rowIndex);
		this.add(repeatChoice, 1, rowIndex, 2, 1);
		rowIndex++;
		
	}
	
	private void buildGradient() {
		StringBuilder sytx = new StringBuilder(bgRadial);
		
		// Focus Angle
		sytx.append(focusAngleStart).append(focusAngle.get()).append(focusAngleUnit);
		sytx.append(separator);
		
		// Focus Distant
		sytx.append(focusDistStart).append(focusDistance.get()).append(focusDistUnit);
		sytx.append(separator);
		
		// Center
		sytx.append(centerStart).append(centerX.get()).append(centerUnit);
		sytx.append(centerY.get()).append(centerUnit);
		sytx.append(separator);
		
		// Radius
		sytx.append(radiusStart).append(radius.get()).append(radiusPercentUnit);
		sytx.append(separator);
		
		// Repeat or Reflect
		if(repeatReflect.getValue()!=null){
			sytx.append(repeatReflect.getValue().toString());
			sytx.append(separator);
		}
		
		//sytx.append("gray, darkgray 75%, dimgray");
		sytx.append("red, darkgray, black");
		sytx.append(bgGradEnd);
		gradientSyntax = sytx.toString();
		System.out.println(gradientSyntax);
	}
}
