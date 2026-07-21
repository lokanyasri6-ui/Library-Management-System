
public class Point2D {
    int x;
    int y;
    Point2D (int x,int y){
        this.x =x;
        this.y=y;
    }
    double distance(Point2D p){
        int dx = p.x - this.x;
        int dy = p.y - this.y;
        return Math.sqrt(dx*dx+dy*dy);
    }
    void display() {
        System.out.println("(" + x + "," + y + ")");
    }
    public static void main(String[] args){
        Point2D p = new Point2D(8,7);
        Point2D p1 = new Point2D(8,5);
        p.display();
        p1.display();
        System.out.println("Distace:"+p.distance(p1));

    }
}
