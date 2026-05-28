class Solution {

    class TrieNode {

        TrieNode[] child = new TrieNode[26];

        int index = -1;
    }

    TrieNode root = new TrieNode();

    String[] words;

    public int[] stringIndices(String[] wordsContainer, String[] wordsQuery) {

        words = wordsContainer;

        int best = 0;

        for(int i = 1; i < wordsContainer.length; i++){

            if(wordsContainer[i].length() < wordsContainer[best].length()){
                best = i;
            }
        }

        root.index = best;

        for(int i = 0; i < wordsContainer.length; i++){
            insert(wordsContainer[i], i);
        }

        int[] ans = new int[wordsQuery.length];

        for(int i = 0; i < wordsQuery.length; i++){
            ans[i] = search(wordsQuery[i]);
        }

        return ans;
    }

    private void insert(String s, int idx){

        TrieNode node = root;

        update(node, idx);

        for(int i = s.length() - 1; i >= 0; i--){

            int c = s.charAt(i) - 'a';

            if(node.child[c] == null){
                node.child[c] = new TrieNode();
            }

            node = node.child[c];

            update(node, idx);
        }
    }

    private void update(TrieNode node, int idx){

        if(node.index == -1){
            node.index = idx;
        }
        else{

            int currLen = words[node.index].length();
            int newLen = words[idx].length();

            if(newLen < currLen){
                node.index = idx;
            }
            else if(newLen == currLen && idx < node.index){
                node.index = idx;
            }
        }
    }

    private int search(String s){

        TrieNode node = root;

        for(int i = s.length() - 1; i >= 0; i--){

            int c = s.charAt(i) - 'a';

            if(node.child[c] == null){
                break;
            }

            node = node.child[c];
        }

        return node.index;
    }
}