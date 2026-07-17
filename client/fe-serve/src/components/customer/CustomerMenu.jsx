import { useState, useEffect } from "react";
import { ShoppingBag, Search, SlidersHorizontal, Leaf, Star } from "lucide-react";

export default function CustomerMenu({ menuData, categories, theme, addToCart, updateQuantity, getItemQuantity }) {
    const [selectedCategory, setSelectedCategory] = useState("");
    const [searchTerm, setSearchTerm] = useState("");

    // Set first category as default when categories load
    useEffect(() => {
        if (categories.length > 0 && !selectedCategory) {
            setSelectedCategory(categories[0]);
        }
    }, [categories, selectedCategory]);

    if (!menuData || !menuData.menu || menuData.menu.length === 0) {
        return (
            <div className="text-center text-slate-500 mt-10 p-4">
                <ShoppingBag size={48} className="mx-auto text-slate-300 mb-4" />
                <p>This restaurant has not added any menu items yet.</p>
            </div>
        );
    }

    // Filter items by selected category and search term
    const displayItems = menuData.menu.filter(item => {
        if (searchTerm) {
            return item.name.toLowerCase().includes(searchTerm.toLowerCase());
        }
        return selectedCategory === 'All' ? true : item.categoryName === selectedCategory;
    });

    const categoryIconMap = {
        'All': <div className="grid grid-cols-2 gap-[2px] w-4 h-4"><div className="border border-slate-500 rounded-sm"></div><div className="border border-slate-500 rounded-sm"></div><div className="border border-slate-500 rounded-sm"></div><div className="border border-slate-500 rounded-sm"></div></div>,
        'Breakfast': '🍞',
        'Chinese': '🍜',
        'Veg Main Course': '🥗',
        'Non Veg Main Course': '🍗',
        'Tandoori': '🍢',
        'Beverages': '🍹',
        'Desserts': '🍦',
        'Starters': '🥟'
    };

    // Make sure 'All' is at the front
    const displayCategories = ['All', ...categories.filter(c => c !== 'All')];

    // Set default category
    useEffect(() => {
        if (!selectedCategory) {
            setSelectedCategory('All');
        }
    }, [selectedCategory]);

    return (
        <div className="mt-2">
            {/* Sticky Header: Search + Categories */}
            <div className="sticky top-0 bg-[#FAFAFA] z-20 pb-2 shadow-sm">
                <div className="px-4 mb-4 mt-2">
                    <div className="flex items-center gap-3">
                        <div className="relative flex-1">
                            <Search className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-400" size={18} />
                            <input 
                                type="text" 
                                placeholder="Search for dishes, items..." 
                                value={searchTerm}
                                onChange={(e) => setSearchTerm(e.target.value)}
                                className="w-full pl-11 pr-4 py-3 bg-white border border-slate-100 rounded-full outline-none shadow-sm text-sm focus:border-[#4A7B4F]"
                            />
                        </div>
                        <button className="w-12 h-12 flex-shrink-0 bg-[#4A7B4F] text-white rounded-full flex items-center justify-center shadow-md active:scale-95 transition-transform">
                            <SlidersHorizontal size={18} />
                        </button>
                    </div>
                </div>
                
                <div className="flex overflow-x-auto hide-scrollbar gap-4 px-4 pb-2">
                    {displayCategories.map((category) => {
                        const isSelected = selectedCategory === category && !searchTerm;
                        return (
                        <button
                            key={category}
                            onClick={() => {
                                setSelectedCategory(category);
                                setSearchTerm(""); 
                            }}
                            className="flex flex-col items-center gap-2 min-w-[64px]"
                        >
                            <div className={`w-16 h-16 rounded-full flex items-center justify-center text-2xl transition-all ${
                                isSelected ? 'bg-white border-2 border-slate-200 shadow-sm' : 'bg-white border border-slate-100 shadow-sm'
                            }`}>
                                {categoryIconMap[category] || '🍽️'}
                            </div>
                            <span className={`text-[10px] font-semibold whitespace-nowrap px-1 pb-1 border-b-2 transition-colors ${
                                isSelected ? 'text-slate-900 border-[#4A7B4F]' : 'text-slate-600 border-transparent'
                            }`}>
                                {category}
                            </span>
                        </button>
                    )})}
                </div>
            </div>

            {/* Section Title */}
            <div className="px-4 py-3 flex justify-between items-center mt-2">
                <h2 className="text-lg font-bold text-slate-800">Popular Dishes</h2>
                <button className="text-xs font-semibold text-[#4A7B4F] flex items-center gap-1">
                    View All <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round"><path d="m9 18 6-6-6-6"/></svg>
                </button>
            </div>

            {/* Menu Items Grid */}
            <div className="px-4 pb-6 grid grid-cols-2 gap-4">
                {displayItems.length === 0 ? (
                    <div className="col-span-2 text-center text-slate-400 py-10 text-sm">
                        No items found.
                    </div>
                ) : (
                    displayItems.map((item, index) => (
                        <div 
                            key={item.itemId} 
                            className="flex flex-col bg-white rounded-[20px] shadow-[0_2px_10px_rgb(0,0,0,0.04)] border border-slate-100 overflow-hidden relative"
                        >
                            {/* Item Image */}
                            {item.image && (
                                <div className="w-full h-[110px] relative overflow-hidden bg-slate-50 flex-shrink-0">
                                    <img 
                                        src={item.image} 
                                        alt={item.name} 
                                        className="w-full h-full object-cover"
                                    />
                                </div>
                            )}

                            {/* Top-Left Badge (Bestseller or Veg) */}
                            <div className={`absolute top-2 left-2 z-10 ${item.image ? '' : 'relative top-0 left-0 mt-3 ml-3 mb-1 self-start'}`}>
                                {index % 3 === 0 ? (
                                    <div className="bg-[#2D6A35] text-white text-[9px] font-bold px-1.5 py-0.5 rounded-sm flex items-center gap-0.5 shadow-sm uppercase tracking-wide inline-flex">
                                        <Star size={8} fill="currentColor" /> Bestseller
                                    </div>
                                ) : item.foodType ? (
                                    <div className={`text-white text-[9px] font-bold px-1.5 py-0.5 rounded-sm flex items-center gap-0.5 shadow-sm uppercase tracking-wide inline-flex ${item.foodType === 'VEG' ? 'bg-[#2D6A35]' : 'bg-[#C93C3C]'}`}>
                                        {item.foodType === 'VEG' ? <Leaf size={8} /> : <div className="w-1.5 h-1.5 bg-white rounded-full"></div>} 
                                        {item.foodType}
                                    </div>
                                ) : null}
                            </div>

                            {/* Item Details */}
                            <div className={`p-3 flex flex-col flex-1 justify-between ${item.image ? 'pt-2.5' : 'pt-1'}`}>
                                <h4 className="font-bold text-slate-800 text-[13px] leading-tight mb-2 line-clamp-2">{item.name}</h4>
                                
                                <div className="flex items-center justify-between mt-auto">
                                    <p className="font-bold text-slate-700 text-sm">₹{item.price}</p>
                                    
                                    {/* Add button / Quantity Selector */}
                                    {getItemQuantity(item.itemId) > 0 ? (
                                        <div className="flex items-center justify-between bg-white border border-[#4A7B4F] rounded-full px-2 py-1 shadow-sm w-[72px]">
                                            <button 
                                                onClick={() => updateQuantity(item.itemId, -1)}
                                                className="w-5 h-5 flex items-center justify-center font-bold text-[#4A7B4F] active:scale-95"
                                            >
                                                −
                                            </button>
                                            <span className="font-bold text-xs text-[#4A7B4F]">
                                                {getItemQuantity(item.itemId)}
                                            </span>
                                            <button 
                                                onClick={() => addToCart(item)}
                                                className="w-5 h-5 flex items-center justify-center font-bold text-[#4A7B4F] active:scale-95"
                                            >
                                                +
                                            </button>
                                        </div>
                                    ) : (
                                        <button 
                                            onClick={() => addToCart(item)}
                                            className="px-4 py-1.5 bg-white border border-[#4A7B4F] text-[#4A7B4F] rounded-full font-bold text-xs flex items-center gap-1 shadow-sm active:scale-95 hover:bg-slate-50 transition-colors"
                                        >
                                            + Add
                                        </button>
                                    )}
                                </div>
                            </div>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
}
