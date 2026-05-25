export interface Student {
  id: number;
  studentCode: string;
  fullName: string;
  major: string;
  year: number;
  status: 'ACTIVE' | 'INACTIVE';
}
// Backend model for Student entity
// Fields: id, studentCode, fullName, major, year, status
// Frontend need to fix when consuming API
