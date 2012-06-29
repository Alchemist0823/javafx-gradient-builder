package com.javafx.gradientbuilder.application;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.stage.Stage;

public class GradientBuilderApp extends Application {

	Stage stage;
	Scene scene;
	BorderPane root;
	
	Rectangle rectangle;
	Circle circle;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		
		configure();
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

	private void configure() {
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
		
		rectangle = RectangleBuilder.create().build();
		StackPane sp = StackPaneBuilder.create()
									   .style("-fx-background-color:yellow;")
									   .build();

		StackPane topPane = StackPaneBuilder.create()
											.padding(new Insets(15))
											.children(sp)
											.build();
		return topPane;
	}
	
	private StackPane configureBottomPane(){
		circle = CircleBuilder.create().build();
		StackPane sp = StackPaneBuilder.create()
								  .style("-fx-background-color:yellow;")
								  .build();
		
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

