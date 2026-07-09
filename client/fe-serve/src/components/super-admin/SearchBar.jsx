import { Search } from "lucide-react";

export default function SearchBar({ value, onChange }) {
    return (
        <label className="mt-5 flex items-center gap-3 rounded-xl border border-[#E2E8F0] bg-white px-4 py-3">
            <Search size={18} className="text-slate-400" />

            <input
                value={value}
                onChange={(event) => onChange(event.target.value)}
                placeholder="Search..."
                className="min-w-0 flex-1 outline-none"
            />
        </label>
    );
}
