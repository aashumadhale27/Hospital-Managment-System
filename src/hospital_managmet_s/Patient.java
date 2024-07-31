
	package hospital_managmet_s;

	import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

	public class Patient {
		 private Connection connection;
		    private Scanner scanner;

		    public Patient(Connection connection, Scanner scanner){
		        this.connection = connection;
		        this.scanner = scanner;
		    } public void addPatient(){
		        System.out.print("Enter Patient Name: ");
		        String name = scanner.next();
		        System.out.print("Enter Patient Age: ");
		        int age = scanner.nextInt();
		        System.out.print("Enter Patient Gender: ");
		        String gender = scanner.next();

		        try{
		            String query = "INSERT INTO patients(name, age, gender) VALUES(?, ?, ?)";
		            PreparedStatement preparedStatement = connection.prepareStatement(query);
		            preparedStatement.setString(1, name);
		            preparedStatement.setInt(2, age);
		            preparedStatement.setString(3, gender);
		            int affectedRows = preparedStatement.executeUpdate();
		            if(affectedRows>0){
		                System.out.println("Patient Added Successfully!!");
		            }else{
		                System.out.println("Failed to add Patient!!");
		            }
		            

		        }catch (SQLException e){
		            e.printStackTrace();
		        }
		    }

		    public void viewPatients(){
		        String query = "select * from patients";
		        try{
		            PreparedStatement preparedStatement = connection.prepareStatement(query);
		            ResultSet resultSet = preparedStatement.executeQuery();
		            System.out.println("Patients: ");
		            System.out.println("+------------+--------------------+----------+------------+");
		            System.out.println("| Patient Id | Name               | Age      | Gender     |");
		            System.out.println("+------------+--------------------+----------+------------+");
		            while(resultSet.next()){
		                int id = resultSet.getInt("id");
		                String name = resultSet.getString("name");
		                int age = resultSet.getInt("age");
		                String gender = resultSet.getString("gender");
		                System.out.printf("| %-10s | %-18s | %-8s | %-10s |\n", id, name, age, gender);
		                System.out.println("+------------+--------------------+----------+------------+");
		            }

		        }catch (SQLException e){
		            e.printStackTrace();
		        }
		    }
		    
		    public void deletePatient() {
		        try {
		            System.out.print("Enter Patient ID to delete: ");
		            int id = scanner.nextInt();  // Read patient ID from user input
		            
		            String query = "DELETE FROM patients WHERE id=?";
		            PreparedStatement preparedStatement = connection.prepareStatement(query);
		            preparedStatement.setInt(1, id);
		            
		            int affectedRows = preparedStatement.executeUpdate();
		            if (affectedRows > 0) {
		                System.out.println("Patient with ID " + id + " deleted successfully!");
		            } else {
		                System.out.println("No patient found with ID " + id);
		            }
		            
		        } catch (InputMismatchException e) {
		            System.out.println("Please enter a valid integer for Patient ID.");
		            scanner.next();  // Consume invalid input to prevent infinite loop
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    public void updatePatient() {
		        try {
		            System.out.print("Enter Patient ID to update: ");
		            int id = scanner.nextInt();  // Read patient ID from user input
		            
		            // Check if the patient with the given ID exists
		            String checkQuery = "SELECT * FROM patients WHERE id=?";
		            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
		            checkStatement.setInt(1, id);
		            ResultSet resultSet = checkStatement.executeQuery();
		            
		            if (!resultSet.next()) {
		                System.out.println("Patient with ID " + id + " does not exist.");
		                return;
		            }
		            
		            // If patient exists, proceed with updating
		            System.out.println("Patient found. Enter new information:");

		            System.out.print("Enter new Patient Name: ");
		            String name = scanner.next();
		            System.out.print("Enter new Patient Age: ");
		            int age = scanner.nextInt();
		            System.out.print("Enter new Patient Gender: ");
		            String gender = scanner.next();

		            // Update query
		            String updateQuery = "UPDATE patients SET name=?, age=?, gender=? WHERE id=?";
		            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
		            updateStatement.setString(1, name);
		            updateStatement.setInt(2, age);
		            updateStatement.setString(3, gender);
		            updateStatement.setInt(4, id);

		            int affectedRows = updateStatement.executeUpdate();
		            if (affectedRows > 0) {
		                System.out.println("Patient with ID " + id + " updated successfully!");
		            } else {
		                System.out.println("Failed to update patient with ID " + id);
		            }

		        } catch (InputMismatchException e) {
		            System.out.println("Please enter valid input.");
		            scanner.next();  // Consume invalid input to prevent infinite loop
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }





		    

		    public boolean getPatientById(int id){
		        String query = "SELECT * FROM patients WHERE id = ?";
		        try{
		            PreparedStatement preparedStatement = connection.prepareStatement(query);
		            preparedStatement.setInt(1, id);
		            ResultSet resultSet = preparedStatement.executeQuery();
		            if(resultSet.next()){
		                return true;
		            }else{
		                return false;
		            }
		        }catch (SQLException e){
		            e.printStackTrace();
		        }
		        return false;
		    }

		

	}


