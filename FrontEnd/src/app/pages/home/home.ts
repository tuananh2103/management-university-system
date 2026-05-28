import { Component, OnInit , ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { finalize } from 'rxjs';

import { DashboardSummary } from './home.model';
import { HomeApiService } from './home.api.service';
import { AuthSessionService } from '../login/admin.service';
import { AuthUser } from '../login/login.model';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class HomeComponent implements OnInit {
  summary: DashboardSummary | null = null;
  currentUser: AuthUser | null = null;

  loading = false;
  error = '';

  constructor(
    private homeApi: HomeApiService,
    private authSession: AuthSessionService, 
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authSession.getUser();
    this.loadSummary();
  }

  loadSummary(): void {
    this.loading = true;
    this.error = '';

    this.homeApi
      .getDashboardSummary()
      .pipe(
        finalize(() => {
          this.loading = false;
          this.cdr.detectChanges();
        })
      )
      .subscribe({
        next: (summary) => {
          this.summary = summary;
        },
        error: (err) => {
          console.error('Dashboard API error:', err);
          this.error = `Failed to load dashboard summary. Status: ${err.status}. Message: ${err.message}`;
          this.loading = false;
        },
      });
  }
}