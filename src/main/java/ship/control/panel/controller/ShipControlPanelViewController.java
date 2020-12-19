package ship.control.panel.controller;

import eu.hansolo.medusa.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import ship.control.panel.model.Ship;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ShipControlPanelViewController implements Initializable {
    @FXML
    private AnchorPane mainPane;

    @FXML
    private Pane engineSpeedGaugePane;

    @FXML
    private Pane speedValueGaugePane;

    @FXML
    private Pane weatherDisplayPane;

    @FXML
    private Pane fuelGaugePane;

    @FXML
    private Pane gasPane;

    @FXML
    private Pane inner;

    @FXML
    private Pane outer;

    @FXML
    private Pane indicator;

    private static Position indicatorPosition = Position.STOP;
    private static boolean wasFull = false;

    @FXML
    private Button buttonStartStop;

    @FXML
    private Button buttonAnchorUp;

    @FXML
    private Button buttonLights;

    @FXML
    private Button buttonAnchorDown;

    @FXML
    private Button buttonHorn;

    @FXML
    private Button buttonMode;

    Ship ship;

    Gauge gaugeFuel = initializeGaugeFuel();

    Gauge lcdScreen = initializeLcdScreen();

    Gauge gaugeSpeed = initializeGaugeSpeed();

    Gauge rpm = initializeRPM();

    double stateFuel = 0.95;

    private static boolean firstRun = false;

    private void simulateEngineBehaviour() {
        boolean temp = true;
        while (true) {
            if (firstRun) {
                if (Ship.isOn()) {
                    lcdScreen.setValue(26);
                    lcdScreen.setTitle("Weather");
                    lcdScreen.setUnit("C");
                    lcdScreen.setLcdDesign(LcdDesign.LIGHTGREEN);
                } else {
                    lcdScreen.setValue(0);
                    lcdScreen.setTitle("");
                    lcdScreen.setUnit("");
                    lcdScreen.setLcdDesign(LcdDesign.BLACK);
                }
                if (Ship.isOn()) {
                    double tempRpmValue = rpm.getValue();
                    if (temp) {
                        rpm.setValue(tempRpmValue + 25);
                    } else {
                        rpm.setValue(tempRpmValue - 25);
                    }
                    temp = !temp;
                } else {
                    System.out.println("rpm off");
                    rpm.setValue(0);
                }
                if (Ship.isOn()) {
                    if (indicatorPosition == Position.STOP) {
                        rpm.setValue(1000);
                    } else if (indicatorPosition == Position.LEFTSLOW || indicatorPosition == Position.RIGHTSLOW) {
                        rpm.setValue(2000);
                    } else if (indicatorPosition == Position.LEFTHALF || indicatorPosition == Position.RIGHTHALF) {
                        rpm.setValue(2800);
                    } else if (indicatorPosition == Position.LEFTFULL || indicatorPosition == Position.RIGHTFULL) {
                        rpm.setValue(3500);
                    }
                    var oldValue = stateFuel - rpm.getValue() / 1000000;
                    stateFuel = oldValue;
                    System.out.println("fuel on " + oldValue);
                    gaugeFuel.setValue(oldValue);
                } else {
                    System.out.println("fuel off");
                    gaugeFuel.setValue(0);
                }
                if (Ship.isOn()) {
                    if (indicatorPosition == Position.STOP) {
                        gaugeSpeed.setValue(0);
                    } else if (indicatorPosition == Position.LEFTSLOW) {
                        gaugeSpeed.setValue(-10);
                    } else if (indicatorPosition == Position.LEFTHALF) {
                        gaugeSpeed.setValue(-20);
                    } else if (indicatorPosition == Position.LEFTFULL) {
                        gaugeSpeed.setValue(-30);
                    } else if (indicatorPosition == Position.RIGHTSLOW) {
                        gaugeSpeed.setValue(10);
                    } else if (indicatorPosition == Position.RIGHTHALF) {
                        gaugeSpeed.setValue(20);
                    } else if (indicatorPosition == Position.RIGHTFULL) {
                        gaugeSpeed.setValue(30);
                    }
                }else{
                    gaugeSpeed.setValue(0);
                }

            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    String pathHorn = "src/main/resources/horn.mp3";
    String pathChain = "src/main/resources/chain.mp3";
    Media mediaHorn = new Media(new File(pathHorn).toURI().toString());
    Media mediaChain = new Media(new File(pathChain).toURI().toString());
    MediaPlayer horn = new MediaPlayer(mediaHorn);
    MediaPlayer chain = new MediaPlayer(mediaChain);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        inner.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String str = "-fx-rotate: " + event.getSceneX() % 360 + ";";
                inner.setStyle(str);
            }
        });

        indicator.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double position = event.getX();
                System.out.println(event.getX());
                if (position > 145 && position < 245) {
                    indicatorPosition = indicatorPosition.change(0);

                    if (indicatorPosition == Position.LEFTFULL && !wasFull) {
                        indicator.getStyleClass().removeAll("indicator_2");
                        indicator.getStyleClass().add("indicator_1");
                        wasFull = true;
                    } else if (indicatorPosition == Position.LEFTHALF) {
                        indicator.getStyleClass().removeAll("indicator_3");
                        indicator.getStyleClass().add("indicator_2");
                    } else if (indicatorPosition == Position.LEFTSLOW) {
                        indicator.getStyleClass().removeAll("indicator_4");
                        indicator.getStyleClass().add("indicator_3");
                    } else if (indicatorPosition == Position.STOP) {
                        indicator.getStyleClass().removeAll("indicator_5");
                        indicator.getStyleClass().add("indicator_4");
                    } else if (indicatorPosition == Position.RIGHTSLOW) {
                        indicator.getStyleClass().removeAll("indicator_6");
                        indicator.getStyleClass().add("indicator_5");
                    } else if (indicatorPosition == Position.RIGHTHALF) {
                        indicator.getStyleClass().removeAll("indicator_7");
                        indicator.getStyleClass().add("indicator_6");
                        wasFull = false;
                    }
                } else if (position < 343) {
                    indicatorPosition = indicatorPosition.change(1);

                    if (indicatorPosition == Position.LEFTHALF) {
                        indicator.getStyleClass().removeAll("indicator_1");
                        indicator.getStyleClass().add("indicator_2");
                        wasFull = false;
                    } else if (indicatorPosition == Position.LEFTSLOW) {
                        indicator.getStyleClass().removeAll("indicator_2");
                        indicator.getStyleClass().add("indicator_3");
                    } else if (indicatorPosition == Position.STOP) {
                        indicator.getStyleClass().removeAll("indicator_3");
                        indicator.getStyleClass().add("indicator_4");
                    } else if (indicatorPosition == Position.RIGHTSLOW) {
                        indicator.getStyleClass().removeAll("indicator_4");
                        indicator.getStyleClass().add("indicator_5");
                    } else if (indicatorPosition == Position.RIGHTHALF) {
                        indicator.getStyleClass().removeAll("indicator_5");
                        indicator.getStyleClass().add("indicator_6");
                    } else if (indicatorPosition == Position.RIGHTFULL && !wasFull) {
                        indicator.getStyleClass().removeAll("indicator_6");
                        indicator.getStyleClass().add("indicator_7");
                        wasFull = true;
                    }

                }
                System.out.println(indicatorPosition);
            }
        });

        Ship.setOn(false);
//        gasPane.getStyleClass().add("gradientGas");
        mainPane.getStyleClass().add("bgSets");
        buttonStartStop.getStyleClass().add("sale");
        buttonLights.getStyleClass().add("sale");
        buttonMode.getStyleClass().add("sale");
        buttonAnchorDown.getStyleClass().add("sale");
        buttonAnchorUp.getStyleClass().add("sale");
        buttonHorn.getStyleClass().add("sale");
//        leftGas.getStyleClass().add("box");

        fuelGaugePane.getChildren().add(gaugeFuel);
        weatherDisplayPane.getChildren().add(lcdScreen);
        speedValueGaugePane.getChildren().add(gaugeSpeed);
        engineSpeedGaugePane.getChildren().add(rpm);

        Thread threadSimulateEngine = new Thread(this::simulateEngineBehaviour);
        threadSimulateEngine.setDaemon(true);
        threadSimulateEngine.start();


        buttonStartStop.addEventFilter(ActionEvent.ACTION, actionEvent -> {
            if (!firstRun) {
                firstRun = true;
                System.out.println("wystartowal");
            }
            if (!Ship.isOn()) {
                rpm.setValue(1000);
                gaugeFuel.setValue(stateFuel);
            }
            Ship.setOn(!Ship.isOn());
        });


        buttonLights.addEventFilter(ActionEvent.ACTION, actionEvent -> {
        });

        buttonHorn.addEventFilter(ActionEvent.ACTION, actionEvent -> {
            if (Ship.isOn()) {
                horn.play();
            }
        });

        buttonAnchorUp.addEventFilter(ActionEvent.ACTION, actionEvent -> {
            if (Ship.isOn()) {
                chain.play();
            }
        });

        buttonAnchorDown.addEventFilter(ActionEvent.ACTION, actionEvent -> {
            if (Ship.isOn()) {
                chain.play();
            }
        });

        buttonMode.addEventFilter(ActionEvent.ACTION, actionEvent -> {
            if (Ship.isOn()) {
                if(lcdScreen.getLcdDesign() == LcdDesign.LIGHTGREEN)
                {
                    lcdScreen.setLcdDesign(LcdDesign.DARKGREEN);
                }
                else if(lcdScreen.getLcdDesign() == LcdDesign.DARKGREEN){
                    lcdScreen.setLcdDesign(LcdDesign.LIGHTGREEN);
                }

            }
        });


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
        gaugeFuel.setValue(0.0);
        return gaugeFuel;
    }

    private Gauge initializeLcdScreen() {
        var gaugeLcdScreen = GaugeBuilder.create()
                .skinType(Gauge.SkinType.LCD)
                .lcdDesign(LcdDesign.BLACK)
                .prefSize(480, 201)
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
                .skinType(Gauge.SkinType.HORIZONTAL)
                .sections(new Section(85, 90, "", Color.rgb(204, 0, 0, 0.5)),
                        new Section(90, 95, "", Color.rgb(204, 0, 0, 0.75)),
                        new Section(95, 100, "", Color.rgb(204, 0, 0)))
                .title("KNOT SPEED")
                .maxValue(50)
                .minValue(-50)
                .prefSize(350, 350)
                //.borderPaint(Color.WHITE)
                .foregroundBaseColor(Color.WHITE)
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
