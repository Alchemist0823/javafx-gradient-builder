package com.javafx.gradientbuilder.application;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GradientBuilderApp extends Application {

	Stage stage;
	Scene scene;
	StackPane root;
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
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
	    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		root = new StackPane();
		BorderPane bp = new BorderPane();
		bp.setCenter(root);
		bp.autosize();
		this.scene = new Scene(bp, Color.WHITE);
		//scene.getStylesheets().add("styles/template.css");
	}

	private void configure() {
		SplitPane sp = new SplitPane();
		root.getChildren().add(sp);
		
		StackPane topPane = StackPaneBuilder.create().style("-fx-background-color:gold").build();
		StackPane bottomPane = StackPaneBuilder.create().style("-fx-background-color:red").build();
		
		//StackPane leftPane = StackPaneBuilder.create().style("-fx-background-color:red").build();
		SplitPane leftPane = new SplitPane();
		leftPane.setOrientation(Orientation.VERTICAL);
		leftPane.getItems().addAll(topPane,bottomPane);
		
		StackPane rightPane = StackPaneBuilder.create().style("-fx-background-color:yellow").build();
		sp.getItems().addAll(leftPane,rightPane);
		
	}

	
}

