import { Eye, EyeOff, Lock } from "lucide-react";
import InputField from "./InputField";
import { useState } from "react";

export default function PasswordField(props) {
    const [showPassword, setShowPassword] = useState(false);

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    return (
        <div className="relative">
            <InputField
                icon={Lock}
                type={showPassword ? "text" : "password"}
                inputClassName="pr-7"
                {...props}
            />

            <button
                type="button"
                onClick={togglePasswordVisibility}
                className="absolute bottom-4 right-4 text-slate-400 hover:text-slate-600 focus:outline-none"
            >
                {showPassword ? (
                    <EyeOff size={18} />
                ) : (
                    <Eye size={18} />
                )}
            </button>
        </div>
    );
}
