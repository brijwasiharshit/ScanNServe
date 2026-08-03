import React, { useState, useMemo } from 'react';
import { Trash2, Plus, Minus, Send, X, ShoppingCart, CheckSquare, ChevronRight } from 'lucide-react';
import Button from '../common/Button';

export default function POSView({ menu = [], restaurant }) {
    const [cart, setCart] = useState([]);
    const [activeCategory, setActiveCategory] = useState("All");
    const [isWhatsAppModalOpen, setIsWhatsAppModalOpen] = useState(false);
    const [customerPhone, setCustomerPhone] = useState("");
    const [discount, setDiscount] = useState(0);

    // Extract unique categories from the menu
    const categories = useMemo(() => {
        const cats = new Set(menu.map(item => item.categoryName || item.category || "Uncategorized"));
        return ["All", ...Array.from(cats)];
    }, [menu]);

    // Filter menu items based on active category
    const filteredMenu = useMemo(() => {
        if (activeCategory === "All") return menu;
        return menu.filter(item => (item.categoryName || item.category || "Uncategorized") === activeCategory);
    }, [menu, activeCategory]);

    // Cart operations
    const addToCart = (item) => {
        setCart(prev => {
            const existing = prev.find(cartItem => cartItem.itemId === item.itemId);
            if (existing) {
                return prev.map(cartItem => 
                    cartItem.itemId === item.itemId 
                        ? { ...cartItem, quantity: cartItem.quantity + 1 }
                        : cartItem
                );
            }
            return [...prev, { ...item, quantity: 1 }];
        });
    };

    const updateQuantity = (itemId, delta) => {
        setCart(prev => prev.map(cartItem => {
            if (cartItem.itemId === itemId) {
                const newQuantity = cartItem.quantity + delta;
                return newQuantity > 0 ? { ...cartItem, quantity: newQuantity } : cartItem;
            }
            return cartItem;
        }));
    };

    const removeFromCart = (itemId) => {
        setCart(prev => prev.filter(cartItem => cartItem.itemId !== itemId));
    };

    const clearCart = () => {
        if (window.confirm("Are you sure you want to clear the current order?")) {
            setCart([]);
        }
    };

    // Calculations
    const subtotal = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);
    const tax = 0; // Tax logic can be added here later
    const grandTotal = Math.max(0, subtotal - discount + tax);
    const totalItems = cart.reduce((sum, item) => sum + item.quantity, 0);

    const handleSendBillClick = () => {
        if (cart.length === 0) {
            alert("Cart is empty!");
            return;
        }
        setIsWhatsAppModalOpen(true);
    };

    const confirmSendBill = () => {
        if (!customerPhone) {
            alert("Please enter a phone number");
            return;
        }

        const restaurantName = restaurant?.name || "Restaurant";
        
        const currentDate = new Date().toLocaleString('en-IN', {
            dateStyle: 'medium',
            timeStyle: 'short'
        });

        let message = `*ORDER INVOICE*\n`;
        message += `*${restaurantName}*\n\n`;
        message += `*Time:* ${currentDate}\n\n`;

        message += "```\n";
        message += "Item          Qty   Total\n";
        message += "-------------------------\n";

        cart.forEach(item => {
            let name = item.itemName || item.name;
            if (name.length > 13) {
                name = name.substring(0, 11) + "..";
            }
            name = name.padEnd(13, ' ');
            const qty = String(item.quantity).padStart(3, ' ');
            const total = String((item.price * item.quantity).toFixed(2)).padStart(8, ' ');
            message += `${name} ${qty} ${total}\n`;
        });

        message += "-------------------------\n";
        
        if (discount > 0) {
            const discountLabel = "Discount".padEnd(13, ' ');
            const discountQty = "   ";
            const discountVal = String(`-${discount.toFixed(2)}`).padStart(8, ' ');
            message += `${discountLabel} ${discountQty} ${discountVal}\n`;
        }
        
        if (tax > 0) {
            const taxLabel = "Tax".padEnd(13, ' ');
            const taxQty = "   ";
            const taxVal = String(tax.toFixed(2)).padStart(8, ' ');
            message += `${taxLabel} ${taxQty} ${taxVal}\n`;
        }
        
        const totalStr = "Total".padEnd(13, ' ');
        const totalItemsStr = String(totalItems).padStart(3, ' ');
        const grandTotalStr = String(grandTotal.toFixed(2)).padStart(8, ' ');
        message += `${totalStr} ${totalItemsStr} ${grandTotalStr}\n`;
        message += "```\n\n";

        message += `_Thank you for ordering!_`;

        const encodedMessage = encodeURIComponent(message);
        const whatsappUrl = `https://wa.me/${customerPhone.replace(/[^0-9]/g, '')}?text=${encodedMessage}`;
        
        // Reset cart and states before redirecting
        setCart([]);
        setDiscount(0);
        setIsWhatsAppModalOpen(false);
        setCustomerPhone("");
        
        window.open(whatsappUrl, "_blank");
    };

    return (
        <div className="flex flex-col lg:flex-row gap-6 mt-6 h-auto lg:h-[calc(100vh-250px)] lg:min-h-[500px]">
            {/* Left Side: Menu Grid */}
            <div className="flex-1 flex flex-col min-h-[500px] lg:min-h-0 bg-white rounded-2xl border border-slate-200 shadow-sm overflow-hidden">
                {/* Category Filter */}
                <div className="p-4 border-b border-slate-200 overflow-x-auto whitespace-nowrap scrollbar-hide">
                    <div className="flex gap-2">
                        {categories.map(cat => (
                            <button
                                key={cat}
                                onClick={() => setActiveCategory(cat)}
                                className={`px-4 py-2 rounded-full text-sm font-medium transition-colors ${
                                    activeCategory === cat 
                                        ? 'bg-[#155E75] text-white' 
                                        : 'bg-slate-100 text-slate-600 hover:bg-slate-200'
                                }`}
                            >
                                {cat}
                            </button>
                        ))}
                    </div>
                </div>

                {/* Grid */}
                <div className="flex-1 overflow-y-auto p-4">
                    {filteredMenu.length === 0 ? (
                        <div className="text-center text-slate-500 mt-10">No items found in this category.</div>
                    ) : (
                        <div className="grid grid-cols-2 md:grid-cols-3 xl:grid-cols-4 gap-4">
                            {filteredMenu.map(item => (
                                <div key={item.itemId} className="border border-slate-200 rounded-xl overflow-hidden shadow-sm flex flex-col hover:border-[#155E75] transition-colors">
                                    <div className="h-32 bg-slate-100 relative">
                                        {item.customImage || item.image ? (
                                            <img src={item.customImage || item.image} alt={item.itemName || item.name} className="w-full h-full object-cover" />
                                        ) : (
                                            <div className="w-full h-full flex items-center justify-center text-slate-400">
                                                No Image
                                            </div>
                                        )}
                                    </div>
                                    <div className="p-3 flex-1 flex flex-col">
                                        <h3 className="text-sm font-semibold text-slate-800 line-clamp-1">{item.itemName || item.name}</h3>
                                        <p className="text-xs text-slate-500 mt-1 line-clamp-1">{item.categoryName || item.category}</p>
                                        <div className="mt-auto pt-3 flex items-center justify-between">
                                            <span className="font-bold text-[#155E75]">Rs. {item.price}</span>
                                            <button 
                                                onClick={() => addToCart(item)}
                                                disabled={!item.available}
                                                className={`px-3 py-1 text-xs font-semibold rounded-lg border ${
                                                    item.available 
                                                        ? 'border-[#155E75] text-[#155E75] hover:bg-[#155E75] hover:text-white transition-colors' 
                                                        : 'border-slate-300 text-slate-400 cursor-not-allowed'
                                                }`}
                                            >
                                                + Add
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            </div>

            {/* Right Side: Cart */}
            <div className="w-full lg:w-[420px] flex flex-col bg-[#FAFAFA] rounded-2xl border border-slate-200 shadow-sm flex-shrink-0 overflow-hidden font-sans relative min-h-[400px] lg:min-h-0">
                {/* Cart Header */}
                <div className="p-4 border-b border-slate-200 bg-white flex justify-between items-center shadow-sm z-10 relative">
                    <h2 className="text-lg font-serif font-bold text-slate-800 tracking-wide">POS Order</h2>
                    <button
                        onClick={clearCart}
                        disabled={cart.length === 0}
                        className={`flex items-center gap-1.5 px-3 py-2 rounded-lg text-xs font-bold active:scale-95 transition-colors ${
                            cart.length > 0 ? 'bg-red-50 border border-red-100 text-red-600 hover:bg-red-100' : 'bg-slate-50 text-slate-400 cursor-not-allowed'
                        }`}
                    >
                        <Trash2 size={14} />
                        Clear
                    </button>
                </div>

                <div className="flex-1 overflow-y-auto p-4 flex flex-col relative pb-32">
                    {cart.length === 0 ? (
                        <div className="flex-1 flex flex-col items-center justify-center p-5 text-slate-500 m-auto">
                            <ShoppingCart size={48} className="text-slate-200 mb-4" />
                            <p className="font-medium text-slate-600">Your cart is empty.</p>
                            <p className="text-xs mt-1 text-slate-400">Add items from the menu to start</p>
                        </div>
                    ) : (
                        <>
                            {/* Your Cart Banner */}
                            <div className="bg-[#121814] rounded-2xl p-4 flex items-center gap-4 mb-4 shadow-lg shrink-0">
                                <div className="w-12 h-12 rounded-full bg-[#3D5638] flex items-center justify-center flex-shrink-0">
                                    <ShoppingCart size={24} className="text-white" />
                                </div>
                                <div className="text-white">
                                    <h2 className="text-xl font-bold mb-0.5">Your Cart</h2>
                                    <p className="text-[#8FB390] text-xs font-medium">
                                        {totalItems} Items • All items selected
                                    </p>
                                </div>
                            </div>

                            {/* Cart Items List */}
                            <div className="flex flex-col gap-3 mb-6 shrink-0">
                                {cart.map(item => (
                                    <div key={item.itemId} className="bg-white rounded-xl shadow-sm border border-slate-100 p-3 flex items-start gap-3 relative">
                                        {/* Checkbox Icon */}
                                        <div className="pt-8">
                                            <CheckSquare size={20} className="text-[#155E75] fill-[#155E75]/10" />
                                        </div>

                                        {/* Item Image */}
                                        <div className="w-20 h-20 rounded-lg overflow-hidden bg-slate-50 flex-shrink-0">
                                            {item.customImage || item.image ? (
                                                <img src={item.customImage || item.image} alt={item.itemName || item.name} className="w-full h-full object-cover" />
                                            ) : (
                                                <div className="w-full h-full flex items-center justify-center">
                                                    <ShoppingCart size={20} className="text-slate-300" />
                                                </div>
                                            )}
                                        </div>

                                        {/* Item Details */}
                                        <div className="flex-1 min-w-0 pr-6">
                                            <h4 className="font-bold text-slate-800 text-sm leading-tight mb-1 truncate">{item.itemName || item.name}</h4>
                                            
                                            <p className="font-bold text-slate-800 text-sm mt-2">Rs. {(item.price * item.quantity).toFixed(2)}</p>
                                        </div>

                                        {/* Remove Button (X) */}
                                        <button
                                            onClick={() => removeFromCart(item.itemId)}
                                            className="absolute top-3 right-3 text-slate-400 hover:text-slate-600 active:scale-95"
                                        >
                                            <X size={16} />
                                        </button>

                                        {/* Quantity Selector */}
                                        <div className="absolute bottom-3 right-3 flex items-center bg-[#F0F9FF] rounded-full px-1.5 py-0.5 shadow-sm border border-[#E0F2FE]">
                                            <button
                                                onClick={() => updateQuantity(item.itemId, -1)}
                                                className="w-6 h-6 flex items-center justify-center font-bold text-[#0369A1] text-lg active:scale-95"
                                            >
                                                −
                                            </button>
                                            <span className="font-bold text-sm w-6 text-center text-[#0369A1]">
                                                {item.quantity}
                                            </span>
                                            <button
                                                onClick={() => updateQuantity(item.itemId, 1)}
                                                className="w-6 h-6 flex items-center justify-center font-bold text-[#0369A1] text-lg active:scale-95"
                                            >
                                                +
                                            </button>
                                        </div>
                                    </div>
                                ))}
                            </div>

                            {/* Bill Summary */}
                            <div className="bg-white rounded-xl shadow-sm border border-slate-100 p-4 mb-4 shrink-0">
                                <h3 className="font-bold text-slate-800 text-sm mb-4">Bill Summary</h3>

                                <div className="flex justify-between items-center mb-2.5">
                                    <span className="text-slate-600 text-xs">Item Total ({totalItems} items)</span>
                                    <span className="font-semibold text-slate-800 text-xs">Rs. {subtotal.toFixed(2)}</span>
                                </div>
                                
                                <div className="flex justify-between items-center mb-2.5">
                                    <span className="text-slate-600 text-xs">Discount</span>
                                    <input 
                                        type="number" 
                                        min="0"
                                        max={subtotal}
                                        value={discount === 0 ? '' : discount}
                                        onChange={(e) => setDiscount(Number(e.target.value) || 0)}
                                        placeholder="0"
                                        className="w-20 px-2 py-0.5 text-right border border-slate-200 rounded text-xs outline-none focus:border-[#155E75] focus:ring-1 focus:ring-[#155E75]"
                                    />
                                </div>
                                
                                {tax > 0 && (
                                    <div className="flex justify-between items-center mb-2.5">
                                        <span className="text-slate-600 text-xs">Tax</span>
                                        <span className="font-semibold text-slate-800 text-xs">Rs. {tax.toFixed(2)}</span>
                                    </div>
                                )}

                                <div className="flex justify-between items-center pt-2 border-t border-dashed border-slate-200 mt-2">
                                    <span className="font-bold text-slate-800 text-sm">To Pay</span>
                                    <span className="font-bold text-[#155E75] text-lg">Rs. {grandTotal.toFixed(2)}</span>
                                </div>
                            </div>
                        </>
                    )}
                </div>

                {/* Sticky Checkout Footer */}
                {cart.length > 0 && (
                    <div className="absolute bottom-0 left-0 right-0 bg-white border-t border-slate-100 p-4 pb-safe shadow-[0_-8px_20px_-10px_rgba(0,0,0,0.1)] z-20">
                        <button
                            onClick={handleSendBillClick}
                            className="w-full py-3.5 bg-[#22c55e] hover:bg-[#16a34a] text-white rounded-xl shadow-lg transition-transform active:scale-95 flex items-center justify-between px-6"
                        >
                            <div className="flex flex-col items-start text-left">
                                <span className="text-[10px] font-medium text-white/80">TOTAL</span>
                                <span className="font-bold text-base">Rs. {grandTotal.toFixed(2)}</span>
                            </div>
                            <div className="flex items-center gap-2">
                                <Send size={16} />
                                <span className="font-bold text-sm tracking-wide">WhatsApp Bill</span>
                                <ChevronRight size={18} />
                            </div>
                        </button>
                    </div>
                )}
            </div>

            {/* WhatsApp Modal */}
            {isWhatsAppModalOpen && (
                <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50 backdrop-blur-sm p-4">
                    <div className="bg-white rounded-2xl w-full max-w-md shadow-2xl overflow-hidden animate-in fade-in zoom-in-95 duration-200">
                        <div className="flex justify-between items-center p-5 border-b border-slate-100 bg-slate-50/50">
                            <div className="flex items-center gap-3">
                                <div className="bg-[#22c55e]/10 p-2 rounded-full text-[#16a34a]">
                                    <Send size={20} />
                                </div>
                                <h3 className="text-lg font-bold text-slate-800">Send WhatsApp Bill</h3>
                            </div>
                            <button 
                                onClick={() => setIsWhatsAppModalOpen(false)}
                                className="text-slate-400 hover:text-slate-600 transition-colors p-1"
                            >
                                <X size={20} />
                            </button>
                        </div>
                        
                        <div className="p-6">
                            <div>
                                <label className="block text-sm font-semibold text-slate-700 mb-2">
                                    Customer Mobile Number
                                </label>
                                <p className="text-xs text-slate-500 mb-3">Include country code (e.g., 919876543210)</p>
                                <input
                                    type="tel"
                                    value={customerPhone}
                                    onChange={(e) => setCustomerPhone(e.target.value)}
                                    placeholder="e.g. 919876543210"
                                    className="w-full border-2 border-slate-200 rounded-xl px-4 py-3 outline-none focus:border-[#155E75] focus:ring-4 focus:ring-[#155E75]/10 transition-all text-lg font-medium text-slate-800 placeholder:font-normal placeholder:text-slate-400"
                                    autoFocus
                                    onKeyDown={(e) => {
                                        if (e.key === 'Enter') confirmSendBill();
                                    }}
                                />
                            </div>
                        </div>
                        
                        <div className="p-5 border-t border-slate-100 flex gap-3 bg-slate-50">
                            <Button 
                                variant="outline" 
                                className="flex-1 py-2.5 font-semibold text-slate-700 border-slate-300 hover:bg-slate-100"
                                onClick={() => setIsWhatsAppModalOpen(false)}
                            >
                                Cancel
                            </Button>
                            <Button 
                                className="flex-1 py-2.5 font-bold bg-[#22c55e] hover:bg-[#16a34a] text-white shadow-md flex justify-center items-center gap-2"
                                onClick={confirmSendBill}
                            >
                                <Send size={16} />
                                Send Bill
                            </Button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}
