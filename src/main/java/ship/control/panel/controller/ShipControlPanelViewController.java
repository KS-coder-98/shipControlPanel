package ship.control.panel.controller;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import eu.hansolo.medusa.TickMarkType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class ShipControlPanelViewController implements Initializable {

    @FXML
    private Pane engineSpeedGaugePane;

    @FXML
    private Pane speedValueGaugePane;

    @FXML
    private Slider leftGas;

    @FXML
    private Slider rightGas;

    @FXML
    private Pane weatherDisplayPane;

    @FXML
    private Pane fuelGaugePane;

    Gauge gaugeFuel = initializeGaugeFuel();

    Gauge lcdScreen = initializeLcdScreen();

    Gauge gaugeSpeed = initializeGaugeSpeed();

    Gauge rpm = initializeRPM();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        fuelGaugePane.getChildren().add(gaugeFuel);
        weatherDisplayPane.getChildren().add(lcdScreen);
        speedValueGaugePane.getChildren().add(gaugeSpeed);
        engineSpeedGaugePane.getChildren().add(rpm);


    }

    private Gauge initializeGaugeFuel() {
        var gaugeFuel = GaugeBuilder.create()
                .skinType(Gauge.SkinType.VERTICAL)
//                .prefSize(500, 250)
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
                .build();
        gaugeFuel.setValue(0.85);
        return gaugeFuel;
    }

    private Gauge initializeLcdScreen() {
        var gaugeLcdScreen = GaugeBuilder.create()
                .skinType(Gauge.SkinType.LCD)
                .title("TEMPERATURE")
                .prefSize(480, 201)
                .subTitle("subtitle")
                .value(26)
                .unit("C")
                .averageVisible(true)
                .build();
        return gaugeLcdScreen;
    }

    private Gauge initializeGaugeSpeed() {
        Gauge gauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.MODERN)
                .sections(new Section(85, 90, "", Color.rgb(204, 0, 0, 0.5)),
                        new Section(90, 95, "", Color.rgb(204, 0, 0, 0.75)),
                        new Section(95, 100, "", Color.rgb(204, 0, 0)))
                .title("KNOT SPEED")
                .value(14)
                .prefSize(200, 225)
                .unit("UNIT")
                .threshold(85)
                .thresholdVisible(true)
                .animated(true)
                .build();
        return gauge;
    }

    private Gauge initializeRPM(){
        return GaugeBuilder.create()
                .borderPaint(Color.WHITE)
                .foregroundBaseColor(Color.WHITE)
                .prefSize(400, 400)
                .startAngle(290)
                .angleRange(220)
                .minValue(0)
                .value(1000)
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
