package egovframework.com.cmm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class ExcelSelectService {

    @Autowired
    private DataSource dataSource;

    public List<JsonNode> getExcelDataByDateRange(Date startDate, Date endDate) throws Exception {
        List<JsonNode> results = new ArrayList<>();
        String query = "SELECT * FROM penalty.excel WHERE created_at BETWEEN ? AND ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDate(1, new java.sql.Date(startDate.getTime()));
            ps.setDate(2, new java.sql.Date(endDate.getTime()));

            try (ResultSet rs = ps.executeQuery()) {
                ObjectMapper mapper = new ObjectMapper();
                while (rs.next()) {
                    ObjectNode node = mapper.createObjectNode();
                    node.put("col1", rs.getString("col1"));
                    node.put("col2", rs.getString("col2"));
                    node.put("col3", rs.getString("col3"));
                    node.put("col4", rs.getString("col4"));
                    node.put("col5", rs.getString("col5"));
                    node.put("col6", rs.getString("col6"));
                    node.put("created_at", rs.getDate("created_at").toString());
                    results.add(node);
                }
            }
        }

        return results;
    }
}
