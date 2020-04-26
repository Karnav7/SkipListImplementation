import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class GraphData extends JPanel {

    // int[] data = {
    //     21, 14, 18, 03, 86, 88, 74, 87, 54, 77,
    //     61, 55, 48, 60, 49, 36, 38, 27, 20, 18
    // };
    private List<Long> data;

    final int PAD = 1;

    public void setData(List<Long> list) {
        this.data = list;
    }
 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        // Draw ordinate.
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD));
        // Draw abcissa.
        g2.draw(new Line2D.Double(PAD, h-PAD, w-PAD, h-PAD));
        double xInc = (double)(w - 2*PAD)/(data.size()-1);
        double scale = (double)(h - 2*PAD)/getMax();
        // Mark data points.
        g2.setPaint(Color.red);
        for(int i = 0; i < data.size(); i++) {
            double x = PAD + i*xInc;
            double y = h - PAD - scale*data.get(i);
            g2.fill(new Ellipse2D.Double(x-2, y-2, 4, 4));
        }
    }
 
    private Long getMax() {
        Long max = -Long.MAX_VALUE;
        for(int i = 0; i < data.size(); i++) {
            if(data.get(i) > max)
                max = data.get(i);
        }
        return max;
    }

}