package dev.purple.Model;

import dev.purple.connection.ConnectionDB;
import dev.purple.domain.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public List<Student> findAllStudents(){
        List<Student> students = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = ConnectionDB.getConnecttion();
        String sql = "SELECT * FROM student ORDER BY id_student";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Student student = new Student();
                student.setIdStudent(rs.getInt("id_student"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setPhoneNumber(rs.getString("phone_number"));
                student.setEmail(rs.getString("email"));

                students.add(student);
            }
        }catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("Close" +  e.getMessage());
            }
        }

        return students;
    }

    public boolean findById(Student student){
        PreparedStatement ps;
        ResultSet rs;
        Connection conn = ConnectionDB.getConnecttion();
        String sql = "SELECT * FROM student WHERE id_student = ? ";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, student.getIdStudent());
            rs = ps.executeQuery();
            if(rs.next()){
                student.setIdStudent(rs.getInt("id_student"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setPhoneNumber(rs.getString("phone_number"));
                student.setEmail(rs.getString("email"));

                return true;
            }
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }finally {
            try {
                conn.close();
            }catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return false;
    }

    public boolean addStudent(Student student){
        PreparedStatement ps;
        Connection conn = ConnectionDB.getConnecttion();
        String sql = "INSERT INTO student(first_name, last_name, phone_number, email) " + " VALUES(?, ?, ?, ?)";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setString(3, student.getPhoneNumber());
            ps.setString(4, student.getEmail());
            ps.execute();
            return true;
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }finally {
            try {
                conn.close();
            }catch (Exception e) {
                System.out.println("Connection error: " + e.getMessage());
            }
        }
        return false;
    }

    public boolean updateStudent(Student student){
        PreparedStatement ps;
        Connection conn = ConnectionDB.getConnecttion();
        String sql = "UPDATE student SET first_name=?, last_name=?, phone_number=?, email=? " + " WHERE id_student=?" ;

        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setString(3, student.getPhoneNumber());
            ps.setString(4, student.getEmail());
            ps.setInt(5, student.getIdStudent());
            ps.execute();
            return true;
        }catch (Exception e){
            System.out.println("Update error: " + e.getMessage());
        }finally {
            try {
                conn.close();
            }catch (Exception e){
                System.out.println("Connection close error" + e.getMessage());
            }
        }

        return false;
    }

    public boolean deleteStudent(Student student){
        PreparedStatement ps;
        Connection conn = ConnectionDB.getConnecttion();
        String sql = "DELETE FROM student WHERE id_student=?";

        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1, student.getIdStudent());
            ps.execute();
            return true;
        }catch (Exception e){
            System.out.println("Delete error: " + e.getMessage());
        }finally {
            try {
                conn.close();
            }catch (Exception e){
                System.out.println("Connection close error: " + e.getMessage());
            }
        }
        return false;
    }

    public static void main(String[] args) {
        StudentDAO student = new StudentDAO();

        // Add student
//        Student newStudent = new Student("Carlos", "Ponssa", "343443", "carlos@email.com");
//        boolean isAdded = student.addStudent(newStudent);
//        if(isAdded){
//            System.out.println("Se agrego el nuevo estudiante");
//        }else {
//            System.out.println("No se pudo agregar estudiante a la base de datos.");
//        }

        // Update student
//        Student studentToUpdate = new Student(1, "Miriam", "Hernandez", "2131213", "miriam@email.com");
//        boolean isUpdated = student.updateStudent(studentToUpdate);
//
//        if(!isUpdated){
//            System.out.println("Error al actualizar estudiante");
//        }else {
//            System.out.println("Estudiante actualizado");
//        }

        // Delete student
        Student studentToDelete = new Student(3);
        boolean isDeleted = student.deleteStudent(studentToDelete);
        if(!isDeleted){
            System.out.println("No se pudo borrar el estudiante con id: " + studentToDelete);
        }else {
            System.out.println("Se eliminó con éxito el estudiante");
        }

        // Find all students
        System.out.println("Listado de Estudiantes: ");
        List<Student> students =  student.findAllStudents();
        students.forEach(System.out::println);

        //Find student by id
        Student student1 = new Student(1);
         boolean findStudent = student.findById(student1);

        if(findStudent){
            System.out.println("Estudiante encontrado: " + student1);
        }else {
            System.out.println("No se encontró estudiante con el ID: " + student1.getIdStudent());
        }
    }
}


