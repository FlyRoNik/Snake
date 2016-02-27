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
        if (t > 10) {
            x = point_snake_body.get(1).getX();
            y = point_snake_body.get(1).getY();
        }

        String result = "";
        char key = 0;

        if (point_snake.getX() > point_app.getX()) {
            key = 'l';
            if (board.getField()[point_snake.getX()-1][point_snake.getY()] == Elements.NONE.ch() ||
                    board.getField()[point_snake.getX()-1][point_snake.getY()] == Elements.GOOD_APPLE.ch()) {
                result = Direction.LEFT.toString();
            }
        }

        if (point_snake.getX() < point_app.getX()){
            key = 'r';
            if (board.getField()[point_snake.getX()+1][point_snake.getY()] == Elements.GOOD_APPLE.ch() ||
                    board.getField()[point_snake.getX()+1][point_snake.getY()] == Elements.NONE.ch()) {
                result =  Direction.RIGHT.toString();
            }
        }

        if (point_snake.getY() > point_app.getY()) {
            key = 'u';
            if (board.getField()[point_snake.getX()][point_snake.getY()-1] == Elements.NONE.ch()||
                    board.getField()[point_snake.getX()][point_snake.getY()-1] == Elements.GOOD_APPLE.ch()) {
                result = Direction.UP.toString();
            }
        }

        if (point_snake.getY() < point_app.getY()) {
            key = 'd';
            if (board.getField()[point_snake.getX()][point_snake.getY()+1] == Elements.NONE.ch()||
                    board.getField()[point_snake.getX()][point_snake.getY()+1] == Elements.GOOD_APPLE.ch()) {
                result = Direction.DOWN.toString();
            }
        }

//        if (point_app_kek.getX() == point_snake.getX() &&
//                !(Math.abs(point_app.getY()-point_snake.getY())<Math.abs(point_app.getY()-point_app_kek.getY())) &&
//                (board.getField()[point_snake.getX()][point_snake.getY()] == Elements.HEAD_UP.ch() ||
//                        board.getField()[point_snake.getX()][point_snake.getY()] == Elements.HEAD_DOWN.ch())) {
//            if (board.getField()[point_snake.getX() + 1][point_snake.getY()] != Elements.BREAK.ch() && point_snake.getX() < point_app.getX()) {
//                result = Direction.RIGHT.toString();
//            } else {
//                result = Direction.LEFT.toString();
//            }
//        }
//
//        if (point_app_kek.getY() == point_snake.getY() &&
//                !(Math.abs(point_app.getX()-point_snake.getX())<Math.abs(point_app.getX()-point_app_kek.getX())) &&
//                (board.getField()[point_snake.getX()][point_snake.getY()] == Elements.HEAD_RIGHT.ch() ||
//                        board.getField()[point_snake.getX()][point_snake.getY()] == Elements.HEAD_LEFT.ch())) {
//
//            if (board.getField()[point_snake.getX()][point_snake.getY() - 1] != Elements.BREAK.ch() && point_snake.getY() > point_app.getY()) {
//                result = Direction.UP.toString();
//            } else {
//                result = Direction.DOWN.toString();
//            }
//        }

        if (result == "") {
            switch (key) {
                case 'l':{
                    if (board.getField()[point_snake.getX()][point_snake.getY()-1] == Elements.NONE.ch()||
                            board.getField()[point_snake.getX()][point_snake.getY()-1] == Elements.GOOD_APPLE.ch()) {
                        result = Direction.UP.toString();
                    }else {
                        if (board.getField()[point_snake.getX()][point_snake.getY() + 1] == Elements.NONE.ch() ||
                                board.getField()[point_snake.getX()][point_snake.getY() + 1] == Elements.GOOD_APPLE.ch()) {
                            result = Direction.DOWN.toString();
                        } else {
                            result = Direction.RIGHT.toString();
                        }
                    }
                    break;
                }

                case 'r':{
                    if (board.getField()[point_snake.getX()][point_snake.getY()-1] == Elements.NONE.ch()||
                            board.getField()[point_snake.getX()][point_snake.getY()-1] == Elements.GOOD_APPLE.ch()) {
                        result = Direction.UP.toString();
                    }else {
                        if (board.getField()[point_snake.getX()][point_snake.getY() + 1] == Elements.NONE.ch() ||
                                board.getField()[point_snake.getX()][point_snake.getY() + 1] == Elements.GOOD_APPLE.ch()) {
                            result = Direction.DOWN.toString();
                        } else {
                            result = Direction.LEFT.toString();
                        }
                    }
                    break;
                }

                case 'u':{
                    if (board.getField()[point_snake.getX()-1][point_snake.getY()] == Elements.NONE.ch() ||
                            board.getField()[point_snake.getX()-1][point_snake.getY()] == Elements.GOOD_APPLE.ch()) {
                        result = Direction.LEFT.toString();
                    }else {
                        if (board.getField()[point_snake.getX() + 1][point_snake.getY()] == Elements.GOOD_APPLE.ch() ||
                                board.getField()[point_snake.getX() + 1][point_snake.getY()] == Elements.NONE.ch()) {
                            result = Direction.RIGHT.toString();
                        } else {
                            result = Direction.DOWN.toString();
                        }
                    }
                    break;
                }
                case  'd':{
                    if (board.getField()[point_snake.getX()-1][point_snake.getY()] == Elements.NONE.ch() ||
                            board.getField()[point_snake.getX()-1][point_snake.getY()] == Elements.GOOD_APPLE.ch()) {
                        result = Direction.LEFT.toString();
                    }else {
                        if (board.getField()[point_snake.getX() + 1][point_snake.getY()] == Elements.GOOD_APPLE.ch() ||
                                board.getField()[point_snake.getX() + 1][point_snake.getY()] == Elements.NONE.ch()) {
                            result = Direction.RIGHT.toString();
                        } else {
                            result = Direction.UP.toString();
                        }
                    }
                    break;
                }
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
