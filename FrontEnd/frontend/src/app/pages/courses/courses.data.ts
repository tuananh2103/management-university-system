import { Course } from './courses.model';

/**
 * Static in-memory dataset of offered courses grouped by semester.
 * This is used by the demo/standalone `CoursesComponent` to populate
 * the UI. In a real app this would come from an API/backend.
 */
export const COURSES_BY_SEMESTER: Record<number, Course[]> = {
  1: [
    { id: 0, courseCode: 'HUM110', title: 'Islamic Studies', credits: 3, lectureHours: 3, labHours: 0, teachers: ['HYBRID'], semester: 1, status: 'OPEN' },
    { id: 1, courseCode: 'HUM100', title: 'English Composition', credits: 3, lectureHours: 3, labHours: 0, teachers: ['Ms. Mehwish Mushtaq'], semester: 1, status: 'OPEN' },
    { id: 2, courseCode: 'PHY121', title: 'Applied Physics', credits: 4, lectureHours: 3, labHours: 1, teachers: ['Mr. Muhammad Moosa'], semester: 1, status: 'OPEN' },
    { id: 3, courseCode: 'CSC101', title: 'ICT', credits: 3, lectureHours: 3, labHours: 0, teachers: ['Mr. Umar Iqbal'], semester: 1, status: 'OPEN' },
    { id: 4, courseCode: 'MTH101', title: 'Calculus - I', credits: 0, lectureHours: 0, labHours: 0, teachers: ['Ms. Itrat Rubab'], semester: 1, status: 'OPEN' },
    { id: 5, courseCode: 'MTH100', title: 'Mathematics I', credits: 0, lectureHours: 0, labHours: 0, teachers: ['Ms. Itrat Rubab'], semester: 1, status: 'OPEN' },
  ],

  2: [
    { id: 0, courseCode: 'HUM102', title: 'RWS', credits: 3, lectureHours: 3, labHours: 0, teachers: ['HYBRID'], semester: 2, status: 'OPEN' },
    { id: 1, courseCode: 'EEE241', title: 'DLD', credits: 4, lectureHours: 3, labHours: 1, teachers: ['Dr. Ahsan Khawaja'], semester: 2, status: 'OPEN' },
    { id: 2, courseCode: 'MTH104', title: 'Calculus', credits: 3, lectureHours: 3, labHours: 0, teachers: ['Dr. Muhammad Yousaf'], semester: 2, status: 'OPEN' },
    { id: 3, courseCode: 'CSC102', title: 'DS', credits: 3, lectureHours: 3, labHours: 0, teachers: ['Ms. Memoona Malik'], semester: 2, status: 'OPEN' },
    { id: 4, courseCode: 'CSC110', title: 'PPIT', credits: 3, lectureHours: 3, labHours: 0, teachers: ['Mr. Anwar Shoukat'], semester: 2, status: 'OPEN' },
    { id: 5, courseCode: 'CSC103', title: 'PF', credits: 4, lectureHours: 3, labHours: 1, teachers: ['Mr. Rizwan Rashid'], semester: 2, status: 'OPEN' },
  ], 

  3: [
    { id: 0, courseCode: 'HUM103', title: 'Communication Skills', credits: 3, lectureHours: 3, labHours: 0, teachers: ['Dr. Mahmood ul Hassan'], semester: 3, status: 'OPEN' },
    { id: 1, courseCode: 'BIO231', title: 'Genetics', credits: 4, lectureHours: 3, labHours: 1, teachers: ['Dr. Kafayat Ullah'], semester: 3, status: 'OPEN' },
    { id: 2, courseCode: 'MTH105', title: 'Multivariable Calculus', credits: 3, lectureHours: 3, labHours: 0, teachers: ['Dr. Bilal Ashraf'], semester: 3, status: 'OPEN' },
    { id: 3, courseCode: 'CSC211', title: 'DSA', credits: 4, lectureHours: 3, labHours: 1, teachers: ['Dr. Inayat ur Rehman'], semester: 3, status: 'OPEN' },
    { id: 4, courseCode: 'CSC241', title: 'OOP', credits: 4, lectureHours: 3, labHours: 1, teachers: ['Dr. Zobia Rehman'], semester: 3, status: 'OPEN' },
  ],

  4: [
    { id: 0, courseCode: 'MTH231', title: 'Linear Algebra', credits: 3, lectureHours: 3, labHours: 0, teachers: ['Dr. Saima Noreen'], semester: 4, status: 'OPEN' },
    { id: 1, courseCode: 'MTH206', title: 'Statistics & Probability', credits: 3, lectureHours: 3, labHours: 0, teachers: ['Dr. Masood Anwar'], semester: 4, status: 'OPEN' },
    { id: 2, courseCode: 'CSC322', title: 'Operating Systems', credits: 3, lectureHours: 2, labHours: 1, teachers: ['Ms. Zahida Walayat'], semester: 4, status: 'OPEN' },
    { id: 3, courseCode: 'CSC371', title: 'Database Systems I', credits: 4, lectureHours: 3, labHours: 1, teachers: ['Mr. Qasim Malik'], semester: 4, status: 'OPEN' },
    { id: 4, courseCode: 'CSC291', title: 'Software Engineering', credits: 3, lectureHours: 3, labHours: 0, teachers: ['Mr. Tehseen Riaz'], semester: 4, status: 'OPEN' },
  ],

  5: [
    { id: 0, courseCode: 'MTH231', title: 'Linear Algebra', credits: 3, lectureHours: 3, labHours: 0, teachers: ['Ms. Fareeha Pervaiz'], semester: 5, status: 'OPEN' },
    { id: 1, courseCode: 'MTH206', title: 'Statistics & Probability', credits: 3, lectureHours: 3, labHours: 0, teachers: ['Mr. Abdul Wakeel'], semester: 5, status: 'OPEN' },
    { id: 2, courseCode: 'CSC339', title: 'Data Communications', credits: 3, lectureHours: 2, labHours: 1, teachers: ['Dr. Ashfaq Ahmed'], semester: 5, status: 'OPEN' },
    { id: 3, courseCode: 'CSC336', title: 'Web Technologies', credits: 3, lectureHours: 2, labHours: 1, teachers: ['Dr. Waseem Abbas'], semester: 5, status: 'OPEN' },
    { id: 4, courseCode: 'CSC462', title: 'Artificial Intelligence', credits: 3, lectureHours: 2, labHours: 1, teachers: ['Ms Shehla Saif'], semester: 5, status: 'OPEN' },
  ],

  6: [
    { id: 0, courseCode: 'CSC478', title: 'Numerical Computing', credits: 3, lectureHours: 3, labHours: 0, teachers: ['Mr. Tanveer Ahmed'], semester: 6, status: 'OPEN' },
    { id: 1, courseCode: 'CSC312', title: 'Theory of Automata', credits: 3, lectureHours: 3, labHours: 0, teachers: ['Mr. Muhammad Mustafa'], semester: 6, status: 'OPEN' },
    { id: 2, courseCode: 'CSC303', title: 'Mobile App Dev', credits: 3, lectureHours: 2, labHours: 1, teachers: ['Mr. Zaheer ul Hassan'], semester: 6, status: 'OPEN' },
    { id: 3, courseCode: 'CSC354', title: 'Machine Learning', credits: 3, lectureHours: 3, labHours: 0, teachers: ['Dr. Usman Yasin'], semester: 6, status: 'OPEN' },
    { id: 4, courseCode: 'CSC350', title: 'Web Technologies', credits: 3, lectureHours: 3, labHours: 0, teachers: ['Mr. Rashid Mukhtar'], semester: 6, status: 'OPEN' },
  ],

  7: [
    { id: 0, courseCode: 'CSC441', title: 'Compiler Construction', credits: 3, lectureHours: 2, labHours: 1, teachers: ['Mr. Salman Aslam'], semester: 7, status: 'OPEN' },
    { id: 1, courseCode: 'CSC356', title: 'Computer Interaction', credits: 3, lectureHours: 2, labHours: 1, teachers: ['Ms. Gulmina Rextina'], semester: 7, status: 'OPEN' },
    { id: 2, courseCode: 'HUM111', title: 'Pakistan Studies', credits: 3, lectureHours: 3, labHours: 0, teachers: ['HYBRID'], semester: 7, status: 'OPEN' },
    { id: 3, courseCode: 'EEE440', title: 'Computer Architecture', credits: 3, lectureHours: 3, labHours: 0, teachers: ['Mr. Dilshad Sabir'], semester: 7, status: 'OPEN' },
    { id: 4, courseCode: 'CSC350', title: 'Web Technologies', credits: 3, lectureHours: 3, labHours: 0, teachers: ['Ms. Humaira Waqas'], semester: 7, status: 'OPEN' },
    { id: 5, courseCode: 'CSC454', title: 'Pattern Recognition', credits: 3, lectureHours: 3, labHours: 0, teachers: ['Mr. Bilal Aurangzaib'], semester: 7, status: 'OPEN' },
    { id: 6, courseCode: 'CSC498', title: 'Senior Design Project I', credits: 2, lectureHours: 0, labHours: 2, teachers: ['Dr. Sohail Asghar'], semester: 7, status: 'OPEN' },
  ],

  8: [
    { id: 0, courseCode: 'BIO310', title: 'Intro to Bioinformatics', credits: 4, lectureHours: 3, labHours: 1, teachers: ['Dr. Muhammad Sajjad'], semester: 8, status: 'OPEN' },
    { id: 1, courseCode: 'MGT131', title: 'Financial Accounting', credits: 3, lectureHours: 3, labHours: 0, teachers: ['Dr. Naveed Raza'], semester: 8, status: 'OPEN' },
    { id: 2, courseCode: 'HUM434', title: 'Chinese', credits: 3, lectureHours: 3, labHours: 0, teachers: ['Mr. Yasir Muhammad'], semester: 8, status: 'OPEN' },
    { id: 3, courseCode: 'CSC499', title: 'SDP-II', credits: 4, lectureHours: 0, labHours: 4, teachers: ['Dr. Sohail Asghar'], semester: 8, status: 'OPEN' },
    { id: 4, courseCode: 'MTH242', title: 'Differential Equations', credits: 3, lectureHours: 3, labHours: 0, teachers: ['Mr. Saad Raza'], semester: 8, status: 'OPEN' },
  ],
};
