class Solution {
    public int firstStableIndex(int[] nums, int k) {

        int n= nums.length;
        int [] suffixmin = new int [n];
        suffixmin[n - 1] = nums[n -1];

        for (int i=n-2; i>=0; i--){
            suffixmin[i] = Math.min(nums[i], suffixmin[i+1]);
        }

        int prefixmax = nums[0];

        for (int i =0; i<n; i++){
            prefixmax = Math.max(prefixmax, nums[i]);

            long instability = (long) prefixmax - suffixmin[i];

            if(instability <= k){
                return i;
            }
        }
        return -1;
    }
}