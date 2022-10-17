
import com.gembox.spreadsheet.*;
import com.gembox.spreadsheet.charts.*;

public class program2 {

    public static void main(String[] args) throws java.io.IOException {
        // If using Professional version, put your serial key below.
        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
        Simulation sim = new Simulation();

        ExcelFile ef = new ExcelFile();
        ExcelWorksheet ws = ef.addWorksheet("Chart");

        double[] u_lbs = { -0.1, -0.5, -1 };
        double[] u_ubs = { 0.1, 0.5, 1 };

        String[] u_ranges = { "(-0.1, 0.1)", "(-0.5, 0.5)", "(-1, 1)" };

        int Number_of_Uncertainty_intervals = u_ranges.length;

        // Create Excel chart and select data for it.
        ExcelChart chart = ws.getCharts().add(ChartType.BAR, "D2", "M25");
        chart.selectData(ws.getCells().getSubrangeAbsolute(0, 0, Number_of_Uncertainty_intervals, 2), true);

        // Add data which is used by the Excel chart.
        for (int i = 0; i < Number_of_Uncertainty_intervals; i++) {
            ws.getCell(i + 1, 0)
                    .setValue(u_ranges[i % u_ranges.length]
                            + (i < u_ranges.length ? "" : " " + (i / u_ranges.length + 1)));
            int[] win_summary = sim.uncertainty_simulation(u_lbs[i], u_ubs[i]);
            int blue_wins = win_summary[0];
            int red_wins = win_summary[1];
            ws.getCell(i + 1, 1).setValue(blue_wins);
            ws.getCell(i + 1, 2).setValue(red_wins);
        }

        // Set header row and formatting.
        ws.getCell(0, 0).setValue("Starting Uncertainty range");
        ws.getCell(0, 1).setValue("Blue Wins");
        ws.getCell(0, 2).setValue("Red Wins");
        ws.getCell(0, 1).getStyle().getFont().setWeight(ExcelFont.BOLD_WEIGHT);
        ws.getCell(0, 0).getStyle().getFont().setWeight(ExcelFont.BOLD_WEIGHT);
        ws.getCell(0, 2).getStyle().getFont().setWeight(ExcelFont.BOLD_WEIGHT);
        ws.getColumn(0).setWidth((int) LengthUnitConverter.convert(4, LengthUnit.CENTIMETER,
                LengthUnit.ZERO_CHARACTER_WIDTH_256_TH_PART));
        ef.save("Chart.xlsx");
    }

}
