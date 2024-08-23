package egovframework.penalty.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.exception.BizException;
import egovframework.com.cmm.exception.ErrorCode;
import egovframework.penalty.ComnCdVO;
import egovframework.penalty.FineMngeVO;
import egovframework.penalty.service.FineMngeService;
          
@Service("FineMngeService")
public class FineMngeServiceImpl extends EgovAbstractServiceImpl implements FineMngeService {
	@Resource(name = "FineMngeDAO")
	private FineMngeDAO FineMngeDAO;
	
	//위반종류코드 키워드 리스트 (순서 중요)
	private static final Map<String, String> vltKindCdKeywordMap = new LinkedHashMap<>();
	static {
        vltKindCdKeywordMap.put("노인", "노인");
        vltKindCdKeywordMap.put("폐기물", "폐기물");
        vltKindCdKeywordMap.put("소방", "소방");
        vltKindCdKeywordMap.put("속도위반", "속도위반");
        vltKindCdKeywordMap.put("친환경", "친환경");
        vltKindCdKeywordMap.put("특별", "특별");
        vltKindCdKeywordMap.put("주정차", "주정차");
        vltKindCdKeywordMap.put("신호", "신호");
        vltKindCdKeywordMap.put("버스", "버스");
        vltKindCdKeywordMap.put("전용차로", "전용차로");
        vltKindCdKeywordMap.put("혼잡", "혼잡");
        vltKindCdKeywordMap.put("요금", "요금");
        vltKindCdKeywordMap.put("통행료", "통행료");
        vltKindCdKeywordMap.put("책임", "책임");
        vltKindCdKeywordMap.put("검사", "검사");
        vltKindCdKeywordMap.put("쓰레기", "쓰레기");
        vltKindCdKeywordMap.put("무단", "무단");
        vltKindCdKeywordMap.put("통행구분", "통행구분");
        vltKindCdKeywordMap.put("끼어들기", "끼어들기");
        vltKindCdKeywordMap.put("진로변경", "진로변경");
    }
	
	//범칙금관리 검색조건 콤보박스 조회
	@Override
	public Map<String, Object> retrieveComboBoxList(ComnCdVO comnCdVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", FineMngeDAO.retrieveComboBoxList(comnCdVO));
		
		return map;
	}
	
	//범칙금관리 목록 조회
	@Override
	public Map<String, Object> retrieveFineMnge(FineMngeVO fineMngeVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", FineMngeDAO.retrieveFineMnge(fineMngeVO));
		
		return map;
	}

	//범칙금관리 확정 상태 업데이트
	@Override
	public int updateCfmtStat(FineMngeVO fineMngeVO) throws Exception {
		int cnt = FineMngeDAO.updateCfmtStat(fineMngeVO);
		
		return cnt;
	}

	//발송처부서명 목록 조회
	@Override
	public Map<String, Object> retrieveSendPlcDeptList(FineMngeVO fineMngeVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", FineMngeDAO.retrieveSendPlcDeptList(fineMngeVO));
		
		return map;
	}

	//범칙금 상태 유효성 검사
	@Override
	public void checkFineStat(FineMngeVO fineMngeVO, String errKey) throws Exception {
		//범칙금관리 조회 서비스 호출
		Map<String, Object> fineDataList = retrieveFineMnge(fineMngeVO);
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
	}
	
	//범칙금관리 등록
	@Override
	public int insertFine(FineMngeVO fineMngeVO, String errKey) throws Exception {
		//범칙금 중복 여부 확인
		//기본적인 등록 서비스에선 차량번호, 위반일자와 위반시각의 범칙금을 중복으로 등록할 수 없다.
		/*해지정산금 또는 가산세 등의 사후 처리가 필요할 경우(필요시 개발)
		 * 1.화면에서 단일 행 체크 후 분할 버튼 클릭(미확정 상태만 가능)
		 * 2.모달에서 원하는 분할금액 입력
		 * 3.기존 범칙금 내역의 범칙금액 수정 (범칙금액-분할금액)
		 * 4.신규 범칙금 내역은 기존 범칙금 내역을 복사한 후 범칙금액을 분할금액으로 세팅
		 * # 분할 확인용 상태 코드 필요 시 범칙금구분코드(FINE_DIV_CD) 생성하여 코드값은 상황에 맞게 추가 (ex| 1:범칙금, 2:해지정산금, 3:가산세)
		 */
		FineMngeVO dupeCheck = new FineMngeVO();
		dupeCheck.setVltDt(fineMngeVO.getVltDt());//위반일자
		dupeCheck.setVltAtime(fineMngeVO.getVltAtime());//위반시각
		dupeCheck.setVhclNo(fineMngeVO.getVhclNo());//차량번호
		
		//범칙금관리 조회 서비스 호출
		List<FineMngeVO> fineDupeCehck = FineMngeDAO.retrieveFineDupeCheck(dupeCheck);
		
		//중복일 경우 오류
		if(fineDupeCehck != null && fineDupeCehck.size() > 0) {
			throw new BizException(ErrorCode.ERR010, errKey);
		}
		
		//범칙금일련번호 채번
		List<FineMngeVO> fineSeq = FineMngeDAO.retrieveFineSeqSN(fineMngeVO);
		
		//채번된 일련번호가 있을 경우 채번한 값 세팅
		if(fineSeq.get(0) != null) {
			fineMngeVO.setFineSeq(fineSeq.get(0).getFineSeq());
		} 
		//해당 위반일자에 내역이 없어서 채번하지 못한 경우 1로 세팅
		else {
			fineMngeVO.setFineSeq("1");
		}
		
		//BIZ_범칙금기본 등록
		int cnt = FineMngeDAO.insertFine(fineMngeVO);
		
		return cnt;
	}

	//범칙금관리 수정
	@Override
	public int updateFine(FineMngeVO fineMngeVO) throws Exception {
		//BIZ_범칙금기본 수정
		int cnt = FineMngeDAO.updateFine(fineMngeVO);
		
		return cnt;
	}

	//범칙금관리 삭제
	@Override
	public int deleteFine(FineMngeVO fineMngeVO) throws Exception {
		//BIZ_범칙금기본 삭제
		int cnt = FineMngeDAO.deleteFine(fineMngeVO);
		
		return cnt;
	}

	//차량번호로 대출정보 조회
	@Override
	public Map<String, Object> retrieveVhclNoLoanMas(FineMngeVO fineMngeVO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", FineMngeDAO.retrieveVhclNoLoanMas(fineMngeVO));
		
		return map;
	}
	
	//차량번호로 대출정보 유효성 검사
	@Override
	public void checkVhclNoLoanInf(FineMngeVO fineMngeVO, String errKey) throws Exception {
		//차량번호로 대출정보 조회
		Map<String, Object> fineDataList = retrieveVhclNoLoanMas(fineMngeVO);
		List<FineMngeVO> loanInf = (List<FineMngeVO>)fineDataList.get("resultList");

		//조회되지 않을 경우 오류
		if(loanInf.isEmpty() || loanInf == null) {
			throw new BizException(ErrorCode.ERR007, errKey);
		}
	}
	
	//위반종류코드 매핑
	@Override
	public String retrieveVltKindCd(FineMngeVO fineMngeVO) throws Exception {
		String vltCts = fineMngeVO.getVltCts(); //위반내용
		String keyWord1 = ""; //키워드1
		String keyWord2 = ""; //키워드2

		//위반내용에 따라 위반종류코드 키워드 설정
		// 복합 조건 처리
        if (vltCts.contains("고속도로")) {
        	if(vltCts.contains("버스전용차로")) {
        		keyWord1 = "고속도로";
        		keyWord2 = "버스전용차로";
        	} else if(vltCts.contains("지정차로")) {
        		keyWord1 = "고속도로";
        		keyWord2 = "지정차로";
        	} else if(vltCts.contains("갓길")) {
        		keyWord1 = "고속도로";
        		keyWord2 = "갓길";
        	}
        } else if (vltCts.contains("어린이")) {
        	if(vltCts.contains("속도위반")) {
        		keyWord1 = "어린이";
        		keyWord2 = "속도위반";
        	} else if(vltCts.contains("신호")) {
        		keyWord1 = "어린이";
        		keyWord2 = "신호";
        	} else if(vltCts.contains("지시")) {
        		keyWord1 = "어린이";
        		keyWord2 = "지시";
        	}
        } else if (vltCts.contains("장애인")) {
        	if(vltCts.contains("자동차")) {
        		keyWord1 = "장애인";
        		keyWord2 = "자동차";
        	} else if(vltCts.contains("표지")) {
        		keyWord1 = "장애인";
        		keyWord2 = "표지";
        	} else if(vltCts.contains("주차구역")) {
        		keyWord1 = "장애인";
        		keyWord2 = "주차구역";
        	} else if(vltCts.contains("방해")) {
        		keyWord1 = "장애인";
        		keyWord2 = "방해";
        	} else if(vltCts.contains("보호구역")) {
        		keyWord1 = "장애인";
        		keyWord2 = "보호구역";
        	} else if(vltCts.contains("속도위반")) {
        		keyWord1 = "장애인";
        		keyWord2 = "속도위반";
        	}
        } else {
            // 기본 키워드 처리
        	for (Map.Entry<String, String> entry : vltKindCdKeywordMap.entrySet()) {
                if (vltCts.contains(entry.getKey())) {
                	keyWord1 = entry.getValue();
                }
            }
            
        }
		
        //설정된 키워드가 없을 경우 '기타'로 세팅
        if (keyWord1.isEmpty()) {
        	keyWord1 = "기타";
        }
        
        //키워드를 VO에 세팅
		fineMngeVO.setVltKindCdKey1(keyWord1);
		fineMngeVO.setVltKindCdKey2(keyWord2);
		
		//위반종류 코드 조회
		List<FineMngeVO> vltKindCd = FineMngeDAO.retrieveVltKindCd(fineMngeVO);
		String result = Objects.toString(vltKindCd.get(0).getVltKindCd(), "");
		
		//서버 오류로 인해 코드값을 조회 못했을 경우 오류
		if(result.isEmpty()) {
			throw new BizException(ErrorCode.ERR008, "");
		}
		
		return result;
	}
	
	//발송처코드 매핑
	@Override
	public List<FineMngeVO> retrieveSendPlcCd(FineMngeVO fineMngeVO) throws Exception {
		String sendPlcNm = fineMngeVO.getSendPlcNm(); //발송처명(매핑용)
		String sendPlcCdKey = sendPlcNm; //발송처 매핑 키
		
		//발송처 매핑 키 설정 (선가공)
		if(sendPlcNm.isEmpty()) {
			//미입력 -> '기타'
			sendPlcCdKey = "기타";
		} else if(sendPlcNm.contains("양주시청")) {
			//'양주시청' -> '경기도양주시'
			sendPlcCdKey = "경기도양주시";
		} else if(sendPlcNm.equals("시청")) {
			//'시청' -> '서울시청'
			sendPlcCdKey = "서울시청";
		} else if(sendPlcNm.contains("시청") && !sendPlcNm.contains("부산") && !sendPlcNm.contains("대구") && !sendPlcNm.contains("서울") && !sendPlcNm.contains("인천")) {
			//'OO시청' -> 'OO시' ('부산시청', '대구시청', '서울시청', '인천시청' 제외)
			sendPlcCdKey = sendPlcNm.substring(0, sendPlcNm.indexOf("시청")+1);
		} else if(sendPlcNm.contains("고양") && sendPlcNm.endsWith("구청") && !sendPlcNm.contains("고양시")) {
			//'OO고양구청' -> 'OO고양시구청'
			int endIdx = sendPlcNm.indexOf("고양")+2;
			sendPlcCdKey = sendPlcNm.substring(0, endIdx) + "시" + sendPlcNm.substring(endIdx);
		} else if(sendPlcNm.equals("보은군수")) {
			//'보은군수' -> '보은군'
			sendPlcCdKey = "보은군";
		} else if(sendPlcNm.endsWith("군청")) {
			//'OO군청' -> 'OO군'
			sendPlcCdKey = sendPlcNm.substring(0, sendPlcNm.indexOf("군청")+1);
		} else if(sendPlcNm.contains("평택종합관제사업소")) {
			//'O평택종합관제사업소O' -> '경기도평택시'
			sendPlcCdKey = "경기도평택시";
		} else if(sendPlcNm.contains("울산중구") && sendPlcNm.contains("도시공사")) {
			//'O울산중구O도시공사O' -> '울산시중구도시관리공단'
			sendPlcCdKey = "울산시중구도시관리공단";
		} else if(sendPlcNm.startsWith("충청남도")) {
			//'충청남도OO' -> '충남OO'
			sendPlcCdKey = "충남" + sendPlcNm.substring(sendPlcNm.indexOf("충청남도")+4);
		} else if(sendPlcNm.contains("호원") || sendPlcNm.contains("흥선동") || sendPlcNm.contains("송산3동")) {
			//'O호원O', 'O흥선동O', 'O송산3동O' -> '경기도의정부시'
			sendPlcCdKey = "경기도의정부시";
		} else if(sendPlcNm.equals("중구")) {
			//'중구' -> '서울중구'
			sendPlcCdKey = "서울중구";
		}
		
		//선가공 키 VO 세팅
		fineMngeVO.setSendPlcCdKey(sendPlcCdKey);
		
		//발송처코드 조회
		List<FineMngeVO> result = FineMngeDAO.retrieveSendPlcCd(fineMngeVO);

		//조회 후 결과 '기타'일 경우 가공 후 재조회
		if(result.get(0).getSendPlcNm().equals("기타")) {
			//발송처 매핑 키 설정 (후가공)
			if(sendPlcNm.endsWith("구청")) {
				//'OO구청' -> 'OO구'
				sendPlcCdKey = sendPlcNm.substring(0, sendPlcNm.indexOf("구청")+1);
			} else if(sendPlcNm.startsWith("서울")) {
				//'서울OO' -> 'OO'
				sendPlcCdKey = sendPlcNm.substring(2);
			} else if(sendPlcNm.endsWith("동단")) {
				//'OO동단' -> 'OO공단'
				sendPlcCdKey = sendPlcNm.substring(0, sendPlcNm.length()-2) + "공단";
			} else if(sendPlcNm.endsWith("주식회사")) {
				//'OO주식회사' -> 'OO'
				sendPlcCdKey = sendPlcNm.substring(0, sendPlcNm.length()-4);
			} else if(sendPlcNm.startsWith("양산") && !sendPlcNm.contains("양산시")) {
				//'양산OO' -> '양산시OO'
				sendPlcCdKey = "양산시" + sendPlcNm.substring(3);
			} else if(sendPlcNm.endsWith("시도시공사")) {
				//'OO시도시공사' -> 'OO도시공사'
				sendPlcCdKey = sendPlcNm.substring(0, sendPlcNm.indexOf("시도시공사")) + "도시공사";
			} else if(sendPlcNm.contains("남양주") && !sendPlcNm.contains("남양주시")) {
				//'O남양주O' -> 'O남양주시O'
				sendPlcCdKey = sendPlcNm.replace("남양주", "남양주시");
			} else if(sendPlcNm.endsWith("공단")) {
				//'OO공단' -> 'OO'
				sendPlcCdKey = sendPlcNm.substring(0, sendPlcNm.length()-2);
			} else if(sendPlcNm.contains("강원") && !sendPlcNm.contains("강원도")) {
				//'O강원O' -> 'O강원도O'
				sendPlcCdKey = sendPlcNm.replace("강원", "강원도");
			} else if(sendPlcNm.contains("강원도")) {
				//'O강원도O' -> 'O강원O'
				sendPlcCdKey = sendPlcNm.replace("강원도", "강원");
			} else {
				//가공할게 없으면 결과는 동일하므로 기존 값 반환
				return result;
			}
			
			//후가공 키 VO 세팅
			fineMngeVO.setSendPlcCdKey(sendPlcCdKey);

			//재조회
			result = FineMngeDAO.retrieveSendPlcCd(fineMngeVO);
		}
		
		return result;
	}
}