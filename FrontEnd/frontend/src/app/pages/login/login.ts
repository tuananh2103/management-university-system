import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators , FormControl , FormGroup} from '@angular/forms';
type Login = 'IDLE' | 'LOADING' | 'SUCCESS' | 'ERROR';
type LoginForm = {
  username: FormControl<string>;
  password: FormControl<string>;
};

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class LoginComponent{
  status: Login = 'IDLE';
  errorMessage = '';
  form : FormGroup<LoginForm>;

   constructor(private fb: FormBuilder) {
    this.form = this.fb.nonNullable.group({
      username: this.fb.nonNullable.control('', [
        Validators.required,
        Validators.minLength(3),
      ]),
      password: this.fb.nonNullable.control('', [
        Validators.required,
        Validators.minLength(6),
      ]),
    });
   }
  

 

  submit() : void {
    this.errorMessage = '';
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
  
  this.status = 'LOADING';

  const { username, password } = this.form.getRawValue();
  
  // Simulate an API call
  setTimeout(() => {
    if (username === 'admin' && password === 'admin123') {
      this.status = 'SUCCESS';
      this.errorMessage = '';
    } else {
      this.status = 'ERROR';
      this.errorMessage = 'Invalid username or password.';
    }
  }, 1500);
  }

  get username(): FormControl<string> {
    return this.form.controls.username;
  }

  get password(): FormControl<string> {
    return this.form.controls.password;
  }
}
