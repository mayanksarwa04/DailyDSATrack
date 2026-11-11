class Solution {
    public long minimumCost(int cost1, int cost2, int costBoth,
                            int need1, int need2) {

        // Strategy 1: Buy both types separately
        long option1 = (long) need1 * cost1
                     + (long) need2 * cost2;

        // Strategy 2: Buy type 3 for the common requirement
        // and individual items for the remaining requirement
        long common = Math.min(need1, need2);

        long option2 = common * costBoth
                     + (long) (need1 - common) * cost1
                     + (long) (need2 - common) * cost2;

        // Strategy 3: Buy only type 3 items,
        // even if some requirements get exceeded
        long option3 = (long) Math.max(need1, need2) * costBoth;

        return Math.min(option1, Math.min(option2, option3));
    }
}
