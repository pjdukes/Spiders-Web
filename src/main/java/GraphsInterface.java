import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;

public class GraphsInterface extends JFrame{
    
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    
    public GraphsInterface() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //
        setBounds(100, 100, 850, 650); //Changed by me 
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane); //
    }
    
    public void createPieChart(PieChart chart) {
        JPanel pieChart = new XChartPanel<PieChart>(chart);
        contentPane.add(pieChart);
        contentPane.validate();
    }
    
    public void createCategoryChart(CategoryChart chart) {
        JPanel panelChart = new XChartPanel<CategoryChart>(chart);
        contentPane.add(panelChart);
        contentPane.validate();
    }
}
