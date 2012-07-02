package com.javafx.gradientbuilder.application;


import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.ToolBarBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
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
	
	Rectangle rectangle;
	Ellipse circle;
	
	enum GradientType { LINEAR, RADIAL };
	
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
		
		rectangle = RectangleBuilder.create().style("-fx-fill:yellow;").build();
		final StackPane sp = StackPaneBuilder.create().alignment(Pos.CENTER)
										.children(rectangle)
									   .build();

		rectangle.heightProperty().bind(new DoubleBinding() {
			{
				bind(sp.heightProperty());
			}
			@Override
			protected double computeValue() {
				return sp.getHeight()-20d;
			}
		});
		rectangle.widthProperty().bind(new DoubleBinding() {
			{
				bind(sp.widthProperty());
			}
			@Override
			protected double computeValue() {
				return sp.getWidth()-20d;
			}
		});
		StackPane topPane = StackPaneBuilder.create()
											.padding(new Insets(15))
											.children(sp)
											.build();
		return topPane;
	}
	
	private StackPane configureBottomPane(){
		
		
		circle = EllipseBuilder.create().style("-fx-fill:yellow;").radiusX(20).radiusY(30).build();
		final StackPane sp = StackPaneBuilder.create().alignment(Pos.CENTER)
													  .children(circle)
													  .build();

		circle.radiusXProperty().bind(new DoubleBinding() {
			{
				bind(sp.widthProperty());
			}
			@Override
			protected double computeValue() {
				return sp.getWidth()/2-10d;
			}
		});
		circle.radiusYProperty().bind(new DoubleBinding() {
			{
				bind(sp.heightProperty());
			}
			@Override
			protected double computeValue() {
				return sp.getHeight()/2-10d;
			}
		});
		
		StackPane bottomPane = StackPaneBuilder.create()
											.padding(new Insets(15))
											.children(sp)
											.build();
		return bottomPane;
	}
	
	
	/**
	 * Configures the Gradient setting pane.
	 * @return ScrollPane
	 */
	private ScrollPane configureGradientSettings(){
		StackPane cont = StackPaneBuilder.create().minHeight(900).minWidth(700).build();
		
		ScrollPane scroll = ScrollPaneBuilder.create()
				                             .styleClass("builder-scroll-pane")
				                             .fitToHeight(true)
				                             .fitToWidth(true)
				                             .content(cont)
				                             .build();
		return scroll;
	}

	
}

