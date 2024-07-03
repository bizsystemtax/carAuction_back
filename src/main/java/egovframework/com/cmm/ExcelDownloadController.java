package egovframework.com.cmm;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/excel")
public class ExcelDownloadController {

    @Autowired
    private ExcelDownloadService excelDownloadService;

    @PostMapping("/download")
    public ResponseEntity<byte[]> downloadExcel(@RequestBody Map<String, String> requestParams) throws Exception {
        String startDate = requestParams.get("startDate");
        String endDate = requestParams.get("endDate");

        List<Map<String, Object>> dataList = excelDownloadService.getExcelData(startDate, endDate);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headersArray = {"col1", "col2", "col3", "col4", "col5", "col6", "created_at"};
        for (int i = 0; i < headersArray.length; i++) {
            headerRow.createCell(i).setCellValue(headersArray[i]);
        }

        // Fill data rows
        int rowNum = 1;
        for (Map<String, Object> data : dataList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue((String) data.get("col1"));
            row.createCell(1).setCellValue((String) data.get("col2"));
            row.createCell(2).setCellValue((String) data.get("col3"));
            row.createCell(3).setCellValue((String) data.get("col4"));
            row.createCell(4).setCellValue((String) data.get("col5"));
            row.createCell(5).setCellValue((String) data.get("col6"));
            row.createCell(6).setCellValue((String) data.get("created_at"));
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        byte[] bytes = out.toByteArray();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.xlsx");

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(bytes);
    }
}
