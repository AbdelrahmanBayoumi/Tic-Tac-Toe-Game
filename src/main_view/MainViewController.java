package main_view;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Abdelrahman Bayoumi - https://github.com/AbdelrahmanBayoumi
 */
public class MainViewController implements Initializable {

    @FXML
    private Button one;
    @FXML
    private Button two;
    @FXML
    private Button three;
    @FXML
    private Button four;
    @FXML
    private Button five;
    @FXML
    private Button six;
    @FXML
    private Button seven;
    @FXML
    private Button eight;
    @FXML
    private Button nine;
    @FXML
    private Label status;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXDrawer drawer;

    @FXML
    private Label countX;
    @FXML
    private Label countO;
    Button[][] Buttons_List = new Button[3][3];
    private String player = "X";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        status.setText("X turn");
        initButtonList();
        initDrawer();
    }

    private void initDrawer() {
        try {
            VBox toolbar = FXMLLoader.load(getClass().getResource("/toolbar_view/Toolbar.fxml"));
            drawer.setSidePane(toolbar);
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
        }
        HamburgerBasicCloseTransition task = new HamburgerBasicCloseTransition(hamburger);
        task.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
            drawer.toggle();
        });
        drawer.setOnDrawerOpening((event) -> {
            task.setRate(task.getRate() * -1);
            task.play();
            drawer.toFront();
        });
        drawer.setOnDrawerClosed((event) -> {
            drawer.toBack();
            task.setRate(task.getRate() * -1);
            task.play();
        });
    }

    private void initButtonList() {
        Buttons_List[0][0] = one;
        Buttons_List[0][1] = two;
        Buttons_List[0][2] = three;
        Buttons_List[1][0] = four;
        Buttons_List[1][1] = five;
        Buttons_List[1][2] = six;
        Buttons_List[2][0] = seven;
        Buttons_List[2][1] = eight;
        Buttons_List[2][2] = nine;
    }

    private void reset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Buttons_List[i][j].setText("");
            }
        }
        player = "X";
        status.setText("X turn");
    }

    @FXML
    private void resetAction(ActionEvent event) {
        reset();
    }

    private void changePlayer() {
        if (player.equals("X")) {
            player = "O";
        } else {
            player = "X";
        }
        status.setText(player + " turn");
    }

    private void checkForWinner() {
        getWinner();
        getDrawn();
    }

    @FXML
    private void playAction(ActionEvent event) {
        Button btn = ((Button) event.getSource());
        if (btn.getText().equals("")) {
            btn.setText(player);
            changePlayer();
            checkForWinner();
        }
    }

    private String valueOf(int i, int j) {
        return Buttons_List[i][j].getText();
    }

    private void getWinner() {
        for (int i = 0; i < 3; i++) {
            String score = "";
            for (int j = 0; j < 3; j++) {
                score += valueOf(i, j);
            }
            if (isWinning(score)) {
                winner(score.charAt(0));
                return;
            }
        }
        for (int i = 0; i < 3; i++) {
            String score = "";
            for (int j = 0; j < 3; j++) {
                score += valueOf(j, i);
            }
            if (isWinning(score)) {
                winner(score.charAt(0));
                return;
            }
        }
        String score = "";
        score += valueOf(0, 0);
        score += valueOf(1, 1);
        score += valueOf(2, 2);
        if (isWinning(score)) {
            winner(score.charAt(0));
            return;
        }

        score = "";
        score += valueOf(2, 0);
        score += valueOf(1, 1);
        score += valueOf(0, 2);
        if (isWinning(score)) {
            winner(score.charAt(0));
        }
    }

    private boolean isWinning(String score) {
        if ("".equals(score) || score.length() != 3) {
            return false;
        }
        char c = score.charAt(0);
        for (int i = 1; i < score.length(); i++) {
            if (c != score.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private void winner(char c) {
        status.setText(c + " won");
        if (c == 'X') {
            countX.setText((Integer.parseInt(countX.getText()) + 1) + "");
        } else {
            countO.setText((Integer.parseInt(countO.getText()) + 1) + "");
        }
        reset();
    }

    private boolean getDrawn() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ("".equals(valueOf(i, j))) {
                    return false;
                }
            }
        }
        reset();
        return true;
    }
}
