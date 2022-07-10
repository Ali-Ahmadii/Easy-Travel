package com.example.easytravelpro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.Date;

public class PaymentPageController implements Initializable {
    static final String DB_URL = "jdbc:mysql://localhost:3306/easy_travel";
    static final String USER = "root";
    static final String PASS = "integral4560sini";
    @FXML
    private HBox cardBox , recentTransitionBox;
    @FXML
    private Label accountBalance;
    @FXML
    private TextField cardNumber, year, month, ccv2;
    @FXML
    private RadioButton p50, p100, p200, p500;
    double selectedAmount = 50;
    static boolean entered = false;
    @FXML
    private Button chargeBtn;
    static ArrayList<Transition> transitions = new ArrayList<>();
    static ArrayList<VBox> recentTransitionCompos = new ArrayList<>();
    static int q = 0;


    static ArrayList<Card> cards = new ArrayList<>();
    static ArrayList<AnchorPane> cardCompos = new ArrayList<>();
    static int l = 0;
    public void radioBtn(ActionEvent event) {
        if (p50.isSelected()) {
            selectedAmount = 50;
            chargeBtn.setText("+Charge $"+String.valueOf(selectedAmount));
        }
        else if (p100.isSelected()) {
            selectedAmount = 100;
            chargeBtn.setText("+Charge $"+String.valueOf(selectedAmount));
        }
        else if (p200.isSelected()) {
            selectedAmount = 200;
            chargeBtn.setText("+Charge $"+String.valueOf(selectedAmount));
        }
        else if (p500.isSelected()) {
            selectedAmount = 500;
            chargeBtn.setText("+Charge $"+String.valueOf(selectedAmount));
        }
    }

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


    public void charge(ActionEvent event) throws SQLException, IOException {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tehran"));
        Date date = new Date();
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        SimpleDateFormat fmt = new SimpleDateFormat("hh:mm:ss a");

        String now = fmt.format(date);

        AD[0] = Integer.parseInt(year.format(date));
        AD[1] = Integer.parseInt(month.format(date));
        AD[2] = Integer.parseInt(day.format(date));

        int[] solar = Solar(AD[0], AD[1], AD[2]);
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
            if (p50.isSelected()) {
                selectedAmount = 50;
                chargeBtn.setText("+Charge $"+String.valueOf(selectedAmount));
            }
            else if (p100.isSelected()) {
                selectedAmount = 100;
                chargeBtn.setText("+Charge $"+String.valueOf(selectedAmount));
            }
            else if (p200.isSelected()) {
                selectedAmount = 200;
                chargeBtn.setText("+Charge $"+String.valueOf(selectedAmount));
            }
            else if (p500.isSelected()) {
                selectedAmount = 500;
                chargeBtn.setText("+Charge $"+String.valueOf(selectedAmount));
            }
            if (RecentTransitionController.isAnyCardSelected) {
                double nextbalance = exbalanceint + selectedAmount;
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Are you Sure?");
                alert.setContentText("With this action you will add $" + selectedAmount + " to your Balance.");

                if (alert.showAndWait().get() == ButtonType.OK) {

                    RecentTransitionController.isSuccessfulCharge = true;

                    Method.notification(Alert.AlertType.INFORMATION,
                            "Great!",
                            "Successful Charge!",
                            "You add $" + selectedAmount + " to your Balance.");
                    PreparedStatement setnewbalancestate = cor.prepareStatement("UPDATE passengers SET Balance = " + "'" + String.valueOf(nextbalance) + "'" + "WHERE UserName = " + "'" + RightPanel.Name + "'");
                    setnewbalancestate.executeUpdate();
                    accountBalance.setText("Account Balance : $"+String.valueOf(nextbalance));
///////
                    transitions.add(new Transition(
                            String.valueOf(selectedAmount),
                            "Charging was Successful",
                            solar[0] + "/" + solar[1] + "/" + solar[2],
                            now
                    ));

                    recentTransitionCompos.add(FXMLLoader.load(Objects.requireNonNull(
                            this.getClass().getResource("RecentTransition.fxml"))));
                    recentTransitionBox.getChildren().add(recentTransitionCompos.get(q));
                    q++;

                } else {

                    RecentTransitionController.isSuccessfulCharge = false;

                    transitions.add(new Transition(
                            String.valueOf(selectedAmount),
                            "Charging was unSuccessful",
                            solar[0] + "/" + solar[1] + "/" + solar[2],
                            now
                    ));

                    recentTransitionCompos.add(FXMLLoader.load(Objects.requireNonNull(
                            this.getClass().getResource("RecentTransition.fxml"))));
                    recentTransitionBox.getChildren().add(recentTransitionCompos.get(q));
                    q++;
                }
                //////////////////////
                cor.close();

            } else
                Method.notification(Alert.AlertType.ERROR, "Error!", "no card selected",
                        "Please Select your card from Your cards");


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
            if(entered) {
                PreparedStatement balancestate = con.prepareStatement("SELECT Balance FROM passengers WHERE UserName = " + "'" + RightPanel.Name + "'");
                ResultSet getbalance = balancestate.executeQuery();
                getbalance.next();
                String mybalance = getbalance.getString(1);
                accountBalance.setText("Account Balance : $"+mybalance);
            }
//            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int[] AD = new int[3];

    public static int[] Solar(int gy, int gm, int gd) {
        int[] out = {
                (gm > 2) ? (gy + 1) : gy,
                0,
                0
        };
        {
            int[] g_d_m = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
            out[2] = 355666 + (365 * gy) + ((out[0] + 3) / 4) - ((out[0] + 99) / 100) + ((out[0] + 399) / 400) + gd + g_d_m[gm - 1];
        }
        out[0] = -1595 + (33 * (out[2] / 12053));
        out[2] %= 12053;
        out[0] += 4 * (out[2] / 1461);
        out[2] %= 1461;
        if (out[2] > 365) {
            out[0] += (out[2] - 1) / 365;
            out[2] = (out[2] - 1) % 365;
        }
        if (out[2] < 186) {
            out[1] = 1 + (out[2] / 31);
            out[2] = 1 + (out[2] % 31);
        } else {
            out[1] = 7 + ((out[2] - 186) / 30);
            out[2] = 1 + ((out[2] - 186) % 30);
        }
        return out;
    }
}
