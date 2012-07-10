package com.javafx.gradientbuilder.application;

import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;

import com.javafx.gradientbuilder.application.SyntaxConstants.RepeatOrReflect;

public abstract class AbstractSettingsLayout extends StackPane{
	
	protected GradientBuilderApp app;
	protected SimpleStringProperty gradientSyntax = new SimpleStringProperty("");
	
	protected SimpleBooleanProperty isRepeat = new SimpleBooleanProperty(true);
	protected SimpleObjectProperty<RepeatOrReflect> repeatReflect = new SimpleObjectProperty<RepeatOrReflect>();
	protected ObservableList<ColorStopDTO> colorStops = FXCollections.observableArrayList();
	
	protected VBox layout;
	protected VBox colorStopsVB;
	protected GridPane grid;
	
	protected ChangeListener<Object> changeListener = new ChangeListener<Object>() {
		@Override
		public void changed(ObservableValue<? extends Object> arg0,	Object arg1, Object arg2) {
			buildGradient();
		}
	};
	
	public AbstractSettingsLayout() {
		super();
		setAlignment(Pos.TOP_LEFT);
		layout = new VBox();
		layout.setSpacing(10);
		layout.setPadding(new Insets(10));
		getChildren().add(layout);
	}

	protected abstract void buildGradient();
	
	protected HBox getColorStopTemplate(int startValue, int endValue, int pos, int finalPos){
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
	
	
	protected int getColorStopPosition(HBox current){
		int finalPos = -1;
		for (int i=0 ; i<colorStopsVB.getChildren().size(); i++) {
			if(colorStopsVB.getChildren().get(i) == current){
				finalPos = i;
				break;
			}
		}
		return finalPos;
	}
	
	protected void checkForDeleteBtn(){
		boolean flag = (colorStopsVB.getChildren().size()>2) ? true : false;
		for (Node node : colorStopsVB.getChildren()) {
			if(flag){
				((HBox)node).getChildren().get(3).setDisable(false);
			}else{
				((HBox)node).getChildren().get(3).setDisable(true);
			}
		}
	}
	
}
