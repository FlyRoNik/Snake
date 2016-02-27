package com.codenjoy.dojo.snake.client;


import com.codenjoy.dojo.client.Direction;
import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.RandomDice;
import com.codenjoy.dojo.snake.model.Elements;

import java.util.List;

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
        Point point_app_kek = board.getStones().get(0);

//        List<Point> point_snake_body = board.getSnake();

//        int t = point_snake_body.size();

        String result = Direction.UP.toString();

        if (point_snake.getX() > point_app.getX()) {
            result = Direction.LEFT.toString();
        }

        if (point_snake.getX() < point_app.getX()){
            result =  Direction.RIGHT.toString();
        }

        if (point_snake.getY() > point_app.getY()) {
            result = Direction.UP.toString();
        }

        if (point_snake.getY() < point_app.getY()) {
            result = Direction.DOWN.toString();
        }

        if (point_app_kek.getX() == point_snake.getX()) {
            result = Direction.RIGHT.toString();
        }

        if (point_app_kek.getY() == point_snake.getY()) {
            result = Direction.UP.toString();
        }

        return result;
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
