class Solution {
    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;
        List<long[]>[] adj = new List[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        int[] indeg = new int[n];
        for (int[] e : edges) {
            adj[e[0]].add(new long[]{e[1], e[2]});
            indeg[e[1]]++;
        }
        int[] topo = new int[n];
        int idx = 0;
        int[] queue = new int[n];
        int qh = 0, qt = 0;
        for (int i = 0; i < n; i++) if (indeg[i] == 0) queue[qt++] = i;
        while (qh < qt) {
            int u = queue[qh++];
            topo[idx++] = u;
            for (long[] e : adj[u]) {
                int v = (int) e[0];
                if (--indeg[v] == 0) queue[qt++] = v;
            }
        }

        long[] distinctCosts = Arrays.stream(edges).mapToLong(e -> e[2]).distinct().sorted().toArray();

        int lo = 0, hi = distinctCosts.length - 1;
        long ans = -1;
        int target = n - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            long t = distinctCosts[mid];
            if (feasible(n, adj, topo, online, target, t, k)) {
                ans = t;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return (int) ans;
    }

    private boolean feasible(int n, List<long[]>[] adj, int[] topo, boolean[] online, int target, long threshold, long k) {
        long INF = Long.MAX_VALUE / 2;
        long[] dist = new long[n];
        Arrays.fill(dist, INF);
        dist[0] = 0;
        for (int u : topo) {
            if (dist[u] >= INF) continue;
            for (long[] e : adj[u]) {
                int v = (int) e[0];
                long cost = e[1];
                if (cost < threshold) continue;
                if (v != target && !online[v]) continue;
                long nd = dist[u] + cost;
                if (nd < dist[v]) dist[v] = nd;
            }
        }
        return dist[target] <= k;
    }
}