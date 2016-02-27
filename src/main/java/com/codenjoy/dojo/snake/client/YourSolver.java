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

        List<Point> point_snake_body = board.getSnake();

        int x = point_snake.getX();
        int y = point_snake.getY();

        int t = point_snake_body.size();
        if (t > 1) {
            x = point_snake_body.get(t - 1).getX();
            y = point_snake_body.get(t - 1).getY();
        }

        String result = Direction.UP.toString();

        if (point_snake.getX() > point_app.getX()) {
            if (point_snake.getX() - 1 != y) {
                result = Direction.LEFT.toString();
            }
        }

        if (point_snake.getX() < point_app.getX()){
            if (point_snake.getX() + 1 != y) {
                result =  Direction.RIGHT.toString();
            }
        }

        if (point_snake.getY() > point_app.getY()) {
            if (point_snake.getY() - 1 != y) {
                result = Direction.UP.toString();
            }
        }

        if (point_snake.getY() < point_app.getY()) {
            if (point_snake.getY() + 1 != y) {
                result = Direction.DOWN.toString();
            }
        }

        if (point_app_kek.getX() == point_snake.getX() &&
                !(Math.abs(point_app.getY()-point_snake.getY())<Math.abs(point_app.getY()-point_app_kek.getY())) &&
                (board.getField()[point_snake.getX()][point_snake.getY()] == Elements.HEAD_UP.ch() ||
                        board.getField()[point_snake.getX()][point_snake.getY()] == Elements.HEAD_DOWN.ch())) {
            if (board.getField()[point_snake.getX() + 1][point_snake.getY()] != Elements.BREAK.ch() && point_snake.getX() < point_app.getX()) {
                result = Direction.RIGHT.toString();
            } else {
                result = Direction.LEFT.toString();
            }
        }

        if (point_app_kek.getY() == point_snake.getY() &&
                !(Math.abs(point_app.getX()-point_snake.getX())<Math.abs(point_app.getX()-point_app_kek.getX())) &&
                (board.getField()[point_snake.getX()][point_snake.getY()] == Elements.HEAD_RIGHT.ch() ||
                        board.getField()[point_snake.getX()][point_snake.getY()] == Elements.HEAD_LEFT.ch())) {

            if (board.getField()[point_snake.getX()][point_snake.getY() - 1] != Elements.BREAK.ch() && point_snake.getY() > point_app.getY()) {
                result = Direction.UP.toString();
            } else {
                result = Direction.DOWN.toString();
            }
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
