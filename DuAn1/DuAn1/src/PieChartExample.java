
import com.edusys.dao.ThongKeDAO;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author TranVanThien
 */
public class PieChartExample extends ApplicationFrame{
    
    public PieChartExample(String title) {
        super(title);
        DefaultPieDataset dataset = new DefaultPieDataset();
        //Set dữ liệu
//        dataset.setValue("Category 1", 1.0);
//        dataset.setValue("Category 2", 2.0);
//        dataset.setValue("Category 3", 3.0);
        ThongKeDAO TKDao = new ThongKeDAO();
        List<Object[]> list = TKDao.getPie_Chart();
        for (Object[] row : list) {
           int year = (int) row[0];
           int Sl = (int) row[1];
           dataset.setValue(String.valueOf(year), Sl);
        }
        

        JFreeChart chart = ChartFactory.createPieChart(
                "Pie Chart Example",
                dataset,
                true, true, false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2})")); // Hiển thị tên và phần trăm

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 500)); // Đặt kích thước biểu đồ
        setContentPane(chartPanel);
    }

    public static void main(String[] args) {
        PieChartExample chart = new PieChartExample("Pie Chart Example");
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}