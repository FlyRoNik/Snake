package com.codenjoy.dojo.snake.client;


import com.codenjoy.dojo.client.Direction;
import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.RandomDice;
import com.codenjoy.dojo.snake.model.Elements;

/**
 * User: your name
 */
public class YourSolver implements Solver<Board> {

    private static final String USER_NAME = "hang.glider.viru007@gmail.com";

    private Dice dice;
    private Board board;

    public YourSolver(Dice dice) {
        this.dice = dice;
    }

    @Override
    public String get(Board board) {
        this.board = board;

        Point point_app = board.getApples().get(0);
        Point point_snake = board.getHead();

        if (point_snake.getX() > point_app.getX()) {
            return Direction.LEFT.toString();
        }

        if (point_snake.getX() < point_app.getX()){
            return Direction.RIGHT.toString();
        }

        if (point_snake.getY() > point_app.getY()) {
            return Direction.UP.toString();
        }

        if (point_snake.getY() < point_app.getY()) {
            return Direction.DOWN.toString();
        }

        return Direction.UP.toString();
    }

    public static void main(String[] args) {
        start(USER_NAME, WebSocketRunner.Host.REMOTE);
    }

    public static void start(String name, WebSocketRunner.Host server) {
        try {
            WebSocketRunner.run(server, name,
                    new YourSolver(new RandomDice()),
                    new Board());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
