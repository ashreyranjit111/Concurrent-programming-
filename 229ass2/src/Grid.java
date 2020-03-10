import bos.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Grid implements GameBoard<Cell> {

    private Cell[][] cells = new Cell[20][20];

    private int x;
    private int y;
    private List<Cell> blocksLoc;

    private boolean rBlock;
    private boolean LBlock;
    private boolean dBlock;
    private boolean uBlock;

    private Point lastSeenMousePos;
    private long stillMouseTime;

    public void setBlocksLoc(List<Cell> blocksLoc) {
        this.blocksLoc = blocksLoc;
    }

    public Grid(int x, int y) {
        this.x = x;
        this.y = y;
        this.blocksLoc = new ArrayList<Cell>();

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                cells[i][j] = new Cell(x + j * 35, y + i * 35);
            }
        }
    }

    public void paint(Graphics g, Point mousePosition) {
        if (lastSeenMousePos != null && lastSeenMousePos.equals(mousePosition)) {
            stillMouseTime++;
        } else {
            stillMouseTime = 0;
        }
        doToEachCell((c) -> {
            c.paint(g, c.contains(mousePosition));
        });
        doToEachCell((c) -> {
            if (c.contains(mousePosition)) {
                if (stillMouseTime > 20) {
                    g.setColor(Color.YELLOW);
                    g.fillRoundRect(mousePosition.x + 20, mousePosition.y + 20, 50, 15, 3, 3);
                    g.setColor(Color.BLACK);
                    g.drawString("grass: " + c.getGrassHeight(), mousePosition.x + 20, mousePosition.y + 32);
                }
            }
        });
        lastSeenMousePos = mousePosition;
    }

    public Cell getRandomCell() {
        java.util.Random rand = new java.util.Random();
        return cells[rand.nextInt(20)][rand.nextInt(20)];
    }

    private bos.Pair<Integer, Integer> indexOfCell(Cell c) {
        for (int y = 0; y < 20; ++y) {
            for (int x = 0; x < 20; ++x) {
                if (cells[y][x] == c) {
                    return new bos.Pair(y, x);
                }
            }
        }
        return null;
    }

    public Pair<Integer, Integer> findAmongstCells(Predicate<Cell> predicate) {
        for (int y = 0; y < 20; ++y) {
            for (int x = 0; x < 20; ++x) {
                if (predicate.test(cells[y][x]))
                    return new Pair(y, x);
            }
        }
        return null;
    }

    public Optional<Pair<Integer, Integer>> safeFindAmongstCells(Predicate<Cell> predicate) {
        for (int y = 0; y < 20; ++y) {
            for (int x = 0; x < 20; ++x) {
                if (predicate.test(cells[y][x]))
                    return Optional.of(new Pair(y, x));
            }
        }
        return Optional.empty();

    }

    private void doToEachCell(Consumer<Cell> func) {
        for (int y = 0; y < 20; ++y) {
            for (int x = 0; x < 20; ++x) {
                func.accept(cells[y][x]);
            }
        }
    }

    @Override
    public Optional<Cell> below(Cell relativeTo) {
        return safeFindAmongstCells((c) -> c == relativeTo)
                .filter((pair) -> pair.first < 19)
                .map((pair) -> cells[pair.first + 1][pair.second]);
    }

    @Override
    public Optional<Cell> above(Cell relativeTo) {
        return safeFindAmongstCells((c) -> c == relativeTo)
                .filter((pair) -> pair.first > 0)
                .map((pair) -> cells[pair.first - 1][pair.second]);
    }

    @Override
    public Optional<Cell> rightOf(Cell relativeTo) {
        return safeFindAmongstCells((c) -> c == relativeTo)
                .filter((pair) -> pair.second < 19)
                .map((pair) -> cells[pair.first][pair.second + 1]);
    }

    @Override
    public Optional<Cell> leftOf(Cell relativeTo) {
        return safeFindAmongstCells((c) -> c == relativeTo)
                .filter((pair) -> pair.second > 0)
                .map((pair) -> cells[pair.first][pair.second - 1]);
    }

    public Cell cellAtRowCol(Integer row, Integer col) {
        return cells[row][col];
    }


    @Override
    public java.util.List<RelativeMove> movesBetween(Cell from, Cell to, GamePiece<Cell> mover) {
        Pair<Integer, Integer> fromIndex = findAmongstCells((c) -> c == from);
        Pair<Integer, Integer> toIndex = findAmongstCells((c) -> c == to);

        List<RelativeMove> result = new ArrayList<RelativeMove>();

        nextBlock(fromIndex);

        // horizontal movement
        if (fromIndex.second <= toIndex.second) {
            for (int i = fromIndex.second; i < toIndex.second; i++) {
                result.add(new MoveRight(this, mover));
            }

            if (fromIndex.second != toIndex.second && rBlock) {
                return new ArrayList<RelativeMove>();
            }
        } else {
            for (int i = toIndex.second; i < fromIndex.second; i++) {
                result.add(new MoveLeft(this, mover));
            }

            if (LBlock) {
                return new ArrayList<RelativeMove>();
            }

        }

        // vertical movement
        if (fromIndex.first <= toIndex.first) {
            for (int i = fromIndex.first; i < toIndex.first; i++) {
                result.add(new MoveDown(this, mover));
            }
            if ((result.isEmpty() || fromIndex.second == toIndex.second) && dBlock) {
                return new ArrayList<RelativeMove>();
            }
        } else {
            for (int i = toIndex.first; i < fromIndex.first; i++) {
                result.add(new MoveUp(this, mover));
            }
            if ((result.isEmpty() || fromIndex.second == toIndex.second) && uBlock) {
                return new ArrayList<RelativeMove>();
            }
        }
        return result;
    }

    private void nextBlock(Pair<Integer, Integer> fromIndex) {
        rBlock = false;
        LBlock = false;
        dBlock = false;
        uBlock = false;

        int rightLoc = fromIndex.second + 1;
        int leftLoc = fromIndex.second - 1;
        int downLoc = fromIndex.first + 1;
        int upLoc = fromIndex.first - 1;

        for (Cell block : blocksLoc) {
            Pair<Integer, Integer> b_Index = findAmongstCells((c) -> c == block);
            if (rightLoc == b_Index.second && fromIndex.first == b_Index.first) {
                rBlock = true;
                return;
            }
            if (leftLoc == b_Index.second && fromIndex.first == b_Index.first) {
                LBlock = true;
                return;
            }
            if (downLoc == b_Index.first && fromIndex.second == b_Index.second) {
                dBlock = true;
                return;
            }
            if (upLoc == b_Index.first && fromIndex.second == b_Index.second) {
                uBlock = true;
                return;
            }
        }
    }


}