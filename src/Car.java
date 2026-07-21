public class Car {
    String brand;
    String model;
    double mileage;
    Car(String brand,String model,double mileage){
        this.brand=brand;
        this.model=model;
        this.mileage=mileage;
    }
    void drive(int km){
        mileage+=km;
        System.out.println("Driven:"+km+  ",New mileage:"+mileage);

    }
    void display(){
        System.out.println("Brand:"+brand+",Model:"+model);
    }
    public static void main(String[] args){
        Car c1=new Car("BMW","Corolla",10000);
        c1.display();
        c1.drive(1900);
    }
}
