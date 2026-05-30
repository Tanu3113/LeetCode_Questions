import java.util.*;

class Solution {
    int[] tree;
    int n;

    void update(int node, int start, int end, int idx, int val) {
        if (start == end) { tree[node] = val; return; }
        int mid = (start + end) / 2;
        if (idx <= mid) update(2*node, start, mid, idx, val);
        else            update(2*node+1, mid+1, end, idx, val);
        tree[node] = Math.max(tree[2*node], tree[2*node+1]);
    }

    int query(int node, int start, int end, int l, int r) {
        if (l > r || r < start || end < l) return 0;
        if (l <= start && end <= r) return tree[node];
        int mid = (start + end) / 2;
        return Math.max(
            query(2*node, start, mid, l, r),
            query(2*node+1, mid+1, end, l, r)
        );
    }

    public List<Boolean> getResults(int[][] queries) {
        int maxX = 0;
        for (int[] q : queries) maxX = Math.max(maxX, q[1]);
        n = maxX;

        tree = new int[4 * (n + 1)];

        TreeSet<Integer> obstacles = new TreeSet<>();
        obstacles.add(0);
        update(1, 0, n, 0, n);

        List<Boolean> results = new ArrayList<>();

        for (int[] q : queries) {
            if (q[0] == 1) {
                int x = q[1];
                int prev = obstacles.floor(x - 1);
                Integer nextObs = obstacles.higher(x);
                int next = (nextObs == null) ? n : nextObs;

                update(1, 0, n, prev, x - prev);
                update(1, 0, n, x, next - x);
                obstacles.add(x);
            } else {
                int x  = q[1];
                int sz = q[2];

                int lastObs = obstacles.floor(x);
                int maxGap = lastObs > 0 ? query(1, 0, n, 0, lastObs - 1) : 0;
                maxGap = Math.max(maxGap, x - lastObs);

                results.add(maxGap >= sz);
            }
        }

        return results;
    }
}