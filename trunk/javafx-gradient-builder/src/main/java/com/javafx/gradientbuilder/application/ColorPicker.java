package com.javafx.gradientbuilder.application;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.HBox;

public class ColorPicker extends HBox{
	private SimpleStringProperty colorCode = new SimpleStringProperty();
	
	public ColorPicker(){
		super();
	}

	public SimpleStringProperty colorCodeProperty() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode.set(colorCode);
	}
	
	public void getColorCode() {
		this.colorCode.get();
	}
	
	
}
