package university;

/**
* This class represents a student.
* 
* It manages students and the operation done on them.
*
*/

public class Student {

    private static final int DIST_COURSES = 25;

    private int id;
    private String studentFirst;
    private String studentLast;
    private float studentAverage;
    
    private Exam[] exams;
    private Course[] courseFollowedNumber;

    private int courseNumber;
    private int examNumber;

    private float score;
    
    /**
	 * Constructor
	 * @param id Id of the student
     * @param studentFirst First name of the student
     * @param studentLast Last name of the student
	 */

    public Student(int id, String studentFirst, String studentLast) { 

        this.id = id;
        this.studentFirst = studentFirst;
        this.studentLast = studentLast;
        this.studentAverage = 0;

        this.courseNumber = 0;
        this.examNumber = 0; 

        this.exams = new Exam[DIST_COURSES];
        this.courseFollowedNumber = new Course[DIST_COURSES]; 

    }

    public String getStudentFirst() {

        return studentFirst;

    }

    public int getId() {

        return id;

    }

    public String getStudentLast() {

        return studentLast;
    }

    public int getCourseNumber() {

        return courseNumber;

    }
    
    public void setCourseNumber() {

        courseNumber++;

    }

    public void setCourses(Student student, Course course, int i){

        student.courseFollowedNumber[i] = course;

    }

    public Course[] getFollowedCourse() {

		return courseFollowedNumber;

	}

    public float getStudentAverage() {

        return studentAverage;

    }

    public void setAverage(float studenAverage) {

        this.studentAverage = studenAverage;

    }

    public int getExamNumber() {

        return examNumber;

    }

    public void setExamNumbers() {

        examNumber++;

    }

    public Exam[] getExams() {

        return exams;

    }

    public void setTakenExam(Exam exam) {

        this.exams[examNumber] = exam;
        examNumber++;

    }

    public float getEvaluation(int i){

        return exams[i].getExamEvaluation();

    }


    public float calculateScore(){ 

        float sum = 0;
      
        for(int i = 0; i < examNumber; i++){

            sum += exams[i].getExamEvaluation();
        }

        if(sum == 0){

            studentAverage = 0;
        }
        else {

            this.studentAverage= sum/examNumber;

        }

        this.score = studentAverage + ((examNumber/(courseNumber)) * 10);
        
        return score;
    }

     public float getScore() {

        return score;

    }

}
