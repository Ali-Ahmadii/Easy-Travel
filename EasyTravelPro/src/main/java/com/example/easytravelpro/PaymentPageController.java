package com.example.easytravelpro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class PaymentPageController implements Initializable {
    static final String DB_URL = "jdbc:mysql://localhost:3306/easy_travel";
    static final String USER = "root";
    static final String PASS = "integral4560sini";
    @FXML
    private HBox cardBox;
    @FXML
    private Label accountBalance;
    @FXML
    private TextField cardNumber, year, month, ccv2;
    @FXML
    private RadioButton p50, p100, p200, p500;
    double selectedAmount = 50;


    static ArrayList<Card> cards = new ArrayList<>();
    static ArrayList<AnchorPane> cardCompos = new ArrayList<>();
    static int l = 0;

    public void addCard(ActionEvent event) throws IOException, SQLException {
        Connection com = DriverManager.getConnection(DB_URL,USER,PASS);
        String accountnumber = cardNumber.getText();
        String cvv2 = ccv2.getText();
        String author = RightPanel.Name;
        String Expirestring = year.getText() +month.getText();
        PreparedStatement addcard = com.prepareStatement("INSERT INTO creditcard (Account, AuthorUser, CVV2, Expire)"+"VALUES "+"('"+accountnumber+"','"+author+"','"+cvv2+"','"+Expirestring+"')");
        addcard.execute();
        cards.add(new Card(cardNumber.getText(),ccv2.getText(),RightPanel.Name,year.getText() + "/" + month.getText()));
        cardCompos.add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("CardComponent.fxml"))));
        cardBox.getChildren().add(cardCompos.get(l));
        l++;
        ////////////
        com.close();
    }


    public void charge(ActionEvent event) throws SQLException {

        Connection cor = DriverManager.getConnection(DB_URL,USER,PASS);
        PreparedStatement howmanycards = cor.prepareStatement("SELECT COUNT(Account)" + "FROM creditcard " + "WHERE AuthorUser = "+"'"+RightPanel.Name+"'");
        ResultSet setcount = howmanycards.executeQuery();
        setcount.next();
        int counts = Integer.parseInt(setcount.getString(1));
        if(counts!=0) {
            PreparedStatement exbalancestate = cor.prepareStatement("SELECT Balance FROM passengers WHERE UserName = " + "'" + RightPanel.Name + "'");
            ResultSet getexbalance = exbalancestate.executeQuery();
            getexbalance.next();
            double exbalanceint = Double.parseDouble(getexbalance.getString(1));
            if (p50.isSelected())
                selectedAmount = 50;
            else if (p100.isSelected())
                selectedAmount = 100;
            else if (p200.isSelected())
                selectedAmount = 200;
            else if (p500.isSelected())
                selectedAmount = 500;
            double nextbalance = exbalanceint + selectedAmount;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you Sure?");
            alert.setContentText("With this action you will add $" + selectedAmount + " to your Balance.");

            if (alert.showAndWait().get() == ButtonType.OK) {
                PreparedStatement setnewbalancestate = cor.prepareStatement("UPDATE passengers SET Balance = " + "'" + String.valueOf(nextbalance) + "'" + "WHERE UserName = " + "'" + RightPanel.Name + "'");
                setnewbalancestate.executeUpdate();
                accountBalance.setText(String.valueOf(nextbalance));
                Method.notification(Alert.AlertType.INFORMATION,
                        "Great!",
                        "Successful Charge!",
                        "You add $" + selectedAmount + " to your Balance.");

                SignInUp.passengers.get(SignInUp.i - 1).setBalance(selectedAmount +
                        SignInUp.passengers.get(SignInUp.i - 1).getBalance());

                accountBalance.setText("Account Balance: " +
                        SignInUp.passengers.get(SignInUp.i - 1).getBalance());
            }
        }
        else{
            Method.notification(Alert.AlertType.ERROR,
                    "Error!",
                    "Failure",
                    "You Don't Have Any Account In Our Application");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection con = DriverManager.getConnection(DB_URL,USER,PASS);
            PreparedStatement howmanycards = con.prepareStatement("SELECT COUNT(Account)" + "FROM creditcard " + "WHERE AuthorUser = "+"'"+RightPanel.Name+"'");
            ResultSet setcount = howmanycards.executeQuery();
            setcount.next();
            int counts = Integer.parseInt(setcount.getString(1));
//            for(int i = 0;i<counts;i++){
            PreparedStatement cardnumberstate = con.prepareStatement("SELECT Account FROM creditcard WHERE AuthorUser = "+"'"+RightPanel.Name+"'");
            ResultSet getcards = cardnumberstate.executeQuery();
            while(getcards.next()){
                String mycard = getcards.getString(1);
                PreparedStatement cvv2state = con.prepareStatement("SELECT CVV2 FROM creditcard WHERE Account = "+"'"+mycard+"'");
                ResultSet getcvv = cvv2state.executeQuery();
                getcvv.next();
                String mycvv = getcvv.getString(1);
                PreparedStatement yearstate = con.prepareStatement("SELECT Expire FROM creditcard WHERE Account = "+"'"+mycard+"'");
                ResultSet getyear = yearstate.executeQuery();
                getyear.next();
                String myexpire = getyear.getString(1);
                cards.add(new Card(mycard,mycvv,AccountPageController.fullname,myexpire));
                cardCompos.add(FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("CardComponent.fxml"))));
                cardBox.getChildren().add(cardCompos.get(l));
                l++;
            }
            System.out.println(RightPanel.Name);
            PreparedStatement balancestate = con.prepareStatement("SELECT Balance FROM passengers WHERE UserName = "+"'"+RightPanel.Name+"'");
            ResultSet getbalance = balancestate.executeQuery();
            getbalance.next();
            String mybalance = getbalance.getString(1) +" $ ";
            accountBalance.setText(mybalance);
//            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
