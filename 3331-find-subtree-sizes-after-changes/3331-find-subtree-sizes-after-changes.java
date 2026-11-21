
import java.util.Arrays;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {

        int n = parent.length;

        // Build original tree
        int[] head = new int[n];
        int[] next = new int[n];
        int[] to = new int[n];

        Arrays.fill(head, -1);

        int idx = 0;

        for (int i = 1; i < n; i++) {
            int p = parent[i];

            to[idx] = i;
            next[idx] = head[p];
            head[p] = idx++;
        }

        // Find new parent using original tree
        int[] newParent = new int[n];
        int[] last = new int[26];
        int[] oldLast = new int[n];

        Arrays.fill(last, -1);
        newParent[0] = -1;

        // Iterative DFS to avoid stack overflow
        int[] stack = new int[2 * n];
        boolean[] exit = new boolean[2 * n];

        int top = 0;

        stack[top] = 0;
        exit[top++] = false;

        while (top > 0) {

            int node = stack[--top];
            boolean isExit = exit[top];

            int c = s.charAt(node) - 'a';

            if (isExit) {
                last[c] = oldLast[node];
                continue;
            }

            // Save previous ancestor with same character
            oldLast[node] = last[c];

            // Find closest matching ancestor
            if (last[c] != -1) {
                newParent[node] = last[c];
            } else if (node != 0) {
                newParent[node] = parent[node];
            }

            // Current node becomes nearest ancestor
            last[c] = node;

            // Exit event: restore ancestor after children
            stack[top] = node;
            exit[top++] = true;

            // Visit original children
            for (int e = head[node]; e != -1; e = next[e]) {
                stack[top] = to[e];
                exit[top++] = false;
            }
        }

        // Build modified tree
        int[] newHead = new int[n];
        int[] newNext = new int[n];
        int[] newTo = new int[n];

        Arrays.fill(newHead, -1);

        idx = 0;

        for (int i = 1; i < n; i++) {

            int p = newParent[i];

            newTo[idx] = i;
            newNext[idx] = newHead[p];
            newHead[p] = idx++;
        }

        // Get traversal order of modified tree
        int[] order = new int[n];
        int[] dfs = new int[n];

        int size = 0;
        top = 0;

        dfs[top++] = 0;

        while (top > 0) {

            int node = dfs[--top];

            order[size++] = node;

            for (int e = newHead[node]; e != -1; e = newNext[e]) {
                dfs[top++] = newTo[e];
            }
        }

        // Calculate subtree sizes bottom-up
        int[] ans = new int[n];
        Arrays.fill(ans, 1);

        for (int i = size - 1; i > 0; i--) {

            int node = order[i];

            ans[newParent[node]] += ans[node];
        }

        return ans;
    }
}
