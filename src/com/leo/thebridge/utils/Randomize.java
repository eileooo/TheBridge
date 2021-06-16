package com.leo.thebridge.utils;

import java.util.Random;

public class Randomize {
	
	public static String getRandomID() {
		Random random = new Random();
		int number = random.nextInt(999) + 100;
		return "b" + number;
	}

}
