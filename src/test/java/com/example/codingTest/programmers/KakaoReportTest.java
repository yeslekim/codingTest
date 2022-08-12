package com.example.codingTest.programmers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class KakaoReportTest {

//	문제 설명
//	신입사원 무지는 게시판 불량 이용자를 신고하고 처리 결과를 메일로 발송하는 시스템을 개발하려 합니다. 무지가 개발하려는 시스템은 다음과 같습니다.
//
//	각 유저는 한 번에 한 명의 유저를 신고할 수 있습니다.
//	신고 횟수에 제한은 없습니다. 서로 다른 유저를 계속해서 신고할 수 있습니다.
//	한 유저를 여러 번 신고할 수도 있지만, 동일한 유저에 대한 신고 횟수는 1회로 처리됩니다.
//	k번 이상 신고된 유저는 게시판 이용이 정지되며, 해당 유저를 신고한 모든 유저에게 정지 사실을 메일로 발송합니다.
//	유저가 신고한 모든 내용을 취합하여 마지막에 한꺼번에 게시판 이용 정지를 시키면서 정지 메일을 발송합니다.
//	다음은 전체 유저 목록이 ["muzi", "frodo", "apeach", "neo"]이고, k = 2(즉, 2번 이상 신고당하면 이용 정지)인 경우의 예시입니다.
//
//	유저 ID	유저가 신고한 ID	설명
//	"muzi"	"frodo"	"muzi"가 "frodo"를 신고했습니다.
//	"apeach"	"frodo"	"apeach"가 "frodo"를 신고했습니다.
//	"frodo"	"neo"	"frodo"가 "neo"를 신고했습니다.
//	"muzi"	"neo"	"muzi"가 "neo"를 신고했습니다.
//	"apeach"	"muzi"	"apeach"가 "muzi"를 신고했습니다.
//	각 유저별로 신고당한 횟수는 다음과 같습니다.
//
//	유저 ID	신고당한 횟수
//	"muzi"	1
//	"frodo"	2
//	"apeach"	0
//	"neo"	2
//	위 예시에서는 2번 이상 신고당한 "frodo"와 "neo"의 게시판 이용이 정지됩니다. 이때, 각 유저별로 신고한 아이디와 정지된 아이디를 정리하면 다음과 같습니다.
//
//	유저 ID	유저가 신고한 ID	정지된 ID
//	"muzi"	["frodo", "neo"]	["frodo", "neo"]
//	"frodo"	["neo"]	["neo"]
//	"apeach"	["muzi", "frodo"]	["frodo"]
//	"neo"	없음	없음
//	따라서 "muzi"는 처리 결과 메일을 2회, "frodo"와 "apeach"는 각각 처리 결과 메일을 1회 받게 됩니다.
//
//	이용자의 ID가 담긴 문자열 배열 id_list, 각 이용자가 신고한 이용자의 ID 정보가 담긴 문자열 배열 report, 정지 기준이 되는 신고 횟수 k가 매개변수로 주어질 때, 각 유저별로 처리 결과 메일을 받은 횟수를 배열에 담아 return 하도록 solution 함수를 완성해주세요.
	@Test
	public void 신고결과받기() throws Exception {
		String[] id_list = {"muzi", "frodo", "apeach", "neo"};
		String[] report = {"muzi frodo","apeach frodo","frodo neo","muzi neo","apeach muzi"};
		int k = 2;
		
		int[] answer = new int[id_list.length];
		Map<String, List<String>> reportMap = new HashMap<>();		// <신고당한 사람, 신고자 list>
		Map<String, Integer> answerMap = new HashMap<>();			// <신고자, 신고자의 배열 위치값>
		
		for (int i = 0; i < id_list.length; i++) {
			answerMap.put(id_list[i], i);
			reportMap.put(id_list[i], new ArrayList<>());
		}
		
		for (int i = 0; i < report.length; i++) {
			String[] tmp = report[i].split(" ");
			
			if(!reportMap.get(tmp[1]).contains(tmp[0])) {
				reportMap.get(tmp[1]).add(tmp[0]);
			}
		}
		
		for(String key : reportMap.keySet()) {
			if(reportMap.get(key).size() >= k) {
				for(String key2 : reportMap.get(key)) {
					answer[answerMap.get(key2)] ++;
				}
			}
		}
		
		// 처리결과메일 갯수
//		[2,1,1,0]
		System.out.println(answer);
	}
	
	
	
	
	@Test
	public int[] 신고결과받기_다른사람풀이() throws Exception {
		String[] id_list = {"muzi", "frodo", "apeach", "neo"};
		String[] report = {"muzi frodo","apeach frodo","frodo neo","muzi neo","apeach muzi"};
		int k = 2;
		
		List<String> list = Arrays.stream(report).distinct().collect(Collectors.toList());
		// > stream.distnct() : 스트림에서 중복을 제거한 새로운 스트림
		// > stream collect : 스트림의 아이템들을 List 또는 Set 자료형으로 변환
		
        HashMap<String, Integer> count = new HashMap<>();
        for (String s : list) {
            String target = s.split(" ")[1];
            count.put(target, count.getOrDefault(target, 0) + 1);
            // > Map getOrDefault : 찾는 키가 존재한다면 찾는 키의 값을 반환하고 없다면 기본 값을 반환하는 메서드
        }

        return Arrays.stream(id_list).map(_user -> {
            final String user = _user;
            List<String> reportList = list.stream().filter(s -> s.startsWith(user + " ")).collect(Collectors.toList());
            return reportList.stream().filter(s -> count.getOrDefault(s.split(" ")[1], 0) >= k).count();
            // > Stream count : 조건에 해당하는 갯수
        }).mapToInt(Long::intValue).toArray();
		// > mapToInt : 스트림을 IntStream으로 변환해주는 메서드
	}
	
	@Test
	public int[] 신고결과받기_다른사람풀이2() throws Exception {
		String[] id_list = {"muzi", "frodo", "apeach", "neo"};
		String[] report = {"muzi frodo","apeach frodo","frodo neo","muzi neo","apeach muzi"};
		int k = 2;
		
		
		 int[] answer = new int[id_list.length];
	        ArrayList<User> users = new ArrayList<>();
	        HashMap<String,Integer> suspendedList = new HashMap<>(); //<이름>
	        HashMap<String,Integer> idIdx = new HashMap<String,Integer>(); // <이름, 해당 이름의 User 클래스 idx>
	        int idx = 0;

	        for(String name : id_list) {
	            idIdx.put(name,idx++);
	            users.add(new User(name));
	        }

	        for(String re : report){
	            String[] str = re.split(" ");
	            //suspendedCount.put(str[0], suspendedCount.getOrDefault(str[0],0)+1);
	            users.get( idIdx.get(str[0])).reportList.add(str[1]);
	            users.get( idIdx.get(str[1])).reportedList.add(str[0]);
	        }

	        for(User user : users){
	            if(user.reportedList.size() >= k)
	                suspendedList.put(user.name,1);
	        }

	         for(User user : users){
	             for(String nameReport : user.reportList){
	                 if(suspendedList.get(nameReport) != null){
	                     answer[idIdx.get(user.name)]++;
	                 }

	             }
	        }




	        return answer;
	}
}

class User{
	String name;
	HashSet<String> reportList;
	HashSet<String> reportedList;
	public User(String name) {
		this.name = name;
		reportList = new HashSet<>();
		reportedList = new HashSet<>();
		
	}
}

