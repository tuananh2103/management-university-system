export interface LoginRequest {
  username: string;
  password: string;
}

export interface AuthUser {
  id: number;
  username: string;
  fullName: string;
  role: string;
  studentRegNumber: string | null;
}

export interface LoginResponse {
  token: string;
  user: AuthUser;
}