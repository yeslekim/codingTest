package com.example.codingTest.programmers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class LottoTest {

	
	@Test
	public void 로또의최고순위와최저순위() {
		int[] lottos = {44, 1, 0, 0, 31, 25};
		int[] win_nums = {31, 10, 45, 1, 6, 19};
		
		int[] answer = new int[2];
		
		// 일치하는 숫자 개수
		List<Integer> lottosList = Arrays.stream(lottos).boxed().collect(Collectors.toList());
		int cnt = 0;
		for (int i = 0; i < win_nums.length; i++) {
			if(lottosList.contains(win_nums[i])) {
				cnt ++;
			}
		}
		
		// 0 개수 체크 후 순위 만들기
		int zeroCnt = (int) lottosList.stream().filter(s -> s == 0).count();
		
		// 순위는 6(1등), 5(2등), 4(3등), 3(4등), 2(5등), 그외(6등)
		answer[0] = (7 - (cnt + zeroCnt)) > 5 ? 6 : (7 - (cnt + zeroCnt)); 
		answer[1] = (7 - cnt) > 5 ? 6 : (7 - cnt); 
		
		// {3,5}
		System.out.println(answer[0] + ", " + answer[1]);
	}
}
