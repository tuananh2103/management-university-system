import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule} from '@angular/forms';
import { Course } from './courses.model';
@Component({
  selector: 'app-courses',
  standalone: true, // stand alone component no need NgModule,import dependency direct in component
  imports: [CommonModule ,  FormsModule],
  templateUrl: './courses.html',
  styleUrl: './courses.scss',
})
export class CoursesComponent {
  search = '';
  // Sample data for courses
      courses: Course[] = [
      {
      id: 1,
      courseCode: 'PRGC',
      title: 'Programation confiance',
      credits: 4,
      teachers: ['Masson Domique', 'Durand Paul'],
      semester: 1,
      status: 'OPEN',
      },
    {
      id: 2,
      courseCode: 'ALG',
      title: 'Algorithms',
      credits: 4,
      teachers: ['Garcia Maria', 'Lee David'],
      semester: 1,
      status: 'OPEN',
    },
    {
      id: 3,
      courseCode: 'MAG',
      title: 'Methodes algorithmiques',
      credits: 3,
      teachers: ['Smith John' ],
      semester: 1,
      status: 'CLOSED',
    },
    {
      id: 4,
      courseCode: 'OOP',
      title: 'Programation orientee objet',
      credits: 4,
      teachers: ['Johnson Emily', 'Brown Michael'],
      semester: 2,
      status: 'OPEN',
    },
    {
      id: 5,
      courseCode: 'OPC',
      title: 'Programation en C',
      credits: 5,
      teachers: ['Johnson Emily', 'Brown Michael'],
      semester: 1,
      status: 'OPEN',
    },
  ];

  get filteredCourses(): Course[] {
    const q = this.search.trim().toLowerCase();

    return this.courses.filter((course: Course) =>
      course.courseCode.toLowerCase().includes(q) ||
      course.title.toLowerCase().includes(q) ||
      course.teachers.some((teacher: string) =>
        teacher.toLowerCase().includes(q)
      )
    );
  }

  onAddCourse() {
    // Logic to add a new course by js
    console.log('Add Course button clicked');
  }
}
