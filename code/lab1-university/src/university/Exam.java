package university;
 
/**
* This class represents an exam.
*     
* It manages the single exam and the basic operation upon.
*
*/

public class Exam {

    private int studentId;
    private int courseCode;
    private int examEvaluation;

    /**
	 * Constructor
	 * @param studentId Id of the student
     * @param courseCode Code of the course
     * @param examEvaluation Evaluation of the exam (0-30)
	 */

    public Exam(int studentId, int courseCode, int examEvaluation) {

        this.studentId = studentId;
        this.courseCode = courseCode;
        this.examEvaluation = examEvaluation;

    }

    public void setStudentId(int studentId) {

        this.studentId = studentId;

    }

    public void setCourseCode(int courseCode) {

        this.courseCode = courseCode;

    }

    public void setExamEvaluation(int examEvaluation) {

        this.examEvaluation = examEvaluation;

    }

    public int getExamEvaluation() {

        return examEvaluation;

    }

    public int getStudentId() {

        return studentId;

    }

    public int getCourseCode() {

        return courseCode;

    }

}
