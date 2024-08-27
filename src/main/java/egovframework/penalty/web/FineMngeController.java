package egovframework.penalty.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
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
import egovframework.penalty.ComnCdVO;
import egovframework.penalty.FineMngeVO;
import egovframework.penalty.service.FineMngeService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@Transactional(rollbackFor = Exception.class)
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
				
				//수정, 삭제 전용 범칙금 상태 유효성 검사
				fineMngeService.checkFineStat(fineMngeVO, errKey);
				
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
			
		//발송처부서 리스트 조회 서비스 호출
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
	@PostMapping(value = "/updateFine")
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
			
			//업데이트 VO 세팅
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

			//수정, 삭제 전용 범칙금 상태 유효성 검사
			fineMngeService.checkFineStat(fineMngeVO, "");

			//범칙금관리 수정 서비스 호출
			int cnt = fineMngeService.updateFine(fineMngeVO);
			
			//업데이트를 실패한 경우 오류
			if(cnt <= 0) {
				throw new BizException(ErrorCode.ERR005, "");
			}
				
			resultMap.put("svcNm", "updateFine");
			
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
	 * @author 범칙금관리 삭제 컨트롤러
	 * @param  fineMngeVO
	 * @return resultVO
	 * @throws Exception
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/deleteFine")
	public ResponseEntity<ResultVO> deleteFine(@RequestBody List<Map<String, String>> requestParams) throws Exception{
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
				
				//삭제용 VO 세팅
				fineMngeVO.setVltDt(vltDt);
				fineMngeVO.setVltAtime(vltAtime);
				fineMngeVO.setVhclNo(vhclNo);
				fineMngeVO.setFineSeq(fineSeq);
				
				//유효한 데이터인지 확인용 VO 세팅
				fineMngeVO.setInVltDtStrt(vltDt);
				fineMngeVO.setInVltDtEnd(vltDt);
	
				String errKey = "\n(차량번호: " + vhclNo + " / 위반일자: " + vltDt + " / 위반시각: " + vltAtime + ")";
				
				//수정, 삭제 전용 범칙금 상태 유효성 검사
				fineMngeService.checkFineStat(fineMngeVO, errKey);
				
				//범칙금관리 삭제 서비스 호출
				int cnt = fineMngeService.deleteFine(fineMngeVO);
				
				//삭제를 실패한 경우 오류
				if(cnt <= 0) {
					throw new BizException(ErrorCode.ERR006, errKey);
				}
			}
			resultMap.put("svcNm", "deleteFine");
			
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
	 * @author 범칙금관리 업로드(이파인)
	 * @param  fineMngeVO
	 * @return resultVO
	 * @throws Exception
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/uploadEfine")
	public ResponseEntity<ResultVO> uploadEfine(@RequestBody List<Map<String, String>> requestParams) throws Exception{
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
				//화면에서 넘어온 데이터 VO 세팅
				String docFineNo1 = list.get("docFineNo1");						//문서범칙금번호1(순번)
				String docFineNo2 = list.get("docFineNo2");						//문서범칙금번호2(요청번호)
				String docFineNo3 = list.get("docFineNo3");						//문서범칙금번호3(과태료번호)
				String vltDt = list.get("vltDt").replaceAll("-", "");			//위반일자
				String vltAtime = list.get("vltAtime").replaceAll(":", "");		//위반시각
				String vltPnt = list.get("vltPnt");								//위반장소
				String vltCts = list.get("vltCts");								//위반내용
				String vhclNo = list.get("vhclNo");								//차량번호
				String sendPlcNm = list.get("sendPlcNm");						//발송처명(발송처코드 매핑용)
				String pnltStatNm = list.get("pnltStatNm");						//과태료상태
				String fineAmt = list.get("fineAmt").replaceAll("[^0-9]","");	//범칙금금액
				String docFineNo4 = list.get("docFineNo4");						//문서범칙금번호4(일련번호)
				String actBankNm1 = list.get("actBankNm1");						//계좌은행명1
				String actNo1 = list.get("actNo1");								//계좌번호1
				String actBankNm2 = list.get("actBankNm2");						//계좌은행명2
				String actNo2 = list.get("actNo2");								//계좌번호2
				String actBankNm3 = list.get("actBankNm3");						//계좌은행명3
				String actNo3 = list.get("actNo3");								//계좌번호3
				String actBankNm4 = list.get("actBankNm4");						//계좌은행명4
				String actNo4 = list.get("actNo4");								//계좌번호4
				String pymtDdayDt = list.get("pymtDdayDt").replaceAll("-", "");	//납부기한일자
				
				fineMngeVO.setDocFineNo1(docFineNo1);
				fineMngeVO.setDocFineNo2(docFineNo2);
				fineMngeVO.setDocFineNo3(docFineNo3);
				fineMngeVO.setVltDt(vltDt);
				fineMngeVO.setVltAtime(vltAtime);
				fineMngeVO.setVltPnt(vltPnt);
				fineMngeVO.setVltCts(vltCts);
				fineMngeVO.setVhclNo(vhclNo);
				fineMngeVO.setSendPlcNm(sendPlcNm);
				fineMngeVO.setPnltStatNm(pnltStatNm);
				fineMngeVO.setFineAmt(fineAmt);
				fineMngeVO.setDocFineNo4(docFineNo4);
				fineMngeVO.setActBankNm1(actBankNm1);
				fineMngeVO.setActNo1(actNo1);
				fineMngeVO.setActBankNm2(actBankNm2);
				fineMngeVO.setActNo2(actNo2);
				fineMngeVO.setActBankNm3(actBankNm3);
				fineMngeVO.setActNo3(actNo3);
				fineMngeVO.setActBankNm4(actBankNm4);
				fineMngeVO.setActNo4(actNo4);
				fineMngeVO.setPymtDdayDt(pymtDdayDt);
				
				String errKey = "\n(차량번호: " + vhclNo + " / 위반일자: " + vltDt + " / 위반시각: " + vltAtime + ")";
				
				//차량번호로 대출정보 유효성 검사
				fineMngeService.checkVhclNoLoanInf(fineMngeVO, errKey);
				
				//위반종류 코드 매핑
				String vltKindCd = fineMngeService.retrieveVltKindCd(fineMngeVO);
				
				fineMngeVO.setVltKindCd(vltKindCd);
				
				//발송처 코드 매핑
				List<FineMngeVO> sendplcData = fineMngeService.retrieveSendPlcCd(fineMngeVO);
				
				fineMngeVO.setSendPlcCd(sendplcData.get(0).getSendPlcCd()); //발송처코드
				fineMngeVO.setSendPlcSeq(sendplcData.get(0).getSendPlcSeq()); //발송처일련번호

				//추가 파라미터 세팅
				fineMngeVO.setRcptDt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))); //접수일자(현재날짜)
				fineMngeVO.setNtcdocKindCd("1"); //고지서종류코드(1:위반사실확인서)
				fineMngeVO.setFineUploadCd("1"); //범칙금업로드코드(1:이파인)
				
				//범칙금관리 등록 서비스 호출
				int cnt = fineMngeService.insertFine(fineMngeVO, errKey);
				
				//등록을 실패한 경우 오류
				if(cnt <= 0) {
					throw new BizException(ErrorCode.ERR009, errKey);
				}
			}
			resultMap.put("svcNm", "uploadEfine");
			
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
	 * @author 범칙금관리 업로드(한도공)
	 * @param  fineMngeVO
	 * @return resultVO
	 * @throws Exception
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/uploadEx")
	public ResponseEntity<ResultVO> uploadEx(@RequestBody List<Map<String, String>> requestParams) throws Exception{
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
				//화면에서 넘어온 데이터 VO 세팅
				String docFineNo1 = list.get("docFineNo1");						//문서범칙금번호1(no)
				String vltDt = list.get("vltDt").replaceAll("-", "");			//위반일자
				String vltAtime = list.get("vltAtime").replaceAll(":", "");		//위반시각
				String vltPnt = list.get("vltPnt");								//위반장소
				String vltCts = list.get("vltCts");								//위반내용
				String vhclNo = list.get("vhclNo");								//차량번호
				String fineAmt = list.get("fineAmt").replaceAll("[^0-9]","");	//범칙금금액
				String sendPlcNm = "한국도로공사";									//발송처명(발송처코드 매핑용)
				
				fineMngeVO.setDocFineNo1(docFineNo1);
				fineMngeVO.setVltDt(vltDt);
				fineMngeVO.setVltAtime(vltAtime);
				fineMngeVO.setVltPnt(vltPnt);
				fineMngeVO.setVltCts(vltCts);
				fineMngeVO.setVhclNo(vhclNo);
				fineMngeVO.setFineAmt(fineAmt);
				fineMngeVO.setSendPlcNm(sendPlcNm);
				
				String errKey = "\n(차량번호: " + vhclNo + " / 위반일자: " + vltDt + " / 위반시각: " + vltAtime + ")";
				
				//차량번호로 대출정보 유효성 검사
				fineMngeService.checkVhclNoLoanInf(fineMngeVO, errKey);
				
				//한도공은 '통행료미납' 고정이며 DB 위반내용엔 다른 형식이 들어가므로 별도의 VO를 만들어 위반종류 코드를 조회한다.
				FineMngeVO vltKindCdVO = new FineMngeVO();
				vltKindCdVO.setVltCts("통행료");
				
				//위반종류 코드 매핑
				String vltKindCd = fineMngeService.retrieveVltKindCd(vltKindCdVO);
				
				fineMngeVO.setVltKindCd(vltKindCd);
				
				//발송처 코드 매핑
				List<FineMngeVO> sendplcData = fineMngeService.retrieveSendPlcCd(fineMngeVO);
				
				fineMngeVO.setSendPlcCd(sendplcData.get(0).getSendPlcCd()); //발송처코드
				fineMngeVO.setSendPlcSeq(sendplcData.get(0).getSendPlcSeq()); //발송처일련번호
				
				//추가 파라미터 세팅
				fineMngeVO.setRcptDt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))); //접수일자(현재날짜)
				fineMngeVO.setNtcdocKindCd("1"); //고지서종류코드(1:위반사실확인서)
				fineMngeVO.setFineUploadCd("2"); //범칙금업로드코드(2:한국도로공사)
				
				//범칙금관리 등록 서비스 호출
				int cnt = fineMngeService.insertFine(fineMngeVO, errKey);
				
				//등록을 실패한 경우 오류
				if(cnt <= 0) {
					throw new BizException(ErrorCode.ERR009, errKey);
				}
			}
			resultMap.put("svcNm", "uploadEx");
			
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
	 * @author 범칙금관리 업로드(위택스)
	 * @param  fineMngeVO
	 * @return resultVO
	 * @throws Exception
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/uploadWetax")
	public ResponseEntity<ResultVO> uploadWetax(@RequestBody List<Map<String, String>> requestParams) throws Exception{
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
				//화면에서 넘어온 데이터 VO 세팅
				String sendPlcNm = list.get("sendPlcNm").replaceAll(" ", "");	//발송처명(발송처코드 매핑용)
				String vltCts = list.get("vltCts");								//위반내용
				String docFineNo1 = list.get("docFineNo1");						//문서범칙금번호1(전자납부번호)
				String pymtDdayDt = list.get("pymtDdayDt");						//납부기한일자
				String fineAmt = list.get("fineAmt").replaceAll("[^0-9]","");	//범칙금금액
				String vhclNo = list.get("vhclNo");								//차량번호
				String vltDt = list.get("vltDt").replaceAll("-", "");			//위반일자
				String vltAtime = list.get("vltAtime").replaceAll(":", "");		//위반시각
				String vltPnt = list.get("vltPnt");								//위반장소
				
				fineMngeVO.setSendPlcNm(sendPlcNm);
				fineMngeVO.setVltCts(vltCts);
				fineMngeVO.setDocFineNo1(docFineNo1);
				fineMngeVO.setPymtDdayDt(pymtDdayDt);
				fineMngeVO.setFineAmt(fineAmt);
				fineMngeVO.setVhclNo(vhclNo);
				fineMngeVO.setVltDt(vltDt);
				fineMngeVO.setVltAtime(vltAtime);
				fineMngeVO.setVltPnt(vltPnt);
				
				String errKey = "\n(차량번호: " + vhclNo + " / 위반일자: " + vltDt + " / 위반시각: " + vltAtime + ")";
				
				//차량번호로 대출정보 유효성 검사
				fineMngeService.checkVhclNoLoanInf(fineMngeVO, errKey);
				
				//위반종류 코드 매핑
				String vltKindCd = fineMngeService.retrieveVltKindCd(fineMngeVO);
				
				fineMngeVO.setVltKindCd(vltKindCd);
				
				//발송처 코드 매핑
				List<FineMngeVO> sendplcData = fineMngeService.retrieveSendPlcCd(fineMngeVO);
				
				fineMngeVO.setSendPlcCd(sendplcData.get(0).getSendPlcCd()); //발송처코드
				fineMngeVO.setSendPlcSeq(sendplcData.get(0).getSendPlcSeq()); //발송처일련번호
				
				//추가 파라미터 세팅
				fineMngeVO.setRcptDt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))); //접수일자(현재날짜)
				fineMngeVO.setNtcdocKindCd("1"); //고지서종류코드(1:위반사실확인서)
				fineMngeVO.setFineUploadCd("3"); //범칙금업로드코드(3:위택스)
				fineMngeVO.setDocFineNo2(String.valueOf(i+1)); //문서범칙금번호2(순번)
				
				//범칙금관리 등록 서비스 호출
				int cnt = fineMngeService.insertFine(fineMngeVO, errKey);
				
				//등록을 실패한 경우 오류
				if(cnt <= 0) {
					throw new BizException(ErrorCode.ERR009, errKey);
				}
			}
			resultMap.put("svcNm", "uploadWetax");
			
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
	 * @author 범칙금관리 업로드(OCR)
	 * @param  fineMngeVO
	 * @return resultVO
	 * @throws Exception
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/uploadOCR")
	public ResponseEntity<ResultVO> uploadOCR(@RequestBody List<Map<String, String>> requestParams) throws Exception{
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
				//화면에서 넘어온 데이터 VO 세팅
				String docFineNo1 = list.get("docFineNo1");						//문서범칙금번호1(순번)
				String vhclNo = list.get("vhclNo");								//차량번호
				String fineAmt = list.get("fineAmt").replaceAll("[^0-9]","");	//범칙금금액
				String vltDt = list.get("vltDt").replaceAll("-", "");			//위반일자
				String vltAtime = list.get("vltAtime").replaceAll(":", "");		//위반시각
				String vltCts = list.get("vltCts");								//위반내용
				String vltPnt = list.get("vltPnt");								//위반장소
				String docFineNo2 = list.get("docFineNo2");						//문서범칙금번호2(요청번호)
				String sendPlcNm = list.get("sendPlcNm");						//발송처명(발송처코드 매핑용)
				String pymtDdayDt = list.get("pymtDdayDt").replaceAll("-", "");	//납부기한일자
				String actBankNm1 = list.get("actBankNm1");						//계좌은행명1
				String actNo1 = list.get("actNo1");								//계좌번호1
				
				fineMngeVO.setDocFineNo1(docFineNo1);
				fineMngeVO.setVhclNo(vhclNo);
				fineMngeVO.setFineAmt(fineAmt);
				fineMngeVO.setVltDt(vltDt);
				fineMngeVO.setVltAtime(vltAtime);
				fineMngeVO.setVltCts(vltCts + " " + vltPnt); //발송처 검색을 위해 위반장소까지 세팅 후 후반에 위반내용만 재세팅 한다.
				fineMngeVO.setVltPnt(vltPnt);
				fineMngeVO.setDocFineNo2(docFineNo2);
				fineMngeVO.setSendPlcNm(sendPlcNm);
				fineMngeVO.setPymtDdayDt(pymtDdayDt);
				fineMngeVO.setActBankNm1(actBankNm1);
				fineMngeVO.setActNo1(actNo1);
				
				String errKey = "\n(차량번호: " + vhclNo + " / 위반일자: " + vltDt + " / 위반시각: " + vltAtime + ")";
				
				//차량번호로 대출정보 유효성 검사
				fineMngeService.checkVhclNoLoanInf(fineMngeVO, errKey);
				
				//위반종류 코드 매핑
				String vltKindCd = fineMngeService.retrieveVltKindCd(fineMngeVO);
				
				fineMngeVO.setVltKindCd(vltKindCd);
				
				//발송처 코드 매핑
				List<FineMngeVO> sendplcData = fineMngeService.retrieveSendPlcCd(fineMngeVO);
				
				fineMngeVO.setSendPlcCd(sendplcData.get(0).getSendPlcCd()); //발송처코드
				fineMngeVO.setSendPlcSeq(sendplcData.get(0).getSendPlcSeq()); //발송처일련번호
				fineMngeVO.setVltCts(vltCts);

				//추가 파라미터 세팅
				//원랜 관공서명에 '경찰서', '경찰청'이 들어갈 경우 업로드코드를 이파인 업로드로 세팅하는 조건이 있긴 한데 이건 추후 따져봐야 할듯. (20240827)
				fineMngeVO.setRcptDt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))); //접수일자(현재날짜)
				fineMngeVO.setNtcdocKindCd("1"); //고지서종류코드(1:위반사실확인서)
				fineMngeVO.setFineUploadCd("4"); //범칙금업로드코드(4:OCR)
				
				//범칙금관리 등록 서비스 호출
				int cnt = fineMngeService.insertFine(fineMngeVO, errKey);
				
				//등록을 실패한 경우 오류
				if(cnt <= 0) {
					throw new BizException(ErrorCode.ERR009, errKey);
				}
			}
			resultMap.put("svcNm", "uploadOCR");
			
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
	 * @author 범칙금관리 업로드(카택스)
	 * @param  fineMngeVO
	 * @return resultVO
	 * @throws Exception
	 */
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공"),
			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
	})
	@PostMapping(value = "/uploadCartax")
	public ResponseEntity<ResultVO> uploadCartax(@RequestBody List<Map<String, String>> requestParams) throws Exception{
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
				//화면에서 넘어온 데이터 VO 세팅
				String docFineNo1 = list.get("docFineNo1");						//문서범칙금번호1(no)
				String sendPlcNm = list.get("sendPlcNm");						//발송처명(발송처코드 매핑용)
				String vltCts = list.get("vltCts");								//위반내용
				String vhclNo = list.get("vhclNo");								//차량번호
				String docFineNo2 = list.get("docFineNo2");						//문서범칙금번호2(회사명 / 고지날짜)
				String vltDt = list.get("vltDt").replaceAll("-", "");			//위반일자
				String vltAtime = list.get("vltAtime").replaceAll(":", "");		//위반시각
				String pymtDdayDt = list.get("pymtDdayDt").replaceAll("-","");	//납부기한일자
				String fineAmt = list.get("fineAmt").replaceAll("[^0-9]","");	//범칙금금액
				String vltPnt = list.get("vltPnt");								//위반장소
				
				fineMngeVO.setDocFineNo1(docFineNo1);
				fineMngeVO.setSendPlcNm(sendPlcNm);
				fineMngeVO.setVltCts(vltCts);
				fineMngeVO.setVhclNo(vhclNo);
				fineMngeVO.setDocFineNo2(docFineNo2);
				fineMngeVO.setVltDt(vltDt);
				fineMngeVO.setVltAtime(vltAtime);
				fineMngeVO.setPymtDdayDt(pymtDdayDt);
				fineMngeVO.setFineAmt(fineAmt);
				fineMngeVO.setVltPnt(vltPnt);
				
				String errKey = "\n(차량번호: " + vhclNo + " / 위반일자: " + vltDt + " / 위반시각: " + vltAtime + ")";
				
				//차량번호로 대출정보 유효성 검사
				fineMngeService.checkVhclNoLoanInf(fineMngeVO, errKey);
				
				//카택스는 '버스전용차로위반'과 '전용차로위반'을 제외한 나머지는 '주정차위반'으로 설정하기 때문에 별도의 VO를 만들어 위반종류 코드를 조회한다.
				FineMngeVO vltKindCdVO = new FineMngeVO();
				if(!vltCts.contains("전용차로위반")) {
					vltKindCdVO.setVltCts("주정차위반");
				} else {
					vltKindCdVO.setVltCts(vltCts);
				}
				
				//위반종류 코드 매핑
				String vltKindCd = fineMngeService.retrieveVltKindCd(vltKindCdVO);
				
				fineMngeVO.setVltKindCd(vltKindCd);
				
				//발송처 코드 매핑
				List<FineMngeVO> sendplcData = fineMngeService.retrieveSendPlcCd(fineMngeVO);
				
				fineMngeVO.setSendPlcCd(sendplcData.get(0).getSendPlcCd()); //발송처코드
				fineMngeVO.setSendPlcSeq(sendplcData.get(0).getSendPlcSeq()); //발송처일련번호
				
				//추가 파라미터 세팅
				fineMngeVO.setRcptDt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))); //접수일자(현재날짜)
				fineMngeVO.setNtcdocKindCd("1"); //고지서종류코드(1:위반사실확인서)
				fineMngeVO.setFineUploadCd("5"); //범칙금업로드코드(5:카택스)
				
				//범칙금관리 등록 서비스 호출
				int cnt = fineMngeService.insertFine(fineMngeVO, errKey);
				
				//등록을 실패한 경우 오류
				if(cnt <= 0) {
					throw new BizException(ErrorCode.ERR009, errKey);
				}
			}
			resultMap.put("svcNm", "uploadCartax");
			
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
	 * @author 범칙금관리 다운로드(이파인)
	 * @param  fineMngeVO
	 * @return resultVO
	 * @throws Exception
	 */
//	@ApiResponses(value = {
//			@ApiResponse(responseCode = "200", description = "조회 성공"),
//			@ApiResponse(responseCode = "403", description = "인가된 사용자가 아님")
//	})
//	@PostMapping(value = "/downloadEfine")
//	public ResponseEntity<ResultVO> downloadEfine(@RequestBody List<Map<String, String>> requestParams) throws Exception{
//		FineMngeVO fineMngeVO = new FineMngeVO();
//		ResultVO resultVO = new ResultVO();
//		List<Map<String, String>> resultList = new Has
//		
//		try {
//			LoginVO loginVO = null;
//			
//			//로그인 여부 확인
////			Boolean isLogin = EgovUserDetailsHelper.isAuthenticated();
////			
////			if(isLogin) {
////				//사용자 정보 세팅
////				loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
////				fineMngeVO.setUserId(loginVO.getId());
////				fineMngeVO.setUserIp(loginVO.getIp());
////			} else {
////				throw new BizException(ErrorCode.ERR300, "");
////			}
//			for(int i=0; i<requestParams.size(); i++) {
//				Map<String, String> list = requestParams.get(i);
//				//화면에서 넘어온 데이터 VO 세팅
//				String vltDt = list.get("vlt_dt");		//위반일자
//				String vltAtime = list.get("vlt_atime");//위반시각
//				String vhclNo = list.get("vhcl_no");	//차량번호
//				String fineSeq = list.get("fine_seq");	//범칙금일련번호
//				
//				//삭제용 VO 세팅
//				fineMngeVO.setVltDt(vltDt);
//				fineMngeVO.setVltAtime(vltAtime);
//				fineMngeVO.setVhclNo(vhclNo);
//				fineMngeVO.setFineSeq(fineSeq);
//				
//				//유효한 데이터인지 확인용 VO 세팅
//				fineMngeVO.setInVltDtStrt(vltDt);
//				fineMngeVO.setInVltDtEnd(vltDt);
//	
//				String errKey = "\n(차량번호: " + vhclNo + " / 위반일자: " + vltDt + " / 위반시각: " + vltAtime + ")";
//				
//				//범칙금 내역 존재 여부 확인
////				fineMngeService.checkVhclNoLoanInf(fineMngeVO, errKey);
//				
//				//다운로드 데이터 조회
//				Map<String, Object> resultMap = fineMngeService.retrieveFineMnge(fineMngeVO);
//				resultList.add(resultMap);
//			}
//			
//			resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
//			resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
//			resultVO.setResult(resultMap);
//			return ResponseEntity.ok(resultVO);
//		} catch (BizException e) {
//			e.printStackTrace();
//			resultMap.put("errMsg", e.getMessage());
//			resultVO.setResult(resultMap);
//			return ResponseEntity.status(400).body(resultVO);
//		} catch (Exception e) {
//			e.printStackTrace();
//			resultMap.put("errMsg", ErrorCode.ERR000.getMessage());
//			resultVO.setResult(resultMap);
//			return ResponseEntity.status(400).body(resultVO);
//		}
//	}
}