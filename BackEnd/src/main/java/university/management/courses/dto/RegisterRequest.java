package university.management.courses.dto;

/**
 * Minimal DTO for registration requests.
 * Added to ensure the file has a package declaration and a basic structure
 * so the Java compiler and language server can index it.
 */
public class RegisterRequest {
	private String studentId;
	private String courseId;

	public RegisterRequest() {}

	public RegisterRequest(String studentId, String courseId) {
		this.studentId = studentId;
		this.courseId = courseId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
}
