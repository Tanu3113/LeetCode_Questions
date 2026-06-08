class Solution {
    public TreeNode createBinaryTree(int[][] descriptions) {

        Map<Integer, TreeNode> map = new HashMap<>();
        Set<Integer> children = new HashSet<>();

        for (int[] d : descriptions) {

            int parent = d[0];
            int child = d[1];
            int isLeft = d[2];

            map.putIfAbsent(parent, new TreeNode(parent));
            map.putIfAbsent(child, new TreeNode(child));

            TreeNode p = map.get(parent);
            TreeNode c = map.get(child);

            if (isLeft == 1) {
                p.left = c;
            } else {
                p.right = c;
            }

            children.add(child);
        }

        for (int[] d : descriptions) {
            int parent = d[0];
            if (!children.contains(parent)) {
                return map.get(parent);
            }
        }

        return null;
    }
}