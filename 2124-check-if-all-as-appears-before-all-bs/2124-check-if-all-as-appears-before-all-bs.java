class Solution {
    public boolean checkString(String s) {

     boolean founndB = false;

     for (int i=0; i< s.length(); i++){

        if (s.charAt(i) == 'b'){
            founndB = true;
        }

        if (s.charAt(i)== 'a' && founndB){
            return false;
        }
     }
    return true;
    }
}