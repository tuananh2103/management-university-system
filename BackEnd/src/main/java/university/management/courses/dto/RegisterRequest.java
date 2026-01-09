package university.management.courses.dto;

import java.util.List;
/**
 * Minimal DTO for registration requests.
 * Added to ensure the file has a package declaration and a basic structure
 * so the Java compiler and language server can index it.
 */
public record RegisterRequest (String regNumber,int semester, List<Integer> courseIds) {
	
} 
