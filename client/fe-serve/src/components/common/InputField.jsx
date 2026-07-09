export default function InputField({
    icon: Icon,
    label,
    className = "",
    inputClassName = "",
    ...props
}) {
    return (
        <label className={`block ${className}`}>
            {label && (
                <span className="mb-2 block text-sm font-medium text-slate-700">
                    {label}
                </span>
            )}

            <span className="flex items-center gap-3 rounded-xl border border-slate-200 px-4 py-3 focus-within:border-[#0891B2]">
                {Icon && <Icon size={18} className="text-slate-400" />}

                <input
                    className={`min-w-0 flex-1 bg-transparent outline-none ${inputClassName}`}
                    {...props}
                />
            </span>
        </label>
    );
}
