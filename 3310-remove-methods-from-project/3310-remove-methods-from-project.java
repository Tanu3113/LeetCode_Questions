class Solution {
    public java.util.List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        java.util.ArrayList<Integer>[] graph = new java.util.ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new java.util.ArrayList<>();
        }

        for (int[] e : invocations) {
            graph[e[0]].add(e[1]);
        }

        boolean[] suspicious = new boolean[n];
        java.util.ArrayDeque<Integer> q = new java.util.ArrayDeque<>();
        q.offer(k);
        suspicious[k] = true;

        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : graph[u]) {
                if (!suspicious[v]) {
                    suspicious[v] = true;
                    q.offer(v);
                }
            }
        }

        for (int[] e : invocations) {
            if (!suspicious[e[0]] && suspicious[e[1]]) {
                java.util.List<Integer> ans = new java.util.ArrayList<>();
                for (int i = 0; i < n; i++) ans.add(i);
                return ans;
            }
        }

        java.util.List<Integer> ans = new java.util.ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!suspicious[i]) ans.add(i);
        }
        return ans;
    }
}