import { Eye, Lock } from "lucide-react";
import InputField from "./InputField";

export default function PasswordField(props) {
    return (
        <div className="relative">
            <InputField
                icon={Lock}
                type="password"
                inputClassName="pr-7"
                {...props}
            />

            <Eye
                size={18}
                className="pointer-events-none absolute bottom-4 right-4 text-slate-400"
            />
        </div>
    );
}
