class Solution {
    public int[] replaceElements(int[] arr) {
        int Gvalue=0;
        for (int i =0; i < arr.length-1; i++){
            Gvalue =arr[i+1];
            for (int j=i+1;j < arr.length; j++){
                if(arr[j] >= Gvalue){
                    Gvalue = arr[j];
                    arr[i]=Gvalue;
                }
            }
        }
        arr[arr.length-1] = -1;
        return arr;
        
    }
}