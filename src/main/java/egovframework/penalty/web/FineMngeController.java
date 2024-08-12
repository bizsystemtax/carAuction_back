package egovframework.penalty.web;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.exception.BizException;
import egovframework.com.cmm.exception.ErrorCode;
import egovframework.com.cmm.service.ResultVO;
import egovframework.penalty.ComnCdVO;
import egovframework.penalty.FineMngeVO;
import egovframework.penalty.service.FineMngeService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/fineMnge")
public class FineMngeController {
	
	@Resource(name = "FineMngeService")
	private FineMngeService fineMngeService;
	
	/**
	 * @author 범칙금관리 검색조건 콤보박스 조회 컨트롤러
	 * @param paramVO
	 * @return resultVO
	 * @throws Exception
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/comboBoxList")
	public ResultVO retrieveComboBoxList() throws Exception {
		ComnCdVO paramVO = new ComnCdVO();
		ResultVO resultVO = new ResultVO();
		
		Map<String, Object> resultMap = fineMngeService.retrieveComboBoxList(paramVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}

	/**
	 * @author 범칙금관리 조회 컨트롤러
	 * @param  fineMngeVO
	 * @return resultVO
	 * @throws Exception
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/list")
	public ResultVO retrieveFineMnge(@RequestBody Map<String, String> requestParams) throws Exception{
		FineMngeVO fineMngeVO = new FineMngeVO();
		ResultVO resultVO = new ResultVO();
		
		String inVltDtStrt = requestParams.get("in_vlt_dt_strt").replaceAll("-", "");//위반일자시작
		String inVltDtEnd = requestParams.get("in_vlt_dt_end").replaceAll("-", "");	//위반일자종료
		String inVltKindCd = requestParams.get("in_vlt_kind_cd");					//위반종류코드
		String inSendPlcCd = requestParams.get("in_send_plc_cd");					//발송처코드
		String inGdCd = requestParams.get("in_gd_cd").replaceAll("[^0-9]","");		//상품코드
		String inCsNm = requestParams.get("in_cs_nm");								//고객명
		String inVhclNo = requestParams.get("in_vhcl_no");							//차량번호

		//조회조건 VO 세팅
		fineMngeVO.setInVltDtStrt(inVltDtStrt);
		fineMngeVO.setInVltDtEnd(inVltDtEnd);
		fineMngeVO.setInVltKindCd(inVltKindCd);
		fineMngeVO.setInSendPlcCd(inSendPlcCd);
		fineMngeVO.setInGdCd(inGdCd);
		fineMngeVO.setInCsNm(inCsNm);
		fineMngeVO.setInVhclNo(inVhclNo);
			
		//범칙금관리 조회 서비스 호출
		Map<String, Object> resultMap = fineMngeService.retrieveFineMnge(fineMngeVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}

	/**
	 * @author 범칙금관리 확정 상태 업데이트 컨트롤러
	 * @param  fineMngeVO
	 * @return resultVO
	 * @throws Exception
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/cfmt")
	public ResponseEntity<ResultVO> updateCfmtStat(@RequestBody List<Map<String, String>> requestParams) throws Exception{
		FineMngeVO fineMngeVO = new FineMngeVO();
		ResultVO resultVO = new ResultVO();
		Map<String, Object> resultMap = new HashMap<>();
		
		try {
			LoginVO loginVO = null;

			//로그인 여부 확인
//			Boolean isLogin = EgovUserDetailsHelper.isAuthenticated();
//			
//			if(isLogin) {
//				//사용자 정보 세팅
//				loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//				fineMngeVO.setUserId(loginVO.getId());
//				fineMngeVO.setUserIp(loginVO.getIp());
//			} else {
//				throw new BizException(ErrorCode.ERR300, "");
//			}

			for(int i=0; i<requestParams.size(); i++) {
				Map<String, String> list = requestParams.get(i);
				
				String vltDt = list.get("vlt_dt");		//위반일자
				String vltAtime = list.get("vlt_atime");//위반시각
				String vhclNo = list.get("vhcl_no");	//차량번호
				String fineSeq = list.get("fine_seq");	//범칙금일련번호
				String cfmtDt = LocalDate.now().toString().replaceAll("-", ""); //확정일자(당일로 세팅)
				
				//확정일자 업데이트용 VO 세팅
				fineMngeVO.setVltDt(vltDt);
				fineMngeVO.setVltAtime(vltAtime);
				fineMngeVO.setVhclNo(vhclNo);
				fineMngeVO.setFineSeq(fineSeq);
				fineMngeVO.setCfmtDt(cfmtDt);
				
				//유효한 데이터인지 확인용 VO 세팅
				fineMngeVO.setInVltDtStrt(vltDt);
				fineMngeVO.setInVltDtEnd(vltDt);
	
				String errKey = "\n(차량번호: " + vhclNo + " / 위반일자: " + vltDt + " / 위반시각: " + vltAtime + ")";
				
				//범칙금관리 조회 서비스 호출
				Map<String, Object> fineDataList = fineMngeService.retrieveFineMnge(fineMngeVO);
				List<FineMngeVO> fineData = (List<FineMngeVO>)fineDataList.get("resultList");

				//조회되지 않을 경우 오류
				if(fineData.isEmpty() || fineData == null) {
					throw new BizException(ErrorCode.ERR004, errKey);
				}
				
				String oldCfmtDt = Objects.toString(fineData.get(0).getCfmtDt(), ""); //확정일자
				
				//이미 확정된 경우 오류
				if(!"".equals(oldCfmtDt)) {
					throw new BizException(ErrorCode.ERR002, errKey);
				}
				
				//범칙금관리 확정 상태 업데이트 서비스 호출
				int cnt = fineMngeService.updateCfmtStat(fineMngeVO);
				
				//업데이트를 실패한 경우 오류
				if(cnt <= 0) {
					throw new BizException(ErrorCode.ERR003, errKey);
				}
			}
			resultMap.put("svcNm", "cfmt");
			
			resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
			resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
			resultVO.setResult(resultMap);
			return ResponseEntity.ok(resultVO);
		} catch (BizException e) {
			e.printStackTrace();
			resultMap.put("errMsg", e.getMessage());
			resultVO.setResult(resultMap);
			return ResponseEntity.status(400).body(resultVO);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("errMsg", ErrorCode.ERR000.getMessage());
			resultVO.setResult(resultMap);
			return ResponseEntity.status(400).body(resultVO);
		}
	}
	
	/**
	 * @author 발송처부서명 조회 컨트롤러
	 * @param  fineMngeVO
	 * @return resultVO
	 * @throws Exception
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/sendPlcDeptList")
	public ResultVO retrieveSendPlcDeptList(@RequestBody List<Map<String, String>> requestParams) throws Exception{
		FineMngeVO fineMngeVO = new FineMngeVO();
		ResultVO resultVO = new ResultVO();
		Map<String, String> list = requestParams.get(0);
		
		String sendPlcCd = list.get("sendPlcCd"); //발송처코드

		//조회조건 VO 세팅
		fineMngeVO.setSendPlcCd(sendPlcCd);
			
		//범칙금관리 조회 서비스 호출
		Map<String, Object> resultMap = fineMngeService.retrieveSendPlcDeptList(fineMngeVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
	
	/**
	 * @author 범칙금관리 수정 컨트롤러
	 * @param  fineMngeVO
	 * @return resultVO
	 * @throws Exception
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/fineUpdate")
	public ResponseEntity<ResultVO> updateFine(@RequestBody List<Map<String, String>> requestParams) throws Exception{
		FineMngeVO fineMngeVO = new FineMngeVO();
		ResultVO resultVO = new ResultVO();
		Map<String, Object> resultMap = new HashMap<>();
		
		try {
			LoginVO loginVO = null;

			//로그인 여부 확인
//			Boolean isLogin = EgovUserDetailsHelper.isAuthenticated();
//			
//			if(isLogin) {
//				//사용자 정보 세팅
//				loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
//				fineMngeVO.setUserId(loginVO.getId());
//				fineMngeVO.setUserIp(loginVO.getIp());
//			} else {
//				throw new BizException(ErrorCode.ERR300, "");
//			}

			Map<String, String> list = requestParams.get(0);
			
			String vltDt = list.get("vltDt"); //위반일자
			String vltAtime = list.get("vltAtime"); //위반시각
			String vhclNo = list.get("vhclNo"); //차량번호
			String fineSeq = list.get("fineSeq"); //범칙금일련번호
			String sendPlcCd = list.get("sendPlcCd"); //발송처코드
			String sendPlcSeq = list.get("sendPlcSeq"); //발송처일련번호
			String vltKindCd = list.get("vltKindCd"); //위반종류코드
			String fineAmt = list.get("fineAmt").replaceAll("[^0-9]",""); //범칙금금액
			String vltCts = list.get("vltCts");	//위반내용
			String vltPnt = list.get("vltPnt");	//위반장소
			String rcptDt = list.get("rcptDt").replaceAll("-", "");	//접수일자
			String pymtDdayDt = list.get("pymtDdayDt").replaceAll("-", "");	//납부기한일자
			
			//확정일자 업데이트용 VO 세팅
			fineMngeVO.setVltDt(vltDt);
			fineMngeVO.setVltAtime(vltAtime);
			fineMngeVO.setVhclNo(vhclNo);
			fineMngeVO.setFineSeq(fineSeq);
			fineMngeVO.setSendPlcCd(sendPlcCd);
			fineMngeVO.setSendPlcSeq(sendPlcSeq);
			fineMngeVO.setVltKindCd(vltKindCd);
			fineMngeVO.setFineAmt(fineAmt);
			fineMngeVO.setVltCts(vltCts);
			fineMngeVO.setVltPnt(vltPnt);
			fineMngeVO.setRcptDt(rcptDt);
			fineMngeVO.setPymtDdayDt(pymtDdayDt);
			
			//유효한 데이터인지 확인용 VO 세팅
			fineMngeVO.setInVltDtStrt(vltDt);
			fineMngeVO.setInVltDtEnd(vltDt);

			//범칙금관리 조회 서비스 호출
			Map<String, Object> fineDataList = fineMngeService.retrieveFineMnge(fineMngeVO);
			List<FineMngeVO> fineData = (List<FineMngeVO>)fineDataList.get("resultList");

			//조회되지 않을 경우 오류
			if(fineData.isEmpty() || fineData == null) {
				throw new BizException(ErrorCode.ERR004, "");
			}
			
			String oldCfmtDt = Objects.toString(fineData.get(0).getCfmtDt(), ""); //확정일자
			
			//이미 확정된 경우 오류
			if(!"".equals(oldCfmtDt)) {
				throw new BizException(ErrorCode.ERR002, "");
			}
			
			//범칙금관리 수정 서비스 호출
			int cnt = fineMngeService.updateFine(fineMngeVO);
			
			//업데이트를 실패한 경우 오류
			if(cnt <= 0) {
				throw new BizException(ErrorCode.ERR005, "");
			}
				
			resultMap.put("svcNm", "fineUpdate");
			
			resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
			resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
			resultVO.setResult(resultMap);
			return ResponseEntity.ok(resultVO);
		} catch (BizException e) {
			e.printStackTrace();
			resultMap.put("errMsg", e.getMessage());
			resultVO.setResult(resultMap);
			return ResponseEntity.status(400).body(resultVO);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("errMsg", ErrorCode.ERR000.getMessage());
			resultVO.setResult(resultMap);
			return ResponseEntity.status(400).body(resultVO);
		}
	}
}