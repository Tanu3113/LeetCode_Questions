class Solution {
    private record Node(int[] remain, int prod) {}

    private static class SegmentTree {
        private final int n;
        private final int k;
        private final Node[] tree;

        public SegmentTree(int[] nums, int k) {
            this.n = nums.length;
            this.k = k;
            this.tree = new Node[4 * n];
            build(nums, 0, 0, n - 1);
        }

        private void build(int[] nums, int cur, int left, int right) {
            if (left == right) {
                int[] remain = new int[k];
                remain[nums[left]] = 1;
                tree[cur] = new Node(remain, nums[left]);
                return;
            }
            int mid = (left + right) / 2;
            build(nums, 2 * cur + 1, left, mid);
            build(nums, 2 * cur + 2, mid + 1, right);
            tree[cur] = merge(tree[2 * cur + 1], tree[2 * cur + 2]);
        }

        public void update(int i, int val) {
            update(0, 0, n - 1, i, val);
        }

        private void update(int treeIndex, int lo, int hi, int i, int val) {
            if (lo == hi) {
                int[] remain = new int[k];
                remain[val] = 1;
                tree[treeIndex] = new Node(remain, val);
                return;
            }
            int mid = (lo + hi) / 2;
            if (i <= mid) {
                update(2 * treeIndex + 1, lo, mid, i, val);
            } else {
                update(2 * treeIndex + 2, mid + 1, hi, i, val);
            }
            tree[treeIndex] = merge(tree[2 * treeIndex + 1], tree[2 * treeIndex + 2]);
        }

        public Node query(int i, int j) {
            return query(0, 0, n - 1, i, j);
        }

        private Node query(int treeIndex, int lo, int hi, int i, int j) {
            if (i <= lo && hi <= j) {
                return tree[treeIndex];
            }
            if (j < lo || hi < i) {
                int[] emptyRemain = new int[k];
                return new Node(emptyRemain, 1);
            }
            int mid = (lo + hi) / 2;
            return merge(query(2 * treeIndex + 1, lo, mid, i, j), query(2 * treeIndex + 2, mid + 1, hi, i, j));
        }

        private Node merge(Node left, Node right) {
            int newProd = (left.prod * right.prod) % k;
            int[] newRemain = new int[k];
            for (int i = 0; i < k; i++) {
                newRemain[i] = left.remain[i];
            }
            for (int i = 0; i < k; i++) {
                newRemain[(i * left.prod) % k] += right.remain[i];
            }
            return new Node(newRemain, newProd);
        }
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            nums[i] %= k;
        }
        for (int[] query : queries) {
            query[1] %= k;
        }

        SegmentTree tree = new SegmentTree(nums, k);
        int[] ans = new int[queries.length];

        for (int q = 0; q < queries.length; q++) {
            int index = queries[q][0];
            int value = queries[q][1];
            int start = queries[q][2];
            int x = queries[q][3];

            tree.update(index, value);
            ans[q] = tree.query(start, n - 1).remain[x];
        }

        return ans;
    }
}