package MainPackage.Unity;

import MainPackage.Annotation.Column;
import MainPackage.Annotation.ID;
import MainPackage.Annotation.Table;

@Table(name = "StudentData")
public class Student {
    @ID(autoIncrement = true)
    @Column(name = "ID", type = "INT")
    int ID;
    @Column(name = "name", type = "VARCHAR(50)")
    String name;
    @Column(name = "address", type = "VARCHAR(200)")
    String address;
    @Column(name = "age", type = "INT")
    int age;

    public Student() {
    }
    public Student(String name, String address, int age) {
        this.name = name;
        this.address = address;
        this.age = age;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
