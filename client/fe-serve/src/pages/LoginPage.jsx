import LoginForm from "../components/auth/LoginForm";
import useLogin from "../hooks/useLogin";

export default function LoginPage() {
  const login = useLogin();

  return (
    <div className="min-h-screen bg-slate-50 flex items-center justify-center p-6">
      <div className="w-full max-w-6xl bg-white rounded-3xl overflow-hidden shadow-2xl border border-slate-200 flex">
        <div className="flex-1 p-8 md:p-14 flex items-center">
          <div className="w-full max-w-md mx-auto">
            <LoginForm
              formData={login.formData}
              loading={login.loading}
              error={login.error}
              onChange={login.handleChange}
              onSubmit={login.handleLogin}
            />
          </div>
        </div>
      </div>
    </div>
  );
}
