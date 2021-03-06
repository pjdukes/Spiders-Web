import org.knowm.xchart.*;

import java.io.IOException;
import java.util.ArrayList;

public class Visualize {
    
    
    public void makePieChart(ArrayList<String> al1, ArrayList<Integer> al2, int flag, String name) {
        PieChart chart = new PieChartBuilder().width(800).height(800).build();

        for (int i = 0; i < al1.size(); i++) {
            chart.addSeries(al1.get(i), al2.get(i));
        }

        if (flag == 1) {
            try {
                BitmapEncoder.saveBitmap(chart, "./" + name, BitmapEncoder.BitmapFormat.PNG);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        GraphsInterface graph = new GraphsInterface();
        graph.setVisible(true);
        graph.createPieChart(chart);
    }

    public void makeBarChart(ArrayList<String> al1, ArrayList<Integer> al2, int flag, String name) {
        CategoryChart chart = new CategoryChartBuilder().width(800).height(800).build();

        chart.addSeries("Bar Chart", al1, al2);
        
        chart.getStyler().setXAxisLabelRotation(90);

        if (flag == 1) {
            try {
                BitmapEncoder.saveBitmap(chart, "./" + name, BitmapEncoder.BitmapFormat.PNG);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        
        GraphsInterface graph = new GraphsInterface();
        graph.setVisible(true);
        graph.createCategoryChart(chart);
    }
}
