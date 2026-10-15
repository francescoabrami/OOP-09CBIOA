package university;
import java.util.logging.Logger;

/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {

	private String name;
	private String rectorFirst;
	private String rectorLast;

// R1
	/**
	 * Constructor
	 * @param name name of the university
	 */
	public University(String name){
		// Example of logging
		// logger.info("Creating extended university object");
		//TODO: to be implemented

		this.name = name;
	}
	
	/**
	 * Getter for the name of the university
	 * 
	 * @return name of university
	 */
	public String getName(){
		//TODO: to be implemented
		return name;
	}
	
	/**
	 * Defines the rector for the university
	 * 
	 * @param first first name of the rector
	 * @param last	last name of the rector
	 */
	public void setRector(String first, String last){
		//TODO: to be implemented
		rectorFirst = first;
		rectorLast = last;
	}
	
	/**
	 * Retrieves the rector of the university with the format "First Last"
	 * 
	 * @return name of the rector
	 */
	public String getRector(){
		//TODO: to be implemented

		return rectorFirst + " " + rectorLast;
	}

	private static final int MAX_STUDENTS = 1000;
	private static final int FIRST_ID = 10000;

	private String[] studentFirst = new String[MAX_STUDENTS];
	private String[] studentLast = new String[MAX_STUDENTS];
	private int studentNum = 0;

// R2
	/**
	 * Enrol a student in the university
	 * The university assigns ID numbers 
	 * progressively from number 10000.
	 * 
	 * @param first first name of the student
	 * @param last last name of the student
	 * 
	 * @return unique ID of the newly enrolled student
	 */
	public int enroll(String first, String last){

		//TODO: to be implemented

		if (studentNum < MAX_STUDENTS) {

			studentFirst[studentNum] = first;
			studentLast[studentNum] = last;
			studentNum++;

		} 

		int id = FIRST_ID + studentNum - 1;

		return id;

	}
	
	/**
	 * Retrieves the information for a given student.
	 * The university assigns IDs progressively starting from 10000
	 * 
	 * @param id the ID of the student
	 * 
	 * @return information about the student
	 */
	public String student(int id){
	
		int index = id - FIRST_ID;

		if (index < 0 || index >= studentNum) {
			return null;
		}

		return id + " " + studentFirst[index] + " " + studentLast[index];
	}

	private static final int MAX_COURSES = 50;
	private static final int FIRST_COURSE_CODE = 10;

	private String[] courseTitles = new String[MAX_COURSES];
	private String[] courseTeachers = new String[MAX_COURSES];
	private int courseNum = 0;	

// R3
	/**
	 * Activates a new course with the given teacher
	 * Course codes are assigned progressively starting from 10.
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * 
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher) {

    	if (courseNum >= MAX_COURSES) {
        	return -1;
    	}

    	int code = FIRST_COURSE_CODE + courseNum;

    	courseTitles[courseNum] = title;
    	courseTeachers[courseNum] = teacher;

    	courseNum++;

    return code;
	}
	
	/**
	 * Retrieve the information for a given course.
	 * 
	 * The course information is formatted as a string containing 
	 * code, title, and teacher separated by commas, 
	 * e.g., {@code "10,Object Oriented Programming,James Gosling"}.
	 * 
	 * @param code unique code of the course
	 * 
	 * @return information about the course
	 */
	public String course(int code){
		
		int index = code - FIRST_COURSE_CODE;

		if (index < 0 || index >= courseNum) {
			return null;
		}

		return code + "," + courseTitles[index] + "," + courseTeachers[index];
	}


	private static final int MAX_ATTENDEES = 100;
	private static final int MAX_STUDENT_COURSES = 25;


	private int[][] attendees = new int[MAX_COURSES][MAX_ATTENDEES];
	private int[] attendeeNum = new int[MAX_COURSES];

	private int[][] studentCourses = new int[MAX_STUDENTS][MAX_STUDENT_COURSES];
	private int[] studentCourseNum = new int[MAX_STUDENTS];	
	
// R4
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode){
		//TODO: to be implemented


		int studentIndex = studentID - FIRST_ID;
    	int courseIndex = courseCode - FIRST_COURSE_CODE;

		if (studentIndex < 0 || studentIndex >= studentNum || courseIndex < 0 || courseIndex >= courseNum) {
        
			return;

   		}

		for (int i = 0; i < studentCourseNum[studentIndex]; i++) {

        	if (studentCourses[studentIndex][i] == courseCode) {

            	return;

			}
        }

		if (studentCourseNum[studentIndex] >= MAX_STUDENT_COURSES || attendeeNum[courseIndex] >= MAX_ATTENDEES) {

        	return;

    	}

		
    	studentCourses[studentIndex][studentCourseNum[studentIndex]] = courseCode;
    	studentCourseNum[studentIndex]++;

    	attendees[courseIndex][attendeeNum[courseIndex]] = studentID;
    	attendeeNum[courseIndex]++;


    }


	/**
	 * Retrieve a list of attendees.
	 * 
	 * The students appear one per row (rows end with {@code'\n'}) 
	 * and each row is formatted as described in method {@link #student}
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode) {

    	int courseIndex = courseCode - FIRST_COURSE_CODE;

    	if (courseIndex < 0 || courseIndex >= courseNum) {

        	return null;

    	}

    	String result = "";

    	for (int i = 0; i < attendeeNum[courseIndex]; i++) {

        	result += student(attendees[courseIndex][i]) + "\n";

    	}

    	return result;
}

	/**
	 * Retrieves the study plan for a student.
	 * 
	 * The study plan is reported as a string having
	 * one course per line (i.e. separated by '\n').
	 * The courses are formatted as describe in method {@link #course}
	 * 
	 * @param studentID id of the student
	 * 
	 * @return the list of courses the student is registered for
	 */
	public String studyPlan(int studentID) {

    	int studentIndex = studentID - FIRST_ID;

    	if (studentIndex < 0 || studentIndex >= studentNum) {
       		return null;
    	}

    	String result = "";

    	for (int i = 0; i < studentCourseNum[studentIndex]; i++) {

        	int courseCode = studentCourses[studentIndex][i];

        	if (i > 0) {
            	result += "\n";
        	}

        	result += course(courseCode);
    	}

   		return result;
	}


// R5
	/**
	 * records the grade (integer 0-30) for an exam can 
	 * 
	 * @param studentId the ID of the student
	 * @param courseID	course code 
	 * @param grade		grade ( 0-30)
	 */
	public void exam(int studentId, int courseID, int grade) {
		
	}

	/**
	 * Computes the average grade for a student and formats it as a string
	 * using the following format 
	 * 
	 * {@code "Student STUDENT_ID : AVG_GRADE"}. 
	 * 
	 * If the student has no exam recorded the method
	 * returns {@code "Student STUDENT_ID hasn't taken any exams"}.
	 * 
	 * @param studentId the ID of the student
	 * @return the average grade formatted as a string.
	 */
	public String studentAvg(int studentId) {
		return null;
	}
	
	/**
	 * Computes the average grades of all students that took the exam for a given course.
	 * 
	 * The format is the following: 
	 * {@code "The average for the course COURSE_TITLE is: COURSE_AVG"}.
	 * 
	 * If no student took the exam for that course it returns {@code "No student has taken the exam in COURSE_TITLE"}.
	 * 
	 * @param courseId	course code 
	 * @return the course average formatted as a string
	 */
	public String courseAvg(int courseId) {
		return null;
	}
	

// R6
	/**
	 * Retrieve information for the best students to award a prize.
	 * 
	 * The students' score is evaluated as the average grade of the exams they've taken. 
	 * To take into account the number of exams taken and not only the grades, 
	 * a special bonus is assigned on top of the average grade: 
	 * the number of taken exams divided by the number of courses the student is enrolled to, multiplied by 10.
	 * The bonus is added to the exam average to compute the student score.
	 * 
	 * The method returns a string with the information about the three students with the highest score. 
	 * The students appear one per row (rows are terminated by a new-line character {@code '\n'}) 
	 * and each one of them is formatted as: {@code "STUDENT_FIRSTNAME STUDENT_LASTNAME : SCORE"}.
	 * 
	 * @return info on the best three students.
	 */
	public String topThreeStudents() {
		return null;
	}

// R7
    /**
     * This field points to the logger for the class that can be used
     * throughout the methods to log the activities.
     */
    public static final Logger logger = Logger.getLogger("University");

}
