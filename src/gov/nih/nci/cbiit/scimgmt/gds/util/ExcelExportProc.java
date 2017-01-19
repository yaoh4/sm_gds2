package gov.nih.nci.cbiit.scimgmt.gds.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import gov.nih.nci.cbiit.scimgmt.gds.model.ExportRow;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class ExcelExportProc {

    private final Logger log = LogManager.getLogger(ExcelExportProc.class);

    private List<String> headers;
    private List<ExportRow> data;

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public void setData(List<ExportRow> data) {
        this.data = data;
    }

    public void doExportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filename = "search_results_" + MessageFormat.format("{0, date, MM_dd_yyyy_hh_mm_ss}", new Date()).trim() + ".xls";
        String mimeType = "application/vnd.ms-excel";
        String characterEncoding = response.getCharacterEncoding();
        if (characterEncoding != null) {
            mimeType += "; charset=" + characterEncoding;
        }
        response.setContentType(mimeType);
        response.addHeader("content-disposition", "inline; filename=" + filename);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();

        CellStyle headerStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headerStyle.setFont(font);

        int rownum = 0;

        // write rows data
        Iterator<ExportRow> itRows = data.iterator();
        boolean group = false;
        int startGroup = 0, endGroup = 0;
        while (itRows.hasNext()) {
            HSSFRow row = sheet.createRow(rownum);
            int cellnum = 0;
            ExportRow currentRow = itRows.next();
            Iterator<String> itCells = currentRow.getRow().iterator();
            while (itCells.hasNext()) {
                HSSFCell cell = row.createCell(cellnum);
                cell.setCellValue(itCells.next());
                if(currentRow.isHeader()) {
                	cell.setCellStyle(headerStyle);
                    sheet.autoSizeColumn(cellnum);
                }
                cellnum++;
            }
            
            if(!group && currentRow.isGroup()) { //start grouping
            	startGroup = rownum;
            	group = true;
            }
            if(group && !currentRow.isGroup()) { //end grouping
            	endGroup = rownum-1;
            	sheet.groupRow(startGroup,endGroup);
            	sheet.setRowGroupCollapsed(startGroup, true);
            	sheet.setRowSumsBelow(false);
            	group = false;
            }
            rownum++;
        }
        // end last group if exist
        if(group) { //end grouping
        	endGroup = rownum-1;
        	sheet.groupRow(startGroup,endGroup);
        	sheet.setRowGroupCollapsed(startGroup, true);
        	sheet.setRowSumsBelow(false);
        }


        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            log.error("Failed to write into response - fileName=" + filename + ", mimeType=" + mimeType, e);
        }
        finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }

    }

    protected String escapeCell(Object value)
    {
        if (value != null)
        {
            return "\"" + StringUtils.replace(StringUtils.trim(value.toString()), "\"", "\"\"") + "\"";
        }

        return null;
    }

}
