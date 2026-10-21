package university;

/**
* This class represents an course given by a university.
*     
* It manages the course.
*
*/

public class Course {

    private static final int MAX_ATTENDEES = 100;

    private int courseId;
    private String title;
    private String teacher;

    private int participantsNumber;
    private int examTakenNumber;

    private Student[] participants;
    private Exam[] exams;
    
	/**
	 * Constructor
	 * @param courseId Id of the course
     * @param title Title of the course
     * @param teacher Name of the teacher of the course
	 */

    public Course(int courseId, String title, String teacher) {


        this.courseId = courseId;
        this.title = title;
        this.teacher = teacher;

        this.participants = new Student[MAX_ATTENDEES];
        this.exams = new Exam[MAX_ATTENDEES];

    }

    public int getCourseId() {

        return courseId;

    }

    public String getTitle() {

        return title;

    }

    public String getTeacher() {

        return teacher;

    }

    public int getParticipantsNumber() {

        return participantsNumber;

    }

    public void setParticipantsNumber() {

        participantsNumber++;

    }

     public Student[] getParticipants() {

        return participants;

    }

    public void setStudent(Student student, Course course, int i){

        course.participants[i] = student; 

    }

    public Exam[] getExams() {

        return exams;

    }

    public void setExams(Exam exam) {

        exams[examTakenNumber] = exam;
        examTakenNumber++;

    }

    public int getTakenExamNumber() {

        return examTakenNumber;

    }
   
}