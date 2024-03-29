package ship.control.panel.controller;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import eu.hansolo.medusa.TickMarkType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import ship.control.panel.model.Ship;

import java.net.URL;
import java.util.ResourceBundle;

public class ShipControlPanelViewController implements Initializable {
    @FXML
    private AnchorPane mainPane;

    @FXML
    private Pane engineSpeedGaugePane;

    @FXML
    private Pane speedValueGaugePane;

//    @FXML
//    private Slider leftGas;
//
//    @FXML
//    private Slider rightGas;

    @FXML
    private Pane weatherDisplayPane;

    @FXML
    private Pane fuelGaugePane;
//
//    @FXML
//    private Pane gasPane;

    @FXML
    private Button buttonStartStop;

    Ship ship;

    Gauge gaugeFuel = initializeGaugeFuel();

    Gauge lcdScreen = initializeLcdScreen();

    Gauge gaugeSpeed = initializeGaugeSpeed();

    Gauge rpm = initializeRPM();

    private void simulateRPM() {
        boolean temp = true;
        while (true) {
            if (Ship.isOn()) {
                if (temp) {
                    rpm.setValue(rpm.getValue() + 25);
                } else {
                    rpm.setValue(rpm.getValue() - 25);
                }
                temp = !temp;
            } else {
                System.out.println("rpm off");
                rpm.setValue(0);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void simulateFuel() {
        while (true) {
            if (Ship.isOn()) {
                gaugeFuel.setValue(gaugeFuel.getValue() - rpm.getValue() / 1000000);
            } else {
                System.out.println("fuel off");
//                gaugeFuel.setValue(0);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    private synchronized void simulateSpeed() {
//        double speed = leftGas.getValue() + rightGas.getValue();
//        gaugeSpeed.setValue(speed);
//    }

    ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Ship.setOn(false);

//        gasPane.getStyleClass().add("gradientGas");
        mainPane.getStyleClass().add("bgSets");
        buttonStartStop.getStyleClass().add("sale");
//        leftGas.getStyleClass().add("box");

        fuelGaugePane.getChildren().add(gaugeFuel);
        weatherDisplayPane.getChildren().add(lcdScreen);
        speedValueGaugePane.getChildren().add(gaugeSpeed);
        engineSpeedGaugePane.getChildren().add(rpm);


        Thread threadSimulatedRPM = new Thread(this::simulateRPM);
        threadSimulatedRPM.setDaemon(true);
        threadSimulatedRPM.start();

        Thread threadSimulatedFuel = new Thread(this::simulateFuel);
        threadSimulatedFuel.setDaemon(true);
        threadSimulatedFuel.start();

        buttonStartStop.addEventFilter(ActionEvent.ACTION, actionEvent -> {
            Ship.setOn(!Ship.isOn());
            if (Ship.isOn()) {
                gaugeFuel.setValue(0.95);
                rpm.setValue(1000);
            }
        });

//        leftGas.valueProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
//                System.out.println(newValue);
//                simulateSpeed();
//            }
//        });
//
//        rightGas.valueProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
//                System.out.println(newValue);
//                simulateSpeed();
//            }
//        });


    }

    private Gauge initializeGaugeFuel() {
        var gaugeFuel = GaugeBuilder.create()
                .skinType(Gauge.SkinType.VERTICAL)
                .foregroundBaseColor(Color.rgb(249, 249, 249))
                .knobColor(Color.BLACK)
                .maxValue(1.0)
                .valueVisible(false)
                .sectionsVisible(true)
                .sections(new Section(0.0, 0.2, Color.rgb(255, 10, 1)))
                .minorTickMarksVisible(false)
                .mediumTickMarksVisible(false)
                .majorTickMarkType(TickMarkType.BOX)
                .title("FUEL")
                .needleSize(Gauge.NeedleSize.THICK)
                .needleShape(Gauge.NeedleShape.ROUND)
                .needleColor(Color.rgb(255, 61, 10))
                .shadowsEnabled(true)
                .angleRange(90)
                .customTickLabelsEnabled(true)
                .customTickLabels("E", "", "", "", "", "1/2", "", "", "", "", "F")
                .animated(true)
                .value(0)
                .build();
//        gaugeFuel.setValue(0.0);
        return gaugeFuel;
    }

    private Gauge initializeLcdScreen() {
        var gaugeLcdScreen = GaugeBuilder.create()
                .skinType(Gauge.SkinType.LCD)
//                .ledColor(Color.PURPLE)
//                .foregroundBaseColor(Color.BLACK)
//                .barColor(Color.BLACK)
//                .averageColor(Color.BLACK)
//                .barBackgroundColor(Color.BLACK)
//                .ledOn(true)
//                .
//                .title("TEMPERATURE")
                .prefSize(480, 201)
//                .subTitle("subtitle")
//                .value(26)
//                .unit("C")
//                .averageVisible(true)
                .build();
        gaugeLcdScreen.setBarBackgroundColor(Color.BLACK);
        gaugeLcdScreen.setForegroundBaseColor(Color.WHITE);
        gaugeLcdScreen.setKnobColor(Color.BLACK);
        gaugeLcdScreen.setBarColor(Color.BLACK);
        gaugeLcdScreen.setAverageColor(Color.PURPLE);
        gaugeLcdScreen.setLedColor(Color.BLACK);
        return gaugeLcdScreen;
    }

    private Gauge initializeGaugeSpeed() {
        return GaugeBuilder.create()
                .skinType(Gauge.SkinType.MODERN)
                .sections(new Section(85, 90, "", Color.rgb(204, 0, 0, 0.5)),
                        new Section(90, 95, "", Color.rgb(204, 0, 0, 0.75)),
                        new Section(95, 100, "", Color.rgb(204, 0, 0)))
                .title("KNOT SPEED")
                .maxValue(100)
                .minValue(-100)
                .prefSize(200, 225)
                .unit("UNIT")
                .threshold(85)
                .thresholdVisible(true)
                .animated(true)
                .value(0)
                .build();
    }

    private Gauge initializeRPM() {
        return GaugeBuilder.create()
                .borderPaint(Color.WHITE)
                .foregroundBaseColor(Color.WHITE)
                .prefSize(400, 400)
                .startAngle(290)
                .angleRange(220)
                .minValue(0)
                .maxValue(4000)
                .prefSize(200, 225)
                .valueVisible(false)
                .minorTickMarksVisible(false)
                .majorTickMarkType(TickMarkType.BOX)
                .mediumTickMarkType(TickMarkType.BOX)
                .title("RPM\nx100")
                .needleShape(Gauge.NeedleShape.ROUND)
                .needleSize(Gauge.NeedleSize.THICK)
                .needleColor(Color.rgb(234, 67, 38))
                .knobColor(Gauge.DARK_COLOR)
                .customTickLabelsEnabled(true)
                .customTickLabelFontSize(40)
                .customTickLabels("0", "", "10", "", "20", "", "30", "", "40")
                .animated(true)
                .build();
    }


}
