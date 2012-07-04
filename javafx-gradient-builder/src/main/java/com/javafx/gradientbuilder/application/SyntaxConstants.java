package com.javafx.gradientbuilder.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface SyntaxConstants {
	public String separator=", ";
	public String bgTxt = "-fx-background-color: ";
	public String bgRadial = "radial-gradient(";
	public String bgLinear = "linear-gradient(";
	public String bgGradEnd = ");";
	public String focusAngleStart = "focus-angle ";
	public String focusAngleUnit = "deg ";
	public String focusDistStart = "focus-distance ";
	public String focusDistUnit = "% ";
	public String centerStart = "center ";
	public String centerUnit = "% ";
	public String radiusStart = "radius ";
	public String radiusPercentUnit = "% ";
	public String radiusPixelUnit = "px ";
	public String repeat = "repeat ";
	public String reflect = "reflect ";
	public String colorStopUnit="% ";
	public String pointPercentUnit = "% ";
	public String pointPixelUnit = "px ";
	
	public enum POINT {
		TOP("top"), LEFT("left"), BOTTOM("bottom"),  RIGHT("right"), 
		TOP_LEFT("top left"), TOP_RIGHT("top right"), 
		BOTTOM_LEFT("bottom left"), BOTTOM_RIGHT("bottom right");
		
		String value;
		POINT(String value){
			this.value= value;
		}
		@Override
		public String toString() {
			return this.value;
		}
	}
	
	public enum REPEAT {
		REPEAT("repeat"), REFLECT("reflect");
		
		String value;
		REPEAT(String value){
			this.value= value;
		}
		
		public static ObservableList<REPEAT> getList(){
			ObservableList<REPEAT> list = FXCollections.observableArrayList();
			list.addAll(REPEAT.values());
			return list;
		}
		@Override
		public String toString() {
			return this.value;
		}
	}
	 
	
}
