package ship.control.panel.model;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.TickLabelOrientation;
import eu.hansolo.medusa.skins.ModernSkin;
import javafx.scene.paint.Color;

public class GaugeFuel {

    public static Gauge createGaugeFuel(){
        Gauge gauge = new Gauge();
        gauge.setSkin(new ModernSkin(gauge));  //ModernSkin : you guys can change the skin
        gauge.setTitle("FUEL");  //title
        gauge.setUnit("x100L");  //unit
        gauge.setUnitColor(Color.WHITE);
        gauge.setDecimals(0);
        gauge.setValue(100.00); //deafult position of needle on gauage
        gauge.setAnimated(true);
        gauge.setAnimationDuration(500);

        gauge.setValueColor(Color.WHITE);
        gauge.setTitleColor(Color.WHITE);
        gauge.setSubTitleColor(Color.WHITE);
        gauge.setBarColor(Color.rgb(0, 214, 215));
        gauge.setNeedleColor(Color.RED);
        gauge.setThresholdColor(Color.RED);  //color will become red if it crosses threshold value
//        gauge.setThreshold(85);
//        gauge.setThresholdVisible(true);
        gauge.setTickLabelColor(Color.rgb(151, 151, 151));
        gauge.setTickMarkColor(Color.WHITE);
        gauge.setTickLabelOrientation(TickLabelOrientation.ORTHOGONAL);
        return gauge;
    }
}
