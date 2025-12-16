export interface Course {
  id: number;
  courseCode: string;
  title: string;
  credits: number;
  teachers: string[];   
  semester: 1 | 2;
  status: 'OPEN' | 'CLOSED';
}
