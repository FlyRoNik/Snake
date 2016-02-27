package com.codenjoy.dojo.snake.client;

import com.codenjoy.dojo.services.RandomDice;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by oleksandr.baglai on 01.12.2015.
 */
public class YourSolverTest {
    @Test
    public void testSameDirection() {
        assertB("☼☼☼☼☼☼" +
                "☼ ☻   ☼" +
                "☼ ▼  ☼" +
                "☼    ☼" +
                "☼ ☺  ☼" +
                "☼☼☼☼☼☼", "DOWN");

        assertB("☼☼☼☼☼☼" +
                "☼  ☻  ☼" +
                "☼    ☼" +
                "☼► ☺ ☼" +
                "☼    ☼" +
                "☼☼☼☼☼☼", "RIGHT");

        assertB("☼☼☼☼☼☼" +
                "☼   ☻ ☼" +
                "☼ ☺  ☼" +
                "☼    ☼" +
                "☼ ▲  ☼" +
                "☼☼☼☼☼☼", "UP");

        assertB("☼☼☼☼☼☼" +
                "☼    ☻☼" +
                "☼☺  ◄☼" +
                "☼    ☼" +
                "☼    ☼" +
                "☼☼☼☼☼☼", "LEFT");

    }

    @Test
    public void testEatMe() {
        assertB("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼╔═════════►  ☼" +
                "☼╙            ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼        ☺    ☼" +
                "☼          ☻  ☼" +
                "☼             ☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "DOWN");
    }

    @Test
    public void testEatKek() {
        assertB("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼                  ☼" +
                "☼                  ☼" +
                "☼                  ☼" +
                "☼                  ☼" +
                "☼                  ☼" +
                "☼                  ☼" +
                "☼                  ☼" +
                "☼                  ☼" +
                "☼                  ☼" +
                "☼                  ☼" +
                "☼    ☺   ☻  ◄    ☼" +
                "☼                  ☼" +
                "☼                  ☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "UP");
    }

    @Test
    public void testEatBreak() {
        assertB("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼   ☺   ☻ ◄   ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "DOWN");
    }

    @Test
    public void testEatkek1() {
        assertB("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼   ☺   ☻     ☼" +
                "☼       ▲     ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "LEFT");
    }

    @Test
    public void testEatkek2() {
        assertB("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼" +
                "☼   ☺ ◄ ☻     ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼             ☼" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼", "LEFT");
    }



    @Test
    public void testNotSameDirection() {
        assertB("☼☼☼☼☼☼" +
                "☼ ☻   ☼" +
                "☼ ►  ☼" +
                "☼    ☼" +
                "☼ ☺  ☼" +
                "☼☼☼☼☼☼", "DOWN");

        assertB("☼☼☼☼☼☼" +
                "☼ ☻   ☼" +
                "☼    ☼" +
                "☼◄ ☺ ☼" +
                "☼    ☼" +
                "☼☼☼☼☼☼", "RIGHT");

        assertB("☼☼☼☼☼☼" +
                "☼ ☻   ☼" +
                "☼ ☺  ☼" +
                "☼    ☼" +
                "☼ ►  ☼" +
                "☼☼☼☼☼☼", "UP");

        assertB("☼☼☼☼☼☼" +
                "☼   ☻ ☼" +
                "☼☺  ►☼" +
                "☼    ☼" +
                "☼    ☼" +
                "☼☼☼☼☼☼", "LEFT");

    }

    private void assertB(String boardString, String expected) {
        // given
        YourSolver solver = new YourSolver(new RandomDice());

        // when
        String direction = solver.get((Board)new Board().forString(
                boardString));

        // then
        assertEquals(expected, direction);
    }
}
