
import java.util.Arrays;

class Solution {
    public int maxSelectedElements(int[] nums) {

        Arrays.sort(nums);

        int[] dp = new int[1000002];

        int ans = 0;

        for (int x : nums) {

            // Choice 1: Increase x to x + 1
            dp[x + 1] = Math.max(dp[x + 1], dp[x] + 1);

            // Choice 2: Keep x unchanged
            dp[x] = Math.max(dp[x], dp[x - 1] + 1);

            ans = Math.max(ans, Math.max(dp[x], dp[x + 1]));
        }

        return ans;
    }
}

