package UI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

/**
 *
 * @author sakorpi
 */

import Database.*;
import Domain.*;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Font;


public class App extends Application {

    @Override
    public void start(Stage win) throws SQLException {
        
        // Create app service:
        Service service = new Service();
        
        // Create window components
        BorderPane mainwindow = new BorderPane();
        BorderPane workwindow = new BorderPane();
        VBox testBox = new VBox();
        
        // Create login field:
        VBox loginfield = new VBox();
        Button loginbtn = new Button("Kirjaudu");
        loginbtn.setDefaultButton(true);
        Button formatbtn = new Button("Alusta tietokanta (ensimmäinen käynnistys)");
        TextField loginfieldtext = new TextField("Anna tunnus");
        Label logintext = new Label("Kirjaudu sisään:");
        Label feedbacktext = new Label("");
        loginfield.getChildren().addAll(logintext, loginfieldtext, loginbtn, feedbacktext);
        loginfield.setPrefSize(200, 400);
        loginfield.setSpacing(10);
        
        // Create topic field:
        VBox welcomefield = new VBox();
        Label welcometext = new Label("Tuotannonohjaus DICIP");
        welcomefield.getChildren().add(welcometext);
        welcometext.setAlignment(Pos.CENTER);
        welcometext.setFont(Font.font("Monospace", 20));
        welcomefield.setSpacing(20);
        welcomefield.setAlignment(Pos.CENTER);
        welcomefield.autosize();
        Insets spaces = new Insets(40,40,40,40);
        welcometext.setPadding(spaces);
        
        // Create bottom field:
        HBox bottomfield = new HBox();
        bottomfield.getChildren().add(formatbtn);
        bottomfield.setAlignment(Pos.CENTER);


        // Create center field:
        VBox centerfield = new VBox();
        Label centertext = new Label("CENTERTEXT plaaplaplaaplaa");
        centerfield.getChildren().add(centertext);
        centerfield.setPrefSize(200, 400);
        centerfield.setMinHeight(400);
        centerfield.setSpacing(20);
        
        // Create adminstrative fields:
        // Controlfield:
        HBox adminFieldTop = new HBox();
        Button addUserbtn2 = new Button("Lisää käyttäjä");
        adminFieldTop.getChildren().add(addUserbtn2);
        

        // AddUserField:
        Button addUserbtn = new Button("Lisää käyttäjä");
        TextField addUserTextField = new TextField("");
        addUserTextField.setPromptText("Anna tunnus");
        addUserTextField.setMaxWidth(200);
        Label addUserFeedback = new Label("feedback");
        
        // Radio buttons for status choise:
        final ToggleGroup group = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Työntekjä");
        rb1.setId("0");
        rb1.setToggleGroup(group);
        rb1.setSelected(true);
        RadioButton rb2 = new RadioButton("Työnjohtaja");
        rb1.setId("1");
        rb2.setToggleGroup(group);
        
        // Empty field center
        VBox emptybox = new VBox();
        emptybox.setPrefSize(200, 400);
        emptybox.setSpacing(20);
        
        // Admin field right
        Label righttext = new Label("Plaaplaaplaapalapa");
        VBox adminFieldRight = new VBox();
        adminFieldRight.setPrefWidth(300);
        adminFieldRight.setSpacing(20);
        adminFieldRight.getChildren().add(righttext);

        // Admin field center
        VBox adminField = new VBox();
        adminField.setSpacing(20);
        adminField.getChildren().addAll(adminFieldTop, addUserTextField, rb1, rb2, addUserbtn, addUserFeedback);
  
        // Create left field in workwindow:
        VBox leftfield = new VBox();
        Label lefttext = new Label("leftTEXT plaaplaplaaplaa");
        Button backbtn = new Button("Aloitussivu");
        backbtn.setCancelButton(true);
        Button adminbtn = new Button("Hallinta");
        adminbtn.setDisable(true);
        Button orderbtn = new Button("Tuotanto");
        leftfield.getChildren().addAll(lefttext, adminbtn, orderbtn, backbtn);
        leftfield.setPrefSize(200, 400);
        leftfield.setSpacing(20);
        
        // Create workfield center
        VBox workField = new VBox();
        workField.setSpacing(10);
        workField.setPrefWidth(400);
        Label workLabel = new Label("TÄHÄN TULEE TEKSTIÄ");
        workField.getChildren().add(workLabel);
        
        // Workfield right
        Label rightText = new Label("WORKWIEW RIGHT");
        Button seekbtnCode = new Button("Etsi tilaus koodilla");
        Button seekbtnDate = new Button("Etsi tilaukset päivämäärällä");
        Button createOrder = new Button("Luo uusi tilaus");
        Button createPhase = new Button("Lisää työvaihe");
        VBox workFieldRight = new VBox();
        workFieldRight.setPrefWidth(300);
        workFieldRight.setSpacing(20);
        workFieldRight.getChildren().addAll(rightText, createOrder, createPhase, seekbtnCode, seekbtnDate);
        
        // AddOrderField:
        Button addOrderbtn = new Button("Lisää tilaus");
        addOrderbtn.setDefaultButton(true);
        TextField addOrderTextField = new TextField("");
        addOrderTextField.setPromptText("Luo tilauskoodi");
        addOrderTextField.setMaxWidth(200);
        Label addOrderFeedback = new Label("feedback");

        // Add Order field center
        VBox addOrderField = new VBox();
        addOrderField.setSpacing(20);
        addOrderField.getChildren().addAll(addOrderTextField, addOrderbtn, addOrderFeedback);
        
         // Seek order field:
        Button seekOrderbtn = new Button("Etsi tilaus");
        seekOrderbtn.setDefaultButton(true);
        TextField seekOrderTextField = new TextField("");
        seekOrderTextField.setPromptText("Anna tilauskoodi");
        seekOrderTextField.setMaxWidth(200);
        Label seekOrderFeedback = new Label("feedback");

        // Seek Order field center
        VBox seekOrderField = new VBox();
        seekOrderField.setSpacing(20);
        seekOrderField.getChildren().addAll(seekOrderTextField, seekOrderbtn, seekOrderFeedback);
        
        // AddEventField:
        Button addEventbtn = new Button("Lisää työvaihe");
        addEventbtn.setDefaultButton(true);
        TextField addEventCodeTextField = new TextField("");
        addEventCodeTextField.setMaxWidth(200);
        addEventCodeTextField.setPromptText("Anna tilauskoodi");
        TextField addEventTextField = new TextField("");
        addEventTextField.setPromptText("Anna työvaihe");
        addEventTextField.setMaxWidth(200);
        TextField addEventDescTextField = new TextField("");
        addEventDescTextField.setPromptText("(Anna lisätietoja)");
        addEventDescTextField.setMaxWidth(200);
        Label addEventFeedback = new Label("feedback");

        // Add Event field center:
        VBox addEventField = new VBox();
        addEventField.setSpacing(20);
        addEventField.getChildren().addAll(addEventCodeTextField, addEventTextField, addEventDescTextField, addEventbtn, addEventFeedback);

        // Create workwiew:
        Label welcometext2 = new Label("Tuotannonohjaus DICIP");
        testBox.getChildren().add(welcometext2);
        welcometext2.setAlignment(Pos.CENTER);
        welcometext2.setFont(Font.font("Monospace", 20));
        testBox.setSpacing(20);
        testBox.setPadding(spaces);
        welcometext2.setAlignment(Pos.CENTER);
        workwindow.setTop(testBox);
        workwindow.setLeft(leftfield);
        testBox.setAlignment(Pos.CENTER);

        
        
        // Create begin wiew:
        mainwindow.setBottom(bottomfield);
        mainwindow.setRight(loginfield);
        mainwindow.setCenter(centerfield);
        mainwindow.setTop(welcomefield);
        
        
        Scene beginScene = new Scene(mainwindow, 900, 640);
        Scene workScene = new Scene(workwindow, 900, 640);
        win.setScene(beginScene);
        win.setTitle("DICIP");
        win.show();
        
        
        // Table for showing seeking results
  
        TableView table = new TableView();
        TableColumn timestampCol = new TableColumn("Aikaleima");
        TableColumn workphaseCol = new TableColumn("Työvaihe");
        TableColumn infoCol = new TableColumn("Lisätiedot");
        TableColumn usrCol = new TableColumn("Työntekijä");
        timestampCol.setMinWidth(100);
        workphaseCol.setMinWidth(100);
        infoCol.setMinWidth(100);
        usrCol.setMinWidth(100);
        table.getColumns().addAll(timestampCol, workphaseCol, infoCol, usrCol);
 
     
        
        
        
        // Create functions for buttons:
        // Check if inserted name is correct and then log in
        loginbtn.setOnAction(event -> {
                if (service.login(loginfieldtext.getText())) {
                    feedbacktext.setText("Käyttäjä löytyi");
                    feedbacktext.setTextFill(Color.GREEN);
                    win.setScene(workScene);
                    orderbtn.fire();
                    if (service.getUserStatus(loginfieldtext.getText())) {
                        adminbtn.setDisable(false);
                    }
                } else {
                    feedbacktext.setText("Käyttäjää ei löytynyt.");
                    feedbacktext.setTextFill(Color.RED);
                }
        });
        
        // Format the database if not formated before
        formatbtn.setOnAction(event -> {
                if (service.checkDatabase()) {
                    feedbacktext.setText("Tietokanta alustettu nyt");
                    feedbacktext.setTextFill(Color.GREEN);
                } else {
                    feedbacktext.setText("Alustus on jo tehty");
                    feedbacktext.setTextFill(Color.BLACK);
                }
        }); 
        
        // Show order wiew with buttons
        orderbtn.setOnAction(event -> {
            workwindow.setCenter(workField);
            workwindow.setRight(workFieldRight);
        });
        
        // Go back to the start wiew      
        backbtn.setOnAction(event -> {
            workwindow.setCenter(emptybox);
            win.setScene(beginScene);
            feedbacktext.setText("");
        });
        
        // Show admin wiew
        adminbtn.setOnAction(event -> {
            workwindow.setCenter(adminField);
            workwindow.setRight(adminFieldRight);
            addUserTextField.setText("");
            addUserFeedback.setText("");
            rb1.setSelected(true);
        });
        
        // Check if the username is already taken or too short, then add user
        addUserbtn.setOnAction(event -> {
            if (service.login(addUserTextField.getText())) {
                addUserFeedback.setText("Tunnus on jo olemassa, valitse toinen tunnus.");
                addUserFeedback.setTextFill(Color.RED);
            } else if (addUserTextField.getText().length() < 3) {
                addUserFeedback.setText("Tunnuksen tulee olla vähintään 3 merkkiä pitkä.");
                addUserFeedback.setTextFill(Color.RED);
            } else {
                Integer status = 0;
                if (group.getSelectedToggle().equals(rb1)) {
                    status = 0;
                } else if (group.getSelectedToggle().equals(rb2)) {
                    status = 1;
                }
                if (service.addUser(addUserTextField.getText(), status)) {
                    addUserFeedback.setText("Tunnus lisätty.");
                    addUserFeedback.setTextFill(Color.GREEN);
                } else {
                    addUserFeedback.setText("Tunnuksen luomisessa tapahtui virhe.");
                    addUserFeedback.setTextFill(Color.BLACK);

                }
            }
        });
        
        // Show create order wiew
        createOrder.setOnAction(event -> {
            workwindow.setCenter(addOrderField);
            addOrderTextField.setText("");
            addOrderFeedback.setText("");
            addOrderFeedback.setTextFill(Color.BLACK);
        });
        
        // Check if the given order code is alrady taken, then add order        
        addOrderbtn.setOnAction(event -> {
            if (service.orderExists(addOrderTextField.getText())) {
                addOrderFeedback.setText("Tilausnumero on jo käytössä.");
                addOrderFeedback.setTextFill(Color.RED);
            } else if (service.addOrder(addOrderTextField.getText())) {
                addOrderFeedback.setText("Tilaus lisätty.");
                addOrderFeedback.setTextFill(Color.GREEN);
            } else {
                addOrderFeedback.setText("Tilauksen lisäämien ei onnistunut.");
                addOrderFeedback.setTextFill(Color.RED);
            }
        });
       
        // Show order seeking wiew
        seekbtnCode.setOnAction(event -> {
            workwindow.setCenter(seekOrderField);
            seekOrderTextField.setText("");
            seekOrderFeedback.setText("");
            seekOrderFeedback.setTextFill(Color.BLACK);
        });
        
        // Check if order exists and then get the order info        
        seekOrderbtn.setOnAction(event -> {
             if (service.orderExists(seekOrderTextField.getText())) {
                seekOrderFeedback.setText("Tilaus löytyi.");
                service.getOrder(seekOrderTextField.getText());
                seekOrderFeedback.setTextFill(Color.GREEN);
                workwindow.setBottom(table);
            } else {
                seekOrderFeedback.setText("Tilausta ei löytynyt UI.");
                seekOrderFeedback.setTextFill(Color.RED);
            }
        });
        
        // Show workphase creating wiew
        createPhase.setOnAction(event -> {
            workwindow.setCenter(addEventField);
            addEventCodeTextField.setText("");
            addEventTextField.setText("");
            addEventDescTextField.setText("");
            addEventFeedback.setText("");
            addEventFeedback.setTextFill(Color.BLACK);
        });
        
        // Check if order exists and then add workphase
        addEventbtn.setOnAction(event -> {
            if (!service.orderExists(addEventCodeTextField.getText())) {
                addEventFeedback.setText("Tilausnumeroa ei löydy, ei voi lisätä työvaihetta. UI");
                addEventFeedback.setTextFill(Color.RED);
            } else if (service.addEvent(addEventTextField.getText(),addEventCodeTextField.getText(),addEventDescTextField.getText(), loginfieldtext.getText())) {
                addEventFeedback.setText("Työvaihe lisätty.UI");
                addEventFeedback.setTextFill(Color.GREEN);
            } else {
                addEventFeedback.setText("Työvaiheen lisäämien ei onnistunut. UI");
                addEventFeedback.setTextFill(Color.RED);
            }
        });
        
    }

}
