package utils;

public class Util {
    /**
	 * Returns a random integer min <= int <= max.
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomNumber(int min, int max) {
		return (int)Math.round((Math.random() * (max - min) + min));
	}
}
