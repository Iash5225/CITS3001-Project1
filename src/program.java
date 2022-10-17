import javax.tools.SimpleJavaFileObject;

import com.gembox.spreadsheet.*;
import com.gembox.spreadsheet.charts.*;

public class program {

    public static void main(String[] args) throws java.io.IOException {
        // If using Professional version, put your serial key below.
        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
        Simulation sim = new Simulation();

        ExcelFile ef = new ExcelFile();
        ExcelWorksheet ws = ef.addWorksheet("Chart");

        String[] rounds = new String[] { "50", "100", "150", "200", "250", "300", "350", "400", "450", "500" };

        int Number_of_Round_intervals = rounds.length;

        // Create Excel chart and select data for it.
        ExcelChart chart = ws.getCharts().add(ChartType.BAR, "D2", "M25");
        chart.selectData(ws.getCells().getSubrangeAbsolute(0, 0, Number_of_Round_intervals, 2), true);

        // Add data which is used by the Excel chart.
        for (int i = 0; i < Number_of_Round_intervals; i++) {
            ws.getCell(i + 1, 0)
                    .setValue(rounds[i % rounds.length] + (i < rounds.length ? "" : " " + (i / rounds.length + 1)));
            int round = Integer.parseInt(rounds[i]);
            int[] win_summary = sim.round_simulation(round);
            int blue_wins = win_summary[0];
            int red_wins = win_summary[1];
            ws.getCell(i + 1, 1).setValue(blue_wins);
            ws.getCell(i + 1, 2).setValue(red_wins);
        }

        // Set header row and formatting.
        ws.getCell(0, 0).setValue("Round");
        ws.getCell(0, 1).setValue("Blue Wins");
        ws.getCell(0, 2).setValue("Red Wins");
        ws.getCell(0, 1).getStyle().getFont().setWeight(ExcelFont.BOLD_WEIGHT);
        ws.getCell(0, 0).getStyle().getFont().setWeight(ExcelFont.BOLD_WEIGHT);
        ws.getCell(0, 2).getStyle().getFont().setWeight(ExcelFont.BOLD_WEIGHT);
        ws.getColumn(0).setWidth((int) LengthUnitConverter.convert(3, LengthUnit.CENTIMETER,
                LengthUnit.ZERO_CHARACTER_WIDTH_256_TH_PART));
        ef.save("Chart.xlsx");
    }
}
