class Solution {
    public int countCompleteComponents(int n, int[][] edges) {
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();

        for (int[] e : edges) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }

        boolean[] vis = new boolean[n];
        int ans = 0;

        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                List<Integer> comp = new ArrayList<>();
                dfs(i, graph, vis, comp);

                int vertices = comp.size();
                int degreeSum = 0;

                for (int node : comp)
                    degreeSum += graph[node].size();

                if (degreeSum == vertices * (vertices - 1))
                    ans++;
            }
        }

        return ans;
    }

    private void dfs(int node, List<Integer>[] graph, boolean[] vis, List<Integer> comp) {
        vis[node] = true;
        comp.add(node);

        for (int nei : graph[node]) {
            if (!vis[nei])
                dfs(nei, graph, vis, comp);
        }
    }
}