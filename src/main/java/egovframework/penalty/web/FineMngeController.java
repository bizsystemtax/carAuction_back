package egovframework.penalty.web;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.ResponseCode;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
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
	public ResultVO selectComboBoxList() throws Exception {
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
	public ResultVO selectFineMnge(@RequestBody Map<String, String> requestParams) throws Exception{
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
	public ResultVO updateCfmtStat(@RequestBody List<Map<String, String>> requestParams) throws Exception{
		FineMngeVO fineMngeVO = new FineMngeVO();
		ResultVO resultVO = new ResultVO();
		Map<String, Object> resultMap = new HashMap<>();
		
		try {
			//사용자 정보 세팅
			LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			fineMngeVO.setUserId(loginVO.getId());
			fineMngeVO.setUserIp(loginVO.getIp());
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("errMsg", "사용자 정보를 불러오는 중 오류가 발생했습니다.");
			resultVO.setResult(resultMap);
			return resultVO;
		}

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

			String errKey = "(위반일자: " + vltDt + " / 위반시각: " + vltAtime + " / 차량번호: " + vhclNo + ")";
			
			try {
				//범칙금관리 조회 서비스 호출
				Map<String, Object> fineData = fineMngeService.retrieveFineMnge(fineMngeVO);

				//조회되지 않을 경우 오류
				if(fineData.isEmpty() || fineData == null) {
					throw new Exception("범칙금 유효성 확인 중 오류가 발생했습니다.\n" + errKey);
				}
				//이미 확정된 경우 오류
				else if(fineData.get("cfmt_dt") != "") {
					throw new Exception("이미 확정된 범칙금 내역입니다.\n" + errKey);
				}
			} catch (Exception e) {
				e.printStackTrace();
				resultMap.put("errMsg", e.getMessage());
				resultVO.setResult(resultMap);
				return resultVO;
			}
			
			try {
				//범칙금관리 확정 상태 업데이트 서비스 호출
				fineMngeService.updateCfmtStat(fineMngeVO);
			} catch (Exception e) {
				e.printStackTrace();
				resultMap.put("errMsg", "범칙금 확정 처리 중 오류가 발생했습니다.\n" + errKey);
				resultVO.setResult(resultMap);
				return resultVO;
			}
		}
		resultMap.put("svcNm", "cfmt");
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);
		
		return resultVO;
	}
}
