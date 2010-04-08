package utils;
import java.util.Calendar;

public class Timer extends Thread {

	private int delay;

	public Timer(int delay) {
		this.delay = delay;
	}

	public void run() {
		long startTime = Calendar.getInstance().getTimeInMillis();
		long curTime = Calendar.getInstance().getTimeInMillis();
		while (true) {
			try {
				curTime = Calendar.getInstance().getTimeInMillis();
				if (curTime-startTime >= delay) {
					break;
				}
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
