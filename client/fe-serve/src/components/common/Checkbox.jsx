export default function Checkbox({ label, className = "", ...props }) {
    return (
        <label className={`inline-flex items-center gap-2 text-sm text-slate-600 ${className}`}>
            <input type="checkbox" className="h-4 w-4" {...props} />
            {label}
        </label>
    );
}
