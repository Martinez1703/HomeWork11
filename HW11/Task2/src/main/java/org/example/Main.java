package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
interface Shape {
    double calculateArea();
    double calculatePerimeter();
}

// Класс для круга
class Circle implements Shape {
    private double radius;

    public Circle(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Радиус должен быть положительным.");
        }
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
}
class Square implements Shape
{
    private double side;

    public Square(double side)
    {
        if (side <= 0) {
            throw new IllegalArgumentException("Сторона должна быть положительной.");
        }
        this.side = side;
    }

    @Override
    public double calculateArea()
    {
        return side * side;
    }

    @Override
    public double calculatePerimeter()
    {
        return 4 * side;
    }
}
class Rectangle implements Shape
{
    private double width;
    private double height;

    public Rectangle(double width, double height)
    {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Ширина и высота должны быть положительными.");
        }
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea()
    {
        return width * height;
    }

    @Override
    public double calculatePerimeter()
    {
        return 2 * (width + height);
    }
}
public class Main
{
    public static List<Shape> createShapesFromFile(String filename) throws
            IOException
    {
        List<Shape> shapes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new
                FileReader(filename)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] parts = line.split(",");
                String shapeType = parts[0].trim();

                switch (shapeType.toLowerCase())
                {
                    case "circle":
                        double radius = Double.parseDouble(parts[1].trim());
                        shapes.add(new Circle(radius));
                        break;
                    case "square":
                        double side = Double.parseDouble(parts[1].trim());
                        shapes.add(new Square(side));
                        break;
                    case "rectangle":
                        double width = Double.parseDouble(parts[1].trim());
                        double height = Double.parseDouble(parts[2].trim());
                        shapes.add(new Rectangle(width, height));
                        break;
                    default:
                        throw new IllegalArgumentException("Неизвестный тип фигуры: " + shapeType);
                }
            }
        }
        return shapes;
    }
    public static void writeResultsToFile(List<Shape> shapes, String
            filename) throws IOException
    {
        try (FileWriter writer = new FileWriter(filename))
        {
            for (Shape shape : shapes)
            {
                writer.write("Фигура: " + shape.getClass().getSimpleName() +
                        "\n");
                writer.write("Площадь: " + shape.calculateArea() + "\n");
                writer.write("Периметр: " + shape.calculatePerimeter() +
                        "\n\n");
            }
        }
    }

    public static void main(String[] args) {
        String inputFile = "data.txt"; // Имя входного файла
        String outputFile = "results.txt"; // Имя выходного файла
        try {
            List<Shape> shapes = createShapesFromFile(inputFile);
            writeResultsToFile(shapes, outputFile);
            System.out.println("Результаты записаны в файл: " + outputFile);
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}