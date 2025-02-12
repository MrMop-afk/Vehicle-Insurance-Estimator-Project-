package com.example.vehicleinsuranceestimator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class HelloApplication extends Application {
    Scene mainScene, insertScene, finalScene;
    int maritalStatus, isSuspended,rentalOffer = 0;
    double estimate = 0;
    String coverageLength,fullOrPartial,maleOrFemale = "";
    @Override
    public void start(Stage window) throws IOException {

        //main scene
        Label messageLabel = new Label("Get an Insurance Estimate Faster than a Coffee");
        messageLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,15));
        messageLabel.setAlignment(Pos.CENTER);
        messageLabel.setPadding(new Insets(0,50,0,0));
        //Start Button
        Button startButton = new Button("Start");
        startButton.setAlignment(Pos.CENTER);
        startButton.setMaxHeight(20);
        startButton.setMaxWidth(100);
        //rootPane for mainScene
        VBox rootPane = new VBox(20);
        rootPane.getChildren().addAll(messageLabel,startButton);
        rootPane.setAlignment(Pos.CENTER);


        //insertScene
        //Left Pane
        // Personal Info AKA Insurer table
        Label personalInfo = new Label("Personal Information");
        //License number
        TextField LicenseNumber = new TextField();
        LicenseNumber.setPromptText("License Number");
        //First Name
        TextField Fname = new TextField();
        Fname.setPromptText("First Name:");
        //Last Name
        TextField Lname = new TextField();
        Lname.setPromptText("Last Name:");
        //gender toggle group
        RadioButton Male = new RadioButton("Male");
        RadioButton Female = new RadioButton("Female");
        ToggleGroup gender = new ToggleGroup();
        Male.setToggleGroup(gender);
        Female.setToggleGroup(gender);
        //Age
        TextField age = new TextField();
        age.setPromptText("Age:"); //INT CHECK
        //marital Status toggle group
        ToggleGroup MaritalStatus = new ToggleGroup();
        RadioButton Married = new RadioButton("Married");
        RadioButton Single = new RadioButton("Single");
        Married.setToggleGroup(MaritalStatus);
        Single.setToggleGroup(MaritalStatus);

        //Location
        Label location = new Label("Location Information");
        TextField city = new TextField();
        city.setPromptText("City:");
        TextField province = new TextField();
        province.setPromptText("Province:");
        TextField country = new TextField();
        country.setPromptText("Country:");
        //Insurer Info Pane
        VBox insurerInfo = new VBox(20);
        insurerInfo.getChildren().addAll(personalInfo,LicenseNumber,Fname,Lname,Male,Female,age,Married,Single,location,city,province,country);
        insurerInfo.setMaxWidth(250);

        //Vehicle Information
        Label vehicle = new Label("Vehicle Information");
        //make
        TextField  make = new TextField();
        make.setPromptText("Make:");
        make.setMaxWidth(100);
        //Mileage
        TextField mileage = new TextField();
        mileage.setPromptText("Mileage:"); // INT CHECK
        mileage.setMaxWidth(100);
        //year
        TextField year = new TextField();
        year.setPromptText("Year:");
        year.setMaxWidth(100);
        // # of Accidents
        TextField numOfAccidents = new TextField();
        numOfAccidents.setPromptText("# of Accidents");
        numOfAccidents.setMaxWidth(100);
        // # of tickets
        TextField numOfTickets = new TextField();
        numOfTickets.setPromptText("# of Tickets");
        numOfTickets.setMaxWidth(100);
        //suspended toggle group
        ToggleGroup licenseSuspended = new ToggleGroup();
        RadioButton suspended = new RadioButton("Suspended");
        RadioButton notSuspended = new RadioButton("Not Suspended");
        suspended.setToggleGroup(licenseSuspended);
        notSuspended.setToggleGroup(licenseSuspended);
        //Vehicle info pane
        VBox vehicleInfo = new VBox(20);
        vehicleInfo.getChildren().addAll(vehicle,make,mileage,year,numOfAccidents,numOfTickets,suspended,notSuspended);

        //Coverage info
        Label coverage = new Label("Coverage Information");
        //months planned toggle group
        RadioButton fourMonths = new RadioButton("4 Months");
        RadioButton twelveMonths = new RadioButton("12 Months");
        ToggleGroup coverage_length = new ToggleGroup();
        fourMonths.setToggleGroup(coverage_length);
        twelveMonths.setToggleGroup(coverage_length);

        //months paid
        TextField monthsPaid = new TextField();
        monthsPaid.setPromptText("Months Paid:");
        //coverage type full or partial
        ToggleGroup coverageType = new ToggleGroup();
        RadioButton fullCoverage  = new RadioButton("Full Coverage");
        RadioButton partialCoverage = new RadioButton("Partial Coverage");
        fullCoverage.setToggleGroup(coverageType);
        partialCoverage.setToggleGroup(coverageType);
        //rental offer or not
        ToggleGroup rental_offer  = new ToggleGroup();
        RadioButton yesRental = new RadioButton("Rental");
        RadioButton noRental = new RadioButton("No Rental");
        yesRental.setToggleGroup(rental_offer);
        noRental.setToggleGroup(rental_offer);
        // coverage pane
        VBox coverageInfo = new VBox(20);
        coverageInfo.getChildren().addAll(coverage,fourMonths,twelveMonths,monthsPaid,fullCoverage,partialCoverage,yesRental,noRental);
        // put the 3 together
        //center pane
        HBox vehicleAndperosnal = new HBox(50);
        vehicleAndperosnal.getChildren().addAll(insurerInfo,vehicleInfo,coverageInfo);
        vehicleAndperosnal.setAlignment(Pos.TOP_CENTER);

        //submit button
        Button submitButton = new Button("Estimate");
        submitButton.setMaxWidth(75);
        submitButton.setMaxHeight(30);
        VBox submtBtn = new VBox(20);
        submtBtn.getChildren().addAll(submitButton);
        submtBtn.setAlignment(Pos.TOP_CENTER);
        submtBtn.setMinHeight(150);

        //rootPane for InsertScene
        BorderPane rootInsert = new BorderPane();
        rootInsert.setCenter(vehicleAndperosnal);
        rootInsert.setBottom(submtBtn);
        insertScene = new Scene(rootInsert,700,700);

        //final Scene
        Label estimateLabel = new Label("Here is your Estimate");
        estimateLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,15));
        Label estimatedQuote = new Label();
        estimatedQuote.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,10));
        
        //Final Scene Pane
        VBox finalLayout = new VBox(20);
        finalLayout.getChildren().addAll(estimateLabel,estimatedQuote);
        finalLayout.setAlignment(Pos.CENTER);
        finalScene = new Scene(finalLayout,700,500);

        //Event handlers
        mainScene = new Scene(rootPane,700,500);
        startButton.setOnAction(e -> {
            window.setScene(insertScene);
        });


        submitButton.setOnAction( e -> {
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection conn = DriverManager.getConnection("Paste dbURL here","Username","Password");
                if(conn != null) {System.out.println("Connection Secured");}


                //Check for what option was selected
                if(fourMonths.isSelected()){
                    coverageLength = "4 months";
                    if(Integer.parseInt(age.getText()) <= 25){
                        estimate = 975;
                    }else{estimate = 925;}
                    if(fullCoverage.isSelected()){fullOrPartial = "Full Coverage";}else{fullOrPartial = "Partial Coverage";}
                    if(Married.isSelected()){
                        maritalStatus = 1;
                        estimate = estimate - (estimate*0.05);
                    }else{maritalStatus = 0; estimate = estimate + (estimate * 0.07);}

                    if(Male.isSelected()){estimate = estimate + (estimate * 0.05); maleOrFemale = "Male";}else{maleOrFemale = "Female";}

                    if(suspended.isSelected()){
                        isSuspended = 1;
                        estimate = estimate + (estimate * 0.05);
                    }else{isSuspended = 0; estimate = estimate - (estimate * 0.05);}

                    if(yesRental.isSelected()){
                        rentalOffer = 1;
                        estimate = estimate + (estimate * 0.05);
                    }else{rentalOffer = 0;}

                    String formatted = String.format("%.2f",estimate);
                    estimatedQuote.setText(formatted + " per 4 Months");
                    Statement DBstatement = conn.createStatement();
                    DBstatement.execute("Begin INSERT_INTO_ALL_TABLES("+ LicenseNumber.getText()+",'"+Fname.getText()+"','"+ Lname.getText()+"','"+ maleOrFemale+"',"+ age.getText()+","+maritalStatus+ ",'"+city.getText()+"','"+province.getText()+"','"+country.getText()+"',"+ numOfAccidents.getText() +","+numOfTickets.getText()+","+isSuspended+"'"+ make.getText()+"','"+mileage.getText()+"',"+year.getText()+",'"+coverageLength+"',"+monthsPaid.getText()+",'"+fullOrPartial+"',"+rentalOffer+"); end;");

                }else{coverageLength = "12 Months";

                    if(Integer.parseInt(age.getText()) <= 25){
                        estimate = 3000;
                    }else{estimate = 2500;}

                    if(fullCoverage.isSelected()){fullOrPartial = "Full Coverage";}else{fullOrPartial = "Partial Coverage";}

                    if(Male.isSelected()){estimate = estimate + (estimate * 0.05); maleOrFemale = "Male";}else{maleOrFemale = "Female";}

                    if(Married.isSelected()){
                        maritalStatus = 1;
                        estimate = estimate - (estimate*0.05);
                    }else{maritalStatus = 0; estimate = estimate + (estimate * 0.07);}

                    if(suspended.isSelected()){
                        isSuspended = 1;
                        estimate = estimate + (estimate * 0.05);
                    }else{isSuspended = 0; estimate = estimate - (estimate * 0.05);}

                    if(yesRental.isSelected()){
                        rentalOffer = 1;
                        estimate = estimate + (estimate * 0.05);
                    }else{rentalOffer = 0;}
                    
                    String formatted = String.format("%.2f",estimate);
                    estimatedQuote.setText(formatted + " per Year");

                    Statement DBstatement = conn.createStatement();
                    DBstatement.execute("Begin INSERT_INTO_ALL_TABLES("+ LicenseNumber.getText()+",'"+Fname.getText()+"','"+ Lname.getText()+"','"+ maleOrFemale+"',"+ age.getText()+","+maritalStatus+ ",'"+city.getText()+"','"+province.getText()+"','"+country.getText()+"',"+ numOfAccidents.getText() +","+numOfTickets.getText()+","+isSuspended+",'"+ make.getText()+"','"+mileage.getText()+"',"+year.getText()+",'"+coverageLength+"',"+monthsPaid.getText()+",'"+fullOrPartial+"',"+rentalOffer+"); end;");

                }
                System.out.println(estimate);
            }catch(ClassNotFoundException | SQLException i){
                i.printStackTrace();
                System.out.println("System Not Connected");
            }
            window.setScene(finalScene);
        });
        window.setScene(mainScene);
        window.show();
    }
    public static void main(String[] args) {
        launch();
    }
}