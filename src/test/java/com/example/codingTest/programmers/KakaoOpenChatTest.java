package com.example.codingTest.programmers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class KakaoOpenChatTest {

	@Test
	public void 오픈채팅방() {
		String[] record = {"Enter uid1234 Muzi", "Enter uid4567 Prodo","Leave uid1234","Enter uid1234 Prodo","Change uid4567 Ryan", "Change uid4567 Muzi"};
		String[] answer = {};
		
		Map<String, String> nickName = new HashMap<>();	// 마지막 nickName 저장
		for (int i = 0; i < record.length; i++) {
			String[] splitRecord = record[i].split(" ");
			String uid = splitRecord[1];
			if(!splitRecord[0].equals("Leave")) {
				nickName.put(uid, splitRecord[2]);
			}
		}
		List<String> list = new ArrayList<>();
		
		for (int i = 0; i < record.length; i++) {
			String[] splitRecord = record[i].split(" ");
			String uid = splitRecord[1];
			
			if(splitRecord[0].equals("Enter")) {
				list.add(nickName.get(uid) + "님이 들어왔습니다.");
			}
			else if(splitRecord[0].equals("Leave")) {
				list.add(nickName.get(uid) + "님이 나갔습니다.");
			}
		}
		answer = list.toArray(new String[list.size()]);
		
		System.out.println(answer);
	}
}
