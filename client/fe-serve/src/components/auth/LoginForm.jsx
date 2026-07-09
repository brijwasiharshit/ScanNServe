import { Mail } from "lucide-react";
import Button from "../common/Button";
import Checkbox from "../common/Checkbox";
import ErrorAlert from "../common/ErrorAlert";
import InputField from "../common/InputField";
import PasswordField from "../common/PasswordField";

export default function LoginForm({
    formData,
    loading,
    error,
    onChange,
    onSubmit,
}) {
    return (
        <form className="w-full max-w-md" onSubmit={onSubmit}>
            <ErrorAlert message={error} />

            <InputField
                label="Email Address"
                icon={Mail}
                type="email"
                name="userName"
                value={formData.userName}
                onChange={onChange}
                placeholder="Enter your email"
                className="mb-5"
            />

            <PasswordField
                label="Password"
                name="password"
                value={formData.password}
                onChange={onChange}
                placeholder="Enter your password"
                className="mb-3"
            />

            <div className="mb-6 flex items-center justify-between text-sm">
                <Checkbox
                    name="rememberMe"
                    checked={formData.rememberMe}
                    onChange={onChange}
                    label="Remember me"
                />

                <button
                    type="button"
                    className="font-medium text-[#F59E0B]"
                >
                    Forgot Password?
                </button>
            </div>

            <Button type="submit" disabled={loading} className="w-full py-3.5">
                {loading ? "Signing In..." : "Sign In"}
            </Button>

            <div className="relative my-8">
                <div className="border-t border-slate-200" />
                <span className="absolute -top-3 left-1/2 -translate-x-1/2 bg-white px-4 text-sm text-slate-400">
                    OR
                </span>
            </div>

            <Button variant="ghost" className="w-full py-3.5">
                Continue with Google
            </Button>
        </form>
    );
}
