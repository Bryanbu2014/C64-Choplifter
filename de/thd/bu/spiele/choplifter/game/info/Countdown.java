package de.thd.bu.spiele.choplifter.game.info;

import de.thd.bu.spiele.choplifter.game.GameView;

/**
 * Countdown for the level.
 */
public class Countdown {
    private long countDownLength;
    private long timeLeft;
    private long startTime;
    private boolean stopCountdown;
    private final GameView gameView;

    /**
     * Creates a countdown for the level.
     * @param gameView GameView to print this object on.
     */
    public Countdown(GameView gameView) {
        this.gameView = gameView;
    }

    /**
     * Start count down timer.
     * @param countDownDuration Duration of the count down.
     */
    public void startCountdown(int countDownDuration) {
        this.startTime = gameView.getGameTimeInMilliseconds();
        this.countDownLength = countDownDuration * 1000L;
        this.timeLeft = this.countDownLength;
        this.stopCountdown = false;
    }

    /**
     * Stop the timer.
     */
    public void stop() {
        stopCountdown = true;
    }

    /**
     * Return the remaining time from countdown.
     * @return Remaining time from countdown.
     */
    public int getTimeLeft() {
        if (!stopCountdown && timeLeft > 0) {
            timeLeft = countDownLength - (gameView.getGameTimeInMilliseconds() - startTime);
        }
        return (int) Math.ceil(timeLeft / 1000d);
    }
}
