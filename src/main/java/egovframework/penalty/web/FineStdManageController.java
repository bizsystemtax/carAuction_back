package egovframework.penalty.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.exception.BizException;
import egovframework.com.cmm.exception.ErrorCode;
import egovframework.com.cmm.service.ResultVO;
import egovframework.penalty.FineMngeVO;
import egovframework.penalty.FineStdManageVO;
import egovframework.penalty.service.FineStdManageService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@Transactional
@RequestMapping("/fineStd")
public class FineStdManageController {
	@Resource(name = "FineStdManageService")
	private FineStdManageService fineStdManageService;
	
	/**
	 * 범칙금발송처기준관리 공통코드 조회 컨트롤러
	 * @return resultVO - VLT_KIND_CD, FINE_UPLOAD_CD, SEND_PLC_CD
	 * @throws BizException
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/comboBoxList")
	public ResultVO retrieveComboBoxList() throws Exception {
		ResultVO resultVO = new ResultVO();
		
		Map<String, Object> resultMap = fineStdManageService.retrieveComboBoxList();
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		return resultVO;
	}
	
	/**
	 * 범칙금발송처기준관리 조회 컨트롤러
	 * @param  requestParams - send_plc_cd, doc_typ_cd, send_plc_kind_cd, rcpt_typ_cd, hdlg_typ_cd
	 * @return resultVO - 조회 조건에 부합하는 발송처 목록
	 * @throws BizException
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/list")
	public ResultVO retrieveFineStdManageList(@RequestBody Map<String, String> requestParams) throws Exception {
		ResultVO resultVO = new ResultVO();
		FineStdManageVO paramVO = new FineStdManageVO();
		
		//조회조건 VO 세팅
		paramVO.setSendPlcCd(requestParams.get("send_plc_cd"));
		paramVO.setDocTypCd(requestParams.get("doc_typ_cd"));
		paramVO.setSendPlcKindCd(requestParams.get("send_plc_kind_cd"));
		paramVO.setRcptTypCd(requestParams.get("rcpt_typ_cd"));
		paramVO.setHdlgTypCd(requestParams.get("hdlg_typ_cd"));
		
		//범칙금발송처기준관리 조회 서비스 호출
		Map<String, Object> resultMap = fineStdManageService.retrieveFineStdManageList(paramVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		return resultVO;
	}
	
	/**
	 * 발송처 검색 모달 조회 컨트롤러
	 * @param  requestParams - ntcdoc_send_plc_nm
	 * @return resultVO - 조회 조건에 부합하는 발송처 목록
	 * @throws BizException
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/modalList")
	public ResultVO retrieveNtcdocSendPlcList(@RequestBody Map<String, String> requestParams) throws Exception {
		ResultVO resultVO = new ResultVO();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		//파라미터 세팅
		paramMap.put("ntcdocSendPlcNm", requestParams.get("ntcdoc_send_plc_nm"));
		
		//발송처 검색 모달 조회 서비스 호출
		Map<String, Object> resultMap = fineStdManageService.retrieveNtcdocSendPlcList(paramMap);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		return resultVO;
	}
	
	/**
	 * 발송처 상세 정보 저장 컨트롤러
	 * @param  requestParams - type, data
	 * @param  request - 토큰값으로 인증된 사용자를 확인하기 위한 HttpServletRequest
	 * @security {@SecurityRequirement(name = "Authorization")}
	 * @return resultVO
	 * @throws BizException
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/saveSendPlcData")
	public ResultVO insertSendPlcData(@RequestBody Map<String, Object> requestParams,
			HttpServletRequest request,
			@Parameter(hidden = true) @AuthenticationPrincipal LoginVO user) throws Exception {
		
		ResultVO resultVO = new ResultVO();
		Map<String, Object> param = (Map<String, Object>) requestParams.get("data");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String userId = user.getId();
		String userIp = user.getIp();

		//로그인 여부 확인
		if(!StringUtils.defaultString(userId).isEmpty()) {
			//사용자 정보 세팅
			param.put("sessionId", userId);
			param.put("sessionIp", userIp);
		} else {
			throw new BizException(ErrorCode.ERR300, "");
		}
		
		String type = (String) requestParams.get("type");
		
		//inser: 신규 등록
		if (type.equals("insert")) {
			//발송처 신규 등록 서비스 호출
			int cnt = fineStdManageService.insertSendPlcData(param);
			
			//업데이트를 실패한 경우 오류
			if(cnt <= 0) {
				throw new BizException(ErrorCode.ERR009, "");
			}
		} 
		//update: 수정
		else if (type.equals("update")) {
			String sendPlcCd = (String) param.get("sendPlcCd");		//발송처코드
			String sendPlcSeq = (String) param.get("sendPlcSeq");	//발송처일련번호
			
			//Key 존재 여부 확인
			if (StringUtils.defaultString(sendPlcCd).isEmpty() || StringUtils.defaultString(sendPlcSeq).isEmpty()) {
				throw new BizException(ErrorCode.ERR013, "");
			}
			
			//발송처 수정 서비스 호출
			int cnt = fineStdManageService.updateSendPlcData(param);
			
			//업데이트를 실패한 경우 오류
			if(cnt <= 0) {
				throw new BizException(ErrorCode.ERR005, "");
			}
		}
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		return resultVO;
	}
	
	/**
	 * 발송처 삭제 컨트롤러
	 * @param  requestParams - sendPlcCd, sendPlcSeq
	 * @param  request - 토큰값으로 인증된 사용자를 확인하기 위한 HttpServletRequest
	 * @return resultVO
	 * @throws BizException
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/deleteSendPlcData")
	public ResultVO deleteSendPlcData(@RequestBody List<Map<String, String>> requestParams,
			HttpServletRequest request,
			@Parameter(hidden = true) @AuthenticationPrincipal LoginVO user) throws Exception {
		FineStdManageVO fineStdManageVO = new FineStdManageVO();
		ResultVO resultVO = new ResultVO();
		Map<String, Object> resultMap = new HashMap<>();
		
		String userId = user.getId();

		//로그인 여부 확인
		if(StringUtils.defaultString(userId).isEmpty()) {
			throw new BizException(ErrorCode.ERR300, "");
		}
		
		for(int i=0; i<requestParams.size(); i++) {
			Map<String, String> list = requestParams.get(i);
			
			String sendPlcCd = list.get("sendPlcCd");	//발송처코드
			String sendPlcSeq = list.get("sendPlcSeq");	//발송처일련번호
			
			if (StringUtils.defaultString(sendPlcCd).isEmpty() || StringUtils.defaultString(sendPlcSeq).isEmpty()) {
				throw new BizException(ErrorCode.ERR013, "");
			}

			//삭제용 VO 세팅
			fineStdManageVO.setSendPlcCd(sendPlcCd);
			fineStdManageVO.setSendPlcSeq(sendPlcSeq);
			
			//삭제 서비스 호출
			int cnt = fineStdManageService.deleteSendPlcData(fineStdManageVO);
			
			//삭제를 실패한 경우 오류
			if(cnt <= 0) {
				throw new BizException(ErrorCode.ERR006, "");
			}
		}
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		return resultVO;
	}
	
	/**
	 * 발송처 업로드
	 * @param  requestParams - sendPlcCd, sendPlcKindCd, ntcdocSendPlcNm, ntcdocSendPlcDeptNm, comnSendPlcNm, st1SendPlcOficNm, scndSendPlcOficNm, 
	 * 						   st1SendPlcTelno, scndSendPlcTelno, thrdSendPlcTelno, faxno, oficEmailAddr, hpageAddr, docTypCd, rcptTypCd, hdlgTypCd, 
	 * 						   zipCd, sendPlcAddr, sendPlcSpcfcAddr, plclMtr, sendPlcRem, st1DeptKeyWordCts, scndDeptKeyWordCts, thrdDeptKeyWordCts
	 * @param  request - 토큰값으로 인증된 사용자를 확인하기 위한 HttpServletRequest
	 * @return resultVO - 성공한 서비스명
	 * @throws BizException
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/uploadSendPlc")
	public ResultVO uploadSendPlc(@RequestBody List<Map<String, Object>> requestParams, 
			HttpServletRequest request,
			@Parameter(hidden = true) @AuthenticationPrincipal LoginVO user) throws Exception{
		ResultVO resultVO = new ResultVO();
		Map<String, Object> resultMap = new HashMap<>();
		
		String userId = user.getId();
		String userIp = user.getIp();

		//로그인 여부 확인
		if(StringUtils.defaultString(userId).isEmpty()) {
			throw new BizException(ErrorCode.ERR300, "");
		}
		
		for(int i=0; i<requestParams.size(); i++) {
			Map<String, Object> list = requestParams.get(i);

			// 세션 사용자 정보 세팅
			list.put("sessionId", userId);
			list.put("sessionIp", userIp);
			
			String sendPlcCd = (String) list.get("sendPlcCd");						//발송처코드
			String ntcdocSendPlcNm = (String) list.get("ntcdocSendPlcNm");			//고지서발송처명
			String ntcdocSendPlcDeptNm = (String) list.get("ntcdocSendPlcDeptNm");	//고지서발송처부서명
			
			String errKey = "\n(발송처코드: " + sendPlcCd + " / 발송처: " + ntcdocSendPlcNm + " / 부서: " + ntcdocSendPlcDeptNm + ")";
			
			//발송처 등록 서비스 호출
			int cnt = fineStdManageService.insertSendPlcData(list);
			
			//등록을 실패한 경우 오류
			if(cnt <= 0) {
				throw new BizException(ErrorCode.ERR009, errKey);
			}
		}

		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		return resultVO;
	}
	
	/**
	 * 발송처 다운로드
	 * @param  requestParams - sendPlcCd, sendPlcSeq, ntcdocSendPlcNm, ntcdocSendPlcDeptNm
	 * @return resultVO - 엑셀 양식에 들어갈 다운로드 내용
	 * @throws BizException
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/downloadSendPlc")
	public ResultVO downloadSendPlc(@RequestBody List<Map<String, Object>> requestParams) throws Exception{
		ResultVO resultVO = new ResultVO();
		List<FineStdManageVO> finalList = new ArrayList<>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		for(int i=0; i<requestParams.size(); i++) {
			Map<String, Object> list = requestParams.get(i);
			//화면에서 넘어온 데이터 VO 세팅
			String sendPlcCd = (String) list.get("sendPlcCd");						//발송처코드
			String ntcdocSendPlcNm = (String) list.get("ntcdocSendPlcNm");			//고지서발송처명
			String ntcdocSendPlcDeptNm = (String) list.get("ntcdocSendPlcDeptNm");	//고지서발송처부서명
			
			String errKey = "\n(발송처코드: " + sendPlcCd + " / 발송처: " + ntcdocSendPlcNm + " / 부서: " + ntcdocSendPlcDeptNm + ")";
			
			//다운로드 데이터 조회
			List<FineStdManageVO> data = fineStdManageService.downloadSendPlc(list);
			
			//발송처 정보가 조회되지 않으면 오류
			if(data.size() == 0) {
				throw new BizException(ErrorCode.ERR014, errKey);
			}
			
			for (FineStdManageVO vo : data) {
	            vo.setFileName("발송처_다운로드_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
	        }

			finalList.addAll(data);
		}
		
		//발송처코드와 발송처일련번호를 숫자로 형변환 한 뒤 오름차순으로 정렬
		finalList.sort(Comparator.comparing(vo -> {
			FineStdManageVO fmVO = (FineStdManageVO) vo;
		    return Integer.parseInt(fmVO.getCol1());
		}).thenComparing(vo -> {
			FineStdManageVO fmVO = (FineStdManageVO) vo;
		    return Integer.parseInt(fmVO.getSendPlcSeq());
		}));
		
		resultMap.put("resultList", finalList);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		return resultVO;
	}
}
