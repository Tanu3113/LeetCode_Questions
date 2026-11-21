class Solution {
    List<Integer>[] children;
    int[] newParent;
    int[] ans;
    int[] last;

    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        children = new ArrayList[n];
        newParent = new int[n];
        ans = new int[n];
        last = new int[26];

        Arrays.fill(last, -1);

        for (int i = 0; i < n; i++) {
            children[i] = new ArrayList<>();
            newParent[i] = parent[i];
        }

        for (int i = 1; i < n; i++) {
            children[parent[i]].add(i);
        }

        dfs(0, s);

        List<Integer>[] modified = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            modified[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            modified[newParent[i]].add(i);
        }

        calculateSize(0, modified);

        return ans;
    }

    void dfs(int node, String s) {
        int ch = s.charAt(node) - 'a';

        int previous = last[ch];

        if (previous != -1) {
            newParent[node] = previous;
        }

        last[ch] = node;

        for (int child : children[node]) {
            dfs(child, s);
        }

        last[ch] = previous;
    }

    int calculateSize(int node, List<Integer>[] modified) {
        int size = 1;

        for (int child : modified[node]) {
            size += calculateSize(child, modified);
        }

        ans[node] = size;

        return size;
    }
}
