import { useState } from "react";
import Button from "../common/Button";

export default function TableForm({ onSubmit }) {
    const [tableNumber, setTableNumber] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit({ tableNumber });
    };

    return (
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
            <div>
                <label className="mb-2 block text-sm font-medium text-slate-700">
                    Table Number
                </label>
                <input
                    type="text"
                    value={tableNumber}
                    onChange={(e) => setTableNumber(e.target.value)}
                    required
                    className="w-full rounded-xl border border-slate-200 px-4 py-3 focus:border-[#F97316] focus:outline-none focus:ring-1 focus:ring-[#F97316]"
                    placeholder="e.g., 12 or Patio-1"
                />
            </div>

            <Button type="submit" className="mt-4">
                Create Table
            </Button>
        </form>
    );
}
