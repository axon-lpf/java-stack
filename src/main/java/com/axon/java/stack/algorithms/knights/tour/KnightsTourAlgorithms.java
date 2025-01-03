package com.axon.java.stack.algorithms.knights.tour;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 骑士周游算法
 */
public class KnightsTourAlgorithms {


    public static void main(String[] args) {

        int[][] map = new int[8][8];
        KnightsTour knightsTour = new KnightsTour(map);

        knightsTour.knights(0, 0, 1);

        knightsTour.show();

    }


}

class KnightsTour {

    /**
     * 是否被访问过
     */
    int[] visited;

    /**
     * 地图
     */
    int[][] chessBoard;

    int X;

    int Y;

    /**
     * 是否完成了
     */
    boolean finished;

    public KnightsTour(int[][] map) {
        this.chessBoard = map;
        this.X = map[0].length;
        this.Y = map.length;
        this.visited = new int[this.X * this.Y];

    }


    /**
     * 骑士周游算法
     *
     * @param row 坐标  代表列
     * @param column 左边  代表行
     * @param step 步骤树
     */
    public void knights(int row, int column, int step) {

        //设置当前地图的步骤树
        this.chessBoard[row][column] = step;
        //这是是否被访问过  因为x 和y 的下标均从0 开始的 。
        this.visited[row * this.X + column] = 1;

        // 获取当前点所对应的步数
        List<Point> next = getNext(new Point(column, row));
        // 进行排序
        sortPoint(next);

        while (!next.isEmpty()) {

            Point p = next.remove(0);

            // 判断当前节点有没有被访问过
            if (visited[p.y * this.X + p.x] != 1) {

                // 继续去下一个的步骤
                knights(p.y,p.x, step + 1);
            }

        }
        // 1.棋盘到目前位置还没有走完
        // 2.棋盘还在回溯
        if (step <this.X * this.Y && !this.finished) {
            this.chessBoard[row][column] = 0;
            visited[row * this.X + column] = 0;
        } else {
           finished=true;
        }


    }


    /**
     * 根据一个坐标获取器对应可走的坐标的位置
     *
     * @param curPoint
     * @return
     */
    public List<Point> getNext(Point curPoint) {
        List<Point> ps = new ArrayList<>();

        Point p1 = new Point();

        // 棋盘中马走日字 方法， 进行位移，不能超越棋盘的界限
        //判断马儿能否走5这个位置
        if ((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y - 1) >=0) {
            ps.add(new Point(p1));
        }

        //判断马儿能否走6这个位置
        if ((p1.x = curPoint.x - 1) >= 0 && (p1.y = curPoint.y - 2) >= 0) {
            ps.add(new Point(p1));
        }
        //判断马儿能否走7这个位置
        if ((p1.x = curPoint.x + 1) < this.X && (p1.y = curPoint.y - 2) >= 0) {
            ps.add(new Point(p1));
        }
        //判断马儿能否走0这个位置
        if ((p1.x = curPoint.x + 2) < this.X && (p1.y = curPoint.y - 1) >= 0) {
            ps.add(new Point(p1));
        }
        //判断马儿能否走1这个位置
        if ((p1.x = curPoint.x + 2) < this.X && (p1.y = curPoint.y + 1) < this.Y) {
            ps.add(new Point(p1));
        }
        //判断马儿能否走2这个位置
        if ((p1.x = curPoint.x + 1) < this.X && (p1.y = curPoint.y + 2) < this.Y) {
            ps.add(new Point(p1));
        }
        //判断马儿能否走3这个位置
        if ((p1.x = curPoint.x -1 ) >=0 && (p1.y = curPoint.y + 2) < this.Y) {
            ps.add(new Point(p1));
        }
        //判断马儿能否走4这个位置
        if ((p1.x = curPoint.x -2 ) >=0 && (p1.y = curPoint.y + 1) < this.Y) {
            ps.add(new Point(p1));
        }
        return ps;

    }

    public void sortPoint(List<Point> points) {

        points.sort((o1, o2) -> {

            int o1Size = getNext(o1).size();
            int o2size = getNext(o2).size();

            if (o1Size > o2size) {
                return 1;
            } else if (o1Size == o2size) {
                return 0;
            } else {
                return -1;
            }
        });

    }

    public void show() {
        for (int[] row : this.chessBoard) {
            for (int cell : row) {
                System.out.printf("%2d ", cell);
            }
            System.out.println();
        }
    }



}



