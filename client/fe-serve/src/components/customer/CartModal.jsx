import { ArrowLeft, Trash2, CheckSquare, Ticket, Info, ShieldCheck, ChevronRight, ShoppingCart, X } from "lucide-react";
import { placeOrder } from "../../services/userService";

export default function CartModal({ 
    cart, 
    onClose, 
    updateQuantity,
    removeFromCart,
    clearCart,
    getCartTotal, 
    theme = '#F97316',
    tableNumber,
    phoneNumber,
    restaurantName,
    tableToken
}) {
    const itemTotal = getCartTotal();
    const grandTotal = itemTotal;
    const totalItems = cart.reduce((total, item) => total + item.quantity, 0);

    const handleClearCart = () => {
        clearCart();
    };

    // Generate WhatsApp Order URL
    const handleOrder = () => {
        if (!phoneNumber) {
            alert("Restaurant phone number is not available.");
            return;
        }

        // Clean phone number
        let cleanPhone = phoneNumber.replace(/[^\d+]/g, '');
        if (cleanPhone.startsWith('+')) {
            cleanPhone = cleanPhone.substring(1);
        }

        let message = `*New Order!*\n`;
        if (tableNumber) {
            message += `*Table:* ${tableNumber}\n`;
        }
        message += `--------------------\n`;
        
        cart.forEach(item => {
            message += `${item.quantity} x ${item.name} - ₹${(item.price * item.quantity).toFixed(2)}\n`;
        });
        
        message += `--------------------\n`;
        message += `*Total: ₹${grandTotal.toFixed(2)}*`;

        // Asynchronously call the backend to place the order in DB
        const orderItems = cart.map(item => ({
            restaurantMenuItemId: item.itemId,
            quantity: item.quantity
        }));
        placeOrder(tableToken, orderItems).catch(err => {
            console.error("Failed to sync order with backend:", err);
        });

        // Continue to redirect to WhatsApp
        const encodedMessage = encodeURIComponent(message);
        const waUrl = `https://wa.me/${cleanPhone}?text=${encodedMessage}`;
        
        window.open(waUrl, "_blank");
    };

    if (cart.length === 0) {
        return (
            <div className="fixed inset-0 z-50 flex flex-col bg-white animate-in fade-in duration-200 font-sans">
                <div className="flex items-center justify-between p-4 sticky top-0 bg-white">
                    <button onClick={onClose} className="p-2 border border-slate-200 rounded-lg shadow-sm active:scale-95">
                        <ArrowLeft size={20} className="text-slate-700" />
                    </button>
                    <h1 className="text-lg font-serif font-bold text-slate-800">{restaurantName || "Restaurant"}</h1>
                    <div className="w-10"></div>
                </div>
                <div className="flex-1 flex flex-col items-center justify-center p-5 text-slate-500">
                    <ShoppingCart size={48} className="text-slate-200 mb-4" />
                    <p>Your cart is empty.</p>
                </div>
            </div>
        );
    }

    return (
        <div className="fixed inset-0 z-50 flex flex-col bg-[#FAFAFA] animate-in slide-in-from-bottom duration-300 font-sans overflow-hidden">
            {/* Top Navigation */}
            <div className="flex items-center justify-between p-4 sticky top-0 bg-[#FAFAFA] z-10">
                <button onClick={onClose} className="p-2 border border-slate-200 rounded-lg shadow-sm bg-white active:scale-95">
                    <ArrowLeft size={20} className="text-slate-700" />
                </button>
                
                <h1 className="text-lg font-serif font-bold text-slate-800 tracking-wide">
                    {restaurantName || "Restaurant"}
                </h1>

                <button 
                    onClick={handleClearCart}
                    className="flex items-center gap-1.5 px-3 py-2 bg-green-50 border border-green-100 rounded-lg text-[#4A7B4F] text-xs font-bold active:scale-95"
                >
                    <Trash2 size={14} />
                    Clear Cart
                </button>
            </div>

            <div className="flex-1 overflow-y-auto px-4 pb-32">
                {/* Your Cart Banner */}
                <div className="bg-[#121814] rounded-2xl p-4 flex items-center gap-4 mb-4 shadow-lg">
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
                <div className="flex flex-col gap-3 mb-6">
                    {cart.map((item) => (
                        <div key={item.itemId} className="bg-white rounded-xl shadow-sm border border-slate-100 p-3 flex items-start gap-3 relative">
                            {/* Checkbox Icon */}
                            <div className="pt-8">
                                <CheckSquare size={20} className="text-[#4A7B4F] fill-[#4A7B4F]/10" />
                            </div>
                            
                            {/* Item Image */}
                            <div className="w-20 h-20 rounded-lg overflow-hidden bg-slate-50 flex-shrink-0">
                                {item.image ? (
                                    <img src={item.image} alt={item.name} className="w-full h-full object-cover" />
                                ) : (
                                    <div className="w-full h-full flex items-center justify-center">
                                        <ShoppingCart size={20} className="text-slate-300" />
                                    </div>
                                )}
                            </div>

                            {/* Item Details */}
                            <div className="flex-1 min-w-0 pr-6">
                                <h4 className="font-bold text-slate-800 text-sm leading-tight mb-1 truncate">{item.name}</h4>
                                { (item.foodType || item.tag) && (
                                    <div className="flex items-center gap-2 mb-2 mt-1">
                                        {item.foodType && (
                                            <div className={`w-2.5 h-2.5 rounded-full shadow-sm ${item.foodType === 'VEG' ? 'bg-[#3F7C48]' : 'bg-[#C93C3C]'}`}></div>
                                        )}
                                        {item.tag && (
                                            <span className={`text-[9px] font-bold px-1.5 py-0.5 rounded-sm shadow-sm text-white uppercase tracking-wide inline-flex items-center gap-0.5
                                                ${item.tag === 'BESTSELLER' ? 'bg-[#F59E0B]' : 
                                                  item.tag === 'HIGH_PROTEIN' ? 'bg-[#3B82F6]' : 
                                                  item.tag === 'BUDGET_PICK' ? 'bg-[#10B981]' : 
                                                  item.tag === 'QUICK_BITE' ? 'bg-[#F43F5E]' : 'bg-slate-600'}`
                                            }>
                                                {item.tag.replace('_', ' ')}
                                            </span>
                                        )}
                                    </div>
                                )}
                                <p className="font-bold text-slate-800 text-sm">₹{(item.price * item.quantity).toFixed(2)}</p>
                            </div>

                            {/* Remove Button (X) */}
                            <button 
                                onClick={() => removeFromCart(item.itemId)}
                                className="absolute top-3 right-3 text-slate-400 hover:text-slate-600 active:scale-95"
                            >
                                <X size={16} />
                            </button>

                            {/* Quantity Selector */}
                            <div className="absolute bottom-3 right-3 flex items-center bg-[#F3F8F4] rounded-full px-1.5 py-0.5 shadow-sm border border-[#E2EBE4]">
                                <button 
                                    onClick={() => updateQuantity(item.itemId, -1)}
                                    className="w-6 h-6 flex items-center justify-center font-bold text-[#4A7B4F] text-lg active:scale-95"
                                >
                                    −
                                </button>
                                <span className="font-bold text-sm w-6 text-center text-[#4A7B4F]">
                                    {item.quantity}
                                </span>
                                <button 
                                    onClick={() => updateQuantity(item.itemId, 1)}
                                    className="w-6 h-6 flex items-center justify-center font-bold text-[#4A7B4F] text-lg active:scale-95"
                                >
                                    +
                                </button>
                            </div>
                        </div>
                    ))}
                </div>


                {/* Bill Summary */}
                <div className="bg-white rounded-xl shadow-sm border border-slate-100 p-4 mb-4">
                    <h3 className="font-bold text-slate-800 text-sm mb-4">Bill Summary</h3>
                    
                    <div className="flex justify-between items-center mb-2.5">
                        <span className="text-slate-600 text-xs">Item Total ({totalItems} items)</span>
                        <span className="font-semibold text-slate-800 text-xs">₹{itemTotal.toFixed(2)}</span>
                    </div>
                    
                    <div className="flex justify-between items-center pt-2 border-t border-dashed border-slate-200">
                        <span className="font-bold text-slate-800 text-sm">To Pay</span>
                        <span className="font-bold text-[#4A7B4F] text-lg">₹{grandTotal.toFixed(2)}</span>
                    </div>
                </div>
            </div>

            {/* Sticky Checkout Footer */}
            <div className="absolute bottom-0 left-0 right-0 bg-white border-t border-slate-100 p-4 pb-safe shadow-[0_-8px_20px_-10px_rgba(0,0,0,0.1)] z-20">
                <button 
                    onClick={handleOrder}
                    className="w-full py-3.5 bg-[#3B6641] hover:bg-[#2F5234] text-white rounded-xl shadow-lg transition-transform active:scale-95 flex items-center justify-between px-6"
                >
                    <div className="flex flex-col items-start text-left">
                        <span className="text-[10px] font-medium text-white/80">TOTAL TO PAY</span>
                        <span className="font-bold text-base">₹{grandTotal.toFixed(2)}</span>
                    </div>
                    <div className="flex items-center gap-2">
                        <span className="font-bold text-sm tracking-wide">Place Order</span>
                        <ChevronRight size={18} />
                    </div>
                </button>
            </div>
        </div>
    );
}
