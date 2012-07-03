package com.javafx.gradientbuilder.application;


import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.ToolBarBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.EllipseBuilder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.stage.Stage;

public class GradientBuilderApp extends Application {

	Stage stage;
	Scene scene;
	BorderPane root;
	
	StackPane rectangle;
	StackPane circle;
	
	enum GradientType { LINEAR, RADIAL };
	ObservableList<ColorStopDTO> colorStops = FXCollections.observableArrayList();
	VBox colorStopsVB;
	
	// Properties
	private GradientType gradientType = GradientType.LINEAR;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		
		configureCenter();
		configureToolBar();
	}

	private void configureToolBar() {
		ToolBar tb = ToolBarBuilder.create().prefHeight(35).build();
		
		final CustomRadioButton linearButton = new CustomRadioButton("Linear");
		linearButton.setSelected(true);
		
		final CustomRadioButton radialButton = new CustomRadioButton("Radial");
		
		EventHandler<ActionEvent> btnAction = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(linearButton.getSelected()){
					linearButton.setSelected(false);
					radialButton.setSelected(true);
					gradientType = GradientType.RADIAL;
				}else{
					linearButton.setSelected(true);
					radialButton.setSelected(false);
					gradientType = GradientType.LINEAR;
				}
			}
		};
		linearButton.setOnAction(btnAction);
		radialButton.setOnAction(btnAction);
		
		tb.getItems().addAll(linearButton, radialButton);
		root.setTop(tb);
		
	}

	private void configureStage(){
		stage.setTitle("Gradient Builder");
		stage.setWidth(980);
	    stage.setHeight(650);
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		root = new BorderPane();
		root.autosize();
		this.scene = new Scene(root, Color.WHITE);
		this.scene.getStylesheets().add("styles/gradientbuilder.css");
	}

	private void configureCenter() {
		ScrollPane rightPane = configureGradientSettings();
		StackPane topPane = configureTopPane();
		StackPane bottomPane = configureBottomPane();
		
		SplitPane leftPane = new SplitPane();
		leftPane.setOrientation(Orientation.VERTICAL);
		leftPane.getItems().addAll(topPane,bottomPane);
		
		SplitPane mainPane = new SplitPane();
		mainPane.getItems().addAll(leftPane,rightPane);
		
		root.setCenter(mainPane);
	}
	
	private StackPane configureTopPane(){
		
		final StackPane rectangle = StackPaneBuilder.create().alignment(Pos.CENTER)
										.style("-fx-background-color:yellow;")
										.build();

		StackPane topPane = StackPaneBuilder.create()
											.padding(new Insets(15))
											.children(rectangle)
											.build();
		return topPane;
	}
	
	private StackPane configureBottomPane(){
		
		circle = StackPaneBuilder.create().alignment(Pos.CENTER)
										  .styleClass("circle-shape")
										  .style("-fx-background-color:yellow;")
										  .build();

		
		HBox hb = new HBox();
		hb.setAlignment(Pos.CENTER_RIGHT);
		hb.setPrefHeight(20);
		Label radiusX = new Label();
		radiusX.textProperty().bind(new StringBinding() {
			{
				bind(circle.widthProperty());
			}
			@Override
			protected String computeValue() {
				return (circle.getWidth())/2+"px";
			}
		});
		
		Label radiusY = new Label();
		radiusY.textProperty().bind(new StringBinding() {
			{
				bind(circle.heightProperty());
			}
			@Override
			protected String computeValue() {
				return ((circle.getHeight()/2))+"px";
			}
		});
		hb.getChildren().addAll(new Label("X-Radius : "), radiusX, new Label("Y-Radius : "),radiusY);
		
		BorderPane bp = new BorderPane();
		bp.setCenter(circle);
		bp.setBottom(hb);
		
		StackPane bottomPane = StackPaneBuilder.create()
											.padding(new Insets(10))
											.children(bp)
											.build();
		return bottomPane;
	}
	
	
	/**
	 * Configures the Gradient setting pane.
	 * @return ScrollPane
	 */
	private ScrollPane configureGradientSettings(){
		colorStopsVB = VBoxBuilder.create().spacing(15).build();
		for (int i = 0; i < 5; i++) {
			colorStopsVB.getChildren().add(getColorStopTemplate(0, 100, 0));
		}
		StackPane cont = StackPaneBuilder.create()
									.alignment(Pos.CENTER)
									.children(colorStopsVB).build();
		
		ScrollPane scroll = ScrollPaneBuilder.create()
				                             .styleClass("builder-scroll-pane")
				                             .fitToHeight(true)
				                             .fitToWidth(true)
				                             .content(cont)
				                             .build();
		return scroll;
	}

	private HBox getColorStopTemplate(int startValue, int endValue, int pos){
		ColorStopDTO dto = new ColorStopDTO();
		colorStops.add(dto);
		
		SliderTextField sliderTF = new SliderTextField(startValue, endValue, pos);
		dto.percentProperty().bindBidirectional(sliderTF.valueProperty());
		
		ColorPicker colorPicker = new ColorPicker();
		dto.colorCodeProperty().bindBidirectional(colorPicker.colorCodeProperty());
		
		ImageView add = new ImageView(new Image(ColorPicker.class.getResource("/images/add.png").toExternalForm()));
		ImageView delete = new ImageView(new Image(ColorPicker.class.getResource("/images/delete.png").toExternalForm()));
		
		HBox hb = HBoxBuilder.create()
							 .maxHeight(30)
							 .spacing(20)
							 .alignment(Pos.CENTER_LEFT)
							 .children(colorPicker, sliderTF, StackPaneBuilder.create().alignment(Pos.CENTER_LEFT).padding(new Insets(0,0,0,20)).children(add).build(),delete)
							 .build();
		
		return hb;
	}
}

