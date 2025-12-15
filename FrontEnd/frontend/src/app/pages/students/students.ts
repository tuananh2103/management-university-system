import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Student } from './student.model';

@Component({
  selector: 'app-students',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './students.html',
  styleUrl: './students.scss',
})
export class StudentsComponent {
students: Student[] = [
    {
      id: 1,
      studentCode: '22108508',
      fullName: 'NGUYEN Tuan Anh',
      major: 'license Informatique parcours Informatique',
      year: 3,
      status: 'ACTIVE',
    },
  ];
};
