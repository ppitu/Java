import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Histogram extends HistogramBase {
    private int[] histogram;
    private int pointsWithoutNeighbour;

    @Override
    public int[] getHistogram() {
        int toMargin = neighbours.length / 2;
        Map<Integer, Set<String>> finalOccurrences = new HashMap<>();
        int maxValueInNeighbourhood = getMaxValueInNeighbourhood();
        histogram = new int[maxValueInNeighbourhood + 1];

        for (int row = 0; row < occupancy.length; ++row) {
            for (int col = 0; col < occupancy[row].length; ++col) {
                if (occupancy[row][col]) {
                    Point actualPoint = new Point(row, col);
                    List<Point> actualPointNeighbours = getPointsInNeighboursBounds(new Point(row, col));
                    if(actualPointNeighbours.size() == 0){
                        pointsWithoutNeighbour++;
                    }
                    actualPointNeighbours.forEach(neighbourPoint -> {
                        int neighbourValue = this.neighbours[neighbourPoint.getRow() - actualPoint.getRow() + toMargin][neighbourPoint.getCol() - actualPoint.getCol() + toMargin];
                        String pairStringCoords = getPairCoordsAsString(actualPoint, neighbourPoint);
                        Set<String> pairs = finalOccurrences.getOrDefault(neighbourValue, new HashSet<>());
                        pairs.add(pairStringCoords);
                        finalOccurrences.put(neighbourValue, pairs);
                    });
                }
            }
        }

        finalOccurrences.forEach((neighbourValue, pairs) -> {
            System.out.println(neighbourValue + " => " + pairs.size());
            histogram[neighbourValue] = pairs.size();
        });

        return histogram;
    }

    @Override
    public int noNeighbours() {
        if (histogram == null) {
            histogram = getHistogram();
        }
        return pointsWithoutNeighbour;
    }

    private static class Point {
        int row;
        int col;

        Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        int getRow() {
            return row;
        }

        int getCol() {
            return col;
        }

    }

    private int getMaxValueInNeighbourhood() {
        return Arrays.stream(neighbours)
                     .flatMapToInt(Arrays::stream)
                     .distinct()
                     .boxed()
                     .max(Integer::compareTo)
                     .get();
    }

    private String getPairCoordsAsString(Point actualPoint, Point neighbourPoint) {
        String actualPointCoords = actualPoint.getCol() + ":" + actualPoint.getRow();
        String neighbourPointCoords = neighbourPoint.getCol() + ":" + neighbourPoint.getRow();
        String pairStringCoords;
        if (actualPoint.getCol() < neighbourPoint.getCol()) {
            pairStringCoords = actualPointCoords + ":" + neighbourPointCoords;
        } else if (actualPoint.getCol() == neighbourPoint.getCol()) {
            if (actualPoint.getRow() < neighbourPoint.getRow()) {
                pairStringCoords = actualPointCoords + ":" + neighbourPointCoords;
            } else {
                pairStringCoords = neighbourPointCoords + ":" + actualPointCoords;
            }
        } else {
            pairStringCoords = neighbourPointCoords + ":" + actualPointCoords;
        }

        return pairStringCoords;
    }

    private List<Point> getPointsInNeighboursBounds(Point actual) {
        int toMargin = neighbours.length / 2;

        int toTopMargin = actual.getRow() - toMargin;
        int toBottomMargin = actual.getRow() + toMargin;
        int toLeftMargin = actual.getCol() - toMargin;
        int toRightMargin = actual.getCol() + toMargin;
        int top = Math.max(toTopMargin, 0);
        int bottom = Math.min(toBottomMargin, occupancy.length - 1);
        int left = Math.max(toLeftMargin, 0);
        int right = Math.min(toRightMargin, occupancy[0].length - 1);

        List<Point> neighbours = new LinkedList<>();
        for (int i = left; i <= right; ++i) {
            for (int j = top; j <= bottom; ++j) {
                boolean isSameAsActualPoint = j == actual.getRow() && i == actual.getCol();
                if (occupancy[j][i] && !isSameAsActualPoint) {
                    neighbours.add(new Point(j, i));
                }
            }
        }
        return neighbours;
    }
}
