module application{
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.media;
	requires javafx.swing;
	requires javafx.web;
	
	exports application;
	exports application.Controller;
	exports application.model.appmodel;
	opens application to javafx.fxml;
	opens application.Controller to javafx.fxml;
}