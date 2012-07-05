package com.javafx.gradientbuilder.application;


import javafx.application.Application;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.ToolBarBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.GridPaneBuilder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GradientBuilderApp extends Application {

	Stage stage;
	Scene scene;
	BorderPane root;
	
	StackPane rectangle;
	StackPane circle;
	
	enum GradientType { LINEAR, RADIAL };
	
	StackPane linearSettingLayout;
	StackPane radialSettingLayout;
	StackPane settingsContainer;
	
		// Properties
	private SimpleObjectProperty<GradientType> gradientType = new SimpleObjectProperty<GradientType>();
	
	
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
		
		//ScenicView.show(scene);
		
	}

	private void configureToolBar() {
		ToolBar tb = ToolBarBuilder.create().prefHeight(35).build();
		
		final CustomRadioButton linearButton = new CustomRadioButton("Linear");
		linearButton.setSelected(true);
		
		final CustomRadioButton radialButton = new CustomRadioButton("Radial");
		
		gradientType.addListener(new ChangeListener<GradientType>() {
			@Override
			public void changed(ObservableValue<? extends GradientType> arg0,	GradientType arg1, GradientType type) {
				switch(type){
				case LINEAR:
					settingsContainer.getChildren().clear();
					settingsContainer.getChildren().add(linearSettingLayout);
					break;
				case RADIAL:
					settingsContainer.getChildren().clear();
					settingsContainer.getChildren().add(radialSettingLayout);
					break;
				}
			}
		});
		
		EventHandler<ActionEvent> btnAction = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(linearButton.getSelected()){
					linearButton.setSelected(false);
					radialButton.setSelected(true);
					gradientType.set(GradientType.RADIAL);
				}else{
					linearButton.setSelected(true);
					radialButton.setSelected(false);
					gradientType.set(GradientType.LINEAR);
				}
			}
		};
		linearButton.setOnAction(btnAction);
		radialButton.setOnAction(btnAction);
		
		tb.getItems().addAll(linearButton, radialButton);
		root.setTop(tb);
		radialButton.fire();
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
		StackPane topPane = configureTopPane();
		StackPane bottomPane = configureBottomPane();
		ScrollPane rightPane = configureGradientSettings();
		
		SplitPane leftPane = new SplitPane();
		leftPane.setOrientation(Orientation.VERTICAL);
		leftPane.getItems().addAll(topPane,bottomPane);
		
		SplitPane mainPane = new SplitPane();
		mainPane.getItems().addAll(leftPane,rightPane);
		
		root.setCenter(mainPane);
	}
	
	private StackPane configureTopPane(){
		
		rectangle = StackPaneBuilder.create().alignment(Pos.CENTER)
										.style("-fx-background-color:yellow;")
										.build();

		HBox hb = HBoxBuilder.create()
							 .alignment(Pos.CENTER_RIGHT)
							 .prefHeight(20).build();
		
		Label widthLbl = getValueLabel();
		widthLbl.textProperty().bind(new StringBinding() {
			{
				bind(rectangle.widthProperty());
			}
			@Override
			protected String computeValue() {
				return rectangle.getWidth()+"px";
			}
		});
		
		Label heightLbl = getValueLabel();
		heightLbl.textProperty().bind(new StringBinding() {
			{
				bind(rectangle.heightProperty());
			}
			@Override
			protected String computeValue() {
				return rectangle.getHeight()+"px";
			}
		});
		
		hb.getChildren().addAll(getBoldLabel("Width : "), widthLbl, getSpacer(), getBoldLabel("Height : "), heightLbl);
		
		BorderPane bp = new BorderPane();
		bp.setCenter(rectangle);
		bp.setBottom(hb);
		
		StackPane topPane = StackPaneBuilder.create()
											.padding(new Insets(15,15,3,15))
											.children(bp)
											.build();
		return topPane;
	}
	
	private StackPane configureBottomPane(){
		
		circle = StackPaneBuilder.create().alignment(Pos.CENTER)
										  .styleClass("circle-shape")
										  .style("-fx-background-color:yellow;")
										  .build();

		
		HBox hb = HBoxBuilder.create()
							 .alignment(Pos.CENTER_RIGHT)
							 .prefHeight(20).build();
		
		Label radiusX = getValueLabel();
		radiusX.textProperty().bind(new StringBinding() {
			{
				bind(circle.widthProperty());
			}
			@Override
			protected String computeValue() {
				return (circle.getWidth())/2+"px";
			}
		});
		
		Label radiusY = getValueLabel();
		radiusY.textProperty().bind(new StringBinding() {
			{
				bind(circle.heightProperty());
			}
			@Override
			protected String computeValue() {
				return ((circle.getHeight()/2))+"px";
			}
		});
		hb.getChildren().addAll(getBoldLabel("X-Radius : "), radiusX, getSpacer(), getBoldLabel("Y-Radius : "),radiusY);
		
		BorderPane bp = new BorderPane();
		bp.setCenter(circle);
		bp.setBottom(hb);
		
		StackPane bottomPane = StackPaneBuilder.create()
											.padding(new Insets(15,15,3,15))
											.children(bp)
											.build();
		return bottomPane;
	}
	
	private StackPane getSpacer(){
		return StackPaneBuilder.create().prefWidth(20).build();
	}
	
	private Label getBoldLabel(String str){
		return LabelBuilder.create()
						   .text(str)
						   .style("-fx-font-weight:bold;").build();
	}
	
	private Label getValueLabel(){
		return LabelBuilder.create()
						   .style("-fx-font-family:verdana;").build();
	}
	
	
	/**
	 * Configures the Gradient setting pane.
	 * @return ScrollPane
	 */
	private ScrollPane configureGradientSettings(){
		radialSettingLayout = configureRadialSettings();
		linearSettingLayout = StackPaneBuilder.create().alignment(Pos.TOP_LEFT).children(new Label("Linear")).build();
		
		settingsContainer = StackPaneBuilder.create().alignment(Pos.TOP_LEFT).build();

		ScrollPane scroll = ScrollPaneBuilder.create()
				                             .styleClass("builder-scroll-pane")
				                             .fitToHeight(true)
				                             .fitToWidth(true)
				                             .content(settingsContainer)
				                             .build();
		
		
		
		return scroll;
	}

	private StackPane configureRadialSettings() {
		GridPane grid = new RadialSettingsLayout(this);
		
		StackPane cont = StackPaneBuilder.create().alignment(Pos.TOP_LEFT).children(grid).build();
		return cont;
	}

	public void applyStyles(String bg){
		rectangle.setStyle("-fx-background-color:"+bg);
		circle.setStyle("-fx-background-color:"+bg);
		
	}
}

