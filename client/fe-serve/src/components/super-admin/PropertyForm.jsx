import { useState } from "react";
import Button from "../common/Button";
import InputField from "../common/InputField";
import * as superAdminApi from "../../api/superAdminApi";

export default function PropertyForm({ onNext }) {
    const [name, setName] = useState("");
    const [logo, setLogo] = useState("");
    const [theme, setTheme] = useState("#2563EB");
    const [address, setAddress] = useState("");
    const [description, setDescription] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const handleLogoUpload = (e) => {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onloadend = () => {
                setLogo(reader.result);
            };
            reader.readAsDataURL(file);
        }
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        setLoading(true);
        setError("");

        try {
            // Subscription expiry 1 year from now
            const expiry = new Date();
            expiry.setFullYear(expiry.getFullYear() + 1);

            const payload = {
                name,
                logo: logo || "https://res.cloudinary.com/demo/image/upload/v1750000000/restaurants/default.png", // fallback
                theme,
                address,
                description,
                phoneNumber,
                subscriptionExpiry: expiry.toISOString().split('Z')[0]
            };

            const response = await superAdminApi.createRestaurant(payload);
            const newRestaurant = response.data.data;
            onNext(newRestaurant.restaurantId);
        } catch (err) {
            console.error("Failed to create property:", err);
            setError(err.response?.data?.message || "Failed to create restaurant");
        } finally {
            setLoading(false);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            {error && <div className="text-red-500 text-sm mb-3">{error}</div>}
            
            <InputField 
                placeholder="Property Name" 
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
            />
            
            <InputField 
                placeholder="Address" 
                className="mt-3" 
                value={address}
                onChange={(e) => setAddress(e.target.value)}
                required
            />
            
            <InputField 
                placeholder="Contact Number (e.g. 919876543210)" 
                className="mt-3" 
                value={phoneNumber}
                onChange={(e) => setPhoneNumber(e.target.value)}
                required
            />
            
            <textarea
                placeholder="Description"
                className="mt-3 h-20 w-full rounded-xl border border-slate-200 px-4 py-3 outline-none focus:border-[#2563EB]"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
            />

            <div className="mt-3">
                <label className="block text-sm font-medium text-slate-700 mb-1">Logo Image</label>
                <input 
                    type="file" 
                    accept="image/*" 
                    onChange={handleLogoUpload}
                    className="w-full text-sm text-slate-500 file:mr-4 file:py-2 file:px-4 file:rounded-xl file:border-0 file:text-sm file:font-semibold file:bg-[#2563EB]/10 file:text-[#2563EB] hover:file:bg-[#2563EB]/20"
                />
            </div>

            <div className="mt-3 flex items-center gap-3">
                <label className="block text-sm font-medium text-slate-700">Theme Color</label>
                <input 
                    type="color" 
                    value={theme}
                    onChange={(e) => setTheme(e.target.value)}
                    className="h-8 w-8 rounded cursor-pointer border-0 p-0"
                />
            </div>

            <Button type="submit" className="mt-4 w-full" disabled={loading}>
                {loading ? "Creating..." : "Next: Add Admin"}
            </Button>
        </form>
    );
}
