class Solution {
    public int maxIceCream(int[] costs, int coins) {
        int maxCost = 0;
        for (int cost : costs) {
            if (cost > maxCost) {
                maxCost = cost;
            }
        }
        
        int[] frequency = new int[maxCost + 1];
        for (int cost : costs) {
            frequency[cost]++;
        }
        
        int iceCreamCount = 0;
        
        for (int price = 1; price <= maxCost; price++) {
            if (frequency[price] == 0) {
                continue;
            }
            
            if (coins < price) {
                break;
            }
            
            long maxCanBuy = (long) coins / price;
            long actualBuy = Math.min(maxCanBuy, frequency[price]);
            
            coins -= actualBuy * price;
            iceCreamCount += actualBuy;
        }
        
        return iceCreamCount;
    }
}