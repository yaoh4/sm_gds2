package gov.nih.nci.cbiit.scimgmt.gds.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
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
    private List<List<String>> data;

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public void setData(List<List<String>> data) {
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

        // write headers
        if (headers != null && !headers.isEmpty()) {
            HSSFRow row = sheet.createRow(rownum++);
            int cellnum = 0;
            Iterator<String> it = headers.iterator();
            while (it.hasNext()) {
                HSSFCell cell = row.createCell(cellnum);
                cell.setCellValue(it.next());
                cell.setCellStyle(headerStyle);
                sheet.autoSizeColumn(cellnum++);
            }
        }

        // write rows data
        Iterator<List<String>> itRows = data.iterator();
        while (itRows.hasNext()) {
            HSSFRow row = sheet.createRow(rownum++);
            int cellnum = 0;
            Iterator<String> itCells = itRows.next().iterator();
            while (itCells.hasNext()) {
                HSSFCell cell = row.createCell(cellnum++);
                cell.setCellValue(itCells.next());
            }
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

    public void doExport(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String filename = "search_results_" + MessageFormat.format("{0, date, MM_dd_yyyy_hh_mm_ss}", new Date()).trim() + ".xls";
        String mimeType = "application/vnd.ms-excel";
        String characterEncoding = response.getCharacterEncoding();
        if (characterEncoding != null) {
            mimeType += "; charset=" + characterEncoding;
        }
        response.setContentType(mimeType);
        response.addHeader("content-disposition", "inline; filename=" + filename);

        Writer w = response.getWriter();
        StringBuffer sb = new StringBuffer();

        try {
            // write headers
            if (headers != null && !headers.isEmpty()) {
                Iterator<String> it = headers.iterator();
                while (it.hasNext()) {
                    sb.append(escapeCell(it.next()));
                    if (it.hasNext()) {
                        sb.append("\t");
                    }
                }
                sb.append("\n");
            }
            write(w, sb.toString());

            // write rows data
            Iterator<List<String>> itRows = data.iterator();
            while (itRows.hasNext()) {
                Iterator<String> itCells = itRows.next().iterator();
                sb = new StringBuffer();
                while (itCells.hasNext()) {
                    sb.append(escapeCell(itCells.next()));
                    if (itCells.hasNext()) {
                        sb.append("\t");
                    }
                }
                if (itRows.hasNext()) {
                    sb.append("\n");
                }
                write(w, sb.toString());
            }
        } finally {
            w.flush();
            w.close();
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
    private void write(Writer out, String string) throws IOException
    {
        if (string != null)
        {
            out.write(string);
        }
    }
}
