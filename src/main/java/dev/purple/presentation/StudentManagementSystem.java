package dev.purple.presentation;

import dev.purple.Model.StudentDAO;
import dev.purple.domain.Student;

import java.util.List;
import java.util.Scanner;

public class StudentManagementSystem {
    public static void main(String[] args) {
        boolean exit = false;
        Scanner console = new Scanner(System.in);

        // Create service instance
        StudentDAO studentDAO = new StudentDAO();
        while (!exit){
            try {
                showAppMenu();
                exit = handleMenu(console, studentDAO);
            }catch (Exception e){
                System.out.println("Ocurrió un error al ejectutar operacion: " + e.getMessage());
            }
            System.out.println();
        }//while end
    }

    private static void showAppMenu(){
        System.out.println("""
                *** Sistema de Estudiantes ***
                1. Listar Estudiantes
                2. Buscar Estudiante
                3. Agregar Estudiantee
                4. Modificar Estudiante
                5. Eliminar Estudiante
                6. Salir
                Elege una opcion:
                """);
    }

    private static boolean handleMenu(Scanner console, StudentDAO studentDAO){
        Integer option = Integer.parseInt(console.nextLine());
        boolean exit = false;
        switch (option){
            case 1 ->{// Find Students
                System.out.println("Listado de Estudiantes...");
                List<Student> students = studentDAO.findAllStudents();
                students.forEach(System.out::println);
            }
            case 2 ->{// find student by id
                System.out.println("Introduce el ID del estudiante");
                Integer idStudent = Integer.parseInt(console.nextLine());
                Student student = new Student(idStudent);
                boolean isStudent = studentDAO.findById(student);
                if(!isStudent){
                    System.out.println("No se encontro estudiante con el ID: " + student);
                }else {
                    System.out.println("Estudiante encontrado: " + student);
                }
            }
            case 3 ->{//Add Student
                System.out.println("Agregar Estudiante: ");
                System.out.print("Nombre: ");
                String firstName = console.nextLine();
                System.out.print("Apellido: ");
                String lastName = console.nextLine();
                System.out.print("Número de teléfono:");
                String phoneNumber = console.nextLine();
                System.out.print("Correo electrónico: ");
                String email = console.nextLine();

                //create student object
                Student student = new Student(firstName, lastName, phoneNumber, email);
                boolean isCreated = studentDAO.addStudent(student);
                if(!isCreated){
                    System.out.println("Error al crear estudiante");
                }else{
                    System.out.println("Estudiante creado con éxito");
                }
            }
            case 4 ->{//update Student
                System.out.println("Modificar Estudiante: ");
                System.out.print("ID Estudiante");
                Integer idStudent = Integer.parseInt(console.nextLine());
                System.out.print("Nombre: ");
                String firstName = console.nextLine();
                System.out.print("Apellido: ");
                String lastName = console.nextLine();
                System.out.print("Número de teléfono:");
                String phoneNumber = console.nextLine();
                System.out.print("Correo electrónico: ");
                String email = console.nextLine();

                //Update Studente
                Student studentToUpdate = new Student(idStudent, firstName, lastName, phoneNumber, email);
                boolean isUpdated = studentDAO.updateStudent(studentToUpdate);

                if(!isUpdated){
                    System.out.println("No se pudo modificar el estudiante");
                }else {
                    System.out.println("Estudiante modificado: " + studentToUpdate);
                }
            }
            case 5 ->{//delete student
                System.out.println("Eliminar Estudiante:");
                System.out.print("ID Estudiante: ");
                Integer idStudent = Integer.parseInt(console.nextLine());
                Student studentToDelete = new Student(idStudent);
                boolean isDeleted = studentDAO.deleteStudent(studentToDelete);
                if(!isDeleted){
                    System.out.println("No se pudo eliminar Estudiante");
                }else {
                    System.out.println("Estudiante eliminado: " + studentToDelete);
                }

            }
            case 6 -> {//exit
                System.out.println("Hasta pronto!");
                exit= true;
            }
            default -> System.out.println("Opción no reconocida");
        }
        return exit;
    }
}