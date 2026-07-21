export default function Button({
    children,
    className = "",
    variant = "primary",
    type = "button",
    ...props
}) {
    const variants = {
        primary: "bg-[#2563EB] hover:bg-[#1D4ED8] text-white shadow-lg",
        success: "bg-[#22C55E] hover:bg-green-600 text-white",
        warning: "bg-[#F59E0B] hover:opacity-90 text-white",
        danger: "bg-[#EF4444] hover:bg-red-600 text-white",
        ghost: "border border-slate-200 hover:bg-slate-50 text-slate-700",
    };

    return (
        <button
            type={type}
            className={`inline-flex items-center justify-center gap-2 rounded-xl px-5 py-3 font-semibold transition disabled:opacity-50 ${variants[variant]} ${className}`}
            {...props}
        >
            {children}
        </button>
    );
}
