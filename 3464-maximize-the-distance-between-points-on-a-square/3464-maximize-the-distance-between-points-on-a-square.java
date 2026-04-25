import java.util.*;

class Solution {
    public int maxDistance(int side, int[][] points, int k) {
        int n = points.length;
        long[] pos = new long[n];
        for (int i = 0; i < n; i++) {
            pos[i] = convertTo1D(points[i][0], points[i][1], side);
        }
        Arrays.sort(pos);

        long perimeter = 4L * side;
        int low = 1, high = 2 * side;
        int ans = 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (check(mid, pos, k, perimeter, side)) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return ans;
    }

    private long convertTo1D(int x, int y, int side) {
        if (y == 0) return x;
        if (x == side) return (long) side + y;
        if (y == side) return 2L * side + (side - x);
        return 3L * side + (side - y);
    }

    private boolean check(int d, long[] pos, int k, long perimeter, int side) {
        int n = pos.length;
        int limitIdx = n;
        for (int i = 0; i < n; i++) {
            if (pos[i] - pos[0] >= d) {
                limitIdx = i;
                break;
            }
        }

        for (int i = 0; i < limitIdx; i++) {
            int count = 1;
            long currPos = pos[i];
            long startPos = pos[i];
            
            for (int j = 1; j < k; j++) {
                long target = currPos + d;
                int nextIdx = findFirst(pos, target, n, perimeter);
                if (nextIdx == -1) {
                    count = -1;
                    break;
                }
                long nextPos = getPos(pos, nextIdx, n, perimeter);
                if (nextPos >= startPos + perimeter) {
                    count = -1;
                    break;
                }
                currPos = nextPos;
                count++;
            }
            
            if (count == k && getManhattanDist(currPos, startPos + perimeter, side) >= d) {
                return true;
            }
        }
        return false;
    }

    private int findFirst(long[] pos, long target, int n, long perimeter) {
        int l = 0, r = 2 * n - 1;
        int ans = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            long val = getPos(pos, mid, n, perimeter);
            if (val >= target) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return ans;
    }

    private long getPos(long[] pos, int idx, int n, long perimeter) {
        if (idx < n) return pos[idx];
        return pos[idx - n] + perimeter;
    }

    private long getManhattanDist(long p1, long p2, int side) {
        long x1 = getCoord(p1, side, true), y1 = getCoord(p1, side, false);
        long x2 = getCoord(p2, side, true), y2 = getCoord(p2, side, false);
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private long getCoord(long p, int side, boolean isX) {
        p %= (4L * side);
        if (p <= side) return isX ? p : 0;
        if (p <= 2L * side) return isX ? side : (p - side);
        if (p <= 3L * side) return isX ? (3L * side - p) : side;
        return isX ? 0 : (4L * side - p);
    }
}