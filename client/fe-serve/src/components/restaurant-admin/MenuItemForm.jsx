import { useState, useEffect } from "react";
import Button from "../common/Button";
import * as restaurantService from "../../services/restaurantService";

export default function MenuItemForm({ initialData, onSubmit, existingMenu = [] }) {
    const [itemId, setItemId] = useState(initialData?.itemId || "");
    const [price, setPrice] = useState(initialData?.price || "");
    const [customImage, setCustomImage] = useState(initialData?.image || initialData?.customImage || "");
    const [available, setAvailable] = useState(initialData?.available ?? true);
    
    // Quick search logic
    const [keyword, setKeyword] = useState("");
    const [searchResults, setSearchResults] = useState([]);
    const [selectedItemName, setSelectedItemName] = useState(initialData?.name || "");

    useEffect(() => {
        const fetchSearchResults = async () => {
            if (!keyword) {
                setSearchResults([]);
                return;
            }
            try {
                const res = await restaurantService.searchItems(keyword);
                const allItems = res.data || [];
                // Filter out items already in the menu
                const filtered = allItems.filter(
                    item => !existingMenu.some(existing => existing.itemId === item.itemId)
                );
                setSearchResults(filtered);
            } catch (err) {
                console.error("Search failed", err);
            }
        };

        const timer = setTimeout(fetchSearchResults, 300);
        return () => clearTimeout(timer);
    }, [keyword, existingMenu]);

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onloadend = () => {
                setCustomImage(reader.result);
            };
            reader.readAsDataURL(file);
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        
        const parsedPrice = parseFloat(price);
        if (parsedPrice > 2000) {
            alert("Price cannot exceed Rs. 2000");
            return;
        }

        if (!itemId) {
            alert("Please search and select an item first.");
            return;
        }

        onSubmit({ 
            itemId: parseInt(itemId, 10), 
            price: parsedPrice, 
            customImage, 
            available 
        });
    };

    return (
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
            {!initialData && (
                <div className="border border-slate-200 p-4 rounded-xl mb-4 bg-slate-50">
                    <label className="mb-2 block text-sm font-medium text-slate-700">
                        Search Items
                    </label>
                    {selectedItemName ? (
                        <div className="flex items-center justify-between bg-white p-3 rounded-lg border border-slate-200">
                            <span className="font-semibold text-[#C2410C]">{selectedItemName}</span>
                            <button 
                                type="button" 
                                className="text-xs text-red-500 hover:underline"
                                onClick={() => { setItemId(""); setSelectedItemName(""); setKeyword(""); }}
                            >
                                Change
                            </button>
                        </div>
                    ) : (
                        <div>
                            <input
                                type="text"
                                value={keyword}
                                onChange={(e) => setKeyword(e.target.value)}
                                className="w-full rounded-lg border border-slate-200 px-3 py-2 focus:border-[#F97316] focus:outline-none focus:ring-1 focus:ring-[#F97316]"
                                placeholder="Start typing to search..."
                            />
                            {searchResults.length > 0 && (
                                <div className="max-h-40 overflow-y-auto border border-slate-200 rounded mt-2 bg-white shadow-sm">
                                    {searchResults.map(item => (
                                        <div 
                                            key={item.itemId} 
                                            className="p-3 border-b last:border-b-0 cursor-pointer hover:bg-slate-100 flex justify-between items-center"
                                            onClick={() => { 
                                                setItemId(item.itemId); 
                                                setSelectedItemName(item.itemName); 
                                                setSearchResults([]); 
                                            }}
                                        >
                                            <span className="font-medium text-slate-700">{item.itemName}</span>
                                            <span className="text-xs text-slate-500 bg-slate-100 px-2 py-1 rounded">{item.categoryName}</span>
                                        </div>
                                    ))}
                                </div>
                            )}
                            {keyword && searchResults.length === 0 && (
                                <div className="text-sm text-slate-500 mt-2 p-2 text-center">
                                    No new items found.
                                </div>
                            )}
                        </div>
                    )}
                </div>
            )}

            <div>
                <label className="mb-2 block text-sm font-medium text-slate-700">
                    Price (Rs.)
                </label>
                <input
                    type="number"
                    value={price || ""}
                    onChange={(e) => setPrice(e.target.value)}
                    required
                    max="2000"
                    step="0.01"
                    className="w-full rounded-xl border border-slate-200 px-4 py-3 focus:border-[#F97316] focus:outline-none focus:ring-1 focus:ring-[#F97316]"
                    placeholder="Enter price (max 2000)"
                />
            </div>

            <div>
                <label className="mb-2 block text-sm font-medium text-slate-700">
                    Custom Image
                </label>
                <input
                    type="file"
                    accept="image/*"
                    onChange={handleImageChange}
                    className="w-full rounded-xl border border-slate-200 px-4 py-3 focus:border-[#F97316] focus:outline-none focus:ring-1 focus:ring-[#F97316] bg-white file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:text-sm file:font-semibold file:bg-[#F97316] file:text-white hover:file:bg-[#C2410C]"
                />
                <p className="text-xs text-slate-500 mt-1">Optional. Select an image from your device.</p>
                {customImage && (
                    <img src={customImage} alt="Preview" className="mt-2 w-16 h-16 object-cover rounded-lg border border-slate-200" />
                )}
            </div>

            <div className="flex items-center gap-2 mt-2">
                <input
                    type="checkbox"
                    id="available"
                    checked={available}
                    onChange={(e) => setAvailable(e.target.checked)}
                    className="h-4 w-4 text-[#F97316] focus:ring-[#F97316] border-slate-300 rounded"
                />
                <label htmlFor="available" className="text-sm font-medium text-slate-700">
                    Available
                </label>
            </div>

            <Button type="submit" className="mt-4">
                {initialData ? "Update Item" : "Subscribe Item"}
            </Button>
        </form>
    );
}
