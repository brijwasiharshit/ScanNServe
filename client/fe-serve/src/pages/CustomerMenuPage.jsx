import { useState } from "react";
import { useParams } from "react-router-dom";
import useCustomerMenu from "../hooks/useCustomerMenu";
import useCart from "../hooks/useCart";
import CustomerMenu from "../components/customer/CustomerMenu";
import CartModal from "../components/customer/CartModal";
import { MapPin, Phone, ShoppingBag, Menu, ShoppingCart } from "lucide-react";

export default function CustomerMenuPage() {
    const { tableToken } = useParams();
    const { menuData, categories, loading, error } = useCustomerMenu(tableToken);
    
    // Cart setup
    const { cart, addToCart, updateQuantity, removeFromCart, clearCart, getCartTotal } = useCart(tableToken);
    const [isCartModalOpen, setIsCartModalOpen] = useState(false);

    if (loading) {
        return (
            <div className="min-h-screen bg-white flex justify-center items-center">
                <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-[#4A7B4F]"></div>
            </div>
        );
    }

    if (error) {
        return (
            <div className="min-h-screen bg-slate-50 p-4 text-center text-red-500 pt-20">
                <p>{error}</p>
            </div>
        );
    }

    const restaurant = menuData?.restaurant;

    return (
        <div className="min-h-screen bg-[#FAFAFA] pb-6 font-sans">
            {/* Top Navigation Bar */}
            <div className="bg-[#FAFAFA] px-4 py-3 flex items-center justify-between sticky top-0 z-30">
                {/* Hamburger Menu */}
                <button className="p-1">
                    <Menu size={24} className="text-slate-800" />
                </button>
                
                {/* Centered Restaurant Name / Logo */}
                <div className="flex flex-col items-center justify-center">
                    <h1 className="text-xl font-serif font-bold text-slate-800 tracking-wide text-center">
                        {restaurant?.name || "Spartans Cafe"}
                    </h1>
                </div>

                {/* Cart Icon */}
                <button 
                    onClick={() => setIsCartModalOpen(true)}
                    className="relative p-1"
                >
                    <ShoppingCart size={24} className="text-slate-800" />
                    {cart.length > 0 && (
                        <span className="absolute -top-1 -right-1 bg-[#E85D04] text-white text-[10px] w-4 h-4 flex items-center justify-center rounded-full font-bold">
                            {cart.reduce((total, item) => total + item.quantity, 0)}
                        </span>
                    )}
                </button>
            </div>

            {/* Header Banner Area */}
            <div className="px-4 pb-2">
                <div className="relative bg-[#182A1E] rounded-2xl overflow-hidden shadow-md min-h-[160px] flex items-center p-5">
                    {/* Background Pattern / Overlay */}
                    <div className="absolute inset-0 opacity-20 bg-[radial-gradient(circle_at_top_right,_var(--tw-gradient-stops))] from-white via-transparent to-transparent"></div>
                    
                    {/* Optional right side image (Food Plate placeholder) */}
                    <div className="absolute -right-8 top-1/2 -translate-y-1/2 w-48 h-48 bg-black/10 rounded-full blur-xl"></div>
                    
                    <div className="relative z-10 flex w-full justify-between items-center gap-4">
                        {/* Text Content */}
                        <div className="flex-1 text-white">
                            <h2 className="text-xl font-bold leading-tight">Good Food,</h2>
                            <h2 className="text-2xl font-bold italic text-[#E85D04] mb-3 font-serif">Great Moments!</h2>
                            
                            <p className="text-[#A3B8AD] text-[11px] mb-4">
                                Fresh ingredients. Authentic taste.
                            </p>
                            
                            {/* Made with love badge */}
                            <div className="inline-flex items-center gap-1.5 px-3 py-1.5 rounded-full border border-white/20 bg-transparent">
                                <span className="text-white text-[10px] tracking-wide font-medium flex items-center gap-1">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="10" height="10" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M19 14c1.49-1.46 3-3.21 3-5.5A5.5 5.5 0 0 0 16.5 3c-1.76 0-3 .5-4.5 2-1.5-1.5-2.74-2-4.5-2A5.5 5.5 0 0 0 2 8.5c0 2.3 1.5 4.05 3 5.5l7 7Z"/></svg>
                                    Made with love
                                </span>
                            </div>
                        </div>

                        {/* Pagination Dots Placeholder */}
                        <div className="absolute bottom-2 left-1/2 -translate-x-1/2 flex gap-1">
                            <div className="w-1.5 h-1.5 rounded-full bg-white"></div>
                            <div className="w-1.5 h-1.5 rounded-full bg-white/40"></div>
                            <div className="w-1.5 h-1.5 rounded-full bg-white/40"></div>
                        </div>
                    </div>
                </div>
            </div>

            {/* Menu Content */}
            <div className="mx-auto max-w-lg">
                <CustomerMenu 
                    menuData={menuData} 
                    categories={categories}
                    theme="#4A7B4F"
                    addToCart={addToCart}
                    updateQuantity={updateQuantity}
                    getItemQuantity={(itemId) => {
                        const item = cart.find(i => i.itemId === itemId);
                        return item ? item.quantity : 0;
                    }}
                />
            </div>

            {/* Floating Cart Pill */}
            {cart.length > 0 && (
                <div className="fixed bottom-6 left-0 right-0 px-4 z-40 pointer-events-none flex justify-center pb-safe">
                    <div className="w-full max-w-sm pointer-events-auto">
                        <button 
                            onClick={() => setIsCartModalOpen(true)}
                            className="w-full flex items-center justify-between px-4 py-3 sm:py-3.5 rounded-full text-white font-bold shadow-[0_8px_30px_rgb(0,0,0,0.12)] transition-transform active:scale-95 bg-[#4A7B4F] hover:bg-[#3D6641] border border-white/10"
                        >
                            <div className="flex items-center gap-2">
                                <div className="relative">
                                    <ShoppingBag size={20} />
                                    <span className="absolute -top-1 -right-1 bg-white text-[#4A7B4F] text-[10px] w-4 h-4 flex items-center justify-center rounded-full font-extrabold border border-[#4A7B4F]">
                                        {cart.reduce((total, item) => total + item.quantity, 0)}
                                    </span>
                                </div>
                                <span className="text-sm font-semibold tracking-wide ml-1">View Cart</span>
                            </div>
                            <div className="flex items-center gap-1">
                                <span className="text-sm font-bold">₹{getCartTotal().toFixed(2)}</span>
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round"><path d="m9 18 6-6-6-6"/></svg>
                            </div>
                        </button>
                    </div>
                </div>
            )}
            
            {/* Cart Modal */}
            {isCartModalOpen && (
                <CartModal 
                    cart={cart}
                    onClose={() => setIsCartModalOpen(false)}
                    updateQuantity={updateQuantity}
                    removeFromCart={removeFromCart}
                    clearCart={clearCart}
                    getCartTotal={getCartTotal}
                    theme={restaurant?.theme || '#2563EB'}
                    tableNumber={menuData?.table?.tableNumber}
                    phoneNumber={restaurant?.phoneNumber}
                    restaurantName={restaurant?.name}
                    tableToken={tableToken}
                />
            )}
        </div>
    );
}
