public class Main{ 
    public static void main(String[] args) {
        try {
            Shape s1 = ShapeFactory.getShape("Circle");
            s1.draw();
            Shape s2 = ShapeFactory.getShape("square");
            s2.draw();
        } catch (IllegalArgumentException e) {
            System.out.println("Error" + e.getMessage());
        }
    }
}
