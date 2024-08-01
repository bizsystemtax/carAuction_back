package egovframework.com.cmm.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Excel 데이터 정보를 관리하기 위한 VO 클래스
 * @author biz
 * @since 2024.07.25
 * @version 1.0
 */

@Getter
@Setter
public class ExcelDataVO implements Serializable {

    @Schema(description = "컬럼1")
    private String col1 = "";

    @Schema(description = "컬럼2")
    private String col2 = "";

    @Schema(description = "컬럼3")
    private String col3 = "";

    @Schema(description = "컬럼4")
    private String col4 = "";

    @Schema(description = "컬럼5")
    private String col5 = "";

    @Schema(description = "컬럼6")
    private String col6 = "";

    @Schema(description = "생성일시")
    private String createdAt = "";
    
    @Schema(description = "시작일시")
    private String startDate = "";
    
    @Schema(description = "마지막일시")
    private String endDate = "";

    /**
     * toString 메소드를 대치한다.
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
