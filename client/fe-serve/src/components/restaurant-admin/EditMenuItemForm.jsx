import { useState } from "react";
import Button from "../common/Button";

export default function EditMenuItemForm({ initialData, onSubmit }) {
    const [price, setPrice] = useState(initialData?.price || "");
    const [available, setAvailable] = useState(initialData?.available ?? true);
    const [selectedTag, setSelectedTag] = useState(initialData?.tag || "");

    const handleSubmit = (e) => {
        e.preventDefault();
        
        const parsedPrice = parseFloat(price);
        if (parsedPrice > 2000) {
            alert("Price cannot exceed Rs. 2000");
            return;
        }

        onSubmit({ 
            itemId: initialData.itemId, 
            price: parsedPrice, 
            // Blocking image editing right now, passing original if it exists
            customImage: initialData.customImage || initialData.image || null, 
            available,
            tag: selectedTag || null
        });
    };

    return (
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
            <div className="bg-slate-50 p-4 rounded-xl border border-slate-200 mb-2">
                <p className="text-sm text-slate-500 mb-1">Editing Item</p>
                <h3 className="text-lg font-bold text-[#1E40AF]">{initialData?.name || initialData?.itemName || "Menu Item"}</h3>
            </div>

            <div>
                <label className="mb-2 block text-sm font-medium text-slate-700">
                    Price (Rs.)
                </label>
                <input
                    type="number"
                    value={price}
                    onChange={(e) => setPrice(e.target.value)}
                    required
                    max="2000"
                    step="0.01"
                    className="w-full rounded-xl border border-slate-200 px-4 py-3 focus:border-[#2563EB] focus:outline-none focus:ring-1 focus:ring-[#2563EB]"
                    placeholder="Enter price (max 2000)"
                />
            </div>

            <div>
                <label className="mb-2 block text-sm font-medium text-slate-700">
                    Tag
                </label>
                <select
                    value={selectedTag}
                    onChange={(e) => setSelectedTag(e.target.value)}
                    className="w-full rounded-xl border border-slate-200 px-4 py-3 focus:border-[#2563EB] focus:outline-none focus:ring-1 focus:ring-[#2563EB] bg-white"
                >
                    <option value="">No tag</option>
                    <option value="BESTSELLER">Bestseller</option>
                    <option value="HIGH_PROTEIN">High Protein</option>
                    <option value="BUDGET_PICK">Budget Pick</option>
                    <option value="QUICK_BITE">Quick Bite</option>
                </select>
            </div>

            <div className="flex items-center gap-2 mt-2 p-2 border border-slate-200 rounded-lg bg-white">
                <input
                    type="checkbox"
                    id="edit-available"
                    checked={available}
                    onChange={(e) => setAvailable(e.target.checked)}
                    className="h-5 w-5 text-[#2563EB] focus:ring-[#2563EB] border-slate-300 rounded"
                />
                <label htmlFor="edit-available" className="text-base font-medium text-slate-700 cursor-pointer">
                    Available for Order
                </label>
            </div>

            <Button type="submit" className="mt-4 py-3 text-lg">
                Update Item
            </Button>
        </form>
    );
}
