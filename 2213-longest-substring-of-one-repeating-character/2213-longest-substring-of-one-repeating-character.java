class Solution {
    static class Node {
        int maxLen;
        int prefLen;
        char prefChar;
        int suffLen;
        char suffChar;
        int length; 
        Node(char c) {
            this.maxLen = 1;
            this.prefLen = 1;
            this.prefChar = c;
            this.suffLen = 1;
            this.suffChar = c;
            this.length = 1;
        }

        Node() {}
    }

    private Node[] tree;
    private char[] sArr;

    private Node merge(Node left, Node right) {
        Node res = new Node();
        res.length = left.length + right.length;

        res.maxLen = Math.max(left.maxLen, right.maxLen);

        if (left.suffChar == right.prefChar) {
            res.maxLen = Math.max(res.maxLen, left.suffLen + right.prefLen);
        }

        res.prefChar = left.prefChar;
        res.prefLen = left.prefLen;
        if (left.prefLen == left.length && left.prefChar == right.prefChar) {
            res.prefLen = left.length + right.prefLen;
        }

        res.suffChar = right.suffChar;
        res.suffLen = right.suffLen;
        if (right.suffLen == right.length && right.suffChar == left.suffChar) {
            res.suffLen = right.length + left.suffLen;
        }

        return res;
    }

    private void build(int node, int start, int end) {
        if (start == end) {
            tree[node] = new Node(sArr[start]);
            return;
        }
        int mid = start + (end - start) / 2;
        build(2 * node, start, mid);
        build(2 * node + 1, mid + 1, end);
        tree[node] = merge(tree[2 * node], tree[2 * node + 1]);
    }

    private void update(int node, int start, int end, int idx, char val) {
        if (start == end) {
            tree[node] = new Node(val);
            return;
        }
        int mid = start + (end - start) / 2;
        if (idx <= mid) {
            update(2 * node, start, mid, idx, val);
        } else {
            update(2 * node + 1, mid + 1, end, idx, val);
        }
        tree[node] = merge(tree[2 * node], tree[2 * node + 1]);
    }

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = s.length();
        int k = queryIndices.length;
        this.sArr = s.toCharArray();
        this.tree = new Node[4 * n];

        build(1, 0, n - 1);

        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            int idx = queryIndices[i];
            char c = queryCharacters.charAt(i);
            
            update(1, 0, n - 1, idx, c);
            ans[i] = tree[1].maxLen;
        }

        return ans;
    }
}