package egovframework.com.cmm.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.com.cmm.service.ResultVO;

/**
 * @Class Name : GlobalExceptionHandler
 * @Description : BizException 전역 오류 처리
 * @ 수정일     수정자  수정내용
 * @ -------- ---- ---------------------------
 * @ 20240911 이인하  최초 생성
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(BizException.class)
    @ResponseBody
    public ResponseEntity<?> handleBizException(BizException ex) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("errMsg", ex.getMessage());

        ResultVO resultVO = new ResultVO();
        resultVO.setResult(resultMap);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultVO);
    }
	
	@ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("errMsg", ErrorCode.ERR000.getMessage());

        ResultVO resultVO = new ResultVO();
        resultVO.setResult(resultMap);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultVO);
    }
	
	@ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handleException(Exception ex) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("errMsg", ErrorCode.ERR000.getMessage());

        ResultVO resultVO = new ResultVO();
        resultVO.setResult(resultMap);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultVO);
    }
}
