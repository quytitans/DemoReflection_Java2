package MainPackage;

import MainPackage.Model.BetterModel;
import MainPackage.Unity.Student;

public class MainThread {
    public static void main(String[] args) {
//        BetterModel.migrateData(Student.class);

        Student student = new Student("Nguyen Van Quy","Ha Noi", 30);
        try {
            BetterModel.saveData(student);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
