package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
class Student {
    private String name;
    private int age;
    private String gender;

    public Student()
    {
    }
    public Student(String name, int age, String gender)
    {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public static class Builder
    {
        private String name;
        private int age;
        private String gender;


        public Builder setName(String name)
        {
            this.name = name;
            return this;
        }

        public Builder setAge(int age)
        {
            this.age = age;
            return this;
        }

        public Builder setGender(String gender)
        {
            this.gender = gender;
            return this;
        }

        public Student build()
        {
            return new Student(name, age, gender);
        }
    }

    // Геттеры для полей
    public String getName()
    {
        return name;
    }

    public int getAge()
    {
        return age;
    }

    public String getGender()
    {
        return gender;
    }

    @Override
    public String toString()
    {
        return "Student{name='" + name + "', age=" + age + ", gender='" +
                gender + "'}";
    }
}

public class Main
{
    private static final String FILE_NAME = "students.txt";

    // Метод для записи списка студентов в файл
    public static void saveStudents(List<Student> students)
    {
        try (FileWriter writer = new FileWriter(FILE_NAME))
        {
            for (Student student : students) {
                writer.write(student.getName() + "," +
                        student.getAge() + "," + student.getGender() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
    public static List<Student> loadStudents()
    {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new
                FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    int age = Integer.parseInt(parts[1]);
                    String gender = parts[2];
                    students.add(new Student(name, age, gender));
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении из файла: " +
                    e.getMessage());
        }
        return students;
    }

    // Метод для вывода студентов по полу
    public static void printStudentsByGender(List<Student> students,
                                             String gender) {
        for (Student student : students) {
            if (student.getGender().equalsIgnoreCase(gender)) {
                System.out.println(student);
            }
        }
    }
    public static void main(String[] args)
    {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Иван", 20, "М"));
        students.add(new Student("Анна", 22, "Же"));
        students.add(new Student("Петр", 21, "М"));
        students.add(new Student("Никита", 25, "М"));
        students.add(new Student("Настя", 19, "Ж"));
        saveStudents(students);
        List<Student> loadedStudents = loadStudents();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите пол для фильтрации (М/Ж): ");
        String gender = scanner.nextLine();
//
        System.out.println("Студенты с полом '" + gender + "':");
        printStudentsByGender(loadedStudents, gender);
        scanner.close();
    }
}