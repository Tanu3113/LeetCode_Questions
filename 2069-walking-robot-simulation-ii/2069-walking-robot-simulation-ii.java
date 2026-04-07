class Robot {
    private int width, height;
    private int totalSteps = 0;
    private boolean moved = false;
    private int perimeter;

    public Robot(int width, int height) {
        this.width = width;
        this.height = height;
        this.perimeter = 2 * (width + height) - 4;
    }

    public void step(int num) {
        moved = true;
        totalSteps += num;
        totalSteps %= perimeter;
    }

    public int[] getPos() {
        int s = totalSteps;
        
        if (s < width) {
            return new int[]{s, 0};
        } else if (s < width + height - 1) {
            return new int[]{width - 1, s - (width - 1)};
        } else if (s < 2 * width + height - 2) {
            return new int[]{width - 1 - (s - (width + height - 2)), height - 1};
        } else {
            return new int[]{0, height - 1 - (s - (2 * width + height - 3))};
        }
    }

    public String getDir() {
        
        if (moved && totalSteps == 0) return "South";
        
        int s = totalSteps;
        if (s > 0 && s < width) return "East";
        if (s >= width && s < width + height - 1) return "North";
        if (s >= width + height - 1 && s < 2 * width + height - 2) return "West";
        if (s >= 2 * width + height - 2 || s == 0) {
            
            return s == 0 && !moved ? "East" : "South";
        }
        return "East";
    }
}