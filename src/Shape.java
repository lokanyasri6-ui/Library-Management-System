abstract class Shape{
    abstract void draw();
}
class Circle extends Shape{
    void draw(){
        System.out.println("Drawing Circle");
    }
}
class Square extends Shape {
    void draw(){
        System.out.println("Drawing Square");
    }
}
class ShapeFactory{
    public static Shape getShape(String type){
        if (type == null){
            throw new IllegalArgumentException("Shape type cannot be null");
        }
        if (type.equalsIgnoreCase("Circle")){
            return new Circle();
        } else if (type.equalsIgnoreCase("Square")){
            return new Square();
        }
        throw new IllegalArgumentException("Invalid Shape:"+type);
    }
}
