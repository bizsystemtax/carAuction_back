package egovframework.let.utl.fcc.service;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Class Name  : EgovFormBasedFileVo.java
 * @Description : Form-based File Upload VO
 * @Modification Information
 * 
 *     수정일         수정자                   수정내용
 *     -------          --------        ---------------------------
 *   2009.08.26       한성곤                  최초 생성
 *
 * 
 *  Copyright (C) 2008 by MOPAS  All right reserved.
 */
@SuppressWarnings("serial")
@Getter
@Setter
public class EgovFormBasedFileVo implements Serializable {
    /** 파일명 */
    private String fileName = "";
    /** ContextType */
    private String contentType = "";
    /** 하위 디렉토리 지정 */
    private String serverSubPath = "";
    /** 물리적 파일명 */
    private String physicalName = "";
    /** 파일 사이즈 */
    private long size = 0L;

    /** MultipartFile 객체 추가 */
    private MultipartFile multipartFile;

    /** 파일 데이터 추가 */
    private byte[] data;

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
