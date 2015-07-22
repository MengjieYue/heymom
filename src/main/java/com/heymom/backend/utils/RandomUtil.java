package com.heymom.backend.utils;

import java.util.Random;

public class RandomUtil {
	private static Random random = new Random();

	public static String generate6Int() {
		return String.valueOf(random.nextInt(999999));
	}
}
