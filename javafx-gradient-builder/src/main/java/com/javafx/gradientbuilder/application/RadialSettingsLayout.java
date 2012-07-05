package com.javafx.gradientbuilder.application;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;

public class RadialSettingsLayout extends GridPane implements SyntaxConstants{

	private ChangeListener<Object> changeListener = new ChangeListener<Object>() {
		@Override
		public void changed(ObservableValue<? extends Object> arg0,	Object arg1, Object arg2) {
			buildGradient();
		}
	};
	
	private GradientBuilderApp app;
	private SimpleStringProperty gradientSyntax = new SimpleStringProperty();
	private SimpleBooleanProperty isFocusAngle = new SimpleBooleanProperty(true);
	private SimpleIntegerProperty focusAngle = new SimpleIntegerProperty();
	private SimpleBooleanProperty isFocusDistance = new SimpleBooleanProperty(true);
	private SimpleIntegerProperty focusDistance = new SimpleIntegerProperty();
	private SimpleBooleanProperty isCenter = new SimpleBooleanProperty(true);
	private SimpleIntegerProperty centerX = new SimpleIntegerProperty();
	private SimpleIntegerProperty centerY = new SimpleIntegerProperty();
	private SimpleIntegerProperty radius = new SimpleIntegerProperty();
	private SimpleBooleanProperty isRepeat = new SimpleBooleanProperty(true);
	private SimpleObjectProperty<REPEAT> repeatReflect = new SimpleObjectProperty<REPEAT>();
	private ObservableList<ColorStopDTO> colorStops = FXCollections.observableArrayList();
	
	VBox colorStopsVB;
	
	public RadialSettingsLayout(GradientBuilderApp app){
		super();
		this.app = app;
		setVgap(10);
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
		int rowIndex =0;
		
		/* TextArea*/
		TextArea outputText = TextAreaBuilder.create().prefHeight(100).wrapText(true).build();
		outputText.textProperty().bind(gradientSyntax);
		this.add(outputText, 0, rowIndex, 4, 1);
		rowIndex++;
		
		/* Focus Angle*/
		CheckBox focusAngleCB = new CheckBox();
		focusAngleCB.selectedProperty().bindBidirectional(isFocusAngle);
		SliderTextField focusAngleField = new SliderTextField(0, 360, 0, "deg");
		focusAngleField.sliderDisableProperty().bind(focusAngleCB.selectedProperty().not());
		focusAngle.bindBidirectional(focusAngleField.valueProperty());
		
		this.add(focusAngleCB, 0, rowIndex);
		this.add(new Label("Focus Angle : "), 1, rowIndex);
		this.add(focusAngleField, 2, rowIndex, 2, 1);
		rowIndex++;
		
		/* Focus Distance*/
		CheckBox focusDistCB = new CheckBox();
		focusDistCB.selectedProperty().bindBidirectional(isFocusDistance);
		SliderTextField focusDistField = new SliderTextField(-120, 120, 0, "%");
		focusDistField.sliderDisableProperty().bind(focusDistCB.selectedProperty().not());
		focusDistance.bindBidirectional(focusDistField.valueProperty());
		
		this.add(focusDistCB, 0, rowIndex);
		this.add(new Label("Focus Distance : "), 1, rowIndex);
		this.add(focusDistField, 2, rowIndex, 2, 1);
		rowIndex++;
		
		/* Center */
		CheckBox centerCB = new CheckBox();
		centerCB.selectedProperty().bindBidirectional(isCenter);
		SliderTextField centerXField = new SliderTextField(-120, 120, 50, "%");
		centerXField.sliderDisableProperty().bind(centerCB.selectedProperty().not());
		centerX.bindBidirectional(centerXField.valueProperty());
		
		this.add(centerCB, 0, rowIndex);
		this.add(new Label("Center : "), 1, rowIndex);
		this.add(new Label("X : "), 2, rowIndex);
		this.add(centerXField, 3, rowIndex);
		rowIndex++;
		
		SliderTextField centerYField = new SliderTextField(-120, 120, 50, "%");
		centerYField.sliderDisableProperty().bind(centerCB.selectedProperty().not());
		centerY.bindBidirectional(centerYField.valueProperty());
		
		this.add(new Label("Y : "), 2, rowIndex);
		this.add(centerYField, 3, rowIndex);
		rowIndex++;
		
		/* Radius */
		SliderTextField radiusField = new SliderTextField(0, 120, 50, "%");
		radius.bindBidirectional(radiusField.valueProperty());
		
		this.add(new Label("Radius : "), 1, rowIndex);
		this.add(radiusField, 2, rowIndex, 2, 1);
		rowIndex++;
		
		/* Repeat Or Reflect*/
		CheckBox repeatCB = new CheckBox();
		repeatCB.selectedProperty().bindBidirectional(isRepeat);
		ChoiceBox<REPEAT> repeatChoice = new ChoiceBox<REPEAT>();
		repeatChoice.disableProperty().bind(repeatCB.selectedProperty().not());
		repeatChoice.setItems(REPEAT.getList());
		repeatReflect.bind(repeatChoice.getSelectionModel().selectedItemProperty());
		
		this.add(repeatCB, 0, rowIndex);
		this.add(new Label("Repeat or Reflect : "), 1, rowIndex);
		this.add(repeatChoice, 2, rowIndex, 2, 1);
		rowIndex++;
		
		/* Color Stops */
		colorStopsVB = VBoxBuilder.create().spacing(15).build();
		colorStopsVB.getChildren().addAll(getColorStopTemplate(0, 100, 0, -1),
										  getColorStopTemplate(0, 100, 0, -1));
		
		this.add(new Label("Color Stops : "), 1, rowIndex);
		this.add(colorStopsVB, 2, rowIndex, 2, 1);
		rowIndex++;
		
		checkForDeleteBtn();
	}
	
	private HBox getColorStopTemplate(int startValue, int endValue, int pos, int finalPos){
		ColorStopDTO dto = new ColorStopDTO();
		dto.colorCodeProperty().addListener(changeListener);
		dto.percentProperty().addListener(changeListener);
		
		if(finalPos==-1){
			colorStops.add(dto);
		}else{
			colorStops.add(finalPos, dto);
		}
		
		SliderTextField sliderTF = new SliderTextField(startValue, endValue, pos);
		dto.percentProperty().bindBidirectional(sliderTF.valueProperty());
		
		ColorPicker colorPicker = new ColorPicker();
		dto.colorCodeProperty().bindBidirectional(colorPicker.colorCodeProperty());
		
		Button add = ButtonBuilder.create()
								  .styleClass("transparentButton")
								  .graphic(new ImageView(new Image(ColorPicker.class.getResource("/images/add.png").toExternalForm())))
								  .build();
		Button delete = ButtonBuilder.create()
								  .styleClass("transparentButton")
								  .graphic(new ImageView(new Image(ColorPicker.class.getResource("/images/delete.png").toExternalForm())))
								  .build();

		final HBox hb = HBoxBuilder.create()
							 .maxHeight(30)
							 .spacing(20)
							 .alignment(Pos.CENTER_LEFT)
							 .children(colorPicker, sliderTF, StackPaneBuilder.create().alignment(Pos.CENTER_LEFT).padding(new Insets(0,0,0,20)).children(add).build(),delete)
							 .build();
		
		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				addNewColorStop(hb);
			}
		});
		
		delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				deleteColorStop(hb);
			}
		});
		
		
		return hb;
	}
	
	private void addNewColorStop(HBox current){
		int finalPos = getColorStopPosition(current);
		finalPos = (finalPos == colorStopsVB.getChildren().size()-1 || finalPos == -1) ? -1 : (finalPos+1);
		if(finalPos ==-1){
			colorStopsVB.getChildren().add(getColorStopTemplate(0, 100, 0, finalPos));
		}else{
			colorStopsVB.getChildren().add(finalPos, getColorStopTemplate(0, 100, 0, finalPos));
		}
		buildGradient();
		checkForDeleteBtn();
	}
	
	private void deleteColorStop(HBox current){
		int finalPos = getColorStopPosition(current);
		
		colorStops.get(finalPos).colorCodeProperty().removeListener(changeListener);
		colorStops.get(finalPos).percentProperty().removeListener(changeListener);
		colorStops.remove(finalPos);
		
		colorStopsVB.getChildren().remove(current);
		buildGradient();
		checkForDeleteBtn();
	}
	
	
	private int getColorStopPosition(HBox current){
		int finalPos = -1;
		for (int i=0 ; i<colorStopsVB.getChildren().size(); i++) {
			if(colorStopsVB.getChildren().get(i) == current){
				finalPos = i;
				break;
			}
		}
		return finalPos;
	}
	
	private void checkForDeleteBtn(){
		boolean flag = (colorStopsVB.getChildren().size()>2) ? true : false;
		for (Node node : colorStopsVB.getChildren()) {
			if(flag){
				((HBox)node).getChildren().get(3).setDisable(false);
			}else{
				((HBox)node).getChildren().get(3).setDisable(true);
			}
		}
	}
	
	private void buildGradient() {
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
		if(isRepeat.get() && repeatReflect.getValue()!=null && !repeatReflect.getValue().equals(REPEAT.NONE)){
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
		System.out.println(gradientSyntax.get());
		
		// Setting the result style to nodes.
		app.applyStyles(gradientSyntax.get());
	}
}
