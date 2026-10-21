package university;
import java.util.logging.Logger;

/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */

public class University {

	private static final int FIRST_COURSE_CODE 	= 10;
	private static final int DIST_COURSES		= 25;
	private static final int MAX_COURSE 		= 50;	
	private static final int MAX_ATTENDEES		= 100;
	private static final int MAX_STUDENTS 		= 1000;
	private static final int FIRST_ID 			= 10000;
	
	private String name;
	private String rectorFirst;
	private String rectorLast;

    private Student[] students = new Student[MAX_STUDENTS];
    private int nextStudentId = FIRST_ID; 

	private Course[] courses = new Course[MAX_COURSE];
	private int courseCode = FIRST_COURSE_CODE;

	/**
	 * Constructor
	 * @param name name of the university
	 */

	public University(String name){

		this.name = name;
	}
	
	/**
	 * Getter for the name of the university
	 * 
	 * @return name of university
	 */

	public String getName(){

		return name;

	}
	
	/**
	 * Defines the rector for the university
	 * 
	 * @param first first name of the rector
	 * @param last	last name of the rector
	 */

	public void setRector(String first, String last){

		this.rectorFirst = first;
		this.rectorLast = last;

	}
	
	/**
	 * Retrieves the rector of the university with the format "First Last"
	 * 
	 * @return name of the rector
	 */

	public String getRector(){

		return this.rectorFirst + " " + this.rectorLast;
		
	}

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
	
	public int enroll(String first, String last) {

   		if (nextStudentId - FIRST_ID >= students.length) {

        	return -1;

    	}

    	int id = nextStudentId++;

    	Student student = new Student(id, first, last);
		students[id - FIRST_ID] = student;

		logger.info("New student enrolled: "+nextStudentId+" "+student.getStudentFirst()+" "+student.getStudentLast());
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

		if (id >= FIRST_ID && id < FIRST_ID + students.length) {

			Student student = students[id - FIRST_ID];
			
			if (student != null) {

				return student.getId() + " " + student.getStudentFirst() + " " + student.getStudentLast() + "\n";

			}
		}
	
		return "Student not found";

	}

	/**
	 * Activates a new course with the given teacher
	 * Course codes are assigned progressively starting from 10.
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * 
	 * @return the unique code assigned to the course
	 */

	public int activate(String title, String teacher){

		if (courseCode - FIRST_COURSE_CODE >= courses.length) {

			return -1;

		}
	
		int code = courseCode++;
		Course course = new Course(code, title, teacher);
		courses[code - FIRST_COURSE_CODE] = course;

		logger.info("New course activated: " + code + ", " + course.getTitle() + ", " + course.getTeacher());
	
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

	public String course(int code) {

    	if (code >= FIRST_COURSE_CODE && code < FIRST_COURSE_CODE + courses.length) {

        	Course course = courses[code - FIRST_COURSE_CODE];

        	if (course != null) {

            	return course.getCourseId() + "," +
                   	course.getTitle() + "," +
                   	course.getTeacher();
        	}
    	}	

    	return "Course not found";
	}
	
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */

	public void register(int studentID, int courseCode) {

    	Student student = students[studentID - FIRST_ID];
    	Course course = courses[courseCode - FIRST_COURSE_CODE];

    	int courseNumber = student.getCourseNumber();
    	int participantsNumber = course.getParticipantsNumber();

    	if (courseNumber < DIST_COURSES && participantsNumber < MAX_ATTENDEES) {

        	student.setCourses(student, course, courseNumber);
        	student.setCourseNumber();

        	course.setStudent(student, course, participantsNumber);
        	course.setParticipantsNumber();

			logger.info("Student: " + student + "signed up for course " + course);
    	}
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

		if (courseCode < FIRST_COURSE_CODE|| courseCode >= FIRST_COURSE_CODE + courses.length) {

			return "Code not valid!";

		}
	
		Course course = courses[courseCode - FIRST_COURSE_CODE];
	
		if (course == null) {

			return "The course is not attended by anyone!";

		}
	
		int n = course.getParticipantsNumber();
		Student[] participants = course.getParticipants();
		StringBuffer sb = new StringBuffer();
	
		if (n == 0) {

			return "No student is enrolled in this course!";

		}
	
		for (int i = 0; i < n; i++) {

			int id = participants[i].getId();
			sb.append(student(id));

		}
	
		return sb.toString();

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

	public String studyPlan(int studentID){

		Student student = students[studentID - FIRST_ID];
		int n = student.getCourseNumber();
		Course[] enrolledCoursed = student.getFollowedCourse();
		StringBuffer sb = new StringBuffer();

		if (n == 0) {

			return "This student is not enrolled in any course!";

		}

		for (int i = 0; i < n; i++) {

    		int id = enrolledCoursed[i].getCourseId();
    		sb.append(course(id));

    		if (i < n - 1) {

        		sb.append("\n");

    			}
		}

		return sb.toString();
	}

	/**
	 * records the grade (integer 0-30) for an exam can 
	 * 
	 * @param studentId the ID of the student
	 * @param courseID	course code 
	 * @param grade		grade ( 0-30)
	 */

	public void exam(int studentId, int courseId, int grade) {

		if(grade < 0 || grade > 30){

			return;
		}

		Student student = students[studentId - FIRST_ID];
		Exam exam = new Exam(studentId, courseId, grade);
		student.setTakenExam(exam);
		
		Course course = courses[courseId - FIRST_COURSE_CODE];
		course.setExams(exam);

		logger.info("Student: " + student + "took an exam in course " + course + " with grade " + grade);
	
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

		Student student = students[studentId - FIRST_ID];
		int examNumber = student.getExamNumber();
		int gradeSum = 0;
	
		if (examNumber == 0) {

			return "Student " + studentId + " hasn't taken any exams";

		}
	
		Exam[] esami = student.getExams();
	
		for (int i = 0; i < examNumber; i++) {

			if (esami[i] != null) {

				int grade = esami[i].getExamEvaluation();

				if (grade >= 0 && grade <= 30) {
					
					gradeSum += grade;

				}
			}
		}
	
		if (examNumber == 0) {

			return "Student " + studentId + " hasn't taken any exams!";

		}
	
		float average = gradeSum / examNumber;
		student.setAverage(average);

		return "Student " + studentId + " : " + String.format("%.1f", average);
		
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

		Course course = courses[courseId - FIRST_COURSE_CODE];
		int examNumber = course.getTakenExamNumber();
		float gradeSum = 0;
	
		if (examNumber == 0) {

			return "No student has taken the exam in " + course.getTitle();

		}
	
		Exam[] exams = course.getExams();
		int takenExams = 0;
	
		for (int i = 0; i < examNumber; i++) {

			if (exams[i] != null) {

				int grade = exams[i].getExamEvaluation();

				if (grade >= 0 && grade <= 30) {

					gradeSum += grade;
					takenExams++;

				}
			}
		}
	
		if (takenExams == 0) {

			return "No student has taken the exam in " + course.getTitle();

		}
	
		float average = gradeSum / takenExams;
		return "The average for the course " + course.getTitle() + " is: " + String.format("%.1f", average);

	}
	
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
	
		StringBuffer sb = new StringBuffer();
		Student top1 = null, top2 = null, top3 = null;

		for(Student s : this.students){

			if(s == null) break;

			if(s.calculateScore() != 0){

				if(top1 == null || s.calculateScore() > top1.calculateScore()){
			
					if(top1 == null){

						top1=s;

					}
					else{

						top3=top2;
						top2=top1;
						top1=s;

					}
	
				}
				else if(top2 == null || s.calculateScore() > top2.calculateScore()){
		
					if(top2 == null){
						top2 = s;

					}
					else{

						top3 = top2;
						top2=s;

					}
				}
				else if(top3 == null || s.calculateScore() > top3.calculateScore()){
					
					top3=s;

				}
			}
			
		}

		if(top1 != null){

			sb.append(top1.getStudentFirst() + " " + top1.getStudentLast() +" : "+ top1.getScore() + '\n');

		}

		if(top2 != null){

			sb.append(top2.getStudentFirst() + " " + top2.getStudentLast() +" : "+ top2.getScore() + '\n');

		}

		if(top3 != null){

			sb.append(top3.getStudentFirst() + " " + top3.getStudentLast() +" : "+ top3.getScore() + '\n');

		}

		return sb.toString();
		
	}

    /**
     * This field points to the logger for the class that can be used
     * throughout the methods to log the activities.
     */
    public static final Logger logger = Logger.getLogger("University");

}
