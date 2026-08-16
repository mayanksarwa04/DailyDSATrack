class Solution {
    public double findMaxAverage(int[] nums, int k) {

        int windowsum=0;

        for(int i=0; i<k; i++){
            windowsum += nums[i];
        }
        int maxsum = windowsum;

        for (int i=k; i<nums.length; i++){
            windowsum -= nums[i-k]; //removing element
            windowsum += nums[i];
            maxsum = Math.max(maxsum, windowsum);
        }
        return (double) maxsum/k;
    }
}