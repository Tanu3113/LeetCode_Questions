class Solution {

    static final int MOD = 1_000_000_007;
    int LOG;
    int[][] up;
    int[] depth;
    List<Integer>[] graph;

    public int[] assignEdgeWeights(int[][] edges, int[][] queries) {
        int n = edges.length + 1;

        graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        LOG = 1;
        while ((1 << LOG) <= n) {
            LOG++;
        }

        up = new int[n + 1][LOG];
        depth = new int[n + 1];

        dfs(1, 1);


        long[] pow = new long[n + 1];
        pow[0] = 1;

        for (int i = 1; i <= n; i++) {
            pow[i] = (pow[i - 1] * 2) % MOD;
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int u = queries[i][0];
            int v = queries[i][1];

            int lca = getLCA(u, v);
            int len = depth[u] + depth[v] - 2 * depth[lca];

            if (len == 0) {
                ans[i] = 0;
            } else {
                ans[i] = (int) pow[len - 1];
            }
        }

        return ans;
    }

    private void dfs(int node, int parent) {
        up[node][0] = parent;

        for (int i = 1; i < LOG; i++) {
            up[node][i] = up[up[node][i - 1]][i - 1];
        }

        for (int next : graph[node]) {
            if (next != parent) {
                depth[next] = depth[node] + 1;
                dfs(next, node);
            }
        }
    }

    private int getLCA(int a, int b) {

        if (depth[a] < depth[b]) {
            int temp = a;
            a = b;
            b = temp;
        }

        int diff = depth[a] - depth[b];

        for (int i = LOG - 1; i >= 0; i--) {
            if (((diff >> i) & 1) == 1) {
                a = up[a][i];
            }
        }

        if (a == b) {
            return a;
        }

        for (int i = LOG - 1; i >= 0; i--) {
            if (up[a][i] != up[b][i]) {
                a = up[a][i];
                b = up[b][i];
            }
        }

        return up[a][0];
    }
}