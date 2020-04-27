package dicip.ui;

import dicip.database.DataSql;
import dicip.domain.WorkPhase;
import dicip.domain.Service;
import dicip.domain.User;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.util.StringConverter;

/**
 *
 * @author sakorpi
 */
/**
 *
 * Class for graphical user interface
 */
public class App extends Application {

    @Override
    public void start(Stage win) throws SQLException {

        // Create app service:
        Service service = new Service(new DataSql("jdbc:sqlite:datafile.db"));

        // Create window components
        BorderPane mainwindow = new BorderPane();
        BorderPane workwindow = new BorderPane();
        VBox topBox = new VBox();
        Insets spaces = new Insets(20, 40, 40, 40);

        // Create login field:
        VBox loginfield = new VBox();
        Button loginBtn = new Button("Kirjaudu");
        loginBtn.setDefaultButton(true);
        TextField loginfieldtext = new TextField("Anna tunnus");
        Label loginInfoText = new Label("Ensimmäisen käynnistyksen jälkeen\nvoi kirjautua tunnuksella 'admin',\njolla on kaikki käyttöoikeudet.");
        Label logintext = new Label("Kirjaudu sisään:");
        Label feedbacktext = new Label("");
        loginfield.getChildren().addAll(logintext, loginfieldtext, loginBtn, feedbacktext, loginInfoText);
        loginfield.setSpacing(10);
        loginfield.setPadding(spaces);

        // Create topic field:
        VBox welcomefield = new VBox();
        welcomefield.getStyleClass().add("styles/style.css");
        Label welcometext = new Label("Tuotannonohjaus DICIP");
        welcometext.getStyleClass().add("welcometext");
        welcomefield.getChildren().add(welcometext);
        welcometext.setAlignment(Pos.CENTER);
        welcometext.setFont(Font.font("Monospace", 20));
        welcomefield.setSpacing(20);
        welcomefield.setAlignment(Pos.CENTER);
        welcomefield.autosize();

        welcometext.setPadding(spaces);

        // Create bottom field:
        HBox bottomfield = new HBox();
        bottomfield.setAlignment(Pos.CENTER);
        bottomfield.setPadding(spaces);

        // Create center field:
        VBox centerField = new VBox();
        centerField.setAlignment(Pos.CENTER_RIGHT);
        Image beginImage = null;
        try {
            Image image = new Image(getClass().getResourceAsStream("/styles/kappyra2.png"));
            beginImage = image;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ImageView imView = new ImageView(beginImage);
        imView.setFitHeight(400);
        imView.setFitWidth(600);
        centerField.setPadding(spaces);
        centerField.getChildren().addAll(imView);
        centerField.setPrefSize(200, 500);
        centerField.setMinHeight(400);
        centerField.setSpacing(20);

        // Create adminstrative fields - user management:
        // Controlfield:
        HBox adminFieldTop = new HBox();
        adminFieldTop.setSpacing(20);
        Button addUserLinkBtn = new Button("Lisää käyttäjä");
        Button changeStatusLinkBtn = new Button("Muuta käyttäjäroolia");
        Button removeUserLinkBtn = new Button("Poista käyttäjä");
        adminFieldTop.getChildren().addAll(addUserLinkBtn, changeStatusLinkBtn, removeUserLinkBtn);

        // Modify User Field:
        TextField modUserTextField = new TextField("");
        modUserTextField.setPromptText("Anna tunnus");
        modUserTextField.setMaxWidth(200);
        Label modUserFeedback = new Label("feedback");

        // Radio buttons for status choise:
        final ToggleGroup group = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Työntekjä");
        rb1.setToggleGroup(group);
        rb1.setSelected(true);
        RadioButton rb2 = new RadioButton("Työnjohtaja");
        rb2.setToggleGroup(group);

        // User management button field:
        Button addUserBtn = new Button("Lisää käyttäjä");
        Button removeUserBtn = new Button("Poista käyttäjä");
        Button changeStatusBtn = new Button("Muuta käyttäjärooli");
        VBox modUserBtnField = new VBox();
        modUserBtnField.setSpacing(-25);
        modUserBtnField.getChildren().addAll(addUserBtn, changeStatusBtn, removeUserBtn);

        // Admin field center - user management
        VBox adminField = new VBox();
        adminField.getStylesheets().add("styles/style_admin.css");
        adminField.setSpacing(20);
        adminField.setPadding(spaces);
        adminField.getChildren().addAll(adminFieldTop, modUserTextField, rb1, rb2, modUserBtnField, modUserFeedback);

        // Empty field center
        VBox emptybox = new VBox();
        emptybox.setPrefSize(200, 400);
        emptybox.setSpacing(20);
        VBox emptybox2 = new VBox();
        emptybox.setPrefSize(200, 400);
        emptybox.setSpacing(20);

        // Admin field right
        Label righttext = new Label("Plaaplaaplaapalapa");
        Button modUserLinkBtn = new Button("Käyttäjähallinta");
        Button chartLinkBtn = new Button("Tilastot");
        // chartLinkBtn.getStyleClass().add("button");
        Button settingsLinkBtn = new Button("Asetukset");
        VBox adminFieldRight = new VBox();
        adminFieldRight.getStylesheets().add("styles/style_admin.css");
        adminFieldRight.setPrefWidth(300);
        adminFieldRight.setSpacing(20);
        adminFieldRight.setPadding(spaces);
        adminFieldRight.getChildren().addAll(righttext, modUserLinkBtn, chartLinkBtn, settingsLinkBtn);

        // Settings - controls
        Button removeAllDataBtn = new Button("Tyhjennä tietokanta");

        // Admin field center - settings
        VBox adminFieldSettings = new VBox();
        adminFieldSettings.getStylesheets().add("styles/style_admin.css");
        adminFieldSettings.setSpacing(20);
        adminFieldSettings.setPadding(spaces);
        adminFieldSettings.getChildren().addAll(removeAllDataBtn);

        // Admin field center - chart
        VBox adminFieldChart = new VBox();
//        Label chartTopic = new Label("Tilastoteksti");
        adminFieldChart.setSpacing(20);
        adminFieldChart.setPadding(spaces);

        // Create chart axels
        LocalDate today = LocalDate.now();
        NumberAxis xAxis = new NumberAxis(today.minusDays(30).getDayOfYear(), today.getDayOfYear(), 30);
        // Converter to get numbers to string on xAxis
        StringConverter<Number> formatNumToString = new StringConverter<Number>() {
            @Override
            public String toString(Number t) {
                int i = t.intValue();
                if (i == today.minusDays(30).getDayOfYear()) {
                    return "30pv sitten";
                } else {
                    return "Tänään";
                }
            }

            @Override
            public Number fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };

        xAxis.setTickLabelFormatter(formatNumToString);
        xAxis.setMinorTickCount(30);
        xAxis.setPickOnBounds(true);

        NumberAxis yAxis = new NumberAxis("Tilaukset", 0, 20, 5);

        // Create the chart and insert axises
        LineChart<Number, Number> orderChart30Days = new LineChart<>(xAxis, yAxis);
        orderChart30Days.setCreateSymbols(false);
        orderChart30Days.setTitle("Uudet tilaukset, viimeiset 30 päivää");
        orderChart30Days.setLegendVisible(false);
        orderChart30Days.setVerticalGridLinesVisible(false);

        XYChart.Series orderData = new XYChart.Series();
        // Load orderData when clicked the Button
        orderChart30Days.getData().add(orderData);
        adminFieldChart.getChildren().addAll(orderChart30Days);

        // Create left field in workwindow:
        VBox leftfield = new VBox();
        Label lefttext = new Label("");
        Button logOutBtn = new Button("Kirjaudu ulos");
        logOutBtn.setCancelButton(true);
        Button adminBtn = new Button("Hallinta");
        adminBtn.setDisable(true);
        Button orderBtn = new Button("Tuotanto");
        leftfield.getChildren().addAll(lefttext, adminBtn, orderBtn, logOutBtn);
        leftfield.setPrefSize(200, 400);
        leftfield.setSpacing(20);
        leftfield.setPadding(spaces);

        // Create workfield center
        VBox workField = new VBox();
        workField.setSpacing(10);
        workField.setPrefWidth(400);
        Label workLabel = new Label("TÄHÄN TULEE TEKSTIÄ");

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

        HBox chooseDateBox = new HBox();
        chooseDateBox.setSpacing(20);
        chooseDateBox.setAlignment(Pos.CENTER_LEFT);
        Button chooseTodaybtn = new Button("Tänään");
        Button seekOrderDatebtn = new Button("Etsi tilaukset");
        seekOrderDatebtn.setDefaultButton(true);
        Label seekOrderDateTitle = new Label("Anna päivämäärä muodossa 'vvvv-kk-pp'");
        TextField seekOrderDateTextField = new TextField("");
        seekOrderDateTextField.setPromptText("Anna päivämäärä");
        seekOrderDateTextField.setMaxWidth(200);
        Label seekOrderDateFeedback = new Label("feedback");
        chooseDateBox.getChildren().addAll(seekOrderDateTextField, chooseTodaybtn);

        // Seek Order by date field center
        VBox seekOrderDateField = new VBox();
        seekOrderDateField.setSpacing(20);
        seekOrderDateField.setPadding(spaces);
        seekOrderDateField.getChildren().addAll(seekOrderDateTitle, chooseDateBox, seekOrderDatebtn, seekOrderDateFeedback);

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
        welcometext2.getStyleClass().add("welcometext");
        topBox.getChildren().add(welcometext2);
        topBox.getStyleClass().add("styles/style.css");
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
        mainwindow.setCenter(centerField);
        mainwindow.setTop(welcomefield);

        Scene beginScene = new Scene(mainwindow, 1000, 700);
        beginScene.getStylesheets().add("styles/style.css");
        Scene workScene = new Scene(workwindow, 1000, 700);
        workScene.getStylesheets().add("styles/style.css");
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

        //FUNCTIONS
        // Create functions for buttons:
        // Check if inserted name is correct and then log in
        loginBtn.setOnAction(event -> {
            if (service.login(loginfieldtext.getText())) {
                feedbacktext.setText("Käyttäjä löytyi");
                feedbacktext.setTextFill(Color.GREEN);
                if (service.getLoggedInUser().getStatus() == 1) {
                    adminBtn.setDisable(false);
                } else {
                    adminBtn.setDisable(true);
                }
                lefttext.setText("Kirjautuneena:\n" + service.getLoggedInUser().getName());
                win.setScene(workScene);
                orderBtn.fire();
                orderBtn.requestFocus();
            } else {
                feedbacktext.setText("Käyttäjää ei löytynyt.");
                feedbacktext.setTextFill(Color.RED);
            }
        });

        // Show order view with button panel
        orderBtn.setOnAction(event -> {
            workwindow.setCenter(workField);
            workwindow.setRight(workFieldRight);
            workwindow.setBottom(emptybox2);
        });

        // Go back to the begin view      
        logOutBtn.setOnAction(event -> {
            service.logOut();
            win.setScene(beginScene);
            loginfieldtext.setText("Anna tunnus");
            feedbacktext.setText("");
        });

        // Show admin view
        adminBtn.setOnAction(event -> {
            workwindow.setRight(adminFieldRight);
            workwindow.setBottom(emptybox2);
            chartLinkBtn.fire();
            chartLinkBtn.requestFocus();
        });

        // Admin view: Add user: Check if the username is already taken or too short, then add user
        addUserBtn.setOnAction(event -> {
            if (service.getUser(modUserTextField.getText()) != null) {
                modUserFeedback.setText("Tunnus on jo olemassa, valitse toinen tunnus.");
                modUserFeedback.setTextFill(Color.RED);
            } else if (modUserTextField.getText().length() < 3) {
                modUserFeedback.setText("Tunnuksen tulee olla vähintään 3 merkkiä pitkä.");
                modUserFeedback.setTextFill(Color.RED);
            } else {
                Integer status = 0;
                if (group.getSelectedToggle().equals(rb1)) {
                    status = 0;
                } else if (group.getSelectedToggle().equals(rb2)) {
                    status = 1;
                }
                if (service.addUser(modUserTextField.getText(), status)) {
                    modUserFeedback.setText("Käyttäjä lisätty.");
                    modUserFeedback.setTextFill(Color.GREEN);
                } else {
                    modUserFeedback.setText("Tunnuksen luomisessa tapahtui virhe.");
                    modUserFeedback.setTextFill(Color.BLACK);
                }
            }
        });

        // Admin view: Right button field
        // Load data to chart and show the chart:
        chartLinkBtn.setOnAction(event -> {
            workwindow.setCenter(adminFieldChart);
            orderData.getData().clear();
            service.getOrderAmount30Days().entrySet().stream().forEach(pari -> {
                orderData.getData().add(new XYChart.Data(pari.getKey().getDayOfYear(), pari.getValue()));
            });
        });

        // Show add user field:
        modUserLinkBtn.setOnAction(event -> {
            workwindow.setCenter(adminField);
            modUserTextField.setText("");
            modUserFeedback.setText("");
            addUserBtn.setVisible(true);
            removeUserBtn.setVisible(false);
            changeStatusBtn.setVisible(false);
            addUserLinkBtn.requestFocus();
            rb1.setVisible(true);
            rb2.setVisible(true);
            rb1.setSelected(true);
        });

        // Show settings field:
        settingsLinkBtn.setOnAction(event -> {
            workwindow.setCenter(adminFieldSettings);
        });

        // Settings field Button functions:
        // Remove data from database if user chooses "OK" in the warningbox. Then log out and format database.
        removeAllDataBtn.setOnAction(event -> {
            if (this.removeAllDataIsClear()) {
                service.removeAllDataFromDatabase();
                logOutBtn.fire();
                service.checkDatabase();
            }
        });

        // User management button field:
        addUserLinkBtn.setOnAction(event -> {
            modUserTextField.setText("");
            modUserFeedback.setText("");
            rb1.setSelected(true);
            rb1.setVisible(true);
            rb2.setVisible(true);
            removeUserBtn.setVisible(false);
            changeStatusBtn.setVisible(false);
            addUserBtn.setVisible(true);
        });

        removeUserLinkBtn.setOnAction(event -> {
            rb1.setVisible(false);
            rb2.setVisible(false);
            removeUserBtn.setVisible(true);
            changeStatusBtn.setVisible(false);
            addUserBtn.setVisible(false);
        });

        changeStatusLinkBtn.setOnAction(event -> {
            rb1.setVisible(true);
            rb2.setVisible(true);
            removeUserBtn.setVisible(false);
            changeStatusBtn.setVisible(true);
            addUserBtn.setVisible(false);
        });

        // First check if user exists, then check if it has log in rights, then remove user or login rights
        removeUserBtn.setOnAction(event -> {
            if (service.getUser(modUserTextField.getText()) != null) {
                User user = service.getUser(modUserTextField.getText());
                if (user.getStatus() == 99) {
                    modUserFeedback.setText("Käyttäjän oikeudet on jo poistettu.");
                    modUserFeedback.setTextFill(Color.BLACK);
                } else {
                    if (removeUserIsClear(user) && service.removeUser(modUserTextField.getText())) {
                        modUserFeedback.setText("Poistettu.");
                        modUserFeedback.setTextFill(Color.GREEN);
                        if (user.equals(service.getLoggedInUser())) {
                            logOutBtn.fire();
                        }
                    } else {
                        modUserFeedback.setText("Käyttäjää ei poistettu");
                        modUserFeedback.setTextFill(Color.BLACK);
                    }
                }
            } else {
                modUserFeedback.setText("Käyttäjää ei löydy.");
                modUserFeedback.setTextFill(Color.RED);
            }
        });

        changeStatusBtn.setOnAction(event -> {
            Integer status = 0;
            if (group.getSelectedToggle().equals(rb1)) {
                status = 0;
            } else if (group.getSelectedToggle().equals(rb2)) {
                status = 1;
            }
            if (service.getUser(modUserTextField.getText()) != null) {
                if (changeStatusIsClear(service.getUser(modUserTextField.getText()), status)) {
                    service.changeUserStatus(modUserTextField.getText(), status);
                    modUserFeedback.setText("Käyttäjärooli vaihdettu.");
                    modUserFeedback.setTextFill(Color.GREEN);
                } else {
                    modUserFeedback.setText("Käyttäjäroolia ei vaihdettu.");
                    modUserFeedback.setTextFill(Color.BLACK);
                }
            } else {
                modUserFeedback.setText("Käyttäjää ei löydy.");
                modUserFeedback.setTextFill(Color.RED);
            }
        });

        // Show create order wiew
        createOrder.setOnAction(event -> {
            workwindow.setCenter(addOrderField);
            workwindow.setBottom(emptybox2);
            addOldOrderbtn.setVisible(false);
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
            } else if (service.addOrder(addOrderTextField.getText(), service.getLoggedInUser())) {
                addOrderFeedback.setText("Tilaus lisätty.");
                addOrderFeedback.setTextFill(Color.GREEN);
            } else {
                addOrderFeedback.setText("Tilauksen lisäämien ei onnistunut.");
                addOrderFeedback.setTextFill(Color.RED);
            }
        });

        // When textfield is active when adding a new order name, hide the 'old order adding' button
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
                seekOrderFeedback.setText("Tilaus koodilla '" + seekOrderTextField.getText() + "' löytyi.");
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

        // Get the date of today to the textfield
        chooseTodaybtn.setOnAction(event -> {
            LocalDate date = LocalDate.now();
            seekOrderDateTextField.setText(date.toString());
        });

        // Check if the date is in the right form and then seek the events of that day
        seekOrderDatebtn.setOnAction(event -> {
            if (!seekOrderDateTextField.getText().matches("((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])")) {
                seekOrderDateFeedback.setText("Päivämäärän muoto virheellinen.");
                seekOrderDateFeedback.setTextFill(Color.RED);
            } else {
                if (service.getOrderInfoByDate(seekOrderDateTextField.getText()).isEmpty()) {
                    seekOrderDateFeedback.setText("Tällä päivämäärällä ei löytynyt yhtään käsiteltyä tilausta.");
                    workwindow.setBottom(emptybox2);
                    seekOrderDateFeedback.setTextFill(Color.BLACK);
                } else {
                    table.setItems(service.getOrderInfoByDateGrouped(seekOrderDateTextField.getText()));
                    codeCol.setVisible(true);
                    workwindow.setBottom(tablebox);
                    seekOrderDateFeedback.setText("Käsiteltyjä tilauksia löytyi.\nNäytetään viimeisimmät työvaiheet.\nKlikkaa riviä (pitkään) nähdäksesi tilauksen koko seuranta.");
                    seekOrderDateFeedback.setTextFill(Color.GREEN);
                }
            }
        });

        // Show the history of clicked order and return when release
        // Only if Order code column is visible a.k.a we are in "seek by date" field
        table.setOnMousePressed(event -> {
            WorkPhase selectedWP = table.getSelectionModel().getSelectedItem();
            if (selectedWP != null && codeCol.isVisible()) {
                table.setItems(service.getOrderInfo(selectedWP.getCode()));
                seekOrderDateFeedback.setText("Näytetään tilauksen '" + selectedWP.getCode() + "' seuranta.");
            }
        });
        table.setOnMouseReleased(event -> {
            if (codeCol.isVisible()) {
                seekOrderDatebtn.fire();
            }
        });

        // Show workphase creating wiew
        createPhase.setOnAction(event -> {
            workwindow.setCenter(addEventField);
            workwindow.setBottom(emptybox2);
            logOutCheckBox.setSelected(false);
            logAgainInCheckBox.setSelected(false);
            chooseCourier.setDisable(true);
            addEventDescTextField.setDisable(false);
            addEventCodeTextField.setText("");
            addEventTextField.setText("");
            addEventDescTextField.setText("");
            addEventFeedback.setText("");
            addEventFeedback.setTextFill(Color.BLACK);
        });

        // Show workphase creation wiew when coming from 'Add order' wiew:
        addOldOrderbtn.setOnAction(event -> {
            workwindow.setCenter(addEventField);
            workwindow.setBottom(emptybox2);
            logOutCheckBox.setSelected(false);
            logAgainInCheckBox.setSelected(false);
            logAgainInCheckBox.fire();
            chooseCourier.setDisable(true);
            addEventCodeTextField.setText(addOrderTextField.getText());
            addEventDescTextField.setText("");
            addEventFeedback.setText("");
            addEventFeedback.setTextFill(Color.BLACK);
        });

        // Add event: show the courier seletion radio buttons if "uloskirjaus" is selected
        logOutCheckBox.setOnAction(event -> {
            if (logOutCheckBox.isSelected()) {
                addEventTextField.setText("Uloskirjaus");
                chooseCourier.setDisable(false);
                logAgainInCheckBox.setSelected(false);
                addEventDescTextField.setDisable(true);
            } else {
                addEventTextField.setText("");
                chooseCourier.setDisable(true);
                addEventDescTextField.setDisable(false);
            }
        });

        logAgainInCheckBox.setOnAction(event -> {
            if (logAgainInCheckBox.isSelected()) {
                addEventTextField.setText("Uusi sisäänkirjaus");
                chooseCourier.setDisable(true);
                logOutCheckBox.setSelected(false);
                addEventDescTextField.setDisable(false);
            } else {
                addEventTextField.setText("");
                chooseCourier.setDisable(true);
            }
        });

        // When textfields are active, make changes to the fields and checkboxes
        addEventTextField.setOnKeyTyped(event -> {
            logOutCheckBox.setSelected(false);
            logAgainInCheckBox.setSelected(false);
            addEventDescTextField.setDisable(false);
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
            } else if (service.addEvent(addEventTextField.getText(), addEventCodeTextField.getText(), description, service.getLoggedInUser())) {
                addEventFeedback.setText("Työvaihe lisätty tilauskoodille '" + addEventCodeTextField.getText() + "'.");
                addEventFeedback.setTextFill(Color.GREEN);
            } else {
                addEventFeedback.setText("Työvaiheen lisäämien ei onnistunut.");
                addEventFeedback.setTextFill(Color.RED);
            }
        });

    }

    private boolean removeAllDataIsClear() {
        Boolean clear = false;

        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Tietokannan tyhjennys");
        alert.setHeaderText("Oletko varma että haluat tyhjentää koko tietokannan?");
        alert.setContentText("Kaikki tieto poistetaan tietokannasta. \nKaikki käyttäjät, tilaukset ja työvaiheet poistetaan.");

        ButtonType cancelButtonType = new ButtonType("Peruuta", ButtonData.CANCEL_CLOSE);
        alert.getDialogPane().getButtonTypes().add(cancelButtonType);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            clear = true;
        }
        return clear;
    }

    private boolean changeStatusIsClear(User user, int status) {
        Boolean clear = false;
        String wantedStatus = "";
        String currentStatus = "";
        if (status == 0) {
            wantedStatus = "Työntekijä";
        } else if (status == 1) {
            wantedStatus = "Työnjohtaja";
        }
        if (user.getStatus() == 0) {
            currentStatus = "Työntekijä";
        } else if (user.getStatus() == 1) {
            currentStatus = "Työnjohtaja";
        } else if (user.getStatus() == 99) {
            currentStatus = "Oikeudet poistettu";
        }

        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Käyttäjäroolin vaihto");
        alert.setHeaderText("Oletko varma että haluat vaihtaa käyttäjän '" + user.getName() + "' käyttäjäroolin?");
        alert.setContentText("Nykyinen rooli on '" + currentStatus + "'." + "\n"
                + "Olet asettamassa käyttäjän '" + user.getName() + "' rooliksi '" + wantedStatus + "'.");

        ButtonType cancelButtonType = new ButtonType("Peruuta", ButtonData.CANCEL_CLOSE);
        alert.getDialogPane().getButtonTypes().add(cancelButtonType);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            clear = true;
        }
        return clear;
    }

    private boolean removeUserIsClear(User user) {
        Boolean clear = false;

        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Käyttäjän poistaminen");
        alert.setHeaderText("Oletko varma että haluat poistaa käyttäjän '" + user.getName() + "'.");
        alert.setContentText("Jos käyttäjään liittyy tilauksia tai tapahtumia, \n"
                + "käyttäjältä poistetaan vain sisäänkirjautumisoikeus.\n"
                + "Sen saa takaisin muuttamalla käyttäjäroolia.\n"
                + "Muussa tapauksessa koko käyttäjä poistetaan tietokannasta.\n"
                + "Kaikkia työnjohtajan oikeuksilla olevia käyttäjiä ei voi poistaa.");

        ButtonType cancelButtonType = new ButtonType("Peruuta", ButtonData.CANCEL_CLOSE);
        alert.getDialogPane().getButtonTypes().add(cancelButtonType);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            clear = true;
        }
        return clear;
    }

}
