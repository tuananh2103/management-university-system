export interface LoginRequest {
  username: string;
  password: string;
}

export interface AuthUser {
  id: number;
  username: string;
  fullName: string;
  role: string;
}

export interface LoginResponse {
  token: string;
  user: AuthUser;
}