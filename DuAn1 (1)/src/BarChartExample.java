
import com.edusys.dao.ThongKeDAO;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author TranVanThien
 */
public class BarChartExample extends ApplicationFrame{
    
    public BarChartExample(String title) {
    super(title);
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    
    ThongKeDAO TKDao = new ThongKeDAO();
    List<Object[]> list = TKDao.getBieuDo_chart();
    
    for (Object[] row : list) {
        String MaCD = (String) row[0]; // MaCD là kiểu String
        int SLKH = (int) row[1]; // SLKH là kiểu Integer
        
        dataset.addValue(SLKH, "Category 1", MaCD);
    }

    JFreeChart chart = ChartFactory.createBarChart(
        "Bar Chart Example",
        "Category",
        "Value",
        dataset,
        PlotOrientation.VERTICAL,
        true, true, false
    );
    
    CategoryPlot plot = chart.getCategoryPlot();
    BarRenderer renderer = (BarRenderer) plot.getRenderer();
    
    CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
    renderer.setBaseItemLabelGenerator(generator);
    renderer.setBaseItemLabelsVisible(true);
    
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new java.awt.Dimension(800, 600)); // Đặt kích thước biểu đồ
    setContentPane(chartPanel);
}


    public static void main(String[] args) {
        BarChartExample chart = new BarChartExample("Bar Chart Example");
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}