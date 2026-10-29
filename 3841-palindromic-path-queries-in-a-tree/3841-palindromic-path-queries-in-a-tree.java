class Solution {
    private int n;
    private int timer;
    private List<Integer>[] adj;
    private int[] parent, depth, size, heavy, head, pos;
    private int[] bit;
    private char[] chars;

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        this.n = n;
        this.timer = 0;

        adj = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }

        chars = s.toCharArray();

        parent = new int[n];
        depth = new int[n];
        size = new int[n];
        heavy = new int[n];
        head = new int[n];
        pos = new int[n];

        Arrays.fill(heavy, -1);

        dfsSize(0, -1);
        decompose(0, 0);

        bit = new int[n + 1];

        for (int u = 0; u < n; u++) {
            updateBit(pos[u] + 1, 1 << (chars[u] - 'a'));
        }

        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.split(" ");

            if (p[0].equals("update")) {
                int u = Integer.parseInt(p[1]);
                char c = p[2].charAt(0);

                int oldMask = 1 << (chars[u] - 'a');
                int newMask = 1 << (c - 'a');

                updateBit(pos[u] + 1, oldMask ^ newMask);
                chars[u] = c;
            } else {
                int u = Integer.parseInt(p[1]);
                int v = Integer.parseInt(p[2]);

                int mask = queryPath(u, v);

                ans.add((mask & (mask - 1)) == 0);
            }
        }

        return ans;
    }

    private void dfsSize(int u, int p) {
        parent[u] = p;
        size[u] = 1;

        int maxSize = 0;

        for (int v : adj[u]) {
            if (v == p) {
                continue;
            }

            depth[v] = depth[u] + 1;

            dfsSize(v, u);

            size[u] += size[v];

            if (size[v] > maxSize) {
                maxSize = size[v];
                heavy[u] = v;
            }
        }
    }

    private void decompose(int u, int h) {
        head[u] = h;
        pos[u] = timer++;

        if (heavy[u] != -1) {
            decompose(heavy[u], h);
        }

        for (int v : adj[u]) {
            if (v == parent[u] || v == heavy[u]) {
                continue;
            }

            decompose(v, v);
        }
    }

    private void updateBit(int i, int mask) {
        while (i <= n) {
            bit[i] ^= mask;
            i += i & -i;
        }
    }

    private int queryBit(int i) {
        int mask = 0;

        while (i > 0) {
            mask ^= bit[i];
            i -= i & -i;
        }

        return mask;
    }

    private int rangeQuery(int l, int r) {
        return queryBit(r + 1) ^ queryBit(l);
    }

    private int queryPath(int u, int v) {
        int mask = 0;

        while (head[u] != head[v]) {
            if (depth[head[u]] < depth[head[v]]) {
                int temp = u;
                u = v;
                v = temp;
            }

            mask ^= rangeQuery(pos[head[u]], pos[u]);

            u = parent[head[u]];
        }

        if (depth[u] > depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        mask ^= rangeQuery(pos[u], pos[v]);

        return mask;
    }
}