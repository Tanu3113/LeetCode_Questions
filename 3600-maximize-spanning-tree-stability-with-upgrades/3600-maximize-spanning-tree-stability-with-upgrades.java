class Solution {
    public int maxStability(int n, int[][] edges, int k) {
        int low = 1, high = 200000;
        int ans = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (canAchieve(mid, n, edges, k)) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return ans;
    }

    private boolean canAchieve(int x, int n, int[][] edges, int k) {
        DSU dsu = new DSU(n);
        int edgesCount = 0;
        int upgradesUsed = 0;
        List<int[]> optionalEligible = new ArrayList<>();

        for (int[] e : edges) {
            if (e[3] == 1) {
                if (e[2] < x) return false; 
                if (dsu.union(e[0], e[1])) {
                    edgesCount++;
                } else {
                    return false; 
                }
            } else {
                if (e[2] >= x || e[2] * 2 >= x) {
                    optionalEligible.add(e);
                }
            }
        }

        optionalEligible.sort((a, b) -> {
            int costA = (a[2] >= x) ? 0 : 1;
            int costB = (b[2] >= x) ? 0 : 1;
            return costA - costB;
        });

        for (int[] e : optionalEligible) {
            int cost = (e[2] >= x) ? 0 : 1;
            if (upgradesUsed + cost <= k) {
                if (dsu.union(e[0], e[1])) {
                    edgesCount++;
                    upgradesUsed += cost;
                }
            }
        }

        return edgesCount == n - 1;
    }

    class DSU {
        int[] parent;
        DSU(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }
        int find(int i) {
            if (parent[i] == i) return i;
            return parent[i] = find(parent[i]);
        }
        boolean union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);
            if (rootI != rootJ) {
                parent[rootI] = rootJ;
                return true;
            }
            return false;
        }
    }
}