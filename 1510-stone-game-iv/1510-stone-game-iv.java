class Solution {
    public boolean winnerSquareGame(int n) {
        boolean[] dp = new boolean[n + 1];
        
        for (int i = 1; i <= n; i++) {
            for (int k = 1; k * k <= i; k++) {
                // If we can move to a state where the next player loses, we win
                if (!dp[i - k * k]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        
        return dp[n];
    }
}