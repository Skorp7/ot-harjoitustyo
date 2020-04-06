package ui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

/**
 *
 * @author sakorpi
 */
import domain.WorkPhase;
import domain.Service;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
        VBox topBox = new VBox();
        Insets spaces = new Insets(20, 40, 40, 40);

        // Create login field:
        VBox loginfield = new VBox();
        Button loginbtn = new Button("Kirjaudu");
        loginbtn.setDefaultButton(true);
        Button formatbtn = new Button("Alusta tietokanta (ensimmäinen käynnistys)");
        TextField loginfieldtext = new TextField("Anna tunnus");
        Label logintext = new Label("Kirjaudu sisään:");
        Label feedbacktext = new Label("");
        loginfield.getChildren().addAll(logintext, loginfieldtext, loginbtn, feedbacktext);
        // loginfield.setPrefSize(200, 400);
        loginfield.setSpacing(10);
        loginfield.setPadding(spaces);

        // Create topic field:
        VBox welcomefield = new VBox();
        Label welcometext = new Label("Tuotannonohjaus DICIP");
        welcomefield.getChildren().add(welcometext);
        welcometext.setAlignment(Pos.CENTER);
        welcometext.setFont(Font.font("Monospace", 20));
        welcomefield.setSpacing(20);
        welcomefield.setAlignment(Pos.CENTER);
        welcomefield.autosize();

        welcometext.setPadding(spaces);

        // Create bottom field:
        HBox bottomfield = new HBox();
        bottomfield.getChildren().add(formatbtn);
        bottomfield.setAlignment(Pos.CENTER);
        bottomfield.setPadding(spaces);

        // Create center field:
        VBox centerfield = new VBox();
        centerfield.setPadding(spaces);
        Label centertext = new Label("Alustuksen jälkeen voi kirjautua\ntunnuksella 'admin', jolla \non kaikki käyttöoikeudet.");
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
        rb1.setToggleGroup(group);
        rb1.setSelected(true);
        RadioButton rb2 = new RadioButton("Työnjohtaja");
        rb2.setToggleGroup(group);

        // Empty field center
        VBox emptybox = new VBox();
        emptybox.setPrefSize(200, 400);
        emptybox.setSpacing(20);
        VBox emptybox2 = new VBox();
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
        adminField.setPadding(spaces);
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
        leftfield.setPadding(spaces);

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
        workFieldRight.setPadding(spaces);
        workFieldRight.getChildren().addAll(rightText, createOrder, createPhase, seekbtnCode, seekbtnDate);

        // AddOrderField:
        Button addOrderbtn = new Button("Lisää tilaus");
        addOrderbtn.setDefaultButton(true);
        TextField addOrderTextField = new TextField("");
        addOrderTextField.setPromptText("Luo tilauskoodi");
        addOrderTextField.setMaxWidth(200);
        Label addOrderFeedback = new Label("feedback");
        Button addOldOrderbtn = new Button("Kirjaa olemassa oleva tilaus uudelleen sisään");
        addOldOrderbtn.setVisible(false);

        // Add Order field center
        VBox addOrderField = new VBox();
        addOrderField.setSpacing(20);
        addOrderField.setPadding(spaces);
        addOrderField.getChildren().addAll(addOrderTextField, addOrderbtn, addOrderFeedback, addOldOrderbtn);

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
        seekOrderField.setPadding(spaces);
        seekOrderField.getChildren().addAll(seekOrderTextField, seekOrderbtn, seekOrderFeedback);
        
        Button seekOrderDatebtn = new Button("Etsi tilaus");
        seekOrderDatebtn.setDefaultButton(true);
        Label seekOrderDateTitle = new Label("Anna päivämäärä muodossa 'vvvv-kk-pp'");
        TextField seekOrderDateTextField = new TextField("");
        seekOrderDateTextField.setPromptText("Anna päivämäärä");
        seekOrderDateTextField.setMaxWidth(200);
        Label seekOrderDateFeedback = new Label("feedback");
                
        // Seek Order by date field center
        VBox seekOrderDateField = new VBox();
        seekOrderDateField.setSpacing(20);
        seekOrderDateField.setPadding(spaces);
        seekOrderDateField.getChildren().addAll(seekOrderDateTitle, seekOrderDateTextField, seekOrderDatebtn, seekOrderDateFeedback);

        // AddEventField:
        Button addEventbtn = new Button("Lisää työvaihe");
        addEventbtn.setDefaultButton(true);
        TextField addEventCodeTextField = new TextField("");
        addEventCodeTextField.setMaxWidth(200);
        addEventCodeTextField.setPromptText("Anna tilauskoodi");
        // HBox for selecting the workphase:
        HBox chooseWorkphase = new HBox();
        chooseWorkphase.setSpacing(20);
        chooseWorkphase.setAlignment(Pos.CENTER);
        TextField addEventTextField = new TextField("");
        addEventTextField.setPromptText("Anna työvaihe");
        addEventTextField.setMaxWidth(200);
        CheckBox logOutCheckBox = new CheckBox("LogOut");
        logOutCheckBox.setText("Uloskirjaus");
        CheckBox logAgainInCheckBox = new CheckBox("LogAgainIn");
        logAgainInCheckBox.setText("Sisäänkirjaus\n(vanha tilaus)");
        chooseWorkphase.getChildren().addAll(addEventTextField, logOutCheckBox, logAgainInCheckBox);

        TextField addEventDescTextField = new TextField("");
        addEventDescTextField.setPromptText("(Anna lisätietoja)");
        addEventDescTextField.setMaxWidth(200);

        // HBox for choose the courier when logout is selected
        HBox chooseCourier = new HBox();
        chooseCourier.setSpacing(20);

        final ToggleGroup groupCourier = new ToggleGroup();
        RadioButton cb1 = new RadioButton("Oma lähetti");
        cb1.setToggleGroup(groupCourier);
        cb1.setUserData("Oma lähetti");
        cb1.setSelected(true);
        RadioButton cb2 = new RadioButton("A to B");
        cb2.setUserData("A to B");
        cb2.setToggleGroup(groupCourier);
        RadioButton cb3 = new RadioButton("Bring");
        cb3.setUserData("Bring");
        cb3.setToggleGroup(groupCourier);
        RadioButton cb4 = new RadioButton("Posti");
        cb4.setUserData("Posti");
        cb4.setToggleGroup(groupCourier);
        chooseCourier.getChildren().addAll(cb1, cb2, cb3, cb4);
        chooseCourier.setDisable(true);
        Label addEventFeedback = new Label("feedback");

        // Add Event field center:
        VBox addEventField = new VBox();
        addEventField.setSpacing(20);
        addEventField.setPadding(spaces);
        addEventField.getChildren().addAll(addEventCodeTextField, chooseWorkphase, addEventDescTextField, chooseCourier, addEventbtn, addEventFeedback);

        // Create workwiew:
        Label welcometext2 = new Label("Tuotannonohjaus DICIP");
        topBox.getChildren().add(welcometext2);
        welcometext2.setAlignment(Pos.CENTER);
        welcometext2.setFont(Font.font("Monospace", 20));
        topBox.setSpacing(20);
        topBox.setPadding(spaces);
        welcometext2.setAlignment(Pos.CENTER);
        workwindow.setTop(topBox);
        workwindow.setLeft(leftfield);
        topBox.setAlignment(Pos.CENTER);

        // Create begin wiew:
        mainwindow.setBottom(bottomfield);
        mainwindow.setRight(loginfield);
        mainwindow.setCenter(centerfield);
        mainwindow.setTop(welcomefield);

        Scene beginScene = new Scene(mainwindow, 1000, 700);
        Scene workScene = new Scene(workwindow, 1000, 700);
        win.setScene(beginScene);
        win.setTitle("DICIP");
        win.show();

        // Table for showing seeking results
        TableView<WorkPhase> table = new TableView();
        TableColumn codeCol = new TableColumn("Tilauskoodi");
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        TableColumn timestampCol = new TableColumn("Aikaleima");
        timestampCol.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        TableColumn workphaseCol = new TableColumn("Työvaihe");
        workphaseCol.setCellValueFactory(new PropertyValueFactory<>("workphase"));
        TableColumn infoCol = new TableColumn("Lisätiedot");
        infoCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        TableColumn usrCol = new TableColumn("Työntekijä");
        usrCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        codeCol.setMinWidth(150);
        timestampCol.setMinWidth(150);
        workphaseCol.setMinWidth(150);
        infoCol.setMinWidth(200);
        usrCol.setMinWidth(100);
        table.getColumns().addAll(codeCol, timestampCol, workphaseCol, usrCol, infoCol);
        VBox tablebox = new VBox();
        tablebox.setPrefHeight(300);
        tablebox.setPadding(spaces);
        tablebox.getChildren().add(table);
// 
//        columnOne.setCellValueFactory(c -> new SimpleStringProperty(new String("123")));
//        columnTwo.setCellValueFactory(c -> new SimpleStringProperty(new String("456")));

        // table.getItems().addAll("Column one's data", "Column two's data");
        // Create functions for buttons:
        // Check if inserted name is correct and then log in
        loginbtn.setOnAction(event -> {
            if (service.getUser(loginfieldtext.getText()) != null) {
                feedbacktext.setText("Käyttäjä löytyi");
                feedbacktext.setTextFill(Color.GREEN);
                win.setScene(workScene);
                orderbtn.fire();
                if (service.getUser(loginfieldtext.getText()).getStatus() == 1) {
                    adminbtn.setDisable(false);
                } else {
                    adminbtn.setDisable(true);
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
            workwindow.setBottom(emptybox2);
        });

        // Go back to the start wiew      
        backbtn.setOnAction(event -> {
            workwindow.setCenter(emptybox);
            win.setScene(beginScene);
            workwindow.setBottom(emptybox2);
            feedbacktext.setText("");
        });

        // Show admin wiew
        adminbtn.setOnAction(event -> {
            workwindow.setCenter(adminField);
            workwindow.setRight(adminFieldRight);
            workwindow.setBottom(emptybox2);
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
            workwindow.setBottom(emptybox2);
            addOrderTextField.setText("");
            addOrderFeedback.setText("");
            addOrderFeedback.setTextFill(Color.BLACK);
        });

        // Check if the given order code is alrady taken, then add order        
        addOrderbtn.setOnAction(event -> {
            if (service.getOrder(addOrderTextField.getText()) != null) {
                addOrderFeedback.setText(" Tilausnumero on jo käytössä. \n Jos jo uloskirjattu tilaus on tulossa takaisin sisään,\n kirjaa se sisään tästä:");
                addOrderFeedback.setTextFill(Color.RED);
                addOldOrderbtn.setVisible(true);
            } else if (service.addOrder(addOrderTextField.getText(), loginfieldtext.getText())) {
                addOrderFeedback.setText("Tilaus lisätty.");
                addOrderFeedback.setTextFill(Color.GREEN);
            } else {
                addOrderFeedback.setText("Tilauksen lisäämien ei onnistunut.");
                addOrderFeedback.setTextFill(Color.RED);
            }
        });
        
        // When textfield is active when adding a new order name, hide the old order adding button
        addOrderTextField.setOnKeyTyped(event -> {
            addOldOrderbtn.setVisible(false);
            addOrderFeedback.setText("");
        });

        // Show order seeking (by code) wiew
        seekbtnCode.setOnAction(event -> {
            workwindow.setCenter(seekOrderField);
            workwindow.setBottom(emptybox2);
            seekOrderTextField.setText("");
            seekOrderFeedback.setText("");
            seekOrderFeedback.setTextFill(Color.BLACK);
        });

        // Show order seeking (by date) wiew
        seekbtnDate.setOnAction(event -> {
            workwindow.setCenter(seekOrderDateField);
            workwindow.setBottom(emptybox2);
            seekOrderDateTextField.setText("");
            seekOrderDateFeedback.setText("");
            seekOrderDateFeedback.setTextFill(Color.BLACK);
        });

        // Check if order exists and then get the order info        
        seekOrderbtn.setOnAction(event -> {
            if (service.getOrder(seekOrderTextField.getText()) != null) {
                seekOrderFeedback.setText("Tilaus koodilla '"+ seekOrderTextField.getText() + "' löytyi.");
                seekOrderFeedback.setTextFill(Color.GREEN);
                table.setItems(service.getOrderInfo(seekOrderTextField.getText()));
                codeCol.setVisible(false);
                workwindow.setBottom(tablebox);
            } else {
                seekOrderFeedback.setText("Tilausta ei löytynyt.");
                workwindow.setBottom(emptybox2);
                seekOrderFeedback.setTextFill(Color.RED);
            }
        });
        
        seekOrderDatebtn.setOnAction(event -> {
            if (seekOrderDateTextField.equals("")) {
                seekOrderDateFeedback.setText("Päivämäärän muoto virheellinen.");
                seekOrderDateFeedback.setTextFill(Color.RED);
            } else {
                if (service.getOrderInfoByDate(seekOrderDateTextField.getText()).isEmpty()) {
                    seekOrderDateFeedback.setText("Tällä päivämäärällä ei löytynyt yhtään käsiteltyä tilausta.");
                    seekOrderDateFeedback.setTextFill(Color.RED);
                } else {
                    table.setItems(service.getOrderInfoByDate(seekOrderDateTextField.getText()));
                    codeCol.setVisible(true);
                    workwindow.setBottom(tablebox);
                    seekOrderDateFeedback.setText("Käsiteltyjä tilauksia löytyi.");
                    seekOrderDateFeedback.setTextFill(Color.GREEN);
                }
            }
        });

        // Show workphase creating wiew
        createPhase.setOnAction(event -> {
            workwindow.setCenter(addEventField);
            workwindow.setBottom(emptybox2);
            logOutCheckBox.setSelected(false);
            logAgainInCheckBox.setSelected(false);
            chooseCourier.setDisable(true);
            addEventCodeTextField.setText("");
            addEventTextField.setText("");
            addEventDescTextField.setText("");
            addEventFeedback.setText("");
            addEventFeedback.setTextFill(Color.BLACK);
        });
        
        // Show workphase creation wiew when coming from 'Add order' wiew:
        addOldOrderbtn.setOnAction(event -> {
            addOldOrderbtn.setVisible(false);
            workwindow.setCenter(addEventField);
            workwindow.setBottom(emptybox2);
            logOutCheckBox.setSelected(false);
            logAgainInCheckBox.fire();
            chooseCourier.setDisable(true);
            addEventCodeTextField.setText(addOrderTextField.getText());
            addEventDescTextField.setText("");
            addEventFeedback.setText("");
            addEventFeedback.setTextFill(Color.BLACK);
        });
        
        
        logOutCheckBox.setOnAction(event -> {
            if (logOutCheckBox.isSelected()) {
                addEventTextField.setText("Uloskirjaus");
                chooseCourier.setDisable(false);
                logAgainInCheckBox.setSelected(false);
            } else {
                addEventTextField.setText("");
                chooseCourier.setDisable(true);
            }
        });
        
        logAgainInCheckBox.setOnAction(event -> {
            if (logAgainInCheckBox.isSelected()) {
                addEventTextField.setText("Uusi sisäänkirjaus");
                chooseCourier.setDisable(true);
                logOutCheckBox.setSelected(false);
            } else {
                addEventTextField.setText("");
                chooseCourier.setDisable(true);
            }
        });
        
        // When textfields are active, make changes to the fields and checkboxes
        addEventTextField.setOnKeyTyped(event -> {
            logOutCheckBox.setSelected(false);
            logAgainInCheckBox.setSelected(false);
            chooseCourier.setDisable(true);
            addEventFeedback.setText("");
        });
        
        addEventCodeTextField.setOnKeyTyped(event -> addEventFeedback.setText(""));
        addEventDescTextField.setOnKeyTyped(event -> addEventFeedback.setText(""));

        // Check if order exists and then add workphase.
        addEventbtn.setOnAction(event -> {
            String description = addEventDescTextField.getText();
            if (logOutCheckBox.isSelected()) {
                description = groupCourier.getSelectedToggle().getUserData().toString();
            }
            if (service.getOrder(addEventCodeTextField.getText()) == null) {
                addEventFeedback.setText("Tilausnumeroa ei löydy, ei voi lisätä työvaihetta.");
                addEventFeedback.setTextFill(Color.RED);
            } else if (service.addEvent(addEventTextField.getText(), addEventCodeTextField.getText(), description, loginfieldtext.getText())) {
                addEventFeedback.setText("Työvaihe lisätty tilauskoodille '" + addEventCodeTextField.getText() + "'.");
                addEventFeedback.setTextFill(Color.GREEN);
            } else {
                addEventFeedback.setText("Työvaiheen lisäämien ei onnistunut.");
                addEventFeedback.setTextFill(Color.RED);
            }
        });
    }

}
