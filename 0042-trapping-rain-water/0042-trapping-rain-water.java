class Solution {
    public int trap(int[] height) {

        int n=height.length;

        int[] maxLeft= new int[n];
        maxLeft[0] = height[0]; 
        for (int i=1; i<n; i++){
            maxLeft[i]= Math.max(height[i], maxLeft[i-1]);
        }

        int[] maxRight= new int[n];
        maxRight[n-1] = height[n-1];
        for (int i=n-2; i>=0;i--){
            maxRight[i]= Math.max(height[i], maxRight[i+1]);
        }
        int trappedwater=0;
        for (int i=0; i<n;i++){
            int waterlevel = Math.min(maxLeft[i], maxRight[i]);
            trappedwater += waterlevel - height[i]; 
        }
        
        return trappedwater;
    }
}