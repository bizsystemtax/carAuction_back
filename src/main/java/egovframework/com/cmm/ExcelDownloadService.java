package egovframework.com.cmm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExcelDownloadService {

    @Autowired
    private DataSource dataSource;

    public List<Map<String, Object>> getExcelData(String startDate, String endDate) throws Exception {
        String query = "SELECT col1, col2, col3, col4, col5, col6, created_at FROM penalty.excel WHERE created_at BETWEEN ? AND ?";

        List<Map<String, Object>> dataList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("col1", rs.getString("col1"));
                dataMap.put("col2", rs.getString("col2"));
                dataMap.put("col3", rs.getString("col3"));
                dataMap.put("col4", rs.getString("col4"));
                dataMap.put("col5", rs.getString("col5"));
                dataMap.put("col6", rs.getString("col6"));
                dataMap.put("created_at", rs.getString("created_at"));
                dataList.add(dataMap);
            }
        }
        return dataList;
    }
}
