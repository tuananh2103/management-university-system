export type StudentStatus = 'ACTIVE' | 'INACTIVE';

export interface Student {
  id: number;
  studentCode: string;
  regNumber: string;
  fullName: string;
  email: string;
  major: string;
  year: number;
  status: StudentStatus;
}

export interface CreateStudent {
  studentCode: string;
  regNumber: string;
  fullName: string;
  email: string;
  major: string;
  year: number;
  status: StudentStatus;
}

export interface UpdateStudent {
  studentCode: string;
  regNumber: string;
  fullName: string;
  email: string;
  major: string;
  year: number;
  status: StudentStatus;
}
// Backend model for Student entity
// Fields: id, studentCode, fullName, email, major, year, status
// Frontend need to fix when consuming API
