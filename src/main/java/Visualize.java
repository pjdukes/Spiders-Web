import org.knowm.xchart.*;

import java.util.ArrayList;
import java.util.List;

public class TestChart {
    public void makePieChart(int numSeries, ArrayList<String> al) {
        PieChart chart = new PieChartBuilder().width(800).height(800).build();

        for (int i = 0; i < numSeries; i += 2) {
            chart.addSeries(al.get(i), Integer.parseInt(al.get(i + 1)));
        }


        new SwingWrapper(chart).displayChart();
    }

    public void makeBarChart(List<String> al1, List<Integer> al2) {
        CategoryChart chart = new CategoryChartBuilder().width(800).height(800).build();

        chart.addSeries("Bar Chart", al1, al2);

        new SwingWrapper(chart).displayChart();
    }

//    public static void main(String[] args) {
//        TestChart tc = new TestChart();
//        ArrayList<String> array = new ArrayList<>();
//        array.add(0, "com");
//        array.add(1, "10");
//        array.add(2, "org");
//        array.add(3, "20");
//
//        List<String> list1 = new ArrayList<>();
//        List<Integer> list2 = new ArrayList<>();
//
//        list1.add("com");
//        list1.add("org");
//        list2.add(10);
//        list2.add(20);
//        tc.makePieChart(4, array);
//        tc.makeBarChart(list1, list2);
//    }
}